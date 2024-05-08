package checkNews.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Class for store and  Manage a HashMap of DataSetMessages - Type B
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class DataSetMessageHM {

	/*--------------------------*/
	/*         VARIABLES    	*/
	/*--------------------------*/
	
	private static HashMap<String, DataSetMessage> hashMapDataSet = new HashMap<String, DataSetMessage>(200, (float) 0.75);// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html -> HashMap(int initialCapacity, float loadFactor)

	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/

	/**
	 * Method for adding a DataSet to HashMap
	 * @param message to search (key)
	 * @param dataSet (value)
	 */
	public static void add(String message , DataSetMessage dataSet) {		
	
		hashMapDataSet.put(message.toLowerCase(), dataSet);
	}
	
	
	/**
	 * Method for getting the HM value of a message
	 * @param message (key)
	 * @return value
	 */
	public static DataSetMessage getDataMessage(String message) {
		
		DataSetMessage value = hashMapDataSet.get(message);
		return value;
	}
	
	
	/**
	 * 
	 * @return number of the elements from the HashMap
	 */
	public static int getNumberOfElements() {
		
		return hashMapDataSet.size();
		
	}
	
	/**
	 * Method for getting the HashMap of all DataSetMessage objects
	 * @return hashMapDataSet
	 */
	public static HashMap<String, DataSetMessage> getDataSetHM() {
		
		return hashMapDataSet;
	}
	
	
	/**
	 * Method for getting a list of all DatSetMessage (Type B)
	 * @return listOfDataSetMessage
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<DataSetMessage> getDataSetMessageList() {
		
		ArrayList<DataSetMessage> listOfDataSetMessage = new ArrayList<DataSetMessage>();
		DataSetMessage datSetMessageRecord = new DataSetMessage();
		
		try {
			
			Iterator<Entry<String, DataSetMessage>> hmIterator = hashMapDataSet.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		    	datSetMessageRecord = (DataSetMessage) hashMapElement.getValue();
		    	listOfDataSetMessage.add(datSetMessageRecord);
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no dataset found");
		}
				
		return listOfDataSetMessage;
	}

	
	/**
	 * Method for getting a filter list of DataSet (Type B)
	 * The filter has to be an unique string(the url used for creating the dataset)
	 * @param query (url)
	 * @return listOfDataSetMessage
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<DataSetMessage> getDataSetMessageFilterList(String query) {
		
		ArrayList<DataSetMessage> listOfDataSetMessage = new ArrayList<DataSetMessage>();
		DataSetMessage datSetMessageRecord = new DataSetMessage();
		
		try {
			
			Iterator<Entry<String, DataSetMessage>> hmIterator = hashMapDataSet.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		    	datSetMessageRecord = (DataSetMessage) hashMapElement.getValue();
		    	
		    	if (datSetMessageRecord.getMessage().equals(query)) {
		    		
		    		listOfDataSetMessage.add(datSetMessageRecord);
		    	}
		    	
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no dataset found");
		}
				
		return listOfDataSetMessage;
		
	}
	
}
