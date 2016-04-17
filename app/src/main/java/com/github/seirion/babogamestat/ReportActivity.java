package com.github.seirion.babogamestat;

import android.app.Activity;
import android.os.Bundle;

public class ReportActivity extends Activity {
    private static final String TAG = ReportActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}
