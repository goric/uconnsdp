import java.awt.*;
import javax.swing.*;

import java.net.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class FileTrans extends JDialog implements ActionListener
{
	private JLabel descript, user;
	private Container container;
	private JButton open, send;
	private JTextField fn, un;
	private JFileChooser fc;
	private File file;
	public static boolean wait, go;
	public static Socket sock;
	public static ServerSocket servsock;
	
	public FileTrans (JFrame aframe)
	{
		super (aframe, "File Transfer", true);
		FileFrame(aframe);
	}
	
	private void FileFrame(JFrame aframe)
	{

		go = false;
		file = null;

		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		fn = new JTextField(25);
		un = new JTextField(15);
		open = new JButton("open");
		send = new JButton("send");
		descript = new JLabel("File Name: ");
		user = new JLabel("User name: ");
		open.setActionCommand("opn");
		send.setActionCommand("snd");
        layout.putConstraint(SpringLayout.NORTH, descript,
				 5,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, descript,
				 5,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, user,
				 50,
				 SpringLayout.NORTH, container);
       layout.putConstraint(SpringLayout.WEST, user,
				 5,
				 SpringLayout.WEST, container);
        
        
        layout.putConstraint(SpringLayout.NORTH, fn,
				 25,
				 SpringLayout.NORTH, container);
       layout.putConstraint(SpringLayout.WEST, fn,
				 5,
				 SpringLayout.WEST, container);
       layout.putConstraint(SpringLayout.NORTH, un,
				 65,
				 SpringLayout.NORTH, container);
     layout.putConstraint(SpringLayout.WEST, un,
				 5,
				 SpringLayout.WEST, container); 
       
       
        layout.putConstraint(SpringLayout.NORTH, send,
				 140,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, send,
				 200,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, open,
				 140,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, open,
				 50,
				 SpringLayout.WEST, container);
        
		container.add(descript);
		container.add(user);
		container.add(open);
		container.add(send);
		container.add(fn);
		container.add(un);
		open.addActionListener(this);
		send.addActionListener(this);
		
		this.setSize(320,220);
		this.setResizable(false);
		this.setLocation(400,200);
		this.setVisible(true);
	}
	
    protected void quit() {
    	System.out.println("Program Terminated by User");
        System.exit(0);
    }
	
	public void actionPerformed(ActionEvent e)
	{
		if ("opn".contentEquals(e.getActionCommand())) 
		{
        	System.out.println("Open File to send");
        	//int x = fc.showOpenDialog(FileTrans.this);
        	
        	fc = new JFileChooser();
        	file = null;
        	
            int x = fc.showDialog(FileTrans.this, "Open");
        	
            if (x == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                //This is where a real application would open the file.
                try {
                fn.setText(file.getCanonicalPath()); }
                catch (IOException ioe){}
            } 
            else {
                
            }
        } 
        else if ("snd".contentEquals(e.getActionCommand()))
        {
        	boolean doSend = true;
        	System.out.println("Send File");
        	if (file == null)
        		doSend = false;
        	if (un.getText() == "")
        		doSend = false;
        	
        	if (doSend == true)
        	{
        		System.out.println("sending file");
        		this.setVisible(false);
        		wait = true;	
        		int port = 13267;
        		
        		if (servsock == null){
        		try
        		{
        		 servsock = new ServerSocket(port);


        		}
        		catch (IOException io){System.out.println("error");}
        		
        		}
     		      String temp = "09 " + LogIn.username + " " + un.getText() + " " + file.getName() + " " + Client.ip + " " + port + " " + file.length();
       		      
    		      LogIn.thisclient.SendMessage(temp);
        	}
        }
        
        else
        {
        	quit();
        }
	}
	
	public  void send() throws IOException {
	      sock = servsock.accept();

	      System.out.println("Accepted connection : " + sock);

	      // sendfile
	      // File myFile = new File ("fishing_the_sky.mp3");
	      byte [] mybytearray  = new byte [(int)file.length()];
	      FileInputStream fis = new FileInputStream(file);
	      BufferedInputStream bis = new BufferedInputStream(fis);
	      bis.read(mybytearray,0,mybytearray.length);
	      OutputStream os = sock.getOutputStream();
	      System.out.println("Sending...");
	      os.write(mybytearray,0,mybytearray.length);
	      os.flush();
	      sock.close();
	      wait = false;
	      //System.out.println("Finished");
	      
	      JFrame frame = new JFrame();
	      JOptionPane.showMessageDialog(frame,
	    		    "File Transfer Complete", 
	    		    "Success",
	    		    JOptionPane.PLAIN_MESSAGE);
	      
		
	}
	
	public static void main(String args[]){
	/*	JFrame f = new JFrame();
		FileTrans ft = new FileTrans(f);
		ft.setVisible(true);
		*/
	}
	
	
}