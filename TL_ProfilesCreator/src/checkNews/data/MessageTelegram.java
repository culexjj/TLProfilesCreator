package checkNews.data;

/**
 * this class describe a Message from Telegram
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class MessageTelegram {
	

	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/ 
	
	private Long messageId;
	private Long senderId;
	private String date;
	private Long chatId;
	private String content; //The content of the Telegram Message
	private String urlQuery; //the URL source which returns this message
	private MessageType type; //type of the message TEXT, PICTURE, VIDEO, DEFAULT
	

	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * MessageTelegram builder
	 */
	public MessageTelegram () {
		
	}
	
	/**
	 * MessageTelegram builder
	 * @param messageId
	 * @param senderId
	 * @param date
	 * @param chatId
	 * @param content
	 * @param type
	 * @param query (source)
	 */
	
	public MessageTelegram (Long messageId, Long senderId, String date, Long chatId, String content, MessageType type, String query) {	
		
		this.messageId = messageId;
		this.senderId = senderId;
		this.date = date;
		this.chatId = chatId;
		this.content = content;
		this.setType(type);	
		this.urlQuery = query;
	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * 
	 * @return messageId
	 */
	public Long getMessageId() {
		
		return messageId;
	}

	
	/**
	 * 
	 * @param messageId
	 */
	public void setMessageId(Long messageId) {
		
		this.messageId = messageId;
	}

	
	/**
	 * 
	 * @return senderId
	 */
	public Long getSenderId() {
		
		return senderId;
	}

	
	/**
	 * 
	 * @param senderId
	 */
	public void setSenderId(Long senderId) {
		
		this.senderId = senderId;
	}

	
	/**
	 * 
	 * @return date
	 */
	public String getDate() {
		
		return date;
	}

	
	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		
		this.date = date;
	}

	
	/**
	 * 
	 * @return chatId
	 */
	public Long getChatId() {
		
		return chatId;
	}


	/**
	 * 
	 * @param chatId
	 */
	public void setChatId(Long chatId) {
		
		this.chatId = chatId;
	}

	
	/**
	 * 
	 * @return content of the message
	 */
	public String getContent() {
		
		return content;
	}

	
	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		
		this.content = content;
	}
	
	
	/**
	 * 
	 * @return the URL query  which returns this message
	 */
	public String getSource() {
		
		return urlQuery;
	}

	
	/**
	 * 
	 * @param query (the URL)
	 */
	public void setSource(String query) {
		
		this.urlQuery = query;
	}


	
	/**
	 * 
	 * @return type of message TEXT, PICTURE, VIDEO, DEFAULT
	 */
	public MessageType getType() {
		
		return type;
	}

	
	/**
	 * 
	 * @param type
	 */
	public void setType(MessageType type) {
		
		this.type = type;
	}
	
}
