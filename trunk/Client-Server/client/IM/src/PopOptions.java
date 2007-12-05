import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class PopOptions extends JFrame implements ActionListener
{
	private JLabel awaylabel, status_header, logo, logo2;
	private JTextField awayfield;
	private Container container;
	private JButton awaybutton, backbutton, dndbutton, probutton, eprobutton;
	
	
	public PopOptions ()
	{
		OptionsFrame();
	}
	
	private void OptionsFrame()
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		awaybutton = new JButton("Set Away");
		backbutton = new JButton("Set Back");
		dndbutton = new JButton("Set Do Not Disturb");
		eprobutton = new JButton("Edit Profile");
		probutton = new JButton("View Profile");
		awayfield = new JTextField(15);
		awaylabel = new JLabel("Enter Away Message");
		ImageIcon icon = new ImageIcon("options.jpg");
		logo = new JLabel(icon);
		ImageIcon icon2 = new ImageIcon("status.jpg");
		logo2 = new JLabel(icon2);
		status_header = new JLabel("Set Your Status");
		awaybutton.setActionCommand("away");
		backbutton.setActionCommand("back");
		dndbutton.setActionCommand("dnd");
		eprobutton.setActionCommand("epro");
		probutton.setActionCommand("pro");
		SpringLayout.Constraints eproCst = layout.getConstraints(eprobutton);
		eproCst.setX(Spring.constant(10));
		eproCst.setY(Spring.constant(5));
		SpringLayout.Constraints proCst = layout.getConstraints(probutton);
		proCst.setX(Spring.constant(10));
		proCst.setY(Spring.constant(5));
		Spring widthSpring = Spring.max(eproCst.getWidth(), proCst.getWidth());
		eproCst.setWidth(widthSpring);
		proCst.setWidth(widthSpring);
        layout.putConstraint(SpringLayout.NORTH, logo, 10, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, logo, 60, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, eprobutton, 70, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, eprobutton, 25, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, probutton, 70, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, probutton, -25, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, logo2, 115, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, logo2, 75, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, awaylabel, 170, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, awaylabel, 75, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, awayfield, 190, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, awayfield, 50, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, awaybutton, 220, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, awaybutton, 90, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, backbutton, 260, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, backbutton, 90, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, dndbutton, 300, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, dndbutton, 65, SpringLayout.WEST, container);
		container.add(awayfield);
		container.add(awaylabel);
		container.add(awaybutton);
		container.add(backbutton);
		container.add(dndbutton);
		container.add(eprobutton);
		container.add(logo);
		container.add(logo2);
		container.add(probutton);
		awaybutton.addActionListener(this);
		backbutton.addActionListener(this);
		dndbutton.addActionListener(this);
		probutton.addActionListener(this);
		eprobutton.addActionListener(this);
		this.setSize(280,380);
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
        	String temp = "12 " + LogIn.username + " " + "away " + awaymsg;
    		LogIn.thisclient.SendMessage(temp);
    		Client.status_away = true;
    		Client.away_msg = awaymsg;
        	this.setVisible(false);
        } 
        else if ("back".contentEquals(e.getActionCommand()))
        {
        	String temp = "13 " + LogIn.username;
        	LogIn.thisclient.SendMessage(temp);
        	Client.status_away = false;
        	this.setVisible(false);
        }
        else if ("epro".contentEquals(e.getActionCommand()))
        {
			String temp = ("28 " + LogIn.username + " " + LogIn.username);
			System.out.println(temp);
			LogIn.thisclient.SendMessage(temp);
        }
        else if ("dnd".contentEquals(e.getActionCommand()))
        {
        	String temp = ("12 " + LogIn.username + " " + "dnd");
        	LogIn.thisclient.SendMessage(temp);
        }
        else if ("pro".contentEquals(e.getActionCommand()))
        {	
        	String temp = ("11 " + LogIn.username + " " + LogIn.username);
        	LogIn.thisclient.SendMessage(temp);
        }
        else
        {
        }
	}
		public static void main(String args[]) throws Exception
		{
				PopOptions pop = new PopOptions();
		}
	
}