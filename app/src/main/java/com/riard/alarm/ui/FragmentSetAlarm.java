package com.riard.alarm.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.riard.alarm.R;
import com.riard.alarm.ui.view.DaysOfWeekControl;
import com.riard.alarm.mvp.models.database.SingletonDB;
import com.riard.alarm.mvp.models.Alarm;
import com.riard.alarm.mvp.common.JobTime;
import com.riard.alarm.mvp.common.JobTimeImpl;
import com.riard.alarm.mvp.presenters.SetAlarmPresenter;
import com.riard.alarm.mvp.views.SetAlarmView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FragmentSetAlarm extends MvpAppCompatFragment implements SetAlarmView {
    @InjectPresenter
    SetAlarmPresenter setAlarmPresenter;

    @Override
    public void setTime() {
        RadialTimePickerDialogFragment radialTimePickerDialogFragment =
                new RadialTimePickerDialogFragment();
        radialTimePickerDialogFragment.setOnTimeSetListener(onTimeSetListener);
        radialTimePickerDialogFragment.setForced24hFormat();
        radialTimePickerDialogFragment.show(getFragmentManager(), TAG_TIME);
    }

    @Override
    public void showTime(String time) {
        buttonTime.setText(time);
    }

    @Override
    public void setDaysOfWeek() {

    }

    @Override
    public void showListAudio() {

    }

    @Override
    public void showListTypeWakeUp() {

    }

    @Override
    public void setDescription() {

    }

    public interface SendMessageFromActivity {
        Alarm getAlarm();
        void cancelSetAlarm();
        int getTypeCreateAlarm();
    }

    private final String LOG = FragmentSetAlarm.class.getName();
    private static final String TAG_TIME = "timePickerDialogFragment";

    private static final int ADD = 0;
    private static final int UPDATE = 1;
    private static final int DELETE = 2;

    private int typeAdd;

    private Button buttonTime;
    private Button buttonSound;
    private Button buttonCancel;
    private Button buttonDelete;
    private Button buttonOk;
    private Button buttonStopAlarm;
    private CheckBox checkBoxVibration;
    private EditText editTextDescription;
    private DaysOfWeekControl daysOfWeekControl;

    private Alarm alarm;
    private SendMessageFromActivity sendMessageFromActivity;
    private JobTime jobTime;
    private SingletonDB singletonDB;
    private Map<String, String> listMusic;
    private String[] arrayMusic;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private MediaPlayer mediaPlayer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessageFromActivity = (SendMessageFromActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_alarm, container, false);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonDelete = view.findViewById(R.id.button_delete);
        buttonOk = view.findViewById(R.id.button_ok);
        buttonSound = view.findViewById(R.id.button_sound);
        buttonStopAlarm = view.findViewById(R.id.button_stop_alarm);
        buttonTime = view.findViewById(R.id.button_time);
        checkBoxVibration = view.findViewById(R.id.check_box_vibration);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        daysOfWeekControl = view.findViewById(R.id.day_of_week_control);
        buttonTime.setOnClickListener(setTime);
        buttonOk.setOnClickListener(addAlarm);
        buttonCancel.setOnClickListener(cancel);
        buttonDelete.setOnClickListener(deleteAlarm);
        buttonSound.setOnClickListener(setMusic);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listMusic = new HashMap<>();
        alarm = sendMessageFromActivity.getAlarm();
        typeAdd = sendMessageFromActivity.getTypeCreateAlarm();
        singletonDB = SingletonDB.getInstance(getActivity().getApplicationContext());
        jobTime = new JobTimeImpl();
        if (alarm.getTime() != null) {
            buttonTime.setText(alarm.getTime());
        } else {
            buttonTime.setText(jobTime.getCurrentTime());
            buttonDelete.setVisibility(View.GONE);
        }
        if (alarm.getDaysOfWeek() != null) {

        }
        if (alarm.getSound() != null) {
            buttonSound.setText(alarm.getSoundName());
        }
        if (alarm.getTypeWakeUp() != 0) {
            buttonStopAlarm.setText("hello");
        }
        if (alarm.getDescription() != null) {
            editTextDescription.setText(alarm.getDescription());
        }
        checkBoxVibration.setChecked(alarm.isVibration());
        daysOfWeekControl.setChoseDays(alarm.getDaysOfWeek());
    }

    @Override
    public void onResume() {
        super.onResume();
        RadialTimePickerDialogFragment radialTimePickerDialogFragment =
                (RadialTimePickerDialogFragment)getFragmentManager().findFragmentByTag(TAG_TIME);
        if (radialTimePickerDialogFragment != null) {
            radialTimePickerDialogFragment.setOnTimeSetListener(onTimeSetListener);
        }
    }

    View.OnClickListener addAlarm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            alarm.setTime(buttonTime.getText().toString());
            alarm.setDaysOfWeek(daysOfWeekControl.getChoseDays());
            alarm.setDescription(editTextDescription.getText().toString());
            if (typeAdd == ADD) {
                new SetAlarmAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ADD);
            } else {
                new SetAlarmAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, UPDATE);
            }
        }
    };

    View.OnClickListener deleteAlarm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new SetAlarmAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DELETE);
        }
    };

    View.OnClickListener cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            closeFragment();
        }
    };

    View.OnClickListener setTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setAlarmPresenter.setTime();
        }
    };

    RadialTimePickerDialogFragment.OnTimeSetListener onTimeSetListener =
            new RadialTimePickerDialogFragment.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
            setAlarmPresenter.showTime(hourOfDay, minute);
        }
    };

    private void closeFragment() {
        sendMessageFromActivity.cancelSetAlarm();
    }

    private View.OnClickListener setMusic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getResources().getString(R.string.choose_music));
            builder.setCancelable(false);
            builder.setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    stopMusic();
                    if (alarm.getSound() == null) {
                        alarm.setSound(listMusic.get(arrayMusic[0]));
                        alarm.setSoundName(arrayMusic[0]);
                        buttonSound.setText(arrayMusic[0]);
                    }
                    dialog.cancel();
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    stopMusic();
                    dialog.cancel();
                }
            });
            new LoadMusicAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    };

    private class SetAlarmAsyncTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... num) {
            String str = null;
            Log.d(LOG, "Start doInBackground");
            Log.d(LOG, String.valueOf(num[0]));
            switch (num[0]) {
                case ADD:
                    singletonDB.getDb().alarmDao().insertAlarm(alarm);
                    str = getContext().getResources().getString(R.string.alarm_added);
                    break;
                case UPDATE:
                    singletonDB.getDb().alarmDao().updateAlarm(alarm);
                    str = getContext().getResources().getString(R.string.alarm_added);
                    break;
                case DELETE:
                    singletonDB.getDb().alarmDao().deleteAlarm(alarm);
                    str = getContext().getResources().getString(R.string.alarm_deleted);
                    break;
            }
            return str;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
            closeFragment();
        }
    }

    private class LoadMusicAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String[] STAR = {"*"};
            Cursor cursor;
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            cursor = getActivity().managedQuery(uri, STAR, selection, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String songName = cursor
                                .getString(cursor
                                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                        String path = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DATA));
                        listMusic.put(songName, path);
                    } while (cursor.moveToNext());
                }
            }
            int count = 0;
            arrayMusic = new String[listMusic.size()];
            for (Object object:listMusic.keySet()) {
                arrayMusic[count] = object.toString();
                count++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            builder.setSingleChoiceItems(arrayMusic, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    stopMusic();
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(listMusic.get(arrayMusic[i]));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buttonSound.setText(arrayMusic[i]);
                    alarm.setSoundName(arrayMusic[i]);
                    alarm.setSound(listMusic.get(arrayMusic[i]));
                }
            });
            dialog = builder.create();
            dialog.show();
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}
