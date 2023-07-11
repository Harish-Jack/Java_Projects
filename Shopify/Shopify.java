package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.util.Scanner;
public class Shopify
{
    static Scanner sc=new Scanner(System.in);
    public static void main(String ar[])throws Exception
    {
         new TableCreation().tableOrder();
         new TableInsertion().insertOrder();
         boolean flag=true;
         while(flag)
         {
			try
			{
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tSHOPIFY(Indias Fast E-Shopping Platform)");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\t1.LOGIN\n\t\t\t\t\t2.SIGN UP\n\t\t\t\t\t3.EXIT");
				System.out.println("---------------------------------------------------------------------------------------------------");
				int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: new Login().signIn();
							break;
							
					case 2: new SignUp().newUser();
							break;
							
					
					case 3: flag=false;
							StatementSingleton.close();
							Database.getInstance().closeDB();
							break;
							
					default:
							System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
							break;
					
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
            catch(DataNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}