package com.example.projet.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet.database.CigaretteLog;
import com.example.projet.database.DrinkingLog;

import java.util.List;

@Dao
public interface DrinkingLogDao {

    // Insert a new drinking log
    @Insert
    void insert(DrinkingLog drinkingLog);

    @Update
    void update(DrinkingLog drinkingLog);

    // Get all drinking logs for a specific user
    @Query("SELECT * FROM drinking_log WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<DrinkingLog>> getAllDrinkingLogsForUser(long userId);

    // Delete all drinking logs for a specific user
    @Query("DELETE FROM drinking_log WHERE userId = :userId")
    void deleteAllLogsForUser(long userId);

    @Query("SELECT * FROM drinking_log WHERE userId = :userId AND date = :date LIMIT 1")
    DrinkingLog getLogForDate(long userId, String date);

    // Delete a specific log by its ID
    @Query("DELETE FROM drinking_log WHERE id = :logId")
    void deleteLogById(int logId);

    @Query("SELECT * FROM drinking_log WHERE userId = :userId ORDER BY date DESC LIMIT 7")
    List<DrinkingLog> getLast7DaysLog(long userId);

    @Query("SELECT * FROM drinking_log WHERE userId = :userId ORDER BY date ASC LIMIT 1")
    DrinkingLog getFirstDayTracked(long userId);

    @Query("SELECT * FROM drinking_log WHERE userId = :userId")
    List<DrinkingLog> getDrunkDays(long userId);

}

