package com.example.projet.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drinking_log")
public class DrinkingLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    @NonNull
    private String date;  // format: yyyy-MM-dd

    private int BottlesConsumed;  // Number of beers consumed on this date

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public int getBottlesConsumed() {
        return BottlesConsumed;
    }

    public void setBottlesConsumed(int beersConsumed) {
        this.BottlesConsumed = beersConsumed;
    }
}
