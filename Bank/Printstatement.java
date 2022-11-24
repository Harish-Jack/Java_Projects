package BankManagement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
public class Printstatement
{
    private String accNo;
    private String accReceiver;
    private  int amount;
    private Date date;
    private String description;
    private int balance;
   
    Printstatement(String accNo,String accReceiver,int amount,Date date,String description,int balance)
    {
       this.accNo=accNo;
       this.accReceiver=accReceiver;
       this.amount=amount;
       this.date=date;
       this.description=description;
       this.balance=balance;
    }
    public String toString()
    {
        String format=String.format("%15s %15s %15s %15d %15d %15s",this.date,this.accNo,this.accReceiver,this.amount,this.balance,this.description);
        //return this.date+"                 "+this.accNo+"          "+this.accReceiver+"           "+this.amount+"          "+this.balance+"           "+this.description;
        return format;
    }
}
