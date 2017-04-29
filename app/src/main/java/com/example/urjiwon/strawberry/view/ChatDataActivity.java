package com.example.urjiwon.strawberry.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.adapters.ABEntry;
import com.example.urjiwon.strawberry.adapters.CustomAdapter;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.ChatData;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatDataActivity extends AppCompatActivity {
    private ArrayAdapter<ABEntry> abAdapter;
    private ArrayList<ABEntry> abList ;

    private EditText editText;
    private ListView dataList;
    private ActionBar actionBar;
    private String uuid;
    private Button button;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras == null) return;

        actionBar = getSupportActionBar();
        abList= new ArrayList<ABEntry>();
        uuid = extras.getString("UUID");
        userName = extras.getString("username");
        actionBar.setTitle(userName);
        Log.d("UUID", uuid);
        setContentView(R.layout.activity_chat_data);
        editText = (EditText) findViewById(R.id.editText99);
        dataList = (ListView) findViewById(R.id.dataList);
        button= (Button)findViewById(R.id.send_button);


        FriendsData.getInstance().getmDatabase().child("chatList").child(uuid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                if(!(chatData.getMessge().equals("")))
                {
                    String a= FriendsData.getInstance().getUserHash().get(chatData.getUserName()).toString();
                    ABEntry ne = new ABEntry(chatData.getUserName() , chatData.getMessge() , chatData.getTime() , Uri.parse(a));
                    abList.add(ne);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(editText.getText().toString().equals("")))
                {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String formatDate = sdfNow.format(date);

                    FriendsData.getInstance().getmDatabase().child("news").child("chat").child(uuid).setValue(Auth.getSingleAuth().userName);
                    FriendsData.getInstance().getmDatabase().child("chatList").child(uuid).push().setValue(new ChatData(Auth.getSingleAuth().userName, editText.getText().toString(), formatDate));
                    editText.setText("");
                }

            }
        });
       abAdapter = new CustomAdapter(getApplicationContext(), R.layout.entry, R.id.eName, abList, null);
        dataList.setAdapter(abAdapter);


        FriendsData.getInstance().getmDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //리스트뷰 자동 스크롤
                dataList.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        dataList.setSelection(abAdapter.getCount() - 1);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}