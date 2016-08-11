package com.rakesh.amdb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rakesh.amdb.R;
import com.rakesh.amdb.GridActivityComponents.SlidingTabLayout;
import com.rakesh.amdb.GridActivityComponents.ViewPagerAdapter;


public class GridActivity extends AppCompatActivity {
    final int numberOfTabs = 2;
    public static boolean sort = false;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence titles[] = {"Movies", "Series"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, numberOfTabs);
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
        Intent m = new Intent(this, MainActivity.class);
        switch (item.getOrder()) {
            case 100: {
                startActivity(m);
                finish();
                break;
            }
            case 200: {
                startActivity(m);
                finish();
                break;
            }
            case 300: {
                m = new Intent(this, GridActivity.class);
                sort = !sort;
                startActivity(m);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

