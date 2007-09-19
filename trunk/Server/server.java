import java.io.*;
import java.net.*;

public class server{
	
	//initialize variables
	ServerSocket aServer = null;
	Socket aClient = null;
	BufferedReader inReader = null;
	PrintWriter outPrinter = null;
	String line;
	
	//constructor
	public server(){
	}
	
	public static void main(String[] args){
		//create a new instance of this class and call the listening function
		server thisServer = new server();
		thisServer.listenSocket();
	}
	
	public void listenSocket(){
		
		//open an accepting connection socket on port 6013
		try{
			aServer = new ServerSocket(6013);
			System.out.println("The server was started at " + new java.util.Date().toString());
		} catch(IOException ioe){
			System.out.println(ioe.toString());
		}
		
		//when a client attempts to connect, accept connection
		try{
			aClient = aServer.accept(); 
			System.out.println("A client connected at " + new java.util.Date().toString());
		} catch(IOException ioe){
			System.out.println(ioe.toString());
		}
		
		//once connection is made, set up input and output along that connection
		try{
			inReader = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
			outPrinter = new PrintWriter(aClient.getOutputStream(), true);
		} catch(IOException ioe){
			System.out.println(ioe.toString());
		}
		
		//run an infinite loop to wait for input
		while(true){
			try{
				line = inReader.readLine();
			} catch(IOException ioe){
				System.out.println(ioe.toString());
			}
			
			if(line == null){
				break;
			}
			
			System.out.println(new java.util.Date().toString() + ": Received text from the client: " + line);
		
			//in this test, the server automatically sends back all 
			// messages received.
			outPrinter.println("Server received: " + line);
		}
		
	}
	
	//override the java.lang.Object.finalize function to make sure that everything is
	// properly closed on exiting the connection
	protected void finalize(){
		
		//close the input/output mechanisms and the server
		try{
			inReader.close();
			outPrinter.close();
			aServer.close();
		} catch(IOException ioe){
			System.out.println(ioe.toString());
		}
		
	}
	
	

}
