package com.riard.alarm.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.riard.alarm.app.Constants;
import com.riard.alarm.app.FirstStart;
import com.riard.alarm.R;
import com.riard.alarm.mvp.models.Alarm;

public class AlarmActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentListAlarms.SendMessageToActivity,
        FragmentSetAlarm.SendMessageFromActivity{

    private final String LOG = AlarmActivity.class.getName();

    FragmentManager fragmentManager;
    FragmentListAlarms fragmentListAlarms;
    FragmentSetAlarm fragmentSetAlarm;

    private Alarm alarm;
    private SharedPreferences sharedPreferences;
    private boolean flag;

    public static final int CREATE_ALARM = 0;
    public static final int UPDATE_ALARM = 1;
    private int typeCreateAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "Start onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarm = new Alarm();
        sharedPreferences = getSharedPreferences(Constants.NAME_FILE_FIRST_START, MODE_PRIVATE);
        flagFirstStartRead();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentListAlarms = new FragmentListAlarms();
        fragmentSetAlarm = new FragmentSetAlarm();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, fragmentListAlarms).commit();
        if (!flag) {
            Log.d(LOG, "First start");
            new AsyncTaskFirstStart().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            flag = true;
            flagFirstStartWrite();
        }
        Log.d(LOG, "Start onCreate");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_alarm:

                break;
            case R.id.nav_broadcast:

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startSetAlarm(Alarm alarm) {
        this.alarm = alarm;
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragmentSetAlarm)
                .addToBackStack(null).commit();
    }

    @Override
    public void setTypeCreateAlarm(int type) {
        typeCreateAlarm = type;
    }

    @Override
    public Alarm getAlarm() {
        return alarm;
    }

    @Override
    public void cancelSetAlarm() {
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragmentListAlarms).commit();
        fragmentListAlarms.changeAlarms();
    }

    @Override
    public int getTypeCreateAlarm() {
        return typeCreateAlarm;
    }

    void flagFirstStartRead() {
        flag = sharedPreferences.getBoolean(Constants.NAME_FIRST_START_FLAG, false);
    }

    void flagFirstStartWrite() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.NAME_FIRST_START_FLAG, flag);
        editor.commit();
    }

    private class AsyncTaskFirstStart extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(LOG, "Creating... files");
            FirstStart firstStart = new FirstStart(AlarmActivity.this.getApplicationContext());
            firstStart.initData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(LOG, "Created files!");
            super.onPostExecute(aVoid);
            fragmentListAlarms.changeAlarms();
        }
    }
}