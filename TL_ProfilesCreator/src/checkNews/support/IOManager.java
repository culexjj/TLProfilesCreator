package checkNews.support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import checkNews.data.DataSetMessage;
import checkNews.data.DataSetMessageHM;
import checkNews.data.DataSetUser;
import checkNews.data.DataSetUserHM;
import checkNews.data.MessageTelegram;
import checkNews.data.MessageTelegramHM;
import checkNews.data.MessageType;
import checkNews.data.News;
import checkNews.data.NewsHM;
import checkNews.data.NewsType;
import checkNews.data.UserTelegram;

/**
 * Class for managing IO actions, it read and write JSON files
 * requirements:
 * add 	requires json.simple;   in module-info.java
 * use jar json-simple-4.0.1.jar  and add it in  'properties-java build path-libraries-module path'
 * @author Jose Javier Culebras
 * @version 1.0 
 */

public class IOManager {
	

	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/ 
	
	private int numberOfNews = 0;
	private int numberOfNewsNew = 0;
	private int numberOfMessages = 0;
	private int numberOfMessagesNew = 0;
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	public IOManager () {
		
	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
		
	/**
	 * Method for reading a file of News. Use with GUI
	 */
	@SuppressWarnings("static-access")
	public void readNewsJsonFile (String filePath) {
		
		numberOfNews = NewsHM.getNumberOfElements(); //Counter. NUmber of News
		
		String date = null;
		String title = null;
		String url = null; 
		String description = null;
		String type = null; 
		NewsType typeEnum = NewsType.DEFAULT; //type of the message INTERNET, RSS, DEFAULT
			
		try { //If a file with News exits is loaded
				 
		    BufferedReader fileReader = Files.newBufferedReader(Paths.get(filePath));
		    JsonArray fileParser = (JsonArray) Jsoner.deserialize(fileReader);  

		    for (Object i:fileParser) {  //Iterate through file and add News to HashMap
			    	
		    	date = ((JsonObject) i).get("date").toString();
		    	title = ((JsonObject) i).get("title").toString();
		    	url = ((JsonObject) i).get("url").toString();
		    	description = ((JsonObject) i).get("description").toString();
				type = ((JsonObject) i).get("type").toString();
				typeEnum = typeEnum.valueOf(type);
			    	
				News news = new News(date, title, url, description, typeEnum);
		        NewsHM.add(url.toLowerCase(), news);
		    }
			    
		    fileReader.close();			    
			} catch (IOException e) {
				
			    System.out.println("ERROR: file reading error");
			    return;
			} catch (Exception e) 	{
				
				System.out.println("ERROR: check file " + filePath);
				return;
			} 
			
		numberOfNewsNew = NewsHM.getNumberOfElements();
		System.out.println("INFO: " + (numberOfNewsNew-numberOfNews) + " news loaded" );
	}
	

	/**
	 * Method for writing a file of News. Use with GUI
	 * @param filePath
	 */
	public void writeNewsJsonFile (String filePath) {
		
		int number = 0; //Counter. Number of News
		
       	try {
				
   		    JsonArray listJson = new JsonArray();
   		    HashMap<String, News> listNews = NewsHM.getNewsHM();
   		    number = listNews.size();
   			Iterator<Entry<String, News>> hmIterator = listNews.entrySet().iterator();
   			News aNew = new News();
    		    
   		    while (hmIterator.hasNext()) { //Iterate through HashMap and add News to file
				
  		        @SuppressWarnings("rawtypes")
				Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 	    		        
   		        aNew = (News) hashMapElement.getValue();	    		        
   		        JsonObject newToWrite = new JsonObject();
    		     
   		        newToWrite.put("date", aNew.getDate());
   		        newToWrite.put("url", aNew.getUrl());
   		        newToWrite.put("title", aNew.getTitle());
   		        newToWrite.put("type", aNew.getType().toString());
			    newToWrite.put("description", aNew.getDescription());
   		    	listJson.add(newToWrite);
   		    }
    		    
   		    BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(filePath));
   		    Jsoner.serialize(listJson, fileWriter); //Write JSON array to file	
   		    fileWriter.close(); 

   		} catch (IOException e) {
				
   		    System.out.println("ERROR: file writing error");
   		    return;
   		} catch (Exception e) 	{
    			
   			System.out.println("ERROR: check file " + filePath); 
   			return;
   		}
       	
       	System.out.println("INFO: " + number + " news saved" );
	}


	/**
	 * Method for reading a file of MessageTelegram
	 */
	@SuppressWarnings("static-access")
	public void readMessageTelegramJsonFile (String filePath) {
		
		numberOfMessages = MessageTelegramHM.getNumberOfElements(); //Counter. NUmber of Messages
		
		String date = null;
		Long messageId = null;
		Long senderId = null;
		Long chatId = null;
		String content = null;
		String type = null; 
		MessageType typeEnum = MessageType.DEFAULT; //type of the message TEXT, PICTURE, VIDEO, DEFAULT		
		String urlQuery = null;
		
			try { //If a file with MessageTelegram exits is loaded
				 
			    BufferedReader fileReader = Files.newBufferedReader(Paths.get(filePath));
			    JsonArray fileParser = (JsonArray) Jsoner.deserialize(fileReader);  

			    for (Object i:fileParser) { //Iterate through file and add MessageTelegram to HashMap
			    	
			    	date = ((JsonObject) i).get("date").toString();
			    	BigDecimal messageIdBD = (BigDecimal) ((JsonObject) i).get("messageId");
			    	messageId = messageIdBD.longValue();
			    	BigDecimal senderIdBD = (BigDecimal) ((JsonObject) i).get("senderId");
			    	senderId = senderIdBD.longValue();
			    	BigDecimal chatIdBD = (BigDecimal) ((JsonObject) i).get("chatId");
			    	chatId = chatIdBD.longValue();			    	
			    	content = ((JsonObject) i).get("content").toString();
			    	type = ((JsonObject) i).get("type").toString();
			    	typeEnum = typeEnum.valueOf(type);
			    	urlQuery = ((JsonObject) i).get("urlQuery").toString();
			    
			    	MessageTelegram message = new MessageTelegram(messageId, senderId, date, chatId, content, typeEnum, urlQuery );
			        
					MessageTelegramHM.add(messageId, message);
			    }
			    
			    fileReader.close();
			} catch (IOException e) {
				
			    System.out.println("ERROR: file reading error"); 
			    return;
			} catch (Exception e) 	{
							
				System.out.println("ERROR: check file " + filePath);  
				return;
			}
			
			numberOfMessagesNew = MessageTelegramHM.getNumberOfElements();
			System.out.println("INFO: " + (numberOfMessagesNew-numberOfMessages) + " messages loaded" );
	}
	
	
	/**
	 * Method for writing a file of Messages Telegram . Use with GUI.
	 * @param filePath
	 */
	public void writeMessageTelegramJsonFile (String filePath) {
		
		int number = 0; //Counter. Number of Messages 
		
        try {
        					
        	JsonArray listJson = new JsonArray();
        	HashMap<Long, MessageTelegram> listMessageTelegram = MessageTelegramHM.getMessageTelegramHM();
        	number = listMessageTelegram.size();
        	Iterator<Entry<Long, MessageTelegram>> hmIterator = listMessageTelegram.entrySet().iterator();
        	MessageTelegram message = new MessageTelegram();
    		    
    		while (hmIterator.hasNext()) { //Iterate through HashMap and add MessageTelegram to file
					
    		    @SuppressWarnings("rawtypes")
    		    Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 	    		        
    		    message = (MessageTelegram) hashMapElement.getValue();
    		        
    		    JsonObject messageToWrite = new JsonObject();
    		     
    		    messageToWrite.put("date", message.getDate());
    		    messageToWrite.put("messageId", message.getMessageId());
    		    messageToWrite.put("senderId", message.getSenderId());
    		    messageToWrite.put("chatId", message.getChatId());
    		    messageToWrite.put("content", message.getContent());       
    		    messageToWrite.put("type", message.getType().toString());
    		    messageToWrite.put("urlQuery", message.getSource().toString());
    		        
    		    listJson.add(messageToWrite);   
    		}
    		     
    		BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(filePath));
    		Jsoner.serialize(listJson, fileWriter); //Write JSON array to file	
    		fileWriter.close(); 

        } catch (IOException e) {
				
        	System.out.println("ERROR: file writing error");
        	return;
    	} catch (Exception e) 	{
    			
    		System.out.println("ERROR: check file " + filePath);    			
    		return;
    	}
        
        System.out.println("INFO: " + number + " messages saved" );
	}

	
	/**
	 * Method for writing a dataset User file
	 * @param query
	 * @param path
	 */
	public void writeDataSetUserJsonFile(Long query, String path) {
		
	   	try {
        			    		    
	   		JsonObject dataSetToWrite = new JsonObject();
	   		JsonArray listJson = new JsonArray();
	   		DataSetUser dataSet = DataSetUserHM.getDataSenderId(query);
	    			 			    
	   		dataSetToWrite.put("senderId", dataSet.getUserTelegram().getSenderId());
	   		dataSetToWrite.put("firstName", dataSet.getUserTelegram().getFirstName());
	   		dataSetToWrite.put("lastName", dataSet.getUserTelegram().getLastName());
	   		dataSetToWrite.put("userName", dataSet.getUserTelegram().getUserName());
		    dataSetToWrite.put("isFake", dataSet.getUserTelegram().getIsFake());
		    dataSetToWrite.put("isScam", dataSet.getUserTelegram().getIsScam());
		    dataSetToWrite.put("isVerified", dataSet.getUserTelegram().getIsVerified());
		    dataSetToWrite.put("isPremium", dataSet.getUserTelegram().getIsPremium());
	    		        
	   		ArrayList<MessageTelegram> listOfMessages = dataSet.getListOfMessages();
	    		       
	   		Iterator<MessageTelegram> arrayIterator = listOfMessages.iterator();
	   		MessageTelegram message = new MessageTelegram();
	        		    
	       	while (arrayIterator.hasNext()) { //Iterate through file and add DataSet to HashMap
	        			
	       		message = arrayIterator.next();
	       		JsonObject messageToWrite = new JsonObject();	
	        		    	
	       		messageToWrite.put("date", message.getDate());
	       		messageToWrite.put("messageId", message.getMessageId());
	       		messageToWrite.put("senderId", message.getSenderId());
	       		messageToWrite.put("chatId", message.getChatId());
	       		messageToWrite.put("content", message.getContent());       
	       		messageToWrite.put("type", message.getType().toString());
	       			messageToWrite.put("urlQuery", message.getSource().toString());
	        		    	
	       		listJson.add(messageToWrite);
	       	}
	    				
	       	dataSetToWrite.put("messages", listJson);
	   		
	       	BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(path));
	   		Jsoner.serialize(dataSetToWrite, fileWriter); //Write JSON array to file	
	   		fileWriter.close(); 
	    		
    	} catch (IOException e) {
				
    	    System.out.println("ERROR: file writing error");	 
    	    return;
    	} catch (Exception e) 	{
    			
    		System.out.println("WARNING: no data available");  
    		return;
    	}
	        
	       System.out.println("INFO: dataset saved" );	
	}
	
	
	/**
	 * 
	 * Method for writing a file of DataSetMessage. 
	 * @param query, the message to search
	 * @param pathFile
	 */
	public void writeDataSetMessageJsonFile(String query, String pathFile) {
		
        try {
        			    		    
        	JsonObject dataSetToWrite = new JsonObject();
    	    JsonArray listJson = new JsonArray();
    		    
    	    DataSetMessage dataSet = DataSetMessageHM.getDataMessage(query.toLowerCase());
    		    
    	    dataSetToWrite.put("message", dataSet.getMessage());    							    		        
    	    ArrayList<UserTelegram> listOfUsers = dataSet.getListOfUsers();
 
    	    if (listOfUsers.size() == 0) {
    		    	
    	    	System.out.println("WARNING: no data available");
        		return;
    	    }
    		    
    	    Iterator<UserTelegram> arrayIterator = listOfUsers.iterator();
    		    
    	    UserTelegram user = new UserTelegram();
        		    
        	while (arrayIterator.hasNext()) { //Iterate through HashMap and add DataSet to file
        			
        	  	user = arrayIterator.next();	        		    	
        	    JsonObject messageToWrite = new JsonObject();	
        		    	
        	    messageToWrite.put("senderId", user.getSenderId());
        	    messageToWrite.put("firstName", user.getFirstName());
        	    messageToWrite.put("lastName", user.getLastName());
        	    messageToWrite.put("userName", user.getUserName());
        	    messageToWrite.put("fake", user.getIsFake());       
        	    messageToWrite.put("scam", user.getIsScam());
        	    messageToWrite.put("verified", user.getIsVerified());       
        	    messageToWrite.put("premium", user.getIsPremium());
        		    	
        	    listJson.add(messageToWrite);
        	}
    				
        	dataSetToWrite.put("users", listJson);
    		    
        	BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(pathFile));
    	    Jsoner.serialize(dataSetToWrite, fileWriter); //Write JSON array to file	
    	    fileWriter.close(); 
	    		
    	} catch (IOException e) {
				
    	    System.out.println("ERROR: file writing error");	  
    	    return;
    	} catch (Exception e) 	{
    			
    		System.out.println("WARNING: no data available");
    		return;
   		}
        	
       	System.out.println("INFO: dataset saved" );
	}
			
}
