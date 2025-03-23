package com.example.projet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet.database.Medecin;

@Dao
public interface MedecinDao {

    @Insert
    void insert(Medecin medecin);

    @Update
    void update(Medecin Medecin);

    @Query("SELECT * FROM medecin_table WHERE id = :id LIMIT 1")
    Medecin getMedecinById(int id); // ✅ Add this method
}
