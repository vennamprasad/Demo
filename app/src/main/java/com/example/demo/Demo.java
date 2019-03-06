package com.example.demo;

import android.app.Application;

public class Demo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Utils.showMsg(Utils.getExceptionRootMessage(throwable), Demo.this.getApplicationContext());
                System.exit(0);
            }
        });
    }
}
