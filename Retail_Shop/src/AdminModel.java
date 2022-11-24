package com.zoho.retailshop;
public class AdminModel
{
       private String adminname;
       private long adminmobile;
       private int loginid;
       
       AdminModel(String adminname,long adminmobile,int loginid)
       {
                 this.adminname=adminname;
                 this.adminmobile=adminmobile;
                 this.loginid=loginid;
       }
       
      public String getAdminname()
      {
              return adminname;
      }
      public Long getAdminmobile()
      {
              return adminmobile;
      }
      public int getloginid()
      {
              return loginid;
      }
      
      public void setAdminname(String adminname)
      {
              this.adminname=adminname;
      }
      public void setAdminmobile(long adminmobile)
      {
              this.adminmobile=adminmobile;
      }
}
