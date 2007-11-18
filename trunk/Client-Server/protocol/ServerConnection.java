/**
 * Class for maintaining connections to the server.  This class is given one instance per connection that the
 * server recieves, and each instance is run as its own thread.
 *  
 * @author Tim Goric
 */

package protocol;

import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable
{
	private DataInputStream mDataInStream;
	
	private Socket mClientSocket;
	private Thread mThread;
	
	private boolean mIsRunning;

	/**
	 * Constructor for the ServerConnection class.
	 * @param pSocket - The instance of socket that this instance of ServerConnection is tied to.
	 */
	public ServerConnection( Socket pSocket)
	{
		try	
		{
			this.mClientSocket = pSocket;
			this.mIsRunning = true;

			mDataInStream = new DataInputStream( mClientSocket.getInputStream());
			
			mThread = new Thread( this);
			mThread.start();
		}
		catch(Exception e)
		{
			System.out.println( "Error in ServerConnection constructor: " + e.toString());
		}
	}

	/**
	 * This function is necessary in any class which implements Runnable. This function accepts messages from the client
	 * who belongs to that particluar thread and sends any recieved messages to the server's ReceiveMessageFromClient function.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		while( mIsRunning)
		{
			try	{
				String aMessage = mDataInStream.readUTF();
				server.Server.ReceiveMessageFromClient( aMessage);
			}
			catch( Exception e) 
			{
				mIsRunning = false;
				server.Server.ReceiveMessageFromClient("17 " + server.Server.mReverseUserMap.get(mClientSocket));
				server.Server.RemoveUser( mClientSocket);
				
				try	
				{
					mClientSocket.close();
				} 
				catch(Exception ee) {
					System.out.println( "Error in closing the socket in ServerConnection.run: " + ee);
				}
			}
		}
	}
}