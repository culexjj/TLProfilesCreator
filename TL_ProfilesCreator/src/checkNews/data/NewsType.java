package checkNews.data;


/**
 * Class emun type of News source
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public enum NewsType {
	
	INTERNET, RSS, DEFAULT; 
	
	/**
	 * toString Method
	 */
	@Override
	public String toString() {
		switch (this) {
			case INTERNET:
				return "INTERNET";
			case RSS:
				return "RSS";
			case DEFAULT:
				return "DEFAULT";
			default:
				return "";
		}
	}

}



