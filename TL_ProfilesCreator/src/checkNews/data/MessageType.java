package checkNews.data;

/**
 * Class emun type of Message
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public enum MessageType { 

	TEXT, PICTURE, VIDEO, DEFAULT; 


	/**
	 * toString Method
	 */
	@Override
	public String toString() {
		switch (this) {
			case TEXT:
				return "TEXT";
			case PICTURE:
				return "PICTURE";
			case VIDEO:
				return "VIDEO";
			case DEFAULT:
				return "DEFAULT";
			default:
				return "";
		}
	}

}
