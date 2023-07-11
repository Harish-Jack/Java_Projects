package com.zoho.ShopifyApp;
public class ProductListModel 
{
    private String productName;
    private String categoryName;
    private String vendorName;
    private String brandName;
    private int productId;
    private int categoryId;
    private int vendorId;
    private int brandId;
    private String specification;
    private int count;
    private double price;
    
    public ProductListModel(String productName, String categoryName, String vendorName, String brandName,int productId, int categoryId, int vendorId, int brandId,String specification, int count, double price) 
    {
        this.productName = productName;
        this.categoryName = categoryName;
        this.vendorName = vendorName;
        this.brandName = brandName;
        this.productId = productId;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
        this.brandId = brandId;
        this.specification = specification;
        this.count = count;
        this.price = price;
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
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public int getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }
    
    public int getBrandId() {
        return brandId;
    }
    
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
    
    public String getSpecification() {
        return specification;
    }
    
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}
