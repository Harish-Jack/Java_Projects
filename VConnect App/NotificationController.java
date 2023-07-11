package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
public class NotificationController 
{
    public int unreadNotificationCount(int uid)
    {
        int c=0;
        try 
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select count(id) from likes where post_id in (select id from posts where user_id=?) and view_status='unread' and status='t';");
            ps.setInt(1,uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                c+=rs.getInt(1);
            ps=Database.getInstance().getConnection().prepareStatement("select count(id) from comments where post_id in (select id from posts where user_id=?) and view_status='unread' and status='t';");
            ps.setInt(1,uid);
            rs=ps.executeQuery();
            if(rs.next())
                c+=rs.getInt(1);
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public void viewNotification(int uid,boolean flag)throws IOException
    {
        int num=0;
        LinkedHashMap<Integer,PostModel> notifylikes=new LinkedHashMap<>();
        LinkedHashMap<Integer,PostModel> notifycmnts=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps=null;
            if(flag)
                ps=Database.getInstance().getConnection().prepareStatement("select l.id,l.post_id,u.uname,l.liked_at,p.caption from likes l join user_details u on u.id=l.liker_id join posts p on p.id=l.post_id where l.post_id IN (select id from posts where user_id=?) and l.view_status='unread' and l.status=true;");
            else
                ps=Database.getInstance().getConnection().prepareStatement("select l.id,l.post_id,u.uname,l.liked_at,p.caption from likes l join user_details u on u.id=l.liker_id join posts p on p.id=l.post_id where l.post_id IN (select id from posts where user_id=?) and l.status=true;");
            ps.setInt(1,uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                notifylikes.put(rs.getInt(1),new PostModel(rs.getInt(2),rs.getString(3),rs.getDate(4),rs.getString(5)));
            }
            if(flag)
                ps=Database.getInstance().getConnection().prepareStatement("select c.id,c.post_id,u.uname,c.comment,c.comment_at,p.caption from comments c join user_details u on u.id=c.commenter_id join posts p on p.id=c.post_id where c.post_id IN (select id from posts where user_id=?) and c.view_status='unread' and c.status=true;");
            else
                ps=Database.getInstance().getConnection().prepareStatement("select c.id,c.post_id,u.uname,c.comment,c.comment_at,p.caption from comments c join user_details u on u.id=c.commenter_id join posts p on p.id=c.post_id where c.post_id IN (select id from posts where user_id=?) and c.status=true;");
            ps.setInt(1,uid);
            rs=ps.executeQuery();
            while(rs.next())
            {
                notifycmnts.put(rs.getInt(1),new PostModel(rs.getInt(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6)));
            }
            if(notifylikes.size()+notifycmnts.size()==0)
                throw new DataNotFoundException("\nNO RECENT NOTIFICATION AVAILABLE");
            
            System.out.println("\n------------------------------------------------~NOTIFICATION~----------------------------------------------");
            for(Map.Entry<Integer,PostModel> val:notifylikes.entrySet())
            {
                System.out.println("\n"+val.getKey()+"-"+val.getValue().user.getUserName()+" likes your post:"+val.getValue().getPostId()+" at "+val.getValue().getLiked_at());
                ps=Database.getInstance().getConnection().prepareStatement("update likes set view_status='read' where id=?;");
                ps.setInt(1,val.getKey());
                ps.executeUpdate();
                System.out.println("_______________________________________*VIEWED*________________________________________");
                if("n".equals(Validation.getInstance().isCheck("y-continue/n-Home")))
                    return;
            }
            for(Map.Entry<Integer,PostModel> val:notifycmnts.entrySet())
            {
                System.out.println("\n"+val.getKey()+"-"+val.getValue().user.getUserName()+" commend your post:("+val.getValue().getComment()+") Id:"+val.getValue().getPostId()+" at "+val.getValue().getCommented_at());
                ps=Database.getInstance().getConnection().prepareStatement("update comments set view_status='read' where id=?;");
                ps.setInt(1,val.getKey());
                ps.executeUpdate();
                System.out.println("_______________________________________*VIEWED*________________________________________");
                if("n".equals(Validation.getInstance().isCheck("y-NextPage/n-Home")))
                    return;
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
