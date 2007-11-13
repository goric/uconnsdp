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

    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
    
    //Optionally set the look and feel.
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

        //Create a tree that allows one selection at a time.
        treeModel = new DefaultTreeModel(top);
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addMouseListener(new MyMouseAdapter(frame,tree));
        //Listen for when the selection changes.
     //   tree.addTreeSelectionListener(this);

        //Create the scroll pane and add the tree to it. 
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

    /** Required by TreeSelectionListener interface. */
  /*  public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            UserInfo auser = (UserInfo)nodeInfo;
            initilizeChat(auser);
            if (DEBUG) {
                System.out.print(auser.aUserInfo + ":  \n    ");
            }
        } else {
            System.out.println("else");
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }
    }*/

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
    			//	if(user.isOnline != OFFLINE) {
    					frame.createFrame(user,false);
    			//	} else {
    			//		JOptionPane.showMessageDialog(frame,
    			//				"Click on an online user to send a message",
    			//				"Error",JOptionPane.INFORMATION_MESSAGE);
    			//	}
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
		frame.createFrame(message._user,true);
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

	