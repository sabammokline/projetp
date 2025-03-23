package com.example.projet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet.database.Coach;
import com.example.projet.database.Medecin;

@Dao
public interface CoachDao {
    @Query("SELECT * FROM coach_table WHERE id = :id")
    Coach getById(int id);

    @Update
    void update(Coach coach);

    @Insert
    void insert(Coach coach);

}
