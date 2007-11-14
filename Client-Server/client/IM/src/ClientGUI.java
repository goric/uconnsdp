import java.awt.*;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.event.*;
import java.util.*;


public class ClientGUI extends JFrame implements ActionListener {
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private JButton login;
	private JTextField name, send;
	private JMenuBar menu;
	private JMenu menu1;
	private JMenuItem quitmenu;
	private Container container;
	private static Container container2;
	private String username,password;
	private JButton optbutton, manbutton;
	public static boolean rflag = true, pflag = true;
	public static Hashtable frameTable = new Hashtable();
    private static DefaultMutableTreeNode poop;
	
	public ClientGUI()
	{
		MainWin();
	}
	public void MainWin()
	{
		rflag = true;
		pflag = true;
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
			OnlineTree contacts = new OnlineTree(this);
			container.add(contacts);
			pflag = false;
			rflag = false;
		}
		}
		
		optbutton.addActionListener(this);
		manbutton.addActionListener(this);
		quitmenu.addActionListener(this);
		container2 = container;
		container2 = this;
		this.setJMenuBar(menu);
		this.setSize(225, 400);
		this.setLocation(400,100);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("/dance");
	}
	
	public static void giveitawhirl()
	{
		container2.setVisible(false);
		ClientGUI gui = new ClientGUI();
	}

	public static void giveitawhirl2()
	{
		container2.setVisible(false);
	}
	
    public static void RefreshGUI(JTree tree)
    {
  	((DefaultTreeModel)tree.getModel()).reload();
    }
    protected void quit() {
    	System.out.println("Program Terminated by User");
        System.exit(0);
    }
    
    public static void createFrame(User user)
	{
		ChatWindow dialog ;
		synchronized(frameTable) {
				dialog = (ChatWindow) frameTable.get(user.toString());
				if(dialog == null) {
					dialog = new ChatWindow(user);
					dialog.setLocation(500, 500);
					frameTable.put(user.toString(),dialog);
					//getDispatcher().addObserver(dialog);
				}
			}
		}
    
    public static void createInfoFrame(User user)
	{
		UserInfo dialog ;
		dialog = new UserInfo(user);
		dialog.setLocation(500, 500);
	}

    public static void createNotesFrame(User user)
	{
		UserNotes dialog ;
		dialog = new UserNotes(user);
		dialog.setLocation(600, 500);
	}
		
	

    public static void removeFrame(User user)
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
			System.out.println("Options Screen Brought Up");	
			popOptions();
        } 
        else if ("man".contentEquals(e.getActionCommand()))
        {
        	System.out.println("Manage Screen Brought Up");
        	popManage();
        }
		else if ("quit".contentEquals(e.getActionCommand()))
		{
			quit();
		}
		else
		{
			System.out.println("I Got Bad Input in ClientGUI");
		}
	}
	public void authenticate()
	{
		showLogIn();
	}
	
    public void popOptions()
    {
    	PopOptions dialog = new PopOptions(this);
    }
    
    public void popManage()
    {
    	PopManage dialog = new PopManage(this);
    }
    
    private void showLogIn()
    {
	//	LogIn dialog = new LogIn(this);
	//	username = dialog.getUsername();
	//	password = dialog.getPassword();
	}
}