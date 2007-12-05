import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class AddUser extends JDialog implements ActionListener
{
	private Container container;
	private JLabel userlab;
	private JTextField userfield;
	private JButton subbutton, canbutton;
	
	public AddUser (JFrame aframe)
	{
		super (aframe, "AddUser", true);
		AddUserFrame(aframe);
	}
	
	private void AddUserFrame(JFrame aframe)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		userlab = new JLabel("Username: ");
		userfield = new JTextField(9);
		subbutton = new JButton("Add");
		subbutton.setActionCommand("add");
		subbutton.setMnemonic(KeyEvent.VK_ENTER); 
		canbutton = new JButton("Cancel");
		canbutton.setActionCommand("can");
		ImageIcon icon = new ImageIcon("username.jpg");
		JLabel userlab = new JLabel(icon);
		SpringLayout.Constraints subCst = layout.getConstraints(subbutton);
		subCst.setX(Spring.constant(10));
		subCst.setY(Spring.constant(5));
		SpringLayout.Constraints canCst = layout.getConstraints(canbutton);
		canCst.setX(Spring.constant(10));
		canCst.setY(Spring.constant(5));
		Spring widthSpring = Spring.max(subCst.getWidth(), canCst.getWidth());
		subCst.setWidth(widthSpring);
		canCst.setWidth(widthSpring);
        layout.putConstraint(SpringLayout.NORTH, userfield, 60, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userfield, 55, SpringLayout.WEST, container); 
        layout.putConstraint(SpringLayout.NORTH, userlab, 15, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, userlab, 45, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, subbutton, 125, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, subbutton, -30, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, canbutton, 125, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, canbutton, 30, SpringLayout.WEST, container);
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
		userfield.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					setVisible(false);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
		        	String username = userfield.getText();
		        	String temp = "20 " + LogIn.username + " " + username;
		        	LogIn.thisclient.SendMessage(temp);
		        	setVisible(false);
				}
			}
		});
	}
	
    protected void quit() {
    	System.out.println("Program Terminated by User");
        System.exit(0);
    }
	
	public void actionPerformed(ActionEvent e)
	{
		if ("can".contentEquals(e.getActionCommand())) 
		{
        	this.setVisible(false);
        } 
        else if ("add".contentEquals(e.getActionCommand())) 
		{
        	String username = userfield.getText();
        	String temp = "20 " + LogIn.username + " " + username;
        	LogIn.thisclient.SendMessage(temp);
        	this.setVisible(false);
        } 
        else
        {
        }
	}
}