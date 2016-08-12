package com.github.seirion.babogamestat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.seirion.babogamestat.chart.ChartSetter;
import com.github.seirion.babogamestat.model.BaboData;

import java.util.Locale;

public class DataListActivity extends Activity {
    private static final String TAG = DataListActivity.class.getSimpleName();

    private DataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new DataListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            int index = DataSet.instance().size() - position - 1;
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra(ChartSetter.KEY_INDEX, index);
            startActivity(intent);
        });
    }

    private class DataListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private DataSet dataSet;

        public DataListAdapter() {
            inflater = DataListActivity.this.getLayoutInflater();
            dataSet = DataSet.instance();
        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int index = dataSet.size() - position - 1;
            int prevIndex = index - 1;

            View v = convertView;
            if (v == null) {
                v = inflater.inflate(R.layout.list_data, null);
                v.setTag("DATA_LIST_VIEW");
            }
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView inout = (TextView) v.findViewById(R.id.inout);
            TextView balance = (TextView) v.findViewById(R.id.balance);

            BaboData data = dataSet.get(index);
            int year = data.getDate() / 10000;
            int month = data.getDate() % 10000 / 100;
            int day = data.getDate() % 100;
            date.setText(String.format(Locale.getDefault(), "%02d.%02d.%02d", year, month, day));

            if (0 <= prevIndex) {
                BaboData prev = dataSet.get(prevIndex);
                inout.setText(String.format(Locale.getDefault(), "%+,d", data.getCurrent() - prev.getCurrent()));
            } else {
                inout.setText("");
            }
            balance.setText(String.format(Locale.getDefault(), "%,d", data.getCurrent()));
            return v;
        }
    }
}
