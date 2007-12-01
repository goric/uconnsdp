import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class HyperLinkLabel extends JLabel{
String url;
Color rc;
 
public HyperLinkLabel(String text, Icon icon, String url){
super(text);
this.setIcon(icon);
this.url=url;
addListeners();
}
private void addListeners(){
final String thisUrl=url;
this.addMouseListener(new MouseAdapter(){
public void mouseClicked(MouseEvent e){
HyperLinker.displayURL(thisUrl);
}
public void mouseEntered(MouseEvent e){
HyperLinkLabel.this.rc=HyperLinkLabel.this.getForeground();
HyperLinkLabel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
HyperLinkLabel.this.setForeground(Color.blue);
}
public void mouseExited(MouseEvent e){
HyperLinkLabel.this.setForeground(HyperLinkLabel.this.rc);
HyperLinkLabel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}
 
});
}
 
/*public static void main(String[] args){
JFrame f=new JFrame("HyperLinkLabel Tester");
f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
Container c=f.getContentPane();
JLabel lb=new HyperLinkLabel("click me", null,
"http://goodmorningsky.com.ne.kr");
c.add(lb);
f.pack();
f.show();
}// end of main method*/
 
}// end of class