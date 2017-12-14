package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by laurageerars on 14-12-17.
 */

public class FavorietList extends ArrayAdapter<Favoriet> {
    private Activity context;
    private List<Favoriet> favorietList;

    public FavorietList(Activity context, List<Favoriet> favorietList) {
        super(context, R.layout.list_layout, favorietList);
        this.context = context;
        this.favorietList = favorietList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewTitle = (TextView) listviewitem.findViewById(R.id.textViewTitle);

        Favoriet favoriet = favorietList.get(position);
        textViewTitle.setText(favoriet.getTitle());

        return listviewitem;
    }
}
