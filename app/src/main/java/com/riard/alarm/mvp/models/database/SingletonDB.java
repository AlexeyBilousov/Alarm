package com.riard.alarm.mvp.models.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class SingletonDB {

    private static SingletonDB instance;
    private AlarmDatabase db;


    private SingletonDB(Context context) {
        db = Room.databaseBuilder(context, AlarmDatabase.class, "alarms").build();
    }

    public static SingletonDB getInstance(Context context) {
        if (instance == null) {
            synchronized (SingletonDB.class) {
                instance = new SingletonDB(context);
            }
        }
        return instance;
    }

    public AlarmDatabase getDb() {
        return db;
    }

}
