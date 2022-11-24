package com.zoho.retailshop;
public class VendorModel
{
      private String vendorname;
      private String vendorlocation;
      private Long vendormobile;
      
      public VendorModel(String vendorname,Long vendormobile,String vendorlocation)
      {
                    this.vendorname=vendorname;
                    this.vendormobile=vendormobile;
                    this.vendorlocation=vendorlocation;
      }
      
      public String getVendorname()
      {
              return vendorname;
      }
      public Long getVendormobile()
      {
              return vendormobile;
      }
      public String getVendorlocation()
      {
              return vendorlocation;             
      }
      
      public void setVendorname(String vendorname)
      {
              this.vendorname=vendorname;
      }
      public void setVendormobile(long vendormobile)
      {
              this.vendormobile=vendormobile;
      }
      public void setVendorlocation(String vendorlocation)
      {  
              this.vendorlocation=vendorlocation;
      }
}
