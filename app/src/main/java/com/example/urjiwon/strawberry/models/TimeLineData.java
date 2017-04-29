package com.example.urjiwon.strawberry.models;

/**
 * Created by verak on 2017-02-23.
 */

public class TimeLineData  {
    private String userName;
    private String messge;
    private String time;
    private int comment_count;
    private int up;
    private int down;

    public TimeLineData()
    {
    }

    public TimeLineData(String n, String m ,String t, int c , int u , int d)
    {
        this.userName=n;
        this.messge=m;
        this.time=t;
        this.comment_count=c;
        this.up=u;
        this.down=d;
    }

    public String getUserName()
    {
        return userName;
    }
    public String getMessge() {return  messge;}
    public String getTime(){return  time;}
    public int getComment_count(){return  comment_count;}
    public int getUp(){return up;}
    public int getDown(){return down;}
}
