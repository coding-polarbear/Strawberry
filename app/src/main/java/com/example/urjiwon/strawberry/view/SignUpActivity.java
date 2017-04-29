package com.example.urjiwon.strawberry.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.models.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity  {
    private EditText editId, editPassword, editPassword2, editUserName;
    private String id, password, password2, userName;
        private TextView signUpBtn;
        private TextView textView;
        private DatabaseReference mDatabase;
        private ArrayList<String> datas;
        private FirebaseAuth firebaseAuth; //firebasAuth
        private int cnt;
        private  String icon_uri="https://firebasestorage.googleapis.com/v0/b/login-test-82bb8.appspot.com/o/people_icon.png?alt=media&token=e4afa196-1499-4dfe-97b7-731d86a00f92";
        private Map<String, Object> UpdateUserList;

        Auth singleAuth;

        @Override
        public void finish() {
            Intent data = new Intent();
            data.putExtra("returnData", userName);
            setResult(RESULT_OK, data);
            super.finish();
    }
    public void finish2()
    {
        super.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        textView = (TextView) findViewById(R.id.link_login);
        editId = (EditText) findViewById(R.id.sign_editId);
        editPassword = (EditText) findViewById(R.id.sign_editPassword);
        editPassword2 = (EditText) findViewById(R.id.sign_editPassword2);
        editUserName = (EditText) findViewById(R.id.sign_name);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        datas = new ArrayList<String>();
        signUpBtn = (TextView) findViewById(R.id.signupBtn);
        singleAuth = Auth.getSingleAuth();


        mDatabase.child("userList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    datas.add(postSnapshot.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt=0;
                id = editId.getText().toString();
                password = editPassword.getText().toString();
                password2 = editPassword2.getText().toString();
                userName = editUserName.getText().toString();

         if(!(id.equals("")) && !(password.equals(""))) {
             if (password.equals(password2)) //패스워드 재확인
             {

                 singleAuth.mAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         Log.d("Register", "회원가입 : " + task.isSuccessful());
                         if (!task.isSuccessful()) {
                             Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                         }
                         else
                         {
                             for (String str : datas) {
                                 if (str.equals(userName)) {
                                     cnt++;
                                 }
                             }

                             if (cnt == 0) {
                                 Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                          //       mDatabase.child("userList").push().setValue(editUserName.getText().toString());
                                 UpdateUserList = new HashMap<>();
                                 UpdateUserList.put(editUserName.getText().toString() , icon_uri);
                                 mDatabase.child("userList").updateChildren(UpdateUserList);
                                 mDatabase.child("condition").child(userName).setValue(" ");


                            //     childUpdates2.put(chatID, Auth.getSingleAuth().userName);
                                 FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                 UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                         .setDisplayName(editUserName.getText().toString()).setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/login-test-82bb8.appspot.com/o/people_icon.png?alt=media&token=e4afa196-1499-4dfe-97b7-731d86a00f92")).build();
                                 user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()) {
                                   //          Log.d("SignUpActivity", "Profile Updated.");
                                         }
                                     }
                                 });
                                 finish();
                             } else {
                                 Toast.makeText(getApplicationContext(), "UserName 중복입니다.", Toast.LENGTH_SHORT).show();
                                 editUserName.setText("");
                                 editId.setText("");
                                 editPassword.setText("");
                                 editPassword2.setText("");
                             }

                         }
                     }
                 });
             }
             else
             {
                 Toast.makeText(getApplicationContext(), "비밀번호 재확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                 editPassword.setText("");
                 editPassword2.setText("");
             }
         }

            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2();
            }
        });
    }



    }


