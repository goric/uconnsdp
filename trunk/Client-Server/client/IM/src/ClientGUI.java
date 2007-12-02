import java.awt.*;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.tree.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.event.*;
import java.util.*;


public class ClientGUI extends JFrame implements ActionListener {
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private JMenuBar menu;
	private JMenu menu1;
	private JMenuItem quitmenu;
	private Container container;
	private static Container container2;
	private String username,password;
	private JButton optbutton, manbutton;
	private JLabel logo;
	public static boolean rflag = true, pflag = true;
	public static Hashtable frameTable = new Hashtable();
	public static Hashtable buddyTable = new Hashtable();
    public static int i = 0;
    private static int chat_counter = 0;
    private Image image;
	
	public ClientGUI(Point x)
	{
		MainWin(x);
	}
	public void MainWin(Point x)
	{
		container = this.getContentPane();
		SpringLayout layout = new SpringLayout();
		container.setLayout(layout);
		menu = new JMenuBar();
		menu1 = new JMenu("File");
		quitmenu = new JMenuItem("Quit");
		menu1.add(quitmenu);
		menu.add(menu1);
		optbutton = new JButton("Options");
		manbutton = new JButton("Manage");
		optbutton.setActionCommand("opt");
		manbutton.setActionCommand("man");
		quitmenu.setActionCommand("quit");
        layout.putConstraint(SpringLayout.NORTH, optbutton,
				 290,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, optbutton,
				 120,
				 SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, manbutton,
				 290,
				 SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, manbutton,
				 20,
				 SpringLayout.WEST, container);
		container.add(optbutton);
		container.add(manbutton);
		while(rflag == true){
		
		if ((Client.anythingMessage2[0].contentEquals("02")) && (pflag == true))
		{
			String instancename = LogIn.username;
			OnlineTree contacts;
    		contacts = (OnlineTree) buddyTable.get(instancename);
    		if(contacts == null)
    		{
    			contacts = new OnlineTree(this);
    			
    		buddyTable.put(instancename,contacts);
			container.add(contacts);
			pflag = false;
			rflag = false;
    		}
    		rflag = false;
		}
		}
		optbutton.addActionListener(this);
		manbutton.addActionListener(this);
		quitmenu.addActionListener(this);
		this.setJMenuBar(menu);
		this.setSize(225, 400);
		this.setLocation(x);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("/dance " + LogIn.username + " dance");
		}
	
    protected void quit() {
    	System.out.println("Program Terminated by User");
        System.exit(0);
    }
    
    public static void createFrame(String user)
    {
		ChatWindow dialog ;
		synchronized(frameTable) {
				dialog = (ChatWindow) frameTable.get(user);
				if(dialog == null) {
					dialog = new ChatWindow(user, 25*chat_counter, 25*chat_counter);
					//dialog.setLocation(500, 500);
					frameTable.put(user,dialog);
			    	chat_counter++;
				}
			}
		}
    
    public static void createInfoFrame(String user)
	{
		UserInfo dialog ;
		dialog = new UserInfo(user);
		dialog.setLocation(500, 400);
	}

    public static void createNotesFrame(String user)
	{
		UserNotes dialog ;
		dialog = new UserNotes(user);
		dialog.setLocation(600, 500);
	}

    public static void removeFrame(String user)
    {
    	synchronized(frameTable) 
    	{
    		frameTable.remove(user.toString());
    	}
    }
    
	public void actionPerformed(ActionEvent e)
	{
		if ("opt".contentEquals(e.getActionCommand())) 
		{
			popOptions();
        } 
        else if ("man".contentEquals(e.getActionCommand()))
        {
        	popManage();
        }
		else if ("quit".contentEquals(e.getActionCommand()))
		{
			quit();
		}
		else
		{
			System.out.println("Program Terminated by User");
			quit();
		}
	}
	
    public void popOptions()
    {
    	PopOptions dialog = new PopOptions();
    }
    
    public void popManage()
    {
    	Point manageDefaultLoc = new Point(400,330);
    	PopManage dialog = new PopManage(manageDefaultLoc);
    }
}