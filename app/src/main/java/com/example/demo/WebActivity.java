package com.example.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import im.delight.android.webview.AdvancedWebView;

public class WebActivity extends AppCompatActivity {
    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.webview);
        mWebView.setListener(this, (AdvancedWebView.Listener) this);
        mWebView.loadUrl("http://www.example.org/");
    }
}
