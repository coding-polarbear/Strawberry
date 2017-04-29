package com.example.urjiwon.strawberry.adapters;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.urjiwon.strawberry.R;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by verak on 2017-02-15.
 */

public class CustomAdapter extends ArrayAdapter<com.example.urjiwon.strawberry.adapters.ABEntry> {
    private ArrayList<com.example.urjiwon.strawberry.adapters.ABEntry> items;
    public HashMap<String, String> message;
    private Context context;
    private int rsrc;

    public CustomAdapter(Context context, int rsrc, int textId, ArrayList<com.example.urjiwon.strawberry.adapters.ABEntry> data , HashMap<String, String> message) {
        super(context, rsrc ,textId, data);
        this.context=context;
        this.rsrc=rsrc;
        this.items=data;
        this.message=message;
    }
    public CustomAdapter(Context context, int rsrc, int textId, ArrayList<com.example.urjiwon.strawberry.adapters.ABEntry> data) {
        super(context, rsrc ,textId, data);
        this.context=context;
        this.rsrc=rsrc;
        this.items=data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater li=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= li.inflate(rsrc, null);
        }
            com.example.urjiwon.strawberry.adapters.ABEntry e = items.get(position);

        if (e.getTimeE() != null) {
            ((TextView) v.findViewById(R.id.times)).setText(e.getTimeE());
        }
        ((TextView) v.findViewById(R.id.eName)).setText(e.getNameE());
        if(v.findViewById(R.id.eMessage) != null && e.getMessage() != null) {
            ((TextView) v.findViewById(R.id.eMessage)).setText(e.getMessage());
        } else if (v.findViewById(R.id.eMessage) != null && message != null) {
            ((TextView) v.findViewById(R.id.eMessage)).setText(message.get(e.getNameE()));
        }

        Glide.with(getContext()).load(e.getPhotoIdE()).bitmapTransform(new CropCircleTransformation(getContext())).into(((ImageView) v.findViewById(R.id.ePhoto)));
        return v;
    }



}

