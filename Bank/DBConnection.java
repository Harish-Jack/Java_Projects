package BankManagement;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDate;
import org.postgresql.util.PSQLException;
public class DBConnection
{
 LocalDate localdate =LocalDate.now();
 static Connection con=null;
 static Statement st=null;
 private DBConnection()
{
 
}
public static void resultOrder()throws SQLException,Exception
 {
  DBConnection db=new DBConnection();
  db.checkConnection();
  st=con.createStatement();
  db.createAcc();
  db.createLog();
  db.createBranch();
  db.createAcctype();
  db.createBankdetails();
  db.createTrans();
  
 }
 private Connection checkConnection()throws SQLException,Exception
 {
  if(con==null)
  {
    DBConnection();
  }
  return con;
 }
 public static Connection DBConnection()
 {
   String url="jdbc:postgresql://localhost:5432/bankmanagement";
   String user="postgres";
   String password="1234";
   try
   {
     con=DriverManager.getConnection(url,user,password);
   }
   catch(SQLException e)
   {
    e.printStackTrace();
   }
   System.out.println("Weclcome");
   return con;
 }
 private static void createAcc()throws SQLException,Exception
  {
  String accHolder=("CREATE TABLE IF NOT EXISTS customerdetails("+ "Id SERIAL primary key,"
	             + "Username varchar(30) not null,"      + "Phonenumber varchar(20) not null,"
	             + "dob Date not null,"                  + "gender varchar(10) not null,"
	             + "Aadharnumber varchar(20) not null,"  + "Address varchar(40) not null);");
  st.executeUpdate(accHolder);
  }
  private static void createLog()throws SQLException,Exception
  {
       String login="CREATE TABLE IF NOT EXISTS login(" +"id SERIAL PRIMARY KEY,"
                     +"accNo VARCHAR(20) NOT NULL,"+"password integer NOT NULL)";
       st.executeUpdate(login);
  }
  private static void createBranch()throws SQLException,Exception
  {
  String branch="CREATE TABLE IF NOT EXISTS branches(id SERIAL PRIMARY KEY NOT NULL,"
                 +"name VARCHAR(20) NOT NULL)";
  st.executeUpdate(branch);
  }
  private static void createAcctype()throws SQLException,Exception
  {
  String accType="CREATE TABLE IF NOT EXISTS accounttype(id SERIAL PRIMARY KEY NOT NULL,"
                 +"account_type varchar(20) NOT NULL)";
  st.executeUpdate(accType);
  }
  private static void createBankdetails()throws SQLException,Exception
  {
  String bankdetails="CREATE TABLE IF NOT EXISTS accountdetails("
           +"id SERIAL PRIMARY KEY,"+"accountno VARCHAR(20) NOT NULL,"+"custId integer NOT NULL,"+"accId integer NOT NULL,"
           +"branch_id INTEGER NOT NULL,acctype_id INTEGER NOT NULL,"+"CONSTRAINT fk_acctype FOREIGN KEY(acctype_id) REFERENCES accounttype(id),"
           +"CONSTRAINT fk_branch FOREIGN KEY(branch_id) REFERENCES branches(id),CONSTRAINT fk_login FOREIGN KEY(custId) REFERENCES login(id),"
           +"minBalance integer NOT NULL,"+"CONSTRAINT fk_accholder FOREIGN KEY(accId) REFERENCES customerdetails(id),"
           +"status BOOLEAN DEFAULT '1')";  
  st.executeUpdate(bankdetails);
  }
  private static void createTrans()throws SQLException,Exception
  {
  String transaction="CREATE TABLE IF NOT EXISTS transaction("
          +"id SERIAL PRIMARY KEY,"+"accId integer NOT NULL,"+"CONSTRAINT fk_accholder FOREIGN KEY(accId) REFERENCES customerdetails(id),"
          +"accountnumber VARCHAR(20) NOT NULL,"+"accReceiver VARCHAR(20) NULL,"+"amount integer NOT NULL,"+"currDate timestamp NOT NULL,"
          +"description VARCHAR(50) NULL,balance INTEGER NOT NULL)";
  st.executeUpdate(transaction);
  }
}
  
