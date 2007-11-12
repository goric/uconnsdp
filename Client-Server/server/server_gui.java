package server;
import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import javax.swing.ImageIcon;


public class server_gui extends javax.swing.JFrame {
    
   //Constructor
    public server_gui() {
        initComponents();
    }
    
   // Initialize components (the main guts of gui)
    private void initComponents() {
    	
    	// Create all components
        jLabel1 = new javax.swing.JLabel();
        jTextArea2 = new javax.swing.JTextArea();
        jTextArea2.setEditable(false);
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea1.setEditable(false);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jTextArea4.setEditable(false);
        jLabel5 = new javax.swing.JLabel();
        
        // Default title and close operation
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatterIM Server");
        
//////// SERVER UP-TIME ///////////////////////////////////////////        
        // Timer for Up-Time
        int delay = 0000;
        int period = 1000;  // repeat every sec.
        Timer timer = new Timer();
        
        // Server Up-time LAbel
        jLabel1.setText("Server Up-Time:");
        
        // Start Time
        final long stime = System.currentTimeMillis();
        
        // Box Dimensions
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 12));
        jTextArea2.setRows(5);
        jTextArea2.setMinimumSize(new java.awt.Dimension(0, 0));
        
        // Time calculations (updates every second)
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	
            	// Current time - start time in milliseconds
            	long ctime = System.currentTimeMillis();
            	long time = ctime - stime;
            	
            	// Convert milliseconds to seconds
            	long modseconds = time/1000;
            	int  timeinseconds = (int)modseconds;
            	
            	// Convert Seconds to Days + Hours + Minutes + Seconds
            		  int days = timeinseconds/86400;
            		  timeinseconds = timeinseconds - (days * 86400);
            	      int hours = timeinseconds / 3600;
            	      timeinseconds = timeinseconds - (hours * 3600);
            	      int minutes = timeinseconds / 60;
            	      timeinseconds = timeinseconds - (minutes * 60);
            	      int seconds = timeinseconds;
            	      
            	      String day, hour, min, sec;
            	      if(days >= 10)
            	      {
            	    	  day = String.valueOf(days);
            	      }
            	      else
            	      {
            	    	  day = "0" + String.valueOf(days);
            	      }
            	      
            	      if(hours >= 10)
            	      {
            	    	  hour = String.valueOf(hours);
            	      }
            	      else
            	      {
            	    	  hour = "0" + String.valueOf(hours);
            	      }
            	      
            	      if(minutes >= 10)
            	      {
            	    	  min = String.valueOf(minutes);
            	      }
            	      else
            	      {
            	    	  min = "0" + String.valueOf(minutes);
            	      }
            	      
            	      if(seconds >= 10)
            	      {
            	    	  sec = String.valueOf(seconds);
            	      }
            	      else
            	      {
            	    	  sec = "0" + String.valueOf(seconds);
            	      }
                jTextArea2.setText(day + ":" + hour + ":" + min + ":" + sec);
            }
        }, delay, period);
        
////////USERS ONLINE//////////////////////////////////////////////
        jLabel2.setText("Users Online:");
        
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        //jTextArea1.setText("user names go here");
        jScrollPane1.setViewportView(jTextArea1);

        
//ACTIVITY///////////////////////////////////////////
        jLabel3.setText("Activity:");
        
        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        //jTextArea4.setText("server activity goes here");
        jScrollPane2.setViewportView(jTextArea4);

        
 ////LOGO///////////////////////////////////////////////
        ImageIcon icon = new ImageIcon("C:/Documents and Settings/Chris Rindfleisch/Desktop/Senior Design Project/Client-Server/server/logo.jpg" );
        
        jLabel5 = new javax.swing.JLabel();
        jLabel5.setIcon(icon);

     // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(170, 170, 170)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                .addContainerGap())
        );
        pack();
    }
    
    
    public void actout(String t){
    	String tempact = jTextArea4.getText();
    	jTextArea4.setText(tempact + t + "\n");
    }
    
    public void clearusers(){
    	jTextArea1.setText(" ");
    }
    
    public void userlist(String u){
    	String tempuser = jTextArea1.getText();
    	jTextArea1.setText(tempuser + u + "\n");
    }
///// MAIN////////////////////////////////////////////
   /* public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server_gui().setVisible(true);
            }
        });
    }*/
    
    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JLabel jLabel5;
}

