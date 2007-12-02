import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.util.Hashtable;
import java.io.*;

public class PopManage extends JFrame implements ActionListener
{
	private JLabel descript;
	private Container container;
	private JButton addcon, removecon, ret;
	JLabel userlab;
	JTextField userfield;
	JButton subbutton, canbutton;
	private static Container container2;
	public static Hashtable buddyTable = new Hashtable();
	
	public PopManage (Point x)
	{
		ManageFrame(x);
	}
	
	private void ManageFrame(Point x)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		addcon = new JButton("Add User");
		removecon = new JButton("Remove");
		ret = new JButton("Return");
		addcon.setActionCommand("con");
		removecon.setActionCommand("removec");
		ret.setActionCommand("ret");
        layout.putConstraint(SpringLayout.NORTH, removecon, 310, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, removecon, 15, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, addcon, 310, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, addcon, 115, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, ret, 350, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, ret, 70, SpringLayout.WEST, container);
		container.add(addcon);
		container.add(removecon);
		container.add(ret);
		String instancename = LogIn.username;
		UserList contacts;
		contacts = (UserList) buddyTable.get(instancename);
		if(contacts == null)
		{
			contacts = new UserList(this);
			
		buddyTable.put(instancename,contacts);
		}
        layout.putConstraint(SpringLayout.WEST, contacts, 20, SpringLayout.WEST, container);
		container.add(contacts);
		addcon.addActionListener(this);
		removecon.addActionListener(this);
		ret.addActionListener(this);
		this.setSize(220,420);
		this.setResizable(false);
		this.setLocation(x);
		this.setVisible(true);
	}
	
    protected void quit() {
    	System.out.println("Program Terminated by User");
        System.exit(0);
    }
	
	public void actionPerformed(ActionEvent e)
	{
		if ("removec".contentEquals(e.getActionCommand())) 
		{
        	removeContact();
        } 
        else if ("con".contentEquals(e.getActionCommand()))
        {
        	addContact();
        }
        else if ("ret".contentEquals(e.getActionCommand()))
  		{
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
	public void removeContact()
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
