import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

public class PopOptions extends JFrame implements ActionListener
{
	private JLabel awaylabel, status_header;
	private JTextField awayfield;
	private Container container;
	private JButton awaybutton, backbutton, dndbutton, probutton;
	private String awaymsg;
	private JCheckBox show_time, show_reg, show_status;
	public static boolean time_flag = true, reg_flag = true, status_flag = true;
	
	
	public PopOptions (JFrame aframe)
	{
	//	super (aframe, "Options", true);
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
		probutton = new JButton("Edit Profile");
		awayfield = new JTextField(15);
		awaylabel = new JLabel("Enter Away Message");
		status_header = new JLabel("Set Your Status");
		show_time = new JCheckBox("Show Online/Last Seen Time in Profile", true);
		show_reg = new JCheckBox("Show Registration Date in Profile", true);
		show_status = new JCheckBox("Show Status in Profile", true);
		awaybutton.setActionCommand("away");
		backbutton.setActionCommand("back");
		dndbutton.setActionCommand("dnd");
		probutton.setActionCommand("pro");
		show_time.setActionCommand("time");
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
        layout.putConstraint(SpringLayout.NORTH, probutton, 240, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, probutton, 50, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, show_time, 280, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, show_time, 130, SpringLayout.WEST, container);
		container.add(awayfield);
		container.add(awaylabel);
		container.add(awaybutton);
		container.add(backbutton);
		container.add(dndbutton);
		container.add(probutton);
	//	container.add(show_time);
		awaybutton.addActionListener(this);
		backbutton.addActionListener(this);
		dndbutton.addActionListener(this);
		probutton.addActionListener(this);
		show_time.addActionListener(this);
		this.setSize(320,420);
		this.setResizable(false);
		this.setLocation(400,200);
		this.setVisible(true);
		status_flag = show_status.isSelected();
		reg_flag = show_reg.isSelected();
		time_flag = show_time.isSelected();
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
        	String temp = "12 " + LogIn.username + " " + "away " + awaymsg;
    		LogIn.thisclient.SendMessage(temp);
        	this.setVisible(false);
        } 
        else if ("back".contentEquals(e.getActionCommand()))
        {
        	String temp = "13 " + LogIn.username + " " + "away " + awaymsg;
        	LogIn.thisclient.SendMessage(temp);
        	this.setVisible(false);
        }
        else if ("pro".contentEquals(e.getActionCommand()))
        {
			String temp = ("28 " + LogIn.username + " " + LogIn.username);
			System.out.println(temp);
			LogIn.thisclient.SendMessage(temp);
        }
        else if ("fil".contentEquals(e.getActionCommand()))
        {	
        	
        }
        else if ("time".contentEquals(e.getActionCommand()))
        {
        	time_flag = show_time.isSelected();
        }
        else
        {
        	quit();
        }
	}
}