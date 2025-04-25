package com.example.projet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet.database.CigaretteLog;

import java.util.List;

@Dao
public interface CigaretteLogDao {
    @Update
    void update(CigaretteLog log);
    @Insert
    void insert(CigaretteLog log);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId AND date = :date")
    CigaretteLog getLogForDate(long userId, String date);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId")
    List<CigaretteLog> getAllLogsForUser(long userId);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId ORDER BY date DESC LIMIT 7")
    List<CigaretteLog> getLast7DaysLog(long userId);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId ORDER BY date ASC LIMIT 1")
    CigaretteLog getFirstDay(long userId);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId AND time IS NOT NULL ORDER BY date DESC LIMIT 1")
    CigaretteLog getLastSmokedDate(long userId);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId AND cigarettesSmoked < :averageCigs")
    List<CigaretteLog> getSmokedDays(long userId, int averageCigs);

}
