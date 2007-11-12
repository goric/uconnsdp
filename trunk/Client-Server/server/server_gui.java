package server;
import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.*;


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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jTextArea5 = new javax.swing.JTextArea();

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
            	      day = String.valueOf(days);
            	      hour = String.valueOf(hours);
            	      min = String.valueOf(minutes);
            	      sec = String.valueOf(seconds);
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
        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jTextArea5.setText("ChatterIM Logo\nGoes Here");

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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 424, Short.MAX_VALUE)
                        .addComponent(jTextArea5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextArea5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
                .addContainerGap())
        );
        pack();
    }
    
    
    public void actout(String t){
    	String tempact = jTextArea4.getText();
    	jTextArea4.setText(tempact + "\n" + t);
    }
    
    public void clearusers(){
    	jTextArea1.setText(" ");
    }
    
    public void userlist(String u){
    	String tempuser = jTextArea1.getText();
    	jTextArea1.setText(tempuser + "\n" + u);
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
    private javax.swing.JTextArea jTextArea5;
}
