package com.example.myfirstapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfirstapp.db.dao.FilmDao;
import com.example.myfirstapp.db.models.Film;

@Database(entities = {Film.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDao filmDao();

    private static final String DB_NAME = "swapp_db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }

        return instance;
    }

    private static AppDatabase create (final Context context) {
        return Room.databaseBuilder(
            context,
            AppDatabase.class,
            DB_NAME
        ).build();
    }
}
