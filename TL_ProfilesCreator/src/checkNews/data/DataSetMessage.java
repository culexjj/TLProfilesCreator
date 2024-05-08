package checkNews.data;

import java.util.ArrayList;


/**
 * two types of dataset may be generated
 * type A(USER): show all Telegram messages send by a telegram user
 * 		header - data from the user
 * 		body -a list of messages
 * 		example:
 * 			
 *				USER INFORMATION: 
 *				SENDER ID | FIRSTNAME | LASTNAME | USERNAME | FAKE | SCAM | VERIFIED | PREMIUM
 *				1967173530 | Juan Carlos | Macarro |  | false | false | false | false
 *				
 *				USER MESSAGES: 
 *				ID: DATE | MESSAGE ID | SENDER ID | CHAT ID | MENSAJE
 *				1: 29/01/2024 09:14:23 | 6320816128 | 1967173530 | -1001248984999 | Hoy lunes bailamos swing en la concha de Santiago que está muy cerca del templete del retiro de 19 a 21.30 horas será el último bailé antes de que me operen de la rodilla pero en cuanto pueda andar iré a poner música aunque  no pueda bailar muchas gracias por vuestro apoyo
 *				2: 27/01/2024 16:48:16 | 6316621824 | 1967173530 | -1001248984999 | Mañana domingo debido al buen tiempo y para no coincidir con el pasea swing hemos decidido cambiar el horario mañana domingo  28 de Enero bailamos swing en la concha de Santiago que está muy cerca del templete del retiro de 17.30 a 21.30 y el lunes  también bailamos en la concha de Santiago pero de 19. A 21.30 horas la concha tiene un suelo muy bueno buena acústica y también tiene 5 bancos para sentarse en un sitio muy bonito y tranquilo
 *				3: 27/01/2024 11:50:00 | 6315573248 | 1967173530 | -1001248984999 | Ya estoy Poniendo música os recordamos que hoy  sábado y mañana también Domingo bailamos  swing  en la concha de Santiago hasta  las 15 horas hace un día muy soleado y no hace nada de frío está ideal para bailar Ya la tengo bien barrida   os  recordamos que la concha de Santiago tiene un suelo muy bueno de baldosa lisa que desliza muy bien y tiene 5 bancos para sentarse en un entorno muy bonito y tranquilo rodeado de árboles
 *
 * type B (MESSAGE): show all Telegram users who have sent sent the same message
 * 		header - the message (or part of it)
 * 		body - a list of users
 * 		example:
 * 			
 *				MESSAGE INFORMATION:
 *				https://www.madforswing.es/evento/pasea-swing-11/
 *
 *				LIST OF USERS: 
 *				SENDER ID | FIRSTNAME | LASTNAME | USERNAME | FAKE | SCAM | VERIFIED | PREMIUM
 *				1: 12802065 | Marta | GQ | Optimita | false | false | false | false
 *				2: 226996303 | Manu |  | jurujuju | false | false | false | false
 *
 * this class describe a DataSet from type B (MESSAGE)
 * 
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public class DataSetMessage {
	
	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	private String message; //header of the dataset.
	private ArrayList<UserTelegram> listOfUsers; //body of the dataset
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * DataSetMessage builder
	 */
	public DataSetMessage() {
		
	}
	
	
	/**
	 * DataSetMessage builder
	 * @param message
	 * @param listOfUsers
	 */
	public DataSetMessage (String message,  ArrayList<UserTelegram> listOfUsers ) {
		
		this.message = message;
		this.listOfUsers = listOfUsers;
	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * 
	 * @return message
	 */
	public String getMessage() {
		
		return message;
	}

	
	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		
		this.message = message;
	}
	
	
	/**
	 * 
	 * @return listOfUsers
	 */
	public ArrayList<UserTelegram> getListOfUsers() {
		
		return listOfUsers;
	}

	
	/**
	 * 
	 * @param listOfUsers
	 */
	public void setListOfUsers(ArrayList<UserTelegram> listOfUsers) {
		
		this.listOfUsers = listOfUsers;
	}

}
