import javax.swing.text.html.HTMLEditorKit;

public class AppendChatWindow {
	public static void appendData(String user, String str, boolean received, ChatWindow window, String time)
	{
		if(user != null) {
			if(received) {
				str ="<FONT COLOR='red' STYLE='font-size:12pt;font-family:Arial'>"+time+" "+user+": </FONT><FONT STYLE='font-size:12pt;font-family:Arial'>"+str;
			} else {
				str ="<FONT COLOR='blue' STYLE='font-size:12pt;font-family:Arial'>"+time+" "+user+": </FONT><FONT STYLE='font-size:12pt;font-family:Arial'>"+str;
			}
		} else {
			str ="<FONT COLOR='red' STYLE='font-size:12pt;font-family:Arial'><B>"+str;
		}

		str+="</FONT>";

		try {
		((HTMLEditorKit)window.recv.getEditorKit()).read(new java.io.StringReader(str),
				window.recv.getDocument(), window.recv.getDocument().getLength());
		window.recv.setCaretPosition(window.recv.getDocument().getLength());
	 	} catch(Exception e){}
	}
	public static void appendData3(String user, String str, boolean received, OneToMany window)
	{
		if(user != null) {
			if(received) {
				str ="<FONT COLOR='blue' STYLE='font-size:12pt;font-family:Arial'>"+user+": </FONT><FONT STYLE='font-size:12pt;font-family:Arial'>"+str;
			} else {
				str ="<FONT COLOR='red' STYLE='font-size:12pt;font-family:Arial'>"+user+": </FONT><FONT STYLE='font-size:12pt;font-family:Arial'>"+str;
			}
		} else {
			str ="<FONT COLOR='red' STYLE='font-size:12pt;font-family:Arial'><B>"+str;
		}

		str+="</FONT>";

		try {
		((HTMLEditorKit)window.recv.getEditorKit()).read(new java.io.StringReader(str),
				window.recv.getDocument(), window.recv.getDocument().getLength());
		window.recv.setCaretPosition(window.recv.getDocument().getLength());
	 	} catch(Exception e){}
	}
	public static void appendData4(String user, String str, OneToMany window)
	{
		if(user != null) {
				str ="<FONT COLOR='black' STYLE='font-size:12pt;font-family:Arial'><b>"+"SystemOP"+" : </b></FONT><b><FONT STYLE='font-size:12pt;font-family:Arial'>"+str;

		}
		str+="</b></FONT>";

		try {
		((HTMLEditorKit)window.recv.getEditorKit()).read(new java.io.StringReader(str),
				window.recv.getDocument(), window.recv.getDocument().getLength());
		window.recv.setCaretPosition(window.recv.getDocument().getLength());
	 	} catch(Exception e){}
	}
}
