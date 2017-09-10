package com.riard.alarm.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.riard.alarm.R;
import com.riard.alarm.mvp.models.Alarm;
import com.riard.alarm.mvp.presenters.ListAlarmPresenter;
import com.riard.alarm.mvp.views.ListAlarmView;

import java.util.ArrayList;
import java.util.List;

public class FragmentListAlarms extends MvpAppCompatFragment implements RecyclerViewAdapter.SentMessageToFragment,
        LoaderManager.LoaderCallbacks<List<Alarm>>, ListAlarmView {
    @InjectPresenter
    ListAlarmPresenter listAlarmPresenter;

    @Override
    public void showListAlarms(List<Alarm> alarms) {
        adapter = new RecyclerViewAdapter(alarms, this, getMvpDelegate());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessageSwitcher(String message) {

    }

    public interface SendMessageToActivity {
        void startSetAlarm(Alarm alarm);
        void setTypeCreateAlarm(int type);
    }

    SendMessageToActivity sendMessageToActivity;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonAddAlarm;
    private Button buttonAddTimer;

    private LoadAlarms loadAlarms;
    private List<Alarm> alarms;
    private final String LOG = FragmentListAlarms.class.getName();

    private static final int LOAD_ALARMS = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessageToActivity = (SendMessageToActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG, "Start onCreateView");
        View view = inflater.inflate(R.layout.fragment_list_alarms, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_alarms);
        buttonAddAlarm = view.findViewById(R.id.button_alarm);
        buttonAddTimer = view.findViewById(R.id.button_timer);
        Log.d(LOG, "Finish onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(LOG, "Start onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        alarms = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        buttonAddAlarm.setOnClickListener(addAlarm);
        getActivity().getSupportLoaderManager().initLoader(LOAD_ALARMS, null, this);
        Log.d(LOG, "Finish onActivityCreated");
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOAD_ALARMS:
                Log.d(LOG, "Create LoadAlarms");
                loadAlarms = new LoadAlarms(FragmentListAlarms.this.getActivity().getApplicationContext());
                loadAlarms.forceLoad();
                return loadAlarms;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Alarm>> loader, List<Alarm> data) {
        switch (loader.getId()) {
            case LOAD_ALARMS:
                Log.d(LOG, "Get Result LoadAlarms");
                listAlarmPresenter.showListAlarm(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    View.OnClickListener addAlarm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendMessageToActivity.setTypeCreateAlarm(AlarmActivity.CREATE_ALARM);
            sendMessageToActivity.startSetAlarm(new Alarm());
        }
    };

    @Override
    public void sendAlarm(Alarm alarm) {
        sendMessageToActivity.setTypeCreateAlarm(AlarmActivity.UPDATE_ALARM);
        sendMessageToActivity.startSetAlarm(alarm);
    }

    public void changeAlarms() {
        loadAlarms.onContentChanged();
    }


}


