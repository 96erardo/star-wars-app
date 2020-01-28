package com.example.myfirstapp.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapp.db.models.Film;

import java.util.List;

@Dao
public interface FilmDao {
    @Query("SELECT * FROM film WHERE id = :filmId")
    Film getFilm(int filmId);

    @Query("SELECT COUNT(*) FROM film WHERE id = :filmId AND (last_modified + :day) > CURRENT_TIMESTAMP")
    int filmExists (int filmId, long day);

    @Query("SELECT * FROM film ORDER BY episode ASC")
    Film[] getFilms();

    @Insert
    public void insertFilm (Film film);
}
