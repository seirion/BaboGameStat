package com.github.seirion.babogamestat;

import com.db.chart.model.LineSet;
import com.github.seirion.babogamestat.model.BaboData;

import java.util.List;

public class DataSet {
    private static DataSet INSTANCE = new DataSet();
    private List<BaboData> origin;
    private float min = 0f;
    private float max = 0f;

    public static DataSet instance() {
        return INSTANCE;
    }

    private DataSet() {} // singleton

    public void init(List<BaboData> origin) {
        this.origin = origin;
    }

    public LineSet getLineSet() {
        final int MAX_NUM = 12;
        LineSet lineSet = new LineSet();

        min = Float.MAX_VALUE;
        max = Float.MIN_VALUE;

        int sampleRate = (origin.size() + MAX_NUM - 1) / MAX_NUM;
        int i = origin.size() % sampleRate + 1;

        for (BaboData org : origin) {
            int date = org.getDate() % 10000;
            String label = (i++ % sampleRate == 0) ? String.format("%02d.%02d", date / 100, date % 100) : "";
            float value = (org.getCurrent() - org.getBase()) * 100 / (float)org.getBase();
            lineSet.addPoint(label, value);

            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        lineSet.setSmooth(true)
                .setFill(0x330000FF)
                .setDotsColor(0x99DD1010)
                .setDotsRadius(10)
                .setThickness(4)
                .setDashed(new float[] {4, 4});

        return lineSet;
    }

    public int size() {
        return origin.size();
    }

    public BaboData get(int index) {
        try {
            return origin.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public float getMin() {
        return min;
    }

    public int getMinInt() {
        if (min < 0) {
            return (int)(min - 1f);
        }
        return (int)min;
    }

    public float getMax() {
        return max;
    }

    public int getMaxInt() {
        return (int)(max + 1);
    }

    public BaboData getLastMonth(int index) {
        if (isFirstMonthOfYear(index)) {
            int year = origin.get(index).getDate() / 10000;
            return getFirstDayOfYear(year);
        }
        return getPrev(index, 100);
    }

    public BaboData getLastYear(int index) {
        return getPrev(index, 10000);
    }

    public boolean isFirstDayOfYear(int index) {
        return index == 0 ||
                origin.get(index).getDate() / 10000 != origin.get(index - 1).getDate() / 10000;
    }

    public boolean isFirstMonthOfYear(int index) {
        return index == 0 || origin.get(index).getDate() / 100 % 100 == 1;
    }

    private BaboData getPrev(int index, int dev) {
        int current = origin.get(index).getDate() / dev;
        BaboData prev = origin.get(index);
        while (0 <= --index) {
            prev = origin.get(index);
            if (current != prev.getDate() / dev) break;
        }
        return prev;
    }

    public long getBaseOfYear(int year) {
        BaboData firstOfYear = getFirstDayOfYear(year);
        return firstOfYear.getCurrent();
    }

    public BaboData getFirstDayOfYear(int year) {
        int i = origin.size() - 1;
        while (0 <= i) {
            BaboData data = origin.get(i);
            if (data.getDate() / 10000 < year) {
                return origin.get(i+1);
            }
            i--;
        }
        return origin.get(0);
    }

}
