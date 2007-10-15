package protocol;

import java.sql.*;

public class ServerProtocolFunctions 
{
	
	//message code 01
	public static void ClientLogIn( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aSHA1Passwd = pMessage[2];
		
		//open DB connection
		try
		{
			//String url = "jdbc:mysql://localhost/sdp?user=root&password=";
			String url = "jdbc:mysql://ocean.hostingzoom.com/gamex_sdp?user=gamex_sdp&password=seniordesign";
	        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (url);
	        //System.out.println("Connected.");
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeQuery ("SELECT password FROM users WHERE username='" + aUserName + "'");
	        
	        ResultSet aResultSet = aStatement.getResultSet();
	        //aResultSet.next();
	        
	        if(!aResultSet.next())
	        {
	        	//send 'username not found'
	        	System.out.println("invalid username:" + aUserName);
	        	server.Server.SendMessageToClients( "01 " + aUserName + " denied" );
	        }
	        else
	        {   
	        	//aResultSet.next();
	        	String aPasswd = aResultSet.getString("password");
	        	
	    		if(aPasswd.equals(aSHA1Passwd))
	    		{
			        System.out.println(aPasswd.toString());
			        server.Server.SendMessageToClients( "01 " + aUserName + " accepted" );
	    		}
	    		else
	    		{
	    			//send 'incorrect password' error
	    			System.out.println("invalid password:" + aSHA1Passwd);
	    			server.Server.SendMessageToClients( "01 " + aUserName + " denied" );
	    		}
	        }
	        aResultSet.close();
	        aStatement.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	
	//message code 02
	public static void SendContactList( String[] pMessage)
	{
		String aUserName = pMessage[1];
		System.out.println(aUserName);
		//open DB connection
		try
		{
			//String url = "jdbc:mysql://localhost/sdp?user=root&password=";
			String url = "jdbc:mysql://ocean.hostingzoom.com/gamex_sdp?user=gamex_sdp&password=seniordesign";
	        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (url);
	        //System.out.println("Connected.");
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeQuery ("SELECT username FROM users " +
	        						"WHERE user_id IN (SELECT buddy_id from buddy_list " +
	        						"WHERE user_id IN (SELECT user_id " +
	        						"FROM users WHERE username='" + aUserName + "'))");
	        
	        ResultSet aResultSet = aStatement.getResultSet();
	        String aBuddyList = "";
	        int count = 0;
	        
	        while(aResultSet.next())
	        {
	        	count++;
	        	aBuddyList += aResultSet.getString("username") + " ";
	        }
	        
	        System.out.println("buddies: " + aBuddyList);
	        server.Server.SendMessageToClients("02 " + aUserName + " " + count + " " + aBuddyList);
	        System.out.println("sent: " + "02 " + aUserName + " " + count + " " + aBuddyList);
	        
	        aResultSet.close();
	        aStatement.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	//message code 03
	public static void SendSingleMessage( String[] pMessage)
	{
		System.out.println("Message code 03 entered.");
		
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		
	}
	
	//message code 04
	
	//message code 05
	public static void SendSingleChatInvite( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		String aChatName = pMessage[3];
		System.out.println("Message code 05 entered.");
	}
	
	//message code 06
	public static void SendMultipleChatInvites( String[] pMessage)
	{
		System.out.println("Message code 06 entered.");
	}
	
	//message code 07
	
	
	//message code 08
	public static void SendMessageToEntireChat( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		System.out.println("Message code 08 entered.");
	}
	
	//message code 09
	public static void SendFileTransferRequest( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		System.out.println("Message code 09 entered.");
	}
	
	//message code 10
	
	
	//message code 11
	public static void GetUserInfo( String[] pMessage)
	{
		System.out.println("Message code 11 entered.");
	}
	
	//message code 12
	public static void SetStatusMessage( String[] pMessage)
	{
		System.out.println("Message code 12 entered.");
	}
	
	//message code 13
	public static void RemoveStatusMessage( String[] pMessage)
	{
		System.out.println("Message code 13 entered.");
	}
	
	//message code 14
	public static void SetProfileInformation( String[] pMessage)
	{
		System.out.println("Message code 14 entered.");
	}
	
	//message code 15
	public static void GetCommonContacts( String[] pMessage)
	{
		System.out.println("Message code 15 entered.");
	}
	
	
}
