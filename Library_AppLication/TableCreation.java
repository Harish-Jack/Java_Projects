package com.zoho.LibraryApp;
import java.sql.SQLException;
import java.sql.Statement;
class TableCreation
{
     
     public static Statement st=null;
     public void tableOrder()throws Exception
     {
         if(st==null)
         st=Database.getInstance().getConnection().createStatement();
         booksTable();
         categoryTable();
         authorTable();
         usersTable();
         bookDetails();
         barrowDetails();
         reservationDetails();
     }
     private void booksTable()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS books(id SERIAL PRIMARY KEY,name VARCHAR(50) NOT NULL)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("booksTable Not Created");
             } 
     }
     private void authorTable()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS authors(id SERIAL PRIMARY KEY,name VARCHAR(30) NOT NULL)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("authorTable Not Created");
             } 
     }
     private void categoryTable()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS category(id SERIAL PRIMARY KEY,name VARCHAR(30) NOT NULL)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("categoryTable Not Created");
             } 
     }
     private void usersTable()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS user_details(id SERIAL PRIMARY KEY,name VARCHAR(50) NOT NULL,address TEXT NOT NULL,mobile BIGINT NOT NULL,gender VARCHAR(50) NOT NULL,status BOOLEAN DEFAULT true)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("usersTable Not Created");
             } 
     }
     private void bookDetails()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS book_details(id SERIAL PRIMARY KEY,book_id INTEGER NOT NULL,author_id INTEGER NOT NULL,category_id INTEGER NOT NULL,count INTEGER NOT NULL,CONSTRAINT fk_bookid FOREIGN KEY(book_id) REFERENCES books(id),CONSTRAINT fk_authorid FOREIGN KEY(author_id) REFERENCES authors(id),CONSTRAINT fk_categoryid FOREIGN KEY(category_id) REFERENCES category(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("bookDetails Not Created");
             } 
     }
     private void barrowDetails()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS barrow_details(id SERIAL PRIMARY KEY,user_id INTEGER NOT NULL,book_id INTEGER NOT NULL,barrow_date DATE,returning_date DATE,return_status BOOLEAN DEFAULT false,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id),CONSTRAINT fk_bookid FOREIGN KEY(book_id) REFERENCES book_details(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("barrowDetails Not Created");
             } 
     }
     private void reservationDetails()
     {
           try
             {
                   st.executeUpdate("CREATE TABLE IF NOT EXISTS reservation(id SERIAL PRIMARY KEY,user_id INTEGER NOT NULL,book_id INTEGER NOT NULL,reservation_date DATE,status varchar(20),CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id),CONSTRAINT fk_bookid FOREIGN KEY(book_id) REFERENCES book_details(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("barrowDetails Not Created");
             } 
     }
}
