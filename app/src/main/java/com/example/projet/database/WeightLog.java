package com.example.projet.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weight_log")
public class WeightLog {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;

    @NonNull
    private String date;  // format: yyyy-MM-dd

    private float weight;  // Weight recorded on this date

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public WeightLog(){
    }
    public WeightLog(long id, long userId, String date, float weight){
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.weight = weight;
    }
}
