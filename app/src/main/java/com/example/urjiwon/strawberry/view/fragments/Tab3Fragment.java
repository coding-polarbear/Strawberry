package com.example.urjiwon.strawberry.view.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.adapters.CustomAdapter;
import com.example.urjiwon.strawberry.models.Auth;
import com.example.urjiwon.strawberry.models.FriendsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3Fragment extends Fragment {
    private ImageView imageview;
    private ProgressDialog progressDialog;
    private Button edit_check;
    private EditText edit_message;
    private StorageReference mstorage;
    private static final int GALLERY_INTENT = 2;
    private Map<String, Object> UpdateUserList;
    private TextView textView;
    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UpdateUserList = new HashMap<>();
        View view = inflater.inflate(R.layout.fragment_tab3, null);
  //      Glide.with(getActivity()).load(Uri.parse(a)).bitmapTransform(new CropCircleTransformation(getActivity())).into(imageview);
        FriendsData.getInstance().getmDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("순서", "3번");
                String a= FriendsData.getInstance().getUserHash().get(Auth.getSingleAuth().userName).toString();
                Glide.with(getActivity()).load(Uri.parse(a)).bitmapTransform(new CropCircleTransformation(getActivity())).into(imageview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    //  Log.d("섹스", a);
        FriendsData.getInstance().getmDatabase().child("condition").child(Auth.getSingleAuth().userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                edit_message.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        imageview = (ImageView) view.findViewById(R.id.edit_icon);
        textView= (TextView)view.findViewById(R.id.myname);
        textView.setFocusableInTouchMode(false);
        textView.setText(Auth.getSingleAuth().userName);
        edit_message= (EditText)view.findViewById(R.id.edit_message);
        edit_check = (Button)view.findViewById(R.id.edit_check);
        mstorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(getActivity());

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });

        edit_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendsData.getInstance().getmDatabase().child("condition").child(Auth.getSingleAuth().userName).setValue(edit_message.getText().toString());
                Toast.makeText(getContext() , "상태메세지가 변경 되었습니다." , Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("업로드 중입니다....");
            progressDialog.show();
            Uri uri = data.getData();
            StorageReference filepath = mstorage.child(Auth.getSingleAuth().userName);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                        UpdateUserList.put(Auth.getSingleAuth().userName , downloadUri.toString());
                    FriendsData.getInstance().getmDatabase().child("userList").updateChildren(UpdateUserList);

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(Auth.getSingleAuth().userName).setPhotoUri(downloadUri).build();

                    Auth.getSingleAuth().user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            }
                        }
                    });

                    Glide.with(getActivity()).load(downloadUri).bitmapTransform(new CropCircleTransformation(getActivity())).into(imageview);
                    progressDialog.dismiss();

                }
            });
        }
    }

}