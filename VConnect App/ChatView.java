package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;
public class ChatView 
{
     public void chatMenu(UserModel user)
     {
        outer:
        while(true)
        {
            try
            {
                ChatController chatc=new ChatController();
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tVCONNECT\t\t\t\t\t"+user.getUserName());
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.VIEW CHAT\n\t\t\t\t\t2.SEARCH FRIEND\n\t\t\t\t\t3.DELETE CHAT\n\t\t\t\t\t4.BACK");
				System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: chatc.viewChat(user);
							break;
							
					case 2: chatc.searchFriend(user.getUserName(),user.getUserId());
							break;
					
					case 3: chatc.deleteChat(user.getUserId());
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
        }
    }
}
