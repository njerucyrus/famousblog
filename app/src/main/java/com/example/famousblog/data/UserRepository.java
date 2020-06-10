package com.example.famousblog.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.famousblog.database.FamousBlogDB;
import com.example.famousblog.models.User;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private static UserRepository instance;
    private Application application;

    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository();
            instance.application = application;
        }
        return instance;
    }

    public User login(String username, String password) {
        //
        User user;
        try {
            user = new LoginTask(application, username, password).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public long saveUser(User user){
        long rowId = -1;
        try {
            rowId = new SaveUserTask(application).execute(user).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return rowId;
    }

    //async task classes to access db below
    private static final class LoginTask extends AsyncTask<Void, Void, User> {
        private String username;
        private String password;
        private FamousBlogDB db;


         LoginTask(Application application, String username, String password) {
            this.username = username;
            this.password = password;
            db = FamousBlogDB.getDatabase(application);
        }



        @Override
        protected User doInBackground(Void... voids) {
            return db.userDao().login(username, password);
        }


    }

    private static final class SaveUserTask extends AsyncTask<User, Void, Long> {

        private FamousBlogDB db;
        private Application application;

         SaveUserTask(Application application) {
            this.application = application;
            db = FamousBlogDB.getDatabase(application);
        }


        @Override
        protected Long doInBackground(User... args) {

            return db.userDao().saveUser(args[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (aLong > 0) {
                Toast.makeText(application.getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while creating your account.try again later.", Toast.LENGTH_SHORT).show();

            }

        }


    }
}
