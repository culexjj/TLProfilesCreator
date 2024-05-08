package checkNews.data;

/**
 * this class describe a Chat from Telegram
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public class ChatTelegram {
	

	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	private Long chatId; 
	private String chatName;	
	private ChatTelegramStatus status; //Status of the chat MEMBER, NO_MEMBER, DEFAULT
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * Chat Builder
	 */
	public ChatTelegram () {
		
	}
	
	/**
	 * Chat Builder
	 * @param chatId
	 * @param chatName
	 * @param status
	 */
	public ChatTelegram(Long chatId, String chatName, ChatTelegramStatus status) {
		super();
		this.chatId = chatId;
		this.chatName = chatName;
		this.status = status;
	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
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
	 * @return chatName
	 */
	public String getChatName() {
		
		return chatName;
	}
	
	
	/**
	 * 
	 * @param chatName
	 */
	public void setChatName(String chatName) {
		
		this.chatName = chatName;
	}
	
	
	/**
	 * The current status of the chat concerning the user [MEMBER | NO_MEMBER,]
	 * @return status
	 */
	public ChatTelegramStatus getStatus() {
		
		return status;
	}
	
	
	/**
	 * 
	 * @param status
	 */
	public void setStatus(ChatTelegramStatus status) {
		
		this.status = status;
	}

}
