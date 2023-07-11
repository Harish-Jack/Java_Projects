package com.zoho.LibraryApp;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
class Validation
{
	static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	public String isCharacter(String s) throws IOException
	{
		System.out.print("\nEnter "+s+"	:");
		String str=reader.readLine();
		while(!Pattern.matches("(([a-zA-Z][a-z]*[ ]{1}[a-zA-Z][a-z]*)+)?[a-zA-Z][a-z]*|([a-zA-Z][a-z]*[ ]{1}[a-zA-Z][a-z]*)",str))
		{
            System.out.print("\n\tEnter "+s+" in String Format	:");
            str=reader.readLine();
		}
		return str;
	}
	public long isMobile(String s) throws IOException
	{
		System.out.print("\nEnter "+s+"	:");
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
		System.out.print("\nEnter "+s+"	:");
		String str=reader.readLine();
		while(!Pattern.matches("[0-9]*",str))
		{
		System.out.print("\n\tEnter input in Correct Format	:");
		str=reader.readLine();
		}
		return Integer.parseInt(str);
	}
    public String isCheck(String s) throws IOException
	{
		System.out.print("\nEnter "+s+" :");
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
		System.out.print("\nEnter "+s+" :\nm-Male-m\nf-Female\no-Others");
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
}
