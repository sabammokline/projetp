package com.example.projet.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "post_table")
public class Post {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public String username;
    public String content;
    public String date;

    public Post(int userId, String username, String content, String date) {
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.date = date;
    }
    public Post() {}

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

