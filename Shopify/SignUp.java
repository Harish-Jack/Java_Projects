package com.zoho.ShopifyApp;
import java.util.Map;
import java.util.LinkedHashMap;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.SQLException;
public class SignUp 
{
    public void newUser()throws IOException,SQLException
    {
        UserModel user=null;
        ResultSet resultset=null;
        String uname=Validation.getInstance().isCharacter("User Name");
        LinkedHashMap<Integer,String> role=new LinkedHashMap<>();
        resultset=StatementSingleton.getStatement().executeQuery("SELECT id,role FROM user_role where id!=3;");;
        while(resultset.next()){
            role.put(resultset.getInt(1),resultset.getString(2));
        }
        for(Map.Entry<Integer,String> res:role.entrySet())
            System.out.println("\n\t"+res.getKey()+"==>"+res.getValue());
        int roleid=Validation.getInstance().isNumeric("RoleId");
        while(!role.containsKey(roleid))
        {
            for(Map.Entry<Integer,String> res:role.entrySet())
                System.out.println(res.getKey()+"==>"+res.getValue());
            roleid=Validation.getInstance().isNumeric("Valid RoleId");
        }
        String gender=Validation.getInstance().getGender("Gender");
        long mobile=Validation.getInstance().isMobile("Mobile");
        String mail=Validation.getInstance().isEmail("Email-Id");
        String password=Validation.getInstance().isPassword("Password");
        String rpassword=Validation.getInstance().isPassword("ReEnter-Password");
        while(!password.equals(rpassword))
        {
            rpassword=Validation.getInstance().isPassword("ReEnter-Password");
        }
        user=new UserModel(uname,roleid,gender,mobile,mail,password);
        int userId=new TableInsertion().addUser(user);
        AddressModel adr=Validation.getInstance().setAddress("Address",userId);
        new TableInsertion().addAddress(adr);
        String otp=Validation.getInstance().generateOtp();
        System.out.println("\n\t\tOTP:"+otp);
        System.out.print("\t\tEnter OTP:");
        while(!otp.equals(Validation.reader.readLine()))
        {
            System.out.println("\n\t\tEnter Valid OTP:");
        }
        System.out.println("\nUSER ADDED SUCCESSFULLY...");
    }
}
