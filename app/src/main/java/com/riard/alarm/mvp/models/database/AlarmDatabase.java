package com.riard.alarm.mvp.models.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.riard.alarm.mvp.models.Alarm;

@Database(entities = {Alarm.class}, version = 1)
public abstract class AlarmDatabase extends RoomDatabase {

    public abstract AlarmDao alarmDao();
}
