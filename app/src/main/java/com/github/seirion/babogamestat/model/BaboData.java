package com.github.seirion.babogamestat.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class BaboData extends RealmObject {

    private int date;
    private long current;
    private long base;

    @Ignore
    private int sessionId;


    public int getDate() {
        return date;
    }

    public long getCurrent() {
        return current;
    }

    public long getBase() {
        return base;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public void setBase(long base) {
        this.base = base;
    }

    public List<BaboData> getAll() {
        List<BaboData> list = new ArrayList<>();
        try {
            //RealmResults<BaboData> results = realm.where(BaboData.class).findAll();
            //results.sort("date");
        } catch (Exception e) {

        }
        return list;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaboData)) {
            return false;
        }
        BaboData data = (BaboData)o;
        return this.date == data.date;
    }

    @Override
    public int hashCode() {
        return date * 31;
    }
}