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
	private JEditorPane recv;
	private JTextArea type;
	private JButton send;
	private User user;
	private Timer timer=null;
	boolean isFocused = false;

	public ChatWindow(ClientGUI frame,User user)
	{
		this.frame = frame;
		this.user = user;
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
					frame.removeFrame(user);
				} else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						Client myclient = new Client();
						String temp = "03 " + LogIn.username + " " + user + " " + "boob";
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
					frame.removeFrame(user);
				}
			}
		});


		send.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							setVisible(false);
							frame.removeFrame(user);
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
		this.setTitle(user+" - Message");
		this.setLocation(300,300);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				if(timer != null) timer.stop();
				frame.removeFrame(user);
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
			try {
				String temp = "03 " + LogIn.username + " " + user + " " + "hardcoded message";
				System.out.println(temp);
				LogIn.thisclient.SendMessage(temp);
			}
			catch(Exception e) {
				System.out.println("Error sending message");
			}
			type.setText("");
		}
	}
}