package com.example.urjiwon.strawberry.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.controllers.LoginHandler;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.User;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        Auth.getSingleAuth().initializing();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auth.getSingleAuth().initializing();

                Auth.getSingleAuth().finalUser = new User();

                boolean isWork = loadData();
                String user = Auth.getSingleAuth().finalUser.getUserID();
                if(user != null && isWork != false) {
                    goToTab();
                } else {
                    goToLogin();
                }
            }
        },SPLASH_DISPLAY_LENGTH);

    }

    public void goToLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void goToTab() {
        LoginHandler loginHandler = new LoginHandler(SplashActivity.this);
        loginHandler.login();
    }
    public boolean loadData() {
        boolean isWork = true;
        try {
            FileInputStream fis = openFileInput("userinfo");
            DataInputStream dis = new DataInputStream(fis);
            Auth.getSingleAuth().finalUser.setUserID(dis.readUTF());
            Log.d("userid on load", Auth.getSingleAuth().finalUser.getUserID());
            Auth.getSingleAuth().finalUser.setUserPassword(dis.readUTF());
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            isWork = false;
        } catch (IOException e) {
            //e.printStackTrace();
            isWork = false;
        }
        return isWork;
    }
}