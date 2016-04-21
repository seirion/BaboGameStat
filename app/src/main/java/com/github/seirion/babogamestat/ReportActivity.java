package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.seirion.babogamestat.chart.ChartSetter;
import com.github.seirion.babogamestat.model.BaboData;

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
}
