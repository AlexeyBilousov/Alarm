package com.riard.alarm.di;

import android.content.Context;

import com.riard.alarm.di.modules.ContextModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class})
public interface AppComponent {
    Context getContext();
}
