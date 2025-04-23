package com.example.projet.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet.database.WeightLog;

import java.util.List;

@Dao
public interface WeightLogDao {

    // Insert a new weight log
    @Insert
    void insert(WeightLog weightLog);

    // Get all weight logs for a specific user
    @Query("SELECT * FROM weight_log WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<WeightLog>> getAllWeightLogsForUser(long userId);

    // Delete all weight logs for a specific user
    @Query("DELETE FROM weight_log WHERE userId = :userId")
    void deleteAllLogsForUser(long userId);

    // Delete a specific log by its ID
    @Query("DELETE FROM weight_log WHERE id = :logId")
    void deleteLogById(int logId);
}
