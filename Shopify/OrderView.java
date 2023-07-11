package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.IOException;
public class OrderView 
{
    OrderController ocontrol=new OrderController();
    public void orderMenu(UserModel user)//throws IOException
    {
        outer:
        while(true)
        {
            try
            {
                System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tSHOPIFY\t\t\t\t\t"+user.getUname()+"("+user.getRole()+")");
				System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.SEARCH\n\t\t\t\t\t2.RANDOM ORDER\n\t\t\t\t\t3.BACK");
				System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
				switch(num)
				{
					case 1: System.out.println("\n\t\t1.SEARCH BY PRODUCTNAME\n\t\t2.SEARCH BY CATEGORY");
                            num=Validation.getInstance().isNumeric("Your Choice");
                            while(num>2 || num<1)
                            {
                                num=Validation.getInstance().isNumeric("Valid Choice");
                            }
                            if(num==1)
                                ocontrol.sortProduct("ProductName",user);
                            else
                                ocontrol.sortProduct("Category",user);
							break;
							
					case 2: ocontrol.sortProduct("Random",user);
							break;
					case 3: 
							break outer;
							
					default:
							System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
							break;
					
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
        }
        
    }
    public void manageOrder(UserModel user)throws IOException
    {
        while(true)
        {
            try
            {
                System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("\n\t\t\t\t\t1.ORDER HISTORY\n\t\t\t\t\t2.CANCEL ORDER\n\t\t\t\t\t3.RETURN PRODUCT\n\t\t\t\t\t4.BACK");
                System.out.println("---------------------------------------------------------------------------------------------------");
                int num=Validation.getInstance().isNumeric("Your Choice");
                LinkedHashMap<Integer,OrderModel> orders=ocontrol.getOrderHistory(user.adr.getUserId());
                switch(num)
                {
                    case 1: System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println(String.format("\t\t%-20s|%-48s|%-20s|%-20s|%-20s|%-20s|%-20s|","ID","PRODUCT_NAME","AMOUNT","ORDER_DATE","DELIVERY_DATE","COUNT","ORDER_STATUS"));
                            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            for(Map.Entry<Integer,OrderModel> val:orders.entrySet())
                            {
                                System.out.println(String.format("\t\t%-20s|%-48s|%-20s|%-20s|%-20s|%-20s|%-20s|",val.getKey(),val.getValue().getProductName(),val.getValue().getAmount(),val.getValue().getOrderDate(),val.getValue().getDeliveryDate(),val.getValue().getCount(),val.getValue().getOrderStatus()));
                            }
                            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            break;
                    case 2: ocontrol.manageOrder(orders,"cancelation");
                            break;
                    
                    case 3: ocontrol.manageOrder(orders,"returning");
                            break;

                    case 4: return;
                            
                    default:
                            System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
                            break;
                    
                }
            }
            catch(DataNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}