
import java.awt.*;
import javax.swing.*;

import java.net.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class RecieveFile extends JDialog {
	  
int cancel,n;
private ProgressMonitor progressMon;

	private File file_loc(JFileChooser f){
		 File file;
		 file = null;
		int returnVal = f.showSaveDialog(RecieveFile.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             file = f.getSelectedFile();
            //This is where a real application would save the file.
            System.out.println("Save location: " + file.getAbsolutePath());
        }
        else if (returnVal == JFileChooser.CANCEL_OPTION) {
        	System.out.println("cancel hit");
        	  String temp2 = "22 " +  Client.filetran[2] + " " + Client.filetran[1] + " reject";
		      LogIn.thisclient.SendMessage(temp2);
		     cancel =1;
		    // n=1;
		     
		      
        }
        
        return file;
		
	}
	
	public void RecieveFile()  throws IOException{
		File f;   
		 f=null;
		 cancel=0;
		int filesize = Integer.parseInt(Client.filetran[6])+10000  ; // filesize temporary hardcoded

			//RecieveFile rc = new RecieveFile();
		    JFileChooser fc = new JFileChooser();
		   
		    JFrame frame = new JFrame("Confirm");
		     n=0;
		     n = JOptionPane.showConfirmDialog(
		    	    frame,
		    	    "User " + Client.filetran[2] + " would like to send file " + Client.filetran[3]  ,
		    	    "Confirm File Transfer",
		    	    JOptionPane.YES_NO_OPTION);
		    
			System.out.println("n value is " + n );
		    
		   // Client sends: [22][FromUser][ToUser][accept/reject][
		    if (n == 0){
		      f = file_loc(fc);
		    	

		    	
		     
		    //System.out.println("asdd");
		    //fc.showSaveDialog(recieverClient.this);
		    if (cancel==0){
			
		    String temp = "22 " + Client.filetran[2] + " " + Client.filetran[1] + " " +"accept" + " " + 123;
			LogIn.thisclient.SendMessage(temp);
		    	
		    long start = System.currentTimeMillis();
		    int bytesRead;
		    int current = 0;
		   
		    
		    String blah = Client.filetran[4];
		    int colon = blah.indexOf(':');
		    blah = blah.substring(1, colon);
		    
		    System.out.println("Sender IP " + blah);
		    
		    int port = Integer.parseInt(Client.filetran[5]);
		    
		    if (f !=null){
		    Socket sock = new Socket(blah , port);
		    System.out.println("Connecting...");

		    // receive file
		    byte [] mybytearray  = new byte [filesize];
		    InputStream is = sock.getInputStream();
		    FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    bytesRead = is.read(mybytearray,0,mybytearray.length);
		    current = bytesRead;
		    System.out.println("Transferring...");

		    progressMon = new ProgressMonitor(RecieveFile.this,
                    "Transfering "+ Client.filetran[3],
                    "", 0, Integer.parseInt(Client.filetran[6]));



		    do {
		    	progressMon.setProgress((int)(bytesRead/(Integer.parseInt(Client.filetran[6]))));
		       bytesRead =
		          is.read(mybytearray, current, (mybytearray.length-current));
		       if(bytesRead >= 0) current += bytesRead;
		    } while(bytesRead > -1);

		    bos.write(mybytearray, 0 , current);
		    long end = System.currentTimeMillis();
		    System.out.println("Transfer complete.");
		    //  JFrame frame = new JFrame();
		      JOptionPane.showMessageDialog(frame,
		    		    "File Transfer Complete", 
		    		    "Success",
		    		    JOptionPane.PLAIN_MESSAGE);
		    bos.close();
		    sock.close();
		    }
		    }
		    }
		    if (n==1)
		    {
		    	  System.out.println("Reject Message");
	  		      String temp2 = "22 " +  Client.filetran[2] + " " + Client.filetran[1] + " reject";
			      LogIn.thisclient.SendMessage(temp2);
		    }
	}
}

