package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;

import com.zoho.VConnectApp.SettingsController;
public class VConnect 
{
    public static void main(String ar[])throws Exception
    {
         new TableCreation().tableOrder();
         //new TableInsertion().insertOrder();
         boolean flag=true;
         while(flag)
         {
			try
			{
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tVCONNECT");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\t1.LOGIN\n\t\t\t\t\t2.SIGN UP\n\t\t\t\t\t3.FOR-GOT PASSWORD\n\t\t\t\t\t4.EXIT");
				System.out.println("---------------------------------------------------------------------------------------------------");
				int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: new Login().signIn();
							break;
							
					case 2: new SignUp().newUser();
							break;
					
					case 3: new SettingsController().forgotPassword();
							break;
					
					case 4: flag=false;
							StatementSingleton.close();
							Database.getInstance().closeDB();
							break;
							
					default:
							System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
							break;
					
				}
			}
			catch(IOException e)
			{
				System.out.println("\nGIVE VALID INPUT");
			}
			catch(SQLException e)
			{
				System.out.println("\nUSER-NAME ALREADY EXISTS");
			}
			catch(DataNotFoundException e)
			{
				System.out.println(e.getMessage());
			}
        }
    }
}
