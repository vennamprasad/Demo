package com.example.demo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Demo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("NoteKeeperRealmDB.realm")
                .build();

        Realm.setDefaultConfiguration(configuration);
        new AppSignatureHelper(getApplicationContext()).getAppSignatures();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            DemoUtils.showMsg(DemoUtils.getExceptionRootMessage(throwable), Demo.this.getApplicationContext());
            System.exit(0);
        });
    }
}
