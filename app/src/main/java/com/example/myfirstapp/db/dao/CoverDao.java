package com.example.myfirstapp.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.myfirstapp.db.models.Cover;

@Dao
public interface CoverDao {
    @Query("SELECT * FROM cover")
    Cover[] getCovers ();

    @Query ("SELECT * FROM cover WHERE episode_id = :episode")
    Cover getCover (int episode);
}
