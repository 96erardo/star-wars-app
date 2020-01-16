package com.example.myfirstapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapp.db.models.Film;

import com.example.myfirstapp.db.models.Film;

import java.util.List;

@Dao
public interface FilmDao {
    @Query("SELECT * FROM film")
    LiveData<List<Film>> getAll();

    @Insert
    void insertAll (List<Film> films);

}
