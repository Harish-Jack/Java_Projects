package com.zoho.LibraryApp;
import java.sql.SQLException;
import java.sql.Statement;
final class StatementSingleton
{
    public static Statement st=null;
    public static StatementSingleton single=null;
    private StatementSingleton()throws SQLException
    {
        st=Database.getInstance().getConnection().createStatement();
    }
    public static Statement getStatement()throws SQLException
	{
	      if(single==null || st==null)
	       single=new StatementSingleton();
	     return st;
	}
    public static void close()throws SQLException
    {
        st.close();
        single=null;
    }   
}
