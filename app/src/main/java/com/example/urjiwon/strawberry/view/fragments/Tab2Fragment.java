package com.example.urjiwon.strawberry.view.fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.adapters.ABEntry;
import com.example.urjiwon.strawberry.adapters.CustomAdapter;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.ChatData;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.example.urjiwon.strawberry.utils.UUIDBuilder;
import com.example.urjiwon.strawberry.view.ChatDataActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.sql.Types.NULL;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Tab2Fragment extends Fragment {
    public static ArrayAdapter<ABEntry> abAdapter;


    private int cnt = 0;

    private EditText dialogEdit;
    private ListView listView;
    private android.support.design.widget.FloatingActionButton button;
    private String UserName;
    public static ArrayList<String> temp = new ArrayList<String>();
    private String chatID;
    public static ArrayList<ABEntry> abList = new ArrayList<ABEntry>();
    public static LinkedList<String> UUIDs = new LinkedList<String>();
    public static HashMap<String, String> message=new HashMap<String, String>();
    private Map<String, Object> childUpdates;
    private Map<String, Object> childUpdates2;
    public Tab2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, null);
        listView = (ListView) view.findViewById(R.id.chat_list);
        button = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);
        abAdapter = new CustomAdapter(getActivity(), R.layout.entry2, R.id.eName, abList, FriendsData.getInstance().getMessage());
        listView.setAdapter(abAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoChat(id,abAdapter.getItem(position).getNameE());

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt = 0;
                dialogEdit = new EditText(getActivity());
                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark_Dialog));
                dialog.setTitle("친구 추가").setMessage("친구의 닉네임을 입력하세요").setView(dialogEdit).setPositiveButton("생성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserName = dialogEdit.getText().toString();
                        if (UserName.equals(NULL)) {
                            Toast.makeText(getActivity(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            UserName = dialogEdit.getText().toString();
                            for (String str : FriendsData.getInstance().getUserList_datas()) {
                                if (str.equals(UserName)) {
                                    cnt++;
                                }
                            }
                            if (cnt != 0) {
                                cnt = 0;
                                for (String str : FriendsData.getInstance().getUserList2_name()) {
                                    if (str.equals(UserName)) {
                                        cnt++;
                                    }
                                }

                                if (cnt == 0) {
                                    chatID = UUIDBuilder.getUUID();
                                    childUpdates = new HashMap<>();
                                    childUpdates.put(chatID, dialogEdit.getText().toString());
                                    FriendsData.getInstance().getmDatabase().child("chatList").child(chatID).push().setValue(new ChatData("", "","x"));
                                    FriendsData.getInstance().getmDatabase().child("userList2").child(Auth.getSingleAuth().userName).updateChildren(childUpdates);
                                    childUpdates2 = new HashMap<>();
                                    childUpdates2.put(chatID, Auth.getSingleAuth().userName);
                                    FriendsData.getInstance().getmDatabase().child("userList2").child(dialogEdit.getText().toString()).updateChildren(childUpdates2);
                                    Toast.makeText(getActivity(), "친구추가됨!", Toast.LENGTH_SHORT).show();
                                    FriendsData.getInstance().getmDatabase().child("news").child("chat").child(chatID).setValue("  ");
                                    abAdapter.notifyDataSetChanged();
                                } else {
                                    dialogEdit.setText("");
                                    Toast.makeText(getActivity(), "이미 친구추가가 되어있습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                dialogEdit.setText("");
                                Toast.makeText(getActivity(), "사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "취소!! ", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }
        });

        return view;
    }

    public void gotoChat(long id, String userName) {
        Intent i = new Intent(getActivity(), ChatDataActivity.class);
        int index = (int) id;
        //    Log.d("index", Integer.toString(index));
        i.putExtra("UUID", UUIDs.get(index));
        i.putExtra("username",userName );
        startActivity(i);
    }


}