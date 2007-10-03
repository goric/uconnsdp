package server;

import java.util.*;
import java.net.*;
import java.io.*;

public class Server 
{
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
	 */
	private static synchronized void SendMessageToClients( String pMessage)
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
		synchronized( pMessage){
			SendMessageToClients( pMessage);
		}
	}
	
	public static void RemoveUser( Socket pSocket)
	{
		synchronized(mConnectedList){
			mConnectedList.remove( pSocket);
		}
		
		System.out.println("One client left. There are now " + mConnectedList.size() + " clients." );
	}
}
