import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

public class PopManage extends JFrame implements ActionListener
{
	private JLabel descript;
	private Container container;
	private JButton addc, addb, ret;
	JLabel userlab;
	JTextField userfield;
	JButton subbutton, canbutton;
	
	public PopManage (JFrame aframe)
	{
		ManageFrame(aframe);
	}
	
	private void ManageFrame(JFrame aframe)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		addc = new JButton("Add User");
		addb = new JButton("Remove User");
		ret = new JButton("Return");
		addc.setActionCommand("con");
		addb.setActionCommand("block");
		ret.setActionCommand("ret");
        layout.putConstraint(SpringLayout.NORTH, addc,
				 320,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, addc,
				 200,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, addb,
				 320,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, addb,
				 50,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, ret,
				 350,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, ret,
				 125,
				 SpringLayout.WEST, container);
		container.add(addc);
		container.add(addb);
		container.add(ret);
		UserList thislist = new UserList(this);
		container.add(thislist);
		addc.addActionListener(this);
		addb.addActionListener(this);
		ret.addActionListener(this);
		this.setSize(320,420);
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
		if ("con".contentEquals(e.getActionCommand())) 
		{
        	System.out.println("Adding a User to Contact List");
        	addContact();
        } 
        else if ("block".contentEquals(e.getActionCommand()))
        {
        	System.out.println("Removing a User from the Contact List");
        	addBlock();
        }
        else if ("ret".contentEquals(e.getActionCommand()))
  		{
        	System.out.println("Returning from Manage Screen");
        	this.setVisible(false);
  		}
        else
        {
        	System.out.println("I Bugged Out!");
        	quit();
        }
	}
	public void addContact()
	{
		ShowAdd();

	}
	public void addBlock()
	{
		ShowRemove();
	}
    private void ShowAdd()
    {
		AddUser dialog = new AddUser(this);
	}
    private void ShowRemove()
    {
    	RemoveUser dialog = new RemoveUser(this);
    }
}
