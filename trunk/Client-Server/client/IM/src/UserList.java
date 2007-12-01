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

public class UserList extends JPanel implements ActionListener {
	
    private JTree tree;
    private static boolean DEBUG = false;
    public static DefaultMutableTreeNode toop;
    private ClientGUI frame;
    private UserList frame2;
    private static DefaultTreeModel treeModel;
    private Hashtable nodeTable = new Hashtable();
    String[] ThisBuddyArray;
    private JPopupMenu menu;
    private static String user2;
    public static JTree stree;

    public UserList() {
        this.frame = frame;
        initAwtContainer();
    }
    public UserList(PopManage frame2)
    {
        initAwtContainer();
    }
    private void initAwtContainer()
    {
    	this.setLayout(new FlowLayout());
        //Create the nodes.
    	DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Contacts");
    	toop = top;
        createNodes(top);

        treeModel = new DefaultTreeModel(top);
        tree = new JTree(top);
    	tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener(new MyMouseAdapter(frame,tree));
        menu = new JPopupMenu();
        JMenuItem getinfo = new JMenuItem("Get Info");
        menu.add(getinfo);
        JMenuItem getnotes = new JMenuItem("Get Notes");
        menu.add(getnotes);
        getnotes.addActionListener(this);
        getinfo.addActionListener(this);
        getnotes.setActionCommand("notes");
		getinfo.setActionCommand("info");
		JScrollPane scrollpane;
		stree = tree;
		scrollpane = new JScrollPane(tree);
		scrollpane.setPreferredSize(new Dimension(160,280));
		this.add(scrollpane);
    }
    
   /* public static void refreshIt(String user)
    {
        DefaultMutableTreeNode tehuser = null;
    	tehuser = new DefaultMutableTreeNode(new User
            	(user));
           	toop.add(tehuser);
    	((DefaultTreeModel)tree.getModel()).reload();
    	treeModel.reload(tehuser);
    	ClientGUI.RefreshGUI(tree);
    }*/
	public void removeUser(String user)
	{

	}
	
	public void actionPerformed(java.awt.event.ActionEvent e) 
	{
		if ("info".contentEquals(e.getActionCommand())) 
		{	
			String temp = ("28 " + LogIn.username + " " + user2);
			System.out.println(temp);
			LogIn.thisclient.SendMessage(temp);
        }
		else if ("notes".contentEquals(e.getActionCommand()))
		{
			ClientGUI.createNotesFrame(user2);
		}
        
    }
	public void showit(Component com, int x, int y, String user1)
	{
		user2 = user1;
		menu.show(tree,x,y);
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
    				String user = (String)(node.getUserObject());
    				showit(tree, e.getX(), e.getY(), user);
        		}
    			if(e.getClickCount() == 2) 
    			{
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				String user2 = (String)(node.getUserObject());
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
        ThisBuddyArray = Client.buddyarray;
        String boob = ThisBuddyArray[2];
        int p = Integer.valueOf(boob).intValue();
        p = p + p;
        p = p + 3;
        for (int i = 3; i < p; i++)
        {
        	auser = new DefaultMutableTreeNode(new String
        	(ThisBuddyArray[i]));
        	top.add(auser);
        	i++;
        }
    }
}

	