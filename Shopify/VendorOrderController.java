package com.zoho.ShopifyApp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
public class VendorOrderController 
{
    public void approveOrder(UserModel user)throws IOException
    {
        Random random = new Random();
        LinkedHashMap<Integer,VendorOrderModel> orderdetails=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("Select o.id,o.product_id,o.user_id,o.count,o.amount,o.order_date,o.payment_type,o.payment_status,u.name as username,u.mobile,u.gmail,u.gender,p.name as productname,c.name as categoryname,b.name as brandname,pd.specification,a.door_no,a.street_name,a.city,a.district,a.pincode FROM order_details o JOIN product_details pd ON o.product_id=pd.id JOIN products p ON pd.product_id=p.id JOIN category c ON pd.category_id=c.id JOIN brand b ON pd.brand_id=b.id JOIN user_details u ON o.user_id=u.id JOIN user_address a ON a.user_id=u.id WHERE u.status='true' AND o.order_status='ordered' AND pd.vendor_id=?;");
            ps.setInt(1,user.adr.getUserId());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                orderdetails.put(rs.getInt(1),new VendorOrderModel(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDouble(5),rs.getDate(6),rs.getString(7),rs.getBoolean(8),rs.getString(9),rs.getLong(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(14),rs.getString(16),rs.getInt(17),rs.getString(18),rs.getString(19),rs.getString(20),rs.getInt(21)));
            }
            if(orderdetails.size()>=1)
            {
                for(Map.Entry<Integer,VendorOrderModel> val:orderdetails.entrySet())
                {
                    System.out.println("ORDER-ID:"+val.getKey());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\tORDERED PERSON INFORMATION");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\tNAME:"+val.getValue().getUsername()+"\t\tMOBILE:"+val.getValue().getMobile());
                    System.out.println("\n\tGENDER:"+val.getValue().getGender()+"\t\tGMAIL:"+val.getValue().getGmail());
                    System.out.println("\n\tADDRESS:"+val.getValue().getDoorNo()+" "+val.getValue().getStreetName()+","+val.getValue().getDistrict()+","+val.getValue().getCity()+"-"+val.getValue().getPincode());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\tORDERED PRODUCT INFORMATION");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\tPRODUCTNAME:"+val.getValue().getProductName()+"\t\tBRANDNAME:"+val.getValue().getBrandName());
                    System.out.println("\n\tCATEGORY:"+val.getValue().getCategoryName()+"\t\tCOUNT:"+val.getValue().getCount());
                    System.out.println("\n\tAMOUNT:"+val.getValue().getAmount()+"\t\tORDER-DATE:"+val.getValue().getOrderDate());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("CONFRIM ORDER");
                    if("y".equals(Validation.getInstance().isCheck("y-Yes/n-Next")))
                    {
                        PreparedStatement ps1 = Database.getInstance().getConnection().prepareStatement("update order_details set order_status='shipped' where id=?;");
                        ps1.setInt(1,val.getKey());
                        ps1.executeUpdate();
                        PreparedStatement ps2 = Database.getInstance().getConnection().prepareStatement("update order_details set delivery_date=CURRENT_DATE+? where id=?;");
                        ps2.setInt(1,random.nextInt(3) + 3);
                        ps2.setInt(2,val.getKey());
                        ps2.executeUpdate();
                        System.out.println("\n\nORDER CONFRIMED FOR ORDER-ID:"+val.getKey());
                        if(!"y".equals(Validation.getInstance().isCheck("y-Continue/n-Exit")))
                         break;
                    }
                }
            }
            else
             throw new DataNotFoundException("\nNO ORDERS FOUND");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void approveCancelation(UserModel user)throws IOException
    {
        LinkedHashMap<Integer,VendorOrderModel> returndetails=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("Select o.id,o.product_id,o.user_id,o.count,o.amount,o.order_date,o.payment_type,o.payment_status,u.name as username,u.mobile,u.gmail,u.gender,p.name as productname,c.name as categoryname,b.name as brandname,pd.specification,a.door_no,a.street_name,a.city,a.district,a.pincode,r.return_reason,r.status,r.vendor_id FROM order_details o JOIN return_details r ON r.order_id=o.id JOIN product_details pd ON o.product_id=pd.id JOIN products p ON pd.product_id=p.id JOIN category c ON pd.category_id=c.id JOIN brand b ON pd.brand_id=b.id JOIN user_details u ON o.user_id=u.id JOIN user_address a ON a.user_id=u.id WHERE u.status='t' AND (o.order_status='ordered' or o.order_status='shipped') and r.status='processing' and o.vendor_id=?");
            ps.setInt(1,user.adr.getUserId());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                returndetails.put(rs.getInt(1),new VendorOrderModel(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDouble(5),rs.getDate(6),rs.getString(7),rs.getBoolean(8),rs.getString(9),rs.getLong(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(14),rs.getString(16),rs.getInt(17),rs.getString(18),rs.getString(19),rs.getString(20),rs.getInt(21),rs.getString(22),rs.getString(23),rs.getInt(24)));
            }
            if(returndetails.size()>=1)
            {
                for(Map.Entry<Integer,VendorOrderModel> val:returndetails.entrySet())
                {
                    System.out.println("CANCELATION PRODUCT-ID:"+val.getKey());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\tORDERED PERSON INFORMATION");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\tNAME:"+val.getValue().getUsername()+"\t\tMOBILE:"+val.getValue().getMobile());
                    System.out.println("\n\tGENDER:"+val.getValue().getGender()+"\t\tGMAIL:"+val.getValue().getGmail());
                    System.out.println("\n\tADDRESS:"+val.getValue().getDoorNo()+" "+val.getValue().getStreetName()+","+val.getValue().getDistrict()+","+val.getValue().getCity()+"-"+val.getValue().getPincode());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\tORDERED PRODUCT INFORMATION");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\tPRODUCTNAME:"+val.getValue().getProductName()+"\t\tBRANDNAME:"+val.getValue().getBrandName());
                    System.out.println("\n\tCATEGORY:"+val.getValue().getCategoryName()+"\t\tCOUNT:"+val.getValue().getCount());
                    System.out.println("\n\tAMOUNT:"+val.getValue().getAmount()+"\t\tORDER-DATE:"+val.getValue().getOrderDate());
                    System.out.println("\n\tRETURN REASON::"+val.getValue().rmod.getReturnReason());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("CONFRIM CANCELLATION");
                    if("y".equals(Validation.getInstance().isCheck("y-Yes/n-Next")))
                    {
                        if(val.getValue().getPaymentType().equals("UPI"))
                        {
                            PreparedStatement ps1 = Database.getInstance().getConnection().prepareStatement("update wallet set amount=amount-? where user_id=?;");
                            ps1.setDouble(1,(10*val.getValue().getAmount()*val.getValue().getCount())/100);
                            ps1.setInt(2,1);
                            ps1.executeUpdate();
                            PreparedStatement ps2 = Database.getInstance().getConnection().prepareStatement("update wallet set amount=amount-? where user_id=?;");
                            ps2.setDouble(1,(val.getValue().getAmount()*val.getValue().getCount())-(10*val.getValue().getAmount()*val.getValue().getCount())/100);
                            ps2.setInt(2,user.adr.getUserId());
                            ps2.executeUpdate();
                            new OrderController().updateWallet(val.getValue().getUserId(),(val.getValue().getAmount()*val.getValue().getCount()));
                        }
                        PreparedStatement ps3 = Database.getInstance().getConnection().prepareStatement("update return_details set status='accepted' where order_id=?;");
                        ps3.setInt(1,val.getKey());
                        ps3.executeUpdate();
                        PreparedStatement ps4 = Database.getInstance().getConnection().prepareStatement("update order_details set order_status='cancelled' where id=?;");
                        ps4.setInt(1,val.getKey());
                        ps4.executeUpdate();
                        PreparedStatement ps5 = Database.getInstance().getConnection().prepareStatement("update product_details set count=count+? where id=?;");
                        ps5.setInt(1,val.getValue().getCount());
                        ps5.setInt(2,val.getKey());
                        ps5.executeUpdate();
                        System.out.println("\n\nCANCELLATION APPROVED FOR ORDER-ID:"+val.getKey());
                        if(!"y".equals(Validation.getInstance().isCheck("y-Continue/n-Exit")))
                        break;
                    }
                }
            }
            else
             throw new DataNotFoundException("\nNO RECORDS FOUND");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void approveReturn(UserModel user)throws IOException
    {
        LinkedHashMap<Integer,VendorOrderModel> returndetails=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("Select o.id,o.product_id,o.user_id,o.count,o.amount,o.order_date,o.payment_type,o.payment_status,u.name as username,u.mobile,u.gmail,u.gender,p.name as productname,c.name as categoryname,b.name as brandname,pd.specification,a.door_no,a.street_name,a.city,a.district,a.pincode,r.return_reason,r.status,r.vendor_id FROM order_details o JOIN return_details r ON r.order_id=o.id JOIN product_details pd ON o.product_id=pd.id JOIN products p ON pd.product_id=p.id JOIN category c ON pd.category_id=c.id JOIN brand b ON pd.brand_id=b.id JOIN user_details u ON o.user_id=u.id JOIN user_address a ON a.user_id=u.id WHERE u.status='t' AND o.order_status='delivered' and r.status='processing' and o.vendor_id=?;");
            ps.setInt(1,user.adr.getUserId());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                returndetails.put(rs.getInt(1),new VendorOrderModel(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDouble(5),rs.getDate(6),rs.getString(7),rs.getBoolean(8),rs.getString(9),rs.getLong(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(14),rs.getString(16),rs.getInt(17),rs.getString(18),rs.getString(19),rs.getString(20),rs.getInt(21),rs.getString(22),rs.getString(23),rs.getInt(24)));
            }
            if(returndetails.size()>=1)
            {
                for(Map.Entry<Integer,VendorOrderModel> val:returndetails.entrySet())
                {
                    System.out.println("CANCELATION PRODUCT-ID:"+val.getKey());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\tORDERED PERSON INFORMATION");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\tNAME:"+val.getValue().getUsername()+"\t\tMOBILE:"+val.getValue().getMobile());
                    System.out.println("\n\tGENDER:"+val.getValue().getGender()+"\t\tGMAIL:"+val.getValue().getGmail());
                    System.out.println("\n\tADDRESS:"+val.getValue().getDoorNo()+" "+val.getValue().getStreetName()+","+val.getValue().getDistrict()+","+val.getValue().getCity()+"-"+val.getValue().getPincode());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\tORDERED PRODUCT INFORMATION");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("\tPRODUCTNAME:"+val.getValue().getProductName()+"\t\tBRANDNAME:"+val.getValue().getBrandName());
                    System.out.println("\n\tCATEGORY:"+val.getValue().getCategoryName()+"\t\tCOUNT:"+val.getValue().getCount());
                    System.out.println("\n\tAMOUNT:"+val.getValue().getAmount()+"\t\tORDER-DATE:"+val.getValue().getOrderDate());
                    System.out.println("\n\tRETURN REASON::"+val.getValue().rmod.getReturnReason());
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    System.out.println("ACCEPT RETURN:");
                    if("y".equals(Validation.getInstance().isCheck("y-Yes/n-Next")))
                    {
                        PreparedStatement ps1 = Database.getInstance().getConnection().prepareStatement("update wallet set amount=amount-? where user_id=?;");
                        ps1.setDouble(1,(10*val.getValue().getAmount()*val.getValue().getCount())/100);
                        ps1.setInt(2,1);
                        ps1.executeUpdate();
                        PreparedStatement ps2 = Database.getInstance().getConnection().prepareStatement("update wallet set amount=amount-? where user_id=?;");
                        ps2.setDouble(1,(val.getValue().getAmount()*val.getValue().getCount())-(10*val.getValue().getAmount()*val.getValue().getCount())/100);
                        ps2.setInt(2,user.adr.getUserId());
                        ps2.executeUpdate();
                        new OrderController().updateWallet(val.getValue().getUserId(),(val.getValue().getAmount()*val.getValue().getCount()));
                        PreparedStatement ps3 = Database.getInstance().getConnection().prepareStatement("update return_details set status='accepted' where order_id=?;");
                        ps3.setInt(1,val.getKey());
                        ps3.executeUpdate();
                        PreparedStatement ps4 = Database.getInstance().getConnection().prepareStatement("update order_details set order_status='cancelled' where id=?;");
                        ps4.setInt(1,val.getKey());
                        ps4.executeUpdate();
                        /*PreparedStatement ps5 = Database.getInstance().getConnection().prepareStatement("update product_details set count=count+? where id=?;");
                        ps5.setInt(1,val.getValue().getCount());
                        ps5.setInt(2,val.getKey());
                        ps5.executeUpdate();*/
                        System.out.println("\n\nRETURN APPROVED FOR ORDER-ID:"+val.getKey());
                        if(!"y".equals(Validation.getInstance().isCheck("y-Continue/n-Exit")))
                        break;
                    }
                }
            }
            else
             throw new DataNotFoundException("\nNO RECORDS FOUND");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}