import java.net.InetAddress;
import java.net.Socket;
import java.io.*;

import javax.swing.JOptionPane;

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
public static String[] userInfoArray;
public static String[] commonArray;
public static String[] incomingArray;
private String[] ans;
public static String[] filetran;
public static String[] filetran2;
private int counter;
private boolean addorremove = false;

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
	counter = 0;
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
		if (counter == 0)
		{
		anythingMessage2 = anythingMessage;
		buddyarray = anythingMessage;
		counter = 1;
		}
		else
		{
		anythingMessage2 = anythingMessage;
		buddyarray = anythingMessage;
		if (addorremove == true)
		{
			PopManage.reload();
		}
		ClientGUI.giveitawhirl();
		}
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
		String user = anythingMessage[2];
		ClientGUI.createFrame(user);	
		AppendChatWindow.appendData2(user, tehMessage, false,(ChatWindow)ClientGUI.frameTable.get(user) );
	}
	else if (anythingMessage[0].contentEquals("07"))
	{
		String inviter = anythingMessage[2];
		String chatname = anythingMessage[3];
		ChatName.RespondtoInvite(inviter,chatname);
	}
	else if (anythingMessage[0].contentEquals("10"))
	{
		filetran = anythingMessage;
		RecieveFile rf = new RecieveFile();
		try {rf.RecieveFile();}
		catch (IOException ioe){};	
	}
	else if (anythingMessage[0].contentEquals("11"))
	{
		userInfoArray = anythingMessage;
		String user = new String(userInfoArray[2]);
		UserInfo userinfo = new UserInfo(user);
	}
	else if (anythingMessage[0].contentEquals("15"))
	{
		String theUser = anythingMessage[2];
		commonArray = anythingMessage;
		CommonContacts newCommon = new CommonContacts(theUser);
	}
	else if (anythingMessage[0].contentEquals("16")  || anythingMessage[0].contentEquals("17") || anythingMessage[0].contentEquals("20") || anythingMessage[0].contentEquals("21"))
	{
		if(anythingMessage[1].contentEquals(LogIn.username))
	{

	}
		if (anythingMessage[0].contentEquals("20") || anythingMessage[0].contentEquals("21"))
		{
			addorremove = true;
		}
		else
		{
			addorremove = false;
		}
		String temp = "02 " + LogIn.username;
		LogIn.thisclient.SendMessage(temp);
	}
	else if (anythingMessage[0].contentEquals("22"))
	{
		filetran2 = anythingMessage;
		
		//PopOptions.f.sock.close();
		
		if (filetran2[3].contentEquals("accept")){
			try{
				OnlineTree.f.send();}
			catch (IOException ioe){};
			
			try{OnlineTree.f.servsock.close();}
			catch (IOException ioe){};
		}
		else{
			JOptionPane.showMessageDialog(frame,
				    filetran2[2] + " rejected or canceled the file transfer",
				    "Rejected",
				    JOptionPane.ERROR_MESSAGE);
			
			//System.out.println("no go");
			//PopOptions.f.wait=false;
			
			try{OnlineTree.f.servsock.close();}
			catch (IOException ioe){};
			
		}
		//PopOptions.f.wait=true;
		//System.out.println("yay " + filetran2[3]);
	}
	else if (anythingMessage[0].contentEquals("24"))
	{
		tehMessage = "";
		incomingArray = anythingMessage;
		String fromUser = incomingArray[2];
		if (!(fromUser.contentEquals(LogIn.username)))
		{
        String msg_length = incomingArray[3];
        int p = Integer.valueOf(msg_length).intValue();
        p = p + 4;
        for (int i = 4; i < p; i++)
        {
        	tehMessage = tehMessage + " " + anythingMessage[i];
        }
		String toUser = anythingMessage[2];
		AppendChatWindow.appendData3(toUser, tehMessage, true,(OneToMany)ChatName.onetomanyTable.get(OneToMany.chatname) );
		}
	}
	else if (anythingMessage[0].contentEquals("25"))
	{
		String user = anythingMessage[1];
		String chatname = anythingMessage[2];
		String str = user + " has joined " + chatname;
		AppendChatWindow.appendData4(user, str, (OneToMany)ChatName.onetomanyTable.get(OneToMany.chatname));
	}
	else if (anythingMessage[0].contentEquals("26"))
	{
		String user = anythingMessage[1];
		String chatname = anythingMessage[2];
		String str = user + " has left " + chatname;
		AppendChatWindow.appendData4(user, str, (OneToMany)ChatName.onetomanyTable.get(OneToMany.chatname));
	}
	else if (anythingMessage[0].contentEquals("28"))
	{
		userInfoArray = anythingMessage;
		EditProfile editpro = new EditProfile();
	}
	else
	{
		System.out.println("I don't handle that code");
	}
}

public String[] addToArray(String[] array, String s)
{
   ans = new String[array.length+1];
   System.arraycopy(array, 0, ans, 0, array.length);
   ans[ans.length - 1] = s;
   return ans;
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