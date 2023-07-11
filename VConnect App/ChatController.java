package com.zoho.VConnectApp;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.ValidationEvent;

import com.zoho.VConnectApp.ChatModel;
public class ChatController 
{
      LinkedHashMap<Integer,String> frnds=null;
      int c=0,num=0;
      public void viewChat(UserModel user)throws IOException
      {
            c=0;
            try
            {
                  getChats(user.getUserId());
                  if(frnds.size()==0)
                        throw new DataNotFoundException("\nNO RECORDS AVAILABLE");
                  System.out.println("\n\n-------------------------------------------*CHATS*------------------------------------------------");
                  for(Map.Entry<Integer,String> val:frnds.entrySet())
                  {
                        c++;
                        System.out.println("\n\t\t"+val.getKey()+"."+val.getValue());
                        if(c%10==0 || c==frnds.size())
                        {
                              if("y".equals(Validation.getInstance().isCheck("y-Open Chat/n-NextPage").toLowerCase()))
                              {
                                    num=Validation.getInstance().isNumeric("ID For Open Chat");
                                    while(!frnds.containsKey(num))
                                    {
                                          num=Validation.getInstance().isNumeric("Valid ID For Open Chat");
                                    }
                                    System.out.println("--------------------------------------------------------------------------------------------------");
                                    System.out.println("\n\n\t\t\t\t\t"+frnds.get(num));
                                    System.out.println("--------------------------------------------------------------------------------------------------");
                                    innerloop:
                                    while(true)
                                    {
                                          PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("select sender_id,message,send_at from message where chat_id=? order by id asc limit 10;");
                                          ps.setInt(1,num);
                                          ResultSet rs=ps.executeQuery();
                                          while (rs.next()) 
                                          {
                                                if(rs.getInt(1)!=user.getUserId())
                                                      System.out.println("\n-->"+rs.getString(2)+" :at("+rs.getDate(3)+")");
                                                else
                                                      System.out.println("\n\t\t\t\t-->"+rs.getString(2)+" :at("+rs.getDate(3)+")");
                                          }
                                          ps = Database.getInstance().getConnection().prepareStatement("update message set status='viewed' where chat_id=?;");
                                          ps.setInt(1,num);
                                          ps.executeUpdate();
                                          System.out.println("--------------------------------------------------------------------------------------------------");
                                          if("y".equals(Validation.getInstance().isCheck("y-replay/n-Nope").toLowerCase()))
                                          {
                                                System.out.print("Enter Message:");
                                                String msg=Validation.reader.readLine();
                                                PreparedStatement ps1= Database.getInstance().getConnection().prepareStatement("insert into message (chat_id,sender_id,message,send_at,status) values (?,?,?,?,?);");
                                                ps1.setInt(1,num);
                                                ps1.setInt(2,user.getUserId());
                                                ps1.setString(3,msg);
                                                ps1.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                                                ps1.setString(5,"sent");
                                                ps1.executeUpdate();
                                                ps1.close();
                                                System.out.println("\n\t\t\t\t*MESSAGE SENT SUCCESSFULLY");
                                                if("n".equals(Validation.getInstance().isCheck("y-message again/n-Nope").toLowerCase()))
                                                {
                                                      rs.close();
                                                      ps.close();
                                                      break innerloop;
                                                }
                                          }
                                          else
                                          {
                                            rs.close();
                                            ps.close();
                                            break innerloop;
                                          }
                                    }
                              }
                        }
                  }
            }
            catch(SQLException e)
            {
                  e.printStackTrace();
            }
      }
      public void searchFriend(String name, int uid)throws IOException
      {
            c=0;
            frnds=new LinkedHashMap<>();
            try 
            {
                  System.out.print("\n\t\tSearch Name:");
                  String str = Validation.reader.readLine();
                  PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("SELECT u.id, u.uname FROM friends f JOIN user_details u ON u.id = f.friend_id OR u.id = f.user_id WHERE (user_id = ? OR friend_id = ?) AND f.status = 'friends' AND u.uname != ? AND lower(u.uname) LIKE lower(?);");
                  ps.setInt(1, uid);
                  ps.setInt(2, uid);
                  ps.setString(3, name);
                  ps.setString(4, str+"%");
                  ResultSet rs = ps.executeQuery();
                  while (rs.next()) 
                  {
                        frnds.put(rs.getInt(1),rs.getString(2));
                  }
                  if(frnds.size()==0)
                        throw new DataNotFoundException("\nNO RECORDS AVAILABLE");
                  System.out.println("\n\n-------------------------------------------*FRIENDS*------------------------------------------------");
                  for(Map.Entry<Integer,String> val:frnds.entrySet())
                  {
                        c++;
                        System.out.println("\n\t\t"+val.getKey()+"."+val.getValue());
                        if(c%10==0 || c==frnds.size())
                        {
                              if("y".equals(Validation.getInstance().isCheck("y-Open Chat/n-NextPage").toLowerCase()))
                              {
                                    num=Validation.getInstance().isNumeric("ID For Open Chat");
                                    while(!frnds.containsKey(num))
                                    {
                                          num=Validation.getInstance().isNumeric("Valid ID For Open Chat");
                                    }
                                    System.out.println("\n\n\t\t\t\t\t"+frnds.get(num));
                                    innerloop:
                                    while(true)
                                    {
                                          System.out.print("Enter Message:");
                                          String msg=Validation.reader.readLine();
                                          ps = Database.getInstance().getConnection().prepareStatement("select id from chat where ((user_id_1=? and user_id_2=?) or (user_id_1=? and user_id_2=?)) and status='t';");
                                          ps.setInt(1, uid);
                                          ps.setInt(2, val.getKey());
                                          ps.setInt(3, val.getKey());
                                          ps.setInt(4, uid);
                                          rs=ps.executeQuery();
                                          if(rs.next())
                                          {
                                                PreparedStatement ps1= Database.getInstance().getConnection().prepareStatement("insert into message (chat_id,sender_id,message,send_at,status) values (?,?,?,?,?);");
                                                ps1.setInt(1,rs.getInt(1));
                                                ps1.setInt(2,uid);
                                                ps1.setString(3,msg);
                                                ps1.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                                                ps1.setString(5,"sent");
                                                ps1.executeUpdate();
                                                ps1.close();
                                                System.out.println("\n\t\t\t\t*MESSAGE SENT SUCCESSFULLY");
                                          }
                                          else
                                          {
                                                PreparedStatement ps2= Database.getInstance().getConnection().prepareStatement("insert into chat (user_id_1,user_id_2,created_on) values (?,?,?) RETURNING Id;");
                                                ps2.setInt(1,uid);
                                                ps2.setInt(2,val.getKey());
                                                ps2.setDate(3,new java.sql.Date(System.currentTimeMillis()));
                                                ResultSet rs2=ps2.executeQuery();
                                                if(rs2.next())
                                                {
                                                      PreparedStatement ps1= Database.getInstance().getConnection().prepareStatement("insert into message (chat_id,sender_id,message,send_at,status) values (?,?,?,?,?);");
                                                      ps1.setInt(1,rs2.getInt("Id"));
                                                      ps1.setInt(2,uid);
                                                      ps1.setString(3,msg);
                                                      ps1.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                                                      ps1.setString(5,"sent");
                                                      ps1.executeUpdate();
                                                      ps1.close();
                                                      System.out.println("\n\t\t\t\t*MESSAGE SENT SUCCESSFULLY");
                                                }
                                                ps2.close();
                                                rs2.close();
                                          }
                                          if("n".equals(Validation.getInstance().isCheck("y-Send MSG Again/n-Leave Chat").toLowerCase()))
                                             break innerloop;
                                    }
                              }
                        }
                  }
                  rs.close();
                  ps.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        public void deleteChat(int uid)throws IOException
        {
            c=0;
            try
            {
                  getChats(uid);
                  if(frnds.size()==0)
                        throw new DataNotFoundException("\nNO RECORDS AVAILABLE");
                  System.out.println("\n\n-------------------------------------------*CHATS*------------------------------------------------");
                  for(Map.Entry<Integer,String> val:frnds.entrySet())
                  {
                        c++;
                        System.out.println("\n\t\t"+val.getKey()+"."+val.getValue());
                        if(c%10==0 || c==frnds.size())
                        {
                              if("y".equals(Validation.getInstance().isCheck("y-Delete Chat/n-NextPage").toLowerCase()))
                              {
                                    num=Validation.getInstance().isNumeric("ID For Delete Chat");
                                    while(!frnds.containsKey(num))
                                          num=Validation.getInstance().isNumeric("Valid ID For Delete Chat");
                                    PreparedStatement ps= Database.getInstance().getConnection().prepareStatement("update chat set status='f' where id=?");
                                    ps.setInt(1, num);
                                    ps.executeUpdate();
                                    System.out.println("\n\t\t\t\t*CHAT REMOVED SUCCESSFULLY");
                              }
                        }
                  }
            }
            catch(SQLException e)
            {
                  e.printStackTrace();
            }
        }
        public int unreadChatCount(int uid)
        {
            try
            {
                  PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("SELECT COUNT(id)FROM message WHERE chat_id IN (SELECT c.id FROM chat c JOIN user_details u ON (c.user_id_1 = u.id OR c.user_id_2 = u.id) WHERE (c.user_id_1 = ? OR c.user_id_2 = ?) AND c.status = 't' AND u.id != ?) AND status = 'sent' and sender_id!=?;");
                  ps.setInt(1, uid);
                  ps.setInt(2, uid);
                  ps.setInt(3, uid);
                  ps.setInt(4, uid);
                  ResultSet rs=ps.executeQuery();
                  if(rs.next())
                     return rs.getInt("count");
                  ps.close();
                  rs.close();
            }
            catch(SQLException e)
            {
                  e.printStackTrace();
            }
            return 0;
        }
        public void getChats(int uid)
        {
            frnds=new LinkedHashMap<>();
            try
            {
                  PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("SELECT c.id, u.uname FROM chat c JOIN user_details u ON (c.user_id_1 = u.id OR c.user_id_2 = u.id) WHERE (c.user_id_1 = ? OR c.user_id_2 = ?) AND c.status = 't' and u.id!=? order by c.id asc;");
                  ps.setInt(1, uid);
                  ps.setInt(2, uid);
                  ps.setInt(3, uid);
                  ResultSet rs = ps.executeQuery();
                  while (rs.next()) 
                  {
                        frnds.put(rs.getInt(1),rs.getString(2));
                  }
            }
            catch(SQLException e)
            {
                  e.printStackTrace();
            }
        }
        
}
