package com.example.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Element adsElement = new Element();
        adsElement.setTitle("home automation solutions for tenants and property managers");
        View aboutPage = new AboutPage(this)
                .setCustomFont(String.valueOf(R.font.open_sans))
                .isRTL(false)
                .setImage(R.drawable.about)
                .addItem(new Element().setTitle(BuildConfig.VERSION_NAME))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("vennamprasad@gmail.com")
                .addWebsite("www.smartrent.com")
                .addFacebook("www.facebook.com/smartrent")
                .addPlayStore("com.linpack.smartrent")
                .create();
        setContentView(aboutPage);
    }
}
