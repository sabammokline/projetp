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
    private String time;

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
    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){return time;}

    public void setCigarettesSmoked(int cigarettesSmoked) {
        this.cigarettesSmoked = cigarettesSmoked;
    }

    public CigaretteLog(int id, long userId, int cigarettesSmoked, String date, String time){
        this.id = id;
        this.userId = userId;
        this.cigarettesSmoked = cigarettesSmoked;
        this.date = date;
        this.time = time;
    }

    public CigaretteLog(){}

    @Override
    public String toString() {
        return "CigaretteLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ",time=" + time + '\'' +
                ", cigarettesSmoked=" + cigarettesSmoked +
                '}';
    }
}
