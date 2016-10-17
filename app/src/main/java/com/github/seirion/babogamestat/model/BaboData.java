package com.github.seirion.babogamestat.model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BaboData extends RealmObject implements Comparable<BaboData>{

    @PrimaryKey
    private int date;
    private long current;
    private long base;

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

    public static BaboData create(int date, long current, long base) {
        BaboData data = new BaboData();
        data.setDate(date);
        data.setCurrent(current);
        data.setBase(base);
        return data;
    }

    public static BaboData loadFromString(String str) {
        BaboData data = new BaboData();
        String [] parsed = str.split(" ");
        if (parsed.length != 3) return null;
        try {
            data.setDate(Integer.parseInt(parsed[0]));
            data.setCurrent(Long.parseLong(parsed[1]));
            data.setBase(Long.parseLong(parsed[2]));
        } catch (NumberFormatException e) {
            return null;
        }
        return data;
    }

    public static boolean save(List<BaboData> dataList) {
        if (dataList == null || dataList.isEmpty()) return false;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(dataList);
        realm.commitTransaction();
        return true;
    }

    public static boolean save(BaboData baboData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(baboData);
        realm.commitTransaction();
        return true;
    }

    @Override
    public String toString() {
        return date + " : " + current + " / " + base;
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

    @Override
    public int compareTo(BaboData another) {
        return this.date - another.date;
    }
}