package com.zoho.ShopifyApp;
public class AddressModel 
{
    private int userId;
    private int doorNo;
    private String streetName;
    private String city;
    private String district;
    private int pincode;

    public AddressModel(int userId, int doorNo, String streetName, String city, String district, int pincode) {
        this.userId = userId;
        this.doorNo = doorNo;
        this.streetName = streetName;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(int doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
