import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.event.*;
import javax.swing.Timer;
import javax.swing.text.html.*;
import java.io.*;

public class UserNotes extends JFrame implements ActionListener
{
	private ClientGUI frame;
	private UserNotes thisframe;
	private Container container;
	public JTextArea recv;
	private JButton close;
	private JButton save;
	private String user;
	private Timer timer=null;
	boolean isFocused = false;
	private String str;

	public UserNotes(String user)
	{
		this.user = user;
		MainNotes();
	}

	public void MainNotes()
	{
		thisframe = this;
		container= this.getContentPane();
		container.setLayout(null);

		recv = new JTextArea();
		recv.setFont(new Font("Arial",Font.PLAIN,11));
		recv.setLineWrap(true);

		JScrollPane pane
			= new JScrollPane(recv,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(10,10,290,100);
		save = new JButton("Save");
		close = new JButton("Close");
		close.setBounds(50,130,95,30);
		save.setBounds(160,130,95,30);
		save.addActionListener(this);
		close.addActionListener(this);
		save.setActionCommand("save");
		close.setActionCommand("close");

		container.add(pane);
		container.add(close);
		container.add(save);
		
		readnotes();


		close.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							setVisible(false);
						}
					}
		});

		recv.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent me) {
				isFocused = true;
				if(timer != null)timer.stop();
			}
		});

		this.setResizable(false);
		this.setSize(310,210);
		this.setTitle(user+"'s Notes");
		this.setLocation(300,300);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				if(timer != null) timer.stop();
			}

			public void windowActivated(WindowEvent ae) {
				isFocused = true;
				if(timer != null) timer.stop();
			}

			public void windowDeactivated(WindowEvent ae) {
				isFocused = false;
			}
		});

		this.setVisible(true);
		isFocused = false;
		
		recv.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(container, "DOH!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					recv.setText("");
				}
			}
		}
		);
	}
	
	public void readnotes()
	{
        try {
			FileReader input = new FileReader(LogIn.username + "'s notes on " + user + ".txt");
			BufferedReader bufRead = new BufferedReader(input);
			
            String line; 	// String that holds current file line
            int count = 0;	// Line number of count 

            line = bufRead.readLine();

            while (line != null){
            	recv.setText(line);
                line = bufRead.readLine();
            }
            
            bufRead.close();
			
        }catch (ArrayIndexOutOfBoundsException e){

			System.out.println("Usage: java ReadFile filename\n");			

		}catch (IOException e){

            e.printStackTrace();
        }
	}
	public void writenotes(String input)
	{
		try{
		    FileWriter fstream = new FileWriter(LogIn.username + "'s notes on " + user + ".txt");
		        BufferedWriter out = new BufferedWriter(fstream);
		    out.write(str);

		    out.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
	}

	public void actionPerformed(ActionEvent e)
	{
		if ("close".contentEquals(e.getActionCommand())) 
		{
			this.setVisible(false);
        } 
		else if ("save".contentEquals(e.getActionCommand()))
		{
			str = recv.getText();
			writenotes(str);
			this.setVisible(false);
		}
		
	}
}