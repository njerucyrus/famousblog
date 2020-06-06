package com.example.famousblog.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.famousblog.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE username=:username AND password=:password")
    User login(String username , String password);

    @Query("SELECT * FROM user WHERE username=:username")
    User fetchUser(String username);

    @Query("SELECT * FROM  user WHERE 1")
    List<User> fetchUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user WHERE 1")
    void  deleteUsers();
}
