import java.net.*;
import java.io.*;

public class server
{
	public static void main(String[] args)
	{
		try{
			ServerSocket aSocket = new ServerSocket(6013);

			while(true) {
				Socket aClient = aSocket.accept();

				PrintWriter pWriter = new 
					PrintWriter(aClient.getOutputStream(), true);

				pWriter.println(new java.util.Date().toString());

				aClient.close();
			}
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
