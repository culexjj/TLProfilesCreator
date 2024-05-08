package checkNews.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this class is for sorting news by date
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public class NewsComparator implements java.util.Comparator<News>{
		
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	/**
	* News reverse chronological order
	*/
	public int compare(News a, News b) {
        
		Date date1 = null;
		Date date2 = null;
		
		try {
			
			date1 = dateFormat.parse(a.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			
			date2 = dateFormat.parse(b.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date2.compareTo(date1);
    }

}
