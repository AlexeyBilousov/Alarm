package com.riard.alarm.mvp.presenters;

import android.os.AsyncTask;

import com.arellomobile.mvp.MvpPresenter;
import com.riard.alarm.mvp.common.JobTime;
import com.riard.alarm.mvp.common.JobTimeImpl;
import com.riard.alarm.mvp.views.SetAlarmView;

/**
 * Created by Alexey on 08.09.2017.
 */

public class SetAlarmPresenter extends MvpPresenter<SetAlarmView> {
    private JobTime jobTime;

    public SetAlarmPresenter() {
        jobTime = new JobTimeImpl();
    }

    public void setTime() {
        getViewState().setTime();
    }

    public void showTime(int hours, int minutes) {
        new AsyncTaskSetTime().execute(hours, minutes);
    }

    private class AsyncTaskSetTime extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            return jobTime.getChoiceTime(integers[0], integers[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getViewState().showTime(s);
        }
    }
}
