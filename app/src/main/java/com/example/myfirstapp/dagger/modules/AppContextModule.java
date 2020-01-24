package com.example.myfirstapp.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppContextModule {
    private Context appContext;

    public AppContextModule (Context context) {
        this.appContext = context;
    }

    @Provides
    Context provideAppContext () {
        return this.appContext;
    }
}
