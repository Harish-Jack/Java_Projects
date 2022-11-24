package com.zoho.retailshop;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
class InsertIntoDatabase
{
     private static ResultSet resultset=null;
     private static PreparedStatement ps=null;
     int custid=0,vendorid=0,adminid=0;
     public static void insertionOrder()throws Exception
     {
          InsertIntoDatabase insert=new InsertIntoDatabase();
          insert.insertDefault();
     }
     
     public static void insertDefault()
     {
          if(checkValues("information")==0)
          {
               insertInformation();
          }
          if(checkValues("login")==0)
          { 
               insertlogin();
          }
          if(checkValues("admin_list")==0)
          {
               insertAdmin();
          }
          /*if(checkValues("product_list")==0)
          {
               insertProduct();
          }*/
          if(checkValues("vendor_list")==0)
          {
               insertVendor();
          }          
     }
     
     public int putCredentials(int userId,int password)
     { 
          int loginId=0;
          try
            {
                 String putlogin="INSERT INTO login" + " (user_id, password,information_id) VALUES "+"(?,?,?)RETURNING Id;";
    	         ps=Database.con.prepareStatement(putlogin);
    	         ps.setInt(1,userId);
    	         ps.setInt(2,password);
    	         ps.setInt(3,2);
    	         resultset=ps.executeQuery();
    	         resultset.next();
    	         loginId=resultset.getInt("Id");
            }
   
        catch(SQLException e)
            {
                System.out.println("Error in putCredentials");
                e.printStackTrace();                 
            }
        return loginId;
     }

     public int adminCredentials(int userId,int password)
     { 
          int loginId=0;
          try
            {
                 String putlogin="INSERT INTO login" + " (user_id, password,information_id) VALUES "+"(?,?,?)RETURNING Id;";
    	         ps=Database.con.prepareStatement(putlogin);
    	         ps.setInt(1,userId);
    	         ps.setInt(2,password);
    	         ps.setInt(3,1);
    	         resultset=ps.executeQuery();
    	         resultset.next();
    	         loginId=resultset.getInt("Id");
            }
   
        catch(SQLException e)
            {
                System.out.println("Error in adminCredentials");
                e.printStackTrace();                 
            }
        return loginId;
     }     
     
     public void insertCustomer(CustomerModel custmodule)
     {
         try
           {
                String putcustomer="INSERT INTO customer_list" + "(customer_name,customer_mobileNo,customer_location,login_id) VALUES "+" (?,?,?,?)RETURNING Id;";               
                ps=Database.con.prepareStatement(putcustomer);
                ps.setString(1,custmodule.getCustomername());
                ps.setLong(2,custmodule.getCustomermobile());
                ps.setString(3,custmodule.getCustomerlocation());
                ps.setInt(4,custmodule.getloginid());     
                resultset=ps.executeQuery();
	        resultset.next();
	        custid=resultset.getInt("Id");    
                
           }
       catch(SQLException e)
           {
                e.printStackTrace();                 
           }
           
     }
     
     public void insertVendor(VendorModel vendor)
     {
         try
           {
                String putvendor="INSERT INTO vendor_list" + "(vendor_name,vendor_mobileNo,vendor_location) VALUES "+" (?,?,?)RETURNING Id;";               
                ps=Database.con.prepareStatement(putvendor);
                ps.setString(1,vendor.getVendorname());
                ps.setLong(2,vendor.getVendormobile());
                ps.setString(3,vendor.getVendorlocation());   
                resultset=ps.executeQuery();
	        resultset.next();
	        vendorid=resultset.getInt("Id");    
                
           }
       catch(SQLException e)
           {
                e.printStackTrace();                 
           }
           
     }
     
     public void insertAdmin(AdminModel admin)
     {
         try
           {
                String putadmin="INSERT INTO admin_list" + "(admin_name,admin_mobileNo,login_id) VALUES "+" (?,?,?)RETURNING Id;";               
                ps=Database.con.prepareStatement(putadmin);
                ps.setString(1,admin.getAdminname());
                ps.setLong(2,admin.getAdminmobile());
                ps.setInt(3,admin.getloginid());    
                resultset=ps.executeQuery();
	        resultset.next();
	        adminid=resultset.getInt("Id");    
                
           }
       catch(SQLException e)
           {
                e.printStackTrace();                 
           }
           
     }             
     
     public void insertSale(int custid, int productid,int count)
     {
        int saleid=0;
        try
          {
                String putSale="INSERT INTO sale_details" + "(date,customer_id,product_id,product_count) VALUES "+" (?,?,?,?)RETURNING Id;";               
                ps=Database.con.prepareStatement(putSale);
    	        ps.setDate(1,Date.valueOf(java.time.LocalDate.now()));    
                ps.setInt(2,custid);
                ps.setInt(3,productid); 
                ps.setInt(4,count);                       
                resultset=ps.executeQuery();
	        resultset.next();
	        saleid=resultset.getInt("Id");    
                
           }
       catch(SQLException e)
           {
                e.printStackTrace();                 
           }
           
     }  
     
     private static int checkValues(String tablename)
     {
         int count=0;
         try
           {
                 String getCount="SELECT COUNT(id) FROM "+tablename+";";
                 ps=Database.con.prepareStatement(getCount);
                 resultset=ps.executeQuery();
                 resultset.next();
                 count=resultset.getInt("count");
            
           }
         catch(SQLException e)
           {
                e.printStackTrace();
           }
        return count;
     }
     
     private static void insertInformation()
     {
        try
          {
               String Information="INSERT INTO Information (id,role) VALUES" 
               +"(1,'Admin'),"
               +"(2,'Customer');";
               ps=Database.con.prepareStatement(Information);
               ps.executeUpdate();
          }
      catch(SQLException e)
          {
               System.out.println("Insert Information error");
               e.printStackTrace();
          }
     }
     
     private static void insertlogin()
      {
         try
           {
               String login="Insert INTO login (user_id,password,information_id) VALUES (654321,1234,1);";
               ps=Database.con.prepareStatement(login);
               ps.executeUpdate();
           }
         catch(SQLException e)
           {
               System.out.println("login Information error");
               e.printStackTrace();            
           }
           
      }
      
     
     private static void insertAdmin()
     {
          try
            {
               String admin="INSERT INTO admin_list (admin_name,admin_mobileNo,login_id) VALUES ('admin',9842251585,1);";
               ps=Database.con.prepareStatement(admin);
               ps.executeUpdate();
            }
          catch(SQLException e)
            {
               e.printStackTrace(); 
            }
     }
     
    /* private void insertProduct()
     {
          try
            {
               String product="INSERT INTO product_list (product_name,brand_name) VALUES"
                               +"('display','samsung')," +"('backcase','samsung'),"
                               +"('temperglass','realme')," +"('battery','realme');";
               ps=Database.con.prepareStatement(product);
               ps.executeUpdate();
            }
          catch(SQLException e)
            {
               e.printStackTrace(); 
            } 
     }*/

     private static void insertVendor()
     {
          try
            {
               String vendor="INSERT INTO vendor_list (vendor_name,vendor_mobileNo,vendor_status,vendor_location) VALUES "
                               +"('Durai',9898767214,'true','Madurai');";
               ps=Database.con.prepareStatement(vendor);
               ps.executeUpdate();
            }
          catch(SQLException e)
            {
               e.printStackTrace(); 
            } 
     }
     
     public void addStock(int productid,int count)
     {
        try
          {
                String addstock="INSERT INTO stock" +"(product_id,total_count) VALUES "+"(?,?) RETURNING Id;";
                ps=Database.con.prepareStatement(addstock);
    	        ps.setInt(1,productid);
    	        ps.setInt(2,count);
    	        resultset=ps.executeQuery();
    	        resultset.next();
    	           	
          }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }               
     } 
     
     public void addPurchase(int vendorid,int productid,int productcount)
     {
         try
           {
               String purchase="INSERT INTO purchase_details"+" (date,vendor_id,product_id,count) VALUES "+"(?,?,?,?)RETURNING Id;";
    	       ps=Database.con.prepareStatement(purchase);  
    	       ps.setDate(1,Date.valueOf(java.time.LocalDate.now()));    
    	       ps.setInt(2,vendorid);
    	       ps.setInt(3,productid);
    	       ps.setInt(4,productcount);
    	       resultset=ps.executeQuery();
    	       resultset.next();
    	          	                
           }
        catch(SQLException e)
            {
                System.out.println("Error in purchase");
                e.printStackTrace();                 
            }
     }
     
     public int addProduct(String productname,String brandname)
     {
          int productId=0;
          try
            {
                 
                 String addproduct="INSERT INTO product_list" + " (product_name, brand_name) VALUES "+"(?,?)RETURNING Id;";
    	         ps=Database.con.prepareStatement(addproduct);
    	         ps.setString(1,productname);
    	         ps.setString(2,brandname);
    	         resultset=ps.executeQuery();
    	         resultset.next();
    	         productId=resultset.getByte("Id");
            }
        catch(SQLException e)
            {
                System.out.println("Error in addproduct");
                e.printStackTrace();                 
            }
            return productId;
     }
     
     public void insertReturn(int custid,int productid,int count)
     {
          try
            {
                 String addreturn="INSERT INTO return_details" + "(date,customer_id,product_id,product_count) VALUES" +"(?,?,?,?)RETURNING Id;";
                 ps=Database.con.prepareStatement(addreturn);
                 ps.setDate(1,Date.valueOf(java.time.LocalDate.now()));
                 ps.setInt(2,custid);
    	         ps.setInt(3,productid);
    	         ps.setInt(4,count);
    	         resultset=ps.executeQuery();
    	         resultset.next();
            }
        catch(SQLException e)
            {
                System.out.println("Error in insertReturn");
                e.printStackTrace();                 
            }            
     }                                                                                                         
                               
}













