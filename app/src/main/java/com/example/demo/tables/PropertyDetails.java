package com.example.demo.tables;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PropertyDetails")
public class PropertyDetails implements Serializable {
    @ColumnInfo(name = "PropertyId")
    private String PropertyId;
    @ColumnInfo(name = "PropertyName")
    private String PropertyName;
    @ColumnInfo(name = "buildingType")
    private String buildingType;
    @ColumnInfo(name = "NoOfFloors")
    private String NoOfFloors;
    @ColumnInfo(name = "NameOfSecurityGuard")
    private String NameOfSecurityGuard;
    @ColumnInfo(name = "Address")
    private String Address;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "SecurityGuardMobileNumber")
    private String SecurityGuardMobileNumber="";
    @ColumnInfo(name = "PropertyImage")
    private String PropertyImage;

    public String getPropertyId() {
        return PropertyId;
    }

    public void setPropertyId(String propertyId) {
        PropertyId = propertyId;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getNoOfFloors() {
        return NoOfFloors;
    }

    public void setNoOfFloors(String noOfFloors) {
        NoOfFloors = noOfFloors;
    }

    public String getNameOfSecurityGuard() {
        return NameOfSecurityGuard;
    }

    public void setNameOfSecurityGuard(String nameOfSecurityGuard) {
        NameOfSecurityGuard = nameOfSecurityGuard;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @NonNull
    public String getSecurityGuardMobileNumber() {
        return SecurityGuardMobileNumber;
    }

    public void setSecurityGuardMobileNumber(@NonNull String securityGuardMobileNumber) {
        SecurityGuardMobileNumber = securityGuardMobileNumber;
    }

    public String getPropertyImage() {
        return PropertyImage;
    }

    public void setPropertyImage(String propertyImage) {
        PropertyImage = propertyImage;
    }
}
