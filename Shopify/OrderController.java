package com.zoho.ShopifyApp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;
public class OrderController 
{
    public void sortProduct(String columnname,UserModel user)throws IOException
    {
        int c=1,id=0;
        String input="";
        if(!columnname.equals("Random"))
         input=Validation.getInstance().isCharacter(columnname);
        LinkedHashMap<Integer,ProductListModel> hm=new TableInsertion().addProducts(columnname,input,user);
        for(Map.Entry<Integer,ProductListModel> val:hm.entrySet())
        {
            System.out.println("\n---------------------------------------------------------------------------------------------------");
            System.out.println("\n\tPRODUCT_ID:"+val.getKey()+"\t\t\tPRODUCT_NAME:"+val.getValue().getProductName().toUpperCase());
            System.out.println("\n\tCATEGORY_NAME:"+val.getValue().getCategoryName().toUpperCase()+"\t\tBRAND_NAME:"+val.getValue().getBrandName().toUpperCase());
            System.out.println("\n\tSPECIFICATION:"+val.getValue().getSpecification().toUpperCase());
            System.out.println("\n\tPRICE:"+val.getValue().getPrice());
            System.out.println("---------------------------------------------------------------------------------------------------");
            if(c%5==0 || c==hm.size())
            {
                if("y".equals(Validation.getInstance().isCheck("DO U WANT TO ORDER(y-yes/n-next page)")))
                {
                    id=Validation.getInstance().isNumeric("ProductId");
                    while(!hm.containsKey(id))
                    {
                        id=Validation.getInstance().isNumeric("Valid ProductId");
                    }
                    System.out.println("\n\tAvailable Stock:"+hm.get(id).getCount());
                    int count=Validation.getInstance().isNumeric("Count");
                    while(count<=0 || (count>(int)hm.get(id).getCount()))
                    {
                        count=Validation.getInstance().isNumeric("Valid Count");
                    }
                    choosePaymentType(id,count,hm,user.adr.getUserId());
                    break;
                }
                else
                 continue;
            }
            c++;
        }
    }
    private void choosePaymentType(int id,int count, LinkedHashMap<Integer,ProductListModel> hm,int uid)throws IOException
    {
        int adrsid=0;
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("\n\tSELECT PAYMENT TYPE");
        System.out.println("\t\t\t\t\t1.UPI PAYMENT\n\t\t\t\t\t2.CASH ON DELIVERY\n\t\t\t\t\t3.BACK");
        System.out.println("---------------------------------------------------------------------------------------------------");
        int num=Validation.getInstance().isNumeric("Your Choice");
        while(num>4 || num<1)
        {
            num=Validation.getInstance().isNumeric("Valid Choice");
        }
        switch(num)
        {
            case 1: String upi=Validation.getInstance().isUPI("UPI");
                    try
                    {
                        adrsid=new UserContoller().getAddressId(uid);
                        LinkedHashMap<Integer,AddressModel> address=new UserContoller().chooseAddress(uid);
                        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                        PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("insert into order_details (user_id,product_id,vendor_id,count,amount,order_date,payment_type,payment_status,order_status,address_id) values (?,?,?,?,?,?,?,?,?,?) RETURNING Id;");
                        ps.setInt(1, uid);
                        ps.setInt(2, id);
                        ps.setInt(3,hm.get(id).getVendorId());
                        ps.setInt(4, count);
                        ps.setDouble(5,hm.get(id).getPrice()*count);
                        ps.setDate(6, currentDate);
                        ps.setString(7,"UPI");
                        ps.setBoolean(8,true);
                        ps.setString(9,"ordered");
                        ps.setInt(10,adrsid);
                        ResultSet rs=ps.executeQuery();
                        if(rs.next())
                        {
                            updateCount(id,count,rs.getInt("Id"));
                            //System.out.println((double)((10*hm.get(id).getPrice()*count)/100)+" \n"+hm.get(id).getVendorId()+"\t"+(double)((hm.get(id).getPrice()*count)-((10*hm.get(id).getPrice()*count)/100)));
                            updateWallet(1,(double)((10*hm.get(id).getPrice()*count)/100));
                            updateWallet(hm.get(id).getVendorId(),(double)((hm.get(id).getPrice()*count)-((10*hm.get(id).getPrice()*count)/100)));
                        }
                    }
                    catch(SQLException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            case 2: 
                    try
                    {
                        adrsid=new UserContoller().getAddressId(uid);
                        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                        PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("insert into order_details (user_id,product_id,vendor_id,count,amount,order_date,payment_type,payment_status,order_status,address_id) values (?,?,?,?,?,?,?,?,?,?) RETURNING Id;");
                        ps.setInt(1, uid);
                        ps.setInt(2, id);
                        ps.setInt(3,hm.get(id).getVendorId());
                        ps.setInt(4, count);
                        ps.setDouble(5,hm.get(id).getPrice()*count);
                        ps.setDate(6, currentDate);
                        ps.setString(7,"COD");
                        ps.setBoolean(8,false);
                        ps.setString(9,"ordered");
                        ps.setInt(10,adrsid);
                        ResultSet rs=ps.executeQuery();
                        if(rs.next())
                            updateCount(id,count,rs.getInt("Id"));
                    }
                    catch(SQLException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            
            case 3: return;
                    
            default:
                    System.out.println("Give Valid Input!!!!!!!!!");
                    break;
            
        }
    }
    private void updateCount(int pid,int count,int oid)
    {
        try
        {
            PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("update product_details set count=count-? where id=?;");
            ps.setInt(1, count);
            ps.setInt(2, pid);
            ps.executeUpdate();
            System.out.println("\nPRODUCT ORDERED SUCCESFULLY\nORDER ID:"+oid);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void updateWallet(int id,double amt)
    {
        int c=-1;
        try
        {
            PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("select id from wallet where user_id=?;");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                PreparedStatement ps1 =Database.getInstance().getConnection().prepareStatement("update wallet set amount=amount+? where user_id=?;");
                ps1.setDouble(1,amt);
                ps1.setInt(2,id);
                ps1.executeUpdate();
            }
            else
            {
                PreparedStatement ps1 =Database.getInstance().getConnection().prepareStatement("insert into wallet (user_id,amount) values (?,?);");
                ps1.setInt(1,id);
                ps1.setDouble(2,amt);
                ps1.executeUpdate();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public LinkedHashMap<Integer,OrderModel> getOrderHistory(int uid)
    {
        LinkedHashMap<Integer,OrderModel> hm=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("select o.id,p.name as productname,o.amount,o.order_date,o.count,o.order_status,o.payment_type,o.payment_status,o.vendor_id,o.delivery_date from order_details o INNER JOIN products p on p.id=o.product_id where user_id=?;");
            ps.setInt(1,uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                hm.put(rs.getInt(1),new OrderModel(rs.getString(2),rs.getDouble(3),rs.getDate(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getBoolean(8),rs.getInt(9),rs.getDate(10)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return hm;
    }
    public void manageOrder(LinkedHashMap<Integer,OrderModel> hm,String chk)throws IOException
    {
        LinkedHashMap<Integer,OrderModel> products=new LinkedHashMap<>();
        if(chk.equals("cancelation"))
        {
            for(Map.Entry<Integer,OrderModel> val:hm.entrySet())
            {
                if(val.getValue().getOrderStatus().equals("ordered") || val.getValue().getOrderStatus().equals("shipped"))
                {
                    products.put(val.getKey(),val.getValue());
                }
            }
        }
        else
        {
            for(Map.Entry<Integer,OrderModel> val:hm.entrySet())
            {
                if(val.getValue().getOrderStatus().equals("delivered"))
                {
                    products.put(val.getKey(),val.getValue());
                }
            }
        }
        if(products.size()>0)
        {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(String.format("\n\t\t%-20s|%-48s|%-20s|%-20s|%-20s|%-20s|","ID","PRODUCT_NAME","AMOUNT","ORDER_DATE","COUNT","ORDER_STATUS"));
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Map.Entry<Integer,OrderModel> val:products.entrySet())
            {
                System.out.println(String.format("\t\t%-20s|%-48s|%-20s|%-20s|%-20s|%-20s|",val.getKey(),val.getValue().getProductName(),val.getValue().getAmount(),val.getValue().getOrderDate(),val.getValue().getCount(),val.getValue().getOrderStatus()));
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            int pid=Validation.getInstance().isNumeric("ProductId");
            while(!products.containsKey(pid))
            {
                pid=Validation.getInstance().isNumeric("Valid ProductId");
            }
            try
            {
                if(chk.equals("returning"))
                {
                    PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("SELECT (delivery_date + INTERVAL '10 days') > CURRENT_DATE AS status from order_details where id=?;");
                    ps.setInt(1,pid);
                    ResultSet rs1=ps.executeQuery();
                    if(!rs1.getBoolean("status"))
                      throw new DataNotFoundException("RETURN DATE EXPRIED");
                }
                PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select id from return_details where order_id=?;");
                ps.setInt(1,pid);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                {
                    throw new DataNotFoundException("ALREADY REQUEST IN BENDING");
                }
                else
                {
                    System.out.println("\n\t\tPRODUCT_NAME:"+products.get(pid).getProductName()+"\n");
                    if(Validation.getInstance().isCheck("y-confrim/n-nope").equals("y"))
                    {
                        System.out.print("\n\tEnter Reason:");
                        String reason=Validation.reader.readLine();
                        try
                        {
                            PreparedStatement ps1 =Database.getInstance().getConnection().prepareStatement("insert into return_details (order_id,vendor_id,return_reason,status) values (?,?,?,?);");
                            ps1.setInt(1,pid);
                            ps1.setInt(2,hm.get(pid).getVendorid());
                            ps1.setString(3,reason);
                            ps1.setString(4,"processing");
                            ps1.executeUpdate();
                            System.out.println("\nRETURN REQUEST PROCESSING......\n");
                        }
                        catch(SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
         throw new DataNotFoundException(chk.toUpperCase()+" NOT AVAILABLE");
    }
}
