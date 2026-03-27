package com.example.mobile_app_2.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "showtimes",
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Theater.class,
                        parentColumns = "id",
                        childColumns = "theaterId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Showtime {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int movieId;
    public int theaterId;
    public String startTime; // e.g., "10:00 AM"
    public String date; // e.g., "2023-12-01"

    public Showtime(int movieId, int theaterId, String startTime, String date) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
        this.date = date;
    }
}
