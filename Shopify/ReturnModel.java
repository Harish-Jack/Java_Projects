package com.zoho.ShopifyApp;
public class ReturnModel 
{
    private int vendorId;
    private String returnReason;
    private String status;

    public ReturnModel(String returnReason, String status,int vendorId) 
    {
        this.vendorId = vendorId;
        this.returnReason = returnReason;
        this.status = status;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
