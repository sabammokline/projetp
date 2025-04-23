package com.example.projet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet.database.CigaretteLog;

import java.util.List;

@Dao
public interface CigaretteLogDao {
    @Insert
    void insert(CigaretteLog log);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId AND date = :date")
    CigaretteLog getLogForDate(long userId, String date);

    @Query("SELECT * FROM cigarette_log WHERE userId = :userId")
    List<CigaretteLog> getAllLogsForUser(long userId);
}
