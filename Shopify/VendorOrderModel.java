package com.zoho.ShopifyApp;
import java.sql.Date;

import com.zoho.ShopifyApp.ReturnModel;
public class VendorOrderModel 
{
    private int productId;
    private int userId;
    private int count;
    private double amount;
    private Date orderDate;
    private String paymentType;
    private boolean paymentStatus;
    private String username;
    private long mobile;
    private String gmail;
    private String gender;
    private String productName;
    private String categoryName;
    private String brandName;
    private String specification;
    private int doorNo;
    private String streetName;
    private String city;
    private String district;
    private int pincode;
    ReturnModel rmod=null;

    public VendorOrderModel(int productId, int userId, int count, double amount, Date orderDate, String paymentType, boolean paymentStatus, String username, long mobile, String gmail, String gender, String productName, String categoryName, String brandName, String specification, int doorNo, String streetName, String city, String district, int pincode) 
    {
        this.productId = productId;
        this.userId = userId;
        this.count = count;
        this.amount = amount;
        this.orderDate = orderDate;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.username = username;
        this.mobile = mobile;
        this.gmail = gmail;
        this.gender = gender;
        this.productName = productName;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.specification = specification;
        this.doorNo = doorNo;
        this.streetName = streetName;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
    }
    public VendorOrderModel(int productId, int userId, int count, double amount, Date orderDate, String paymentType, boolean paymentStatus, String username, long mobile, String gmail, String gender, String productName, String categoryName, String brandName, String specification, int doorNo, String streetName, String city, String district, int pincode,String return_reason,String status,int vendor_id) 
    {
        rmod=new ReturnModel(return_reason,status,vendor_id);
        this.productId = productId;
        this.userId = userId;
        this.count = count;
        this.amount = amount;
        this.orderDate = orderDate;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.username = username;
        this.mobile = mobile;
        this.gmail = gmail;
        this.gender = gender;
        this.productName = productName;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.specification = specification;
        this.doorNo = doorNo;
        this.streetName = streetName;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
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
