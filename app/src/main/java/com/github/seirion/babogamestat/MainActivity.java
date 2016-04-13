package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.seirion.babogamestat.io.Loader;
import com.github.seirion.babogamestat.model.BaboData;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        BehaviorSubject<List<BaboData>> networkDataArrived = BehaviorSubject.create();
        Observable<List<BaboData>> localLoader = loadFromLocal().takeUntil(networkDataArrived);
        Observable<List<BaboData>> networkLoader = loadFromNetwork().doOnNext(networkDataArrived::onNext);

        localLoader.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                });
        networkLoader.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                });
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
        TextView dateView = (TextView)findViewById(R.id.date);
        Calendar date = Calendar.getInstance();
        dateView.setText(String.format("%d.%d.%d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)
            ));
    }

    public void onRetry(View view) {

    }
}
