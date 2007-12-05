import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UserNotes extends JFrame implements ActionListener
{
	private Container container;
	public JTextArea recv;
	private JButton close;
	private JButton save;
	private String user;
	private String str;

	public UserNotes(String user)
	{
		this.user = user;
		MainNotes();
	}

	public void MainNotes()
	{
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
		this.setResizable(false);
		this.setSize(310,210);
		this.setTitle(user+"'s Notes");
		this.setLocation(300,300);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});

		this.setVisible(true);
		recv.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						str = recv.getText();
						writenotes(str);
						setVisible(false);
					}
					catch(Exception e) {
						
						}
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
            String line;
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
        }catch (Exception e){}
	}
	public void writenotes(String input)
	{
		try{
		    FileWriter fstream = new FileWriter(LogIn.username + "'s notes on " + user + ".txt");
		        BufferedWriter out = new BufferedWriter(fstream);
		    out.write(str);
		    out.close();
		    }catch (Exception e){
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