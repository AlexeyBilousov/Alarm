package com.riard.alarm;

import android.content.Context;

import com.riard.alarm.database.SingletonDB;
import com.riard.alarm.entity.Alarm;

public class FirstStart {

    private Context context;

    public FirstStart(Context context) {
        this.context = context;
    }

    public void initData() {
        SingletonDB workWithRoom = SingletonDB.getInstance(context);
        workWithRoom.getDb().alarmDao().insertAlarm(new Alarm(true, false, 0, "SAT SUN", "", "", "", "10:00"),
                new Alarm(true, false, 0, "MON TUE WED THU FRI", "", "", "", "07:30"));
    }



}
