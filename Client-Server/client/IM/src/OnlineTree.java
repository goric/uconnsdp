import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.*;
import javax.swing.JTree;
import javax.swing.tree.*;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class OnlineTree extends JPanel implements ActionListener {
	
    private JTree otree;
    public static DefaultMutableTreeNode otoop;
    private ClientGUI frame;
    private DefaultTreeModel otreeModel;
    private String[] oThisBuddyArray;
    public ArrayList<String> OnlineList = new ArrayList<String>();
    private JPopupMenu menu;
    private String user2;
    private static int j = 0;
	public static FileTrans f;
	public static int i;

    public OnlineTree(ClientGUI frame) {
        this.frame = frame;
        OnlineTreeFrame();
    }
    private void OnlineTreeFrame()
    {
    	this.setLayout(new FlowLayout());
    	DefaultMutableTreeNode top = new DefaultMutableTreeNode("Contacts");
    	otoop = top;
        createNodes(top);
        i = 0;
        otreeModel = new DefaultTreeModel(top);
        otree = new JTree(top);
        otree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        otree.addMouseListener(new MyMouseAdapter(frame,otree));
        ImageIcon onlineicon = createImageIcon("images/away.gif");
        if (onlineicon != null) {
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(onlineicon);
            otree.setCellRenderer(renderer);
        } else {
            System.err.println("Leaf icon missing; using default.");
        }
        menu = new JPopupMenu();
        JMenuItem infoitem = new JMenuItem("Get Info");
        menu.add(infoitem);
        JMenuItem msgitem = new JMenuItem("Send Message");
        menu.add(msgitem);
        JMenuItem notesitem = new JMenuItem("Get Notes");
        menu.add(notesitem);
        JMenuItem xferitem = new JMenuItem("Send File");
        menu.add(xferitem);
        JMenuItem invite = new JMenuItem("Invite to Chat");
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
		scrollpane.setPreferredSize(new Dimension(200,250));
		this.add(scrollpane);
    }

    public void addUser(String user)
    {
        DefaultMutableTreeNode tehuser = null;
    	tehuser = new DefaultMutableTreeNode(new String(user));
           	otoop.add(tehuser);
    	((DefaultTreeModel)otree.getModel()).reload();
    	otreeModel.reload(tehuser);
    }
    
	public void removeUser(String user)
	{
	    int startRow = 0;
	    String prefix = user;
	    TreePath path = otree.getNextMatch(prefix, startRow, Position.Bias.Forward);
	    MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
	    otreeModel.removeNodeFromParent(node);
    	((DefaultTreeModel)otree.getModel()).reload();
	}
	
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = OnlineTree.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
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
    
	public void showit(Component com, int x, int y, String user1)
	{
		user2 = user1;
		menu.show(otree,x,y);
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
    			if(e.getClickCount() == 2) 
    			{
    				node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    				String user2 = (String)(node.getUserObject());
    				ClientGUI.createFrame(user2);
    			}
    		}
    	}
    }

    private void createNodes(DefaultMutableTreeNode top) 
    {
        oThisBuddyArray = Client.buddyarray;
        String boob = oThisBuddyArray[2];
        int p = Integer.valueOf(boob).intValue();
        p = p + p;
        p = p + 2;
        for (int i = 3; i < p; i++)
        {
        	UserList.all_contacts.add(oThisBuddyArray[i]);
        	if (oThisBuddyArray[i+1].contentEquals("online"))
        	{
        	String blab = oThisBuddyArray[i];
        	OnlineList.add(blab);
        	i++;
        	}
        	else if(oThisBuddyArray[i+1].contentEquals("away"))
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
    public void createtehNodes(DefaultMutableTreeNode top, ArrayList<String> array)
        {
        DefaultMutableTreeNode auser = null;
        	for (int i = 0; i < OnlineList.size(); i++)
        	{
        		auser = new DefaultMutableTreeNode(new String(OnlineList.get(i)));
                top.add(auser);
        	}
        }
    public void createtehNodes2(String[] array)
    {
    DefaultMutableTreeNode auser = null;
    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Contacts");
    j = OnlineList.size();
    j = j+1;
    	for (int i = 0; i < j; i++)
    	{
    	auser = new DefaultMutableTreeNode(new String
            	(OnlineList.get(i)));
            	top.add(auser);
            	i++;
    	}
    }
}