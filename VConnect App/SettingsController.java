package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SettingsController 
{
    public void changeUserName(int uid)throws IOException
    {
        outer:
        while(true)
        {
            try
            {
                String name=Validation.getInstance().isCharacter("USERNAME");
                while("n".equals(Validation.getInstance().isCheck("y-Confrim/n-ReEnter").toLowerCase()))
                    name=Validation.getInstance().isCharacter("USERNAME");
                if(!checkUnameExists(name))
                {
                    PreparedStatement ps=Database.getInstance().getConnection().prepareStatement(" update user_details set uname=? where id=?;");
                    ps.setString(1, name);
                    ps.setInt(2, uid);
                    ps.executeUpdate();
                    System.out.println("\nUSERNAME CHANGED SUCCESSFULLY");
                    ps.close();
                    break outer;
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            catch(DataAlreadyExistsException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    public void changePassword(int uid)throws IOException
    {
        outer:
        while(true)
        {
            try
            {
                String oldpwd=Validation.getInstance().isPassword("OLD PASSWORD");
                String newpwd=Validation.getInstance().isPassword("NEW PASSWORD");
                String re_newpwd=Validation.getInstance().isPassword("RE-ENTER NEW PASSWORD");
                if(checkOldPassword(oldpwd))
                {
                    if(newpwd.equals(re_newpwd))
                    {
                        PreparedStatement ps=Database.getInstance().getConnection().prepareStatement(" update user_details set password=? where id=?;");
                        ps.setString(1, newpwd);
                        ps.setInt(2, uid);
                        ps.executeUpdate();
                        System.out.println("\nPASSWORD CHANGED SUCCESSFULLY");
                        ps.close();
                        break outer;
                    }
                    else
                     throw new PasswordNotMatchedException("NEW PASSWORD DOES NOT MATCHED");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            catch(DataNotFoundException | PasswordNotMatchedException e)
            {
                System.out.println(e.getMessage());
            }
            if("n".equals(Validation.getInstance().isCheck("y-Retry/n-Back").toLowerCase()))
            break outer;
        }
    }
    private boolean checkUnameExists(String name)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select exists(select uname from user_details where lower(uname)=lower(?)) as status;");
            ps.setString(1,name);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getBoolean("status"))
                {
                  ps.close();
                  rs.close();
                  throw new DataAlreadyExistsException("USERNAME ALREADY EXISTS");
                }
                else
                {
                  ps.close();
                  rs.close();
                  return false;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private boolean checkOldPassword(String pwd)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select exists(select password from user_details where password=?) as status;");
            ps.setString(1,pwd);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(!rs.getBoolean("status"))
                {
                  ps.close();
                  rs.close();
                  throw new DataNotFoundException("OLD PASSWORD DOES NOT EXISTS");
                }
                else
                {
                  ps.close();
                  rs.close();
                  return true;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }
    public void forgotPassword()
    {
        int uid=0;
        try
        {
            String name=Validation.getInstance().isCharacter("UserName");
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select id,email from user_details where uname=?;");
            ps.setString(1,name);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                uid=rs.getInt(1);
                name=rs.getString(2);
                System.out.print("\n\t\t");
                for(int i=0;i<name.length();i++)
                {
                    if(i>0 && i<name.length()-4)
                       System.out.print("*");
                    else
                      System.out.print(name.charAt(i));
                }
                System.out.println();
                while(true)
                {
                    if(name.equals(Validation.getInstance().isEmail("EMAIL")))
                    {
                        String newpwd=Validation.getInstance().isPassword("NEW PASSWORD");
                        String re_newpwd=Validation.getInstance().isPassword("RE-ENTER NEW PASSWORD");
                        if(newpwd.equals(re_newpwd))
                        {
                            PreparedStatement ps2=Database.getInstance().getConnection().prepareStatement(" update user_details set password=? where id=?;");
                            ps2.setString(1, newpwd);
                            ps2.setInt(2, uid);
                            ps2.executeUpdate();
                            System.out.println("\nPASSWORD CHANGED SUCCESSFULLY");
                            ps2.close();
                            break;
                        }
                       else
                        System.out.println("NEW PASSWORD DOES NOT MATCHED");
                    }
                    else
                     if("n".equals(Validation.getInstance().isCheck("y-Retry/n-Back").toLowerCase()))
                      break;
                }
            }
            else
            {
                ps.close();
                rs.close();
                throw new DataNotFoundException("\nUSERNAME NOT FOUND");
            }
            ps.close();
            rs.close();
        }
        catch(SQLException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
