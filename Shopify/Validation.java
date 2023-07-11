package com.zoho.ShopifyApp;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.Random;
import java.io.Console;  
import java.sql.Time;
final class Validation
{
	static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
    private static Validation validate=null;
    public static Validation getInstance()
    {
        if(validate==null)
          validate=new Validation();
        return validate;
    }
	public String isCharacter(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("(([a-zA-Z][a-z]*[ ]{1}[a-zA-Z][a-z]*)+)?[a-zA-Z][a-z]*|([a-zA-Z][a-z]*[ ]{1}[a-zA-Z][a-z]*)",str))
		{
		System.out.print("\n\tEnter "+s+" in String Format	:");
		str=reader.readLine();
		}
		return str;
	}
	public String isEmail(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[a-zA-Z][a-zA-Z0-9_.]*@gmail.com",str))
		{
		System.out.print("\n\tEnter "+s+" in String Format	:");
		str=reader.readLine();
		}
		return str;
	}
    public String isUPI(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[a-z]+[@][a-z]+",str))
		{
		System.out.print("\n\tEnter "+s+" in String Format	:");
		str=reader.readLine();
		}
		return str;
	}
	public long isMobile(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[6-9][0-9]{9}",str))
		{
		System.out.print("\n\tEnter "+s+" in Correct Format	:");
		str=reader.readLine();
		}
		return Long.parseLong(str);
	}
	public int isNumeric(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[0-9]*",str))
		{
		System.out.print("\n\tEnter input in Correct Format	:");
		str=reader.readLine();
		}
		return Integer.parseInt(str);
	}
	public int isPincode(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+"	:");
		String str=reader.readLine();
		while(!Pattern.matches("[0-9]{6}",str))
		{
		System.out.print("\n\tEnter Correct "+s+"	:");
		str=reader.readLine();
		}
		return Integer.parseInt(str);
	}
	public String generateOtp()
	{
		Random random=new Random();
		StringBuffer otp=new StringBuffer();
		for(int i=0;i<6;i++)
		{
			otp.append(random.nextInt(10));
		}
		return String.valueOf(otp);
	}
    public String isCheck(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[ny]{1}",str))
		{
		System.out.print("\n\tEnter "+s+" in String Format :");
		str=reader.readLine();
		}
		return str;
	}
    public String getGender(String s) throws IOException
	{
		System.out.print("\n\tm-Male\n\tf-Female\n\to-Others\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[mfo]{1}",str))
		{
            System.out.print("\n\tEnter Valid "+s+":");
            str=reader.readLine();
		}
        if(str.equals("m"))
		    return "Male";
        else if(str.equals("f"))
            return "Female";
        else
            return "Others";
	}
    public  String isPassword(String st)  throws IOException
    {
        char[] ch=null;
        System.out.print("\n\t"+st+":");
        ch=System.console().readPassword();
        while(!Pattern.matches("[a-zA-Z0-9]*([~`! @#$%^&*()_|:;'<,>.?/][a-zA-Z0-9]*)+",String.valueOf(ch)))
        {
            System.out.print("\n\t *Please Give Strong PASSWORD :");
            ch=System.console().readPassword();
        }
        return String.valueOf(ch);
    }
    public AddressModel setAddress(String st,int userId)  throws IOException
    {
        AddressModel adr=null;
        System.out.print("\n\t\t"+st.toUpperCase()+"");
        int doorno=isNumeric("Door_No");
        String street=isCharacter("Street_Name");
        String district=isCharacter("District");
        String city=isCharacter("City");
        int pincode=isPincode("Pincode");
        System.out.println("\n\t\tCheck Your Address:"+doorno+" "+street+","+district+","+city+"-"+pincode);
        if("y".equals(isCheck("y-Confrim/n-Re-Enter").toLowerCase()))
             adr=new AddressModel(userId,doorno,street,city,district,pincode);
        else
            setAddress(st, userId);
         return adr;
    }
}
