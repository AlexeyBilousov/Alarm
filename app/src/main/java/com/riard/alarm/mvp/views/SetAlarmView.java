package com.riard.alarm.mvp.views;

import com.arellomobile.mvp.MvpView;

public interface SetAlarmView extends MvpView {
    void setTime();
    void showTime(String time);
    void setDaysOfWeek();
    void showListAudio();
    void showListTypeWakeUp();
    void setDescription();
}
