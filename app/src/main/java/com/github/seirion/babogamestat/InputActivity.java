package com.github.seirion.babogamestat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.github.seirion.babogamestat.model.BaboData;

import java.util.Calendar;

public class InputActivity extends Activity {
    private static final String TAG = InputActivity.class.getSimpleName();

    private int prevDate = 0;
    private int date = 0;
    private long value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        loadData();

        EditText dateEdit = (EditText) findViewById(R.id.dateEdit);
        dateEdit.setText(String.valueOf(date));

        EditText valueEdit = (EditText) findViewById(R.id.valueEdit);
        valueEdit.setText(String.valueOf(value));
        valueEdit.requestFocus();

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> save());

        showKeyboard();
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void loadData() {
        int lastIndex = DataSet.instance().size() - 1;
        if (lastIndex != -1) {
            BaboData data = DataSet.instance().get(lastIndex);
            prevDate = data.getDate();
            value = data.getCurrent();
        }
        date = nextDay(prevDate);
    }

    int nextDay(int prev) {
        Calendar calendar = Calendar.getInstance();
        if (prev != 0) {
            int y = prev / 10000;
            int m = prev % 10000 / 100;
            int d = prev % 100;
            calendar.set(y, m-1, d);
        }

        do {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (isWeekEnd(calendar));

        return calendar.get(Calendar.YEAR) * 10000 +
                (calendar.get(Calendar.MONTH)+1) * 100 +
                calendar.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isWeekEnd(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    private void save() {

    }
}
