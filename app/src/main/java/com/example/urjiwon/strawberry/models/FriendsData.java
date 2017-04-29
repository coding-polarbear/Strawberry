package com.example.urjiwon.strawberry.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by baehy on 2017-03-04.
 */

public class FriendsData {
    private ArrayList<String> userList_datas = new ArrayList<>();
    private ArrayList<String> userList2_name = new ArrayList<>();
    private ArrayList<String> userList2_uuid = new ArrayList<>();
    private HashMap<String, Object> userHash = new HashMap<>();
    private HashMap<String, Object> message = new HashMap<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static FriendsData friendsData = new FriendsData();

    private FriendsData(){}

    public static FriendsData getInstance() {
        return friendsData;
    }

    public void setUserHash(HashMap<String,Object> userHash) {
        this.userHash = userHash;
    }

    public HashMap<String, Object> getUserHash() {
        return userHash;
    }

    public void setUserList_datas(ArrayList<String> userList_datas) { //set UserList_datas;
        this.userList_datas = userList_datas;
    }

    public ArrayList<String> getUserList_datas() {      //get UserList_datas
        return userList_datas;
    }

    public void setUserList2_name(ArrayList<String> userList2_name) {   //set UserList2_name
        this.userList2_name = userList2_name;
    }

    public ArrayList<String> getUserList2_name() {      //get UserList2_name
        return userList2_name;
    }

    public void setUserList2_uuid(ArrayList<String> userList2_uuid) {   //set UserList2_uuid
        this.userList2_uuid = userList2_uuid;
    }

    public ArrayList<String> getUserList2_uuid() {          //get UserList2_uuid
        return userList2_uuid;
    }


    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void setMessage(HashMap message) {
        this.message = message;
    }

    public HashMap getMessage() {
        return this.message;
    }


}
