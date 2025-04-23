package com.example.projet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet.database.User;

@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    @Update
    void update(User user);
    @Query("SELECT * FROM user_table WHERE id = :id")
    User getUserById(long id);


    @Query("SELECT * FROM user_table WHERE id = :username LIMIT 1")
    User getUserByUsername(String username);
    @Query("SELECT smoking FROM user_table WHERE userName = :username LIMIT 1")
    boolean smokes(String username);

    @Query("SELECT drinking FROM user_table WHERE userName = :username LIMIT 1")
    boolean drinks(String username);

    @Query("SELECT trackWeight FROM user_table WHERE userName = :username LIMIT 1")
    boolean tracksweight(String username);

}
