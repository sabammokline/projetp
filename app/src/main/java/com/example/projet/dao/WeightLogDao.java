package com.example.projet.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet.database.DrinkingLog;
import com.example.projet.database.WeightLog;

import java.util.List;

@Dao
public interface WeightLogDao {

    // Insert a new weight log
    @Insert
    void insert(WeightLog weightLog);

    @Update
    void update(WeightLog weightLog);

    @Query("SELECT * FROM weight_log WHERE userId = :userId AND date = :date LIMIT 1")
    WeightLog getTodaysLog(long userId, String date);

    // Get all weight logs for a specific user
    @Query("SELECT * FROM weight_log WHERE userId = :userId ORDER BY date DESC")
    List<WeightLog> getAllWeightLogsForUser(long userId);

    // Delete all weight logs for a specific user
    @Query("DELETE FROM weight_log WHERE userId = :userId")
    void deleteAllLogsForUser(long userId);

    // Delete a specific log by its ID
    @Query("DELETE FROM weight_log WHERE id = :logId")
    void deleteLogById(int logId);

    @Query("SELECT * FROM weight_log WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    WeightLog getLastLog(long userId);
}
