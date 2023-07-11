package com.zoho.ShopifyApp;
import java.sql.Date;
public class OrderModel 
{
    private String productName;
    private double amount;
    private Date orderDate;
    private Date deliveryDate;
    private int count;
    private String orderStatus;
    private String paymenttype;
    private boolean paymentstatus;
    private int vendorid;

    public OrderModel(String productName, double amount, Date orderDate, int count, String orderStatus,String paymenttype,boolean paymentstatus,int vendorid,Date deliveryDate) 
    {
        this.productName = productName;
        this.amount = amount;
        this.orderDate = orderDate;
        this.count = count;
        this.orderStatus = orderStatus;
        this.paymenttype=paymenttype;
        this.paymentstatus=paymentstatus;
        this.vendorid=vendorid;
        this.deliveryDate=deliveryDate;
    }    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public boolean isPaymentstatus() {
        return paymentstatus;
    }
    public void setPaymentstatus(boolean paymentstatus) {
        this.paymentstatus = paymentstatus;
    }
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getPaymenttype() {
        return paymenttype;
    }
    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public Date getDeliveryDate() {
        return deliveryDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public int getVendorid() {
        return vendorid;
    }
    public void setVendorid(int vendorid) {
        this.vendorid = vendorid;
    }
}
