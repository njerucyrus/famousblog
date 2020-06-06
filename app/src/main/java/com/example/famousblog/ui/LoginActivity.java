package com.example.famousblog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.famousblog.data.UserRepository;
import com.example.famousblog.databinding.ActivityLoginBinding;
import com.example.famousblog.models.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Login");
            actionBar.setElevation(0f);
        }

        repository = UserRepository.getInstance(getApplication());

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    User user = repository.login(mBinding.txtUsername.getText().toString().trim(), mBinding.txtPassword.getText().toString().trim());
                    if (user != null) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username/password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(v, "Fix the errors above", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mBinding.tvDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private boolean validateInput() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtUsername.getText().toString().trim())) {
            isValid = false;
            mBinding.txtUsername.setError("Required.");
        } else {
            mBinding.txtUsername.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtPassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtPassword.setError("Required.");
        } else {
            mBinding.txtPassword.setError(null);
        }

        return isValid;
    }
}