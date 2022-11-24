package BankManagement;
import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import BankManagement.Login;
class InsertElement extends BankController
{
 Statement st=null;
 int accid=0,custid=0;
 PreparedStatement ps=null;
 ResultSet rs=null;
 public void insertOrder()
 {
    InsertElement ie=new InsertElement();
    ie.insertAcc();
    ie.insertLog();
    ie.insertAccdetails(ie.custid,ie.accid);
 }
 public void insertAcc() 
 {
     try
      {
            String sql = "INSERT INTO customerdetails"+ " (Username, Phonenumber, dob,gender,Aadharnumber,Address) VALUES "+" (?, ?, ?, ?, ?,?)RETURNING Id;";
			ps=DBConnection.con.prepareStatement(sql);
			ps.setString(1, ac.getUserName());
			ps.setString(2,ac.getPhoneNumber());
			ps.setDate(3,Date.valueOf(ac.getDOB()));
			ps.setString(4,ac.getGender());
			ps.setString(5,ac.getAadharNumber());
			ps.setString(6,ac.getAddress().toString());
			rs=ps.executeQuery();
			rs.next();
			accid=rs.getInt("Id");
      } 
    catch(Exception e) 
      {
	    System.out.println(e);
      }
  }
  public void insertLog() 
  {
    try
      {
              String sql="INSERT INTO login" + " (accNo, password) VALUES "+"(?,?)RETURNING Id;";
         			ps=DBConnection.con.prepareStatement(sql);
         			ps.setString(1,ac.getaccNo());
         			ps.setInt(2,ac.getPassword());
         			rs=ps.executeQuery();
			        rs.next();
			        custid=rs.getInt("Id");
      }
    catch(Exception e) 
      {
	    System.out.println(e);
      }
  }
/*  public void insertBranch() throws SQLException
  {
    try
      {
              String sql="INSERT INTO branches" + " (name) VALUES "+"(?);";
         			ps=con.prepareStatement(sql);
         			ps.setString(1,ac.getbranch());
         			ps.executeUpdate();
      }
    catch(SQLException e) 
      {
	    System.out.println(e);
      }
  }
  public void insertAcctype() throws SQLException
  {
    try
      {
              String sql="INSERT INTO accounttype" + " (account_type) VALUES "+"(?);";
         			ps=con.prepareStatement(sql);
         			ps.setString(1,ac.getaccType());
         			ps.executeUpdate();
      }
      }
    catch(SQLException e) 
      {
	    System.out.println(e);
      }
  }*/
  
   public void insertAccdetails(int custid,int accid)
   {
     try
       {
             String sql="INSERT INTO accountdetails" +"(accountno, custId ,accId, branch_id, acctype_id, minBalance) VALUES (?,?,?,?,?,?);";  
                                ps=DBConnection.con.prepareStatement(sql);
                                ps.setString(1,ac.getaccNo());
                                ps.setInt(2,custid);
                                ps.setInt(3,accid); 
                                ps.setInt(4,ac.getbranch());
                                ps.setInt(5,ac.getaccType()); 
                                ps.setLong(6,ac.getAccountBalance()); 
                                ps.executeUpdate();                
       }
      catch(Exception e) 
      {
	    System.out.println(e);
      } 
   }
   public boolean insertTransaction(int accid,String accno,String sender,int amount,String description,int balance)throws Exception
   {
   boolean flag=false;
     try
       {
             String sql="INSERT INTO transaction"+ "(accId, accountnumber, accReceiver, amount, currDate, description, balance) VALUES (?,?,?,?,?,?,?);";
                               ps=DBConnection.con.prepareStatement(sql);   
                               ps.setInt(1,accid);
                               ps.setString(2,accno);
                               ps.setString(3,sender);
                               ps.setInt(4,amount);
                               ps.setDate(5,Date.valueOf(java.time.LocalDate.now()));
                               ps.setString(6,description);
                               ps.setInt(7,balance);
                               ps.executeUpdate(); 
                               flag=true;
       } 
     catch(Exception e)
      {
         e.printStackTrace();
      } 
      return flag;
    }
    public boolean authenticate(String accno, int password)
    { 
      boolean flag=false;
      try
      {
        String authentication = "SELECT (CASE WHEN (A.accno='"+accno+"') AND (A.password="+password+") And (B.status='true') THEN 'true' ELSE 'FALSE' END)AS \"result\" from login A "+
        "inner join accountdetails B ON B.custid=A.id;";  
        ps=DBConnection.con.prepareStatement(authentication);
        rs=ps.executeQuery();
        while(rs.next())
        {
         flag=rs.getBoolean("result");
         if(flag)
         {
          break;
         }
        }
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      return flag;
    }
    public boolean authenticate(String accReceiver)
     {
       boolean flag=false;
       String verifyQuery=null;
     try
     {
        verifyQuery="SELECT (CASE WHEN(accountno='"+accReceiver+"') AND (status='true') THEN 'true' ELSE 'false' END)As \"result\" from accountdetails;";
        ps=DBConnection.con.prepareStatement(verifyQuery);
        rs=ps.executeQuery();
        while(rs.next())
        {
         if(flag=rs.getBoolean("result"))
         {
          break;
         }
        }
      }
    catch(Exception e)
     {
         e.printStackTrace();
     }
   return flag;
    } 
    public int getBalance(String accno)
    {
       int balance=0;
       String balance_query="SELECT MINBALANCE FROM accountdetails WHERE accountno='"+accno+"'";
       try
       {
            ps=DBConnection.con.prepareStatement(balance_query);
            rs = ps.executeQuery();
            rs.next();
            balance = rs.getInt("MINBALANCE");
       }
       catch(SQLException e)
       {
           System.out.println("Input Mismatched!!!!\nkindly check your account number.");
       }
        
        return balance;
    }
    public void updateBalance(int balance,String accno)
    {
       String update_balance = "UPDATE accountdetails SET MINBALANCE ="+balance+"WHERE accountno = '"+accno+"'";
       try
       {
           ps=DBConnection.con.prepareStatement(update_balance);
           ps.executeUpdate();
       }
       catch(SQLException e)
       {
           System.out.println("Input Mismatched!!!!\nkindly check your account number.");
       }
    }
    public void updateWithdraw(int balance)
    {
       String withdrwal = "UPDATE accountdetails SET MINBALANCE ="+balance+"WHERE accountno = '"+accno+"'";
       try
       {
           ps=DBConnection.con.prepareStatement(withdrwal);
           ps.executeUpdate();
       }
       catch(SQLException e)
       {
           System.out.println("Input Mismatched!!!!\nkindly check your account number.");
       }
    }
    public int getAccid(String column,String table,String condition,String recvaccno)
    {
     try
     {
      String id="SELECT "+column+"  FROM "+table+" WHERE "+condition+"='"+recvaccno+"';";
      ps=DBConnection.con.prepareStatement(id);
      rs = ps.executeQuery();
      rs.next();
      accid=rs.getInt("accid");
     }
     catch(Exception e)
     { 
      e.printStackTrace();
     }
     return accid;
    }
    public String getName(String name)
    {
     String getname=null;
     int id=getAccid("accid","accountdetails","accountno",name);
     String Name="SELECT username FROM customerdetails WHERE id="+id+"";
       try
       {
            ps=DBConnection.con.prepareStatement(Name);
            rs = ps.executeQuery();
            rs.next();
            getname = rs.getString("username");
       }
       catch(Exception e)
       {
        e.printStackTrace();
       }
       return getname;
    }
    public void updateQuery(int balance,String accno)
    {
     String query=null;
     try
     {
      query="UPDATE accountdetails SET minbalance = "+balance+" WHERE accountno='"+accno+"';";
      ps=DBConnection.con.prepareStatement(query);
      ps.executeUpdate();
     }
    catch(Exception e)
    {
     System.out.println("Contact Bank Staff's!!!");
    }
   }
   public ArrayList printStatement(String accno)
   {
     String printQuery=null;
     ArrayList<Printstatement> al=new ArrayList<Printstatement>();
    try
    {
      printQuery="SELECT accountnumber,accreceiver,amount,currdate,description,balance FROM transaction WHERE accountnumber='"+accno+"'";
      ps=DBConnection.con.prepareStatement(printQuery);
      rs=ps.executeQuery();
    while(rs.next())
     {
      al.add(new Printstatement(rs.getString("accountnumber"),rs.getString("accreceiver"),rs.getInt("amount"),rs.getDate("currdate"),rs.getString("description"),rs.getInt("balance")));
     }
    }
    catch(Exception e)
    {
     e.printStackTrace();
    }
    return al;
   }
   public int getPass()
   {
    int pw=0;
    InsertElement Ie=new InsertElement();
    try
    {
    Console con = System.console();      
    char[] ch=con.readPassword();       
    pw=Integer.parseInt(String.valueOf(ch)); 
    }
    catch(Exception e)
    {
     System.out.println("Wrong Password!!!!!!!!!!!");
     System.out.print("ReEnter Password:");
     Ie.getPass();
    }
    return pw; 
   } 
 }
