package com.github.seirion.babogamestat.chart;

import android.graphics.Paint;
import android.view.View;

import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;

import java.text.DecimalFormat;

public class ChartSetter {
    private static ChartSetter INSTANCE = new ChartSetter();

    public static ChartSetter instance() {
        return INSTANCE;
    }

    public void init(ChartView chart) {
        chart.setBackgroundColor(0x99EEEEFF);
        chart.setXAxis(true)
                .setYAxis(true)
                .setXLabels(AxisController.LabelPosition.OUTSIDE)
                .setYLabels(AxisController.LabelPosition.OUTSIDE)
                .setAxisBorderValues(-2, 4, 1)
                .setGrid(ChartView.GridType.FULL, 6, 4, new Paint())
                .setValueThreshold(0, 0, new Paint())
                .setLabelsFormat(new DecimalFormat("0.##"))
                .setStep(1);
    }

    public void initLineChart(LineChartView chart) {
        chart.setClickablePointRadius(20);
    }

    public void initEvent(ChartView chart) {
        chart.setOnEntryClickListener((setIndex, entryIndex, entryRect) -> {
        });

        chart.setOnClickListener(v -> {
        });
    }
    private ChartSetter() {}
}
