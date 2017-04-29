package com.example.urjiwon.strawberry.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.controllers.LoginHandler;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends AppCompatActivity {
    EditText editId, editPassword;
    TextView loginBtn;
    TextView registerBtn;

    private static boolean success;
    private User user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private String userID, userPW, userName = null;
    private Auth auth;
    private static final int request_code = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = Auth.getSingleAuth();


        editId = (EditText) findViewById(R.id.editId);
        editPassword = (EditText) findViewById(R.id.editPassword);
        loginBtn = (TextView) findViewById(R.id.loginBtn);
        registerBtn = (TextView) findViewById(R.id.registerBtn);

        firebaseAuth = auth.mAuth;


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), com.example.urjiwon.strawberry.view.SignUpActivity.class);
                startActivityForResult(i,request_code);
                Toast.makeText(getApplicationContext(), "회원가입으로 갑니다.", Toast.LENGTH_SHORT).show();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = editId.getText().toString();
                userPW = editPassword.getText().toString();
                if(!(userID.equals("")) && !(userPW.equals("")) ) {
                    Auth.getSingleAuth().finalUser.setUserID(userID);
                    Auth.getSingleAuth().finalUser.setUserPassword(userPW);
                    if (userName != null) sendUserName();
                    LoginHandler handler = new LoginHandler(LoginActivity.this);
                    handler.login();

                } else {
                    Toast.makeText(getApplicationContext(), "다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    editId.setText("");
                    editPassword.setText("");
                }
            }
        });
    }


    private void sendUserName() {
        firebaseAuth = FirebaseAuth.getInstance();
        if(auth.user!= null) {
            final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName).build();
            auth.user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                          Log.d("LoginActivity", "User profile updated.");
                            } else {
                                         Log.d("LoginActivity", "profile update fail");
                            }
                        }
                    });
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == request_code) && (resultCode == RESULT_OK)) {
            userName = data.getExtras().getString("returnData");
            //      Log.d("UserName : ", userName);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        editId.setText("");
        editPassword.setText("");
    }
}