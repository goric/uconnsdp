import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.Timer;
import javax.swing.text.html.*;

public class OneToMany extends JFrame implements ActionListener
{
	private ClientGUI frame;
	private OneToMany thisframe;
	private Container container;
	public JEditorPane recv;
	private JTextArea type, members;
	private JButton send;
	public String chatname;
	public static String chatnameblah = "chizat";
	private Timer timer=null;
	boolean isFocused = false;
	private String[] array;

	public OneToMany(String[] array, String instancename)
	{
		this.chatname = instancename;
		this.array = array;
		OneToManyFrame(array);
	}

	public void OneToManyFrame(String[] array)
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
		pane.setBounds(10,10,290,230);

		type = new JTextArea();
		type.setFont(new Font("Arial",Font.PLAIN,11));
		type.setLineWrap(true);
		members = new JTextArea();
		members.setEditable(false);
		JScrollPane typepane
			= new JScrollPane(type,
					JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		typepane.setBounds(10,250,210,22);
		JScrollPane memberpane
		= new JScrollPane(members,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memberpane.setBounds(310,10,130,260);
		send = new JButton("Send");
		send.setBounds(235,250,65,22);
		send.addActionListener(this);
		appendMembers(array);
		container.add(memberpane);
		container.add(pane);
		container.add(typepane);
		container.add(send);

		type.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
					ClientGUI.removeFrame(chatname);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					if(type.getText().length() == 0) return;
					AppendChatWindow.appendData3(LogIn.username,type.getText(),false, (OneToMany)ChatName.onetomanyTable.get(chatname));
					String str = type.getText();
					try {
						String temp = "08 " + LogIn.username + " " + chatname + " " + str;
						LogIn.thisclient.SendMessage(temp);
					}
					catch(Exception e) {
						System.out.println("Error sending message");
					}
					type.setText("");
				}
			}
		}
		);


		type.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
				//	ClientGUI.removeFrame(user);
				}
			}
		});


		send.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							setVisible(false);
							//ClientGUI.removeFrame(user);
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
		this.setSize(450,310);
		this.setTitle(chatname+" - " + LogIn.username);
		this.setLocation(300,300);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				if(timer != null) timer.stop();
				ClientGUI.removeFrame(chatname);
				String temp = "26 " + LogIn.username + " " + chatname;
				LogIn.thisclient.SendMessage(temp);
				System.out.println(temp);
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
	public void appendMember(String user)
	{
		String prevMem = members.getText();
		members.setText(prevMem + user + "\n");
	}
	
	public void removeMember(String user)
	{
		String all_members = members.getText();
		all_members.replace(user, "");
	}
	public void appendMembers(String[] memberArray)
	{
		String addMem = "";
		String prevMem = "";
		String member_count = memberArray[3];
		int imember_count = Integer.parseInt(member_count.trim());
		for (int i = 0; i < imember_count; i++)
		{
			System.out.println("i got to append");
			addMem = memberArray[i+4];
			prevMem = members.getText();
			members.setText(prevMem + addMem + "\n");
		}
	}

	public void actionPerformed(ActionEvent event)
	{
		if((event.getSource() == type)||(event.getSource() == send)) {
			if(type.getText().length() == 0) return;
			AppendChatWindow.appendData3(LogIn.username,type.getText(),false, (OneToMany)ChatName.onetomanyTable.get(chatname));
			String blah = type.getText();
			try {
				String temp = "08 " + LogIn.username + " " + chatname + " " + blah;
				LogIn.thisclient.SendMessage(temp);
			}
			catch(Exception e) {
				System.out.println("Error sending message");
			}
			type.setText("");
		}
	}
}