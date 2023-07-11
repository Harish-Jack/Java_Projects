package com.zoho.VConnectApp;
import java.sql.Date;

import com.zoho.VConnectApp.PostModel;
public class UserModel 
{
    private int userId;
    private String userName;
    private String profileName;
    private String email;
    private String gender;
    private String password;
    private Date dateOfBirth;
    private Date createdOn;
    private String privacy;
    private byte[] profilePic;
    private String profilePicName;
    PostModel post=null;
    public UserModel() {
    }
    public UserModel(int userId, String userName, String profileName, String email,String gender,String password, Date dateOfBirth, Date createdOn, String privacy,String profilePicName,byte[] profilePic) 
    {
        this.userId = userId;
        this.userName = userName;
        this.profileName = profileName;
        this.email = email;
        this.gender=gender;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.createdOn = createdOn;
        this.privacy = privacy;
        this.profilePicName=profilePicName;
        this.profilePic=profilePic;
    }
    public UserModel(int postid,int userId, String userName,byte[] posts,String caption,Date created_at,String privacy,int like_count,int comment_count)
    {
        post=new PostModel(postid,posts,caption,created_at,privacy,like_count,comment_count);
        this.userId = userId;
        this.userName = userName;
    }
    public UserModel(String userName)
    {
        this.userName=userName;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
    public String getProfilePicName() {
        return profilePicName;
    }
    public byte[] getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
}
