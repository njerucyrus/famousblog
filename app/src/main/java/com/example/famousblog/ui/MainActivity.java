package com.example.famousblog.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.famousblog.R;
import com.example.famousblog.adapters.PostAdapter;
import com.example.famousblog.data.PostRepository;
import com.example.famousblog.databinding.ActivityMainBinding;
import com.example.famousblog.interfaces.RecyclerViewItemClickListener;
import com.example.famousblog.models.Post;
import com.example.famousblog.models.User;
import com.example.famousblog.utils.Utils;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener<Post> {

    private ActivityMainBinding mBinding;
    private PostAdapter adapter;
    private PostRepository repository;

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
        repository = PostRepository.getInstance(getApplication());
        adapter = new PostAdapter(this, this);
        showPosts();


    }

    private void showPosts() {
        adapter.setData(repository.fetchPosts());
        mBinding.postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.postsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.postsRecyclerView.setAdapter(adapter);
        mBinding.postsRecyclerView.setHasFixedSize(true);

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

    @Override
    public void onItemClicked(Post post) {
        Toast.makeText(this, "Title "+post.getTitle()+" POSTED BY "+post.getPostedBy().getName(), Toast.LENGTH_LONG ).show();
    }
}