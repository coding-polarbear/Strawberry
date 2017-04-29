package com.example.urjiwon.strawberry.view.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.urjiwon.strawberry.adapters.ABEntry;
import com.example.urjiwon.strawberry.adapters.CustomAdapter;
import com.example.urjiwon.strawberry.utils.Parser;
import com.example.urjiwon.strawberry.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {

    List<String> cityList;
    ArrayList<String> temporature;
    ArrayList<String> iconURI;
    private Parser parser;
    private Uri icon;
    private ArrayList<ABEntry> abList;
    private ArrayAdapter<ABEntry> abAdapter;
    private ListView weatherList;
    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, null);
        abList= new ArrayList<ABEntry>();
        weatherList = (ListView)view.findViewById(R.id.weatherList);
        abAdapter = new CustomAdapter(view.getContext(), R.layout.entry_weather, R.id.eName, abList);
        weatherList.setAdapter(abAdapter);
        Parser parser = new Parser();
        try {
            ArrayList[] list = parser.execute(null,null,null).get();
            cityList = Arrays.asList("서해5도", "서울", "강원영서", "강원영동", "충남", "충북", "경북", "울릉도/독도", "전라남도","전라북도", "제주도");
            temporature = list[0];
            iconURI = list[1];
            for(int i = 0; i < cityList.size(); i++) {
                abList.add(new ABEntry(cityList.get(i),temporature.get(i), Uri.parse(iconURI.get(i))));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;

    }
}