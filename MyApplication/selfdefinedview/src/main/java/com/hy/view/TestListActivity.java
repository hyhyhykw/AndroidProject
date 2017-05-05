package com.hy.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestListActivity extends Activity {
    AlertDialog.Builder builder;
    String[] cities = {"西安", "安康", "汉中", "咸阳", "延安", "西安", "安康", "汉中", "咸阳", "延安", "西安", "安康", "汉中", "咸阳", "延安",
            "西安", "安康", "汉中", "咸阳", "延安", "西安", "安康", "汉中", "咸阳", "延安", "西安", "安康", "汉中", "咸阳", "延安", "西安", "安康", "汉中",
            "咸阳", "延安", "西安", "安康", "汉中", "咸阳", "延安"};
    ListView mLst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testlistview);
        mLst = (ListView) findViewById(R.id.lst);
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("windows");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("cancel", null);

        CityAdapter adapter = new CityAdapter();
        mLst.setAdapter(adapter);
        mLst.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) mLst.getItemAtPosition(position);
                builder.setMessage(str);
                builder.show();
                return false;
            }
        });
    }

    class ViewHolder {
        TextView textContent;
    }


    class CityAdapter extends BaseAdapter {
        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(TestListActivity.this);
                convertView = inflater.inflate(R.layout.test_list_item, null);
                holder = new ViewHolder();
                holder.textContent = (TextView) convertView.findViewById(R.id.txt_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textContent.setText(cities[position]);
            holder.textContent.setTextSize(30);
            return convertView;
        }

        @Override
        public int getCount() {
            return cities.length;
        }

        @Override
        public Object getItem(int position) {
            return cities[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}
