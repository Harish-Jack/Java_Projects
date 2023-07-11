package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import com.zoho.VConnectApp.NewsFeedController;

import java.util.LinkedHashMap;
public class NewsFeed 
{
    public void viewNewsFeed(UserModel user)throws IOException
    {
        LinkedHashMap<Integer,UserModel> posts=new LinkedHashMap<>();
        NewsFeedController nfcontrol=new NewsFeedController();
        try
        {
            //and p.user_id NOT IN(select liker_id from likes where post_id=p.id and status='t')
            PreparedStatement ps=Database.getInstance().getConnection().prepareStatement("select p.id,p.user_id,u.uname,p.post,p.caption,p.created_at,p.privacy,(select count(id) from likes where post_id=p.id and status=true) as like_count,(select count(id) from comments where post_id=p.id and status=true) as comments_count from posts p join user_details u on u.id=p.user_id where p.status=true and (p.user_id IN (select user_id from friends where friend_id=? and status='friends') or p.user_id IN (select friend_id from friends where user_id=? and status='friends')) and p.privacy='public' or p.user_id=?;");
            ps.setInt(1,user.getUserId());
            ps.setInt(2,user.getUserId());
            ps.setInt(3,user.getUserId());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                posts.put(rs.getInt(1),new UserModel(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getBytes(4),rs.getString(5),rs.getDate(6),rs.getString(7),rs.getInt(8),rs.getInt(9)));
            }
            if(posts.size()==0)
            {
                rs.close();
                ps.close();
                throw new DataNotFoundException("\nNO RECENT POSTS AVAILABLE");
            }
            else
            {
                home:
                for(Map.Entry<Integer,UserModel> val:posts.entrySet())
                {
                    outer:
                    while(true)
                    {
                        System.out.println("\n\t\tUSER_NAME:"+val.getValue().getUserName());
                        System.out.println("\n\t\tPOST_ID:"+val.getKey()+"\t\tCAPTION:"+val.getValue().post.getCaption());
                        System.out.println("\n\t\tLIKES("+val.getValue().post.getLike_count()+")\t\tCOMMENTS("+val.getValue().post.getComment_count()+")");
                        String like=nfcontrol.getLikeStatus(user.getUserId(),val.getKey());
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        System.out.println("\n\t\t\t\t\t1."+like+"("+val.getValue().post.getLike_count()+")"+"\n\t\t\t\t\t2.COMMENT("+val.getValue().post.getComment_count()+")\n\t\t\t\t\t3.SHARE\n\t\t\t\t\t4.SAVE\n\t\t\t\t\t5.VIEW LIKES\n\t\t\t\t\t6.VIEW COMMENTS\n\t\t\t\t\t7.NEXT\n\t\t\t\t\t8.Home");
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        int num=Validation.getInstance().isNumeric("Your Choice");
                        switch(num)
                        {
                            case 1: if(like.equals("LIKE"))
                                    {
                                        nfcontrol.changeStatus(user.getUserId(),val.getKey(),true);
                                        val.getValue().post.setLike_count(val.getValue().post.getLike_count()+1);
                                    }
                                    else
                                    {
                                        nfcontrol.changeStatus(user.getUserId(),val.getKey(),false);
                                        val.getValue().post.setLike_count(val.getValue().post.getLike_count()-1);
                                    }
                                    break;
                                    
                            case 2: try
                                    {
                                        String ch=Validation.getInstance().isChecking("y-ADD NEW COMMENT/n-REMOVE COMMENT/b-BACK").toLowerCase();
                                        if("y".equals(ch))
                                        {
                                            nfcontrol.addComment(user.getUserId(),val.getKey());
                                            val.getValue().post.setComment_count(val.getValue().post.getComment_count()+1);
                                        }
                                        else if("n".equals(ch))
                                        {
                                            nfcontrol.removeComment(user.getUserId(),val.getKey());
                                            val.getValue().post.setComment_count(val.getValue().post.getComment_count()-1);
                                        }
                                    }
                                    catch(DataNotFoundException e)
                                    {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                            
                            case 3: nfcontrol.sharePost(user.getUserId(),val.getValue().post.getPost(),val.getValue().post.getCaption());
                                    break;

                            case 4: nfcontrol.savePost(val.getValue().getUserName(),val.getValue().post.getPostId()+"",val.getValue().post.getPost());
                                    break;
                            
                            case 5: try
                                    {
                                        nfcontrol.viewLikes(val.getKey());
                                    }
                                    catch(DataNotFoundException e)
                                    {
                                        System.out.println(e.getMessage());
                                    }
                                    break;

                            case 6: try
                                    {
                                        nfcontrol.viewComments(val.getKey());
                                    }
                                    catch(DataNotFoundException e)
                                    {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                            
                            case 7: break outer;

                            case 8: break home;
                                    
                            default:
                                    System.out.println("\nGIVE VALID INPUT!!!!!!!!!");
                                    break;
                        }
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
