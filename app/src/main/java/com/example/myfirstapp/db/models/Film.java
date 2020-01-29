package com.example.myfirstapp.db.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "film")
public class Film implements Comparable<Film> {
    @PrimaryKey
    public int id;

    public int episode;

    public String title;

    @ColumnInfo(name = "opening_crawl")
    public String openingCrawl;

    @Ignore
    public String cover;

    @ColumnInfo(name = "last_modified", defaultValue = "CURRENT_TIMESTAMP")
    public String lastModified;

    public Film (int id, int episode, String title, String openingCrawl) {
        this.id = id;
        this.episode = episode;
        this.title = title;
        this.openingCrawl = openingCrawl;
    }

    @Override
    public int compareTo(Film f) {
        if (this.episode > f.episode)
            return 1;

        if (this.episode < f.episode)
            return -1;

        return 0;
    }
}
