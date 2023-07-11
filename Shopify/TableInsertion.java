package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TableInsertion
{
    private static ResultSet resultset=null;
    private static PreparedStatement ps=null;
    public void insertOrder()throws Exception
    {
        if(checkValues("user_role")==0)
            insertRole();
        if(checkValues("user_details")==0)
            insertUser();
        if(checkValues("products")==0)
            insertProduct();
        if(checkValues("category")==0)
            insertCategory();
        if(checkValues("brand")==0)
            insertBrand();
        if(checkValues("product_details")==0)
            insertProductList();
        productDelivery();
    }
    private void insertRole()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("INSERT INTO user_role (role) VALUES ('User'),('Vendor'),('Admin');");                                
        }             
        catch(SQLException e)
        {
            System.out.println("Category Not Added");
        } 
    }
    private void insertUser()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("INSERT INTO user_details (role_id,name,mobile,gmail,gender,created_on) VALUES (3,'admin',9876543210,'admin@gmail.com','male',current_date),(2,'Rajesh',6789012345,'rajesh@gmail.com','male',current_date),(2,'Ramesh',7890612345,'ramesh@gmail.com','male',current_date);");                                
            StatementSingleton.getStatement().executeUpdate("INSERT INTO accounts (user_id,password) values (1,'Admin@123'),(2,'Rajesh@123'),(3,'Ramesh@123');");
            StatementSingleton.getStatement().executeUpdate("INSERT INTO user_address( user_id,door_no,street_name,city,district,pincode ) VALUES (1,11,'kammapatti','Srivilliputtur','Virudhunagar','626125'),(2,12,'Mettu Theru','Srivilliputtur','Virudhunagar','626125'),(3,14,'nggo colony','Srivilliputtur','Virudhunagar','626125');");
        }             
        catch(SQLException e)
        {
            System.out.println("User Not Added");
        } 
    }
    private void insertProduct()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("INSERT INTO products (name) VALUES ('Samsung Galaxy M31'),('Apple iPhone 12'),('OnePlus 9 Pro'),('Xiaomi Redmi Note 10 Pro'),('Realme 8 Pro'),('Oppo F19 Pro'),('Vivo V21'),('Mi 11X'),('Samsung Galaxy S21 Ultra'),('Apple iPhone SE'),('OnePlus Nord CE'),('Xiaomi Poco X3'),('Realme Narzo 30 Pro'),('Oppo A53'),('Vivo Y20'),('Mi 10T Pro'),('Samsung Galaxy A52'),('Apple iPhone 11'),('OnePlus 8T'),('Xiaomi Redmi 9 Power'),('Realme 7'),('Oppo Reno 5 Pro'),('Vivo V20 Pro'),('Mi 10T Lite'),('Samsung Galaxy M51'),('Apple iPhone XR'),('OnePlus 8 Pro'),('Xiaomi Redmi Note 9 Pro'),('Realme 6 Pro'),('Oppo F17 Pro'),('Vivo Y51');");                                
        }             
        catch(SQLException e)
        {
            System.out.println("Product Not Added");
        } 
    }
    private void insertCategory()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("INSERT INTO category (name) VALUES ('Mobiles'),('Laptops'),('Televisions'),('Cameras'),('Headphones'),('Speakers'),('Wearable Devices'),('Home Appliances'),('Gaming Consoles'),('Printers');");                                
        }             
        catch(SQLException e)
        {
            System.out.println("Category Not Added");
        } 
    }
    private void insertBrand()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("INSERT INTO brand (name) VALUES ('Samsung'),('Apple'),('OnePlus'),('Xiaomi'),('Realme'),('Oppo'),('Vivo'),('Mi'),('Sony'),('LG'),('HP'),('Dell'),('Canon'),('Nikon'),('Bose'),('JBL'),('Fitbit'),('Whirlpool'),('Xbox'),('PlayStation');");                                
        }             
        catch(SQLException e)
        {
            System.out.println("Brand Not Added");
        } 
    }
    private void insertProductList()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("INSERT INTO product_details (product_id, category_id, vendor_id, brand_id, specification, count, price, status) VALUES (1, 1, 2, 1, 'Screen Size: 6.4 inches, RAM: 4GB, Storage: 64GB, Camera: 48MP', 10, 9999.99, true),(2, 1, 3, 2, 'Screen Size: 13.3 inches, RAM: 8GB, Storage: 512GB, Processor: Intel Core i7', 5, 19999.99, true),(3, 1, 2, 3, 'Screen Size: 6.7 inches, RAM: 6GB, Storage: 128GB, Camera: 64MP', 20, 14999.99, true),(4, 1, 2, 1, 'Screen Size: 55 inches, Resolution: 4K Ultra HD, Smart TV: Yes', 15, 24999.99, true),(5, 1, 3, 2, 'Type: Over-ear, Connectivity: Bluetooth 5.0, Battery Life: 30 hours', 8, 17999.99, true),(6, 1, 2, 3, 'Screen Size: 6.5 inches, RAM: 4GB, Storage: 128GB, Camera: 48MP', 12, 11999.99, true),(7, 1, 2, 1, 'Resolution: 20.1 MP, Sensor Type: CMOS, Zoom: 10x Optical Zoom', 30, 29999.99, true),(8, 1, 3, 2, 'Type: True Wireless Earbuds, Battery Life: 6 hours, Water Resistance: IPX7', 3, 15999.99, true),(9, 1, 2, 3, 'Screen Size: 6.2 inches, RAM: 6GB, Storage: 256GB, Camera: 64MP', 18, 13999.99, true),(10, 1, 2, 1, 'Screen Size: 65 inches, Resolution: 4K Ultra HD, Smart TV: Yes', 7, 21999.99, true);");                                
        }             
        catch(SQLException e)
        {
            System.out.println("Product List Not Added");
        } 
    }
    private static int checkValues(String tablename)
    {
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("SELECT COUNT(id) FROM "+tablename+";");;
            resultset.next();
            return resultset.getInt("count");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    public int addUser(UserModel user)
    {
        int userId=0;
        try
        {
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            ps =Database.getInstance().getConnection().prepareStatement("INSERT INTO user_details (role_id, name, mobile, gmail, gender, created_on) VALUES (?, ?, ?, ?, ?, ?) RETURNING ID;");
            ps.setInt(1, user.getRoleid());
            ps.setString(2, user.getUname());
            ps.setLong(3, user.getMobile());
            ps.setString(4, user.getMail());
            ps.setString(5, user.getGender());
            ps.setDate(6, currentDate);
            resultset = ps.executeQuery();
            if (resultset.next()) 
            {
                userId = resultset.getInt("ID");
                if (userId != 0) {
                    ps =Database.getInstance().getConnection().prepareStatement("INSERT INTO accounts (user_id, password) VALUES (?, ?);");
                    ps.setInt(1, userId);
                    ps.setString(2, user.getPassword());
                    ps.executeUpdate();
                }
            }
        }             
        catch(SQLException e)
        {
            throw new DataNotFoundException("USER ALREADY EXISTS");
        } 
    return userId;
    }
    public void addAddress(AddressModel adr)
    {
        try
        {
            ps = Database.getInstance().getConnection().prepareStatement("select Exists(select id from user_address where user_id=? and door_no=? and street_name=? and city=? and district=? and pincode=?) as status;");
            ps.setInt(1, adr.getUserId());
            ps.setInt(2, adr.getDoorNo());
            ps.setString(3, adr.getStreetName());
            ps.setString(4, adr.getCity());
            ps.setString(5, adr.getDistrict());
            ps.setInt(6, adr.getPincode());
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getBoolean("status"))
                    throw new DataAlreadyExistsException("\nADDRESS ALREADY EXISTS!!!!!!");
            }
            ps = Database.getInstance().getConnection().prepareStatement("INSERT INTO user_address (user_id, door_no, street_name, city, district, pincode) VALUES (?, ?, ?, ?, ?, ?);");
            ps.setInt(1, adr.getUserId());
            ps.setInt(2, adr.getDoorNo());
            ps.setString(3, adr.getStreetName());
            ps.setString(4, adr.getCity());
            ps.setString(5, adr.getDistrict());
            ps.setInt(6, adr.getPincode());
            ps.executeUpdate();
        }             
        catch(SQLException e)
        {
            System.out.println(e);
        } 
    }
    public LinkedHashMap<Integer,ProductListModel> addProducts(String chk,String input,UserModel user)
    {
        ResultSet rs=null;
        LinkedHashMap<Integer,ProductListModel> lhm=new LinkedHashMap<>();
        try
        {
            if(chk.equals("ProductName"))
            {
                PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select p.id,p.name as product_name,c.name as category_name,u.name as vendor_name,b.name as brand_name,d.product_id,d.category_id,d.vendor_id,d.brand_id,d.specification,d.count,d.price from product_details d INNER JOIN products p on p.id=d.product_id INNER JOIN category c on c.id=d.category_id INNER JOIN brand b on b.id =d.brand_id INNER JOIN user_details u on u.id=d.vendor_id where d.count!=0 and d.status=true;");
                rs = ps.executeQuery();
                while(rs.next())
                {
                    if(rs.getString(2).toLowerCase().contains(input.toLowerCase()))
                    lhm.put(rs.getInt(1),new ProductListModel(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getInt(11),rs.getDouble(12)));
                }
            }
            else if(chk.equals("Category"))
            {
                PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select p.id,p.name as product_name,c.name as category_name,u.name as vendor_name,b.name as brand_name,d.product_id,d.category_id,d.vendor_id,d.brand_id,d.specification,d.count,d.price from product_details d INNER JOIN products p on p.id=d.product_id INNER JOIN category c on c.id=d.category_id INNER JOIN brand b on b.id =d.brand_id INNER JOIN user_details u on u.id=d.vendor_id where d.count!=0 and d.status=true;");
                rs = ps.executeQuery();
                while(rs.next())
                {
                    if(rs.getString(3).toLowerCase().equals(input.toLowerCase()))
                    lhm.put(rs.getInt(1),new ProductListModel(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getInt(11),rs.getDouble(12)));
                }
            }
            else if(chk.equals("vendor"))
            {
                PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select p.id,p.name as product_name,c.name as category_name,u.name as vendor_name,b.name as brand_name,d.product_id,d.category_id,d.vendor_id,d.brand_id,d.specification,d.count,d.price from product_details d INNER JOIN products p on p.id=d.product_id INNER JOIN category c on c.id=d.category_id INNER JOIN brand b on b.id =d.brand_id INNER JOIN user_details u on u.id=d.vendor_id where d.vendor_id=? and d.status=true;");
                ps.setInt(1,user.adr.getUserId());
                rs = ps.executeQuery();
                while(rs.next())
                {
                    lhm.put(rs.getInt(1),new ProductListModel(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getInt(11),rs.getDouble(12)));
                }
            }
            else
            {
                PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select p.id,p.name as product_name,c.name as category_name,u.name as vendor_name,b.name as brand_name,d.product_id,d.category_id,d.vendor_id,d.brand_id,d.specification,d.count,d.price from product_details d INNER JOIN products p on p.id=d.product_id INNER JOIN category c on c.id=d.category_id INNER JOIN brand b on b.id =d.brand_id INNER JOIN user_details u on u.id=d.vendor_id where d.count!=0 and d.status=true;");
                rs = ps.executeQuery();
                while(rs.next()){
                lhm.put(rs.getInt(1),new ProductListModel(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getInt(11),rs.getDouble(12)));
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return lhm;
    }
    private void productDelivery()
    {
        try
        {
            int id=0;
            double amt=0d;
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select id from order_details where delivery_date<=current_date;");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                id=rs.getInt(1);
                PreparedStatement ps1 = Database.getInstance().getConnection().prepareStatement("select payment_type from order_details where id=?;");
                ps1.setInt(1,id);
                ResultSet rs1 = ps1.executeQuery();
                if(rs1.next())
                {
                    if(rs1.getString(1).equals("COD"))
                    {
                        PreparedStatement ps3 = Database.getInstance().getConnection().prepareStatement("select amount,vendor_id from order_details where id=?;");
                        ps3.setInt(1,id);
                        ResultSet rs3 = ps1.executeQuery();
                        if(rs3.next())
                        {
                            new OrderController().updateWallet(1,(10*rs3.getInt(1))/100);
                            new OrderController().updateWallet(rs3.getInt(2),(rs3.getInt(1)-(10*rs3.getInt(1))/100));
                        }
                    }
                    PreparedStatement ps2 = Database.getInstance().getConnection().prepareStatement("update order_details set order_status='delivered' where id=?;");
                    ps2.setInt(1,id);
                    ps2.executeUpdate();
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}

