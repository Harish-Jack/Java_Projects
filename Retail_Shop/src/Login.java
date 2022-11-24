package com.zoho.retailshop;
import java.util.Scanner;
import java.sql.SQLException;
public class Login
{
    Scanner userinput=new Scanner(System.in);
    Controller control=new Controller();
    String customername=null;
    public void existingUser()throws Exception//Existing user Login 
    {
           try
            {
              CustomerView cv=new CustomerView();
              AdminView av=new AdminView();    
              int uName=0,password=0;
              System.out.println("\nLOGIN");
              System.out.print("UserId:");
              uName=control.getUserid();
              System.out.print("Pasword:");
              password=control.getPass();
              int flag =control.authenticate(uName,password);//check is valid credentials
              if(flag!=0)
              {
                  String admin="Admin";
                  String customer="Customer";
                  String role=control.getRole(uName,password);//Get Customer role
                  if(customer.equals(role))
                  {
                    customername=control.getCustname(uName,password);//Get Customer name
                    int custid=control.getCustid(uName,password);//get Customer id
                    String cust="WELCOME "+role+"-"+customername;
                    cv.customerProcess(cust,custid);
                  }
                  else if(admin.equals(role))
                  { 
                    String adminname=control.getAdminname(uName,password);
                    String ad="WELCOME "+role+"-"+adminname;
                    av.adminProcess(ad,adminname);
                  } 
             }
             else
              {
                  System.out.println("UserId or Password Incorrect!!!!!!!Give Valid Input");
                  existingUser();
                                   
              }
            }
          catch(Exception e)
            {
                  e.printStackTrace();
            } 
    }
    public void newUser()throws SQLException// New user Signup
    { 
         try
           {
             CustomerModel custmodule=null;
             int userId,password;
             InsertIntoDatabase insert=new InsertIntoDatabase();
             System.out.print("Customer Name:");
             String custname=control.checkString();
             System.out.print("Customer MobileNo:");
             long custmob=control.returnMobileno();          
             System.out.print("Customer Location:");
             String custlocation=control.checkString();           
             userId=control.generateUserid();
             password=control.generatePassword();
             System.out.println("\nYour userId is:"+userId);
             System.out.println("your password is:"+password); 
             int loginId=insert.putCredentials(userId,password);
             System.out.println("\nSuccessfully Customer Account Created!!Welcome on board");             
             custmodule=new CustomerModel(custname,custmob,custlocation,loginId);
             insert.insertCustomer(custmodule);//insert data into Customer model 
             System.out.println("do you want to Login (yes-y/no-n) : ");
             if (userinput.next().equalsIgnoreCase("y")) 
	      {
                   existingUser();
              }
           else
              {
                 customername=control.getCustname(userId,password);
                 Database.con.close();
                 userinput.close();
                 System.out.println("\nDear "+customername+", Thanks for supporting my shop!");
              }
            }
           catch(Exception e)
           {
                 e.printStackTrace();
           }
    }
}
