import java.awt.Container;
import java.io.*;
import javax.swing.*;


public class ProgMonitor extends JFrame{

	private JLabel progress, bit_rate;
	private JLabel prog, avg_bits;
	private JProgressBar bar = new JProgressBar();
	private Container container;
	
	public int filesize, previous=0;
	
	
	public ProgMonitor ()
	{
		super ("File Transfer Progress");
		ProgFrame();
	}
	
	
	
	private void ProgFrame()
	{
		SpringLayout layout = new SpringLayout();
		container = this.getContentPane();
		container.setLayout(layout);
		
		progress = new JLabel("Transfer Progess: " );
		prog = new JLabel("prog " );
		
		bit_rate = new JLabel("Avg. Bits Transfered: ");
		avg_bits = new JLabel("zxc");
		
		bar = new JProgressBar();
		
        layout.putConstraint(SpringLayout.NORTH, progress,
				 5,
				 SpringLayout.NORTH, container);
       layout.putConstraint(SpringLayout.WEST, progress,
				 5,
				 SpringLayout.WEST, container);
       
       layout.putConstraint(SpringLayout.NORTH, prog,
  			 5,
  			 SpringLayout.NORTH, container);
     layout.putConstraint(SpringLayout.WEST, prog,
  			 120,
  			 SpringLayout.WEST, container);
       
       layout.putConstraint(SpringLayout.NORTH, bit_rate,
				 40,
				 SpringLayout.NORTH, container);
     layout.putConstraint(SpringLayout.WEST, bit_rate,
				 5,
				 SpringLayout.WEST, container);
  
   layout.putConstraint(SpringLayout.NORTH, avg_bits,
			 40,
			 SpringLayout.NORTH, container);
 layout.putConstraint(SpringLayout.WEST, avg_bits,
			 130,
			 SpringLayout.WEST, container);

 layout.putConstraint(SpringLayout.NORTH, bar,
		 70,
		 SpringLayout.NORTH, container);
layout.putConstraint(SpringLayout.WEST, bar,
		 5,
		 SpringLayout.WEST, container);
 
 
	container.add(bit_rate);
	container.add(progress);
	container.add(prog);
	container.add(avg_bits);
	container.add(bar);	
	
	bar.setValue(0);
	
	bar.setStringPainted(true);

	this.setSize(235,130);
	this.setResizable(false);
	this.setLocation(400,200);
	this.setVisible(true);
	
	
	}
	
	public void setfilesize(int file){
		filesize = file;
		bar.setMinimum(0);
		bar.setMaximum(filesize);
	}
	
	public void setprog(int pro, int bytesR){
		previous++;
		System.out.println("set r");
		prog.setText("" + pro + " / " + filesize);
		bar.setValue(Math.min(pro, filesize));
		
		avg_bits.setText(""+ ((bytesR)/1000)+ " kb/s");
				
		try {Thread.sleep(500);}
		catch (InterruptedException ek){};
		
		
	}
	public void setprog(int pro){
		previous++;
		System.out.println("set prog bar");
		prog.setText("" + pro + " / " + filesize);
		bar.setValue(Math.min(pro, filesize));
						
		try {Thread.sleep(500);}
		catch (InterruptedException ek){};
		
		
	}
	
	
	
	
/*	public static void main(String args[]){
			JFrame f = new JFrame();
			ProgMonitor ft = new ProgMonitor(f);
			ft.setVisible(true);
			
		}
	*/
}
