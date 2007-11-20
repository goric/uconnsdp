/**
 * Class for running the server application.  This class contains the main logic for keeping the server up,
 * managing the online users, and deciding what to do with the messages that are recieved.
 * @author Tim Goric
 */
package server;

import java.util.*;
import java.net.*;
import java.io.*;

public class Server 
{
	public static Map<String, Socket> mUserMap = new HashMap<String, Socket>();
	public static Map<String, Socket> mConnectedList = new HashMap<String, Socket>();
	public static Map<Socket, String> mReverseUserMap = new HashMap<Socket, String>();
	
	public static Map<String, String[]> mUserStatusMap = new HashMap<String, String[]>();
	
	private ServerSocket mServerSocket ;
	public protocol.ServerConnection mServerConnection;
	
    private boolean mIsConnected;
    public static server_gui frame = new server_gui();
    
    /**
     * Constructor for the server class. Opens a ServerSocket at port 6013 for the clients to connect to.
     */
	public Server()
	{
		try	
		{
			mServerSocket= new ServerSocket( 6013);
			this.mIsConnected = true;
		}
		catch( Exception e) 
		{
			System.out.println( "Error in Server constructor:  " + e);
		}
		RunServer();
	}
	
	/**
	 * Main function for the server - creates an instance of Server.
	 */
	public static void main(String args[])
	{
		new Server();
	}
	
	/*
	 * Guts of the server.  This accepts connections from clients and add then to the
	 * currently connected list.  Creates a new ServerConnection for each client.
	 */
	/**
	 * Function that the server runs infinitely. Waits for and accepts connedctions from clients and
	 * adds them to the currently connected list. Creates a new thread containing an instance of
	 * ServerConnection for each client that logs in. 
	 */
	private void RunServer()
	{
		frame.setVisible( true);
		
		Socket aSocket = null;
		
		try
		{
			System.out.println( "Server started at " + InetAddress.getLocalHost().toString() + " port " + 6013);
			
			frame.actout("Server started at " + InetAddress.getLocalHost().toString() + " port " + 6013);
		}
		catch(Exception e)
		{
			System.out.println( "Error in Server.RunServer: " + e.toString());
			System.exit(-1);
		}
		
		while( mIsConnected) 
		{
			
			try	
			{
				aSocket = mServerSocket.accept();
				
				if( aSocket != null)	
				{
					synchronized( mConnectedList) {
						mConnectedList.put( aSocket.getRemoteSocketAddress().toString(), aSocket);
						System.out.println("Server reads connection as: " + aSocket.getRemoteSocketAddress().toString());
					}
					
					mServerConnection = new protocol.ServerConnection( aSocket);
				}
			}

			catch( Exception e) 
			{
				System.out.println( "Error in Server.RunServer's thread creation process: " + e.toString() );
				
				try 
				{
					aSocket.close();
				}
				catch( Exception ee) 
				{
					System.out.println( "Error in closing Server.RunServer's socket: " + ee.toString());
				}
			}
		}
	}

	/**
	 * Function which decides what to do with each message recieved. Calls various functions in ServerProtocolFunctions
	 * after parsing the message recieved to access the message code.
	 * 
	 * @param pMessage - a Message that a client sent to the server. 
	 */
	public static void ReceiveMessageFromClient( String pMessage)
	{	
		if( pMessage != null)
		{
			System.out.println(pMessage);
			
			synchronized( pMessage)
			{
				String[] aMessage = pMessage.split( " ");
				
				int aMsgCode = Integer.parseInt( aMessage[0]);
				
				switch( aMsgCode)
				{
					case 1:
						protocol.ServerProtocolFunctions.ClientLogIn( aMessage);
						frame.clearusers();
						UpdateUserList();
						frame.actout( aMessage[1] + " logged in.");
						break;
						
					case 2:
						protocol.ServerProtocolFunctions.SendContactList( aMessage);
						frame.actout( aMessage[1] + " requested buddy list.");
						break;
						
					case 3:
						protocol.ServerProtocolFunctions.SendSingleMessage( aMessage);
						frame.actout( aMessage[1] + " sent a message to " + aMessage[2] + ".");
						break;
						
					case 4:
						// server should NEVER receive this code - client use only
						break;
						
					case 5:
						protocol.ServerProtocolFunctions.SendSingleChatInvite( aMessage);
						frame.actout( aMessage[1] + " sent a chat invite to " + aMessage[2] + ".");
						break;
						
					case 6:
						protocol.ServerProtocolFunctions.SendMultipleChatInvites( aMessage);
						frame.actout( aMessage[1] + " sent chat invitations to " + aMessage[2] + " users to join"
															+ " the chat in " + aMessage[aMessage.length - 1] + ".");
						break;
						
					case 7:
						//server should NEVER receive this code - client use only
						break;
						
					case 8:
						protocol.ServerProtocolFunctions.SendMessageToEntireChat( aMessage);
						frame.actout(aMessage[1] + " sent a message to everyone in chat " + aMessage[aMessage.length - 1] + ".");
						break;
						
					case 9:
						protocol.ServerProtocolFunctions.SendFileTransferRequest( aMessage);
						frame.actout( aMessage[1] + " sent a file transfer request to " + aMessage[2] + ".");
						break;
						
					case 10:
						//server should NEVER receive this code - client use only
						break;
						
					case 11:
						protocol.ServerProtocolFunctions.GetUserInfo( aMessage);
						frame.actout( aMessage[1] + " requested buddy info for " + aMessage[2] + ".");
						break;
						
					case 12:
						protocol.ServerProtocolFunctions.SetStatusMessage( aMessage);
						frame.actout( aMessage[1] + " changed their status to " + aMessage[2] + ".");
						break;
						
					case 13:
						protocol.ServerProtocolFunctions.RemoveStatusMessage( aMessage);
						frame.actout( aMessage[1] + " removed their status message.");
						break;
						
					case 14:
						protocol.ServerProtocolFunctions.SetProfileInformation( aMessage);
						frame.actout( aMessage[1] + " changed profile information.");
						break;
						
					case 15:
						protocol.ServerProtocolFunctions.GetCommonContacts( aMessage);
						frame.actout( aMessage[1] + " requested contacts in common with " + aMessage[2] + ".");
						break;
						
					case 16:
						//server should NEVER receive this code - client use only
						break;
					
					case 17:
						protocol.ServerProtocolFunctions.SendLogoutNotification( aMessage);
						protocol.ServerProtocolFunctions.WriteLogoutTime( aMessage);
						frame.actout( aMessage[1] + " logged out.");
						break;
						
					case 18:
						//server should NEVER receive this code - client use only
						break;
						
					case 19:
						RemoveUser( server.Server.mUserMap.get( aMessage[1]));
						break;
					
					case 20:
						protocol.ServerProtocolFunctions.AddUserToBuddyList( aMessage);
						frame.actout( aMessage[1] + " added a buddy (" + aMessage[2] + ") to their buddy list.");
						break;
						
					case 21:
						protocol.ServerProtocolFunctions.RemoveUserFromBuddyList( aMessage);
						frame.actout( aMessage[1] + " removed a buddy (" + aMessage[2] + ") from their buddy list.");
						break;
						
					case 22:
						protocol.ServerProtocolFunctions.SendFileTransferRequestResponse( aMessage);
						frame.actout( aMessage[2] + " responded " + aMessage[3] + " to " + aMessage[1] + "'s file transfer request.");
						break;
						
					case 23:
						protocol.ServerProtocolFunctions.RespondToChatInvite( aMessage);
						frame.actout( aMessage[1] + " responded " + aMessage[4] + " to " + aMessage[2] + "'s invite to chat in " + aMessage[3] + ".");
						break;
					
					default:
						break;
				}
			}
		}
		else
		{
			
		}
	}
	
	/**
	 * Function for sending mass messages to all connected clients, for cases such as a login, status change, or
	 * logout where all clients need to be notified.
	 * 
	 * @param pMessage - The message to be sent.
	 * @param pFromUser - The user it originated from.
	 */
	public static synchronized void SendMessageToAllClients( String pMessage, String pFromUser)
	{
		DataOutputStream aDataOutStream;
		Set<String> aKeySet = mUserMap.keySet();
		Iterator<String> aIterator = aKeySet.iterator();
		
		while(aIterator.hasNext())
		{
			String aKey = aIterator.next();
			try
			{
				if(!aKey.contentEquals(pFromUser))
				{
					aDataOutStream = new DataOutputStream( ( mUserMap.get(aKey) ).getOutputStream());
					aDataOutStream.writeUTF( pMessage);
				}
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.SendMessageToClients: " + e.toString());
			}
		}
	}

	/**
	 * Function for sending a message from the server to a single client, used very often as a result of the
	 * client sending the server a message.
	 * 
	 * @param pToUserName - The user to send the message to
	 * @param pMessage - The message to be sent
	 */
	public static synchronized void SendMessageToSingleClient( String pToUserName, String pMessage)
	{
		DataOutputStream aDataOutStream;
		try
		{
			aDataOutStream= new DataOutputStream( ( mUserMap.get(pToUserName) ).getOutputStream());
			aDataOutStream.writeUTF( pMessage);
		}
		catch(Exception e)
		{
			System.out.println("Error in Server.SendMessageToSingleClient: " + e.toString());
		}
	}
	
	/**
	 * Function for updating the userlist in the ClientGUI. Clears the GUI list and then repopulates
	 * it by looping through the list of online users.
	 */
	public static synchronized void UpdateUserList()
	{
		frame.clearusers();
		
		Set<String> aKeySet = mUserMap.keySet();
		Iterator<String> aIterator = aKeySet.iterator();
		
		while(aIterator.hasNext())
		{
			String aKey = aIterator.next();
			try
			{
				String user = aKey;
				frame.userlist(user);
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.UpdateUserList: " + e.toString());
			}
		}
	}
	
	/* Function to reset the server, remove connections to all clients, and clear all frames in GUI */
	
	public static synchronized void ResetServer()
	{
		Set<String> aKeySet = mUserMap.keySet();
		Iterator<String> aIterator = aKeySet.iterator();
		
		while(aIterator.hasNext())
		{
			String aKey = aIterator.next();
			try
			{
				Socket pSocket = mUserMap.get(aKey);
				RemoveUser(pSocket);
				
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.Reset: " + e.toString());
			}
		}
	
	}
	/**
	 * Function for removing a client.  Removes the username and socket instance that are stored
	 * on the server, and also updates the GUI's user list.
	 */
	public static synchronized void RemoveUser( Socket pSocket)
	{
		mUserMap.remove(mReverseUserMap.get(pSocket));
		mReverseUserMap.remove(pSocket);
		
		UpdateUserList();
		
		System.out.println("One client left. There are now " + mUserMap.size() + " clients." );
	}
}
