package com.riard.alarm.mvp.presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.riard.alarm.R;
import com.riard.alarm.app.App;
import com.riard.alarm.mvp.models.database.SingletonDB;
import com.riard.alarm.mvp.models.Alarm;
import com.riard.alarm.mvp.views.ItemListAlarmView;

@InjectViewState
public class ItemListAlarmPresenter extends MvpPresenter<ItemListAlarmView> {
    private Context context = App.getAppComponent().getContext();

    public void switcherWorkAlarm(Alarm alarm) {
        new AsyncTaskUpdateDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, alarm);
    }

    private class AsyncTaskUpdateDB extends AsyncTask<Alarm, Void, Alarm> {
        @Override
        protected Alarm doInBackground(Alarm... alarms) {
            SingletonDB singletonDB = SingletonDB.getInstance(context);
            singletonDB.getDb().alarmDao().updateAlarm(alarms[0]);
            return alarms[0];
        }

        @Override
        protected void onPostExecute(Alarm alarm) {
            super.onPostExecute(alarm);
            String message;
            message = alarm.isWork() ? context.getResources().getString(R.string.alarm_switch_on) :
                    context.getResources().getString(R.string.alarm_switch_off);
            getViewState().showMessageSwitcher(message);
        }
    }

}
