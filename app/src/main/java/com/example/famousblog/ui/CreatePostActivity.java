package com.example.famousblog.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.famousblog.R;
import com.example.famousblog.databinding.ActivityCreatePostBinding;
import com.google.android.material.transformation.FabTransformationBehavior;

public class CreatePostActivity extends AppCompatActivity {

    private ActivityCreatePostBinding mBinding;
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

}