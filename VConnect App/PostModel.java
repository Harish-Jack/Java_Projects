
package com.zoho.VConnectApp;
import java.util.Date;

import com.zoho.VConnectApp.UserModel;
public class PostModel 
{
    private int postId;
    private byte[] post;
    private String caption;
    private Date created_at;
    private String privacy;
    private int like_count;
    private int comment_count;
    private Date liked_at;
    private String comment;
    private Date commented_at;
    UserModel user=null;
    public PostModel() {
    }

    public PostModel(byte[] post, String caption, Date created_at, String privacy) {
        this.post = post;
        this.caption = caption;
        this.created_at = created_at;
        this.privacy = privacy;
    }
    public PostModel(int postId,byte[] post, String caption, Date created_at, String privacy,int like_count,int comment_count) 
    {
        this.postId=postId;
        this.post = post;
        this.caption = caption;
        this.created_at = created_at;
        this.privacy = privacy;
        this.like_count=like_count;
        this.comment_count=comment_count;
    }
    public PostModel(int postId,String uname,Date liked_at,String caption)
    {
        user=new UserModel(uname);
        this.postId=postId;
        this.liked_at=liked_at;
        this.caption=caption;
    }
    public PostModel(int postId,String uname,String comment,Date commented_at,String caption)
    {
        user=new UserModel(uname);
        this.postId=postId;
        this.comment=comment;
        this.commented_at=commented_at;
        this.caption=caption;
    }
    public int getComment_count() {
        return comment_count;
    }
    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
    
    public int getLike_count() {
        return like_count;
    }
    
    public Date getLiked_at() {
        return liked_at;
    }

    public String getComment() {
        return comment;
    }

    public Date getCommented_at() {
        return commented_at;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getPostId() {
        return postId;
    }

    public byte[] getPost() {
        return post;
    }

    public void setPost(byte[] post) {
        this.post = post;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
