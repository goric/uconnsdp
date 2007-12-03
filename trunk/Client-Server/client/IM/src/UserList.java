import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class UserList extends JPanel implements ActionListener {
	
    private JTree tree;
    public static DefaultMutableTreeNode toop;
    private ClientGUI frame;
    private static DefaultTreeModel treeModel;
    private String[] ThisBuddyArray;
    private JPopupMenu menu;
    public static String user2;
    public static JTree stree;
    public static ArrayList<String> all_contacts = new ArrayList<String>();

    public UserList(PopManage frame2)
    {
        UserListFrame();
    }
    private void UserListFrame()
    {
    	this.setLayout(new FlowLayout());
    	DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Contacts");
    	toop = top;
        createNodes(top);
        treeModel = new DefaultTreeModel(top);
        tree = new JTree(top);
    	tree.setEditable(false);
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
    
    public void addUser(String user)
    {
        DefaultMutableTreeNode tehuser = null;
    	tehuser = new DefaultMutableTreeNode(new String(user));
           	toop.add(tehuser);
    	((DefaultTreeModel)tree.getModel()).reload();
    	treeModel.reload(tehuser);
        all_contacts.add(user);
    }
    
	public void removeUser(String user)
	{
	    int startRow = 0;
	    String prefix = user;
	    TreePath path = tree.getNextMatch(prefix, startRow, Position.Bias.Forward);
	    MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
	    treeModel.removeNodeFromParent(node);
    	((DefaultTreeModel)tree.getModel()).reload();
	}
	
	public void actionPerformed(java.awt.event.ActionEvent e) 
	{
		if ("info".contentEquals(e.getActionCommand())) 
		{	
			String temp = ("11 " + LogIn.username + " " + user2);
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
    	private JTree tree;

    	public MyMouseAdapter(ClientGUI frame,JTree tree)
    	{
    		this.tree = tree;
    	}
        
    	public void mouseClicked(MouseEvent e) {
    		int selRow = tree.getRowForLocation(e.getX(), e.getY());
    		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
    		DefaultMutableTreeNode node;
    		if(selRow > 0 ) {
        		if (e.getButton() == 3) 
        		{
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				String user = (String)(node.getUserObject());
    				showit(tree, e.getX(), e.getY(), user);
    				
        		}
    			if(e.getClickCount() >= 1)
    			{
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				String user = (String)(node.getUserObject());
    				user2 = user;
    			}
    		}
    	}
    }

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode auser = null;
        ThisBuddyArray = Client.buddyarray;
        String boob = ThisBuddyArray[2];
        int p = Integer.valueOf(boob).intValue();
        p = p + p;
        p = p + 3;
        for (int i = 3; i < p; i++)
        {
        	all_contacts.add(ThisBuddyArray[i]);
        	auser = new DefaultMutableTreeNode(new String
        	(ThisBuddyArray[i]));
        	top.add(auser);
        	i++;
        }
    }
}

	