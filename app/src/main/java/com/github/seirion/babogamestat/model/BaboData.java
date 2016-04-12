package com.github.seirion.babogamestat.model;

import io.realm.RealmObject;

public class BaboData extends RealmObject {

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