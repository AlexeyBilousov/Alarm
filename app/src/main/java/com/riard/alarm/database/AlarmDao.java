package com.riard.alarm.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.riard.alarm.entity.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {

    @Delete
    void deleteAlarm(Alarm alarm);

    @Insert
    void insertAlarm(Alarm... alarm);

    @Query("select * from alarms")
    List<Alarm> getAlarms();

    @Update
    void updateAlarm(Alarm... alarm);

}
