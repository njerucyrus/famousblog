package com.example.famousblog.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.famousblog.database.FamousBlogDB;
import com.example.famousblog.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostRepository {
    private static PostRepository instance;
    private Application application;

    public static PostRepository getInstance(Application application){
        if (instance == null) {
            instance = new PostRepository();
            instance.application = application;
        }

        return instance;
    }

    public long savePost(Post post) {
        //do async task to save post to db
        long rows = -1;
        try {
             rows = new SavePostTask(application).execute(post).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<Post> fetchPosts(){
        List<Post> posts = new ArrayList<>();
        try {
            posts = new FetchPostTask(application).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return posts;
    }

    private static final class SavePostTask extends AsyncTask<Post, Void, Long>{
        private FamousBlogDB db;
        private Application application;
        SavePostTask(Application application){
            this.application = application;
            this.db = FamousBlogDB.getDatabase(application);
        }
        @Override
        protected Long doInBackground(Post... args) {
            return db.postDao().savePost(args[0]);
        }

        @Override
        protected void onPostExecute(Long rows) {
            super.onPostExecute(rows);
            if (rows > 0) {
                Toast.makeText(application.getApplicationContext(), "Post Created.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while saving your post.", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private static final class FetchPostTask extends AsyncTask<Void, Void, List<Post>>{
        private FamousBlogDB db;
        private Application application;

        FetchPostTask(Application application){
            this.application = application;
            this.db = FamousBlogDB.getDatabase(application);
        }


        @Override
        protected List<Post> doInBackground(Void... voids) {
            return db.postDao().fetchPosts();
        }
    }


}
