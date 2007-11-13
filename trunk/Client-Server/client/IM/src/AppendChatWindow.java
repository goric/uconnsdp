import javax.swing.text.html.HTMLEditorKit;


public class AppendChatWindow {
	
	public static void appendData(String user,String str,boolean received, ChatWindow window)
	{
		if(user != null) {
			if(received) {
				str ="<FONT COLOR='red' STYLE='font-size:10pt;font-family:Arial'>"+user+": </FONT><FONT STYLE='font-size:10pt;font-family:Arial'>"+str;
			} else {
				str ="<FONT COLOR='blue' STYLE='font-size:10pt;font-family:Arial'>"+user+": </FONT><FONT STYLE='font-size:10pt;font-family:Arial'>"+str;
			}
		} else {
			str ="<FONT COLOR='red' STYLE='font-size:10pt;font-family:Arial'><B>"+str;
		}

		str+="</FONT>";

		try {
		((HTMLEditorKit)window.recv.getEditorKit()).read(new java.io.StringReader(str),
				window.recv.getDocument(), window.recv.getDocument().getLength());
		window.recv.setCaretPosition(window.recv.getDocument().getLength());
	 	} catch(Exception e){}
	}
	public static void appendData2(String user,String str,boolean received, ChatWindow window)
	{
		StringBuffer  bfr= new StringBuffer(str);
		if(user != null) {
			if(received) {
				str ="<FONT COLOR='blue' STYLE='font-size:10pt;font-family:Arial'>"+user+": </FONT><FONT STYLE='font-size:10pt;font-family:Arial'>"+str;
			} else {
				str ="<FONT COLOR='red' STYLE='font-size:10pt;font-family:Arial'>"+user+": </FONT><FONT STYLE='font-size:10pt;font-family:Arial'>"+str;
			}
		} else {
			str ="<FONT COLOR='red' STYLE='font-size:10pt;font-family:Arial'><B>"+str;
		}

		str+="</FONT>";//Line break

		try {
		((HTMLEditorKit)window.recv.getEditorKit()).read(new java.io.StringReader(str),
				window.recv.getDocument(), window.recv.getDocument().getLength());
		window.recv.setCaretPosition(window.recv.getDocument().getLength());
	 	} catch(Exception e){}
	}
}
