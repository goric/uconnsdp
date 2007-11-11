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
		frame.setVisible(true);
		
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
						frame.actout("Server reads connection as: " + aSocket.getRemoteSocketAddress().toString());
						
						frame.clearusers();
						UpdateUserList();
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
						break;
						
					case 2:
						protocol.ServerProtocolFunctions.SendContactList( aMessage);
						break;
						
					case 3:
						protocol.ServerProtocolFunctions.SendSingleMessage( aMessage);
						break;
						
					case 4:
						// server should NEVER receive this code - client use only
						break;
						
					case 5:
						protocol.ServerProtocolFunctions.SendSingleChatInvite( aMessage);
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
						break;
						
					case 10:
						//server should NEVER receive this code - client use only
						break;
						
					case 11:
						protocol.ServerProtocolFunctions.GetUserInfo( aMessage);
						break;
						
					case 12:
						protocol.ServerProtocolFunctions.SetStatusMessage( aMessage);
						break;
						
					case 13:
						protocol.ServerProtocolFunctions.RemoveStatusMessage( aMessage);
						break;
						
					case 14:
						protocol.ServerProtocolFunctions.SetProfileInformation( aMessage);
						break;
						
					case 15:
						protocol.ServerProtocolFunctions.GetCommonContacts( aMessage);
						break;
						
					case 16:
						//server should NEVER receive this code - client use only
						//protocol.ServerProtocolFunctions.SendLoginMessage( aMessage);
						break;
					
					case 17:
						protocol.ServerProtocolFunctions.SendLogoutNotification( aMessage);
						break;
						
					case 18:
						//server should NEVER receive this code - client use only
						//protocol.ServerProtocolFunctions.SendStatusChangeNotification( aMessage);
						break;
						
					case 19:
						protocol.ServerProtocolFunctions.WriteLogoutTime( aMessage);
						RemoveUser( server.Server.mUserMap.get( aMessage[1]));
						break;
					
					case 20:
						protocol.ServerProtocolFunctions.AddUserToBuddyList( aMessage);
						break;
						
					case 21:
						protocol.ServerProtocolFunctions.RemoveUserFromBuddyList( aMessage);
						break;
						
					case 22:
						protocol.ServerProtocolFunctions.SendFileTransferRequestResponse( aMessage);
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
	
	//remove a user from list of connected.
	//should also kill/close the socket connection and kill the thread
	// also notify all clients of the removal (for buddy list)
	public static synchronized void RemoveUser( Socket pSocket)
	{
		mUserMap.remove(mReverseUserMap.get(pSocket));
		mReverseUserMap.remove(pSocket);
		
		frame.clearusers();
		UpdateUserList();
		
		System.out.println("One client left. There are now " + mUserMap.size() + " clients." );
		frame.actout("One client left. There are now " + mUserMap.size() + " clients.");
	}
	
	public static synchronized void UpdateUserList(){
		Set<String> aKeySet = mUserMap.keySet();
		Iterator<String> aIterator = aKeySet.iterator();
		
		while(aIterator.hasNext())
		{
			String aKey = aIterator.next();
			try
			{
				String user = mUserMap.get(aKey).toString();
				frame.userlist(user);
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.SendMessageToClients: " + e.toString());
			}
		}
	}
}
