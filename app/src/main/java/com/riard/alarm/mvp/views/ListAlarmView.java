package com.riard.alarm.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.riard.alarm.mvp.models.Alarm;

import java.util.List;

public interface ListAlarmView extends MvpView {
    void showListAlarms(List<Alarm> alarms);

    void showMessageSwitcher(String message);
}
