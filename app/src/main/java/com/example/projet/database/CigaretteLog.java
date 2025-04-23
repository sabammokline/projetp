package com.example.projet.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cigarette_log")
public class CigaretteLog {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;

    @NonNull
    private String date;  // format: yyyy-MM-dd

    private int cigarettesSmoked;

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

    public int getCigarettesSmoked() {
        return cigarettesSmoked;
    }

    public void setCigarettesSmoked(int cigarettesSmoked) {
        this.cigarettesSmoked = cigarettesSmoked;
    }
}
