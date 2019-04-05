package com.example.WeatherApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, ArrayList<String> items) {
        super(context,R.layout.custom_layout, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View CustomView = inflater.inflate(R.layout.custom_layout,parent,false);
        String singleItem = getItem(position);
        TextView itemText = CustomView.findViewById(R.id.list_value);
        ImageView imgView = CustomView.findViewById(R.id.imageView);
        itemText.setText(singleItem);
        imgView.setImageResource(R.drawable.esp);


        return CustomView;
    }
}