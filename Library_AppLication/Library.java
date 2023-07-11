package com.zoho.LibraryApp;
import java.util.Scanner;
import java.sql.SQLException;
public class Library
{
    static Scanner sc=new Scanner(System.in);
    public static void main(String ar[])throws Exception
    {
         new TableCreation().tableOrder();;
         new TableInsertion().insertOrder();
         boolean flag=true;
         while(flag)
         {
			try
			{
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tINDIAN LIBRARY");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\t1.VIEW BOOKS\n\t\t\t\t\t2.ADD BOOK\n\t\t\t\t\t3.ADD USER\n\t\t\t\t\t4.BARROW BOOK\n\t\t\t\t\t5.RETURN BOOK\n\t\t\t\t\t6.BOOK RESERVATION\n\t\t\t\t\t7.EXIT");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.print("Enter Your Choice:");
				Byte num=sc.nextByte();
				while(num>7 || num<1)
				{
					System.out.print("Enter Valid Choice!!!!!!:");
					num=sc.nextByte();
				}
				switch(num)
				{
					case 1: new UpdateBook().viewBooks();
							break;
							
					case 2: new UpdateBook().AddBooks();
							break;
							
					case 3: new UserSignUp().newUser();
							break;
							
					case 4: new BarrowBook().bookBarrow();
							break;
					
					case 5: new ReturnBook().bookReturn();
							break;

					case 6: new ReserveBook().bookReservation();
							break;

					case 7: flag=false;
						if(StatementSingleton.st!=null)
							StatementSingleton.close();
						if(Database.getInstance()!=null)
							Database.getInstance().closeDB();
							break;
							
					default:
							System.out.println("Give Valid Input!!!!!!!!!");
							break;
					
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
			catch(InvalidUserException e)
			{
				System.out.println(e.getMessage());
			}
			catch(DataNotFoundException e)
			{
				System.out.println(e.getMessage());
			}


	 }
    }
}
