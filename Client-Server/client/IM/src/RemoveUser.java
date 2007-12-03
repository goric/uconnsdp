import java.awt.*;
import javax.swing.*;

import java.awt.event.*;


public class RemoveUser extends JDialog implements ActionListener
{
	private Container container;
	private JLabel userlab;
	private JTextField userfield;
	private JButton subbutton, canbutton;
	
	public RemoveUser (JFrame aframe, String user)
	{
		super (aframe, "Remove User", true);
		RemoveFrame(aframe, user);
	}
	
	private void RemoveFrame(JFrame aframe, String user)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		userlab = new JLabel("Username: ");
		userfield = new JTextField(9);
		userfield.setText(user);
		subbutton = new JButton("Remove");
		subbutton.setActionCommand("rem");
		subbutton.setMnemonic(KeyEvent.VK_ENTER); 
		canbutton = new JButton("Cancel");
		canbutton.setActionCommand("can");
		SpringLayout.Constraints subCst = layout.getConstraints(subbutton);
		subCst.setX(Spring.constant(10));
		subCst.setY(Spring.constant(5));
		SpringLayout.Constraints canCst = layout.getConstraints(canbutton);
		canCst.setX(Spring.constant(10));
		canCst.setY(Spring.constant(5));
		Spring widthSpring = Spring.max(subCst.getWidth(), canCst.getWidth());
		subCst.setWidth(widthSpring);
		canCst.setWidth(widthSpring);
        layout.putConstraint(SpringLayout.NORTH, userfield, 30, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userfield, 60, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, userlab, 5, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userlab, 60, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, subbutton, 125, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, subbutton, -20, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, canbutton, 125, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, canbutton, 20, SpringLayout.WEST, container);    
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
        } 
        else
        {
        }
	}
}