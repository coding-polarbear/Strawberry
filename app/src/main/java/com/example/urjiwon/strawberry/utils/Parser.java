package com.example.urjiwon.strawberry.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by verak on 2017-02-14.
 */

public class Parser extends AsyncTask<Void, Void, ArrayList[]> {
    @Override
    protected ArrayList[] doInBackground(Void... params) {
        ArrayList[] lists = new ArrayList[2];
        ArrayList<String> temporature;
        ArrayList<String> iconURI;
        ArrayList<String> names;

        String path = "http://www.kweather.co.kr/main/";

        iconURI = new ArrayList<String>();
        temporature = new ArrayList<String>();

        try {
            Document document = Jsoup.connect(path + "main.html").timeout(7000).get();
            Elements elements;
            Elements icons;

            elements = document.select(".slide").select(".present_map").select("li").select("span");
            for (int i = 0; i < elements.size(); i++) {
                String text = elements.get(i).text();
                Log.d("온도 확인", text);
                temporature.add(text);
            }

            icons = document.select(".slide").select(".present_map").select("li").select("img");
            Log.d("size", Integer.toString(icons.size()));
            for(int i = 0; i < icons.size(); i++) {
                String src = path + icons.get(i).attr("src");
                Log.d("src", src);
                iconURI.add(src);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lists[0] = temporature;
        lists[1] = iconURI;
        return lists;
    }
}