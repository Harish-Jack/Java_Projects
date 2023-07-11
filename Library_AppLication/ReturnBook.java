package com.zoho.LibraryApp;
import java.util.LinkedHashMap;
import java.util.Map;
public class ReturnBook
{
    public void bookReturn()throws Exception
    {
        while(true)
        {
            int id=0;
            id=new Validation().isNumeric("UserId");
            LinkedHashMap<Integer,BarrowModel> barrow=new LinkedHashMap<>();
            UserModel user=new Controller().isValidUserId(id,barrow);
            if(user!=null)
            {
                if(barrow.size()>=1)
                {
                    System.out.println("\n\t\tName:"+user.getUserName()+"\n\t\tAddress:"+user.getUserAddress()+"\n\t\tMobile:"+user.getMobileNo());
                    if("y".equals(new Validation().isCheck("Do you want to continue(y-Yes/n-No)")))
                    {
                        System.out.println(String.format("%-20s|%-20s|%-20s|","Book_id","Barrowed_date","Return_Date"));
                        System.out.println("---------------------------------------------------------------");
                        for(Map.Entry<Integer,BarrowModel> val:barrow.entrySet())
                            System.out.println(String.format("%-20s|%-20s|%-20s|",val.getKey(),val.getValue().getBarrowDate(),val.getValue().getReturnDate()));
                        System.out.println("----------------------------------------------------------------\n\n"); 
                        id=new Validation().isNumeric("ENTER BOOKID FOR RETURN");
                        if(!barrow.containsKey(id))
                        {
                            id=new Validation().isNumeric("\nENTER VALID BOOKID FOR RETURN!!!");
                        }
                        new Controller().bookReturning(id);
                        System.out.println("RETURNED BOOK SUCCESSFULLY!!!!");
                        break;
                    }
                    else
                     break;
                }
                else
                  throw new DataNotFoundException("Return NOT AVAILABLE!!!!!!");
            }
            else
              throw new InvalidUserException("INVALID USER!!!!!!!!!!!!");
        }
    }
}