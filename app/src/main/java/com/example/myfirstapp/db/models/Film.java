package com.example.myfirstapp.db.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "film")
public class Film {
    @PrimaryKey
    public int id;

    public int episode;

    public String title;

    @ColumnInfo(name = "opening_crawl")
    public String openingCrawl;

    @ColumnInfo(name = "last_modified", defaultValue = "CURRENT_TIMESTAMP")
    public String lastModified;

    public Film (int id, int episode, String title, String openingCrawl) {
        this.id = id;
        this.episode = episode;
        this.title = title;
        this.openingCrawl = openingCrawl;
    }
}
