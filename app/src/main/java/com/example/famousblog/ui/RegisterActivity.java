package com.example.famousblog.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.famousblog.R;
import com.example.famousblog.data.UserRepository;
import com.example.famousblog.databinding.ActivityRegisterBinding;
import com.example.famousblog.models.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding mBinding;
    private UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Create Account");
            actionBar.setElevation(0f);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        repository = UserRepository.getInstance(getApplication());

        mBinding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IMPLEMENTATION HERE
                if (validateInput()) {
                    User user = new User(
                            mBinding.txtUsername.getText().toString().trim(),
                            mBinding.txtEmail.getText().toString().trim(),
                            mBinding.txtPhoneNumber.getText().toString().trim(),
                            mBinding.txtName.getText().toString().trim()
                    );
                    user.setPassword(mBinding.txtPassword.getText().toString().trim());
                    Log.d(TAG, "onClick: "+user.toString());
                    long rowId = repository.saveUser(user);
                    if (rowId > 0) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }

                } else {
                    Snackbar.make(v, "Fix the errors above", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mBinding.tvHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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

    private boolean validateInput() {
        boolean isValid = true;

        if (TextUtils.isEmpty(mBinding.txtName.getText().toString().trim())) {
            isValid = false;
            mBinding.txtName.setError("Required.");
        } else {
            mBinding.txtName.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtEmail.getText().toString().trim())) {
            isValid = false;
            mBinding.txtEmail.setError("Required.");
        } else {
            mBinding.txtEmail.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtPhoneNumber.getText().toString().trim())) {
            isValid = false;
            mBinding.txtPhoneNumber.setError("Required.");
        } else {
            mBinding.txtPhoneNumber.setError(null);
        }


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

        if (TextUtils.isEmpty(mBinding.txtConfirmPassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtConfirmPassword.setError("Required.");
        } else {
            mBinding.txtConfirmPassword.setError(null);
        }

        if (!Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), mBinding.txtEmail.getText().toString().trim())) {
            mBinding.txtEmail.setError("Invalid email address");
            isValid = false;
        }else {
            mBinding.txtEmail.setError(null);
        }

        if (!TextUtils.equals(mBinding.txtPassword.getText().toString().trim(), mBinding.txtConfirmPassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtConfirmPassword.setError("Password do not match");
        } else{
            mBinding.txtConfirmPassword.setError(null);
        }

        return isValid;
    }


}