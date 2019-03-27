package com.example.demo.dao;

import com.example.demo.tables.PropertyDetails;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PropertyDetailsDAO {
    @Query("SELECT * FROM PropertyDetails")
    List<PropertyDetails> getAll();

    @Delete
    void delete(PropertyDetails property_details);

    @Query("SELECT COUNT(PropertyName) FROM PropertyDetails ")
    int getNumberOfRows();

    @Query("SELECT COUNT(PropertyName) FROM PropertyDetails where SecurityGuardMobileNumber=:mobile")
    int getCount(String mobile);

    @Insert()
    void insert(PropertyDetails property_details);

    @Update()
    void update(PropertyDetails property_details);
}
