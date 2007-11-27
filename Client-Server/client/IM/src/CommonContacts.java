import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.Timer;
import javax.swing.text.html.*;

public class CommonContacts extends JFrame implements ActionListener
{
	private ClientGUI frame;
	private CommonContacts thisframe;
	private Container container;
	public JEditorPane recv;
	private JButton close;
	private String user;
	private Timer timer=null;
	boolean isFocused = false;

	public CommonContacts(String user)
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

		close = new JButton("Close");
		close.setBounds(105,120,95,50);
		close.addActionListener(this);
		close.setActionCommand("close");

		container.add(pane);
		container.add(close);
		setCommonContacts();

		close.addKeyListener(new KeyAdapter() {
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
		this.setTitle("Contacts in Common with " + user);
		this.setLocation(300,300);

		this.setVisible(true);
	}


	public String toString()
	{
		return user.toString();
	}
	
	public void setCommonContacts()
	{
		String [] copyofCommonArray = Client.commonArray;
		String str = new String();
		String snum = copyofCommonArray[3];
		int inum = Integer.parseInt(snum.trim());
		for (int x = 4; x < inum + 4; x++)
		{
			str = copyofCommonArray[x];
			append(str);
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