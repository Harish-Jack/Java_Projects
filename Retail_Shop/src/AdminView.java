package com.zoho.retailshop;
import java.util.Scanner;
public class AdminView
{
   Scanner userinput=new Scanner(System.in);
   Controller control=new Controller();
   AdminController admincontrol=new AdminController();
   public void adminProcess(String admin,String adminname)
   {
    int choice=0;
    try
      {
       
     do{
         System.out.println("_________________________________________________________________________________\n");
         System.out.println("\t\t\tADMIN PAGE");
         System.out.println("_________________________________________________________________________________\n");
         System.out.println(admin);
         System.out.println("1.View Product");
         System.out.println("2.Add Product");
         System.out.println("3.Add Vendor");
         System.out.println("4.Add Admin");
         System.out.println("5.Remove Vendor");
         System.out.println("6.Remove Admin");
         System.out.println("7.Remove Product");
         System.out.println("8.Statement");         
         System.out.println("9.Exit");
         System.out.println("_________________________________________________________________________________\n");
         System.out.print("\nEnter your choise:");
         choice=userinput.nextInt();;
         System.out.println("_________________________________________________________________________________\n");
         switch(Choices.checkInput(choice))
                {
                   case VIEW_PRODUCT:
                                admincontrol.printProduct();
                                choice=control.moreStuff();                                                         
                                break;
                                
                   case ADD_PRODUCT:
                                admincontrol.addProduct();
                                choice=control.moreStuff();   
                                break;
                                
                   case ADD_VENDOR:
                                admincontrol.addVendor();
                                choice=control.moreStuff();                         
                                break;
                                
                   case ADD_ADMIN:
                                admincontrol.addAdmin();
                                choice=control.moreStuff();                        
                                break; 
                                
                   case REMOVE_VENDOR:
                                admincontrol.removeVendor();
                                choice=control.moreStuff();                         
                                break;
                                
                   case REMOVE_ADMIN:
                                admincontrol.removeAdmin(adminname);
                                choice=control.moreStuff();                        
                                break;   
                                
                   case REMOVE_PRODUCT:
                                admincontrol.removeProduct();
                                choice=control.moreStuff();                        
                                break;                                      
                                
                   case STATEMENT:
                                admincontrol.getStatement();
                                choice=control.moreStuff();
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
enum Choices
           {
             VIEW_PRODUCT(1),
             ADD_PRODUCT(2),
             ADD_VENDOR(3),
             ADD_ADMIN(4),
             REMOVE_VENDOR(5),
             REMOVE_ADMIN(6),
             REMOVE_PRODUCT(7),
             STATEMENT(8),
             EXIT(9),
             INVALID(10);
             
             private int val;
             private Choices(int val)
             {
                 this.val=val;
             }
             public static Choices checkInput(int value) 
             {
		for (Choices option : Choices.values()) 
		{
			if (option.val == value) 
			{
				return option;
			}
		}
		return INVALID;
	     }
	  }

