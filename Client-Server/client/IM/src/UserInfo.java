import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class UserInfo extends JFrame implements ActionListener
{
	private Container container;
	public JEditorPane recv;
	private JButton send;
	private String user;
	private String blank = "-------------------------------------------------------";

	public UserInfo(String user)
	{
		this.user = user;
		initAwtContainer();
	}

	public void initAwtContainer()
	{
		container= this.getContentPane();
		container.setLayout(null);
		recv = new JEditorPane();
		recv.setEditorKit(new HTMLEditorKit());
		recv.setEditable(false);
		JScrollPane pane = new JScrollPane(recv,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(10,10,310,130);
		send = new JButton("Close");
		send.setBounds(110,160,95,50);
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
		this.setResizable(false);
		this.setSize(330,250);
		this.setTitle(user+"'s Info");
		this.setLocation(300,300);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});
		this.setVisible(true);
	}
	
	public void setUserInfo()
	{
		String str = new String();
		int ihours, iminutes, idays, iseconds;
		String [] myUserInfoArray = Client.userInfoArray;
		long l = Long.parseLong(myUserInfoArray[4].trim());
		long timestamp = l * 1000;
		java.util.Date d = new java.util.Date(timestamp);
		str = ("<b>Member Since : </b>" + d);
		append(str);
		if ((myUserInfoArray[3].contentEquals("online")) || (myUserInfoArray[3].contentEquals("dnd")))
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
				str = ("<b>Online Time : </b>" + idays + " days " + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
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
				str = ("<b>Online Time : </b>" + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
			}
			else if (itotal > 59 && itotal < 3599)
			{
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("<b>Online Time : </b>" + iminutes + " minutes " + iseconds + " seconds");
			}
			else
			{
				iseconds = itotal;
				str = ("<b>Online Time : </b>" + iseconds + " seconds");
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
				str = ("<b>Last Seen : </b>" + idays + " days " + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
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
				str = ("<b>Last Seen : </b>" + ihours + " hours " + iminutes + " minutes " + iseconds + " seconds");
			}
			else if (itotal > 59 && itotal < 3599)
			{
				iminutes = itotal / 60;
				if (iminutes > 0)
				{itotal = itotal - (iminutes * 60);}
				iseconds = itotal;
				str = ("<b>Last Seen : </b>" + iminutes + " minutes " + iseconds + " seconds");
			}
			else
			{
				iseconds = itotal;
				str = ("<b>Last Seen : </b>" + iseconds + " seconds");
			}
			append(str);
		}
		String sprofile = myUserInfoArray[6];
		int iprofile = Integer.parseInt(sprofile.trim());
		int awayspot = iprofile + 7;
		System.out.println(awayspot);
		String saway = myUserInfoArray[awayspot];
		int iaway = Integer.parseInt(saway.trim());
		String temp = "";
		if(iaway > 0 && (myUserInfoArray[3].contentEquals("online")))
		{
			temp = "<b>Status : Away, </b>";
			for (int i = 2; i < iaway+1; i++)
			{
				temp = temp + myUserInfoArray[i+awayspot] + " ";
			}
			append(temp);
		}
		else if (myUserInfoArray[3].contentEquals("dnd"))
		{
			temp = "<b>Status : Do Not Disturb</b>";
			append(temp);
		}
		else if (myUserInfoArray[3].contentEquals("offline"))
		{
			temp = "<b>Status : Offline</b>";
			append(temp);
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