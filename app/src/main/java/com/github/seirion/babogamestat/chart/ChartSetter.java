package com.github.seirion.babogamestat.chart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;

import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.github.seirion.babogamestat.DataSet;
import com.github.seirion.babogamestat.ReportActivity;

import java.text.DecimalFormat;

public class ChartSetter {
    private static ChartSetter INSTANCE = new ChartSetter();
    public static String KEY_INDEX = "KEY_INDEX";

    public static ChartSetter instance() {
        return INSTANCE;
    }

    public void init(ChartView chart) {
        chart.setBackgroundColor(0x99EEEEFF);
        chart.setXAxis(true)
                .setYAxis(true)
                .setXLabels(AxisController.LabelPosition.OUTSIDE)
                .setYLabels(AxisController.LabelPosition.OUTSIDE)
                .setValueThreshold(0, 0, new Paint())
                .setLabelsFormat(new DecimalFormat("0.##"))
                .setStep(1);
    }

    public void initLineChart(LineChartView chart) {
        chart.setClickablePointRadius(50);
    }

    public void initEvent(Context context, ChartView chart) {
        chart.setOnEntryClickListener((setIndex, entryIndex, entryRect) -> {
            Intent intent = new Intent(context, ReportActivity.class);
            intent.putExtra(KEY_INDEX, entryIndex);
            context.startActivity(intent);
        });

        chart.setOnClickListener(v -> {
        });
    }

    private ChartSetter() {}

    public void setValueRange(LineChartView chart, DataSet dataSet) {
        int min = dataSet.getMinInt();
        int max = dataSet.getMaxInt();

        int rows = max - min;
        while (rows < 20) rows <<= 1;
        while (rows > 40) rows >>= 1;

        int cols = dataSet.size() -1;

        chart.setAxisBorderValues(min, max, 1)
                .setGrid(ChartView.GridType.FULL, rows, cols, new Paint());
    }
}
