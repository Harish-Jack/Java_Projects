package com.zoho.retailshop;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
public class Database
{
   static Connection con=null;
   static Statement st=null;
   
   private Database()
   {
    
   }
   public static void conn()
   {
     try
     {
       checkConnection();
       st=Database.con.createStatement();
     }
     catch(Exception e)
     {
       System.out.println("Connection not established");
     }
   }
   private static Connection checkConnection()throws SQLException,Exception
      {
       if(con==null)
        {
           DBConnection();
        }
       return con;
      }
  public static Connection DBConnection()throws Exception
    {
      String url="jdbc:postgresql://localhost:5432/retailshop";
      String user="postgres";
      String password="1234";
      Class.forName("org.postgresql.Driver");
      try
       {
         con=DriverManager.getConnection(url,user,password);
       }
      catch(SQLException e)
       {
         e.printStackTrace();
       }
       return con;
    }
}
