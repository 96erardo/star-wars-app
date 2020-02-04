package com.example.myfirstapp.db.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cover")
public class Cover {
    @PrimaryKey
    public int id;

    public String url;

    @ColumnInfo(name = "episode_id")
    public int episodeId;

    public Cover(int id, String url, int episodeId) {
        this.id = id;
        this.url = url;
        this.episodeId = episodeId;
    }
}
