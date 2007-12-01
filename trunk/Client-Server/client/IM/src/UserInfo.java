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
	private String user;
	private Timer timer=null;
	boolean isFocused = false;
	private String blank = "---------------------------------------------------------------";

	public UserInfo(String user)
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
		pane.setBounds(10,10,340,100);

		send = new JButton("Close");
		send.setBounds(105,120,95,50);
		send.addActionListener(this);
		send.setActionCommand("close");

		container.add(pane);
		container.add(send);
		setUserInfo();

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
		this.setSize(360,210);
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
	
	public void setUserInfo()
	{
		String str = new String();
		int ihours, iminutes, idays, iseconds;
		String [] myUserInfoArray = Client.userInfoArray;
		long l = Long.parseLong(myUserInfoArray[4].trim());
		long timestamp = l * 1000;
		java.util.Date d = new java.util.Date(timestamp);
		if (PopOptions.reg_flag == true)
		{
		str = ("Member Since : " + d);
		append(str);
		}
		if (PopOptions.time_flag == true)
		{
		if ((myUserInfoArray[3].contentEquals("online")))
		{
			String sseconds = myUserInfoArray[5];
			int itotal = Integer.parseInt(sseconds.trim());
			if (itotal > 86399)
			{
			    idays = itotal / 86400;
			    if (idays > 0)
			    {itotal = itotal - (idays * 86400);}
				ihours = itotal / 3600;
				if (ihours > 0)
				{itotal = itotal - (ihours * 3600);}
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("Online Time : " + idays + " days " + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
			}
			else if (itotal > 3599 && itotal < 86399)
			{
				ihours = itotal / 3600;
				if (ihours > 0)
				{itotal = itotal - (ihours * 3600);}
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("Online Time : " + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
			}
			else if (itotal > 59 && itotal < 3599)
			{
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("Online Time : " + iminutes + " minutes " + iseconds + " seconds");
			}
			else
			{
				iseconds = itotal;
				str = ("Online Time : " + iseconds + " seconds");
			}
			append(str);
		}
		else if ((myUserInfoArray[3].contentEquals("offline")))
		{
			String sseconds = myUserInfoArray[5];
			int itotal = Integer.parseInt(sseconds.trim());
			if (itotal > 86399)
			{
			    idays = itotal / 86400;
			    if (idays > 0)
			    {itotal = itotal - (idays * 86400);}
				ihours = itotal / 3600;
				if (ihours > 0)
				{itotal = itotal - (ihours * 3600);}
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("Last Seen : " + idays + " days " + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
			}
			else if (itotal > 3599 && itotal < 86399)
			{
				ihours = itotal / 3600;
				if (ihours > 0)
				{itotal = itotal - (ihours * 3600);}
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("Last Seen : " + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
			}
			else if (itotal > 59 && itotal < 3599)
			{
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("Last Seen : " + iminutes + " minutes " + iseconds + " seconds");
			}
			else
			{
				iseconds = itotal;
				str = ("Last Seen : " + iseconds + " seconds");
			}
			append(str);
		}
		}
		String sprofile = myUserInfoArray[6];
		int iprofile = Integer.parseInt(sprofile.trim());
		int awayspot = iprofile + 7;
		System.out.println(awayspot);
		String saway = myUserInfoArray[awayspot];
		int iaway = Integer.parseInt(saway.trim());
		String temp = "";
		if (PopOptions.status_flag == true)
		{
		if(iaway > 0 && (!(myUserInfoArray[3].contentEquals("offline"))))
		{
			System.out.println("I got here");
			temp = "Status : Away";
			append(temp);
			temp = "";
			for (int i = 2; i < iaway+1; i++)
			{
				temp = temp + myUserInfoArray[i+awayspot] + " ";
			}
			append(temp);
		}
		else if (myUserInfoArray[3].contentEquals("offline"))
		{
			temp = "Status : Offline";
			append(temp);
		}
		}
		append(blank);
		temp = "";
		if (iprofile > 0)
		{
			for (int i = 0; i < iprofile; i++)
			{
				temp = temp + myUserInfoArray[i+7] + " ";
			}
			append(temp);
		}
	}

	public void append(String str)
	{
		try {
			((HTMLEditorKit)recv.getEditorKit()).read(new java.io.StringReader(str),
					recv.getDocument(), recv.getDocument().getLength());
			recv.setCaretPosition(recv.getDocument().getLength());
		 	} catch(Exception e){}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if ("close".contentEquals(e.getActionCommand())) 
		{
			this.setVisible(false);
        } 
		
	}
}