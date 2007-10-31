import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.EventObject;
import java.io.*;

public class PopOptions extends JDialog implements ActionListener
{
	private JLabel descript;
	private Container container;
	private JButton save, cancel;
	
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
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		descript = new JLabel("Options Will Be Here");
		save.setActionCommand("sav");
		cancel.setActionCommand("can");
        layout.putConstraint(SpringLayout.NORTH, descript,
				 5,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, descript,
				 60,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, save,
				 320,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, save,
				 200,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, cancel,
				 320,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, cancel,
				 50,
				 SpringLayout.WEST, container);
		container.add(descript);
		container.add(save);
		container.add(cancel);
		save.addActionListener(this);
		cancel.addActionListener(this);
		
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
		if ("sav".contentEquals(e.getActionCommand())) 
		{
        	System.out.println("Changes Were Saved");
        	// Update Info with Independent Methods
        	this.setVisible(false);
        } 
        else if ("can".contentEquals(e.getActionCommand()))
        {
        	System.out.println("Changes Were Thrown Out");
        	this.setVisible(false);
        }
        else
        {
        	quit();
        }
	}
}