package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.seirion.babogamestat.chart.ChartSetter;
import com.github.seirion.babogamestat.model.BaboData;

import java.text.DecimalFormat;
import java.util.Locale;

public class ReportActivity extends Activity {
    private static final String TAG = ReportActivity.class.getSimpleName();
    final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        if (savedInstanceState != null) {
            loadFromBundle(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            loadFromBundle(getIntent().getExtras());
        }
    }

    private void loadFromBundle(Bundle bundle) {
        index = bundle.getInt(ChartSetter.KEY_INDEX, -1);
        if (index == -1) return;

        BaboData data = DataSet.instance().get(index);
        if (data == null) return;
        setDate(data);
        setReport(data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ChartSetter.KEY_INDEX, index);
        super.onSaveInstanceState(outState);
    }

    private void setDate(BaboData data) {
        TextView dateView = (TextView) findViewById(R.id.date);
        int year = data.getDate() / 10000;
        int month = data.getDate() % 10000 / 100;
        int day = data.getDate() % 100;
        dateView.setText(String.format(Locale.getDefault(), "%d.%d.%d", year, month, day));
    }

    private void setReport(BaboData data) {
        TextView reportView = (TextView) findViewById(R.id.reportText);

        StringBuilder sb = new StringBuilder();
        long current = data.getCurrent();

        sb.append("누적 : " + getCalculated(current, data.getBase()) + "\n");

        if (index != 0) {
            BaboData prev = DataSet.instance().get(index - 1);
            sb.append("당일 : " + getCalculated(current, prev.getCurrent()) + "\n");

            BaboData lastMonth = DataSet.instance().getLastMonth(index);
            sb.append("당월 : " + getCalculated(current, lastMonth.getCurrent()) + "\n");

            BaboData lastYear = DataSet.instance().getLastYear(index);
            sb.append("당해 : " + getCalculated(current, lastYear.getCurrent()) + "\n");
        }

        reportView.setText(sb.toString());
    }

    private String getCalculated(long current, long base) {
        if (base == 0) return "";
        long profit = current - base;
        float rateOfProfit = ((float) profit) * 100 / base;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        return String.format(Locale.getDefault(), "%+,d (%+.2f%%)", profit, rateOfProfit);
    }
}
