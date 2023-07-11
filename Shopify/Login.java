package com.zoho.ShopifyApp;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Login 
{
    private ResultSet rs=null;
    public void signIn()throws IOException
    {
        long mobile=Validation.getInstance().isMobile("Mobile");
        String passwd=Validation.getInstance().isPassword("Password");
        UserModel user=validUser(mobile,passwd);
        if(user!=null)
        {
            if(user.getRoleid()==1)
            {
                new UserView().userMenu(user);
            }
            else if(user.getRoleid()==2)
            {
                new VendorView().vendorMenu(user);
            }
            else
            {
                new AdminView().adminMenu(user);
            }
        }
        else
         throw new DataNotFoundException("\nINVALID USER...TRY TO SIGNUP");
    }
    public UserModel validUser(long mobile,String passwd)
    {
        try
        {
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select u.name,u.role_id,r.role,u.gender,u.mobile,u.gmail,l.password,u.created_on,a.user_id,a.door_no,a.street_name,a.city,a.district,a.pincode from user_details u INNER JOIN user_address a on u.id=a.user_id INNER JOIN accounts l on l.user_id=u.id INNER JOIN user_role r on r.id=u.role_id where u.mobile= ? and l.password= ? and l.status=true;");
            ps.setLong(1, mobile);
            ps.setString(2,passwd);
            rs = ps.executeQuery();
            if(rs.next())
            return new UserModel(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getLong(5),rs.getString(6),rs.getString(7),rs.getDate(8),rs.getInt(9),rs.getInt(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getInt(14));
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }
}