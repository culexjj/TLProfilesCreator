package checkNews.data;

/**
 * Class emun status of Chat Telegram
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public enum ChatTelegramStatus {
	
MEMBER, NO_MEMBER, DEFAULT; 
	
	/**
	 * toString Method
	 */
	@Override
	public String toString() {
		switch (this) {
			case MEMBER:
				return "MEMBER";
			case NO_MEMBER:
				return "NO MEMBER";
			case DEFAULT:
				return "DEFAULT";
			default:
				return "";
		}
	}

}
