import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class LogIn extends JFrame implements ActionListener
{
	public static String username, password, hashpass;
	private JTextField userfield;
	private JPasswordField passfield;
	private JButton logbutton, qbutton;
	private JLabel userlab, passlab, link;
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
		link = new HyperLinkLabel("www.chatterim.com", null, "http://www.chatterim.com");
		logbutton.setActionCommand("log");
		logbutton.setMnemonic(KeyEvent.VK_ENTER);
		qbutton = new JButton("Quit");
		qbutton.setActionCommand("quit");
		ImageIcon icon = new ImageIcon("logo.jpg");
		JLabel logo = new JLabel(icon);
		SpringLayout.Constraints quitCst = layout.getConstraints(qbutton);
		quitCst.setX(Spring.constant(10));
		quitCst.setY(Spring.constant(5));
		SpringLayout.Constraints logCst = layout.getConstraints(logbutton);
		logCst.setX(Spring.constant(10));
		logCst.setY(Spring.constant(5));
		Spring widthSpring = Spring.max(quitCst.getWidth(), logCst.getWidth());
		quitCst.setWidth(widthSpring);
		logCst.setWidth(widthSpring);
        layout.putConstraint(SpringLayout.NORTH, userfield, 105, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userfield, 80, SpringLayout.WEST, container);    
        layout.putConstraint(SpringLayout.NORTH, passfield, 105, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, passfield, -80, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, userlab, 85, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userlab, 95, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, passlab, 85, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, passlab, -105, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, logbutton, 135, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, logbutton, -108, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, qbutton, 135, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, qbutton, 108, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, logo, 10, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, logo, 10, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, link, 175, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, link, 130, SpringLayout.WEST, container);
		container.add(userlab);
		container.add(passlab);
		container.add(userfield);
		container.add(passfield);
		container.add(logbutton);
		container.add(qbutton);
		container.add(logo);
		container.add(link);
		userfield.addActionListener(this);
		passfield.addActionListener(this);
		logbutton.addActionListener(this);
		qbutton.addActionListener(this);
		this.setTitle("Welcome to Chatter");
		this.setSize(390,230);
		this.setResizable(false);
		this.setLocation(400,200);
		this.setVisible(true);
		passfield.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					quit();
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
		        	username = userfield.getText();
		        	password = new String(passfield.getPassword());
					authenticatelogin();
				}
			}
		});
	}
	
    protected void quit() {
    	System.out.println("Program Terminated by User in LogIn");
        System.exit(0);
    }
    
	public void actionPerformed(ActionEvent e)
	{
		if ("log".contentEquals(e.getActionCommand())) 
		{
        	username = userfield.getText();
        	password = new String(passfield.getPassword());
        	authenticatelogin();
        } 
        else if ("quit".contentEquals(e.getActionCommand()))
        {
        	quit();
        }
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
		while (!(Client.anythingMessage[0].contentEquals("01")))
		{
		}
		try {
		       Thread.currentThread().sleep(10);
		       }
		     catch (InterruptedException e) {
		       e.printStackTrace();
		       }
		if (Client.validflag == true)
		{
		System.out.println("sdaf " + Client.anythingMessage[0]);
		temp = "02 " + username;
		thisclient.SendMessage(temp);
		this.setVisible(false);
		Point p = new Point(200,300);
		ClientGUI gui = new ClientGUI(p);
		}
		else
		{
			invalidLog();
		}
	}
	public void invalidLog()
	{
		JOptionPane.showMessageDialog(container,"Invalid LogIn Information\n Please Try Again", "Error",JOptionPane.ERROR_MESSAGE); 
		userfield.setText("");
		username = "";
		passfield.setText("");
		password = "";
		Client.anythingMessage[0] = "00";
	}
	}

