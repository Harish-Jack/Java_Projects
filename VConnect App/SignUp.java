package com.zoho.VConnectApp;
import java.sql.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SignUp 
{
    public void newUser()throws Exception,IOException
    {
        String path="";
        byte profilepic[]=null;
        System.out.println("\n\t\t\t\t\tSIGNUP");
        String uname=Validation.getInstance().isCharacter("U-Name(must be unique)");
        String profilename=Validation.getInstance().isCharacter("Profile Name");
        String email=Validation.getInstance().isEmail("Email");
        String gender=Validation.getInstance().getGender("Gender");
        Date dob=Validation.getInstance().isDate("DOB");
        String password=Validation.getInstance().isPassword("Password");
        String rpassword=Validation.getInstance().isPassword("ReEnter-Password");
        while(!password.equals(rpassword))
        {
            rpassword=Validation.getInstance().isPassword("ReEnter-Password");
        }
        System.out.print("\n\tWANT TO ADD PROFILE PICTURE");
        if("y".equals(Validation.getInstance().isCheck("y-Confrim/n-Skip").toLowerCase()))
        {
            System.out.print("\t\tGive file Path (eg:/home/zoho/Downloads/filename.extention):");
            path=Validation.getInstance().isValidPath(Validation.reader.readLine());
            File profile=new File(path);
            profilepic=getByteArray(path,profile);
        }
        System.out.println("\n\n\t\tCHECK YOUR DETAILS");
        System.out.println("\t\tU-Name:"+uname+"\t\tProfile_Name:"+profilename);
        System.out.println("\n\t\tEmail::"+email+"\t\tDOB:"+dob.toString());
        if("y".equals(Validation.getInstance().isCheck("y-Confrim/n-No")));
        {
            try
            {
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                PreparedStatement ps =Database.getInstance().getConnection().prepareStatement("insert into user_details (uname,profile_name,email,gender,password,dob,created_on) values (?,?,?,?,?,?,?) RETURNING Id;");
                ps.setString(1,uname);
                ps.setString(2,profilename);
                ps.setString(3,email);
                ps.setString(4,gender);
                ps.setString(5,password);
                ps.setDate(6,dob);
                ps.setDate(7,currentDate);
                ResultSet rs=ps.executeQuery();
                if(rs.next() && !path.isEmpty())
                {
                    ps =Database.getInstance().getConnection().prepareStatement("insert into profiles (user_id,file_name,profile) values (?,?,?);");
                    ps.setInt(1,rs.getInt("Id"));
                    ps.setString(2,path.substring(path.lastIndexOf("/")+1));
                    ps.setBytes(3,profilepic);
                    ps.executeUpdate();
                }
                System.out.println("USER ADDED SUCCESSFULLY...");
                rs.close();
                ps.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static byte[] getByteArray(String path,File obj)throws Exception
    {
            FileInputStream fin=new FileInputStream(path);  
            byte arr[]=new byte[(int)obj.length()];
            fin.read(arr);
            fin.close();    
             return arr;
    }
    
}
