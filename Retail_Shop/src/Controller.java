package com.zoho.retailshop;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 
import java.io.Console; 
import java.util.Random;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;  
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.SQLException;
public class Controller
{
     private static PreparedStatement ps=null;
     private static ResultSet resultset=null;
     static Scanner userinput=new Scanner(System.in);
     private static Console console=System.console();
     LocalDate localdate=LocalDate.now();  
     
     public int getPass()
     {
          int pwd=0; 
          String str=null; 
          try
            {	
               char[] ch=console.readPassword();       
               str=String.valueOf(ch);
                while(!(isDigits(str)))
                {
                   System.out.println("Give Valid Password!!!!!!!!!!!");
                   System.out.print("Password:");
                   ch=console.readPassword();       
                   str=String.valueOf(ch);   
                } 
                 pwd=Integer.valueOf(str);
            }
           catch(Exception e)
             {
                 System.out.println("Invalid Password!!!!!!!!!!!");
             }
           return pwd; 
      }
      
     public boolean isDigits(String cardNo)
      { 
         char[] cardArr=cardNo.toCharArray();
         for(char ch:cardArr)
           { 
            if(Character.isDigit(ch)==false) 
             {
                return false;
             }
          }
          return true;
      }
      
      public int getUserid()
      {
           String uname=null;
           int result=0;
           try
             {
               uname=console.readLine();
               while(!(isDigits(uname)))
               {
                 uname=null;
                 System.out.println("UserId Incorrect!!!Give valid UserId(eg-123456)");
                 System.out.print("ReEnter UserId:"); 
                 uname=console.readLine();
               }
             }
           catch(Exception e)
             {
                System.out.println("Invalid UserName!!!!!!!!!!!");  
             }
           result=Integer.parseInt(uname);
           return result;              
      }
      
      public int generateUserid()
      {
           String id=null;
           Random random=new Random();
           char[] userId=new char[6];
           String num="123456789";
           for(int i=0;i<userId.length;i++)
           {
              userId[i]=num.charAt(random.nextInt(num.length()));
           }
           id=String.valueOf(userId);
           int userid=Integer.valueOf(id);
           
           return userid;
      }
      
      public int generatePassword()
      {
           String id=null;
           Random random=new Random();
           char[] userId=new char[4];
           String num="123456789";
           for(int i=0;i<userId.length;i++)
           {
              userId[i]=num.charAt(random.nextInt(num.length()));
           }
           id=String.valueOf(userId);
           int password=Integer.valueOf(id);
           
           return password;
      }
      
      
      public long returnMobileno()
      {
          String ptrn = "(0/91)?[7-9][0-9]{9}";
          String mobno=console.readLine();
          long mobilenumber=0;
          while(!(Pattern.matches(ptrn,mobno)))
          {
           System.out.println("Invalid Number(Give valid number eg:9087563432)") ;
           System.out.print("MobilenNo:"); 
              mobno=console.readLine();
          }
          mobilenumber=Long.parseLong(mobno);
         return mobilenumber;
    }
    
    public int validId(int max)
    {
	 String name=console.readLine();
         while((name == null) || (name.equals("")) || (name.matches(".*[^0-"+max+"].*")))
	 {
	      System.out.println("Give valid input!!!!!!!("+max+" id only available)");
	      System.out.print("ReEnter Id:");
	      name=console.readLine();
	 }
	     int num=Integer.parseInt(name);
	     return num;
    }
    
    public int getchoice(int value)
    {
         int num=0;
	 String name=console.readLine();
         while((name == null) || (name.equals("")) || (name.matches(".*[^1-"+value+"].*")))
	 {
	      System.out.println("(Give valid input!!!!!!!)");
	      name=console.readLine();
	 }
	      num=Integer.parseInt(name);
	     return num;
    }    
       
    public String checkString()
    {
         String name=console.readLine();
         while((name == null) || (name.equals("")) || (!name.matches("^[a-zA-Z]*$")))
	 {
	      System.out.println("Give valid input!!!!!!!(accept only a-z/A-Z)");
	      name=console.readLine();
	 }
	 return name;
    }
    
    public int getDays()
    {
         int month=0,days=0;
         while(month<1 || month>3)
           {    
             System.out.println("--------------------------------------------------------------------------------------");
             System.out.println("\nLast 3 Months====>(press 1)");
             System.out.println("Last 2 Monthss====>(press 2)");
             System.out.println("Last 1 Monthss====>(press 3)");
             System.out.print("Enter Your Choice:");
             month = userinput.nextByte();
             if(month==1)
             {
               days=90;
             }
             else if(month==2)
             {
               days=60;
             }
             else
             {
               days=30;
             }
          }
        return days;           
    }
    
    public int authenticate(int userId,int password)
    {
        int flag=0;
        try
          {
             String verify="SELECT A.id FROM information A INNER JOIN login B ON A.id=B.information_id INNER JOIN customer_list C ON B.id=C.login_id WHERE B.user_id="+userId+" "
                            +"AND B.password="+password+" AND C.customer_status='true' UNION SELECT A.id FROM information A INNER JOIN login B ON A.id=B.information_id INNER JOIN admin_list D "
                            +"ON B.id=D.login_id WHERE B.user_id="+userId+" AND B.password="+password+" AND D.admin_status=true;"; 
                            ps=Database.con.prepareStatement(verify);
                            resultset=ps.executeQuery();
                            while(resultset.next())
                            {
                             flag=resultset.getInt("id");
                             if(flag!=0)
                             {
                              break;
                             }
                            }                                       
           }        
      catch(SQLException e)
         {
             e.printStackTrace();
         }
          return flag; 
    }
    public String getRole(int userId,int password)
    {
        String role=null;
        try
         {
            String checkrole="SELECT (CASE WHEN(B.user_id="+userId+") AND (B.password="+password+") AND (A.id=B.information_id) THEN A.role ELSE null END) AS \"role\""
                         + " FROM information A INNER JOIN login B ON information_id=A.id;";
                            ps=Database.con.prepareStatement(checkrole);
                            resultset=ps.executeQuery();
                            while(resultset.next())
                            {
                             role=resultset.getString("role");
                             if(role!=null)
                             {
                              break;
                             }
                            }                                       
         }        
       catch(SQLException e)
         {
             e.printStackTrace();
         }
          return role;        
    }
    
   /* public boolean getStatus(String role,int userId,int password)
    {
       boolean status=false;
       try
         {
             String chekstatus=""
         }
    }*/
    
    public ArrayList<String> viewProducts()
    {
         ArrayList<String> products=new ArrayList<String>(); 
         try
           {
             String getProducts="SELECT product_name FROM product_list WHERE product_status='true';";
             ps=Database.con.prepareStatement(getProducts);
             resultset=ps.executeQuery();
             while(resultset.next())
             {
                products.add(resultset.getString("product_name"));
             }
           }
         catch(SQLException e)
           {
             e.printStackTrace();             
           }  
         return products;
       }   
       
    public ArrayList<String> viewStock()
    {
         ArrayList<String> stocks=new ArrayList<String>(); 
         try
           {
             String getStocks="SELECT total_count FROM stock ORDER BY product_id;";
             ps=Database.con.prepareStatement(getStocks);
             resultset=ps.executeQuery();
             while(resultset.next())
             {
                stocks.add(resultset.getString("total_count"));
             }
           }
         catch(SQLException e)
           {
             e.printStackTrace();             
           }  
         return stocks;
       }   

    public ArrayList<String> viwProducts()
    {
         ArrayList<String> product=new ArrayList<String>(); 
         try
           {
             String getProducts="SELECT brand_name FROM product_list WHERE PRODUCT_status='true';";
             ps=Database.con.prepareStatement(getProducts);
             resultset=ps.executeQuery();
             while(resultset.next())
             {
                product.add(resultset.getString("brand_name"));
             }
           }
         catch(SQLException e)
           {
             e.printStackTrace();             
           }  
         return product;
       }   
     
     public LinkedHashMap<Integer,String> getVendor()
     {
         LinkedHashMap<Integer, String> vendorlist = new LinkedHashMap<Integer, String>();  
         try
           {
             String getVendor="SELECT id,vendor_name from vendor_list WHERE vendor_status='true';";
             ps=Database.con.prepareStatement(getVendor);  
             resultset=ps.executeQuery();
             while(resultset.next())
             {
                vendorlist.put(resultset.getInt("id"),resultset.getString("vendor_name"));
             }
           }
         catch(SQLException e)
           {
             e.printStackTrace();             
           }  
         return vendorlist;
       }                            
       
     public LinkedHashMap<Integer,String> getAdminlist(String adminname)
     {
         LinkedHashMap<Integer, String> adminlist = new LinkedHashMap<Integer, String>();  
         try
           {
             String getAdmin="SELECT id,admin_name from admin_list WHERE admin_status='true' AND admin_name!='"+adminname+"';";
             ps=Database.con.prepareStatement(getAdmin);  
             resultset=ps.executeQuery();
             while(resultset.next())
             {
                adminlist.put(resultset.getInt("id"),resultset.getString("admin_name"));
             }
           }
         catch(SQLException e)
           {
             e.printStackTrace();             
           }  
         return adminlist;
       }                                     
       
       public int getMaxid(String tablename)
       {
           int max=0;
           try
             {
                 String maximum="SELECT MAX(id) AS \"Max\" FROM "+tablename+";";   
                 ps=Database.con.prepareStatement(maximum);
                 resultset=ps.executeQuery();
                 if(resultset.next())
                 {
                   max=resultset.getByte("Max");           
                 }
             }
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return max;        
    }
     
           
     public boolean checkProduct(String productname,String brandname)
     {
         boolean flag=false;
         try
           {
                 String checkproduct="SELECT (CASE WHEN (LOWER(REPLACE(A.product_name,' ',''))=LOWER(REPLACE('"+productname+"',' ',''))) AND " 
                                     +"(LOWER(REPLACE(A.brand_name,' ',''))=LOWER(REPLACE('"+brandname+"',' ',''))) AND (product_status='true')THEN 'true' ELSE 'false' END) " 
                                     +"AS \"status\" FROM product_list A;";
                 ps=Database.con.prepareStatement(checkproduct);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      flag=resultset.getBoolean("status");
                      if(flag)
                      {
                        break;
                      }                      
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;        
     }
          
     public boolean checkSaleProduct(int productid)
     {
         boolean flag=false;
         try
           {
                 String checkSale="SELECT (CASE WHEN (product_id="+productid+") AND (product_count!=0) AND (return_availability='true') THEN 'true' ELSE 'false' END) "
                                     +"AS \"status\" FROM sale_details;";
                 ps=Database.con.prepareStatement(checkSale);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      flag=resultset.getBoolean("status");
                      if(flag)
                      {
                        break;
                      }                      
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;        
     }
     
     public boolean checkProductid(int productid)
     {
         boolean flag=false;
         try
           {
                 String prodid="SELECT (CASE WHEN (A.product_id="+productid+") AND (A.product_id=B.id) THEN 'true' ELSE 'false' END)"
                               +"AS \"status\" FROM stock A INNER JOIN product_list B ON B.id=A.product_id;";
                 ps=Database.con.prepareStatement(prodid);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      flag=resultset.getBoolean("status");
                      if(flag)
                      {
                        break;
                      }
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;        
     }     
     
     public int getProductid(String productname,String brandname)
     {
         int vendorId=-1;
         try
           {
                 String getproduct="SELECT (CASE WHEN (LOWER(REPLACE(A.product_name,' ',''))=LOWER(REPLACE('"+productname+"',' ',''))) AND" 
                                    +"(LOWER(REPLACE(A.brand_name,' ',''))=LOWER(REPLACE('"+brandname+"',' ',''))) THEN A.id ELSE -1 END)"
                                    +"AS \"status\" FROM product_list A;";
                 ps=Database.con.prepareStatement(getproduct);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      vendorId=resultset.getInt("status");
                      if(vendorId!=-1)
                      {
                        break;
                      }
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return vendorId;        
     }
     
     public int getProductcount(int productid)
     { 
         int count=0;
         try
           {
                 String getcount="SELECT total_count FROM stock WHERE product_id="+productid+";";
                 ps=Database.con.prepareStatement(getcount);
                 resultset=ps.executeQuery();
                 if(resultset.next())
                 {
                    count=resultset.getInt("total_count");
                 }
             }
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return count;                        
     }     
     
     public int getPurchasecount(int productid,int custid)
     { 
         int count=0;
         try
           {
                 String getcount="SELECT MAX(product_count) AS \"count\" FROM sale_details WHERE (product_id="+productid+") AND (return_availability='true') AND (customer_id="+custid+");";
                 ps=Database.con.prepareStatement(getcount);
                 resultset=ps.executeQuery();
                 if(resultset.next())
                 {
                    count=resultset.getInt("count");
                 }
             }
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return count;                        
     }      
     
     public String getCustname(int uName,int password)
     {
         String name=null;
         try
           {
            String custname="SELECT (CASE WHEN (A.user_id="+uName+") AND (A.password="+password+") AND (A.id=B.login_id) THEN B.customer_name ELSE null END)" 
                            +"AS \"cust\" FROM login A INNER JOIN customer_list B ON login_id=A.id;";
                 ps=Database.con.prepareStatement(custname);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      name=resultset.getString("cust");
                      if(name!=null)
                      {
                        break;
                      }
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return name;        
     }
     
     public String getAdminname(int uName,int password)
     {
         String name=null;
         try
           {
                 String custname="SELECT (CASE WHEN (A.user_id="+uName+") AND (A.password="+password+") AND (A.id=B.login_id) THEN B.admin_name ELSE null END)" 
                            +"AS \"cust\" FROM login A INNER JOIN admin_list B ON login_id=A.id;";
                 ps=Database.con.prepareStatement(custname);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      name=resultset.getString("cust");
                      if(name!=null)
                      {
                        break;
                      }
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return name;        
     }                                     
     
     
     public void updateStock(int productid,int count)
     {
        try
          {
                 String stock="UPDATE stock SET total_count=total_count+"+count+" WHERE id="+productid+";";
                 ps=Database.con.prepareStatement(stock);
                 ps.executeUpdate();                 
          }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }                  
     }
     
     public void updatePurchase(int custid,int productid,int count,int productcount)
     {
         try
           {
                String update="UPDATE sale_details SET product_count=product_count-"+count+" WHERE product_id="+productid+" AND customer_id="+custid+" AND return_availability='true' "
                              +"AND product_count="+productcount+";";
                 ps=Database.con.prepareStatement(update);
                 ps.executeUpdate();                 
          }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }                  
     }   
     
     
     public void sellStock(int productid,int count)
     {
        try
          {
                 String stock="UPDATE stock SET total_count=total_count-"+count+" WHERE product_id="+productid+";";
                 ps=Database.con.prepareStatement(stock);
                 ps.executeUpdate();                 
          }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }          
        
     } 
     
     public boolean isTableExist()
     {
         boolean flag=false;
         try
           {
                 String isexists="SELECT EXISTS(SELECT id from product_list) AS \"status\";";
                 ps=Database.con.prepareStatement(isexists);
                 resultset=ps.executeQuery();
                 if(resultset.next())
                 {
                   flag=resultset.getBoolean("status");
                 }
             }
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;                        
     }  
     
     public boolean isReturnExist(int custid,String table)
     {
         boolean flag=false;
         try
           {
                 String isexists="SELECT EXISTS(SELECT id from "+table+" WHERE customer_id="+custid+") AS \"status\";";
                 ps=Database.con.prepareStatement(isexists);
                 resultset=ps.executeQuery();
                 if(resultset.next())
                 {
                   flag=resultset.getBoolean("status");
                 }
             }
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;                        
     }          
     
     public boolean isAdminexists()
     {
         boolean flag=false;
         try
           {
                 String isexists="SELECT EXISTS(SELECT id from admin_list WHERE (admin_mobileno!=9842251585) AND (admin_status='true')) AS \"status\";";
                 ps=Database.con.prepareStatement(isexists);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      flag=resultset.getBoolean("status");
                      if(flag)
                      {
                        break;
                      }
                    }               
            }
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;                        
     }               
     
     public boolean verifyAccount(String name,int id,String tablename,String field)
     {
       boolean flag=false;
       try
         {
                 String check="SELECT (CASE WHEN (id="+id+") AND (LOWER("+field+")=LOWER('"+name+"')) THEN 'true' ELSE 'false' END)AS \"status\" FROM "+tablename+";";
                 ps=Database.con.prepareStatement(check);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      flag=resultset.getBoolean("status");
                      if(flag)
                      {
                        break;
                      }
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return flag;                
     }
     public void removeVendor(String vendorname,int vendorid)
     {
          try
            {
                 String remove="UPDATE vendor_list SET vendor_status='false' WHERE LOWER(vendor_name)=LOWER('"+vendorname+"') AND id="+vendorid+";";
                 ps=Database.con.prepareStatement(remove);
                 ps.executeUpdate();
            }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }                 
     }
     
     public void removeAdmin(String adminame,int adminid)
     {
          try
            {
                 String remove="UPDATE admin_list SET admin_status='false' WHERE LOWER(admin_name)=LOWER('"+adminame+"') AND id="+adminid+";";
                 ps=Database.con.prepareStatement(remove);
                 ps.executeUpdate();
            }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }                 
     } 
     
     public void removeProduct(String productname,String brandname)
     {
          try
            {
                String remove="UPDATE product_list SET product_status='false' WHERE LOWER(product_name)=LOWER('"+productname+"') AND LOWER(brand_name)=LOWER('"+brandname+"');";
                 ps=Database.con.prepareStatement(remove);
                 ps.executeUpdate();
            }
        catch(SQLException e)
          {
                 e.printStackTrace();
          }                 
     }                     
      
     public int getCustid(int userId,int password)
     {
        int custid=0;
        try
          {
               String getCustid="SELECT (CASE WHEN (A.user_id="+userId+") AND (A.password="+password+") AND (A.id=B.login_id) THEN B.id ELSE -1 END)"
                                 +"AS \"custid\" FROM login A INNER JOIN customer_list B ON login_id=A.id;";
                 ps=Database.con.prepareStatement(getCustid);
                 resultset=ps.executeQuery();
                 while(resultset.next())
                    {
                      custid=resultset.getInt("custid");
                      if(custid!=-1)
                      {
                        break;
                      }
                    }
           }                                       
          catch(SQLException e)
            {
                 e.printStackTrace();
            }
         return custid;        
     }
     
     public ArrayList printStatement(int days,int custid)
     {
        ArrayList<Printstatement> al=new ArrayList<Printstatement>();
        String fromDate=String.valueOf(localdate.minusDays(days));
        String toDate=String.valueOf(localdate.now());
       try
         {
             String statement="SELECT A.product_name,A.brand_name,B.product_count,B.date FROM sale_details B INNER JOIN product_list A ON B.product_id=A.id "
                               +"WHERE B.date BETWEEN '"+fromDate+"' AND '"+toDate+"' AND A.id=B.product_id AND B.customer_id="+custid+";";
                               ps=Database.con.prepareStatement(statement);
                               resultset=ps.executeQuery();
                        while(resultset.next())
                         {
                         al.add(new Printstatement(String.valueOf(resultset.getDate("date")),resultset.getString("product_name"),resultset.getString("brand_name"),resultset.getInt("product_count")));
                         }
         }
       catch(SQLException e)
        {
            e.printStackTrace();
        }
        return al; 
     }
     
     public ArrayList printSale(int days,int custid)
     {
        ArrayList<Printstatement> sale=new ArrayList<Printstatement>();
        String fromDate=String.valueOf(localdate.minusDays(days));
        String toDate=String.valueOf(localdate.now());
       try
         {
             String statement="SELECT A.product_name,A.brand_name,B.product_count,B.date FROM return_details B INNER JOIN product_list A ON B.product_id=A.id "
                               +"WHERE B.date BETWEEN '"+fromDate+"' AND '"+toDate+"' AND A.id=B.product_id AND B.customer_id="+custid+";";
                               ps=Database.con.prepareStatement(statement);
                               resultset=ps.executeQuery();
                  while(resultset.next())
                     {
                      sale.add(new Printstatement(String.valueOf(resultset.getDate("date")),resultset.getString("product_name"),resultset.getString("brand_name"),resultset.getInt("product_count")));
                     }
         }
       catch(SQLException e)
        {
            e.printStackTrace();
        }
        return sale; 
     }     
     
     public ArrayList saleStatement(int days)
     {
        ArrayList<Printstatement> al=new ArrayList<Printstatement>();
        String fromDate=String.valueOf(localdate.minusDays(days));
        String toDate=String.valueOf(localdate.now());
       try
         {
             String statement="SELECT A.product_name,A.brand_name,B.product_count,C.customer_name,B.date  FROM sale_details B INNER JOIN product_list A ON B.product_id=A.id "
                               +"INNER JOIN customer_list C ON C.id= B.customer_id WHERE B.date BETWEEN '"+fromDate+"' AND '"+toDate+"' AND A.id=B.product_id;";
                               ps=Database.con.prepareStatement(statement);
                               resultset=ps.executeQuery();
                        while(resultset.next())
                         {
                         al.add(new Printstatement(String.valueOf(resultset.getDate("date")),resultset.getString("product_name"),resultset.getString("brand_name"),
                         resultset.getString("customer_name"),resultset.getInt("product_count")));
                         }
         }
       catch(SQLException e)
        {
            e.printStackTrace();
        }
        return al; 
     }
     
     public ArrayList purchaseStatement(int days)
     {
        ArrayList<Printstatement> al=new ArrayList<Printstatement>();
        String fromDate=String.valueOf(localdate.minusDays(days));
        String toDate=String.valueOf(localdate.now());
       try
         {
             String statement="SELECT A.product_name,A.brand_name,B.count,B.date,C.vendor_name FROM purchase_details B INNER JOIN product_list A ON B.product_id=A.id "
                               +"INNER JOIN vendor_list C ON B.vendor_id=C.id WHERE B.date BETWEEN '"+fromDate+"' AND '"+toDate+"' AND A.id=B.product_id;";
                               ps=Database.con.prepareStatement(statement);
                               resultset=ps.executeQuery();
                        while(resultset.next())
                         {
                          al.add(new Printstatement(String.valueOf(resultset.getDate("date")),resultset.getString("product_name"),
                          resultset.getString("brand_name"),resultset.getString("vendor_name"),resultset.getInt("count")));
                         }
         }
       catch(SQLException e)
        {
            e.printStackTrace();
        }
        return al; 
     }   
     
     public ArrayList returnStatement(int days)
     {
        ArrayList<Printstatement> al=new ArrayList<Printstatement>();
        String fromDate=String.valueOf(localdate.minusDays(days));
        String toDate=String.valueOf(localdate.now());
       try
         {
             String statement="SELECT A.product_name,A.brand_name,B.product_count,C.customer_name,B.date  FROM return_details B INNER JOIN product_list A ON B.product_id=A.id "
                               +"INNER JOIN customer_list C ON C.id= B.customer_id WHERE B.date BETWEEN '"+fromDate+"' AND '"+toDate+"' AND A.id=B.product_id;";
                               ps=Database.con.prepareStatement(statement);
                               resultset=ps.executeQuery();
                        while(resultset.next())
                         {
                         al.add(new Printstatement(String.valueOf(resultset.getDate("date")),resultset.getString("product_name"),resultset.getString("brand_name"),
                         resultset.getString("customer_name"),resultset.getInt("product_count")));
                         }
         }
       catch(SQLException e)
        {
            e.printStackTrace();
        }
        return al; 
     }         
          
     public static byte moreStuff()throws SQLException
     {
          byte choice=0;
          System.out.println("do u want to continue (Yes-y/No-n) : ");
          if (userinput.next().equalsIgnoreCase("n")) 
	  {        
	     if(Database.con!=null||Database.st!=null)
	       {
	           Database.con.close();
	           Database.st.close();
	       }
		   userinput.close();
		   choice = -1;
          }
       return choice;
     }
       
} 
