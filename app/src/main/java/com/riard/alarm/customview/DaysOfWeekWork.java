package com.riard.alarm.customview;

import java.util.List;

public interface DaysOfWeekWork {

    List<Boolean> getDaysOfWeek(String str);

    String getDaysOfWeekString(List<Boolean> daysOfWeek);

}
