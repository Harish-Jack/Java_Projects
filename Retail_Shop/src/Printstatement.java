package com.zoho.retailshop;
import java.util.ArrayList;

public class Printstatement
{
       private String date;
       private String productname;
       private String brandname;
       private String vendorname;
       private int count;
       
       public Printstatement(){}
       
       public Printstatement(String date,String productname,String brandname,int count)
       {
                    this.date=date;
                    this.productname=productname;
                    this.brandname=brandname;
                    this.count=count;
       }
       
       public Printstatement(String date,String productname,String brandname,String vendorname,int count)
       {
                    this.date=date;
                    this.productname=productname;
                    this.brandname=brandname;
                    this.count=count;       
                    this.vendorname=vendorname;
       }
       
       public String getDate()
       {
            return date; 
       }
       
       public String getProductname()
       {
            return productname; 
       }
       
       public String getBrandname()
       {
            return brandname; 
       }
       
       public String getVendorname()
       {
            return vendorname; 
       }
       
       public int getCount()
       {
            return count; 
       }
       
       //@Override
       public String toString()
       {
         String format=String.format("%-20s %-20s %-20s %-10d",date,productname,brandname,count);
         return format;
       }
       
       public void printReturn(ArrayList<Printstatement> al)
       {
           for(int i=0;i<al.size();i++)
             {
         	System.out.println(String.format("%-20s %-20s %-20s %-20s ",al.get(i).date,al.get(i).productname,al.get(i).brandname,al.get(i).count));
         	System.out.println("--------------------------------------------------------------------------------------------");
               System.out.println(" ");
             }         
        }       
       
       public void print(ArrayList<Printstatement> al,byte num)
       {
         //String format=String.format("%20s %20s %20s %20s %20d",date,productname,brandname,vendorname,count);
         if(num==1)
          {
           for(int i=0;i<al.size();i++)
             {
         	System.out.println(String.format("%-20s %-20s %-20s %-20s %-20d",al.get(i).date,al.get(i).productname,al.get(i).brandname,al.get(i).vendorname,al.get(i).count));
         	System.out.println("--------------------------------------------------------------------------------------------");
               System.out.println(" ");
             }
          }
      else if(num==2) 
         {
         for(int i=0;i<al.size();i++)
           {
              System.out.println(String.format("%-20s %-20s %-20s %-20s %-20d",al.get(i).date,al.get(i).productname,al.get(i).brandname,al.get(i).vendorname,al.get(i).count));
         	System.out.println("--------------------------------------------------------------------------------------------");
              System.out.println(" ");
           }         
         }
      else if(num==3) 
         {
         for(int i=0;i<al.size();i++)
           {
              System.out.println(String.format("%-20s %-20s %-20s %-20s %-20d",al.get(i).date,al.get(i).productname,al.get(i).brandname,al.get(i).vendorname,al.get(i).count));
         	System.out.println("--------------------------------------------------------------------------------------------");
              System.out.println(" ");
           }         
         }
     }
}
