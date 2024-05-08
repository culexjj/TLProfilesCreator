package checkNews.support.gui;



import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 * This class forward the stdout to a text area
 * @author Jose Javier Culebras
 * @version 1.0 
 */
public class SetOutputStream extends OutputStream {
	
	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	private JTextArea textArea;
    
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * 
	 * @param textArea
	 */
	 public SetOutputStream(JTextArea textArea) {
		 this.textArea = textArea;
	 }
	 
	 /**
	  * 
	  */
	 @Override
	 public void write(int b) throws IOException {
	        
	     textArea.append(String.valueOf((char)b)); // redirects data to the text area
	     textArea.setCaretPosition(textArea.getDocument().getLength());  // scrolls the text area to the end of data
	 }

}
