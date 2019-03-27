package com.example.demo;

import android.app.Application;

public class Demo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new AppSignatureHelper(getApplicationContext()).getAppSignatures();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                DemoUtils.showMsg(DemoUtils.getExceptionRootMessage(throwable), Demo.this.getApplicationContext());
                System.exit(0);
            }
        });
    }
}
