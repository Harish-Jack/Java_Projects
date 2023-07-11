package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;
public class SettingsView 
{
      SettingsController scontrol=new SettingsController();
      public void settingsView(UserModel user)
      {
            outer:
            while(true)
            {
                  try
                  {
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        System.out.println("\n\t\t\t\t\tVCONNECT\t\t\t\t\t"+user.getUserName());
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        System.out.println("\n\t\t\t\t\t1.CHANGE USERNAME\n\t\t\t\t\t2.CHANGE PASSWORD\n\t\t\t\t\t3.BACK");
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        int num=Validation.getInstance().isNumeric("Your Choice");
                        switch(num)
                        {
                              case 1: scontrol.changeUserName(user.getUserId());
                                      break;
                                          
                              case 2: scontrol.changePassword(user.getUserId());
                                      break;
                              
                              case 3: break outer;
                                          
                              default:
                                       System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
                                       break;
                        }
                  }
                  catch(IOException e)
                  {
                        System.out.println(e);
                  }
            }
      }
}
