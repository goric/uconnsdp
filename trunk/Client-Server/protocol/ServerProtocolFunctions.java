package protocol;

import java.sql.*;

public class ServerProtocolFunctions 
{
	private static String mConnectionURL =  "jdbc:mysql://66.29.103.254:3306/gamex_chatterim?user=gamex_chatterim&password=w3e65i6k0n";
	
	
	//message code 01
	public static void ClientLogIn( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aSHA1Passwd = pMessage[2];
		String aMachineAddress = pMessage[3];
		
		//open DB connection
		try
		{
	        Class.forName ( "com.mysql.jdbc.Driver").newInstance();
	        Connection aConnection = DriverManager.getConnection( mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeQuery ( "SELECT password FROM users WHERE username='" + aUserName 
	        							+ "' AND active='1' AND valid='1'");
	        
	        ResultSet aResultSet = aStatement.getResultSet();
	        
	        //add user to mUserMap
	        server.Server.mUserMap.put( aUserName, server.Server.mConnectedList.get( aMachineAddress));
	        server.Server.mReverseUserMap.put(server.Server.mConnectedList.get( aMachineAddress), aUserName);
	        
	        if( !aResultSet.next())
	        {
	        	//username not found, remove from mUserMap
	        	System.out.println( "invalid username:" + aUserName);
	        	server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " denied" );
	        	
	        	server.Server.mUserMap.remove( aUserName);
	        	server.Server.mReverseUserMap.remove(server.Server.mConnectedList.get( aMachineAddress));
	        }
	        else
	        {   
	        	String aPasswd = aResultSet.getString( "password");
	        	
	    		if( aPasswd.equals( aSHA1Passwd))
	    		{   
			        server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " accepted" );
			        
			        //insert last login time to DB
			        Statement aInsertLoginTime = null;
			        try
			        {
			        	aInsertLoginTime = aConnection.createStatement();
			        	aInsertLoginTime.executeUpdate("UPDATE users SET last_login='" 
			        									+ (java.lang.System.currentTimeMillis() / 1000) 
			        									+ "' WHERE username='" + aUserName + "'");
			        }
			        catch(SQLException sqle)
			        {
			        	System.out.println("SQL exception in inserting last login time for " + aUserName);
			        }
	    		}
	    		else
	    		{
	    			//incorrect password, remove user from mUserMap 
	    			System.out.println( "invalid password:" + aSHA1Passwd);
	    			server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " denied" );
	    			server.Server.mUserMap.remove( aUserName);
	    			server.Server.mReverseUserMap.remove(server.Server.mConnectedList.get( aMachineAddress));
	    		}
	        }
	        aResultSet.close();
	        aStatement.close();
	        aConnection.close();
	        
	        System.out.println("There are now " + server.Server.mUserMap.size() + " users logged in.");
	        server.Server.mConnectedList.remove( aMachineAddress);
		}
		catch( Exception e)
		{
			System.out.println( e.toString());
		}
	}
	
	
	//message code 02
	public static void SendContactList( String[] pMessage)
	{
		String aUserName = pMessage[1];
		
		//open DB connection
		try
		{
	        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
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
	        
	        server.Server.SendMessageToSingleClient(aUserName, "02 " + aUserName + " " + count + " " + aBuddyList);
	        
	        aResultSet.close();
	        aStatement.close();
	        aConnection.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	//message code 03
	public static void SendSingleMessage( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		String aMessage = "04 " + pMessage[2] + " " + pMessage[1] + " " + (pMessage.length - 3) + " ";
		
		for(int i = 3; i < pMessage.length; i++)
		{
			aMessage += pMessage[i] + " ";
		}
		
		server.Server.SendMessageToSingleClient( aToUser, aMessage);
	}
	
	//message code 04 - server will never receive this code (client only)
	
	//message code 05
	public static void SendSingleChatInvite( String[] pMessage)
	{
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
		String aUserName = pMessage[1];
		String aRequestingUserName = pMessage[2];
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        
	        //get date/time registered
	        aStatement.executeQuery("SELECT date_joined FROM users WHERE username='" + aRequestingUserName + "'");
	        ResultSet aRegResult = aStatement.getResultSet();
	        
	        int aRegisteredTime = 0;
	        aRegResult.next();
	        aRegisteredTime = aRegResult.getInt("date_joined");
	        System.out.println("registered: " + aRegisteredTime);
	        
	        //get current online time in seconds
	        long aTimeOnline = 0;
	        if(server.Server.mUserMap.containsKey(aRequestingUserName))
	        {
		        aStatement.executeQuery("SELECT last_login FROM users WHERE username='" + aRequestingUserName + "'");
		        ResultSet aLoginResult = aStatement.getResultSet();
		        
		        aLoginResult.next();
		        aTimeOnline = aLoginResult.getInt("last_login");
		        aTimeOnline = (java.lang.System.currentTimeMillis() / 1000) - aTimeOnline;
	        }
	        System.out.println("logged in for: " + aTimeOnline + " seconds.");
	        
	        //get profile - not in DB yet
	        
	        //get status + status info - not in DB yet
		}
		catch(Exception e)
		{
			System.out.println("Error in ServerProtocolFunctions.GetUserInfo: " + e.toString());
		}
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
		String aFromUserName = pMessage[1];
		String aToUserName = pMessage[2];
		
		//open DB connection
		try
		{
	        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeQuery ("SELECT username FROM users " +
	        						"WHERE user_id IN (SELECT buddy_id from buddy_list " +
	        						"WHERE user_id IN (SELECT user_id " +
	        						"FROM users WHERE username='" + aFromUserName + "'))");
	        
	        ResultSet aFromResultSet = aStatement.getResultSet();
	        String aFromBuddy = "";
	        
	        while(aFromResultSet.next())
	        {
	        	aFromBuddy += aFromResultSet.getString("username") + " ";
	        }
	        String[] aFromBuddyList = aFromBuddy.split(" ");
	        
	        aStatement = aConnection.createStatement();
	        aStatement.executeQuery ("SELECT username FROM users " +
	        						"WHERE user_id IN (SELECT buddy_id from buddy_list " +
	        						"WHERE user_id IN (SELECT user_id " +
	        						"FROM users WHERE username='" + aToUserName + "'))");
	        
	        ResultSet aToResultSet = aStatement.getResultSet();
	        String aToBuddy = "";
	        
	        while(aToResultSet.next())
	        {
	        	aToBuddy += aToResultSet.getString("username") + " ";
	        }
	        String[] aToBuddyList = aToBuddy.split(" ");
	        
	        String aList = "";
	        int aCount = 0;
	        
	        for(int i=0; i < aFromBuddyList.length; i++)
	        {
	        	for(int j=0; j < aToBuddyList.length; j++)
	        	{
	        		if( aFromBuddyList[i].equals( aToBuddyList[j]))
	        		{
	        			aList += aFromBuddyList[i] + " ";
	        			aCount++;
	        		}
	        	}
	        }
	        
	        server.Server.SendMessageToSingleClient( aFromUserName, "15 " + aFromUserName + " " 
	        																+ aToUserName + " " + aCount + " " + aList);
	        
	        aFromResultSet.close();
	        aStatement.close();
	        aConnection.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public static void SendLoginMessage( String[] pMessage)
	{
		server.Server.SendMessageToAllClients("16 " + pMessage[1]);
	}
	
	public static void SendLogoutNotification( String[] pMessage)
	{
		server.Server.SendMessageToAllClients("17 " + pMessage[1]);
	}
	
	public static void SendStatusChangeNotification( String[] pMessage)
	{
		server.Server.SendMessageToAllClients("18 " + pMessage[1] + " " + pMessage[2]);
	}
	
	public static void AddUserToBuddyList( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aBuddyToAdd = pMessage[2];
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeUpdate( "INSERT INTO buddy_list VALUES ( "
	        					+ "(SELECT user_id FROM users WHERE username='" + aUserName + "'), " 
	        					+ "(SELECT user_id FROM users WHERE username='" + aBuddyToAdd + "' ) )");

	        aStatement.close();
	        aConnection.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in ServerProtocolFunctions.AddUserToBuddyList: " + e.toString());
		}
	}
	
	public static void RemoveUserFromBuddyList( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aBuddyToAdd = pMessage[2];
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeUpdate( "DELETE FROM buddy_list WHERE user_id="
	        					+ "(SELECT user_id FROM users WHERE username='" + aUserName + "') AND " 
	        					+ "buddy_id=(SELECT user_id FROM users WHERE username='" + aBuddyToAdd + "')");
	        
	        aStatement.close();
	        aConnection.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in ServerProtocolFunctions.AddUserToBuddyList: " + e.toString());
		}
	}
}