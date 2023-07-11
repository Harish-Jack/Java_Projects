package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class FriendsController 
{
    public void findFriend(UserModel user,String str)throws IOException
    {
        try
        {
            PreparedStatement ps=null;
            if(str.equals("find"))
            {
                ps=Database.getInstance().getConnection().prepareStatement("select u.id,u.uname from user_details u left join friends f on f.user_id=u.id where u.id NOT IN(select friend_id from friends where user_id=? or friend_id=?) and u.id NOT IN(select user_id from friends where user_id=? or friend_id=?) and u.status=true group by u.id;");
                ps.setInt(1,user.getUserId());
                ps.setInt(2,user.getUserId());
                ps.setInt(3,user.getUserId());
                ps.setInt(4,user.getUserId());
            }
            else
            {
                String name=Validation.getInstance().isCharacter("UserName");
                ps=Database.getInstance().getConnection().prepareStatement("select u.id,u.uname from user_details u left join friends f on f.user_id=u.id where u.id NOT IN(select friend_id from friends where user_id=? or friend_id=?) and u.id NOT IN(select user_id from friends where user_id=? or friend_id=?) and u.uname LIKE '"+name+"%"+"' and u.status=true group by u.id;");
                ps.setInt(1,user.getUserId());
                ps.setInt(2,user.getUserId());
                ps.setInt(3,user.getUserId());
                ps.setInt(4,user.getUserId());
            }
            ResultSet rs=ps.executeQuery();
            if(!rs.next())
            {
                rs.close();
                ps.close();
                throw new DataNotFoundException("NO PROFILES AVAILABLE");
            }
            rs=ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\n\t\tID:"+rs.getInt(1)+"\n\t\tName:"+rs.getString(2));
                if("y".equals(Validation.getInstance().isCheck("y-Add-Friend/n-Nope").toLowerCase()))
                {
                    PreparedStatement ps1=Database.getInstance().getConnection().prepareStatement("insert into friends (user_id,friend_id,status) values(?,?,?);");
                    ps1.setInt(1,user.getUserId());
                    ps1.setInt(2,rs.getInt(1));
                    ps1.setString(3,"Requested");
                    ps1.executeUpdate();
                    System.out.println("\nRequested Send Successfully!!!\n");
                }
                if("n".equals(Validation.getInstance().isCheck("y-continue/n-back").toLowerCase()))
                {
                    rs.close();
                    ps.close();
                    return;
                }
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public int reqCount(int uid)
    {
        int c=0;
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select count(u.uname) from user_details u join friends f on f.user_id=u.id where friend_id=? and f.status='Requested';");
            ps.setInt(1,uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
              c=rs.getInt("count");
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public int frndCount(int uid)
    {
        int c=0;
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select count(id) from friends where (user_id=? or friend_id =?) and status='friends';");
            ps.setInt(1,uid);
            ps.setInt(2,uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
             c=rs.getInt("count");
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public void manageRequest(int uid)throws IOException
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select f.id,u.uname from user_details u join friends f on f.user_id=u.id where f.friend_id=? and f.status='Requested';");
            ps.setInt(1,uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\n\t\tID:"+rs.getInt(1)+"\n\t\tName:"+rs.getString(2));
                if("y".equals(Validation.getInstance().isCheck("y-Accept/n-Reject").toLowerCase()))
                {
                    PreparedStatement ps1=Database.getInstance().getConnection().prepareStatement("update friends set status='friends' where id=?");
                    ps1.setInt(1, rs.getInt(1));
                    ps1.executeUpdate();
                    ps1.close();
                    System.out.println("\nAccepted!!!\n");
                }
                if("n".equals(Validation.getInstance().isCheck("y-continue/n-back").toLowerCase()))
                {
                    rs.close();
                    ps.close();
                    return;
                }
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void friendList(int uid)throws IOException
    { 
        int c=0;
        System.out.println("\n\tFRIEND LIST:\n");
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select u.id,u.uname from user_details u LEFT JOIN friends f on f.user_id=u.id where u.id in(SELECT friend_id from friends where user_id=? AND status='friends') OR u.id in(SELECT user_id from friends where friend_id=? AND status='friends') GROUP BY u.id;");
            ps.setInt(1,uid);
            ps.setInt(2,uid);
            ResultSet rs=ps.executeQuery();
            if(!rs.next())
                throw new DataNotFoundException("NO FRIENDS AVAILABLE");
            rs=ps.executeQuery();
            while(rs.next())
            {
                c++;
                System.out.println("\n\t\tID:"+rs.getInt(1)+"\n\t\tName:"+rs.getString(2));
                if(c%5==0)
                {
                    if("n".equals(Validation.getInstance().isCheck("y-continue/n-back").toLowerCase()))
                    {
                        rs.close();
                        ps.close();
                        break;
                    }
                }
            }
            rs.close();
            ps.close();       
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
}
