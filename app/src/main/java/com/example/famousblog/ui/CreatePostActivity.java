package com.example.famousblog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.famousblog.data.PostRepository;
import com.example.famousblog.databinding.ActivityCreatePostBinding;
import com.example.famousblog.models.Post;
import com.example.famousblog.models.User;
import com.example.famousblog.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity {

    private ActivityCreatePostBinding mBinding;
    private PostRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0f);
            actionBar.setTitle("Create Post");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        repository = PostRepository.getInstance(getApplication());
        mBinding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    User user = Utils.getPersistedUser(getApplicationContext());
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Post post = new Post(
                            mBinding.txtTitle.getText().toString().trim(),
                            mBinding.txtBody.getText().toString().trim(),
                            df.format(new Date()),
                            user

                    );

                   long created = repository.savePost(post);
                   if (created > 0) {
                       startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
                       finish();
                   }

                } else {
                    Snackbar.make(v, "Fix the errors above", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    //GO BACK TO PREVIOUS ACTIVITY WHEN YOU PRESS BACK ARROW BUTTON
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtTitle.getText().toString().trim())) {
            isValid = false;
            mBinding.txtTitle.setError("Required.");
        } else {
            mBinding.txtTitle.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtBody.getText().toString().trim())) {
            isValid = false;
            mBinding.txtBody.setError("Required.");
        } else {
            mBinding.txtBody.setError(null);
        }

        return isValid;
    }

}