package com.example.urjiwon.strawberry.models;

import android.net.Uri;

/**
 * Created by baehy on 2017-02-21.
 */

public class RecyclerData{
    public String name;
    public Uri uri;
    public String time;
    public String message;
    public int comment_count;
    public int up;
    public int down;
    public RecyclerData(String name, Uri uri , String time , String message, int comment_count , int up , int down){
        this.name = name;
        this.uri = uri;
        this.time=time;
        this.message = message;
        this.comment_count= comment_count;
        this.up= up;
        this.down= down;
    }
}