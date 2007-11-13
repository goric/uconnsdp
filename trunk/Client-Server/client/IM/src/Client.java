import java.net.InetAddress;
import java.net.Socket;
import java.io.*;

import javax.swing.tree.DefaultMutableTreeNode;

public class Client implements Runnable
{
private ClientGUI frame;
private Socket mClientSocket;
private Thread mClientThread;
public static String ip;
public static String[] anythingMessage;
public static String[] buddyarray;
public static String[] recarray;
public static String[] anythingMessage2;

private DataInputStream mDataInStream;
private DataOutputStream mDataOutStream;

private static boolean mIsConnected;
public static String mClientAddress;


public Client()
{
	InputStreamReader aInReader = new InputStreamReader( System.in);
	BufferedReader aBufferedReader = new BufferedReader( aInReader);
	anythingMessage = new String[1];
	anythingMessage[0] = "0";
	anythingMessage2 = new String[3];
	anythingMessage2[0] = "0";
	anythingMessage2[1] = "0";
	anythingMessage2[2] = "0";
}

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
		
		ip = mClientSocket.getLocalAddress() + ":" + mClientSocket.getLocalPort();
		System.out.println(ip);
	}
	catch( Exception e) 
	{
		System.out.println( "Error in Client.GetServerConnection: " + e.toString());
		System.exit(-1);
	}
}

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
public void GetMessageFromServer()
{
	String aMessage = "";
	String tehMessage = "";
	try
	{
		aMessage = mDataInStream.readUTF();
		System.out.println( aMessage);
	}
	catch(Exception e)
	{
		mIsConnected = false;
		System.out.println( "Error in Client.GetMessageFromServer: " + e.toString());
	}
	anythingMessage = aMessage.split(" ");
	if (anythingMessage[0].contentEquals("02"))
	{
		anythingMessage2 = anythingMessage;
		buddyarray = anythingMessage;
	}
	else if (anythingMessage[0].contentEquals("04"))
	{
        String boob = anythingMessage[3];
        int p = Integer.valueOf(boob).intValue();
        p = p + 4;
        for (int i = 4; i < p; i++)
        {
        	tehMessage = tehMessage + " " + anythingMessage[i];
        }
		String toUser = anythingMessage[2];
		User user = new User(toUser);
		ClientGUI.createFrame(user);	
		AppendChatWindow.appendData2(toUser, tehMessage, false,(ChatWindow)ClientGUI.frameTable.get(user.toString()) );
	}
	
}
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

}