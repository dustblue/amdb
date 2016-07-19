package com.rakesh.amdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class GridActivity extends AppCompatActivity {
    final int numberOfTabs =2;
    public boolean sort = false;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence titles[]={"Movies","Series"};
    Intent m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        adapter =  new ViewPagerAdapter(getSupportFragmentManager(), titles, numberOfTabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(position -> R.color.tabsScrollColor);
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getOrder()) {
            case 100 : {
                m = new Intent(this, MainActivity.class);
                startActivity(m);
                break;
            }
            case 200 : {
                m = new Intent(this, MainActivity.class);
                startActivity(m);
                break;
            }
            case 300 : {

                m = new Intent(this, GridActivity.class);
                sort = !sort;
                startActivity(m);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Boolean ifSort(){
        return sort;
    }
}

