package BankManagement;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Random; 
import java.util.Date;
class Login 
{
 Scanner sc=new Scanner(System.in);
 static Login log=null;
 static AccountHolder ac=null;
 public static long balance=0;
 String accNo=null;
 static String accno=null;
 static int password=0;
 char ch=' ';
 InsertElement Ie=null;
 BankProcess BP=null;
 public void newUser()throws Exception
 {
  Ie=new InsertElement();
  Random random=new Random();
  System.out.println("Enter the Username:");
  String username=sc.nextLine();
  System.out.println("Enter the Phone Number:");
  String phonenumber=sc.nextLine();
  phonenumber=checkMobile(phonenumber,sc);
  System.out.println("Enter the DOB(yyyy-MM-dd):");
  String DOB=sc.nextLine();
  DOB=checkDob(DOB,sc);
  System.out.println("Enter the Gender:\nm-Male\nf-Female\no-Others");
  ch=sc.next().charAt(0);
  String gender=checkGender(ch,sc);
  System.out.println("Enter your Aadhar Number(must be 12 digits!!!!!)");
  String aadharnumber=sc.nextLine();
  aadharnumber=checkAadhar(aadharnumber,sc);
  Address adr=getAddress(sc);
  int branch=getBranch(sc);
  accNo=String.valueOf(System.currentTimeMillis()/100);
  int accType=getAcctype(sc);
  password=(int)(Math.random()*9000)+1000;
  balance=getBalance(sc);
  ac=new AccountHolder(username,phonenumber,DOB,gender,aadharnumber,branch,accNo,accType,password,balance,adr);
  Ie.insertOrder();
  System.out.println("Do you want to continue-(Yes-Y,No-N)");
  char ch=sc.next().charAt(0);      
  while(Character.toUpperCase(ch) != 'Y' && Character.toUpperCase(ch) != 'N')
  {
    System.out.println("Give Valid Input-(Yes-Y,No-N)");
    ch=sc.next().charAt(0);             
  }
  switch(Character.toUpperCase(ch))
  {
     case 'Y':
     BankManagement.main(null);
     break;
     
     case 'N':
     System.out.println("THANK YOU FOR VISIT!!!!!!!");
     break;
  }
 }
  public void existUser()throws Exception
  {
        BP=new BankProcess();
        Ie=new InsertElement();
	System.out.print("AccountNumber:");
        accno=sc.nextLine();
	System.out.print("Password:");
	int password=Ie.getPass();
	boolean flag = Ie.authenticate(accno,password);
	if(flag)
	{
	   BP.process();
	}
	else
	{
	    password=0;
	    flag=false;
	    log=new Login();   
	    System.out.println("Username or Password Incorrect!!!!!!");
	    System.out.print("AccountNumber:");
            accno=sc.nextLine();
	    System.out.print("Password:");
	    password=Ie.getPass();
	    flag = Ie.authenticate(accno,password);
	    if(flag)
	    {
	      BP.process();
            }
            else
            {
             log=new Login();
             System.out.println("Username or Password Incorrect!!!!!!");
             log.existUser();
            }
	}
}
  private String checkMobile(String phonenumber,Scanner sc)
  {
     phonenumber=phonenumber.replaceAll("\\s","");
     boolean flag=(phonenumber.length()==10)?isNumeric(phonenumber):false;
     while(flag!=true)
     {
        phonenumber=null;
        System.out.println("Input 10 digits Mobile Number: ");
        phonenumber=sc.nextLine();
        flag=(phonenumber.length()==10)?isNumeric(phonenumber):false;
     }
     return phonenumber;
  }
  private String checkDob(String dob,Scanner sc)throws Exception
  {
     String[] arr=dob.split("-");
     while(Integer.valueOf(arr[0])>2012||Integer.valueOf(arr[1])>12&&Integer.valueOf(arr[2])>31)
     {
       dob=null;
       arr=null;
       System.out.println("Input the Date in Correct Format(yyyy/MM/dd):");
       dob=sc.nextLine();
       arr=dob.split("-");   
     }
      return dob;
  }  
  private String checkGender(char ch,Scanner sc)
  { 
  String gender=null;
  boolean flag=true;
  while(flag)
      {
       // ch=sc.next().charAt(0);   
        if('M' == Character.toUpperCase(ch))
            {
              gender="Male";
              flag=false;
            }   
        else if('F'==Character.toUpperCase(ch))
           {
             gender="Female";
             flag=false;
           }
        else if('O'==Character.toUpperCase(ch))
          {
            gender="Others";
            flag=false;
          }
        else
          {
            System.out.println("Give a valid input!!!!!!!!");
          }
      }
      return gender;
  }
  private String checkAadhar(String aadharnumber,Scanner sc)
  {
     aadharnumber=aadharnumber.replaceAll("\\s","");
     boolean flag=(aadharnumber.length()==12)?isNumeric(aadharnumber):false;
     while(flag!=true)
     {
        aadharnumber=null;
        System.out.println("Input 12 digits AadharNumber: ");
        aadharnumber=sc.nextLine();
        flag=(aadharnumber.length()==12)?isNumeric(aadharnumber):false;
     }
   return aadharnumber;
  }
  private boolean isNumeric(String number)
  {
      for(char c:number.toCharArray())
      {
         if(Character.isDigit(c)==false)
         {
           return false;
         }
      }
    return true;
  }
 private Address getAddress(Scanner sc)
  {
	System.out.println("DoorNo: ");
	String door=sc.nextLine();
	System.out.println("Street-Name: ");
	String street=sc.nextLine();
	System.out.println("City: ");
	String city=sc.nextLine();
	System.out.println("Pincode: ");
	int pincode=sc.nextInt();
	Address adr=new Address(door,street,city,pincode);
	return adr; 
  }
 private long getBalance(Scanner sc)
   {
	System.out.println("Please set Minimum balance for your account(minimum thousand rupees): ");
	long balance=sc.nextLong();
	long rem=0;
	while(balance<1000)
	{
	  balance=rem+balance;
	  rem=1000-balance;
	  System.out.println("Minimum Balance Should be Rs:1000\nYour balance Rs:"+balance+" \nPlease give Remaining Rs:"+rem);
	  rem=sc.nextLong();
	  if(rem==0)
	  {
	    break;
	  }
	}
	return balance;
   }
 private int getAcctype(Scanner sc)
  {
       System.out.println("Give your account type:\n1.Savings\n2.Current"); 
       int input=sc.nextInt();
       while(input!=1&&input!=2)
       {
        input=0;
        System.out.println("Give Valid Input!!!!!!!!!!!!!\n1.Savings\n2.Current");
        input=sc.nextInt();
       }
       String accType=(input==1)?"Savings":"Current";
       return input;
  }
 private int getBranch(Scanner sc)
  {
       System.out.println("Give your Branch:\n1.Srivilliputhur\n2.RajaPalayam"); 
       int input=sc.nextInt();
       while(input!=1&&input!=2)
       {
        input=0;
        System.out.println("Give valid Input!!!!!!!!!!!!!\n1.Srivilliputhur\n2.RajaPalayam");
        input=sc.nextInt();
       }
       String branch=(input==1)?"Srivilliputhur":"RajaPalayam";
       return input;
  }
 }
