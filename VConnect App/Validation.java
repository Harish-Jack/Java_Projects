package com.zoho.VConnectApp;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.Random;
import java.io.Console;  
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.sql.Date;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
	public String isChecking(String s) throws IOException
	{
		System.out.print("\n\tEnter "+s+":");
		String str=reader.readLine();
		while(!Pattern.matches("[nyb]{1}",str))
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
    public String isPassword(String st)  throws IOException
    {
        char[] ch=null;
        System.out.print("\n\t"+st+":");
        ch=System.console().readPassword();
        while(!Pattern.matches("[a-zA-Z0-9]*([~`! @#$%^&*()_|:;'<,>.?/][a-zA-Z0-9]*)+",String.valueOf(ch)) || String.valueOf(ch).length()<8)
        {
            System.out.print("\n\t *Please Give Strong PASSWORD :");
            ch=System.console().readPassword();
        }
        return String.valueOf(ch);
    }
    public Date isDate(String st)  throws IOException
    {
        System.out.print("\n\tEnter "+st+" (YYYY-MM-DD):");
		String date=reader.readLine();
		while(!isValidDate(date))   
		{
			System.out.print("\n\tEnter Valid "+st+" (YYYY-MM-DD):");
			date=reader.readLine();
		}     
        return Date.valueOf(LocalDate.parse(date));
    }
	public static boolean isValidDate(String date) 
	{
        try
		{
            LocalDate.parse(date);
            return true;
        } 
		catch(DateTimeParseException e) 
		{
            return false;
        }
    }
	public String isValidPath(String s)throws IOException
    {
        try
        {
            File profile=new File(s);
            while(!(profile.exists()))
            {
                System.out.println();
                System.out.print("\t\tGive Valid file Path (eg:/home/zoho/Downloads/filename.extention):");
                s=Validation.reader.readLine();
                profile=new File(s);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("FILE NOT FOUND...");
            isValidPath(s);
        }
        return s;
    }
	public String getPrivacy()throws IOException
	{
		String privacy="";
		int num=isNumeric("Privacy Option(1-Public/2-Friends/3-Private)");
        while(num<=0 || num>3)
        {
            num=isNumeric("Privacy Option(1-Public/2-Friends/3-Private)");
        }
        if(num==1)
            privacy="public";
        else if(num==2)
            privacy="friends";
        else
            privacy="private";
		return privacy;
	}
}
