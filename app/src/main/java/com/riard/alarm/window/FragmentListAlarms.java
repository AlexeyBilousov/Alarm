package com.riard.alarm.window;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.riard.alarm.R;
import com.riard.alarm.database.SingletonDB;
import com.riard.alarm.entity.Alarm;

import java.util.ArrayList;
import java.util.List;

public class FragmentListAlarms extends Fragment implements RecyclerViewAdapter.SentMessageToFragment,
        LoaderManager.LoaderCallbacks<List<Alarm>> {

    public interface SendMessageToActivity {
        void startSetAlarm(Alarm alarm);
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
                alarms = data;
                adapter = new RecyclerViewAdapter(alarms, this);
                recyclerView.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    View.OnClickListener addAlarm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendMessageToActivity.startSetAlarm(new Alarm());
        }
    };

    @Override
    public void sendAlarm(Alarm alarm) {
        sendMessageToActivity.startSetAlarm(alarm);
    }

    @Override
    public void updateDB(Alarm alarm) {
        new AsyncTaskUpdateDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, alarm);
    }

    public void changeAlarms() {
        loadAlarms.onContentChanged();
    }

    private class AsyncTaskUpdateDB extends AsyncTask<Alarm, Void, Void> {

        @Override
        protected Void doInBackground(Alarm... alarms) {
            SingletonDB singletonDB = SingletonDB.getInstance(getContext().getApplicationContext());
            singletonDB.getDb().alarmDao().updateAlarm(alarms[0]);
            return null;
        }
    }
}

class LoadAlarms extends AsyncTaskLoader<List<Alarm>> {

    private final String LOG = LoadAlarms.class.getName();

    private Context context;

    public LoadAlarms(Context context) {
        super(context);
        Log.d(LOG, "Start LoadAlarms");
        this.context = context;
    }



    @Override
    public List<Alarm> loadInBackground() {
        Log.d(LOG, "Start LoadInBackground");
        List<Alarm> alarms;
        SingletonDB singletonDB = SingletonDB.getInstance(context);
        alarms = singletonDB.getDb().alarmDao().getAlarms();
        Log.d(LOG, "Finish LoadInBackground");
        return alarms;
    }
}
