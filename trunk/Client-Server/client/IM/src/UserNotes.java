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
	private JButton send;
	private JButton save;
	private User user;
	private Timer timer=null;
	boolean isFocused = false;
	private String str;

	public UserNotes(User user)
	{
		this.user = user;
		initAwtContainer();
	}

	public void initAwtContainer()
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
		send = new JButton("Close");
		save.setBounds(0,120,95,50);
		send.setBounds(105,120,95,50);
		save.addActionListener(this);
		send.addActionListener(this);
		save.setActionCommand("save");
		send.setActionCommand("close");

		container.add(pane);
		container.add(send);
		container.add(save);
		
		readnotes();


		send.addKeyListener(new KeyAdapter() {
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
			
			/*	Sets up a file reader to read the file passed on the command
				line one character at a time */
			FileReader input = new FileReader("notes on " + user + ".txt");
            
			/* Filter FileReader through a Buffered read to read a line at a
			   time */
			BufferedReader bufRead = new BufferedReader(input);
			
            String line; 	// String that holds current file line
            int count = 0;	// Line number of count 
            
            // Read first line
            line = bufRead.readLine();
            count++;
            
			// Read through file one line at time. Print line # and line
            while (line != null){
            	recv.setText(line);
                System.out.println(count+": "+line);
                line = bufRead.readLine();
                count++;
            }
            
            bufRead.close();
			
        }catch (ArrayIndexOutOfBoundsException e){
            /* If no file was passed on the command line, this expception is
			generated. A message indicating how to the class should be
			called is displayed */
			System.out.println("Usage: java ReadFile filename\n");			

		}catch (IOException e){
			// If another exception is generated, print a stack trace
            e.printStackTrace();
        }
	}
	public void writenotes(String input)
	{
		try{
		    // Create file 
		    FileWriter fstream = new FileWriter("notes on " + user + ".txt");
		        BufferedWriter out = new BufferedWriter(fstream);
		    out.write(str);
		    //Close the output stream
		    out.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
	}


	public String toString()
	{
		return user.toString();
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
			
		}
		
	}
}