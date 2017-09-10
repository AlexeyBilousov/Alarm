package com.riard.alarm.mvp.common;

import android.util.Log;

import java.util.Calendar;

public class JobTimeImpl implements JobTime {

    private final String LOG = JobTimeImpl.class.getName();

    private static final int CHECK_TEN = 10;
    private static final String SEPARATOR = ":";
    private static final String ZERO = "0";

    @Override
    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return getTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    @Override
    public String getChoiceTime(int hours, int minutes) {
        return getTime(hours, minutes);
    }

    private String getTime(int hours, int minutes) {
        Log.d(LOG, "Start getTime");
        StringBuilder stringBuilder = new StringBuilder();
        if (hours < CHECK_TEN) {
            stringBuilder.append(ZERO);
        }
        stringBuilder.append(hours);
        stringBuilder.append(SEPARATOR);
        if (minutes < CHECK_TEN) {
            stringBuilder.append(ZERO);
        }
        stringBuilder.append(minutes);
        Log.d(LOG, "Finish getTime");
        return stringBuilder.toString();
    }
}
