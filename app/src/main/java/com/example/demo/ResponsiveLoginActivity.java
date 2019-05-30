package com.example.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ResponsiveLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsive_activity_login);
        AppCompatButton appCompatButton=findViewById(R.id.login);
        appCompatButton.setOnClickListener(v -> {
            startActivity(new Intent(ResponsiveLoginActivity.this, NotesMainActivity.class));
        });
    }
}
