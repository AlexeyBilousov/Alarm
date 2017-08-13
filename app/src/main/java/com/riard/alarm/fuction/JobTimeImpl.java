package com.riard.alarm.fuction;

import android.util.Log;

import java.util.Calendar;

public class JobTimeImpl implements JobTime {

    private final String LOG = JobTimeImpl.class.getName();

    private static final int CHECK_TEN = 10;
    private static final String SEPARATOR = ":";
    private static final String ZERO = "0";

    @Override
    public String getCurrentTime() {
        Log.d(LOG, "Start getCurrentTime");
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) < CHECK_TEN) {
            stringBuilder.append(ZERO);
        }
        stringBuilder.append(calendar.get(Calendar.HOUR_OF_DAY));
        stringBuilder.append(SEPARATOR);
        if (calendar.get(Calendar.MINUTE) < CHECK_TEN) {
            stringBuilder.append(ZERO);
        }
        stringBuilder.append(calendar.get(Calendar.MINUTE));
        Log.d(LOG, "Finish getCurrentTime");
        return stringBuilder.toString();
    }
}
