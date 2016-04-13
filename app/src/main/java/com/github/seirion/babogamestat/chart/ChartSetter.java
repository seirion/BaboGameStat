package com.github.seirion.babogamestat.chart;

import android.util.Log;

import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;

public class ChartSetter {
    private static ChartSetter INSTANCE = new ChartSetter();

    public static ChartSetter instance() {
        return INSTANCE;
    }

    public void init(ChartView chart) {
        chart.setXAxis(true);
        chart.setYAxis(true);
        chart.setBackgroundColor(0xFFEEEEDD);
        //chart.setGrid(ChartView.GridType.FULL, paint);

        chart.setXLabels(AxisController.LabelPosition.INSIDE)
                .setYLabels(AxisController.LabelPosition.INSIDE)
                .setAxisBorderValues(-4, 4, 1)
                .setStep(1);
    }

    public void initLineChart(LineChartView chart) {
        chart.setClickablePointRadius(20);
    }

    public void initEvent(ChartView chart) {
        chart.setOnEntryClickListener((setIndex, entryIndex, entryRect) -> {
        });
    }
    private ChartSetter() {}
}
