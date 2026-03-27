package com.example.mobile_app_2.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String imageUrl;
    public String duration; // e.g., "120 min"

    public Movie(String title, String description, String imageUrl, String duration) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.duration = duration;
    }
}
