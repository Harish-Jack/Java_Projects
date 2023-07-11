package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Notification 
{
    public void viewNotification(String name,int uid)
    {
        outer:
        while(true)
        {
            try
            {
                NotificationController ncontrol=new NotificationController();
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tVCONNECT\t\t\t\t\t"+name);
                System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.UNREAD NOTIFICATION\n\t\t\t\t\t2.ALL NOTIFICATION\n\t\t\t\t\t3.BACK");
                System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
                switch(num)
                {
                    case 1: ncontrol.viewNotification(uid,true);
                            break;
                            
                    case 2: ncontrol.viewNotification(uid,false);
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
