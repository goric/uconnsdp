import java.io.*;
import java.net.*;

public class client {
	
	//initialize variables
	Socket aSocket = null;
	PrintWriter outWriter = null;
	BufferedReader inReader = null;
	
	//constructor
	public client(){
	}
	
	public static void main(String[] args){
		
		//create a new instance of this class and call the socket function
		client thisClient = new client();
		thisClient.callSocket();
		
		System.out.println("Begin communicating with the server:");
		
		//once connection is made, loop infinitely to 
		// communicate with the server until app is closed.
		while(true){
			thisClient.communicate();
		}
	}
	
	public void callSocket(){
		
		//try a new connection to the server application 
		// located at the specified IP and port - this it Tim's IP
		try{
			aSocket = new Socket("137.99.129.59", 6013);
			
			//if the connection is made, initialize input and output mechanisms
			inReader = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			outWriter = new PrintWriter(aSocket.getOutputStream(), true);
			
		} catch(UnknownHostException e){ //catch exception if connection not made
			System.out.println(e);
		}
		catch(IOException ioe){
			System.out.println(ioe);
		}
	}
	
	private void communicate(){
		
		//get input from stdin (console)
	    InputStreamReader inputText = new InputStreamReader( System.in );
	    BufferedReader aInputReader = new BufferedReader(inputText);
	    
	    try{
	    	String aMessage = aInputReader.readLine();
	    	
	    	//send message to server via socket
	    	outWriter.println(aMessage);
	    } catch(IOException ioe){
	    	System.out.println(ioe.toString());
	    }
	    
	    //receive the message back from the server 
	    // to prove it was received
	    try{
	    	String line = inReader.readLine();
	  	  	System.out.println(line);
	    } catch (IOException ioe){
	    	System.out.println(ioe.toString());
	    }
	}

}
