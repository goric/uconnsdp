import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.Timer;
import javax.swing.text.html.*;

public class UserInfo extends JFrame implements ActionListener
{
	private ClientGUI frame;
	private UserInfo thisframe;
	private Container container;
	public JEditorPane recv;
	private JButton send;
	private User user;
	private Timer timer=null;
	boolean isFocused = false;

	public UserInfo(User user)
	{
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

		send = new JButton("Close");
		send.setBounds(105,120,95,50);
		send.addActionListener(this);
		send.setActionCommand("close");

		container.add(pane);
		container.add(send);


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
		this.setTitle(user+"'s Info");
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
		
	}
}