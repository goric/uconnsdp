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
    
    /*
     * Constructor - make a new list of clients connected and create a server socket
     * The choice of 6013 for a port is arbitrary...
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
	
	/*
	 * Main - all this needs to do is make an instance of the server
	 */
	public static void main(String args[])
	{
		Server aNewServer = new Server();
	}
	
	/*
	 * Guts of the server.  This accepts connections from clients and add then to the
	 * currently connected list.  Creates a new ServerConnection for each client.
	 */
	private void RunServer()
	{
		frame.setVisible( true);
		
		Socket aSocket = null;
		
		try
		{
			System.out.println( "Server started at " + InetAddress.getLocalHost().toString()
																		+ " port " + 6013);
			
			frame.actout("Server started at " + InetAddress.getLocalHost().toString()
																		+ " port " + 6013);
			
			/*String[] aStr = new String[3];
			aStr[0] = "01";
			aStr[1] = "User1";
			aStr[2] = "8cb2237d0679ca88db6464eac60da96345513964";
			protocol.ServerProtocolFunctions.ClientLogIn( aStr);
			
			/*String[] Str = new String[4];
			Str[0] = "01";
			Str[1] = "User1";
			protocol.ServerProtocolFunctions.SendContactList( Str);*/
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
					//use synchronized on all accesses to any item that could be shared between threads.
					//    We're all in OS, right?  Look how easy it is here compared to in C..
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
				
				//attempt to shut down gracefully on error
				try 
				{
					aSocket.close();
				}
				catch( Exception ee) {
					System.out.println( "Error in closing Server.RunServer's socket: " + ee.toString());
				}
			}
		}
	}
	
	/*
	 * This function is responsible for parsing the message received from any user
	 *  to determine the message code it uses, and then calling the proper method to handle it.
	 */
	public static void ReceiveMessageFromClient( String pMessage)
	{	
		if( pMessage != null)
		{
			System.out.println(pMessage);
			
			//frame.actout(pMessage);
			
			synchronized( pMessage)
			{
				//find user who sent message
				String[] aMessage = pMessage.split( " ");
				
				//Parse message to get the message code
				int aMsgCode = Integer.parseInt( aMessage[0]);
				
				//depending on message code, call proper function, passing message as param
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
						break;
						
					case 7:
						//server should NEVER receive this code - client use only
						break;
						
					case 8:
						protocol.ServerProtocolFunctions.SendMessageToEntireChat( aMessage);
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
						//protocol.ServerProtocolFunctions.SendLoginMessage( aMessage);
						break;
					
					case 17:
						protocol.ServerProtocolFunctions.SendLogoutNotification( aMessage);
						protocol.ServerProtocolFunctions.WriteLogoutTime( aMessage);
						frame.actout( aMessage[1] + " logged out.");
						break;
						
					case 18:
						//server should NEVER receive this code - client use only
						//protocol.ServerProtocolFunctions.SendStatusChangeNotification( aMessage);
						break;
						
					case 19:
						//protocol.ServerProtocolFunctions.WriteLogoutTime( aMessage);
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
					
					default:
						//SendMessageToClients( );
						break;
				}
			}
		}
		else
		{
			
		}
	}
	
	
	/*
	 * Temporarily this is a stub. This will eventually be a number of functions which will,
	 *  depending on the circumstances, send a number of different messages (i.e. connection status,
	 *  buddy list, another user's status change, messages..)
	 *  
	 *  This function will be used to notify all users on about status changes and people
	 *  	signing on and off.
	 */
	public static synchronized void SendMessageToAllClients( String pMessage)
	{
		DataOutputStream aDataOutStream;
		
		/*for(int i=0; i < mConnectedList.size(); i++) 
		{	
			try 
			{
				aDataOutStream = new DataOutputStream( ( mConnectedList.get(i) ).getOutputStream());
				aDataOutStream.writeUTF( pMessage);
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.SendMessageToClients: " + e.toString());
			}
		}*/
		Set<String> aKeySet = mUserMap.keySet();
		Iterator<String> aIterator = aKeySet.iterator();
		
		while(aIterator.hasNext())
		{
			String aKey = aIterator.next();
			try
			{
				aDataOutStream = new DataOutputStream( ( mUserMap.get(aKey) ).getOutputStream());
				aDataOutStream.writeUTF( pMessage);
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.SendMessageToClients: " + e.toString());
			}
		}
	}
	
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
				System.out.println("Error in Server.SendMessageToClients: " + e.toString());
			}
		}
	}
	
	//remove a user from list of connected.
	//should also kill/close the socket connection and kill the thread
	// also notify all clients of the removal (for buddy list)
	public static synchronized void RemoveUser( Socket pSocket)
	{
		mUserMap.remove(mReverseUserMap.get(pSocket));
		mReverseUserMap.remove(pSocket);
		
		UpdateUserList();
		
		System.out.println("One client left. There are now " + mUserMap.size() + " clients." );
	}
}