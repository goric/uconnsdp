/**
 * Abstract class for holding the implementations of functions defined by the client-server protocol.
 * Contains a definition and implementation for every protocol message that the server will ever recieve.
 *  
 * @author Tim Goric
 */

package protocol;

import java.sql.*;
import java.util.*;

public abstract class ServerProtocolFunctions 
{
	private static String mConnectionURL =  "jdbc:mysql://66.29.103.254:3306/gamex_chatterim?user=gamex_chatterim&password=w3e65i6k0n";
	
	/**
	 * Function to handle Message code 01 - client login.  Queries database to authenticate username and password 
	 * and active/valid statuses, and sends an accept/reject message depending on that result.  If the user is accepted,
	 * updates the last login time of the user.
	 * @param pMessage - String array as defined by the protocol beginning with message code 01
	 */
	public static void ClientLogIn( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aSHA1Passwd = pMessage[2];
		String aMachineAddress = pMessage[3];
		
		if(!server.Server.mUserMap.containsKey(aUserName))
		{
			try
			{
		        Class.forName ( "com.mysql.jdbc.Driver").newInstance();
		        Connection aConnection = DriverManager.getConnection( mConnectionURL);
		        
		        Statement aStatement = aConnection.createStatement();
		        aStatement.executeQuery ( "SELECT password FROM users WHERE username='" + aUserName 
		        							+ "' AND active='1' AND valid='1'");
		        
		        ResultSet aResultSet = aStatement.getResultSet();
		        
		        server.Server.mUserMap.put( aUserName, server.Server.mConnectedList.get( aMachineAddress));
		        server.Server.mReverseUserMap.put(server.Server.mConnectedList.get( aMachineAddress), aUserName);
		        
		        if( !aResultSet.next())
		        {
		        	System.out.println( "invalid username:" + aUserName);
		        	server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " denied invalid username" );
		        	
		        	server.Server.mUserMap.remove( aUserName);
		        	server.Server.mReverseUserMap.remove(server.Server.mConnectedList.get( aMachineAddress));
		        }
		        else
		        {   
		        	String aPasswd = aResultSet.getString( "password");
		        	
		    		if( aPasswd.equals( aSHA1Passwd))
		    		{   
				        server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " accepted" );
				        
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
				        
				        server.Server.SendMessageToAllClients("16 " + aUserName, aUserName);
		    		}
		    		else
		    		{
		    			System.out.println( "invalid password:" + aSHA1Passwd);
		    			server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " denied invalid password" );
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
				server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " denied" );
			}
		}
		else
		{
			server.Server.SendMessageToSingleClient( aUserName, "01 " + aUserName + " denied user already logged in" );
		}
	}
	
	
	/**
	 * Function to handle Message code 02 - contact list retrieval.  Queries the database for the user's contact
	 * list, then check which of these contacts are online and returns all contacts to the requester with 
	 * online/offline flags.
	 * @param pMessage - String array as defined by the protocol beginning with message code 02
	 */
	public static void SendContactList( String[] pMessage)
	{
		String aUserName = pMessage[1];
		
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
	        	String aUser = aResultSet.getString("username");
	        	count++;
	        	aBuddyList += aUser + " ";
	        	
	        	if(server.Server.mUserMap.containsKey(aUser))
	        	{
	        		aBuddyList += "online ";
	        	}
	        	else
	        	{
	        		aBuddyList += "offline ";
	        	}
	        }
	        
	        server.Server.SendMessageToSingleClient(aUserName, "02 " + aUserName + " " + count + " " + aBuddyList);
	        
	        aResultSet.close();
	        aStatement.close();
	        aConnection.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			server.Server.SendMessageToSingleClient(aUserName, "02 " + aUserName + " error buddy list could not be accessed");
		}
	}
	
	/**
	 * Function to handle Message code 03 - sending a message from one user to another.  Checks if the recipient user is online,
	 * and if so then sends the message to them.  If not, sends an error message back to the sender.
	 * @param pMessage - String array as defined by the protocol beginning with message code 03
	 */
	public static void SendSingleMessage( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		
		if(server.Server.mUserMap.containsKey(aToUser))
		{
			String aMessage = "04 " + pMessage[2] + " " + pMessage[1] + " " + (pMessage.length - 3) + " ";
			
			for(int i = 3; i < pMessage.length; i++)
			{
				aMessage += pMessage[i] + " ";
			}
			
			try
			{
				server.Server.SendMessageToSingleClient( aToUser, aMessage);
				server.Server.SendMessageToSingleClient( aFromUser, "03 " + aFromUser + " successful");
			}
			catch(Exception e)
			{
				System.out.println("Error in ServerProtocolFunctions.SendSingleMessage: " + e.toString());
				server.Server.SendMessageToSingleClient( aFromUser, "03 " + aFromUser + " error");
			}
		}
		else
		{
			server.Server.SendMessageToSingleClient(aFromUser, "03 " + aFromUser + " error recipient is not logged in");
		}
	}
	
	//message code 04 - server will never receive this code (client only)
	
	/**
	 * Function to handle Message code 05 - sending a chat invitation from one user to another.  If the recipient is
	 * online, the message is sent - otherwise the sender recieves an error message.
	 * @param pMessage
	 */
	public static void SendSingleChatInvite( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		
		if(server.Server.mUserMap.containsKey(aToUser))
		{
			server.Server.SendMessageToSingleClient(aToUser, "07 " + aToUser + " " + aFromUser + " " + pMessage[3]);
			server.Server.SendMessageToSingleClient(aFromUser, "05 " + aFromUser + " successful");
		}
		else
		{
			server.Server.SendMessageToSingleClient(aFromUser, "05 " + aFromUser + " error recipient is not logged in");
		}
	}
	
	/**
	 * Function to handle Message code 06 - sending a chat invitation from one user to multiple others. Loops through all
	 * recipients and sends them the invitation if they are online.  If any are offline, the sender will recieve an error
	 * message listing all who were offline.
	 * @param pMessage - String array as defined by the protocol beginning with message code 05
	 */
	public static void SendMultipleChatInvites( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		int aNumRecipients = Integer.parseInt(pMessage[2]);
		ArrayList<String> aUnavailableRecipients = new ArrayList<String>();
		
		for(int i=3; i < (aNumRecipients + 3); i++ )
		{
			String aToUser = pMessage[i];
			
			if(server.Server.mUserMap.containsKey(aToUser))
			{
				server.Server.SendMessageToSingleClient(aToUser, "07 " + aToUser + " " + aFromUser + " " + pMessage[aNumRecipients+3]);
			}
			else
			{
				aUnavailableRecipients.add(aToUser);
			}
		}
		
		if(aUnavailableRecipients.size() == 0)
		{
			server.Server.SendMessageToSingleClient( aFromUser, "06 " + aFromUser + " successful");
		}
		else
		{
			String aUnavailableList = "";
			
			for(int i=0; i < aUnavailableRecipients.size(); i++)
			{
				aUnavailableList += aUnavailableRecipients.get(i) + " ";
			}
			
			server.Server.SendMessageToSingleClient( aFromUser, "06 " + aFromUser + " error recipients not online " + aUnavailableList);
		}
	}
	
	//message code 07
	
	
	/**
	 * Function to handle Message code 08 - sending a message to all users in a chat. The sender sends a message along with
	 * a list of all users in the chat, and the message is sent to all users in the list who are currently online.
	 */
	public static void SendMessageToEntireChat( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		int aNumRecipients = Integer.parseInt(pMessage[2]);
		int aNumOffline = 0;
		String aUsersOffline = "";
		String aMessage = "";
		
		for(int i=(aNumRecipients + 3); i < pMessage.length; i++)
		{
			aMessage += pMessage[i] + " ";
		}
		
		for (int i = 3; i < (aNumRecipients + 3); i++)
		{
			if(server.Server.mUserMap.containsKey(pMessage[i]))
			{
				server.Server.SendMessageToSingleClient(pMessage[i], "04 " + aFromUser + " " + aMessage);
			}
			else
			{
				aNumOffline++;
				aUsersOffline += pMessage[i] + " ";
			}
		}
		
		if(aNumOffline != 0)
		{
			server.Server.SendMessageToSingleClient(aFromUser, "08 " + aFromUser + " error recipients not online " + aUsersOffline);
		}
		else
		{
			server.Server.SendMessageToSingleClient(aFromUser, "08 " + aFromUser + " successful");
		}
	}
	
	/**
	 * Function to handle Message code 09 - sending a file transfer request from one user to another. The sender of the file
	 * must initiate. The message is sent if the reciever is online; otherwise the sender recieves an error message.
	 * @param pMessage - String array as defined by the protocol beginning with message code 09
	 */
	public static void SendFileTransferRequest( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		
		if(server.Server.mUserMap.containsKey(aToUser))
		{
			server.Server.SendMessageToSingleClient( aToUser, "10 " + aToUser + " " + aFromUser + " " + pMessage[3]
		                                       + " " + pMessage[4] + " " + pMessage[5] + " " + pMessage[6]);
		}
		else
		{
			server.Server.SendMessageToSingleClient(aFromUser, "09 " + aFromUser + " error recipient not logged in");
		}
	}
	
	//message code 10
	
	
	/**
	 * Function to handle Message code 11 - one user getting a user's information.  Queries the database for join date
	 * and last login/logout time, depending if the user is online or not, and profile information.  Checks the 
	 * UserStatusMap for status messages, then returns everything to the requester. 
	 * @param pMessage - String array as defined by the protocol beginning with message code 11
	 */
	public static void GetUserInfo( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aRequestingUserName = pMessage[2];
		String aOnlineStatus = "";
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        
	        aStatement.executeQuery("SELECT date_joined FROM users WHERE username='" + aRequestingUserName + "'");
	        ResultSet aRegResult = aStatement.getResultSet();
	        
	        int aRegisteredTime = 0;
	        aRegResult.next();
	        aRegisteredTime = aRegResult.getInt("date_joined");
	        System.out.println("registered: " + aRegisteredTime);
	        
	        long aTime = 0;
	        
	        if(server.Server.mUserMap.containsKey(aRequestingUserName))
	        {
		        aStatement.executeQuery("SELECT last_login FROM users WHERE username='" + aRequestingUserName + "'");
		        ResultSet aLoginResult = aStatement.getResultSet();
		        aLoginResult.next();
		        aTime = aLoginResult.getInt("last_login");
		        aLoginResult.close();
		        aOnlineStatus = "online";
	        }
	        else
	        {
	        	aStatement.executeQuery("SELECT last_logout FROM users WHERE username='" + aRequestingUserName + "'");
	        	ResultSet aLoginResult = aStatement.getResultSet();
		        aLoginResult.next();
		        aTime = aLoginResult.getInt("last_logout");
		        aLoginResult.close();
		        aOnlineStatus = "offline";
	        }
		    
	        aTime = (java.lang.System.currentTimeMillis() / 1000) - aTime;
	        
	        aStatement = aConnection.createStatement();
	        aStatement.executeQuery("SELECT profile FROM profiles WHERE user_id=(SELECT user_id FROM users "
	        								+ "WHERE username='" + aRequestingUserName + "')" );
	        String aProfile="";
	        
	        ResultSet aProfileResult = aStatement.getResultSet();
	        
	        if(aProfileResult.next())
	        {
	        	aProfile = aProfileResult.getString("profile");
	        	aProfileResult.close();
	        }
	        String[] aProfileArray = aProfile.split(" ");
	        
	        aStatement.close();
	        
	        String[] aStatusMessageArray;
	        String aStatusMessage = "";
	        
	        if( server.Server.mUserStatusMap.containsKey( aRequestingUserName))
	        {
	        	aStatusMessageArray = server.Server.mUserStatusMap.get(aRequestingUserName);
	        	
	        	for (int i=0; i< aStatusMessageArray.length; i++)
	        	{
	        		aStatusMessage += aStatusMessageArray[i];
	        	}
	        }
	        
	        aStatusMessageArray = aStatusMessage.split(" ");
	        
	        server.Server.SendMessageToSingleClient(aUserName, "11 " + aUserName + " " + aRequestingUserName +
	        									" " + aOnlineStatus + " " + aRegisteredTime + " " + aTime + " "
	        									+ aProfileArray.length + " " + aProfile + " " + aStatusMessageArray.length
	        									+ " " + aStatusMessage);
	        aConnection.close();
		}
		catch(Exception e)
		{
			server.Server.SendMessageToSingleClient(aUserName, "11 " + aUserName + " error in getting information");
			System.out.println("Error in ServerProtocolFunctions.GetUserInfo: " + e.toString());
		}
	}
	
	/**
	 * Function to handle Message code 12 - setting status message.  Sets a user's statue message in the UserStatusMap,
	 * removing any previous status message if there was one.  Sends a notification of the change to all online clients.
	 * @param pMessage - String array as defined by the protocol beginning with message code 12
	 */
	public static void SetStatusMessage( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aStatus = pMessage[2];
		
		String aMessage = "";
		
		try
		{
			for (int i = 3; i < pMessage.length; i++)
			{
				aMessage += pMessage[i] + " ";
			}
			
			String[] aStatusMessage = new String[2];
			aStatusMessage[0] = aStatus + " ";
			aStatusMessage[1] = aMessage;
			
			if( server.Server.mUserStatusMap.containsKey( aUserName))
			{
				server.Server.mUserStatusMap.remove( aUserName);
			}
			server.Server.mUserStatusMap.put(aUserName, aStatusMessage);
			
			server.Server.SendMessageToSingleClient(aUserName, "12 " +aUserName + " successful");
			server.Server.SendMessageToAllClients( "18 " + aUserName + " " + aStatus + " " + aMessage, aUserName);
		}
		catch(Exception e)
		{
			server.Server.SendMessageToSingleClient(aUserName, "12 " + aUserName + " error status could not be set");
		}
	}
	
	/**
	 * Function to handle Message code 13 - removing one's status message.  Checks the UserStatusMap to see if the
	 * user had an old status message, and removes it, setting the user back to 'available.' Sends a notification
	 * of the change to all online clients. 
	 * @param pMessage - String array as defined by the protocol beginning with message code 13
	 */
	public static void RemoveStatusMessage( String[] pMessage)
	{
		String aUser = pMessage[1];
		
		try
		{
			if( server.Server.mUserStatusMap.containsKey( aUser))
			{
				server.Server.mUserStatusMap.remove( aUser);
			}
			
			server.Server.SendMessageToSingleClient(aUser, "13 " + aUser + " successful");
			server.Server.SendMessageToAllClients("18 " + aUser + " available ", aUser);
		}
		catch(Exception e)
		{
			server.Server.SendMessageToSingleClient(aUser, "13 " + aUser + " error status could not be removed");
		}
	}
	
	/**
	 * Function to handle Message code 14 - setting or updating a user's profile information. Inserts into or updates
	 * the user's information stored in the database's profile table, as well as updating the profile last edited field. 
	 * @param pMessage - String array as defined by the protocol beginning with message code 14
	 */
	public static void SetProfileInformation( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aProfile = "";
		
		try
		{
			for(int i=2; i< pMessage.length; i++)
			{
				aProfile += pMessage[i] + " ";
			}
			
			System.out.println(aProfile);
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        aStatement.execute("INSERT INTO profiles(user_id, profile, last_updated) VALUES "
	        					+ "((SELECT user_id FROM users WHERE username='" + aUserName + "'),"
	        					+ " '" + aProfile + "', " + (java.lang.System.currentTimeMillis() / 1000) + ")"
	        					+ " ON DUPLICATE KEY UPDATE profile=" + "'" + aProfile + "', last_updated="
	        					+ "'" + (java.lang.System.currentTimeMillis() / 1000) + "'");
	        
	        aStatement.close();
	        
	        server.Server.SendMessageToSingleClient(aUserName, "14 " + aUserName + " successful");
	        
	        aConnection.close();
		}
		catch(Exception e)
		{
			server.Server.SendMessageToSingleClient(aUserName, "14 " + aUserName + " error profile information could not be set");
		}
	}
	
	/**
	 * function to handle Message code 15 - getting one's contacts in common with another user.  Queries the database
	 * for both user's contact lists, then performs a comparison on them and returns all contacts which both have
	 * in their lists to the requesting user.
	 * @param pMessage - String array as defined by the protocol beginning with message code 15
	 */
	public static void GetCommonContacts( String[] pMessage)
	{
		String aFromUserName = pMessage[1];
		String aToUserName = pMessage[2];
		
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
	        aFromResultSet.close();
	        aStatement.close();
	        
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
	        aToResultSet.close();
	        aStatement.close();
	        
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

	        aConnection.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			server.Server.SendMessageToSingleClient(aFromUserName, "15 " + aFromUserName + " error");
		}
	}
	
	/**
	 * Function to handle Message code 17 - user logging out. Sends a notification message to all online users. 
	 * @param pMessage - String array as defined by the protocol beginning with message code 16
	 */
	public static void SendLogoutNotification( String[] pMessage)
	{
		server.Server.SendMessageToAllClients("17 " + pMessage[1], pMessage[1]);
	}
	
	/**
	 * Function to handle Message code 19 - user exiting system.  Writes the last logout time for that user to the
	 * database. 
	 * @param pMessage - String array as defined by the protocol beginning with message code 19
	 */
	public static void WriteLogoutTime( String[] pMessage)
	{
		try
		{
			Class.forName ( "com.mysql.jdbc.Driver").newInstance();
	        Connection aConnection = DriverManager.getConnection( mConnectionURL);
	        
	        Statement aInsertLoginTime = null;
	        	aInsertLoginTime = aConnection.createStatement();
	        	aInsertLoginTime.executeUpdate("UPDATE users SET last_logout='" 
	        									+ (java.lang.System.currentTimeMillis() / 1000) 
	        									+ "' WHERE username='" + pMessage[1] + "'");
	        	aInsertLoginTime.close();
	        	aConnection.close();

		}
	    catch(Exception e)
	    {
	    	System.out.println("SQL exception in inserting last logout time for " + pMessage[1]);
	    }
	}
	
	/**
	 * Function for handling Message code 20 - user adding a contact to their contacts list.  Queries the database
	 * to make sure that the user exists, and then updates the requester's contacts list.  If the user doesn't
	 * exist in the system, an error message is sent to the requester.
	 * @param pMessage - String array as defined by the protocol beginning with message code 20
	 */
	public static void AddUserToBuddyList( String[] pMessage)
	{
		String aUserName = pMessage[1];
		String aBuddyToAdd = pMessage[2];
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        Connection aConnection = DriverManager.getConnection (mConnectionURL);
	        
	        Statement aStatement = aConnection.createStatement();
	        aStatement.executeQuery("SELECT username FROM users");

	        ResultSet aResultSet = aStatement.getResultSet();
	        boolean aIsValidBuddy = false;
	        
	        while(aResultSet.next())
	        {
	        	if(aBuddyToAdd.equals(aResultSet.getString("username")))
	        	{
	        		aIsValidBuddy = true;
	        	}
	        }
	        
	        aResultSet.close();
	        aStatement.close();
	        
	        if(aIsValidBuddy)
	        {
	        	aStatement = aConnection.createStatement();
		        aStatement.executeUpdate( "INSERT INTO buddy_list VALUES ( "
		        					+ "(SELECT user_id FROM users WHERE username='" + aUserName + "'), " 
		        					+ "(SELECT user_id FROM users WHERE username='" + aBuddyToAdd + "' ) )");
		        
		        aStatement.close();
		        
		        server.Server.SendMessageToSingleClient(aUserName, "20 " + aUserName + " successful");
	        }
	        else
	        {
	        	server.Server.SendMessageToSingleClient(aUserName, "20 " + aUserName + " error invalid username");
	        }
	        
	        aConnection.close();
		}
		catch(Exception e)
		{
			server.Server.SendMessageToSingleClient(aUserName, "20 " + aUserName + " error user could not be added to contacts");
			System.out.println("Exception in ServerProtocolFunctions.AddUserToBuddyList: " + e.toString());
		}
	}
	
	/**
	 * Function to handle Message code 21 - removing a contact from one's list.  Updates the database to reflect this
	 * change and then notifies the sender if the removal was successful or not.
	 * @param pMessage - String array as defined by the protocol beginning with message code 21
	 */
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
	        server.Server.SendMessageToSingleClient(aUserName, "21 " + aUserName + " successful");
		}
		catch(Exception e)
		{
			System.out.println("Exception in ServerProtocolFunctions.AddUserToBuddyList: " + e.toString());
			server.Server.SendMessageToSingleClient(aUserName, "21 " + aUserName + " error user could not be removed from contacts");
		}
	}
	
	/**
	 * Function to handle Message code 22 - response to a file transfer request. Sends the response to the original 
	 * file transfer requester with the proper accept/reject message as specified in the protocol.
	 * @param pMessage - String array as defined by the protocol beginning with message code 22
	 */
	public static void SendFileTransferRequestResponse( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		
		if(pMessage[3].contentEquals("accept"))
		{
			server.Server.SendMessageToSingleClient(aFromUser, "22 " + aFromUser + " " + aToUser + " accept " + pMessage[4]);
		}
		else
		{
			server.Server.SendMessageToSingleClient(aFromUser, "22 " + aFromUser + " " + aToUser + " reject");
		}
	}
	
	/**
	 * Function to handle Message code 23 - response to a chat room invitation. Sends the response to the original
	 * chat inviter with the proper accept/reject message as specified in the protocol.
	 * @param pMessage - String array as defined by the protocol beginning with message code 23
	 */
	public static void RespondToChatInvite( String[] pMessage)
	{
		String aFromUser = pMessage[1];
		String aToUser = pMessage[2];
		
		if(pMessage[4].contentEquals("accept"))
		{
			server.Server.SendMessageToSingleClient(aToUser, "23 " + aFromUser + " " + aToUser + " " + pMessage[3] + " accept");
		}
		else
		{
			server.Server.SendMessageToSingleClient(aToUser, "23 " + aFromUser + " " + aToUser + " " + pMessage[3] + " reject");
		}
	}
}
