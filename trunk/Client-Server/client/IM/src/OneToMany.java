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
	private JTextArea type;
	private JButton send;
	public static String chatname;
	private Timer timer=null;
	boolean isFocused = false;

	public OneToMany(String instancename)
	{
		this.chatname = instancename;
		initAwtContainer();
	}

	public void initAwtContainer()
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

		type.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
				//	ClientGUI.removeFrame(chatname);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						Client myclient = new Client();
						String temp = "03 " + LogIn.username + " " + chatname + " " + "boob";
						System.out.println(temp);
						myclient.SendMessage(temp);
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(container, "DOH!", "Error", JOptionPane.ERROR_MESSAGE);
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
		this.setSize(310,210);
		this.setTitle(chatname+" - " + LogIn.username);
		this.setLocation(300,300);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				if(timer != null) timer.stop();
				//ClientGUI.removeFrame(user);
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