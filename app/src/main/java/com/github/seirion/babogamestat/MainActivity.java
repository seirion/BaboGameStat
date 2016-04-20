package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.db.chart.view.LineChartView;
import com.github.seirion.babogamestat.chart.ChartSetter;
import com.github.seirion.babogamestat.io.Loader;
import com.github.seirion.babogamestat.model.BaboData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    LineChartView chart;
    DataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChartView) findViewById(R.id.chart);

        initRealm();
        initUI();
        loadData();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(config);
    }

    private void initUI() {
        setDate();
    }

    private void loadData() {
        loadFromLocal().filter(r -> !r.isEmpty())
                .subscribe(results -> ready(results),
                        e -> Log.e(TAG, "exception : " + e));
    }

    private void ready(List<BaboData> data) {
        initData(data);
        initSelector(data);
        initChart();
        chart.show();
    }

    private void initSelector(List<BaboData> results) {
        Set<Integer> years = new TreeSet<>();
        for (BaboData data : results) {
            years.add(data.getDate() / 10000);
        }

        Spinner s = (Spinner) findViewById(R.id.selector);
        ArrayList<Integer> arrayList = new ArrayList<>(years);
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(spinnerArrayAdapter);
    }

    private void initData(List<BaboData> results) {
        dataSet = new DataSet(results);
    }

    private void initChart() {
        ChartSetter.instance().init(chart);
        ChartSetter.instance().initLineChart(chart);
        ChartSetter.instance().initEvent(this, chart);
        chart.addData(dataSet.getLineSet());
        ChartSetter.instance().setValueRange(chart, dataSet);
    }

    private Observable<List<BaboData>> loadFromLocal() {
        return Observable.create(subscriber -> {
            List<BaboData> result = Loader.instance().loadFromLocal();
            subscriber.onNext(result);
            subscriber.onCompleted();
        });
    }

    private Observable<List<BaboData>> loadFromNetwork() {
        return Observable.create(subscriber -> {
            List<BaboData> result = Loader.instance().loadFromNetwork();
            subscriber.onNext(result);
            subscriber.onCompleted();
        });
    }

    private void setDate() {
        TextView dateView = (TextView) findViewById(R.id.date);
        Calendar date = Calendar.getInstance();
        dateView.setText(String.format(Locale.KOREAN, "%d.%d.%d",
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
        ));

        dateView.setOnClickListener(v -> {
            reload();
        });
    }

    public void reload() {
        loadFromNetwork().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    chart.getData().clear();
                    ready(results);
                    BaboData.save(results);
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, Menu.NONE, "Reload").setIcon(android.R.drawable.ic_menu_rotate);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                reload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
