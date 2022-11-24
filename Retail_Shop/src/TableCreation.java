package com.zoho.retailshop;
import java.sql.SQLException;
class TableCreation
{
     public static void tableOrder()throws Exception
     {
         //createDB();
         infoTable();
         loginTable();
         adminList();
         customerList();
         productList();
         vendorList();
         purchaseTable();
         saleTable();
         returnTable(); 
         stock();
     }
   /*  public static void createDB()
     {
           try
             {      byte count=0;
                    String checkdb="SELECT COUNT(datname) as \"count\" FROM pg_database WHERE datname='retailshop';";
                    String createdb="CREATE DATABASE retailshop;";
                    Database.ps=Database.con.prepareStatement(checkdb);
                    Database.rs=Database.ps.executeQuery();
                    Database.rs.next();
                    count=Database.rs.getByte("count");

                    if(count==0)
                    {
                         Database.st=Database.con.createStatement();
                         Database.st.executeUpdate(createdb);
                    }
             }
         catch(Exception e)
             {
                   System.out.println(e);
             }              
     }*/
     public static void infoTable()
     {
           try
             {
                   String information="CREATE TABLE IF NOT EXISTS information(id SERIAL PRIMARY KEY,"
                           +"role VARCHAR(20) NOT NULL)";
                   Database.st.executeUpdate(information);
                                      
             }
             
         catch(SQLException e)
             {
                  // e.printStackTrace();
                   System.out.println("infoTable Not Created");
             } 
     }
     public static void loginTable()
     {
           try
             {
                   String login="CREATE TABLE IF NOT EXISTS login(" +"id SERIAL PRIMARY KEY,"
                                +"user_id INTEGER NOT NULL,"+"password INTEGER NOT NULL,"+"information_id INTEGER NOT NULL,"
                                +"CONSTRAINT fk_information FOREIGN KEY(information_id) REFERENCES information(id)on delete cascade)";
                   Database.st.executeUpdate(login);
             }
             
        catch(SQLException e)
           {
                   System.out.println("login Table Not Created");
                   e.printStackTrace();
                   
           }     
     }
     public static void adminList()   
     {
         try
           {
                 String admin="CREATE TABLE IF NOT EXISTS admin_list(" +"id SERIAL PRIMARY KEY,"
                               +"admin_name VARCHAR(30) NOT NULL,"+"admin_mobileNo BIGINT NOT NULL,"
                               +"admin_status BOOLEAN DEFAULT 'true',"+"login_id INTEGER NOT NULL,"
                               +"CONSTRAINT fk_loginid FOREIGN KEY(login_id) REFERENCES login(id) on delete cascade)";
                               Database.st.executeUpdate(admin);                                                                 
           }
      catch(SQLException e)
           {
                  System.out.println("admin Table Not Created");
                  e.printStackTrace();                               
           }
     }            
     public static void vendorList()   
     {
         try
           {
                 String vendor="CREATE TABLE IF NOT EXISTS vendor_list(" +"id SERIAL PRIMARY KEY,"
                               +"vendor_name VARCHAR(30) NOT NULL,"+"vendor_mobileNo BIGINT NOT NULL,"
                               +"vendor_status BOOLEAN DEFAULT 'true',"+"vendor_location VARCHAR(50) NOT NULL)";
                               Database.st.executeUpdate(vendor);                                                                 
           }
      catch(SQLException e)
           {
                  System.out.println("vendor Table Not Created");                               
           }
     }       
     public static void customerList()
     {
           try
             {
                  String customer="CREATE TABLE IF NOT EXISTS customer_list(" +"id SERIAL PRIMARY KEY,"
                                +"customer_name VARCHAR(30) NOT NULL,"+"customer_mobileNo BIGINT NOT NULL,"
                                +"customer_location VARCHAR(50),"+"login_id INTEGER NOT NULL,"                        
                                +"customer_status BOOLEAN DEFAULT 'true',"+"CONSTRAINT fk_login FOREIGN KEY(login_id) REFERENCES login(id)on delete cascade)";
                                Database.st.executeUpdate(customer);
             }
         catch(SQLException e)
            {
                  System.out.println("customer Table Not Created");
            } 
     }
     /*public static void brandList()
     {
          try
            {
                 String brand="CREATE TABLE IF NOT EXISTS brand_list(" +"id SERIAL PRIMARY KEY,"
                               +"brand_name VARCHAR(40) NOT NULL,"+"brand_status BOOLEAN DEFAULT 'true')";
                               Database.st.executeUpdate(brand);   
            }
        catch(SQLException e)
           {
                  System.out.println("brand Table Not Created");                  
           }
     }*/
     public static void productList()
     {
          try
            {
                  String product="CREATE TABLE IF NOT EXISTS product_list(" +"id SERIAL PRIMARY KEY,"
                               +"product_name VARCHAR(40) NOT NULL,"+"brand_name VARCHAR(40) NOT NULL,"
                               +"product_status BOOLEAN DEFAULT 'true')";
                               Database.st.executeUpdate(product);                                  
            }
        catch(SQLException e)
            {
                  System.out.println("product Table Not Created");                               
            }
     }
     public static void purchaseTable()
     {
         try
           {
                String purchase="CREATE TABLE IF NOT EXISTS purchase_details(" +"id SERIAL PRIMARY KEY,"
                               +"date timestamp NOT NULL,"+"vendor_id INTEGER NOT NULL,"
                               +"product_id INTEGER NOT NULL,"+"count INTEGER NOT NULL,"
                               +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id),"
                               +"CONSTRAINT fk_vendor_id FOREIGN KEY(vendor_id) REFERENCES vendor_list(id)on delete cascade)";
                               Database.st.executeUpdate(purchase);                                                                 
           }
      catch(SQLException e)
           {
                  System.out.println("purchase Table Not Created");                              
           }           
     }
     public static void saleTable()   
     {
         try
           {
                String sale="CREATE TABLE IF NOT EXISTS sale_details("+ "id SERIAL PRIMARY KEY,"
                                   +"date timestamp NOT NULL,"+"customer_id INTEGER NOT NULL,"
                                   +"product_id INTEGER NOT NULL,"+"product_count INTEGER NOT NULL,"+"return_availability BOOLEAN DEFAULT 'true',"
                                   +"CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customer_list(id),"
                                   +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id)on delete cascade)";
                                   Database.st.executeUpdate(sale);                                                                                                   
           }
      catch(SQLException e)
           {
                  System.out.println("saleTable Table Not Created");                               
           }               
     }
     
     public static void returnTable()
     {
         try
           {
                String returntable="CREATE TABLE IF NOT EXISTS return_details("+ "id SERIAL PRIMARY KEY,"
                                   +"date timestamp NOT NULL,"+"customer_id INTEGER NOT NULL,"
                                   +"product_id INTEGER NOT NULL,"+"product_count INTEGER NOT NULL,"
                                   +"CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customer_list(id),"
                                   +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id)on delete cascade)";
                                   Database.st.executeUpdate(returntable);                                                                                                   
           }
      catch(SQLException e)
           {
                  System.out.println("returnTable Table Not Created");                               
           }               
     }
          
     
     public static void stock()
     {
         try
           {
                String stock="CREATE TABLE IF NOT EXISTS stock("+ "id SERIAL PRIMARY KEY,"
                             +"product_id INTEGER NOT NULL,"+"total_count INTEGER NOT NULL,"
                             +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id)on delete cascade)";
                              Database.st.executeUpdate(stock);                                                                                                   
           }
      catch(SQLException e)
           {
                  System.out.println("stack Table Not Created");  
                  e.printStackTrace();                             
           }           
     }
}  
