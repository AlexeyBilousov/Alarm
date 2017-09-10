package com.riard.alarm.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpDelegate;

public abstract class MvpAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private MvpDelegate<? extends MvpAdapter> mvpDelegate;
    private MvpDelegate<?> parentDelegate;
    private String childId;

    public MvpAdapter(MvpDelegate<?> parentDelegate, String childId) {
        this.parentDelegate = parentDelegate;
       this.childId = childId;

        getMvpDelegate().onCreate();
    }

    public MvpDelegate getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new MvpDelegate<>(this);
            mvpDelegate.setParentDelegate(parentDelegate, childId);

        }
        return mvpDelegate;
    }
}
