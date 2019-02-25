package com.example.demo.dao;

import com.example.demo.tables.PropertyDetails;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PropertyDetailsDAO {
    @Query("SELECT * FROM PropertyDetails")
    List<PropertyDetails> getAll();

    @Insert(onConflict = REPLACE)
    void insert(PropertyDetails property_details);

    @Delete
    void delete(PropertyDetails property_details);

    @Update
    int update(PropertyDetails property_details);
}
