package com.example.myfirstapp;

import android.app.Application;

import com.example.myfirstapp.dagger.ApplicationGraph;
import com.example.myfirstapp.dagger.DaggerApplicationGraph;
import com.example.myfirstapp.dagger.modules.AppContextModule;

public class SwApplication extends Application {
    ApplicationGraph appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerApplicationGraph
                .builder()
                .appContextModule(new AppContextModule(getApplicationContext()))
                .build();
    }
}
