package com.example.lawnicsdemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ListAdapter extends ArrayAdapter<Data> {
    private Activity context;
    private List<Data> artistList;

    public ListAdapter(  Activity context, List<Data> artistList) {
        super(context,R.layout.list_activity,artistList );
        this.context = context;
        this.artistList = artistList;
    }

     public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         LayoutInflater inflater= context.getLayoutInflater();
         View viewList= inflater.inflate(R.layout.list_activity,null,true);
         TextView name= viewList.findViewById(R.id.textnameName);
         TextView type= viewList.findViewById(R.id.textnametype);
         TextView Genre= viewList.findViewById(R.id.textnameGenre);
         Data data= artistList.get(position);
         name.setText(data.getName());
         type.setText(data.getType());
         Genre.setText(data.getGenre());
         return viewList;
    }
}
