package com.github.seirion.babogamestat;

import com.db.chart.model.LineSet;
import com.github.seirion.babogamestat.model.BaboData;

import java.util.List;

public class DataSet {
    List<BaboData> origin;

    public DataSet(List<BaboData> origin) {
        this.origin = origin;
    }

    public LineSet getLineSet() {
        LineSet lineSet = new LineSet();

        for (BaboData org : origin) {
            int date = org.getDate() % 10000;
            String label = String.format("%02d.%02d", date / 100, date % 100);
            float value = (org.getCurrent() - org.getBase()) * 100 / (float)org.getBase();
            lineSet.addPoint(label, value);
        }

        lineSet.setSmooth(true)
                .setDotsColor(0xFFDD1010)
                .setDotsRadius(8)
                .setThickness(4)
                .setDashed(new float[] {4, 4});

        return lineSet;
    }
}
