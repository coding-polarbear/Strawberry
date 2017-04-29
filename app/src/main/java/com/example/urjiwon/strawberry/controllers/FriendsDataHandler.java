package com.example.urjiwon.strawberry.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.urjiwon.strawberry.adapters.ABEntry;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.example.urjiwon.strawberry.models.RecyclerData;
import com.example.urjiwon.strawberry.models.TimeLineData;
import com.example.urjiwon.strawberry.view.CommentActivity;
import com.example.urjiwon.strawberry.view.fragments.Tab4Fragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.urjiwon.strawberry.view.fragments.Tab2Fragment.UUIDs;
import static com.example.urjiwon.strawberry.view.fragments.Tab2Fragment.abAdapter;
import static com.example.urjiwon.strawberry.view.fragments.Tab2Fragment.abList;
import static com.example.urjiwon.strawberry.view.fragments.Tab2Fragment.temp;
import static com.example.urjiwon.strawberry.view.fragments.Tab4Fragment.mAdapter;
import static com.example.urjiwon.strawberry.view.fragments.Tab4Fragment.myDataset;

/**
 * Created by baehy on 2017-02-12.
 */

public class FriendsDataHandler {
    private int cnt;
    private ProgressDialog progressDialog;
    FriendsData friendsData = FriendsData.getInstance();
    public void setting(Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("데이터 업데이트중 ...");
        progressDialog.show();

        friendsData.getmDatabase().child("userList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    friendsData.getUserList_datas().add(postSnapshot.getKey());
                    Log.d("sttr key=" , postSnapshot.getKey() +"  , value ="+ postSnapshot.getValue().toString());
                    friendsData.getUserHash().put(postSnapshot.getKey() , postSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        friendsData.getmDatabase().child("userList2").child(Auth.getSingleAuth().userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    friendsData.getUserList2_uuid().add(postSnapshot.getKey());
                    Log.d("id str =",postSnapshot.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        friendsData.getmDatabase().child("userList2").child(Auth.getSingleAuth().userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    friendsData.getUserList2_name().add(postSnapshot.getValue(String.class));
                    Log.d("name str =", postSnapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        friendsData.getmDatabase().child("userList2").child(Auth.getSingleAuth().userName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String viewName = (String) dataSnapshot.getValue();
                cnt = 0;
                for (String t : temp) {
                    if (t.equals(viewName)) {
                        cnt++;
                    }
                }
                if (cnt == 0) {
                    UUIDs.add(dataSnapshot.getKey());
                    Log.d("viewName", viewName);


                    String a= friendsData.getUserHash().get(viewName).toString();
                    ABEntry ne = new ABEntry(viewName , null, Uri.parse(a));
                    abList.add(ne);
                    temp.add(viewName);
                    abAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        friendsData.getmDatabase().child("timeline").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CommentActivity.timeline_id.add(dataSnapshot.getKey());
                final TimeLineData timeLineData = dataSnapshot.getValue(TimeLineData.class);
                friendsData.getmDatabase().child("userList").child(timeLineData.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String a = dataSnapshot.getValue().toString();
                        myDataset.add(new RecyclerData(timeLineData.getUserName(), Uri.parse(a) , timeLineData.getTime() , timeLineData.getMessge(), timeLineData.getComment_count(), timeLineData.getUp() , timeLineData.getDown()));
                        Tab4Fragment.mLayoutManager.scrollToPosition(Tab4Fragment.count_timeline);
                        Tab4Fragment.count_timeline++;
                        mAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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

        friendsData.getmDatabase().child("condition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d("getkey", postSnapshot.getKey());
                    Log.d("getValue", postSnapshot.getValue().toString());
                    friendsData.getMessage().put(postSnapshot.getKey().toString(), postSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}