package com.zoho.LibraryApp;
public class UserModel
{
      private String username;
      private String address;
      private long mobileno;
      private String gender;
      
      public UserModel(String username,String address,long mobileno,String gender)
      {
                    this.username=username;
                    this.address=address;
                    this.mobileno=mobileno;
                    this.gender=gender;
      }
      
      public String getUserName()
      {
              return username;
      }
      public void setUserName(String username)
      {
              this.username=username;
      }
      public String getUserAddress()
      {
              return address;
      }
      public void setUserAddress(String address)
      {
              this.address=address;
      }
      public long getMobileNo()
      {
              return mobileno;             
      }
      public void setMobileNo(long mobileno)
      {  
              this.mobileno=mobileno;
      }
      public String getGender()
      {
              return gender;             
      }
      public void setGender(String gender)
      {  
              this.gender=gender;
      }
}