package com.example.urjiwon.strawberry.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.adapters.ABEntry;
import com.example.urjiwon.strawberry.adapters.CustomAdapter;
import com.example.urjiwon.strawberry.adapters.MyAdapter;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.ChatData;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.example.urjiwon.strawberry.models.RecyclerData;
import com.example.urjiwon.strawberry.view.fragments.Tab4Fragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.urjiwon.strawberry.view.fragments.Tab4Fragment.myDataset;

public class CommentActivity extends AppCompatActivity {
    public static ArrayList<RecyclerData> myDataset2= new ArrayList<RecyclerData>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter2;
    private LinearLayoutManager mLayoutManager;
    public static int comment_position;
    public static int comment_count;
    public static ArrayList<String> timeline_id= new ArrayList<String>();
    private ArrayAdapter<ABEntry> abAdapter;
    private ArrayList<ABEntry> abList ;
    private EditText editText;
    private ListView comment_List;
    private Button button;
    public static ArrayList<Integer> up_count= new ArrayList<>();
    public static ArrayList<Integer> down_count= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        button= (Button)findViewById(R.id.comment_btn);
        editText = (EditText)findViewById(R.id.comment_edit);
        abList= new ArrayList<ABEntry>();
        comment_List = (ListView) findViewById(R.id.comment_list);

        abAdapter = new CustomAdapter(getApplicationContext(), R.layout.entry, R.id.eName, abList, null);
        comment_List.setAdapter(abAdapter);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter2 = new MyAdapter(myDataset2);
        mRecyclerView.setAdapter(mAdapter2);



        FriendsData.getInstance().getmDatabase().child("comment").child(timeline_id.get(comment_position)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                if(!(chatData.getMessge().equals("")))
                {
                    String a= FriendsData.getInstance().getUserHash().get(chatData.getUserName()).toString();
                    ABEntry ne = new ABEntry(chatData.getUserName() , chatData.getMessge() , chatData.getTime() , Uri.parse(a) );
                    abList.add(ne);
                    abAdapter.notifyDataSetChanged();
                    comment_List.post(new Runnable() {
                        @Override
                        public void run() {
                            // Select the last row so it will scroll into view...
                            comment_List.setSelection(abAdapter.getCount() - 1);
                        }
                    });
                }
                mAdapter2.notifyDataSetChanged();
                Tab4Fragment.mAdapter.notifyDataSetChanged();
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
               long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formatDate = sdfNow.format(date);
            if(!(editText.getText().toString().equals("")))
            {
                FriendsData.getInstance().getmDatabase().child("comment").child(timeline_id.get(comment_position))
                        .push().setValue(new ChatData(Auth.getSingleAuth().userName, editText.getText().toString(), formatDate));
                comment_count=++(myDataset2.get(0).comment_count);
                FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(comment_position))
                        .child("comment_count").setValue(comment_count);
                editText.setText("");

                myDataset.set(comment_position, new RecyclerData(myDataset2.get(0).name , myDataset2.get(0).uri , myDataset2.get(0).time,myDataset2.get(0).message , myDataset2.get(0).comment_count , myDataset2.get(0).up , myDataset2.get(0).down));
            }
            }
        });


    }

    public static void up(final int position){
        FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(position)).child("up").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(up_count.get(position)==1)
                {
                    myDataset.set(position, new RecyclerData(myDataset.get(position).name , myDataset.get(position).uri , myDataset.get(position).time,myDataset.get(position).message , myDataset.get(position).comment_count , ++myDataset.get(position).up , myDataset.get(position).down));
                    FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(position))
                            .child("up").setValue(myDataset.get(position).up);
                    Tab4Fragment.mAdapter.notifyDataSetChanged();
                }
                else
                {
                    myDataset.set(position, new RecyclerData(myDataset.get(position).name , myDataset.get(position).uri , myDataset.get(position).time,myDataset.get(position).message , myDataset.get(position).comment_count , --myDataset.get(position).up , myDataset.get(position).down));
                    FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(position))
                            .child("up").setValue(myDataset.get(position).up);
                    Tab4Fragment.mAdapter.notifyDataSetChanged();
                }
                up_count.set(position, (up_count.get(position)*-1));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void down(final int position){
        FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(position)).child("down").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(down_count.get(position)==1)
                {
                    myDataset.set(position, new RecyclerData(myDataset.get(position).name , myDataset.get(position).uri , myDataset.get(position).time,myDataset.get(position).message , myDataset.get(position).comment_count ,  myDataset.get(position).up , ++myDataset.get(position).down));
                    FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(position))
                            .child("down").setValue(myDataset.get(position).down);
                    Tab4Fragment.mAdapter.notifyDataSetChanged();
                }
                else
                {
                    myDataset.set(position, new RecyclerData(myDataset.get(position).name , myDataset.get(position).uri , myDataset.get(position).time,myDataset.get(position).message , myDataset.get(position).comment_count , myDataset.get(position).up , --myDataset.get(position).down));
                    FriendsData.getInstance().getmDatabase().child("timeline").child(timeline_id.get(position))
                            .child("down").setValue(myDataset.get(position).down);
                    Tab4Fragment.mAdapter.notifyDataSetChanged();
                }
                down_count.set(position, (down_count.get(position)*-1));
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
