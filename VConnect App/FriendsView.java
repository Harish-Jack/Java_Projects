package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;
public class FriendsView 
{
    public void friendMenu(UserModel user)throws IOException
     {
        outer:
        while(true)
        {
            try
            {
				FriendsController fcontrol=new FriendsController();
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tVCONNECT\t\t\t\t\t"+user.getUserName());
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.FIND-FRIENDS\n\t\t\t\t\t2.FRIEND-REQUESTS("+fcontrol.reqCount(user.getUserId())+")\n\t\t\t\t\t3.FRIENDS("+fcontrol.frndCount(user.getUserId())+")\n\t\t\t\t\t4.SEARCH FRIEND\n\t\t\t\t\t5.BACK");
				System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: fcontrol.findFriend(user,"find");
							break;
							
					case 2: fcontrol.manageRequest(user.getUserId());
							break;
					
                    case 3: fcontrol.friendList(user.getUserId());
							break;

					case 4: fcontrol.findFriend(user,"search");
							break;
					
					case 5: break outer;
							
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
