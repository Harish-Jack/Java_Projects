package BankManagement;
public class Address
{
 private String doorno;
 private String streetname;
 private String city;
 private int pincode;
 public Address(String doorno,String streetname,String city,int pincode)
 {
  this.doorno=doorno;
  this.streetname=streetname;
  this.city=city;
  this.pincode=pincode;
 }
  public String toString()
  {
   return this.doorno+","+this.streetname+","+this.city+","+this.pincode;
  }
 }
  
