package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.io.IOException;
public class VendorView 
{
    public void vendorMenu(UserModel user)
    {
        outer:
        while(true)
        {
            try
            {
                VendorController vendorc=new VendorController();
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tSHOPIFY\t\t\t\t\t"+user.getUname()+"("+user.getRole()+")");
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.PROFILE\n\t\t\t\t\t2.VIEW PRODUCT\n\t\t\t\t\t3.ADD PRODUCT\n\t\t\t\t\t4.REMOVE PRODUCT\n\t\t\t\t\t5.MANAGE ORDER\n\t\t\t\t\t6.WALLET\n\t\t\t\t\t7.LOGOUT");
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
							
					case 2: new UserContoller().viewProduct("vendor",user);
							break;
					
                    case 3: vendorc.addProduct(user);
							break;
                    
                    case 4: vendorc.removeProduct(user);
							break;

                    case 5: new VendorOrderView().orderMenu(user);
							break;  
                    
                    case 6: 
							break;  
					
					case 7: 
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
