import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
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
	
	public PopManage ()
	{
		ManageFrame();
	}
	
	private void ManageFrame()
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
		UserList thislist = new UserList(this);
        layout.putConstraint(SpringLayout.WEST, thislist, 20, SpringLayout.WEST, container);
		container.add(thislist);
		addcon.addActionListener(this);
		removecon.addActionListener(this);
		ret.addActionListener(this);
		container2 = this;
		this.setSize(220,420);
		this.setResizable(false);
		this.setLocation(440,230);
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
	public static void reload()
	{
		container2.setVisible(false);
		PopManage man = new PopManage();	
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
