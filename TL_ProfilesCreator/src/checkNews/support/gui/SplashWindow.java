package checkNews.support.gui;



import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Class for showing initial screen
 * @author Jose Javier Culebras
 * @version 1.0 
 */
public class SplashWindow {
	
	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	JFrame frame;
	JLabel image=new JLabel(new ImageIcon("image\\image.png"));
	JLabel text=new JLabel("TL-ProfilesCreator");
	JProgressBar progressBar=new JProgressBar();
	
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	public SplashWindow() {
		
		frame=new JFrame();
	    frame.getContentPane().setLayout(null);
	    frame.setUndecorated(true);
	    frame.setSize(600,400);
	    frame.setLocationRelativeTo(null);
	    frame.getContentPane().setBackground(Color.LIGHT_GRAY);
	    frame.setVisible(true);

	    image.setSize(600,200);
	    frame.add(image);

	    text.setFont(new Font("Monospaced",Font.BOLD,37));
	    text.setBounds(100,220,600,40);
	    text.setForeground(Color.BLUE);
	    frame.add(text);
	     	      
	    progressBar.setBounds(100,280,400,30);
	    progressBar.setBorderPainted(true);
	    progressBar.setStringPainted(true);
	    progressBar.setBackground(Color.WHITE);
	    progressBar.setForeground(Color.BLACK);
	    progressBar.setValue(0);
	    frame.add(progressBar);
	      
	    showProgressBar();
	  }
	  
	  
	
	/*--------------------------*/
	/*    PRIVATE METHODS       */
	/*--------------------------*/
	
	/**
	 * Methog for showing the % of the Progress Bar
	 */
	private void showProgressBar(){
	    	
	    int i=0;

	    while( i<=100) {
	        	
	        try {
	            	
	            Thread.sleep(20);
	            progressBar.setValue(i);
	            i++;
	            if(i==100) {
	                	
	            	frame.dispose();
	            }
	                    
	        }catch(Exception e){
	               
	        }
	    }
	}
	  
}
