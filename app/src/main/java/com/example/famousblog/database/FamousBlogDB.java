package com.example.famousblog.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.famousblog.interfaces.PostDao;
import com.example.famousblog.interfaces.UserDao;
import com.example.famousblog.models.Post;
import com.example.famousblog.models.User;

@Database(entities = {User.class, Post.class}, version = 2, exportSchema = false)

public abstract class FamousBlogDB extends RoomDatabase {
    private static FamousBlogDB instance;

    public static FamousBlogDB getDatabase(final Application application) {
        if (instance == null) {
            synchronized (FamousBlogDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application.getApplicationContext(),
                            FamousBlogDB.class, "famous_blog_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract UserDao userDao();
    public abstract PostDao postDao();
}
