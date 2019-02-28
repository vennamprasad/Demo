package com.example.demo;

import android.os.Bundle;

import com.thefinestartist.finestwebview.FinestWebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        new FinestWebView.Builder(this).show("https://www.google.com/");
    }
}
