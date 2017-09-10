package com.riard.alarm.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.riard.alarm.mvp.models.Alarm;
import com.riard.alarm.mvp.views.ListAlarmView;

import java.util.List;

@InjectViewState
public class ListAlarmPresenter extends MvpPresenter<ListAlarmView> {
    public void showListAlarm(List<Alarm> alarms) {
        getViewState().showListAlarms(alarms);
    }

    public void updateDBAfterClick(List<Alarm> alarms, int position, boolean isSwitch) {

    }

}
