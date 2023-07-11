package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import com.zoho.VConnectApp.PostController;
public class PostView 
{
    public void postMenu(UserModel user)throws Exception
    {
       outer:
       while(true)
       {
           try
           {
               PostController pcontrol=new PostController();
               System.out.println("---------------------------------------------------------------------------------------------------");
               System.out.println("\n\t\t\t\t\tVCONNECT\t\t\t\t\t"+user.getUserName());
               System.out.println("---------------------------------------------------------------------------------------------------");
               System.out.println("\n\t\t\t\t\t1.UPLOAD POST\n\t\t\t\t\t2.DELETE POST\n\t\t\t\t\t3.CHANGE POST PRIVACY\n\t\t\t\t\t4.BACK");
               System.out.println("---------------------------------------------------------------------------------------------------");
               int num=Validation.getInstance().isNumeric("Your Choice");
               switch(num)
               {
                   case 1: pcontrol.addPost(user.getUserId());
                           break;
                           
                   case 2: pcontrol.deletePost(user.getUserId());
                           break;
                   
                   case 3: pcontrol.changePrivacy(user.getUserId());
                           break;

                   
                   case 4: break outer;
                           
                   default:
                           System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
                           break;
                   
               }
           }
           catch(IOException e)
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
