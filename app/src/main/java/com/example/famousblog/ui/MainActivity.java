package com.example.famousblog.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.famousblog.R;
import com.example.famousblog.databinding.ActivityMainBinding;
import com.example.famousblog.models.User;
import com.example.famousblog.utils.Utils;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Feeds");
            actionBar.setElevation(0f);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_create_post) {
            startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
        } else if (id == R.id.action_profile) {
            //navigate to profile page
        } else if (id == R.id.action_logout) {
            //logout.
            logout();
        }


        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Sign Out")
                .setDescription("Are you sure you want to Sign Out?")
                .setIcon(R.drawable.ic_baseline_info_24)
                .setPositiveText("Sign Out")
                .onPositive((dialog, which) -> {

                    Toast.makeText(getApplicationContext(), "You are now signed out.", Toast.LENGTH_SHORT).show();
                    Utils.logoutUser(getApplicationContext());
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    dialog.dismiss();

                })
                .setNegativeText("Cancel")
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Utils.isLoggedIn(this)) {
            Toast.makeText(this, "Login Required", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}