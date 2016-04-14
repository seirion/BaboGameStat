package com.github.seirion.babogamestat;

import com.db.chart.model.LineSet;
import com.github.seirion.babogamestat.model.BaboData;

import java.util.List;

public class DataSet {
    private List<BaboData> origin;
    private float min = 0f;
    private float max = 0f;

    public DataSet(List<BaboData> origin) {
        this.origin = origin;
    }

    public LineSet getLineSet() {
        LineSet lineSet = new LineSet();

        min = Float.MAX_VALUE;
        max = Float.MIN_VALUE;
        for (BaboData org : origin) {
            int date = org.getDate() % 10000;
            String label = String.format("%02d.%02d", date / 100, date % 100);
            float value = (org.getCurrent() - org.getBase()) * 100 / (float)org.getBase();
            lineSet.addPoint(label, value);

            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        lineSet.setSmooth(true)
                .setFill(0x330000FF)
                .setDotsColor(0x99DD1010)
                .setDotsRadius(8)
                .setThickness(4)
                .setDashed(new float[] {4, 4});

        return lineSet;
    }

    public int size() {
        return origin.size();
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
}
