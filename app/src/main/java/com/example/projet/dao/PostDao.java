package com.example.projet.dao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet.database.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    List<Post> getAllPosts();
}
