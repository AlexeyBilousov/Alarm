package com.riard.alarm.customview;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaysOfWeekWorkImplEn implements DaysOfWeekWork {

    private final String LOG = DaysOfWeekWorkImplEn.this.getClass().getName();
    private static final String SPACE = " ";
    private static final String RULE = "[A-Z]+";

    private Pattern pattern;
    private Matcher matcher;
    private List<Boolean> daysOfWeek;

    private static final String MONDAY = "MON";
    private static final String TUESDAY = "TUE";
    private static final String WEDNESDAY = "WED";
    private static final String THURSDAY = "THU";
    private static final String FRIDAY = "FRI";
    private static final String SATURDAY = "SAT";
    private static final String SUNDAY = "SUN";

    @Override
    public List<Boolean> getDaysOfWeek(String str) {
        Log.d(LOG, "Start getDaysOfWeek");
        daysOfWeek = new ArrayList<>();
        for (int i = 0; i <= DaysOfWeekControl.SUNDAY; i++) {
            daysOfWeek.add(false);
        }
        if (str != null) {
            pattern = Pattern.compile(RULE);
            matcher = pattern.matcher(str);
            while (matcher.find()) {
                Log.d(LOG, matcher.group());
                setDay(matcher.group());
            }
        }
        return daysOfWeek;
    }

    @Override
    public String getDaysOfWeekString(List<Boolean> daysOfWeek) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < daysOfWeek.size(); i++) {
            if (daysOfWeek.get(i)) {
                switch (i) {
                    case DaysOfWeekControl.MONDAY:
                        stringBuilder.append(MONDAY);
                        break;
                    case DaysOfWeekControl.TUESDAY:
                        stringBuilder.append(TUESDAY);
                        break;
                    case DaysOfWeekControl.WEDNESDAY:
                        stringBuilder.append(WEDNESDAY);
                        break;
                    case DaysOfWeekControl.THURSDAY:
                        stringBuilder.append(THURSDAY);
                        break;
                    case DaysOfWeekControl.FRIDAY:
                        stringBuilder.append(FRIDAY);
                        break;
                    case DaysOfWeekControl.SATURDAY:
                        stringBuilder.append(SATURDAY);
                        break;
                    case DaysOfWeekControl.SUNDAY:
                        stringBuilder.append(SUNDAY);
                        break;
                }
                stringBuilder.append(SPACE);
            }
        }
        return stringBuilder.toString();
    }


    private void setDay(String str) {
        switch (str) {
            case MONDAY:
                daysOfWeek.set(DaysOfWeekControl.MONDAY, true);
                break;
            case TUESDAY:
                daysOfWeek.set(DaysOfWeekControl.TUESDAY, true);
                break;
            case WEDNESDAY:
                daysOfWeek.set(DaysOfWeekControl.WEDNESDAY, true);
                break;
            case THURSDAY:
                daysOfWeek.set(DaysOfWeekControl.THURSDAY, true);
                break;
            case FRIDAY:
                daysOfWeek.set(DaysOfWeekControl.FRIDAY, true);
                break;
            case SATURDAY:
                daysOfWeek.set(DaysOfWeekControl.SATURDAY, true);
                break;
            case SUNDAY:
                daysOfWeek.set(DaysOfWeekControl.SUNDAY, true);
                break;
        }
    }
}
