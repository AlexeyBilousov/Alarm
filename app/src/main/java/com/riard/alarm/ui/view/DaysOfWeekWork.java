package com.riard.alarm.ui.view;

import java.util.List;

public interface DaysOfWeekWork {

    List<Boolean> getDaysOfWeek(String str);

    String getDaysOfWeekString(List<Boolean> daysOfWeek);

}
