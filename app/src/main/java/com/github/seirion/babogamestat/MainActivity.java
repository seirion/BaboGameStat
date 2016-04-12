package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

import rx.Observable;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        load();
    }

    private void initUI() {
        setDate();
    }

    private void load() {
        Observable ob;
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
}
