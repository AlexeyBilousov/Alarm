package com.riard.alarm.ui;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.riard.alarm.mvp.models.database.SingletonDB;
import com.riard.alarm.mvp.models.Alarm;

import java.util.List;

public class LoadAlarms extends AsyncTaskLoader<List<Alarm>> {
    private final String LOG = LoadAlarms.class.getName();

    private Context context;

    public LoadAlarms(Context context) {
        super(context);
        Log.d(LOG, "Start LoadAlarms");
        this.context = context;
    }

    @Override
    public List<Alarm> loadInBackground() {
        Log.d(LOG, "Start LoadInBackground");
        List<Alarm> alarms;
        SingletonDB singletonDB = SingletonDB.getInstance(context);
        alarms = singletonDB.getDb().alarmDao().getAlarms();
        Log.d(LOG, "Finish LoadInBackground");
        return alarms;
    }
}
