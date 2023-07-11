package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;

import com.zoho.VConnectApp.ChatController;
import com.zoho.VConnectApp.Notification;
import com.zoho.VConnectApp.NotificationController;
public class UserView 
{
    public void userMenu(UserModel user)throws Exception
    {
        outer:
        while(true)
        {
            try
            {
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tVCONNECT\t\t\t\t\t"+user.getProfileName());
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.PROFILE\n\t\t\t\t\t2.NEWSFEED\n\t\t\t\t\t3.CHAT("+new ChatController().unreadChatCount(user.getUserId())+")\n\t\t\t\t\t4.FRIENDS\n\t\t\t\t\t5.POST\n\t\t\t\t\t6.NOTIFICATION("+new NotificationController().unreadNotificationCount(user.getUserId())+")\n\t\t\t\t\t7.SETTINGS\n\t\t\t\t\t8.LOGOUT");
				System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: System.out.println("\n\n\tUserName:"+user.getUserName()+"\t\tDOB:"+user.getDateOfBirth());
                            System.out.println("\n\n\tGMAIL:"+user.getEmail()+"\t\tGender:"+user.getGender());
                            System.out.println("\n\n\tCREATED-ON:"+user.getCreatedOn());
                            String ch=Validation.getInstance().isCheck("'y'-Back");
                            while(!ch.equals("y")){
                                ch=Validation.getInstance().isCheck(" Valid Input!!!!!'y'-Back");
                            }
							break;
							
					case 2: new NewsFeed().viewNewsFeed(user);
							break;
					
                    case 3: new ChatView().chatMenu(user);
							break;
                        
                    case 4: new FriendsView().friendMenu(user);
							break;
                        
                    case 5: new PostView().postMenu(user);
							break;

                    case 6: new Notification().viewNotification(user.getUserName(),user.getUserId());
                            break;

                    case 7: new SettingsView().settingsView(user);
                            break;
					
					case 8: 
							/*StatementSingleton.close();
							Database.getInstance().closeDB();*/
							break outer;
							
					default:
							System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
							break;
					
				}
			}
            catch (IOException e)
            {
                System.out.println("GIVE VALID USER INPUT");
            }
            catch(DataNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
