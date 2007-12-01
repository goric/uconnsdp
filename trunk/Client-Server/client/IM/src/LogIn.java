import java.awt.*;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LogIn extends JFrame implements ActionListener
{
	public static String username, password, hashpass;
	private JTextField userfield;
	private JPasswordField passfield;
	private JButton logbutton, qbutton;
	private JLabel userlab, passlab;
	private Container container;
	public static Client thisclient = new Client();
	
	public LogIn (Client client)
	{
		LoginFrame();
	}
	
	private void LoginFrame()
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		userlab = new JLabel("Username: ");
		passlab = new JLabel("Password: ");
		userfield = new JTextField(9);
		passfield = new JPasswordField(9);
		logbutton = new JButton("Login");
		logbutton.setActionCommand("log");
		logbutton.setMnemonic(KeyEvent.VK_ENTER);
		qbutton = new JButton("Quit");
		qbutton.setActionCommand("quit");
		ImageIcon icon = new ImageIcon("logo.jpg");
		JLabel logo = new JLabel(icon);
		SpringLayout.Constraints okCst =
			layout.getConstraints(qbutton);
			okCst.setX(Spring.constant(10));
			okCst.setY(Spring.constant(5));
			SpringLayout.Constraints cancelCst =
				layout.getConstraints(logbutton);
				cancelCst.setX(Spring.constant(10));
				cancelCst.setY(Spring.constant(5));
			Spring widthSpring = Spring.max(
					okCst.getWidth(),
					cancelCst.getWidth());
					okCst.setWidth(widthSpring);
					cancelCst.setWidth(widthSpring);
        layout.putConstraint(SpringLayout.NORTH, userfield, 105, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userfield, 80, SpringLayout.WEST, container);    
        layout.putConstraint(SpringLayout.NORTH, passfield, 105, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, passfield, -80, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, userlab, 85, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userlab, 80, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, passlab, 85, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, passlab, -120, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, logbutton, 135, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, logbutton, -108, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, qbutton, 135, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, qbutton, 108, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, logo, 10, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, logo, 10, SpringLayout.WEST, container);
		container.add(userlab);
		container.add(passlab);
		container.add(userfield);
		container.add(passfield);
		container.add(logbutton);
		container.add(qbutton);
		container.add(logo);
		
		userfield.addActionListener(this);
		passfield.addActionListener(this);
		logbutton.addActionListener(this);
		qbutton.addActionListener(this);
		
		this.setTitle("Welcome to Chatter");
		this.setSize(390,200);
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
		if ("log".contentEquals(e.getActionCommand())) 
		{
        	username = userfield.getText();
        	password = new String(passfield.getPassword());
        	System.out.print("the username is: ");
        	System.out.println(username);
        	System.out.print("the password is: ");
        	System.out.println(password);
        	authenticatelogin();
        	
        } 
        else
        {
        	quit();
        }
		this.setVisible(false);
	}
	
	private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	            if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
 
    public static String SHA1(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  {
	MessageDigest md;
	md = MessageDigest.getInstance("SHA-1");
	byte[] sha1hash = new byte[40];
	md.update(text.getBytes("iso-8859-1"), 0, text.length());
	sha1hash = md.digest();
	return convertToHex(sha1hash);
    }

 
	protected String getUsername()
	{
		return username;
	}
	
	protected String getPassword()
	{
		return password;
	}
	
	public void authenticatelogin()
	{
		try {
			hashpass = SHA1(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		thisclient.GetServerConnection("137.99.129.59");
		String ipsend = Client.ip;
		String temp = "01 " + username + " " + hashpass + " " + ipsend;
		thisclient.SendMessage(temp);
		temp = "02 " + username;
		thisclient.SendMessage(temp);
		Point p = new Point(200,300);
		ClientGUI gui = new ClientGUI(p);
	}
	}

