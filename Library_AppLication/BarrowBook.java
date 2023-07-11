package com.zoho.LibraryApp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class BarrowBook
{
    private  ResultSet rs=null;
	private  PreparedStatement ps=null;
    public void bookBarrow()throws Exception
    {
        while(true)
        {
            int id=0,bid=0;
            id=new Validation().isNumeric("UserId");
            UserModel user=new Controller().isValidUserId(id);
            if(user!=null)
            {
                if(new Controller().isEligible(id)<5)
                {
                    System.out.println("\n\t\tName:"+user.getUserName()+"\n\t\tAddress:"+user.getUserAddress()+"\n\t\tMobile:"+user.getMobileNo());
                    if("y".equals(new Validation().isCheck("Do you want to continue(y-Yes/n-No)")))
                    {
                        new Controller().isReserveApproved(id);
                        bid=new Validation().isNumeric("BookId");
                        if(new Controller().isValidBook(id,bid,false)>=1)
                        {
                            try
                            {
                                ps=Database.getInstance().getConnection().prepareStatement("INSERT INTO barrow_details (user_id,book_id,barrow_date,returning_date) VALUES(?,?,?,?)RETURNING Id;");
                                ps.setInt(1,id);
                                ps.setInt(2,bid);
                                ps.setDate(3,new java.sql.Date(new java.util.Date().getTime()));
                                ps.setDate(4,java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(20)));
                                rs=ps.executeQuery();
                                rs.next();
                                System.out.println("\n\nBARROWED ID:"+rs.getInt("Id"));
                                break;
                            }
                            catch(SQLException e)
                            {
                                    e.printStackTrace();
                            } 
                        }
                        else 
                         throw new DataNotFoundException("BOOK NOT AVAILABLE!!!!!!");
                    }
                    else
                       break;
                }
                else 
                    throw new InvalidUserException("ALREADY BARROWED 5 BOOKS!!!!!!!!!!!!");
            }     
            else
                throw new InvalidUserException("INVALID USER!!!!!!!!!!!!");
        }
    }
}