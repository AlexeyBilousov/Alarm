package com.riard.alarm.window;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.riard.alarm.R;
import com.riard.alarm.entity.Alarm;
import com.riard.alarm.fuction.JobTime;
import com.riard.alarm.fuction.JobTimeImpl;

public class FragmentSetAlarm extends Fragment {

    public interface SendMessageFromActivity {
        Alarm getAlarm();
    }

    private Button buttonTime;
    private Button buttonDayOfWeek;
    private Button buttonSound;
    private Button buttonCancel;
    private Button buttonDelete;
    private Button buttonOk;
    private Button buttonStopAlarm;
    private CheckBox checkBoxVibration;
    private EditText editTextDescription;

    private Alarm alarm;
    private SendMessageFromActivity sendMessageFromActivity;
    private JobTime jobTime;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessageFromActivity = (SendMessageFromActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_alarm, container, false);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonDayOfWeek = view.findViewById(R.id.button_days_of_week);
        buttonDelete = view.findViewById(R.id.button_delete);
        buttonOk = view.findViewById(R.id.button_ok);
        buttonSound = view.findViewById(R.id.button_sound);
        buttonStopAlarm = view.findViewById(R.id.button_stop_alarm);
        buttonTime = view.findViewById(R.id.button_time);
        checkBoxVibration = view.findViewById(R.id.check_box_vibration);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alarm = sendMessageFromActivity.getAlarm();
        jobTime = new JobTimeImpl();
        if (alarm.getTime() != null) {
            buttonTime.setText(alarm.getTime());
        } else {
            buttonTime.setText(jobTime.getCurrentTime());
            buttonDelete.setVisibility(View.GONE);
        }
        if (alarm.getDaysOfWeek() != null) {
            buttonDayOfWeek.setText(alarm.getDaysOfWeek());
        }
        if (alarm.getSound() != null) {
            buttonSound.setText(alarm.getSound());
        }
        if (alarm.getTypeWakeUp() != 0) {
            buttonStopAlarm.setText("hello");
        }
        if (alarm.getDescription() != null) {
            editTextDescription.setText(alarm.getDescription());
        }
        checkBoxVibration.setChecked(alarm.isVibration());
    }
}
