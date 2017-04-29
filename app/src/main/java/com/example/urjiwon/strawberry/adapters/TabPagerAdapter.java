package com.example.urjiwon.strawberry.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.urjiwon.strawberry.view.fragments.Tab1Fragment;
import com.example.urjiwon.strawberry.view.fragments.Tab2Fragment;
import com.example.urjiwon.strawberry.view.fragments.Tab3Fragment;
import com.example.urjiwon.strawberry.view.fragments.Tab4Fragment;

/**
 * Created by verak on 2017-01-11.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    int tabCount;

    public TabPagerAdapter(FragmentManager fm , int numberofTabs)
    {
        super(fm);
        this.tabCount=numberofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Tab4Fragment tab1= new Tab4Fragment();
                return tab1;
            case 1:
                Tab2Fragment tab2= new Tab2Fragment();
                return tab2;
            case 2:
                Tab3Fragment tab3= new Tab3Fragment();
                return tab3;
            case 3:
                Tab1Fragment tab4= new Tab1Fragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
