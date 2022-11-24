package BankManagement; 
import java.util.Scanner;
import java.util.ArrayList;
class BankController extends Login implements Bank 
{
 InsertElement ie=null;
 BankProcess bp=null;
 Scanner sc=new Scanner(System.in);
 public void deposit()
 {
  try
  {
  ie=new InsertElement();
  bp =new BankProcess();
  int accid=ie.getAccid("accid","accountdetails","accountno",Login.accno);
  System.out.println("Enter a deposit amount:");
  String description="self_deposit",sender="";
  int amount=sc.nextInt();
  if(amount<50000 && amount%100==0)
  {
    int balance=ie.getBalance(accno);
    balance += amount;
    ie.updateBalance(balance,accno);
    System.out.println("-----------------------------------\nDeposit Successful!!!!!!!!\nDeposited:"+amount+"\nBalance:"+balance+"\n-----------------------------------");
    ie.insertTransaction(accid,accno,sender,amount,description,balance);
  }
  else
  {
    if(amount>50000)
    {
     System.out.println("Depoit Limit Reached!!!!!");
    }
    else
    {
     System.out.println("Rs:100-RS:200-RS:500-Rs-2000 Notes Only Accepted.");
    }
  }
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
	  bp.process();
	  break;
	
	  case 'N':
	  DBConnection.con.close();
	  sc.close();
	  System.out.println("THANK YOU FOR VISIT!!!!!!!");
	  break;
      }
    }
    catch(Exception e)
    {
     e.printStackTrace();
    } 
 }
 public void withdraw()
 {
  try
   {
    ie=new InsertElement();
    bp =new BankProcess();
    int balance=ie.getBalance(accno);
    String description="self_withdraw",sender="";
    int accid=ie.getAccid("accid","accountdetails","accountno",Login.accno);
    System.out.println("Your Account Balance:"+balance);
    System.out.println("RS:500-Rs-2000 Notes Only Have");
    System.out.println("Enter a withdraw amount:");
    int wd=sc.nextInt();   
    if(balance>wd && wd%100==0)
    {
         balance -= wd;
         ie.updateWithdraw(balance);
         System.out.println("-----------------------------------\nwithdraw Successful!!!!!!!!\nWithdrwal:"+wd+"\nBalance:"+balance+"\n-----------------------------------");
         ie.insertTransaction(accid,accno,sender,-wd,description,balance);
    }
    else
    {
    if(balance<wd)
    {
         System.out.println("Withdraw Limit Reached!!!!!");
    }
    else
    {
         System.out.println("RS:500-Rs-2000 Notes Only Have.");
    }
    }
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
	  bp.process();
	  break;
	
	  case 'N':
	  DBConnection.con.close();
	  sc.close();
	  System.out.println("THANK YOU FOR VISIT!!!!!!!");
	  break;
       }
      }
      catch(Exception e) 
      {
       e.printStackTrace();
      }
 }
 public void transaction()
 {
    try
    {
     boolean flag=false;
     ie=new InsertElement();
     int senderBalance=ie.getBalance(accno);
     System.out.println("Enter Receiver Account No: ");
     String accReceiver=sc.nextLine();
     if(Login.accno.equals(accReceiver)==false)
     {     
     flag=ie.authenticate(accReceiver);
     }
     while(flag==false) 
     {
      System.out.println("Enter Valid Receiver Account No: ");
      accReceiver=sc.nextLine();
      flag=ie.authenticate(accReceiver);
     }
     String name=ie.getName(accReceiver);
     System.out.println("Receiver Account Name: "+name);
     System.out.println("Do you want to Continue?(Y-yes    N-no): ");
     char ch=sc.next().charAt(0); 
     if(ch=='y'||ch=='Y')
     {
      if(transProcess(accReceiver,senderBalance,ie))
      {
       senderBalance=ie.getBalance(accno);
       System.out.println("Amount Transferred Successfully....");
       System.out.println("Your Currect Account Balance: "+senderBalance);
       System.out.println("Do You Want to Continue?(Y-yes    N-no): ");
       ch=sc.next().charAt(0);
       if(ch=='y'||ch=='Y')
       {
        BankProcess bp=new BankProcess();
        bp.process();
       }
      }
     }
    }
    catch(Exception e)
    {
     e.printStackTrace();
    }
 }
  private boolean transProcess(String accReceiver,int senderBalance,InsertElement ie)
    {
     int accid=0,amountTrans=0,receiverBalance=0;
     String describe=null;
     boolean flag=false;
      try
      {
        accid=ie.getAccid("accid","accountdetails","accountno",Login.accno);
        System.out.println("Enter Amount for transaction: ");
        amountTrans=sc.nextInt();
        while(amountTrans>senderBalance||amountTrans>100000)
        {
           System.out.println("Enter valid Transaction Amount!!!..");
           amountTrans=sc.nextInt();
        }
        System.out.println("Enter Any Description: ");
        sc.nextLine();
        receiverBalance=ie.getBalance(accReceiver);
        receiverBalance=receiverBalance+amountTrans;
        describe=sc.nextLine();
        senderBalance=senderBalance-amountTrans;
        ie.updateQuery(senderBalance,accno);
        ie.updateQuery(receiverBalance,accReceiver);
        flag=ie.insertTransaction(accid,accno,accReceiver,amountTrans,describe,senderBalance);
     }
     catch(Exception e)
     {
      e.printStackTrace();
     }
     return flag;
     }
 public void printStatement()
 {
  ie=new InsertElement();
  String format=String.format("%15s %15s %15s %15s %15s %15s","DATE","ACCOUNT_NO","RECEIVER","AMOUNT","BALANCE","DESCRIPTION");
  System.out.println("ACCOUNT STATEMENT\n");
  System.out.println(format);
  System.out.println("-----------------------------------------------------------------------------------------------");
  ArrayList<Printstatement> print=new ArrayList<Printstatement>();
  print=ie.printStatement(Login.accno);
  for(Printstatement ps:print)
  {
   System.out.println(ps);
  }
  System.out.println("-----------------------------------------------------------------------------------------------");
 }
}
