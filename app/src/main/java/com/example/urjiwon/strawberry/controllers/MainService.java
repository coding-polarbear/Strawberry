package com.example.urjiwon.strawberry.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import static com.example.urjiwon.strawberry.view.fragments.Tab2Fragment.UUIDs;

/**
 * Created by verak on 2017-03-26.
 */

public class MainService extends Service {
    public static int notifi_cnt=0;
    public static String Last_message;
    @Override
    public void onCreate() {



        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {

        FriendsData.getInstance().getmDatabase().child("news").child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (String a : UUIDs)
                {
                    if (dataSnapshot.getKey().equals(a))
                    {
                        if (!(dataSnapshot.getValue().toString().equals(Auth.getSingleAuth().userName)))
                        {
                            Toast.makeText(getApplicationContext(), "새로운 메세지가 있습니다." , Toast.LENGTH_SHORT).show();
                            NotificationManager Notifi_m;
                            Notification Notifi;

                            Notifi_m = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            Notifi= new Notification.Builder(getApplicationContext())
                                    .setContentTitle("톡 왔어용!").setContentText(dataSnapshot.getValue().toString() +"님이 새로운 메세지가 있습니다.").setSmallIcon(R.drawable.icons)
                                    .setTicker("알림!~!!").build();
                            Notifi.defaults= android.app.Notification.DEFAULT_SOUND;
                            Notifi.flags= android.app.Notification.FLAG_ONLY_ALERT_ONCE;
                            Notifi.flags= android.app.Notification.FLAG_AUTO_CANCEL;

                            Notifi_m.notify(777, Notifi);
                        }
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
