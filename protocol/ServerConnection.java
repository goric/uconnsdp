package protocol;
import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable
{
	private DataInputStream mDataInStream;
	private DataOutputStream mDataOutStream;
	
	private Socket mClientSocket;
	private Thread mThread;
	
	private boolean mIsRunning;

	/*
	 * Constructor - Create a new ServerConnection thread with the socket passed in.
	 * 
	 * I don't think this class will really need to do too much more than this.
	 */
	public ServerConnection( Socket pSocket)
	{
		try	
		{
			this.mClientSocket = pSocket;
			this.mIsRunning = true;

			mDataInStream = new DataInputStream( mClientSocket.getInputStream());
			mDataOutStream = new DataOutputStream( mClientSocket.getOutputStream());
			
			mThread = new Thread( this);
			mThread.start();
		}
		catch(Exception e)
		{
			System.out.println( "Error in ServerConnection constructor: " + e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * Obligatory implementation of run().  Thread will accept input and send it to 
	 * the Server's ReceiveMessageFromClient function
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
				server.Server.RemoveUser(this.mClientSocket);
				
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