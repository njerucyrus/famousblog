package com.example.famousblog.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.famousblog.models.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long savePost(Post post);

    @Update
    void updateUser(Post post);
    @Query("SELECT * FROM post WHERE postId=:id")
    Post fetchPost(int id);

    @Query("SELECT * FROM post WHERE 1")
    List<Post> fetchPosts();

    @Delete
    void deletePost(Post post);

    @Query("DELETE FROM post WHERE 1")
    void deleteAllPosts();
}
