import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.html.*;

public class OneToMany extends JFrame implements ActionListener
{
	private Container container;
	public JEditorPane recv;
	private JLabel totalmembers;
	private JTextArea type, members;
	private JButton send;
	public String chatname;
	private int itotal = 1;
	private JTextField totalfield;
	private String pmember_count;

	public OneToMany(String[] array, String instancename)
	{
		this.chatname = instancename;
		OneToManyFrame(array);
	}

	public void OneToManyFrame(String[] array)
	{
		container= this.getContentPane();
		container.setLayout(null);
		recv = new JEditorPane();
		recv.setEditorKit(new HTMLEditorKit());
		recv.setEditable(false);
		JScrollPane pane = new JScrollPane(recv, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(10,10,290,230);
		type = new JTextArea();
		type.setFont(new Font("Arial",Font.PLAIN,11));
		type.setLineWrap(true);
		members = new JTextArea();
		totalmembers = new JLabel("Total Chatters: ");
		totalfield = new JTextField();
		members.setEditable(false);
		JScrollPane typepane = new JScrollPane(type, 
		JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		typepane.setBounds(10,250,210,20);
		JScrollPane memberpane = new JScrollPane(members,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		totalfield.setEditable(false);
		memberpane.setBounds(310,10,130,220);
		totalmembers.setBounds(315, 240, 100, 20);
		totalfield.setBounds(415, 240, 20, 20);
		send = new JButton("Send");
		send.setBounds(235,249,65,20);
		send.addActionListener(this);
		appendMembers(array);
		container.add(memberpane);
		container.add(pane);
		container.add(typepane);
		container.add(send);
		container.add(totalmembers);
		container.add(totalfield);
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
					ChatName.removeFrame(chatname);
				}
			}
		});

		send.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							setVisible(false);
							ChatName.removeFrame(chatname);
						}
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
				ChatName.removeFrame(chatname);
				String temp = "26 " + LogIn.username + " " + chatname;
				LogIn.thisclient.SendMessage(temp);
			}

    		public void windowOpened( WindowEvent e ){
    		    type.requestFocus();
    	    }
		});

		this.setVisible(true);
		type.requestFocus();
	}
	public void appendMember(String user)
	{
		int imember_count = Integer.parseInt(pmember_count.trim());
		imember_count = imember_count + 1;
		pmember_count = "" + imember_count;
		totalfield.setText(pmember_count);
		String prevMem = members.getText();
		members.setText(prevMem + user + "\n");
		
	}
	
	public void removeMember(String user)
	{
		int imember_count = Integer.parseInt(pmember_count.trim());
		imember_count = imember_count - 1;
		pmember_count = "" + imember_count;
		totalfield.setText(pmember_count);
		String all_members = members.getText();
		all_members = all_members.replace(user+"\n", "");
		members.setText(all_members);
	}
	public void appendMembers(String[] memberArray)
	{
		String addMem = "";
		String prevMem = "";
		String member_count = memberArray[3];
		int imember_count = Integer.parseInt(member_count.trim());
		int ipmember_count = imember_count + 1;
		pmember_count = "" + ipmember_count;
		members.setText(LogIn.username + "\n");
		for (int i = 0; i < imember_count; i++)
		{
			addMem = memberArray[i+4];
			prevMem = members.getText();
			members.setText(prevMem + addMem + "\n");
		}
		totalfield.setText(pmember_count);
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