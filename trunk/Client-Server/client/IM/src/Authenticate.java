import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.EventObject;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.*;

public class Authenticate
{
	public static String username, password, hashpass;
	private JTextField userfield;
	private JPasswordField passfield;
	private JButton logbutton, qbutton;
	private JLabel userlab, passlab;
	private Container container;
	
	public Authenticate (Client aclient)
	{
	}
	
	public static void authenticatelogin()
	{
		Client thisclient = new Client();
		hashpass = "8cb2237d0679ca88db6464eac60da96345513964";
		System.out.println(hashpass);
		thisclient.GetServerConnection( "137.99.129.59");
		String ipsend = Client.ip;
		String temp = "01 " + username + " " + hashpass + " " + ipsend;
		thisclient.SendMessage(temp);
		temp = "02 " + username;
		thisclient.SendMessage(temp);
	}
	}
