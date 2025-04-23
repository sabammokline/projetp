package com.example.projet.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "post_table")
public class Post {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long userId;
    public String username;
    public String content;
    public String date;

    public Post(long userId, String username, String content, String date) {
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.date = date;
    }
    public Post() {}

    // Getters
    public long getId() {
        return id;
    }

    public long getUserId() {
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
    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
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

