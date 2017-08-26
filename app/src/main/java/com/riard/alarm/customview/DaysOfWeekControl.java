package com.riard.alarm.customview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.TextView;

import com.riard.alarm.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexey on 18.08.2017.
 */

public class DaysOfWeekControl extends FrameLayout implements View.OnClickListener {

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;
    private final String LOG = DaysOfWeekControl.this.getClass().getName();

    private TextView mondayView;
    private TextView tuesdayView;
    private TextView wednesdayView;
    private TextView thursdayView;
    private TextView fridayView;
    private TextView saturdayView;
    private TextView sundayView;

    private String[] daysOfWeek;
    private List<Boolean> choseDays;
    private DaysOfWeekWork daysOfWeekWork;


    public DaysOfWeekControl(@NonNull Context context) {
        super(context);
        initControl();
    }

    public DaysOfWeekControl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public DaysOfWeekControl(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl() {
        Log.d(LOG, "Start initControl");
        choseDays = new ArrayList<>();
        daysOfWeekWork = new DaysOfWeekWorkImplEn();
        daysOfWeek = getContext().getResources().getStringArray(R.array.days_of_week);
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        layoutInflater.inflate(R.layout.days_of_week_control, this);
        mondayView = findViewById(R.id.monday_control);
        tuesdayView = findViewById(R.id.tuesday_control);
        wednesdayView = findViewById(R.id.wednesday_control);
        thursdayView = findViewById(R.id.thursday_control);
        fridayView = findViewById(R.id.friday_control);
        saturdayView = findViewById(R.id.saturday_control);
        sundayView = findViewById(R.id.sunday_control);
        mondayView.setOnClickListener(this);
        tuesdayView.setOnClickListener(this);
        wednesdayView.setOnClickListener(this);
        thursdayView.setOnClickListener(this);
        fridayView.setOnClickListener(this);
        saturdayView.setOnClickListener(this);
        sundayView.setOnClickListener(this);
        mondayView.setText(daysOfWeek[MONDAY]);
        tuesdayView.setText(daysOfWeek[TUESDAY]);
        wednesdayView.setText(daysOfWeek[WEDNESDAY]);
        thursdayView.setText(daysOfWeek[THURSDAY]);
        fridayView.setText(daysOfWeek[FRIDAY]);
        saturdayView.setText(daysOfWeek[SATURDAY]);
        sundayView.setText(daysOfWeek[SUNDAY]);
        for(int i = 0; i <= SUNDAY; i++) {
            choseDays.add(false);
        }
        setColorView();
        Log.d(LOG, "Finish initControl");
    }

    public void setChoseDays(String daysOfWeek) {
        Log.d(LOG, "String is " + daysOfWeek);
        choseDays = daysOfWeekWork.getDaysOfWeek(daysOfWeek);
        setColorView();
    }

    public String getChoseDays() {
        return daysOfWeekWork.getDaysOfWeekString(choseDays);
    }

    @Override
    public void onClick(View view) {
        //if (daysOfWeekOnClickListener == null) return;
        switch (view.getId()) {
            case R.id.monday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.MondayClick);
                setTextStyle(mondayView, MONDAY);
                break;
            case R.id.tuesday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.TuesdayClick);
                setTextStyle(tuesdayView, TUESDAY);
                break;
            case R.id.wednesday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.WednesdayClick);
                setTextStyle(wednesdayView, WEDNESDAY);
                break;
            case R.id.thursday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.ThursdayClick);
                setTextStyle(thursdayView, THURSDAY);
                break;
            case R.id.friday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.FridayClick);
                setTextStyle(fridayView, FRIDAY);
                break;
            case R.id.saturday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.SaturdayClick);
                setTextStyle(saturdayView, SATURDAY);
                break;
            case R.id.sunday_control:
                //daysOfWeekOnClickListener.onChecked(ClickEvent.SundayClick);
                setTextStyle(sundayView, SUNDAY);
                break;
        }
    }

    private void setTextStyle(TextView textView, int position) {
        if (choseDays.get(position)) {
            textView.setTextAppearance(getContext(), R.style.TextStyle);
            choseDays.set(position, false);
        } else {
            textView.setTextAppearance(getContext(), R.style.TextStyle_Big);
            choseDays.set(position, true);
        }
    }

    private void setColorView() {
        for (int i = 0; i < choseDays.size(); i++) {
            switch (i) {
                case 0:
                    setColorViewItem(choseDays.get(i), mondayView);
                    break;
                case 1:
                    setColorViewItem(choseDays.get(i), tuesdayView);
                    break;
                case 2:
                    setColorViewItem(choseDays.get(i), wednesdayView);
                    break;
                case 3:
                    setColorViewItem(choseDays.get(i), thursdayView);
                    break;
                case 4:
                    setColorViewItem(choseDays.get(i), fridayView);
                    break;
                case 5:
                    setColorViewItem(choseDays.get(i), saturdayView);
                    break;
                case 6:
                    setColorViewItem(choseDays.get(i), sundayView);
                    break;
            }
        }
    }

    private void setColorViewItem(boolean flag, TextView textView) {
        if (flag) {
            textView.setTextAppearance(this.getContext(), R.style.TextStyle_Big);
        } else {
            textView.setTextAppearance(this.getContext(), R.style.TextStyle);
        }
    }

}
