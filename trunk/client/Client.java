package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable 
{
	private Socket mClientSocket;
	private Thread mClientThread;
	
	private DataInputStream mDataInStream;
	private DataOutputStream mDataOutStream;
	
	private static boolean mIsConnected;
	public static String mClientAddress;
	
	/*
	 * Constructor - set up the instance of the client and get its identification.
	 * Temporarily, this ID is the host name of the machine it is running on.
	 */
    public Client()
    {
    	mIsConnected = false;
    	
    	try
    	{
    		mClientAddress = InetAddress.getLocalHost().toString();
    	}
    	catch(Exception e)
    	{
    		System.out.println( "Failure in getting address: " + e.toString());
    		System.exit(-1);
    	}
    }
    
    /*
     * Main - takes no args. Eventually this will be tied directly to the GUI.
     */
	public static void main(String args[])
    {
  		Client aNewClient = new Client();
  		
  		//This is connecting to Tim's IP - you can edit it to localhost for testing
  		aNewClient.GetServerConnection( "137.99.129.59");
  		
  		/*
  		 * The rest of this function is just driver code for testing only.
  		 * The real deal will handle this portion through the GUI.
  		 */
  		InputStreamReader aInReader = new InputStreamReader( System.in);
		BufferedReader aBufferedReader = new BufferedReader( aInReader);
  		
  		while( true)
  		{
  			try
  			{
				String aTestMessage = aBufferedReader.readLine();
				aNewClient.SendMessage( mClientAddress + ":  " +  aTestMessage);
  			}
  			catch(Exception e)
  			{
  				System.out.println( "Error in Client.main testing: " + e.toString());
  				System.exit(-1);
  			}
  		}
    }
    
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * Any function implementing Runnable must contain a run() function.
	 * This must implement Runnable to be multi-threaded.
	 */
    public void run()
    {
        while( mIsConnected) 
        {
			try 
			{
				GetMessageFromServer();
			}
			catch( Exception e)
			{
				System.out.println( "Error in Client.run: " + e.toString());
				mIsConnected = false;
			}
	   	}
        
	   	System.exit(-1);
    }
    
    /*
     * Connect this client to the ServerConnection class.  This is the gateway between it
     * and the server.
     */
	public void GetServerConnection( String pServer)
	{
		
		try	
		{
			this.mClientSocket = new Socket( pServer, 6013);
			
			this.mDataInStream = new DataInputStream( this.mClientSocket.getInputStream());
			this.mDataOutStream = new DataOutputStream( this.mClientSocket.getOutputStream());
			
			mIsConnected = true;
			
			this.mClientThread = new Thread( this);
			this.mClientThread.start();
		}
		catch( Exception e) 
		{
			System.out.println( "Error in Client.GetServerConnection: " + e.toString());
			System.exit(-1);
		}
	}

    /*
     * This function is temporarily a stub.  In the end, we will need several functions
     *  similar to this one, to write to one/some/all users and complete other
     *  data transfer tasks.
     */
	public void SendMessage( String pMessage)
	{
		try
		{
			this.mDataOutStream.writeUTF( pMessage);
			this.mDataOutStream.flush();
		}
		catch(IOException ioe)
		{
			System.out.println( " Error in Client.SendMessage: " + ioe.toString());
		}
	}

	/*
	 * Like the function above, this is a stub for now.  In the end, we will need
	 * 	a more complex set of functions to parse the server's message and decide what
	 *  to do with it (i.e., in one case it will be a buddy list of a sign on/off notification
	 *  instead of a message for the user.)
	 */
	public void GetMessageFromServer()
	{
		try
		{
			String aMessage = mDataInStream.readUTF();
			System.out.println( aMessage);
		}
		catch(Exception e)
		{
			mIsConnected = false;
			System.out.println( "Error in Client.GetMessageFromServer: " + e.toString());
		}
	}
}
