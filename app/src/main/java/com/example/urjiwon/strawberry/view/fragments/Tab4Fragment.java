package com.example.urjiwon.strawberry.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.adapters.MyAdapter;
import com.example.urjiwon.strawberry.controllers.FriendsDataHandler;
import com.example.urjiwon.strawberry.models.RecyclerData;
import com.example.urjiwon.strawberry.view.CommentActivity;
import com.example.urjiwon.strawberry.view.WriteActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;
    public  static LinearLayoutManager mLayoutManager;
    public static ArrayList<RecyclerData> myDataset= new ArrayList<RecyclerData>();
    public static int count_timeline=0;
    private android.support.design.widget.FloatingActionButton button;
    public Tab4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FriendsDataHandler handler = new FriendsDataHandler();
        handler.setting(getActivity());
        for (int i=1 ; i<=100 ; i++)
        {
            CommentActivity.up_count.add(1);
            CommentActivity.down_count.add(1);
        }


        View view = inflater.inflate(R.layout.fragment_tab4, null);

        button = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab2);

        mRecyclerView = (RecyclerView)view. findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WriteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}