package com.zoho.ShopifyApp;
import java.sql.Date;
public class UserModel
{
    private String Uname;
    private String role;
    private int roleid;
    private String gender;
    private long mobile;
    private String mail;
    private String password;
    private Date cretaeddate;
    AddressModel adr=null;
    UserModel(){}
    UserModel(String Uname,int roleid,String genger,long mobile,String mail,String password)
    {
        this.Uname=Uname;
        this.roleid=roleid;
        this.gender=genger;
        this.mobile=mobile;
        this.mail=mail;
        this.password=password;
    }
    UserModel(String Uname,int roleid,String role,String gender,long mobile,String mail,String password,Date createddate,int userId,int doorNo,String streetName,String city,String district,int pincode)
    {
        adr=new AddressModel(userId, doorNo, streetName, city, district, pincode);
        this.Uname=Uname;
        this.roleid=roleid;
        this.role=role;
        this.gender=gender;
        this.mobile=mobile;
        this.mail=mail;
        this.password=password;
        this.cretaeddate=createddate;
    }
    public String getUname() {
        return Uname;
    }
    public void setUname(String uname) {
        Uname = uname;
    }
    public int getRoleid() {
        return roleid;
    }
    public String getRole() {
        return role;
    }
    public Date getCreatedDate() {
        return cretaeddate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public long getMobile() {
        return mobile;
    }
    public void setMobile(long mobile) {
        this.mobile = mobile;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
