package com.github.seirion.babogamestat.io;

import com.github.seirion.babogamestat.model.BaboData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Loader {
    private static Loader INSTANCE = new Loader();
    public static Loader instance() {
        return INSTANCE;
    }
    private Loader() {}

    public List<BaboData> loadFromLocal() {
        List<BaboData> list = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<BaboData> results = realm.where(BaboData.class).findAll();
        results.sort("date");
        list.addAll(results);
        return list;
    }

    public List<BaboData> loadFromNetwork() {
        List<BaboData> list = new ArrayList<>();
        try {
            URL url = new URL("https://raw.githubusercontent.com/seirion/myutil/master/statistics/seconds.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                BaboData data = BaboData.loadFromString(inputLine);
                if (data != null) {
                    list.add(data);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
