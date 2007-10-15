package server;

import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;

public class Server 
{
	public static Map<String, Socket> mUserMap = new HashMap<String, Socket>();
	public static Vector mConnectedList = new Vector();
	
	private ServerSocket mServerSocket ;
	public protocol.ServerConnection mServerConnection;
	
    private boolean mIsConnected;
    
    /*
     * Constructor - make a new list of clients connected and create a server socket
     * The choice of 6013 for a port is arbitrary...
     */
	public Server()
	{
		mConnectedList = new Vector();
		
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
		Socket aSocket = null;
		
		try
		{
			System.out.println( "Server started at " + InetAddress.getLocalHost().toString()
																		+ " port " + 6013);
			/*String[] aStr = new String[3];
			aStr[0] = "01";
			aStr[1] = "User1";
			aStr[2] = "8cb2237d0679ca88db6464eac60da96345513964";
			protocol.ServerProtocolFunctions.ClientLogIn( aStr);
			
			String[] Str = new String[4];
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
						mConnectedList.addElement( aSocket);
						
						System.out.println( "Client number " + mConnectedList.size() + " just entered.");
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
	 * Temporarily this is a stub. This will eventually be a number of functions which will,
	 *  depending on the circumstances, send a number of different messages (i.e. connection status,
	 *  buddy list, another user's status change, messages..)
	 *  
	 *  This function will be used to notify all users on about status changes and people
	 *  	signing on and off.
	 */
	public static synchronized void SendMessageToClients( String pMessage)
	{
		DataOutputStream aDataOutStream;
		
		for(int i=0; i < mConnectedList.size(); i++) 
		{	
			try 
			{
				aDataOutStream = new DataOutputStream( ( (Socket)mConnectedList.elementAt(i) ).getOutputStream());
				aDataOutStream.writeUTF( pMessage);
			}
			catch(Exception e) 
			{
				System.out.println("Error in Server.SendMessageToClients: " + e.toString());
			}
		}
	}
	
	/*
	 * Temporarily this function is a stub.  It will eventually be responsible for parsing the text
	 *  received according to our protocol and from there calling the proper function to do the 
	 *  task required.
	 */
	public static void ReceiveMessageFromClient( String pMessage)
	{	
		if( pMessage != null)
		{
			synchronized( pMessage)
			{
				//for initial/testing purposes
				//SendMessageToClients( pMessage);
				
				//find user who sent message
				String[] aMessage = pMessage.split(" ");
				
				//Parse message to get first 2 characters
				//depending on that, call proper function, passing message as param
				int aMsgCode = Integer.parseInt(aMessage[0]);
				
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
					
					break;
					
				case 5:
					protocol.ServerProtocolFunctions.SendSingleChatInvite( aMessage);
					break;
					
				case 6:
					protocol.ServerProtocolFunctions.SendMultipleChatInvites( aMessage);
					break;
					
				case 7:
					
					break;
					
				case 8:
					protocol.ServerProtocolFunctions.SendMessageToEntireChat( aMessage);
					break;
					
				case 9:
					protocol.ServerProtocolFunctions.SendFileTransferRequest( aMessage);
					break;
					
				case 10:
					
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
				
				default:
					//this will really be blank eventually
					SendMessageToClients( pMessage);
					break;
				}
			}
		}
		//if message is null, then this user has become disconnected.  Remove them from
		// the list of online users and close that socket.  Also send a message to all clients
		// who are on so they know that person signed off.
		else
		{
			
		}
	}
	
	//remove a user from list of connected.
	//should also kill/close the socket connection and kill the thread
	// also notify all clients of the removal (for buddy list)
	public static void RemoveUser( Socket pSocket)
	{
		synchronized(mConnectedList){
			mConnectedList.remove( pSocket);
		}
		
		System.out.println("One client left. There are now " + mConnectedList.size() + " clients." );
	}
}
