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

public class UserList extends JPanel {
	
    private static JTree tree;
    private static boolean DEBUG = false;
    public static DefaultMutableTreeNode toop;
    private ClientGUI frame;
    private UserList frame2;
    private static DefaultTreeModel treeModel;
    private Hashtable nodeTable = new Hashtable();
    String[] ThisBuddyArray;

    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
    
    private static boolean useSystemLookAndFeel = false;

    public UserList(ClientGUI frame) {
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
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addMouseListener(new MyMouseAdapter(frame,tree));

		JScrollPane scrollpane;
		scrollpane = new JScrollPane(tree);
		scrollpane.setPreferredSize(new Dimension(200,100));
		this.add(scrollpane);
    }
    
    public static void refreshIt(String user)
    {
        DefaultMutableTreeNode tehuser = null;
    	tehuser = new DefaultMutableTreeNode(new User
            	(user));
           	toop.add(tehuser);
    	System.out.println("i got to refresh");
    	((DefaultTreeModel)tree.getModel()).reload();
    	treeModel.reload(tehuser);
    	ClientGUI.RefreshGUI(tree);
    }
	public void removeUser(String user)
	{

	}
    public static void refreshit2(JTree tehtree)
    {
    	((DefaultTreeModel)tree.getModel()).reload();
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
    			if(e.getClickCount() == 2) {
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				User user = (User)(node.getUserObject());
    				frame.createFrame(user);
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
		frame.createFrame(message._user);
	}
}
    

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode auser = null;
        ThisBuddyArray = Client.buddyarray;
        String boob = ThisBuddyArray[2];
        int p = Integer.valueOf(boob).intValue();
        p = p + 3;
        for (int i = 3; i < p; i++)
        {
        	auser = new DefaultMutableTreeNode(new User
        	(ThisBuddyArray[i]));
        	top.add(auser);
        }
    }
}

	