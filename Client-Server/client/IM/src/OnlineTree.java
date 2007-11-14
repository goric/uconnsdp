import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JInternalFrame;


import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.tree.*;

public class OnlineTree extends JPanel implements ActionListener {
	
    private static JTree otree;
    private static boolean DEBUG = false;
    public static DefaultMutableTreeNode otoop;
    private ClientGUI frame;
    private UserList frame2;
    private static DefaultTreeModel otreeModel;
    private Hashtable nodeTable = new Hashtable();
    String[] oThisBuddyArray;
    public static String[] o2ThisBuddyArray;
    private JPopupMenu menu;
    private User user2;
    private static int j = 0;
    private static String[] o3BuddyArray;

    public OnlineTree(ClientGUI frame) {
    	String okfine = Client.buddyarray[2];
    	int okfineint = Integer.valueOf(okfine).intValue();
    	o2ThisBuddyArray = new String[okfineint];
        this.frame = frame;
        initAwtContainer();
    }
    public OnlineTree(PopManage frame2)
    {
        initAwtContainer();
    }
    private void initAwtContainer()
    {
    	this.setLayout(new FlowLayout());
        //Create the nodes.
    	DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Contacts");
    	otoop = top;
        createNodes(top);

        otreeModel = new DefaultTreeModel(top);
        otree = new JTree(top);
        otree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        otree.addMouseListener(new MyMouseAdapter(frame,otree));
        menu = new JPopupMenu();
        JMenuItem whatever = new JMenuItem("Get Info");
        menu.add(whatever);
        JMenuItem whatever2 = new JMenuItem("Send Message");
        menu.add(whatever2);
        JMenuItem whatever3 = new JMenuItem("Get Notes");
        menu.add(whatever3);
        whatever2.addActionListener(this);
        whatever3.addActionListener(this);
        whatever.addActionListener(this);
        whatever3.setActionCommand("notes");
        whatever2.setActionCommand("msg");
		whatever.setActionCommand("info");
		JScrollPane scrollpane;
		scrollpane = new JScrollPane(otree);
		scrollpane.setPreferredSize(new Dimension(200,100));
		this.add(scrollpane);
    }
    
    public static void refreshIt(String user)
    {
        DefaultMutableTreeNode tehuser = null;
    	tehuser = new DefaultMutableTreeNode(new User
            	(user));
           	otoop.add(tehuser);
    	((DefaultTreeModel)otree.getModel()).reload();
    	otreeModel.reload(tehuser);
    	ClientGUI.RefreshGUI(otree);
    }
	public void removeUser(String user)
	{

	}
	
	public void actionPerformed(java.awt.event.ActionEvent e) 
	{
		if ("info".contentEquals(e.getActionCommand())) 
		{
			System.out.println("Getting User Info");	
			ClientGUI.createInfoFrame(user2);
        }
		else if ("msg".contentEquals(e.getActionCommand()))
		{
			ClientGUI.createFrame(user2);
		}
		else if ("notes".contentEquals(e.getActionCommand()))
		{
			ClientGUI.createNotesFrame(user2);
		}
        
    }
	public void showit(Component com, int x, int y, User user1)
	{
		user2 = user1;
		menu.show(otree,x,y);
	}

    public static void refreshit2(JTree tehtree)
    {
    	((DefaultTreeModel)otree.getModel()).reload();
    }

class MyMouseAdapter extends MouseAdapter
    {
    	private ClientGUI frame;
    	private JTree tree;

    	public MyMouseAdapter(ClientGUI frame,JTree tree)
    	{
    		this.frame = frame;
    		this.tree = tree;
    	}
        
    	public void mouseClicked(MouseEvent e) {
    		int selRow = tree.getRowForLocation(e.getX(), e.getY());
    		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
    		ChatWindow dialog;
    		DefaultMutableTreeNode node;
    		if(selRow > 0 ) {
        		if (e.getButton() == 3) 
        		{
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				User user = (User)(node.getUserObject());
    				showit(tree, e.getX(), e.getY(), user);
        		}
    			if(e.getClickCount() == 2) 
    			{
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				User user2 = (User)(node.getUserObject());
    				ClientGUI.createFrame(user2);
    			}
    		}
    	}
    }
class DefaultObserver implements Observer
{

	private ClientGUI frame;
	private ChatWindow dialog;

	public DefaultObserver(ClientGUI frame)
	{
		this.frame=frame;
	}

	public void update(Observable observable,Object object)
	{
		Message message = (Message)object;
		ClientGUI.createFrame(message._user);
	}
}
    

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode auser = null;
        oThisBuddyArray = Client.buddyarray;
        String boob = oThisBuddyArray[2];
        int p = Integer.valueOf(boob).intValue();
        p = p + p;
        p = p + 2;
        int f = 0;
        for (int i = 3; i < p; i++)
        {
        	if (oThisBuddyArray[i+1].contentEquals("online"))
        	{
        	System.out.println("i added one to the array");
        	String blab = oThisBuddyArray[i];
        	o2ThisBuddyArray[f] = blab;
        	i++;
        	f++;
        	}
        	else 
        	{
        		i++;
        	}
        }
        createtehNodes(top, o2ThisBuddyArray);
    }
    public static void createtehNodes(DefaultMutableTreeNode top, String[] array)
        {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode auser = null;
        o3BuddyArray = o2ThisBuddyArray;
        	for (int i = 0; i < o3BuddyArray.length; i++)
        	{
        		/*if (o2ThisBuddyArray[i].isEmpty())
        		{
        			System.out.println("i didnt put it in");
        		}
        		else*/
        		System.out.println("length " + o2ThisBuddyArray.length);
        		System.out.println(i);
        		auser = new DefaultMutableTreeNode(new User(o2ThisBuddyArray[i]));
                top.add(auser);
                System.out.println("i went through");
        	}
        }
    public static void createtehNodes2(String[] array)
    {
    DefaultMutableTreeNode category = null;
    DefaultMutableTreeNode auser = null;
    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Contacts");
    j = o2ThisBuddyArray.length;
    j = j+1;
    	for (int i = 0; i < j; i++)
    	{
    	auser = new DefaultMutableTreeNode(new User
            	(o2ThisBuddyArray[i]));
            	top.add(auser);
            	i++;
    	}
    }
    }