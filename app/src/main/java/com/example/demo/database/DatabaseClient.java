package com.example.demo.database;

import android.content.Context;

import com.example.demo.Utils;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient mInstance;
    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, Utils.DB_PATH).build();
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
