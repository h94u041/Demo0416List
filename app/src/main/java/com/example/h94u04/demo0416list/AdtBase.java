package com.example.h94u04.demo0416list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdtBase extends BaseAdapter {
    LayoutInflater inflat;
    ArrayList<DATA> DataArray;
    Context context;

    AdtBase( Context context , ArrayList<DATA> DataArray) {

        this.DataArray = DataArray;
        this.context = context;
        this.inflat = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return DataArray.size();
    }

    @Override
    public Object getItem(int position) {
        return DataArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            convertView = inflat.inflate(R.layout.fralist, null);

            DataArray.get(position).tv01 = (TextView) convertView.findViewById(R.id.TV01);

            DataArray.get(position).tv02 = (TextView) convertView.findViewById(R.id.TV02);

            DataArray.get(position).tv03 = (TextView) convertView.findViewById(R.id.TV03);

            DataArray.get(position).tv01.setText(DataArray.get(position).id);

            DataArray.get(position).tv02.setText(DataArray.get(position).item);

            DataArray.get(position).tv03.setText(DataArray.get(position).price);

            convertView.setTag(DataArray);
        //data.tv01.setText();
        return convertView;
    }
}
