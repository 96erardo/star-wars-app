package com.example.myfirstapp.dagger;

import android.content.Context;

import com.example.myfirstapp.SwApplication;
import com.example.myfirstapp.dagger.modules.AppContextModule;
import com.example.myfirstapp.dagger.modules.RoomModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AppContextModule.class,
    RoomModule.class
})
public interface ApplicationGraph {
}
