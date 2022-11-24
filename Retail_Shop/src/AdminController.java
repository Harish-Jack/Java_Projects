package com.zoho.retailshop;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.Console;  

public class AdminController extends CustomerController implements AdminPanel
{

       Controller control=new Controller();
       Console console=System.console();
       Scanner userinput = new Scanner(System.in);
       InsertIntoDatabase insert=new InsertIntoDatabase();
       int userId=0,password=0,max=0,vendorid=0;
       boolean flag=false;

       public void vendorList()
       {
              LinkedHashMap<Integer, String> vendorlist=control.getVendor();
              System.out.println("\nAvailable Vendors");
              for(Map.Entry<Integer, String> list:vendorlist.entrySet())
              {
                   System.out.println(list.getKey()+"-"+list.getValue());   
              }            
       }
       
       public void adminList(String adminname)
       {
              LinkedHashMap<Integer, String> adminlist=control.getAdminlist(adminname);
              System.out.println("\nAvailable Admins");
              for(Map.Entry<Integer, String> list:adminlist.entrySet())
              {
                   System.out.println(list.getKey()+"-"+list.getValue());   
              }       
       }
       
       public void addProduct()
       {
         flag=true;
         while(flag)  
           {
             int productid=0,productcount=0;
             max=control.getMaxid("vendor_list");
             vendorList();
             System.out.print("\nVendor Id:");
             vendorid=control.validId(max);             
             System.out.print("Product Name:");
             String productname=console.readLine();             
             System.out.print("Brand Name:");
             String brandname=console.readLine();
             flag=control.checkProduct(productname,brandname);
             if(!flag)
             { 
                productid=insert.addProduct(productname,brandname); 
             }
             else
             {
                productid=control.getProductid(productname,brandname);
             }
             System.out.print("Product Count:");
             productcount=userinput.nextInt();
             insert.addPurchase(vendorid,productid,productcount);
             if(control.checkProductid(productid))
             {  
                 control.updateStock(productid,productcount);
                 System.out.println("\nProduct Added Successfully!!!!!!!!!!!!!\n");
             }
             else
             {
                 insert.addStock(productid,productcount);
                 System.out.println("\nProduct Added Successfully!!!!!!!!!!!!!\n");
             }
             System.out.println("1.Add More\n2.Nope\n");
             System.out.print("Enter Your Choice:");
             int num=control.getchoice(2);
             if(num==1)
             {
               flag=true;
             }
             else
             {
              flag=false;
             }
             
          }                                   
       }
       
       public void addVendor()
       {
             VendorModel vendor=null;
             System.out.print("Vendor Name:");
             String vendorname=control.checkString();
             System.out.print("Vendor MobileNo:");
             long vendormob=control.returnMobileno();
             System.out.print("Vendor Location:");
             String vendorlocation=control.checkString();
             System.out.println("\nSuccessfully Vendor Account Created!!Welcome on board");   
             vendor=new VendorModel(vendorname,vendormob,vendorlocation); 
             insert.insertVendor(vendor);              
       }
       
       public void removeVendor()
       {   
             vendorList();
             System.out.println("\nWhich Vendor you want to Remove!!!?\n");
             System.out.print("Vendor Name:");
             String vendorname=control.checkString();
             max=control.getMaxid("vendor_list");
             System.out.print("Vendor id:");
             vendorid=control.validId(max);
             if(control.verifyAccount(vendorname,vendorid,"vendor_list","vendor_name"))
             {
                control.removeVendor(vendorname,vendorid); 
                System.out.println("\n"+vendorname+ " Vendor Account Deleted!!!!!!");
             }
             else
             {
                System.out.println("\nVendor not Available!!!!!!!!!!!\n");
             }
           }

       
       public void removeAdmin(String name)
       {
          flag=control.isAdminexists();
          if(flag)
           {  
             adminList(name);
             System.out.println("\nWhich Admin you want to Remove!!!?\n");
             System.out.print("Admin Name:");
             String adminname=control.checkString();
             max=control.getMaxid("admin_list");
             System.out.print("Admin id:");
             int adminid=control.validId(max);
             if(control.verifyAccount(adminname,adminid,"admin_list","admin_name"))
             {
                control.removeAdmin(adminname,adminid); 
                System.out.println("\n" +adminname+ " Admin Account Deleted!!!!!!");
             }
             else
             {
                System.out.println("\nAdmin not Available!!!!!!!!!!!\n");
             }
            }
          else
            {
              System.out.println("No admins are available to Remove!!!!!!!!!\n"); 
            } 
             
       }
       
       public void removeProduct()
       {
           flag=control.isTableExist();
           if(flag)
           {        
             printProduct();
             System.out.println("\nWhich product you want to remove!!?");
             System.out.print("\nProduct Name:");
             String prodname=console.readLine();
             System.out.print("Brand Name:");
             String brandname=console.readLine();
             flag=control.checkProduct(prodname,brandname);
             if(flag)
             {
                   control.removeProduct(prodname,brandname);  
                   System.out.println("\nProduct Deleted!!!!!!");     
             }
             else
             {
                   System.out.println("Product does not exists!!!!!!!!!!!!!!!");
             }
           }
           else
           {
                System.out.println("Products does not exists!!!!!!!!!!!\n");
           }
             
       }
       
       public void addAdmin()
       {
             AdminModel admin=null;
             System.out.print("Admin Name:");
             String adminname=control.checkString();            
             System.out.print("Admin MobileNo:");
             long adminmob=control.returnMobileno();             
             userId=control.generateUserid();
             password=control.generatePassword();
             System.out.println("\nYour userId is:"+userId);
             System.out.println("your password is:"+password); 
             int loginId=insert.adminCredentials(userId,password); 
             System.out.println("\nSuccessfully Admin Account Created!!Welcome on board");             
             admin =new AdminModel(adminname,adminmob,loginId);
             insert.insertAdmin(admin);                   
       }
       
       public void getStatement()
       {
           CustomerController cc=new CustomerController();
           int choice=0,days=0;
           do
           {
             System.out.println("1-Sale Statement");
             System.out.println("2-Purchase Statement");
             System.out.println("3-Return Statement");
             System.out.print("\nEnter Your Choice:");
             choice=userinput.nextByte();
             days=control.getDays();  
             switch(choice)
             {
                      case 1:                            
                            ArrayList<Printstatement> sale=control.saleStatement(days);
                            salePrintStatement(sale); 
                            break;
                            
                      case 2:
                            ArrayList<Printstatement> purchase=control.purchaseStatement(days);
                            purchasePrintStatement(purchase); 
                            break;
                            
                      case 3:
                            ArrayList<Printstatement> returnstatemnt=control.returnStatement(days);
                            returnPrintStatement(returnstatemnt); 
                            break;      
                          
                      default:
                            System.out.println("Invalid Input!!!!!!!!!!\n\n");

             }            
            }while(choice<1 || choice>3);
             
       }
       public void purchasePrintStatement(ArrayList<Printstatement> purchase)
       {
           byte value=1;
           System.out.println("\t\t\t\tPURCHASE STATEMENT");
           String format=String.format("%-20s %-20s %-20s %-20s %-20s","Date","Product_Name","Brand_Name","Vendor_Name","Count");
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(format);
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(" ");
           Printstatement ps=new Printstatement();
           ps.print(purchase,value);
       } 
     
       public void salePrintStatement(ArrayList<Printstatement> sale)
       {
           byte value=2;
           System.out.println("\t\t\t\tSALE STATEMENT");
           String format=String.format("%-20s %-20s %-20s %-20s %-20s","Date","Product_Name","Brand_Name","Customer_Name","Count");
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(format);
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(" ");
           Printstatement ps=new Printstatement();
           ps.print(sale,value);
       }
     
       public void returnPrintStatement(ArrayList<Printstatement> returnstmt)
       {
           byte value=3;
           System.out.println("\t\t\t\tRETURN STATEMENT");
           String format=String.format("%-20s %-20s %-20s %-20s %-20s","Date","Product_Name","Brand_Name","Customer_Name","Count");
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(format);
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(" ");
           Printstatement ps=new Printstatement();
           ps.print(returnstmt,value);
       }
}
