package com.example.demo.tables;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TenantDetails")
public class TenantDetails implements Serializable {
    @ColumnInfo(name = "TenantId")
    private String TenantId;
    @ColumnInfo(name = "TenantName")
    private String TenantName;
    @ColumnInfo(name = "TenantEmail")
    private String TenantEmail;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "TenantMobile")
    private String TenantMobile = "";
    @ColumnInfo(name = "TenantProfileStatus")
    private String TenantProfileStatus;
    @ColumnInfo(name = "Address")
    private String Address;
    @ColumnInfo(name = "TenantImage")
    private String TenantImage;
    @ColumnInfo(name = "Gender")
    private String Gender;
    @ColumnInfo(name = "NumberOfMembers")
    private String NumberOfMembers;

    public String getNumberOfMembers() {
        return NumberOfMembers;
    }

    public void setNumberOfMembers(String numberOfMembers) {
        NumberOfMembers = numberOfMembers;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public TenantDetails() {
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        TenantId = tenantId;
    }

    public String getTenantName() {
        return TenantName;
    }

    public void setTenantName(String tenantName) {
        TenantName = tenantName;
    }

    public String getTenantEmail() {
        return TenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        TenantEmail = tenantEmail;
    }

    @NonNull
    public String getTenantMobile() {
        return TenantMobile;
    }

    public void setTenantMobile(@NonNull String tenantMobile) {
        TenantMobile = tenantMobile;
    }

    public String getTenantProfileStatus() {
        return TenantProfileStatus;
    }

    public void setTenantProfileStatus(String tenantProfileStatus) {
        TenantProfileStatus = tenantProfileStatus;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTenantImage() {
        return TenantImage;
    }

    public void setTenantImage(String tenantImage) {
        TenantImage = tenantImage;
    }
}
