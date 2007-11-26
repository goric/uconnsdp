import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

public class PopOptions extends JDialog implements ActionListener
{
	private JLabel awaylabel;
	private JTextField awayfield;
	private Container container;
	private JButton awaybutton, backbutton, dndbutton;
	private String awaymsg;
	
	
	public PopOptions (JFrame aframe)
	{
		super (aframe, "Options", true);
		OptionsFrame(aframe);
	}
	
	private void OptionsFrame(JFrame aframe)
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		awaybutton = new JButton("Set Away");
		backbutton = new JButton("Set Back");
		dndbutton = new JButton("Set Do Not Disturb");
		awayfield = new JTextField(15);
		awaylabel = new JLabel("Enter Away Message");
		awaybutton.setActionCommand("away");
		backbutton.setActionCommand("back");
		dndbutton.setActionCommand("dnd");
        layout.putConstraint(SpringLayout.NORTH, awaylabel, 20, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, awaylabel, 20, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, awayfield, 40, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, awayfield, 20, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, awaybutton, 37, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, awaybutton, 200, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, backbutton, 120, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, backbutton, 50, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, dndbutton, 120, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, dndbutton, 50, SpringLayout.WEST, container);
		container.add(awayfield);
		container.add(awaylabel);
		container.add(awaybutton);
		container.add(backbutton);
		container.add(dndbutton);
		awaybutton.addActionListener(this);
		backbutton.addActionListener(this);
		dndbutton.addActionListener(this);
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
		if ("away".contentEquals(e.getActionCommand())) 
		{
			String awaymsg = awayfield.getText();
        	System.out.println("Set to Away");
        	String temp = "12 " + LogIn.username + " " + "away " + awaymsg;
    		LogIn.thisclient.SendMessage(temp);
        	this.setVisible(false);
        } 
        else if ("back".contentEquals(e.getActionCommand()))
        {
        	System.out.println("Set to Back");
        	this.setVisible(false);
        }
        else if ("fil".contentEquals(e.getActionCommand()))
        {	
        	
        }
        else
        {
        	quit();
        }
	}
}