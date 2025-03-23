package com.example.projet.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cigarette_log")
public class CigaretteLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    @NonNull
    private String date;  // format: yyyy-MM-dd

    private int cigarettesSmoked;

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

    public int getCigarettesSmoked() {
        return cigarettesSmoked;
    }

    public void setCigarettesSmoked(int cigarettesSmoked) {
        this.cigarettesSmoked = cigarettesSmoked;
    }
}
