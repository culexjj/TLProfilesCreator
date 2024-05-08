package checkNews.data;

/**
 * this class describe an User from Telegram
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class UserTelegram {
	
	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	private String firstName;
	private String lastName;
	private String userName;
	private Long senderId;
	private boolean isFake; //True, if many users reported this user as a fake account.
	private boolean isScam; //True, if many users reported this user as a scam.
	private boolean isVerified; //True, if the user is verified.
	private boolean isPremium; //True, if the user is premium.
	

	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * UserTelegram builder
	 */
	public UserTelegram() {
		
	}
	
	/**
	 * UserTelegram builder
	 * @param firstName
	 * @param lastName
	 * @param userName
	 * @param senderId
	 * @param isFake
	 * @param isScam
	 * @param isVerified
	 * @param isPremium
	 */
	public UserTelegram(String firstName, String lastName, String userName, Long senderId, boolean isFake, boolean isScam,
			boolean isVerified, boolean isPremium) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.senderId = senderId;
		this.isFake = isFake;
		this.isScam = isScam;
		this.isVerified = isVerified;
		this.isPremium = isPremium;
	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		
		return firstName;
	}
	
	
	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
	}
	
	
	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		
		return lastName;
	}
	
	
	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		
		this.lastName = lastName;
	}
	
	
	/**
	 * 
	 * @return userName
	 */
	public String getUserName() {
		
		return userName;
	}
	
	
	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		
		this.userName = userName;
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
	 * @return isFake
	 */
	public boolean getIsFake() {
		
		return isFake;
	}
	
	
	/**
	 * 
	 * @return  T/F
	 */
	public String getIsFakeToString() {
		
		return toString(isFake);
	}
	
	
	/**
	 * 
	 * @param isFake
	 */
	public void setIsFake(boolean isFake) {
		
		this.isFake = isFake;
	}
	
	
	/**
	 * 
	 * @return isScam
	 */
	public boolean getIsScam() {
		
		return isScam;
	}
	
	
	/**
	 * 
	 * @return T/F
	 */
	public String getIsScamToString() {
		
		return toString(isScam);
	}
	
	
	/**
	 * 
	 * @param isScam
	 */
	public void setIsScam(boolean isScam) {
		
		this.isScam = isScam;
	}
	
	
	/**
	 * 
	 * @return isVerified
	 */
	public boolean getIsVerified() {
		
		return isVerified;
	}
	
	
	/**
	 * 
	 * @return  T/F
	 */
	public String getIsVerifiedToString() {
		
		return toString(isVerified);
	}
	
	
	/**
	 * 
	 * @param isVerified
	 */
	public void setIsVerified(boolean isVerified) {
		
		this.isVerified = isVerified;
	}
	
	
	/**
	 * 
	 * @return isPremium
	 */
	public boolean getIsPremium() {
		
		return isPremium;
	}
	
	
	/**
	 * 
	 * @return T/F 
	 */
	public String getIsPremiumToString() {
		
		return toString(isPremium);
	}
	
	
	/**
	 * 
	 * @param isPremium
	 */
	public void setIsPremium(boolean isPremium) {
		
		this.isPremium = isPremium;
	}
	
	

	/*--------------------------*/
	/*    PRIVATE  METHODS      */
	/*--------------------------*/
	
	/**
	 * Method ToString for getting boolean values
	 * @param valor
	 * @return the boolean value
	 */
	private String toString(boolean valor) {
		switch (Boolean.toString(valor)) {
			case "true":
				return "T";
			case "false":
				return "F";
			default:
				return "";
		}
	}
	
}
