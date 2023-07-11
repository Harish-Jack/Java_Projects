package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Login 
{
    public void signIn()throws Exception
    {
        System.out.println("\t\t\t\tLOGIN\n");
        String uname=Validation.getInstance().isCharacter("Username");
        String password=Validation.getInstance().isPassword("Password");
        UserModel user=getUserInfo(uname,password);
        if(user!=null)
            new UserView().userMenu(user);
        else
            throw new DataNotFoundException("\nINVALID USERNAME OR PASSWORD....");
    }
    public UserModel getUserInfo(String uname,String password)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select u.id,u.uname,u.profile_name,u.email,u.gender,u.password,u.dob,u.created_on,u.privacy,p.file_name as profile_picname,p.profile from user_details u LEFT JOIN profiles p on u.id=p.user_id where u.status='t' and u.uname=? and u.password=?;");
            ps.setString(1,uname);
            ps.setString(2,password);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                return new UserModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7),rs.getDate(8),rs.getString(9),rs.getString(10),rs.getBytes(11));
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
