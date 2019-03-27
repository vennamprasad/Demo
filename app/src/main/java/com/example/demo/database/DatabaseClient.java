package com.example.demo.database;

import android.content.Context;

import androidx.room.Room;

import static com.example.demo.DemoUtils.DB_PATH;

public class DatabaseClient {

    private static DatabaseClient mInstance;
    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, DB_PATH).fallbackToDestructiveMigration().build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
