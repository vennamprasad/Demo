package com.example.demo.dao;
import com.example.demo.tables.TenantDetails;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TenantDetailsDAO {
    @Query("SELECT * FROM TenantDetails")
    List<TenantDetails> getAll();

    @Delete
    void delete(TenantDetails property_details);

    @Query("SELECT COUNT(TenantName) FROM TenantDetails ")
    int getNumberOfRows();

    @Query("SELECT COUNT(TenantName) FROM TenantDetails where TenantMobile=:mobile")
    int getCount(String mobile);

    @Insert()
    void insert(TenantDetails tenantDetails);

    @Update()
    void update(TenantDetails tenantDetails);
}
