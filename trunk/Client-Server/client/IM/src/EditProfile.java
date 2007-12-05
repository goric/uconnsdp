import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.html.*;

public class EditProfile extends JFrame implements ActionListener
{
	private Container container;
	public JEditorPane edit_pane;
	private JButton close, save;

	public EditProfile()
	{
		editProfileFrame();
	}

	public void editProfileFrame()
	{
		container= this.getContentPane();
		container.setLayout(null);
		edit_pane = new JEditorPane();
		edit_pane.setEditorKit(new HTMLEditorKit());
		edit_pane.setEditable(true);
		JScrollPane pane = new JScrollPane(edit_pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBounds(10,10,340,130);
		close = new JButton("Close");
		save = new JButton("Save");
		close.setBounds(70,160,95,30);
		save.setBounds(180,160,95,30);
		close.addActionListener(this);
		save.addActionListener(this);
		close.setActionCommand("close");
		save.setActionCommand("save");
		container.add(pane);
		container.add(close);
		container.add(save);
		close.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							setVisible(false);
						}
					}
		});
		setUserInfo();
		this.setResizable(false);
		this.setSize(360,240);
		this.setTitle(LogIn.username +"'s Info");
		this.setLocation(300,300);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});

		this.setVisible(true);
	}
	
	public void setUserInfo()
	{
		String temp = "";
		String sprofile = Client.userInfoArray[6];
		int iprofile = Integer.parseInt(sprofile.trim());
		for (int i = 0; i < iprofile; i++)
		{
			temp = temp + Client.userInfoArray[i+7] + " ";
		}
		append(temp);
	}

	public void append(String str)
	{
		try {
			((HTMLEditorKit)edit_pane.getEditorKit()).read(new java.io.StringReader(str), edit_pane.getDocument(), edit_pane.getDocument().getLength());
			edit_pane.setCaretPosition(edit_pane.getDocument().getLength());
		 	} catch(Exception e){}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if ("close".contentEquals(e.getActionCommand())) 
		{
			this.setVisible(false);
        }
		else if ("save".contentEquals(e.getActionCommand()))
		{
			String str = edit_pane.getText();
			String temp = "14 " + LogIn.username + " " + str;
			LogIn.thisclient.SendMessage(temp);
			this.setVisible(false);
		}
	}
}