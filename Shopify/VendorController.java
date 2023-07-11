package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class VendorController
{
    ResultSet rs=null;
    public void addProduct(UserModel user)throws IOException
    {
        String productName=Validation.getInstance().isCharacter("ProductName");
        String categoryName=Validation.getInstance().isCharacter("CategoryName");
        String brandName=Validation.getInstance().isCharacter("BrandName");
        System.out.print("\n\tEnter Specification:");
        String specification=Validation.reader.readLine();
        int count=Validation.getInstance().isNumeric("Count");
        System.out.print("\n\tEnter Price:");
        double price=Shopify.sc.nextDouble();
        int pid=isExists("products",productName);
        int cid=isExists("category", categoryName);
        int bid=isExists("brand", brandName);
        try {
            int id = 0;
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("SELECT id FROM product_details WHERE product_id = ? AND category_id = ? AND vendor_id = ? AND brand_id = ? AND price = ? AND status = true");
            ps.setInt(1, pid);
            ps.setInt(2, cid);
            ps.setInt(3, user.adr.getUserId());
            ps.setInt(4, bid);
            ps.setDouble(5, price);
            rs = ps.executeQuery();
            if (rs.next())
                id = rs.getInt("id");
            if (id == 0) {
                PreparedStatement ps1 = Database.getInstance().getConnection().prepareStatement("INSERT INTO product_details (product_id, category_id, vendor_id, brand_id, specification, count, price) VALUES (?,?,?,?,?,?,?);");
                ps1.setInt(1, pid);
                ps1.setInt(2, cid);
                ps1.setInt(3, user.adr.getUserId());
                ps1.setInt(4, bid);
                ps1.setString(5, specification);
                ps1.setInt(6, count);
                ps1.setDouble(7, price);
                ps1.executeUpdate();
                System.out.println("\nPRODUCT ADDED SUCCESSFULLY\n");
            } else {
                PreparedStatement ps2 = Database.getInstance().getConnection().prepareStatement("UPDATE product_details SET count = count + ? WHERE vendor_id = ?;");
                ps2.setInt(1, count);
                ps2.setInt(2, user.adr.getUserId());
                ps2.executeUpdate();
                System.out.println("\nPRODUCT ADDED SUCCESSFULLY\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
    public int isExists(String tablename,String book)
    {
        try
        {
            rs=StatementSingleton.getStatement().executeQuery("select id from "+tablename+" where name='"+book+"';");
            if(rs.next())
                return rs.getInt("id");
            else
             {
                PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("INSERT INTO "+tablename+" (name) VALUES(?)RETURNING Id;");
                ps.setString(1,book);
                rs=ps.executeQuery();
                rs.next();
                return rs.getInt("Id");
             }
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
        return 0;     
    }
    public void removeProduct(UserModel user)throws IOException
    {
        UserContoller userc =new UserContoller();
        try
        {
            userc.viewProduct("vendor",user);
            int pid=Validation.getInstance().isNumeric("ProductId");
            while(!userc.hm.containsKey(pid))
            {
                pid=Validation.getInstance().isNumeric("Valid ProductId");
            }
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("update product_details set status=false where id=?;");
            ps.setInt(1, pid);
            ps.executeUpdate();
            System.out.println("PRODUCT REMOVED SUCCESSFULLY");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
