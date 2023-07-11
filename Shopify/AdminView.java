package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.io.IOException;
public class AdminView 
{
    public void adminMenu(UserModel user)
    {
        outer:
        while(true)
        {
            try
            {
                UserContoller ucontrol=new UserContoller();
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tSHOPIFY\t\t\t\t\t"+user.getUname()+"("+user.getRole()+")");
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.PROFILE\n\t\t\t\t\t2.REMOVE VENDOR\n\t\t\t\t\t3.WALLET\n\t\t\t\t\t4.LOGOUT");
				System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: System.out.println("\n\n\tUserName:"+user.getUname()+"\t\tRole:"+user.getRole());
                            System.out.println("\n\tGender:"+user.getGender()+"\t\tMobile:"+user.getMobile());
                            System.out.println("\n\tGmail:"+user.getMail()+"\t\tCreated-On:"+user.getCreatedDate());
                            System.out.println("\n\tAddress:"+user.adr.getDoorNo()+" "+user.adr.getStreetName()+","+user.adr.getDistrict()+","+user.adr.getCity()+"-"+user.adr.getPincode());
                            String ch=Validation.getInstance().isCheck("'y'-Back");
                            while(!ch.equals("y")){
                                ch=Validation.getInstance().isCheck(" Valid Input!!!!!'y'-Back");
                            }
							break;
							
					case 2: 
							break;
					
                    case 3: 
							break;

					
					case 4  : 
							StatementSingleton.close();
							Database.getInstance().closeDB();
							break outer;
							
					default:
							System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
							break;
					
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
            catch (IOException e)
            {
                System.out.println("GIVE VALID USER INPUT");
            }
        }
        
    }
}