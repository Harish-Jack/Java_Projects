package com.zoho.ShopifyApp;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.LinkedHashMap;
import java.util.Map;
public class UserContoller 
{
    LinkedHashMap<Integer,ProductListModel> hm=null;
    public void viewProduct(String opt,UserModel user)throws IOException
    {
        hm=new TableInsertion().addProducts(opt,null,user);
        int c=1;
        for(Map.Entry<Integer,ProductListModel> val:hm.entrySet())
        {
            System.out.println("\n---------------------------------------------------------------------------------------------------");
            System.out.println("\n\tPRODUCT_ID:"+val.getKey()+"\t\t\tPRODUCT_NAME:"+val.getValue().getProductName().toUpperCase());
            System.out.println("\n\tCATEGORY_NAME:"+val.getValue().getCategoryName().toUpperCase()+"\t\tBRAND_NAME:"+val.getValue().getBrandName().toUpperCase());
            System.out.println("\n\tSPECIFICATION:"+val.getValue().getSpecification().toUpperCase());
            System.out.println("\n\tPRICE:"+val.getValue().getPrice());
            System.out.println("---------------------------------------------------------------------------------------------------");
            c++;
            if(c%5==0)
            {
                if("y".equals(Validation.getInstance().isCheck("DO U WANT TO MOVE NEXT PAGE(y-yes/n-no)")))
                continue;
                else
                break;
            }
        }
    }
    public int getAddressId(int uid)throws IOException
    {
        int adrsid=0;
        if("n".equals(Validation.getInstance().isCheck("y-choose from old addresses/n-Add New Address").toLowerCase()))
        {
            try
            {
                new TableInsertion().addAddress(Validation.getInstance().setAddress("Enter Address",uid));
            }
            catch(DataAlreadyExistsException e)
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("\n\t\t CHOOSE ADDRESS:");
        LinkedHashMap<Integer,AddressModel> address=chooseAddress(uid);
        for(Map.Entry<Integer,AddressModel> val:address.entrySet())
        {
            System.out.println(val.getKey()+"-"+val.getValue().getDoorNo()+" "+val.getValue().getStreetName()+","+val.getValue().getDistrict()+","+val.getValue().getCity()+"-"+val.getValue().getPincode());
        }
        adrsid=Validation.getInstance().isNumeric("Address Id");
        while(!address.containsKey(adrsid))
        {
            adrsid=Validation.getInstance().isNumeric("Valid Address Id");
        }
        return adrsid;
    }
    public LinkedHashMap<Integer,AddressModel> chooseAddress(int uid)throws IOException
    {
        LinkedHashMap<Integer,AddressModel> adrs=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("select * from user_address where user_id=?;");
            ps.setInt(1, uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                adrs.put(rs.getInt(1),new AddressModel(rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return adrs;
    }
}
