package com.example.urjiwon.strawberry.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.urjiwon.strawberry.R;
import com.example.urjiwon.strawberry.adapters.TabPagerAdapter;
import com.example.urjiwon.strawberry.controllers.FriendsDataHandler;
import com.example.urjiwon.strawberry.models.Auth;


public class TabLayoutActivity extends AppCompatActivity {
    public static FriendsDataHandler friendsData = new FriendsDataHandler();
    private int buttonCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_demo);

        /*
        Intent service = new Intent(this, MainService.class);
        startService(service);
*/
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setElevation(0);
        ProgressDialog asyncDialog = new ProgressDialog(TabLayoutActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로딩중입니다.");
        asyncDialog.show();
        asyncDialog.setCancelable(false);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.btn1).setText("타임라인"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.btn2).setText("채팅"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.btn3).setText("계정"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.unnamed).setText("날씨"));
        asyncDialog.dismiss();



        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
               // Log.d("확인", Integer.toString(tab.getPosition()));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_layout_demo, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            deleteFile("userinfo");
            Toast.makeText(this,"로그아웃이 완료되었습니다. 다시 실행해서 로그인해주세요",Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        buttonCount++;
        if(buttonCount == 2) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        Toast.makeText(this," '뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Auth.getSingleAuth().mAuth.addAuthStateListener(Auth.getSingleAuth().mAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (Auth.getSingleAuth().mAuthListener != null) {
            Auth.getSingleAuth().mAuth.removeAuthStateListener(Auth.getSingleAuth().mAuthListener);
        }
    }





}