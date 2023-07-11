package com.zoho.LibraryApp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class UpdateBook
{
	private ResultSet rs=null;
	private PreparedStatement ps=null;
	public void viewBooks()throws SQLException
	{
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(String.format("\n\t\t%-20s|%-48s|%-20s|%-20s|%-20s|","ID","Book_Name","Author_Name","Category_Name","Count"));
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
		rs = StatementSingleton.getStatement().executeQuery("select D.id,B.name,A.name,C.name,D.count from book_details D INNER JOIN books B ON B.id=D.book_id INNER JOIN authors A ON A.id=D.author_id INNER JOIN category C ON C.id=D.category_id;");
		while (rs.next()) 
		{
			System.out.println(String.format("\t\t%-20s|%-48s|%-20s|%-20s|%-20s|",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)));
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Do u want to go back press-'y'");
		if(!Library.sc.next().equals("y"))
			viewBooks();
	}
	public void AddBooks()throws SQLException
	{
		BookModel books=null;
		String bookName="",autherName="",categoryName="";
		int count=0;
		while(true)
		{
			System.out.print("\n\t\tEnter Book Name:");
			Library.sc.nextLine();
			bookName=Library.sc.nextLine();
			System.out.print("\t\tEnter Author Name:");
			autherName=Library.sc.nextLine();
			System.out.print("\t\tEnter Category Name:");
			categoryName=Library.sc.nextLine();
			System.out.print("\t\tEnter Book Count:");
			count=Library.sc.nextInt();
			System.out.println("\nBOOKNAME:"+bookName+"\nAUTHORNAME:"+autherName+"\nCATEGORY NAME:"+categoryName+"\nBOOK COUNR:"+count);
			System.out.println("press-'y' for confrim");
			if(Library.sc.next().equals("y"))
			{
				books=new BookModel(bookName,autherName,categoryName,count);
				break;
			}
		}
		int bid=0,aid=0,cid=0,id=0;	
		bid=new Controller().isExists("books",books.getBookName().trim());
		aid=new Controller().isExists("authors",books.getAuthorName().trim());
		cid=new Controller().isExists("category",books.getCategoryName().trim());
		id=new Controller().isExists(bid,aid,cid);
		if(id==0)
		{
			try
			{
				ps=Database.getInstance().getConnection().prepareStatement("INSERT INTO Book_details (book_id, author_id, category_id,count) VALUES(?,?,?,?)RETURNING Id;");
				ps.setInt(1,bid);
				ps.setInt(2,aid);
				ps.setInt(3,cid);
				ps.setInt(4,count);
				rs=ps.executeQuery();
				rs.next();
				bid=rs.getInt("Id");
				if(bid!=-1)
					System.out.println("Book Added Sucessfully");
			}
			catch(SQLException e)
			{
					e.printStackTrace();
			} 
					
		}
		else
		{
			try
			{
				StatementSingleton.getStatement().executeUpdate("update book_details set count=(select count from book_details where id="+id+")+"+count+" where id='"+id+"';");
				System.out.println("Book Added Sucessfully");
			}
			catch(SQLException e)
			{
					e.printStackTrace();
			}   
		}   
	}
}


