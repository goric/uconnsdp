import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class ChatWindow extends JFrame implements ActionListener
{
	private Container container;
	public JEditorPane recv;
	private JTextArea type;
	private JButton send;
	private String user;
	private String temp = "";
	private String msg;
	private String time;
	boolean isFocused = false;

	public ChatWindow(String user, int x, int y)
	{
		this.user = user;
		ChatWindowFrame(x, y);
	}

	public void ChatWindowFrame(int x, int y)
	{
		container= this.getContentPane();
		container.setLayout(null);
		recv = new JEditorPane();
		recv.setEditorKit(new HTMLEditorKit());
		recv.setEditable(false);
		JScrollPane pane = new JScrollPane(recv, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(10,10,305,135);
		type = new JTextArea();
		type.setFont(new Font("Arial",Font.PLAIN,12));
		type.setLineWrap(true);
		JScrollPane typepane = new JScrollPane(type, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		typepane.setBounds(10,155,235,20);
		send = new JButton("Send");
		send.setBounds(250,154,65,20);
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
					getTime();
					AppendChatWindow.appendData(LogIn.username,type.getText(),false, (ChatWindow)ClientGUI.frameTable.get(user.toString()), time);
					temp = "03 " + LogIn.username + " " + user + " " + msg;
					LogIn.thisclient.SendMessage(temp);
					type.setText("");
					type.setCaretPosition(0);
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
			}
		});

		this.setResizable(true);
		this.setSize(330,225);
		this.setTitle(user+" - Message");
		System.out.println(x + " " + y);
		this.setLocation(500 + x, 400 + y);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				ClientGUI.removeFrame(user);
			}

			public void windowActivated(WindowEvent ae) {
				isFocused = true;
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

	public void getTime()
	{
	   Calendar cal = new GregorianCalendar();
	   time = "";
	   int hour24 = cal.get(Calendar.HOUR_OF_DAY);
	   int min = cal.get(Calendar.MINUTE);
	   if (hour24 < 10)
	   {
		   time = "[" + "0" + hour24 + ":";
	   }
	   else
	   {
		   time = "[" + hour24 + ":";
	   }
	   if (min < 10)
	   {
		   time = time + "0" + min + "]";
	   }
	   else
	   {
		   time = time + min + "]";
	   }
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if((event.getSource() == type)||(event.getSource() == send)) {
			if(type.getText().length() == 0) return;
			getTime();
			AppendChatWindow.appendData(LogIn.username,type.getText(),false, (ChatWindow)ClientGUI.frameTable.get(user.toString()), time);
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