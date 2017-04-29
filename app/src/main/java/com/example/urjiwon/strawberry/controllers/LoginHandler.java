package com.example.urjiwon.strawberry.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.view.TabLayoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by baehy on 2017-03-04.
 */

public class LoginHandler {
    private Activity activity;
    public LoginHandler(Activity activity) {
        this.activity = activity;
    }
    public void login() {
        Log.d("finaluser", Auth.getSingleAuth().finalUser.getUserID());
        String userID = Auth.getSingleAuth().finalUser.getUserID();
        String userPW = Auth.getSingleAuth().finalUser.getUserPassword();
        Auth.getSingleAuth().mAuth.signInWithEmailAndPassword(userID, userPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("SignIn", "로그인 : " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("login", "fail");
                    Toast.makeText(activity.getApplicationContext(), "로그인에 실패하였습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(),  Auth.getSingleAuth().mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                    Auth.getSingleAuth().userName = Auth.getSingleAuth().mAuth.getCurrentUser().getDisplayName();
                    Log.d("displayName", Auth.getSingleAuth().mAuth.getCurrentUser().getDisplayName());
                    Auth.getSingleAuth().user = Auth.getSingleAuth().mAuth.getCurrentUser();
                    Auth.getSingleAuth().photoUrl = Auth.getSingleAuth().user.getPhotoUrl();
                    Intent i = new Intent(activity, TabLayoutActivity.class);
                    if(activity.getClass().getSimpleName().equals("LoginActivity")) {
                        saveToFile();
                    }
                    activity.startActivity(i);
                }
            }
        });

        Auth.getSingleAuth().mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Auth.getSingleAuth().user = firebaseAuth.getCurrentUser();
                if (Auth.getSingleAuth().user != null) {
                    // User is signed in
                    Log.d("", "onAuthStateChanged:signed_in:" + Auth.getSingleAuth().user.getUid());
                } else {
                    // User is signed out
                    Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                }
            }
        };
    }
    public void saveToFile() {
        String fileName = "userinfo";
        try {
            FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(Auth.getSingleAuth().finalUser.getUserID());
            dos.writeUTF(Auth.getSingleAuth().finalUser.getUserPassword());
            dos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
