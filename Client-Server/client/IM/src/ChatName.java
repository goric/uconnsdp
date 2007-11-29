import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.util.Hashtable;
import java.io.*;

public class ChatName extends JDialog implements ActionListener
{
	private JLabel chatlab;
	private Container container;
	private JButton subbutton,canbutton;
	private JTextField chatfield;
	public static Hashtable onetomanyTable = new Hashtable();
	private boolean chatbool = true;
	private String instancename;
	private User user2;
	private static int i;
	static int cancel,n;
	static JTextField chatname2;
	static String staticname;
	
	public ChatName (User user2)
	{
		this.user2 = user2;
		ChatNameFrame();
	}
	
	private void ChatNameFrame()
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		chatlab = new JLabel("Chat Name: ");
		chatfield = new JTextField(9);
		subbutton = new JButton("Add");
		subbutton.setActionCommand("add");
		subbutton.setMnemonic(KeyEvent.VK_ENTER); 
		canbutton = new JButton("Cancel");
		canbutton.setActionCommand("can");
        layout.putConstraint(SpringLayout.NORTH, chatfield,
				 30,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, chatfield,
				 60,
				 SpringLayout.WEST, container);
       
        layout.putConstraint(SpringLayout.NORTH, chatlab,
				 5,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, chatlab,
				 60,
				 SpringLayout.WEST, container);
       
        layout.putConstraint(SpringLayout.NORTH, subbutton,
				 125,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, subbutton,
				 120,
				 SpringLayout.WEST, container);
       
        layout.putConstraint(SpringLayout.NORTH, canbutton,
				 125,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, canbutton,
	 			 40,
				 SpringLayout.WEST, container);
		container.add(chatlab);
		container.add(chatfield);
		container.add(subbutton);
		container.add(canbutton);
		chatname2 = chatfield;
		chatfield.addActionListener(this);
		subbutton.addActionListener(this);
		canbutton.addActionListener(this);
		
		this.setSize(220,200);
		this.setResizable(false);
		this.setLocation(400,200);
		this.setVisible(true);
		
	}
	
    protected void quit() {
    	System.out.println("Program Terminated by User");
        System.exit(0);
    }
    
    public static void RespondtoInvite(String inviter, String thechatname)
    {
	    JFrame frame = new JFrame("Confirm");
	     n = 0;
	     n = JOptionPane.showConfirmDialog(
	    	    frame,
	    	    inviter + " has invited YOU to join " + thechatname,
	    	    "Confirm Chat Invite",
	    	    JOptionPane.YES_NO_OPTION);
	    if (n == 0)
	    {
	    	createInvitedFrame(thechatname);
	    	
	    }	
	    if (cancel==0)
	    {
	  
	    }
    }
    
    public static void createConFrame()
    {
    	String instancename = "LogIn.username" + i; 
    	OneToMany dialog ;
    	synchronized(onetomanyTable)
    	{
    		dialog = (OneToMany) onetomanyTable.get(instancename);
    		if(dialog == null)
    		{
    			dialog = new OneToMany(instancename);
    			dialog.setLocation(500,400);
    			onetomanyTable.put(instancename,dialog);
    			i++;
    		}
    		else
    		{
    			i++;
    			createConFrame();
    		}
    	}
    }
    
    public static void createInvitedFrame(String thechatname)
    {
    	OneToMany dialog ;
    	synchronized(onetomanyTable)
    	{
    		dialog = (OneToMany) onetomanyTable.get(thechatname);
    		if(dialog == null)
    		{
    			dialog = new OneToMany(thechatname);
    			dialog.setLocation(500,400);
    			onetomanyTable.put(thechatname,dialog);
    		}
    		else
    		{
    			System.out.println("You are already in " + thechatname);
    		}
    	}
    }
	
	public void actionPerformed(ActionEvent e)
	{
		if ("add".contentEquals(e.getActionCommand())) 
		{
        	System.out.println("Creating Chat Instance");
	    	instancename = chatfield.getText(); 
	    	System.out.println(chatfield.getText());
	    	OneToMany dialog ;
	    	synchronized(onetomanyTable)
	    	{
	    		dialog = (OneToMany) onetomanyTable.get(instancename);
	    		if(dialog == null)
	    		{
	    			dialog = new OneToMany(instancename);
	    			dialog.setLocation(500,400);
	    			onetomanyTable.put(instancename,dialog);
	    			staticname = instancename;
	    			String temp = "05 " + LogIn.username + " " + user2 + " " + instancename;
	    			System.out.println(temp);
	    			LogIn.thisclient.SendMessage(temp);
	    			this.setVisible(false);
	    		}
	    		else
	    		{
	    			System.out.println("A chat with that name already exists");
	    			
	    		}
	    	}
			
        } 
        else if ("can".contentEquals(e.getActionCommand()))
  		{
        	this.setVisible(false);
  		}
        else
        {
        	System.out.println("I Bugged Out!");
        	quit();
        }
	}
}