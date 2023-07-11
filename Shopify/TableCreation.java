package com.zoho.ShopifyApp;
import java.sql.SQLException;
public class TableCreation
{
    public void tableOrder()throws Exception
    {
        products();
        category();
        brand();
        userRole();
        userDetails();
        userAddress();
        wallet();
        accountTable();
        productDetails();
        orderDetails();
        returnDetails();
    }
    private void products()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS products(id SERIAL PRIMARY KEY,name VARCHAR(50) NOT NULL);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("products Table not created");
        } 
    }
    private void category()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS category(id SERIAL PRIMARY KEY,name VARCHAR(50) NOT NULL);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("category Table Not Created");
        } 
    }
    private void brand()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS brand(id SERIAL PRIMARY KEY,name VARCHAR(50) NOT NULL);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("brand Table Not Created");
        } 
    }
    private void userRole()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS user_role(id SERIAL PRIMARY KEY,role VARCHAR(50) NOT NULL);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("userRole Table Not Created");
        } 
    }
    private void userDetails()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS user_details(id SERIAL PRIMARY KEY,role_id INT NOT NULL,name VARCHAR(50) NOT NULL,mobile BIGINT UNIQUE,gmail VARCHAR(30) UNIQUE,gender VARCHAR(10) NOT NULL,created_on Date NOT NULL,status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_roleid FOREIGN KEY(role_id) REFERENCES user_role(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("userDetails Table Not Created");
        } 
    }
    private void wallet()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS wallet(id SERIAL PRIMARY KEY,user_id INT NOT NULL,amount DECIMAL,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("wallet Table Not Created");
        } 
    }
    private void userAddress()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS user_address(id SERIAL PRIMARY KEY,user_id INT NOT NULL,door_no INT NOT NULL,street_name VARCHAR(30) NOT NULL,city VARCHAR(30) NOT NULL,district VARCHAR(30) NOT NULL,pincode INT NOT NULL,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("userAddress Table Not Created");
        } 
    }
    private void accountTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS accounts(id SERIAL PRIMARY KEY,user_id INT NOT NULL,password VARCHAR(50) NOT NULL,status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id) on delete cascade);");                                
        }
        catch(SQLException e)
        {
            System.out.println("accountTable Table Not Created");
        } 
     }
    private void productDetails()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS product_details(id SERIAL PRIMARY KEY,product_id INT NOT NULL,category_id INT NOT NULL,vendor_id INT NOT NULL,brand_id INT NOT NULL,specification TEXT,count INT NOT NULL,price DECIMAL NOT NULL,status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_productid FOREIGN KEY(product_id) REFERENCES products(id),CONSTRAINT fk_categoryid FOREIGN KEY(category_id) REFERENCES category(id),CONSTRAINT fk_vendorid FOREIGN KEY(vendor_id) REFERENCES user_details(id),CONSTRAINT fk_brandid FOREIGN KEY(brand_id) REFERENCES brand(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("productDetails Table Not Created");
        } 
    } 
    private void orderDetails()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS order_details(id SERIAL PRIMARY KEY,user_id INT NOT NULL,product_id INT NOT NULL,vendor_id INT NOT NULL,count INT NOT NULL,amount DECIMAL NOT NULL,order_date DATE NOT NULL,payment_type VARCHAR(20) NOT NULL,Payment_status BOOLEAN NOT NULL,order_status VARCHAR(20),delivery_date DATE,address_id INT,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id),CONSTRAINT fk_productid FOREIGN KEY(product_id) REFERENCES products(id),CONSTRAINT fk_vendorid FOREIGN KEY(vendor_id) REFERENCES user_details(id),CONSTRAINT fk_addressid FOREIGN KEY (address_id) REFERENCES user_address (id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("orderDetails Table Not Created");
        } 
    }
    private void returnDetails()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS return_details(id SERIAL PRIMARY KEY,order_id INT NOT NULL,vendor_id INT NOT NULL,return_reason VARCHAR(50),status VARCHAR(20) NOT NULL,CONSTRAINT fk_orderid FOREIGN KEY(order_id) REFERENCES order_details(id),CONSTRAINT fk_vendorid FOREIGN KEY(vendor_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("category Table Not Created");
        } 
    }   
}