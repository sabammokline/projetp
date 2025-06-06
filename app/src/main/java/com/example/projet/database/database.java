package com.example.projet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projet.dao.CigaretteLogDao;
import com.example.projet.dao.PostDao;
import com.example.projet.dao.UserDao;
import com.example.projet.dao.DrinkingLogDao;
import com.example.projet.dao.WeightLogDao;


@Database(entities = {User.class, CigaretteLog.class,
        DrinkingLog.class,WeightLog.class,Post.class}, version = 11)
public abstract class database extends RoomDatabase {

    private static volatile database INSTANCE;

    public abstract CigaretteLogDao cigaretteLogDao();
    public abstract UserDao userDao();

    public  abstract PostDao postDao();
    // DAO for DrinkingLog
    public abstract DrinkingLogDao drinkingLogDao();

    // DAO for WeightLog
    public abstract WeightLogDao weightLogDao();

    public static database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    database.class, "user_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;}
}
