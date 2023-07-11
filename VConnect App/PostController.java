package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;
import java.util.Map;
import java.util.LinkedHashMap;
public class PostController extends AbstractPostProvider
{
    LinkedHashMap<Integer,PostModel> posts=null;
    int num=0;
    String privacy="";
    public void addPost(int uid)throws Exception
    {
        byte newpost[]=null;
        System.out.print("\n\t\tGive file Path (eg:/home/zoho/Downloads/filename.extention):");
        String path=Validation.getInstance().isValidPath(Validation.reader.readLine());
        File post=new File(path);
        newpost=new SignUp().getByteArray(path,post);
        System.out.print("\n\t\tCaption:");
        String caption=Validation.reader.readLine();
        privacy=Validation.getInstance().getPrivacy();
        try
        {
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("insert into posts (user_id,post,caption,created_at,privacy) values (?,?,?,?,?);");
            ps.setInt(1,uid);
            ps.setBytes(2,newpost);
            ps.setString(3,caption);
            ps.setDate(4,currentDate);
            ps.setString(5,privacy);
            ps.executeUpdate();
            ps.close();
            System.out.println("\nPOST ADDED SUCCESSFULLY");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void deletePost(int uid)throws IOException
    {
            System.out.println("\n\tREMOVE POST\n");
            getPosts(uid);
            outer:
            while(true)
            {
                num=Validation.getInstance().isNumeric("Id to remove Post");
                while(!posts.containsKey(num))
                {
                    num=Validation.getInstance().isNumeric("Valid Id to remove Post");
                }
                try
                {
                    PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("update posts set status=false where id=?;");
                    ps.setInt(1,num);
                    ps.executeUpdate();
                    ps.close();
                    System.out.println("\nPOST REMOVED SUCCESSFULLY");
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
                if("n".equals(Validation.getInstance().isCheck("y-continue/n-back").toLowerCase()))
                    break outer;
                else
                    getPosts(uid);
            }
    }
    public void changePrivacy(int uid)throws IOException
    {
        int choice=0;
        System.out.println("\n\tCHANGE PRIVACY\n");
        getPosts(uid);
        outer:
        while(true)
        {
            num=Validation.getInstance().isNumeric("Id for change privacy");
            while(!posts.containsKey(num))
            {
                num=Validation.getInstance().isNumeric("Valid Id for change privacy");
            }
            System.out.println("\n\t\tPOST ID:"+num+" Privacy Type:"+posts.get(num).getPrivacy());
            privacy=posts.get(num).getPrivacy();
            if("y".equals(Validation.getInstance().isCheck("y-continue/n-nope").toLowerCase()))
            {
                choice=Validation.getInstance().isNumeric("Privacy Option(1-Public/2-Friends/3-Private)");
                while(choice<=0 || choice>3 || privacy.equals(posts.get(num).getPrivacy()))
                {
                    choice=Validation.getInstance().isNumeric(" Valid Privacy Option(1-Public/2-Friends/3-Private)");
                    if(choice==1)
                        privacy="public";
                    else if(choice==2)
                        privacy="friends";
                    else
                        privacy="private";
                }
                try
                {
                    PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("update posts set privacy=? where id=?;");
                    ps.setString(1,privacy);
                    ps.setInt(2,num);
                    ps.executeUpdate();
                    ps.close();
                    System.out.println("\nPRIVACY UPDATED SUCCESSFULLY");
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if("n".equals(Validation.getInstance().isCheck("y-continue/n-back").toLowerCase()))
                break outer;
            else
                getPosts(uid);
        }
    }
    public void getPosts(int uid)
    {
        try
        {
            posts=new LinkedHashMap<>();
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select id,post,caption,created_at,privacy from posts where user_id=? and status='t';");
            ps.setInt(1,uid);
            ResultSet rs=ps.executeQuery();
            if(!rs.next())
                throw new DataNotFoundException("\nNO ACTIVE POSTS AVAILABLE");
            rs=ps.executeQuery();
            while(rs.next())
            {
                posts.put(rs.getInt(1),new PostModel(rs.getBytes(2),rs.getString(3),rs.getDate(4),rs.getString(5)));
            }
            System.out.println(String.format("%-20s|%-30s|%-20s|%-20s|","id","Caption","Created-at","privacy"));
            System.out.println("_____________________________________________________________________________________________");
            for(Map.Entry<Integer,PostModel> val:posts.entrySet())
            {
                System.out.println(String.format("%-20s|%-30s|%-20s|%-20s|",val.getKey(),val.getValue().getCaption(),val.getValue().getCreated_at(),val.getValue().getPrivacy()));
            }
            System.out.println("_____________________________________________________________________________________________");
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
