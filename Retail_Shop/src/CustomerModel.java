package com.zoho.retailshop;
public class CustomerModel
{
      private String customername;
      private String customerlocation;
      private long customermobile;
      private int loginid;
      
      public CustomerModel(String customername,long customermobile,String customerlocation,int loginid)
      {
                    this.customername=customername;
                    this.customermobile=customermobile;
                    this.customerlocation=customerlocation;
                    this.loginid=loginid;
      }
      
      public String getCustomername()
      {
              return customername;
      }
      public Long getCustomermobile()
      {
              return customermobile;
      }
      public String getCustomerlocation()
      {
              return customerlocation;             
      }
      public int getloginid()
      {
              return loginid;
      }      
      
      public void setCustomername(String customername)
      {
              this.customername=customername;
      }
      public void setCustomermobile(long customermobile)
      {
              this.customermobile=customermobile;
      }
      public void setCustomerlocation(String customerlocation)
      {  
              this.customerlocation=customerlocation;
      }
}
