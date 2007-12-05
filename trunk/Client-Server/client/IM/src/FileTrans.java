import java.awt.*;
import javax.swing.*;

import java.net.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

import java.util.*;
import java.security.*;
import javax.crypto.*;

public class FileTrans extends JDialog implements ActionListener, Runnable
{
	private JLabel descript, user;
	private Container container;
	private JButton open, send;
	private JTextField fn, un;
	private JFileChooser fc;
	private File file;
	public boolean wait, go;
	public Socket sock;
	public ServerSocket servsock;
	public ProgMonitor p ;
	
	public FileTrans (JFrame aframe)
	{
		super (aframe, "File Transfer", true);
		
		Thread aThread = new Thread(this);
		aThread.start();
		
		FileFrame(aframe);
	}
	
	public void run()
	{
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

		un.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
		        	send_file();
				}
			}
		});
		fn.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
		        	send_file();
				}
			}
		});
		send.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
		        	send_file();
				}
			}
		});
		open.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					choose_file();
				}
			}
		});
		
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
			choose_file();
        } 
        else if ("snd".contentEquals(e.getActionCommand()))
        {
        	send_file();
        }
        
        else {
        	quit();
        }
	}
	
	public void choose_file(){
    	System.out.println("Open File to send");
    	
    	fc = new JFileChooser();
    	file = null;
    	
        int x = fc.showDialog(FileTrans.this, "Open");
    	
        if (x == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            try {
            fn.setText(file.getCanonicalPath()); }
            catch (IOException ioe){}
        } 
        else {
            file = null;
        }
	}
	
	public void send_file(){
		JFrame jf = new JFrame();
    	boolean doSend = true;
    	System.out.println("Send File");
    	if (file == null){
    		doSend = false;
    		
  	      JOptionPane.showMessageDialog(jf,
	    		    "No File Selected", 
	    		    "Success",
	    		    JOptionPane.PLAIN_MESSAGE);
    		
    	}
    	if (un.getText() == ""){
    		doSend = false;
    	
    	      JOptionPane.showMessageDialog(jf,
    	    		    "No User Selected", 
    	    		    "Success",
    	    		    JOptionPane.PLAIN_MESSAGE);	
    	}
    		
    	if (doSend == true)
    	{
    		System.out.println("sending file");
    		this.setVisible(false);
    		wait = true;	
    		int port = 10422;
    		
    		if (servsock == null){
    		try	{
    		 servsock = new ServerSocket(port);
    		}
    		catch (IOException io){System.out.println("servsock error");}
    		
    		}
 		      String temp = "09 " + LogIn.username + " " + un.getText() + " " + file.getName() + " " + Client.ip + " " + port + " " + (file.length()+10000);
		      LogIn.thisclient.SendMessage(temp);
    	}
	}
	

	public  void send() throws IOException {
		
		//try
	//	{
		//if (servsock == null){
		//try { 
		//ServerSocket servsock = new ServerSocket(13267);
		//}
		///catch (IOException io){System.out.println("no socket created");}
		//}
	    // while (true) 
	    // {
		
		
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		PublicKey key = null;
		
		String ciphertextFile = "chatter_encrypted";
		
	      System.out.println("Waiting...");
	      sock = null;
	      
	    	  sock = servsock.accept();

	      System.out.println("Accepted connection : " + sock);
		
		try
		{
			InputStream in = sock.getInputStream();
			System.out.println("Waiting...1");
			ObjectInputStream objIn = new ObjectInputStream(in);
			System.out.println("Waiting...2");
			key = (PublicKey)objIn.readObject();
		}
		catch(Exception e)
		{}
		
		Cipher RSAcipher = null;
		SecretKey secretKey = null;
		Cipher AEScipher = null;
		
		try
		{
			System.out.println("Waiting...3");
			RSAcipher = Cipher.getInstance("RSA");
			System.out.println("Waiting...4");
			RSAcipher.init(Cipher.ENCRYPT_MODE, key);
			System.out.println("Waiting...5");
			KeyGenerator aGen = KeyGenerator.getInstance("AES");
			System.out.println("Waiting...6");
			aGen.init(128);
			System.out.println("Waiting...7");
			secretKey = aGen.generateKey();
			System.out.println("Waiting...8");
			byte[] aEncodedKey = RSAcipher.doFinal(secretKey.getEncoded());
			System.out.println("Waiting...9");
			for(int i=0;i<aEncodedKey.length; i++)
			{
				System.out.println(aEncodedKey[i]);
			}
			
			OutputStream os = sock.getOutputStream();
			DataOutputStream aObjOut = new DataOutputStream(os);
  
			aObjOut.write(aEncodedKey,0,aEncodedKey.length);
		    aObjOut.flush();
		      
		    AEScipher = Cipher.getInstance("AES");
		    AEScipher.init(Cipher.ENCRYPT_MODE, secretKey);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		CipherOutputStream cos = null;

		byte[] block = new byte[8];
		
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(ciphertextFile);
			cos = new CipherOutputStream(fos, AEScipher);
					
			int i = 0;
		
			while ((i = fis.read(block)) != -1) {
				cos.write(block, 0, i);
			}
			cos.close();
			
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
	      File aFile = new File (ciphertextFile);
	      byte [] mybytearray  = new byte [(int)aFile.length()];

		      fis = new FileInputStream(aFile);
		      System.out.println("File length "+ aFile.length());
 
		      BufferedInputStream bis = new BufferedInputStream(fis);
		      bis.read(mybytearray,0,mybytearray.length);
		      OutputStream os = sock.getOutputStream();
		      
		      System.out.println("Sending...");
		      p = new ProgMonitor();
		      p.setfilesize(mybytearray.length);
		     
		      int send = 65536;
		      int offset = 0;
		      while (mybytearray.length > offset){
		      
		      	if ((mybytearray.length-offset) > 65536){
		      		os.write(mybytearray, offset, send);
		      		offset += send;
		      		System.out.println("offset " + offset + "  sent "+send);
		      		set(offset, send);	      	
		      	}
		      	else{
		      		os.write(mybytearray, offset, (mybytearray.length-offset));
		      		offset += mybytearray.length-offset;
		      		System.out.println("ELSE offset " + offset+ "  sent "+(mybytearray.length-offset));
		      		set(offset, send);	
		      	}
		      }  		    
		      os.flush();
		      p.setVisible(false);
		      sock.close();   
		      servsock.close();
		 // }
		//}
		//catch (IOException io){}
		      
		      fis.close();
		      fos.close();
		      bis.close();
		      os.close();
		      

	      JFrame frame = new JFrame();
	      JOptionPane.showMessageDialog(frame,
	    		    "File Transfer Complete", 
	    		    "Success",
	    		    JOptionPane.PLAIN_MESSAGE);
	      
	    	boolean success = (new File("chatter_encrypted")).delete();
	    	if (!success) System.out.println("didn't delete file");
		
	}
	
	public void set(int off, int sent){
		p.setprog(off, sent);
	}
}