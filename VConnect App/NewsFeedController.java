package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
public class NewsFeedController
{
    public String getLikeStatus(int uid,int pid)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select count(id) from likes where post_id=? and liker_id=? and status=true;");
            ps.setInt(1,pid);
            ps.setInt(2,uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            if(rs.getInt("count")==0)
            {
                rs.close();
                ps.close();
                return "LIKE";
            }
            else
            {
                rs.close();
                ps.close();
                return "UNLIKE";
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    public void changeStatus(int uid,int pid,boolean flag)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select count(id) from likes where post_id=? and liker_id=?");
            ps.setInt(1,pid);
            ps.setInt(2,uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getInt("count")==0)
                {
                    PreparedStatement ps1=Database.getInstance().getConnection().prepareStatement("insert into likes (post_id,liker_id,liked_at,status) values (?,?,?,?);");
                    ps1.setInt(1,pid);
                    ps1.setInt(2,uid);
                    ps1.setDate(3,new java.sql.Date(System.currentTimeMillis()));
                    ps1.setBoolean(4,flag);
                    ps1.executeUpdate();
                    ps1.close();
                }
                else
                {
                    PreparedStatement ps1=Database.getInstance().getConnection().prepareStatement("update likes set liked_at=?,view_status=?,status=? where post_id=?;");
                    ps1.setDate(1,new java.sql.Date(System.currentTimeMillis()));
                    ps1.setString(2,"unread");
                    ps1.setBoolean(3,flag);
                    ps1.setInt(4,pid);
                    ps1.executeUpdate();
                    ps1.close();
                }
            }
            if(flag)
             System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~*LIKED*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
            else
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*UNLICKED*~~~~~~~~~~~~~~~~~~~~~~~~~~~~_\n\n");
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void addComment(int uid,int pid)throws IOException
    {
        System.out.print("\n\t\tCOMMENT:");
        String cmd=Validation.reader.readLine();
        try
        {
            PreparedStatement ps1=Database.getInstance().getConnection().prepareStatement("insert into comments (post_id,commenter_id,comment,comment_at) values (?,?,?,?);");
            ps1.setInt(1,pid);
            ps1.setInt(2,uid);
            ps1.setString(3,cmd);
            ps1.setDate(4,new java.sql.Date(System.currentTimeMillis()));
            ps1.executeUpdate();
            ps1.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~*COMMENTED*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
    }
    public void removeComment(int uid,int pid)throws IOException
    {
        LinkedHashMap<Integer,String> cmts=new LinkedHashMap<>();
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select id,comment from comments where post_id=? and commenter_id=? and status=true;");
            ps.setInt(1,pid);
            ps.setInt(2,uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                cmts.put(rs.getInt(1),rs.getString(2));
            }
            if(cmts.size()==0)
                throw new DataNotFoundException("\nNO ACTIVE COMMENTS AVAILABLE");
            else
            {
                System.out.println("\n\t\tYOUR COMMENTS\n");
                for(Map.Entry<Integer,String> val:cmts.entrySet())
                {
                    System.out.println("\n\t\tID:"+val.getKey()+"\t\tCOMMENT:"+val.getValue());
                }
                int num=Validation.getInstance().isNumeric("ID for Remove Comment");
                while(!cmts.containsKey(num))
                {
                    num=Validation.getInstance().isNumeric(" Valid ID for Remove Comment");
                }
                ps=Database.getInstance().getConnection().prepareStatement("update comments set status='false' where id=?;");
                ps.setInt(1,num);
                ps.executeUpdate();
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~*COMMENTED DELETED*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void viewLikes(int pid)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select l.liker_id,u.uname from likes l join user_details u on u.id=l.liker_id where l.post_id=? and l.status=true;");
            ps.setInt(1,pid);
            ResultSet rs=ps.executeQuery();
            if(!rs.next())
            throw new DataNotFoundException("\nNO LIKES AVAILABLE");
            System.out.println("\n___________________________________LIKES_____________________________________________");
            rs=ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\n\t\tLIKER_ID:"+rs.getInt(1)+"\t\tLIKER_NAME:"+rs.getString(2));
            }
            System.out.println("_____________________________________________________________________________________\n");
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void viewComments(int pid)
    {
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select c.commenter_id,u.uname,c.comment from comments c join user_details u on u.id=c.commenter_id where c.post_id=? and c.status=true;");
            ps.setInt(1,pid);
            ResultSet rs=ps.executeQuery();
            if(!rs.next())
            throw new DataNotFoundException("\nNO COMMENTS AVAILABLE");
            System.out.println("\n___________________________________COMMENTS_____________________________________________");
            rs=ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\n\t\tCOMMENTER_ID:"+rs.getInt(1)+"\t\tCOMMENTER_NAME:"+rs.getString(2)+"\n\t\tCOMMENT:"+rs.getString(3));
            }
            System.out.println("_________________________________________________________________________________________\n");
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void savePost(String foldername,String filename,byte[] data)throws IOException
    {
        try
        {
            File file=new File("/home/zoho/Downloads/Harish/Projects/VConnect App/Posts/"+foldername+"/");
            file.mkdir(); 
            File file1=new File(file,filename);
            FileOutputStream os=new FileOutputStream(file1);
            os.write(data);
            os.close();
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*SAVED*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~_\n\n");
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public void sharePost(int uid,byte[] post,String caption)throws IOException
    {
        String privacy=Validation.getInstance().getPrivacy();
        try
        {
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("insert into posts (user_id,post,caption,created_at,privacy) values (?,?,?,?,?);");
            ps.setInt(1,uid);
            ps.setBytes(2,post);
            ps.setString(3, caption);
            ps.setDate(4,new java.sql.Date(System.currentTimeMillis()));
            ps.setString(5, privacy);
            ps.executeUpdate();
            ps.close();
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*SHARED*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~_\n\n");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}