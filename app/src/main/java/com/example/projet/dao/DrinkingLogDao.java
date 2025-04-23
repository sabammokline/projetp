package com.example.projet.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet.database.DrinkingLog;

import java.util.List;

@Dao
public interface DrinkingLogDao {

    // Insert a new drinking log
    @Insert
    void insert(DrinkingLog drinkingLog);

    // Get all drinking logs for a specific user
    @Query("SELECT * FROM drinking_log WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<DrinkingLog>> getAllDrinkingLogsForUser(long userId);

    // Delete all drinking logs for a specific user
    @Query("DELETE FROM drinking_log WHERE userId = :userId")
    void deleteAllLogsForUser(long userId);

    // Delete a specific log by its ID
    @Query("DELETE FROM drinking_log WHERE id = :logId")
    void deleteLogById(int logId);
}
