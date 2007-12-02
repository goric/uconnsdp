import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.Timer;
import javax.swing.text.html.*;

public class ChatWindow extends JFrame implements ActionListener
{
	private ClientGUI frame;
	private ChatWindow thisframe;
	private Container container;
	public JEditorPane recv;
	private JTextArea type;
	private JButton send;
	private String user;
	private Timer timer=null;
	boolean isFocused = false;
	String temp = "";
	String msg;
	Client myclient = new Client();

	public ChatWindow(String user, int x, int y)
	{
		this.user = user;
		ChatWindowFrame(x, y);
	}

	public void ChatWindowFrame(int x, int y)
	{
		thisframe = this;
		container= this.getContentPane();
		container.setLayout(null);

		recv = new JEditorPane();
		recv.setEditorKit(new HTMLEditorKit());
		recv.setEditable(false);

		JScrollPane pane
			= new JScrollPane(recv,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(10,10,290,100);

		type = new JTextArea();
		type.setFont(new Font("Arial",Font.PLAIN,11));
		type.setLineWrap(true);

		JScrollPane typepane
			= new JScrollPane(type,
					JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		typepane.setBounds(10,120,220,50);


		send = new JButton("Send");
		send.setBounds(235,120,65,50);
		send.addActionListener(this);

		container.add(pane);
		container.add(typepane);
		container.add(send);

		type.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					setVisible(false);
					ClientGUI.removeFrame(user);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					if(type.getText().length() == 0) return;
					msg = type.getText();
					AppendChatWindow.appendData(LogIn.username,type.getText(),false, (ChatWindow)ClientGUI.frameTable.get(user.toString()));
					temp = "03 " + LogIn.username + " " + user + " " + msg;
					LogIn.thisclient.SendMessage(temp);
					type.setText("");
				}
			}
		});

		type.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
					ClientGUI.removeFrame(user);
				}
			}
		});


		send.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							setVisible(false);
							ClientGUI.removeFrame(user);
						}
					}
		});

		recv.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent me) {
				isFocused = true;
				if(timer != null)timer.stop();
			}
		});

		this.setResizable(true);
		this.setSize(310,210);
		this.setTitle(user+" - Message");
		System.out.println(x + " " + y);
		this.setLocation(500 + x, 400 + y);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				if(timer != null) timer.stop();
				ClientGUI.removeFrame(user);
			}

			public void windowActivated(WindowEvent ae) {
				isFocused = true;
				if(timer != null) timer.stop();
			}

			public void windowDeactivated(WindowEvent ae) {
				isFocused = false;
			}
    		public void windowOpened( WindowEvent e ){
    		    type.requestFocus();
    	    }
		});

		this.setVisible(true);
		type.requestFocus();
		isFocused = false;
	}


	public String toString()
	{
		return user.toString();
	}

	public void actionPerformed(ActionEvent event)
	{
		if((event.getSource() == type)||(event.getSource() == send)) {
			if(type.getText().length() == 0) return;
			AppendChatWindow.appendData(LogIn.username,type.getText(),false, (ChatWindow)ClientGUI.frameTable.get(user.toString()));
			String blah = type.getText();
			try {
				String temp = "03 " + LogIn.username + " " + user + " " + blah;
				LogIn.thisclient.SendMessage(temp);
			}
			catch(Exception e) {
				System.out.println("Error sending message");
			}
			type.setText("");
		}
	}
}