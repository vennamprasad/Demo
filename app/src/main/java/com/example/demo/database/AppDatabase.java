package com.example.demo.database;

import com.example.demo.dao.PropertyDetailsDAO;
import com.example.demo.dao.TenantDetailsDAO;
import com.example.demo.tables.PropertyDetails;
import com.example.demo.tables.TenantDetails;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {PropertyDetails.class, TenantDetails.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PropertyDetailsDAO property_details_dao();
    public abstract TenantDetailsDAO tenant_details_dao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
