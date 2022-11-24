package com.zoho.retailshop;
import java.util.Scanner;
public class CustomerView
{
   Scanner userinput=new Scanner(System.in);
   Controller control=new Controller();
   public void customerProcess(String customer,int custid)
   {
    CustomerController customercontrol=new CustomerController();
    int choice=0;
    try
      {
       
     do{
         System.out.println("_________________________________________________________________________________\n");
         System.out.println("\t\t\tCUSTOMER PAGE");
         System.out.println("_________________________________________________________________________________\n");
         System.out.println(customer);
         System.out.println("1.Order Products");
         System.out.println("2.Sale Return");       
         System.out.println("3.Statement");
         System.out.println("4.Exit");
         System.out.print("\nEnter your choise:");
         choice=userinput.nextInt();
         System.out.println("_________________________________________________________________________________");
         switch(Choicess.checkInput(choice))
                {
                   case ORDER_PRODUCTS:
                                customercontrol.orderProducts(custid);
                                choice=control.moreStuff(); 
                                break;
                                
                   case SALE_RETURN:
                                customercontrol.saleReturn(custid);
                                choice=control.moreStuff(); 
                                break;             
                                
                   case STATEMENT:
                                System.out.println("\n1.Purchase Statement\n2.Return Statement\n");
                                System.out.print("Enter your choice:");
                                choice=control.getchoice(2);
                                if(choice==1)
                                {
                                   customercontrol.purchaseStatement(custid);
                                   choice=control.moreStuff(); 
                                }
                                else if(choice==2)
                                {
                                   customercontrol.returnStatement(custid);
                                   choice=control.moreStuff();
                                }
                                break;
                                
                   case EXIT:
                              System.out.println("Are you sure (Yes-y/No-n) : ");
                              if (userinput.next().equalsIgnoreCase("y")) 
	                       {
	                        if(Database.con!=null||Database.st!=null)
	                        {
	                         Database.con.close();
	                         Database.st.close();
	                        }
	                         userinput.close();
		                 choice = -1;
                               }
                               break;
                               
                   default:
                              System.out.println("INVALID INPUT!!!!!!");

               }
          }while(choice!=-1);            
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
   }   
}
enum Choicess
           {
             ORDER_PRODUCTS(1),
             SALE_RETURN(2),
             STATEMENT(3),
             EXIT(4),
             INVALID(5);
             
             private int val;
             private Choicess(int val)
             {
                 this.val=val;
             }
             public static Choicess checkInput(int value) 
             {
		for (Choicess option : Choicess.values()) 
		{
			if (option.val == value) 
			{
				return option;
			}
		}
		return INVALID;
	     }
	  }
            
            
            
            
            
            
            
            
            
            
            
