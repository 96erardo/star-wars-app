package com.example.myfirstapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myfirstapp.db.dao.CoverDao;
import com.example.myfirstapp.db.dao.FilmDao;
import com.example.myfirstapp.db.models.Cover;
import com.example.myfirstapp.db.models.Film;

@Database(entities = {
    Film.class,
    Cover.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDao filmDao();
    public abstract CoverDao coverDao();
}
