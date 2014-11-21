package com.alhoda.shefa;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"الرئيسة", "الضبط"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        actionBar = getActionBar();
        mAdapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        SharedPreferences settingPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean programStatus = settingPrefs.getBoolean("programStatus", true);
        boolean running = false;
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
                running = true;
                break;
            }
        }
        if (programStatus && !running) {
            Intent intentService = new Intent(getApplicationContext(), MyService.class);
            getApplicationContext().startService(intentService);
        }
//        if (programStatus) {
//            Intent intentService = new Intent(getApplicationContext(), MyService.class);
//            getApplicationContext().startService(intentService);
//        } else {
//            Intent intentService = new Intent(getApplicationContext(), MyService.class);
//            getApplicationContext().stopService(intentService);
//        }

//        if (programStatus&&!running) {
//            Intent intentService = new Intent(getApplicationContext(), MyService.class);
//            getApplicationContext().startService(intentService);
//        }
//        else if (!programStatus&&running)
//        {
//            Intent intentService = new Intent(getApplicationContext(), MyService.class);
//            getApplicationContext().stopService(intentService);
//        }
    }


//        boolean running = false;
//        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (MyService.class.getName().equals(service.service.getClassName())) {
//                running = true;
//                break;
//            }
//        }
//        if (!running) {
//            Intent intentService = new Intent(getApplicationContext(), MyService.class);
//            getApplicationContext().startService(intentService);
//        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
