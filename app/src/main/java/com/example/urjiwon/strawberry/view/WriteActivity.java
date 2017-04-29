package com.example.urjiwon.strawberry.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.example.urjiwon.strawberry.models.TimeLineData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    private ImageButton send;
    private ImageButton back;
    private EditText timeline_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


        send = (ImageButton)findViewById(R.id.btnSend);
        back = (ImageButton)findViewById(R.id.btnBack);
        timeline_edit = (EditText)findViewById(R.id.timeline_edit);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(timeline_edit.getText().toString().equals("")))
                {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String formatDate = sdfNow.format(date);
                    //   String timeLine_uuid=UUIDBuilder.getUUID();..
                    FriendsData.getInstance().getmDatabase().child("timeline").push()
                            .setValue(new TimeLineData(Auth.getSingleAuth().userName, timeline_edit.getText().toString(),formatDate, 0, 0, 0));
                   // Tab4Fragment.mLayoutManager.scrollToPosition(Tab4Fragment.count_timeline);
                }
               // CustomDialog customDialog = new CustomDialog(getApplicationContext());
               // customDialog.show();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
