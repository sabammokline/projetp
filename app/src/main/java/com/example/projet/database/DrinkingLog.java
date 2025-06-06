package com.example.projet.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drinking_log")
public class DrinkingLog {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;

    @NonNull
    private String date;  // format: yyyy-MM-dd

    private int BottlesConsumed;  // Number of beers consumed on this date

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
    public DrinkingLog(){
    }
    public DrinkingLog(int id, long userId, String date, int bottlesConsumed){
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.BottlesConsumed = bottlesConsumed;
    }

}
