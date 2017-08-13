package com.riard.alarm.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Alarm {

    private boolean vibration;
    private boolean work;
    @PrimaryKey
    private int id;
    private int typeWakeUp;
    private String daysOfWeek;
    private String description;
    private String sound;
    private String time;

    public Alarm() {
    }

    @Ignore
    public Alarm(boolean vibration, boolean work, int id, int typeWakeUp, String daysOfWeek, String description, String sound, String time) {
        this.vibration = vibration;
        this.work = work;
        this.id = id;
        this.typeWakeUp = typeWakeUp;
        this.daysOfWeek = daysOfWeek;
        this.description = description;
        this.sound = sound;
        this.time = time;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeWakeUp() {
        return typeWakeUp;
    }

    public void setTypeWakeUp(int typeWakeUp) {
        this.typeWakeUp = typeWakeUp;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
