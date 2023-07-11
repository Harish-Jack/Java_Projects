package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.io.IOException;
public class VendorOrderView 
{
    public void orderMenu(UserModel user)
    {
        outer:
        while(true)
        {
            try
            {
                VendorOrderController vcontrol=new VendorOrderController();
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tSHOPIFY\t\t\t\t\t"+user.getUname()+"("+user.getRole()+")");
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.APPROVE ORDER\n\t\t\t\t\t2.APPROVE CANCEL REQUEST\n\t\t\t\t\t3.APPROVE RETURN REQUEST\n\t\t\t\t\t4.BACK");
				System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: vcontrol.approveOrder(user);
							break;
							
					case 2: vcontrol.approveCancelation(user);
							break;
					
                    case 3: vcontrol.approveReturn(user);
							break;

					
					case 4: 
							break outer;
							
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