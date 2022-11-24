package BankManagement;
import java.util.Scanner;
class BankProcess
{
   Scanner sc=new Scanner(System.in);
   public void process()throws Exception
   {
	BankController bc=new BankController();
	System.out.println("List of Menu: ");
	System.out.println("1-Deposit");
	System.out.println("2-Withdraw");
	System.out.println("3-Transaction");
	System.out.println("4-PrintStatement");
	System.out.println("5-Exit");
	byte num=sc.nextByte();
	while(num<1||num>5)
	{
	   num=0;
           System.out.println("Enter correct menu number: ");
	   num=sc.nextByte();
	}
	switch(num)
	{
	  case 1:
	  bc.deposit();
	  break;
	
	  case 2:
	  bc.withdraw();
	  break;

	  case 3:
	  bc.transaction();
	  break;

	  case 4:
	  bc.printStatement();
	  break;
	  
	  case 5:
	  DBConnection.con.close();
	  sc.close();
	  System.out.println("THANK YOU FOR VISIT!!!!!!!1");
	  break;
	}
   }
}
