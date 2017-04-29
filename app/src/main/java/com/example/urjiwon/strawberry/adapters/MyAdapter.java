package com.example.urjiwon.strawberry.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.urjiwon.strawberry.models.RecyclerData;
import com.example.urjiwon.strawberry.view.CommentActivity;
import com.example.urjiwon.strawberry.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by baehy on 2017-02-21.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<RecyclerData> mDataset;
    public Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_name, textView_message, textView_time, comment_cnt, up_view , down_view;
        public ImageView ImageiconView, up_btn, down_btn;
        private ImageButton comment;
        public ViewHolder(View view) {
            super(view);
            ImageiconView = (ImageView)view.findViewById(R.id.Imageicon);
            textView_name = (TextView)view.findViewById(R.id.setname);
            textView_message= (TextView)view.findViewById(R.id.setmessage);
            textView_time = (TextView)view.findViewById(R.id.settime);
            comment = (ImageButton)view.findViewById(R.id.comment);
            comment_cnt= (TextView) view.findViewById(R.id.comment_cnt);
            up_btn = (ImageButton) view.findViewById(R.id.up_btn);
            down_btn = (ImageButton) view.findViewById(R.id.down_btn);
            up_view =(TextView)view.findViewById(R.id.up_view);
            down_view = (TextView)view.findViewById(R.id.down_view);
        }
    }

    public MyAdapter(ArrayList<RecyclerData> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_view2, parent, false);
        this.context=parent.getContext();

     //  ImageView ImageiconView= (ImageView)v.findViewById(R.id.Imageicon);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView_name.setText(mDataset.get(position).name);
        holder.textView_time.setText(mDataset.get(position).time);
        holder.textView_message.setText(mDataset.get(position).message);
        holder.up_view.setText(Integer.toString(mDataset.get(position).up));
        holder.down_view.setText(Integer.toString(mDataset.get(position).down));
        Glide.with(context).load(mDataset.get(position).uri).bitmapTransform(new CropCircleTransformation(context)).into(holder.ImageiconView);
        holder.comment_cnt.setText(Integer.toString(mDataset.get(position).comment_count));
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.myDataset2.clear();
                CommentActivity.myDataset2.add(new RecyclerData(mDataset.get(position).name,mDataset.get(position).uri, mDataset.get(position).time, mDataset.get(position).message, mDataset.get(position).comment_count , mDataset.get(position).up, mDataset.get(position).down));
                CommentActivity.comment_position=position;
                Intent i = new Intent(context , CommentActivity.class);
                context.startActivity(i);
            }
        });

        holder.up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CommentActivity.up(position);
            }
        });

        holder.down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CommentActivity.down(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}