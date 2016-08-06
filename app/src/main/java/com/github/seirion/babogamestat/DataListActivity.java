package com.github.seirion.babogamestat;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        load();
    }

    private void load() {

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

            View v = convertView;
            if (v == null) {
                v = inflater.inflate(R.layout.list_data, null);
                v.setTag("DATA_LIST_VIEW");
            }
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView inout = (TextView) v.findViewById(R.id.inout);
            TextView balence = (TextView) v.findViewById(R.id.balence);

            date.setText("1"); // test
            inout.setText("2");
            balence.setText("3");
            return v;
        }
    }
}
