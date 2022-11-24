package BankManagement;
public class AccountHolder
{
  private String username;
  private String phonenumber;
  private String DOB;
  private String gender;
  private String aadharnumber;
  private int password;
  private int branch;
  private String accNo;
  private int accType;
  private long accountbalance;
  private Address adr=null;
  public AccountHolder(String username,String phonenumber,String DOB,String gender,String aadharnumber,int branch,String accNo,int accType,int password,long accountbalance,Address adr)
  {
       this.username=username;
       this.phonenumber=phonenumber;
       this.DOB=DOB;
       this.gender=gender;
       this.aadharnumber=aadharnumber;
       this.branch=branch;
       this.accNo=accNo;
       this.accType=accType;
       this.password=password;
       this.accountbalance=accountbalance;
       this.adr=adr;
  }
  public void setUserName(String username)
  {
   this.username=username;
  }  
  public String getUserName()
  {
   return username;
  }
  public void setPhoneNumber(String phonenumber)
  {
   this.phonenumber=phonenumber;
  }
  public String getPhoneNumber()
  {
   return phonenumber;
  }
  public void setDOB(String DOB)
  {
   this.DOB=DOB;
  }  
  public String getDOB()
  {
    
    return DOB;
  }
/*  public void setGender(String gender)
  {
   this.gender=gender;
  }*/ 
  public String getGender()
  {
    return gender;
  }
  public void setAadharNumber(String aadharnumber)
  {
    this.aadharnumber=aadharnumber;
  } 
  public String getAadharNumber()
  {
    return aadharnumber;
  }
  public void setpassword(int password)
  {
    this.password=password;
  } 
  public int getPassword()
  {
    return password;
  }
  /* public void setAccNo(String accno)
  {
   this.accno=accno;
  }*/ 
  public String getAccNo()
  {
    return accNo;
  }
  public void setAddress(String doorno,String streetname,String city,int pincode)
  {
   this.adr=new Address(doorno,streetname,city,pincode);
  }
  public Address getAddress()
  {
   return adr;
  }
  public void setAccountBalance()
  {
   this.accountbalance=accountbalance;
  }
  public long getAccountBalance()
  {
   return accountbalance;
  }
  public String getaccNo()
  {
   return accNo;
  }
  public int getaccType()
  {
   return accType;
  }
   public int getbranch()
  {
   return branch;
  }
}
  
   
