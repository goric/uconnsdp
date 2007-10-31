import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

public class AddContact extends JDialog implements ActionListener
{
	private JLabel descript;
	private Container container;
	private JButton sub,can;
	
	public AddContact (JFrame aframe)
	{
		super (aframe, "Add User", true);
		ContactAddFrame(aframe);
	}
	
	private void ContactAddFrame(JFrame aframe)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		sub = new JButton("Submit");
		can = new JButton("Cancel");
		sub.setActionCommand("sub");
		can.setActionCommand("can");
        layout.putConstraint(SpringLayout.NORTH, sub,
				 100,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, sub,
				 100,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, can,
				 50,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, can,
				 5,
				 SpringLayout.WEST, container);
		container.add(sub);
		container.add(can);
		sub.addActionListener(this);
		can.addActionListener(this);
		this.setSize(200,180);
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
		if ("sub".contentEquals(e.getActionCommand())) 
		{
        	System.out.println("Submitting User Name for Addition to List");
        	// Call Method to update list with new user
        } 
        else if ("can".contentEquals(e.getActionCommand()))
  		{
        	System.out.println("Canceling and Returning from Add User to Contacts");
        	this.setVisible(false);
  		}
        else
        {
        	System.out.println("I Bugged Out!");
        	quit();
        }
	}
}