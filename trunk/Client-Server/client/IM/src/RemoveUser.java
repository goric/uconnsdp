import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

public class RemoveUser extends JDialog implements ActionListener
{
	private JLabel descript;
	private Container container;
	private JButton addc, addb, ret;
	private JLabel userlab;
	private JTextField userfield;
	private JButton subbutton, canbutton;
	
	public RemoveUser (JFrame aframe)
	{
		super (aframe, "Remove User", true);
		RemoveFrame(aframe);
	}
	
	private void RemoveFrame(JFrame aframe)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		userlab = new JLabel("Username: ");
		userfield = new JTextField(9);
		subbutton = new JButton("Remove");
		subbutton.setActionCommand("rem");
		subbutton.setMnemonic(KeyEvent.VK_ENTER); 
		canbutton = new JButton("Cancel");
		canbutton.setActionCommand("can");
        layout.putConstraint(SpringLayout.NORTH, userfield,
				 30,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userfield,
				 60,
				 SpringLayout.WEST, container);
       
        layout.putConstraint(SpringLayout.NORTH, userlab,
				 5,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userlab,
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
		container.add(userlab);
		container.add(userfield);
		container.add(subbutton);
		container.add(canbutton);
		
		userfield.addActionListener(this);
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
	
	public void actionPerformed(ActionEvent e)
	{
		if ("can".contentEquals(e.getActionCommand())) 
		{
        	System.out.println("Not Removing Any User");
        	this.setVisible(false);
        } 
        else if ("rem".contentEquals(e.getActionCommand())) 
		{
        	String username = userfield.getText();
        	String temp = "21 " + LogIn.username + " " + username;
        	LogIn.thisclient.SendMessage(temp);
        	this.setVisible(false);
        	ClientGUI.giveitawhirl();
        } 
        else
        {
        	System.out.println("I Bugged Out!");
        	quit();
        }
	}
}