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
    public ArrayList<String> OnlineList = new ArrayList<String>();
    private JPopupMenu menu;
    private User user2;
    private static int j = 0;
	public static FileTrans f;
	public static int i;

    public OnlineTree(ClientGUI frame) {
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
    	DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Contacts");
    	otoop = top;
        createNodes(top);
        i = 0;
        otreeModel = new DefaultTreeModel(top);
        otree = new JTree(top);
        otree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        otree.addMouseListener(new MyMouseAdapter(frame,otree));
        menu = new JPopupMenu();
        JMenuItem infoitem = new JMenuItem("Get Info");
        menu.add(infoitem);
        JMenuItem msgitem = new JMenuItem("Send Message");
        menu.add(msgitem);
        JMenuItem notesitem = new JMenuItem("Get Notes");
        menu.add(notesitem);
        JMenuItem xferitem = new JMenuItem("Send File");
        menu.add(xferitem);
        JMenuItem invite = new JMenuItem("Invite to Chizzat");
        menu.add(invite);
        JMenuItem common = new JMenuItem("Contacts in Common");
        menu.add(common);
        msgitem.addActionListener(this);
        notesitem.addActionListener(this);
        infoitem.addActionListener(this);
        xferitem.addActionListener(this);
        invite.addActionListener(this);
        common.addActionListener(this);
        notesitem.setActionCommand("notes");
        msgitem.setActionCommand("msg");
		infoitem.setActionCommand("info");
		xferitem.setActionCommand("xfer");
		invite.setActionCommand("invite");
		common.setActionCommand("common");
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
			String temp = ("11 " + LogIn.username + " " + user2);
			LogIn.thisclient.SendMessage(temp);
        }
		else if ("msg".contentEquals(e.getActionCommand()))
		{
			ClientGUI.createFrame(user2);
		}
		else if ("notes".contentEquals(e.getActionCommand()))
		{
			ClientGUI.createNotesFrame(user2);
		}
		else if ("xfer".contentEquals(e.getActionCommand()))
		{
        	JFrame frame = new JFrame();
        	f = new FileTrans(frame);
		}
		else if ("invite".contentEquals(e.getActionCommand()))
		{
			ChatName aNewChat = new ChatName(user2);
		}
		else if ("common".contentEquals(e.getActionCommand()))
		{
			String temp = "15 " + LogIn.username + " " + user2;
			LogIn.thisclient.SendMessage(temp);
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
    

    private void createNodes(DefaultMutableTreeNode top) 
    {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode auser = null;
        oThisBuddyArray = Client.buddyarray;
        String boob = oThisBuddyArray[2];
        int p = Integer.valueOf(boob).intValue();
        p = p + p;
        p = p + 2;
        for (int i = 3; i < p; i++)
        {
        	if ((oThisBuddyArray[i+1].contentEquals("online")) || (oThisBuddyArray[i+1].contentEquals("away")))
        	{
        	String blab = oThisBuddyArray[i];
        	OnlineList.add(blab);
        	i++;
        	}
        	else 
        	{
        		i++;
        	}
        }
        createtehNodes(top, OnlineList);
    }
    public void createtehNodes(DefaultMutableTreeNode top, ArrayList array)
        {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode auser = null;
        	for (int i = 0; i < OnlineList.size(); i++)
        	{
        		auser = new DefaultMutableTreeNode(new User(OnlineList.get(i)));
                top.add(auser);
        	}
        }
    public void createtehNodes2(String[] array)
    {
    DefaultMutableTreeNode category = null;
    DefaultMutableTreeNode auser = null;
    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Contacts");
    j = OnlineList.size();
    j = j+1;
    	for (int i = 0; i < j; i++)
    	{
    	auser = new DefaultMutableTreeNode(new User
            	(OnlineList.get(i)));
            	top.add(auser);
            	i++;
    	}
    }
    }