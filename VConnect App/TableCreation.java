package com.zoho.VConnectApp;
import java.sql.SQLException;
public class TableCreation
{
    public void tableOrder()throws Exception
    {
        userTable();
        profileTable();
        friendTable();
        postTable();
        likesTable();
        commentTable();
        chatTable();
        messageTable();
        setLikeCommentStatus();
    }
    private void userTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS user_details(id SERIAL PRIMARY KEY,uname VARCHAR(50) UNIQUE NOT NULL,profile_name VARCHAR(50) NOT NULL,email VARCHAR(50) UNIQUE NOT NULL,gender VARCHAR(10) NOT NULL,password VARCHAR(10) NOT NULL,DOB DATE NOT NULL,created_on DATE NOT NULL,privacy VARCHAR(15) DEFAULT 'public',status BOOLEAN DEFAULT TRUE);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("user_details Table not created");
        } 
    }
    private void profileTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS profiles(id SERIAL PRIMARY KEY,user_id INT NOT NULL,file_name VARCHAR(20) NOT NULL,profile BYTEA,status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("profileTable not created");
        } 
    }
    private void friendTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS friends(id SERIAL PRIMARY KEY,user_id INT NOT NULL,friend_id INT NOT NULL,status VARCHAR(20) NOT NULL,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id)on delete cascade,CONSTRAINT fk_friendid FOREIGN KEY(friend_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("friendTable not created");
        } 
    }
    private void postTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS posts(id SERIAL PRIMARY KEY,user_id INT NOT NULL,post BYTEA,caption VARCHAR(50) NOT NULL,created_at DATE NOT NULL,privacy VARCHAR(20)NOT NULL,status BOOLEAN DEFAULT true,CONSTRAINT fk_userid FOREIGN KEY(user_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("PostTable not created");
        } 
    }
    private void likesTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS likes(id SERIAL PRIMARY KEY,post_id INT NOT NULL,liker_id INT NOT NULL,liked_at DATE NOT NULL,view_status VARCHAR(20) DEFAULT 'unread',status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_postid FOREIGN KEY(post_id) REFERENCES posts(id)on delete cascade,CONSTRAINT fk_likerid FOREIGN KEY(liker_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("LikesTable not created");
        } 
    }
    private void commentTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS comments(id SERIAL PRIMARY KEY,post_id INT NOT NULL,commenter_id INT NOT NULL,comment VARCHAR(255) NOT NULL,comment_at DATE NOT NULL,view_status VARCHAR(20) DEFAULT 'unread',status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_postid FOREIGN KEY(post_id) REFERENCES posts(id)on delete cascade,CONSTRAINT fk_commenterid FOREIGN KEY(commanter_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("CommentTable not created");
        } 
    }
    private void chatTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS chat(id SERIAL PRIMARY KEY,user_id_1 INT NOT NULL,user_id_2 INT NOT NULL,created_on DATE NOT NULL,status BOOLEAN DEFAULT TRUE,CONSTRAINT fk_user_id1 FOREIGN KEY(user_id_1) REFERENCES user_details(id)on delete cascade,CONSTRAINT fk_user_id2 FOREIGN KEY(user_id_2) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("chatTable not created");
        } 
    }
    private void messageTable()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS message(id SERIAL PRIMARY KEY,chat_id INT NOT NULL,sender_id INT NOT NULL,message TEXT,send_at DATE NOT NULL,status VARCHAR(20) NOT NULL,CONSTRAINT fk_chatid FOREIGN KEY(chat_id) REFERENCES chat(id)on delete cascade,CONSTRAINT fk_senderid FOREIGN KEY(sender_id) REFERENCES user_details(id) on delete cascade);");                                
        }    
        catch(SQLException e)
        {
            System.out.println("messageTable not created");
        } 
    }
    private void setLikeCommentStatus()
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("update likes set view_status='read' where liker_id=(select user_id from posts where id=post_id) and status=true and view_status='unread';");                     
            StatementSingleton.getStatement().executeUpdate("update comments set view_status='read' where commenter_id=(select user_id from posts where id=post_id) and status=true and view_status='unread';");           
        }    
        catch(SQLException e)
        {
            System.out.println("LikeCommentStatus not Updated");
        } 
    }
}