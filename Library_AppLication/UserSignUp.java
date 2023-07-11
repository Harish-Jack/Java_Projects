package com.zoho.LibraryApp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
public class UserSignUp
{
        private  ResultSet rs=null;
	    private  PreparedStatement ps=null;
        UserModel user=null;
        public void newUser()throws IOException
        {
            String name=new Validation().isCharacter("UserName");
            System.out.print("Address:");
            Library.sc.nextLine();
            String address=Library.sc.nextLine();
            long mob=new Validation().isMobile("Mobile");
            String gender=new Validation().getGender("Gender:");
            user=new UserModel(name,address,mob,gender);
            try
			{
				ps=Database.getInstance().getConnection().prepareStatement("INSERT INTO user_details (name, address, mobile,gender) VALUES(?,?,?,?)RETURNING Id;");
				ps.setString(1,user.getUserName());
				ps.setString(2,user.getUserAddress());
				ps.setLong(3,user.getMobileNo());
				ps.setString(4,user.getGender());
				rs=ps.executeQuery();
				rs.next();
				System.out.println("\n\n\tUserID Generated Sucessfully!!!!!!!!!!!!!!UserId=>("+rs.getInt("Id")+")");
			}
			catch(SQLException e)
			{
					e.printStackTrace();
			} 
        }
}