package com.zoho.retailshop;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Console; 
public class CustomerController implements CustomerPanel
{
      Scanner userinput=new Scanner(System.in);
      Console console=System.console();
      InsertIntoDatabase insert=new InsertIntoDatabase();   
      Controller control=new Controller();
      int productid=0,productcount=0;
      boolean flag=false; 
       public void printProduct()
       {
              ArrayList<String> products=control.viewProducts();
              ArrayList<String> product=control.viwProducts();
              ArrayList<String> stock=control.viewStock();
              System.out.println(String.format("%-20s|%-20s|%-20s|" ,"ProductName","BrandName","stock"));
              System.out.println("_________________________________________________________________");
              for(int i=0;i<product.size();i++)
              {
                 System.out.println(String.format("%-20s|%-20s|%-20s|",products.get(i),product.get(i),stock.get(i)));
              }
              System.out.println("__________________________________________________________________\n\n");   
       }      
      
      public void orderProducts(int custid) //for buy Product
      {
        flag=true;
        while(flag)  
          {
           printProduct();
           System.out.print("\nProduct Name:");
           String prodname=console.readLine();
           System.out.print("Brand Name:");
           String brandname=console.readLine();
           flag=control.checkProduct(prodname,brandname);
           if(flag)
           { 
                productid=control.getProductid(prodname,brandname);
                System.out.print("Count:");
                int count=userinput.nextInt();
                productcount=control.getProductcount(productid);
                if(productcount>=count)
                 {                
                  control.sellStock(productid,count);
                  insert.insertSale(custid,productid,count);
                  flag=false;   
                  System.out.println("\nProduct Purchased Successfully!!!!!!!!!!!!!!!\n");   
                 }
                else if(productcount==0)
                 {
                    System.out.println("Stock Not Available!!!!!!!!!!!!!!!!");
                 }
                else
                 {  
                    System.out.println("Limited stock only!!!!!!!!!Available stock-"+productcount);
                 } 
           }
           else
           {
                System.out.println("Product does not exist!!!!!!!!!!!!!!!!");
           }
           System.out.println("\n1.Purchase More\n2.Nope\n");
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
      
      public void saleReturn(int custid)
      {
         flag=control.isReturnExist(custid,"sale_details");
         if(flag)
         {
           ArrayList<Printstatement> printSet=control.printStatement(10,custid);
           printPurchase(printSet,custid);
          System.out.println("Return only possible for Purchased produects from last 10\n");
           System.out.print("\nProduct Name:");
           String prodname=console.readLine();
           System.out.print("Brand Name:");
           String brandname=console.readLine();
           productid=control.getProductid(prodname,brandname);
           flag=control.checkSaleProduct(productid);
           if(flag)
           { 
              flag=true;
              int count=0;
              productcount=control.getPurchasecount(productid,custid);
              if(productcount==0)
              {
                System.out.println("\nYour productcount is:"+productcount+",So Not possible to make Return!!!!!!!!!!!!!\n");
              }
              else
              {
              System.out.print("Count:");              
              while(flag)
              {
                 count=userinput.nextInt();              
                 if(productcount>=count)
                 {
                      control.updateStock(productid,count);
                      control.updatePurchase(custid,productid,count,productcount);  
                      insert.insertReturn(custid,productid,count);                    
                      System.out.println("Return Accepted!!!!THANK YOU");
                      flag=false;
                 }
                 else
                 {
                     System.out.println("Give Valid count ("+productcount+" products only you purchased!!!!!!!!!!!)");
                 }
               }
              }
             }
           else
            {
                  System.out.println("Product Return Not Available!!!!!!!!");
            }
           }
          else
           {
                 System.out.println("Statement Not Available!!!!!!!!!!!!!!!!\n");
           }
       }
      
      public void purchaseStatement(int custid) //check Customer Statement
      {
         int days=0;
         byte choice=0;
         do
          {  
           System.out.println("\nLast 1 Months====>(press 1)");
           System.out.println("Last 2 Months====>(press 2)");
           System.out.println("Last 3 Months====>(press 3)");
           System.out.print("\nEnter Your Choice:");
           choice = userinput.nextByte();
            
           switch(choice)
                {
                     case 1:
                           days=30;
                           break;
                          
                     case 2:
                           days=60;
                           break;
                           
                      case 3:
                           days=90;
                           break;
                           
                      default:
                           System.out.println("Invalid Input!!!!!!!!!!");

               }
          }while(choice<1 || choice>3);
          ArrayList<Printstatement> printpurchase=control.printStatement(days,custid);
          printPurchase(printpurchase,custid); 
                         
       }
       
       
       public void printPurchase(ArrayList<Printstatement> printpurchase,int custid)
       {
         flag=control.isReturnExist(custid,"sale_details");
         if(flag)
         {
           System.out.println("\t\t\t\t\tSALE STATEMENT");
           String format=String.format("%-20s %-20s %-20s %-20s","Date","Product_Name","Brand_Name","Product_Count");
           System.out.println("--------------------------------------------------------------------------------------");
           System.out.println(format);
           System.out.println("--------------------------------------------------------------------------------------");
           System.out.println(" ");
           for(Printstatement ps:printpurchase)
           {
              System.out.println(ps);
              System.out.println("--------------------------------------------------------------------------------------");
              System.out.println(" ");
           }
         }
       else
        {
             System.out.println("Statement Not Available!!!!!!!!!!!!!!!!\n");
        }
           
      }
     
      public void returnStatement(int custid) //check Customer Statement
      {
         int days=0;
         byte choice=0;
         do
          {  
           System.out.println("\nLast 1 Months====>(press 1)");
           System.out.println("Last 2 Months====>(press 2)");
           System.out.println("Last 3 Months====>(press 3)");
           System.out.print("\nEnter Your Choice:");
           choice = userinput.nextByte();
            
           switch(choice)
                {
                     case 1:
                           days=30;
                           break;
                          
                     case 2:
                           days=60;
                           break;
                           
                      case 3:
                           days=90;
                           break;
                           
                      default:
                           System.out.println("Invalid Input!!!!!!!!!!");

               }
          }while(choice<1 || choice>3);
          ArrayList<Printstatement> printsale=control.printSale(days,custid);
          PrintsaleStatement(printsale,custid);                          
       }   
       
       public void PrintsaleStatement(ArrayList<Printstatement> printsale,int custid)
       {
         flag=control.isReturnExist(custid,"return_details");
         if(flag)
         {       
           byte value=1;
           System.out.println("\t\t\t\tRETURN STATEMENT");
           String format=String.format("%-20s %-20s %-20s %-20s ","Date","Product_Name","Brand_Name","Count");
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(format);
           System.out.println("--------------------------------------------------------------------------------------------");
           System.out.println(" ");
           Printstatement ps=new Printstatement();
           ps.printReturn(printsale);
          }
          else
          {
                 System.out.println("Return Statement Not Available!!!!!!!!!!!!!!!!\n");
          }
        }  
     
}
