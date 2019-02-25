package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void forgot(View view) {
        startActivity(new Intent(this, ForgotActivity.class));
    }
}
