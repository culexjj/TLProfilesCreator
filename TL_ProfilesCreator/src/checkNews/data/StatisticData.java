package checkNews.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class for providing statistical data
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class StatisticData {
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	public StatisticData () {
		
	}

	
	/*--------------------------*/
	/*     PUBLIC METHODS       */
	/*--------------------------*/
	
	/**
	 * Method for getting total data of News, Messages, Chats and datasets
	 * @return totalFigures
	 */
	public int[] getTotalFigures () {
		
		int[] totalFigures = {0,0,0,0,0};
				
		totalFigures[0] = getNumberNews();
		totalFigures[1] = getNumberMessages();
		totalFigures[2] = getNumberChats();
		totalFigures[3] = getNumberUserDataset();
		totalFigures[4] = getNumberMessageDataset();
				
		return totalFigures;
		
	}
	
	/**
	 * Method for getting the number of messages send by day
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int[] getDays() {
		
		int[] days = new int[7];
		int day = 0;
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram message = new MessageTelegram();
		listOfMessages = MessageTelegramHM.getMessageTelegramList();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateInString = "";
		Date date = null;
		
		try {
			
			Iterator<MessageTelegram> arrayIterator = listOfMessages.iterator();
			
			while (arrayIterator.hasNext()) {
				
				message = arrayIterator.next();			
				dateInString = message.getDate();
		
				try {
			
					date = dateFormat.parse(dateInString);
					day = date.getDay();
				} catch (ParseException e) {
		
					//e.printStackTrace();
				}
		 
				switch (day) {
				
				case 0: { 
					
					days[0]++;				
					break;
				}
				case 1: { 
					
					days[1]++;				
					break;
				}
				case 2: { 
					
					days[2]++;				
					break;
				}
				case 3: { 
					
					days[3]++;				
					break;
				}
				case 4: { 
					
					days[4]++;				
					break;
				}
				case 5: { 
					
					days[5
					      ]++;				
					break;
				}
				case 6: { 
					
					days[6]++;				
					break;
				}
				
				default:
				
	 				break;	
			}
		}
		} catch (NullPointerException e) {
		
			System.out.println("WARNING: no messages found");
		}
		
		return days;
	}
	
	
	/**
	 * Method for getting the number of messages send by hour (24h)
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int[] getHours() {
		
		int[] hours = new int[24];
		int hour = 0;
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram message = new MessageTelegram();
		listOfMessages = MessageTelegramHM.getMessageTelegramList();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateInString = "";
		Date date = null;
		
		try {
			
			Iterator<MessageTelegram> arrayIterator = listOfMessages.iterator();
			
			while (arrayIterator.hasNext()) {
				
				message = arrayIterator.next();			
				dateInString = message.getDate();
		
				try {
			
					date = dateFormat.parse(dateInString);
					hour = date.getHours();
				} catch (ParseException e) {
		
					//e.printStackTrace();
				}
		 
				switch (hour) {
				
				case 0: { 
					
					hours[0]++;				
					break;
				}
				case 1: { 
					
					hours[1]++;				
					break;
				}
				case 2: { 
					
					hours[2]++;				
					break;
				}
				case 3: { 
					
					hours[3]++;				
					break;
				}
				case 4: { 
					
					hours[4]++;				
					break;
				}
				case 5: { 
					
					hours[5
					      ]++;				
					break;
				}
				case 6: { 
					
					hours[6]++;				
					break;
				}
				case 7: { 
					
					hours[7]++;				
					break;
				}
				case 8: { 
					
					hours[8]++;				
					break;
				}
				case 9: { 
					
					hours[9]++;				
					break;
				}
				case 10: { 
					
					hours[10]++;				
					break;
				}
				case 11: { 
					
					hours[11]++;				
					break;
				}
				
				case 12: { 
					
					hours[12]++;				
					break;
				}
				case 13: { 
					
					hours[13]++;				
					break;
				}
				case 14: { 
					
					hours[14]++;				
					break;
				}
				case 15: { 
					
					hours[15]++;				
					break;
				}
				case 16: { 
					
					hours[16]++;				
					break;
				}
				case 17: { 
					
					hours[17]++;				
					break;
				}
				case 18: { 
					
					hours[18]++;				
					break;
				}
				case 19: { 
					
					hours[19]++;				
					break;
				}
				case 20: { 
					
					hours[20]++;				
					break;
				}
				case 21: { 
					
					hours[21]++;				
					break;
				}
				case 22: { 
					
					hours[22]++;				
					break;
				}
				case 23: { 
					
					hours[23]++;				
					break;
				}
				
				default:
				
	 				break;	
			}
		}
		} catch (NullPointerException e) {
		
			System.out.println("WARNING: no messages found");
		
		}
		
		return hours;
	}
	
	
	/**
	 * Method for getting the number of each type of Messages
	 * @return the number of each type of Messages
	 */
	public int[] getTotalMessageType() {
		
		int textMessages = 0;
		int pictureMessages = 0;
		int videoMessages = 0;
		int othersMessages = 0;	
		
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram message = new MessageTelegram();
		MessageType type;
		
		listOfMessages = MessageTelegramHM.getMessageTelegramList();
		
		try {
		
			Iterator<MessageTelegram> arrayIterator = listOfMessages.iterator();
			
			while (arrayIterator.hasNext()) {
				
				message = arrayIterator.next();			
				type = message.getType();
							
				switch (type.toString()) {
				
					case "TEXT": { 
						
						textMessages++;				
						break;
					}
					
					case "PICTURE": { 
						
						pictureMessages++;				
						break;
					}
				
					case "VIDEO": { 
						
						videoMessages++;				
						break;
					}
					
					default:
						
						othersMessages++;
		 				break;	
				}
			}
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no messages found");
		}
		
		int[] totalMessageType = {textMessages, pictureMessages, videoMessages, othersMessages};
		return totalMessageType;
	}
	
	
	/**
	 * Method for getting the number of each type of News
	 * @return the number of each type of News
	 */
	public int[] getTotalNewsType() {
		
		int internetNews = 0;
		int rssNews = 0;
		
		ArrayList<News> listOfNews = new ArrayList<News>();
		News aNew = new News();
		NewsType type;
		
		listOfNews = NewsHM.getNewsList();
		
		try {
		
			Iterator<News> arrayIterator = listOfNews.iterator();
			
			while (arrayIterator.hasNext()) {
				
				aNew = arrayIterator.next();			
				type = aNew.getType();
							
				switch (type.toString()) {
				
					case "INTERNET": { 
						
						internetNews++;				
						break;
					}
					
					case "RSS": { 
						
						rssNews++;				
						break;
					}
				
					default:
						
		 				break;	
				}
			}
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no news found");
		}
		
		int[] totalNewsType = {internetNews, rssNews};
		return totalNewsType;
	}
	
	/*--------------------------*/
	/*    PRIVATE METHODS       */
	/*--------------------------*/
	
	/**
	 * Method for getting the number on News stored on HashMap
	 * @return number of elements
	 */
	private int getNumberNews() {
		
		return NewsHM.getNumberOfElements();
	}
	
	
	/**
	 * Method for getting the number on Messages stored on HashMap
	 * @return number of elements
	 */
	private int getNumberMessages() {
		
		return MessageTelegramHM.getNumberOfElements();
	}


	/**
	 * Method for getting the number on Chats stored on HashMap
	 * @return number of elements
	 */
	private int getNumberChats() {
	
		return ChatTelegramHM.getNumberOfElements();
	}


	/**
	 * Method for getting the number on User datasets stored on HashMap
	 * @return number of elements
	 */
	private int getNumberUserDataset() {
	
		return DataSetUserHM.getNumberOfElements();
	}


	/**
	 * Method for getting the number on Message datasets stored on HashMap
	 * @return number of elements
	 */
	private int getNumberMessageDataset() {
	
		return DataSetMessageHM.getNumberOfElements();
	}
	
}
