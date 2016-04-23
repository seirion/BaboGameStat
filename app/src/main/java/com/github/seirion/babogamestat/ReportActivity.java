package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.seirion.babogamestat.chart.ChartSetter;
import com.github.seirion.babogamestat.model.BaboData;

import java.text.DecimalFormat;

public class ReportActivity extends Activity {
    private static final String TAG = ReportActivity.class.getSimpleName();

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
        dateView.setText(String.format("%d.%d.%d", year, month, day));
    }

    private void setReport(BaboData data) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        TextView reportView = (TextView) findViewById(R.id.reportText);
        StringBuilder sb = new StringBuilder();
        float rateOfProfit = ((float)data.getCurrent() - data.getBase()) * 100 / data.getBase();
        sb.append("누적 수익률 : " + decimalFormat.format(rateOfProfit) + " %\n");

        if (index != 0) {
            BaboData prev = DataSet.instance().get(index - 1);
            float rate = ((float)data.getCurrent() - prev.getCurrent()) * 100 / prev.getCurrent();
            sb.append("당일 수익률 : " + decimalFormat.format(rate) + " %\n");
        }
        reportView.setText(sb.toString());
    }
}
