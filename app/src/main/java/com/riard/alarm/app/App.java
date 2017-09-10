package com.riard.alarm.app;

import android.app.Application;

import com.riard.alarm.di.AppComponent;
import com.riard.alarm.di.DaggerAppComponent;
import com.riard.alarm.di.modules.ContextModule;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
