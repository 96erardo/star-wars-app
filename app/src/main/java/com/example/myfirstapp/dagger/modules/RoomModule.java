package com.example.myfirstapp.dagger.modules;

import android.content.Context;

import androidx.room.Room;

import com.example.myfirstapp.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class RoomModule {
    @Singleton
    @Provides
    static AppDatabase appDatabase (Context context) {
        return Room.databaseBuilder(
            context,
            AppDatabase.class,
            "swapp_db"
        ).build();
    }
}
