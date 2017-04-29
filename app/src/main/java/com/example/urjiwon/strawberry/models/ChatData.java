package com.example.urjiwon.strawberry.models;

/**
 * Created by purplebeen on 2017-01-12.
 */

public class ChatData {
    private String userName;
    private String messge;
    private String time;

    public ChatData()
    {
    }

    public ChatData(String userName, String messge ,String time)
    {
        this.userName=userName;
        this.messge=messge;
        this.time=time;
    }

    public String getUserName()
    {
        return userName;
    }
    public String getMessge() {return  messge;}
    public String getTime(){return  time;}
}
