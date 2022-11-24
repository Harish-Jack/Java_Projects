package BankManagement;
import java.util.Scanner;
class BankManagement
{
   public static void main(String[] args)
   {
    try{
       Login lg=new Login();
       DBConnection.resultOrder();
       
       System.out.println("1-New User\n2-Existing User");
       Scanner sc=new Scanner(System.in);
       byte choise=sc.nextByte();
       while(choise != 1 && choise != 2)
       {
	  choise=0;
 	  System.out.println("Gvive valid Input!!!!!!\n1-New User\n2-Existing User");
	  choise=sc.nextByte();
       }
       switch(choise)
       {
	  case 1:
	  String Banknme=String.format("%50s","BANK OF INDIA");
          System.out.println(Banknme);
          System.out.println("SIGNUP");
	  lg.newUser();
	  break;
	  
	  case 2:
	  String Bankname=String.format("%50s","BANK OF INDIA");
          System.out.println(Bankname);
          System.out.println("LOGIN");
	  lg.existUser();
	  break;
       }
      }catch(Exception e)
      {
       e.printStackTrace();
      } 
   }
}

