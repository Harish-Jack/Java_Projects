package com.zoho.retailshop;
import java.util.Scanner;
public class RetailStore
{     
       
      public static void main(String args[])throws Exception
      {

          Database.conn();
          TableCreation.tableOrder();
          InsertIntoDatabase.insertionOrder();  
          Scanner userinput=new Scanner(System.in);
          Controller control=new Controller();
          Login log=new Login();    
          try
            {
              //login page
              System.out.println("\t\t\t\tWELCOME TO MOBILE RETAILSHOP");
              System.out.println("\n\t\t\t\t  1-LOGIN(EXISTING USER)\n\t\t\t\t  2-SIGNUP(NEW USER)");
              System.out.print("\nEnter your input:");
              int choice=control.getchoice(2);
              while(choice!=1 && choice !=2)
              {
                   choice=0;
                   System.out.println("Enter valid input!!!!!Required Input(1.ExixtingUser/2.NewUser)");
                   choice=userinput.nextByte();
              }
              switch(choice)
              {
                    case 1:
                            log.existingUser();
                           break;
                      
                    case 2:
                           System.out.println("\nSIGNUP");
                           log.newUser();                                                                        
                           break;
                           
                   default:
                           System.out.println("INVALID INPUT!!!!!!!!");
                                      
              }
             }
             catch(Exception e)
                {
                   System.out.println("Give Valid Input!!!!!!!!");
                }
         }
}
