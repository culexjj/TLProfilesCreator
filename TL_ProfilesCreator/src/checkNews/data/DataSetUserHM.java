package checkNews.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class for store and  Manage a HashMap of DataSetUser - Type A (USER)
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class DataSetUserHM {
	
	/*--------------------------*/
	/*         VARIABLES    	*/
	/*--------------------------*/
	
	private static HashMap<Long, DataSetUser> hashMapDataSet = new HashMap<Long, DataSetUser>(200, (float) 0.75);// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html -> HashMap(int initialCapacity, float loadFactor)


	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/

	/**
	 * Method for adding a DataSet to HashMap
	 * @param senderId (key)
	 * @param dataSet (value)
	 */
	public static void add(Long senderId, DataSetUser dataSet) {		
	
		hashMapDataSet.put(senderId, dataSet);
	}
	
	
	/**
	 * Method for getting the HM value of a user
	 * @param senderId
	 * @return
	 */
	public static DataSetUser getDataSenderId(Long senderId) {
		
		DataSetUser value = hashMapDataSet.get(senderId);
		return value;
	}
	
	
	/**
	 * Method for getting the HashMap of objects DataSet
	 * @return
	 */
	public static HashMap<Long, DataSetUser> getDataSetHM() {
		
		return hashMapDataSet;
	}
	
	
	/**
	 * 
	 * @return number of the elements from the HashMap
	 */
	public static int getNumberOfElements() {
		
		return hashMapDataSet.size();
		
	}
	
	/**
	 * Method for getting a list of all DatSetUser Type A (USER)
	 * @return listOfDataSetUser
	 */
	public static ArrayList<DataSetUser> getDataSetUserList() {
		
		ArrayList<DataSetUser> listOfDataSetUser = new ArrayList<DataSetUser>();
		DataSetUser datSetUserRecord = new DataSetUser();
		
		try {
			
			Iterator<Entry<Long, DataSetUser>> hmIterator = hashMapDataSet.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	@SuppressWarnings("rawtypes")
				Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		    	datSetUserRecord = (DataSetUser) hashMapElement.getValue();
		    	listOfDataSetUser.add(datSetUserRecord);
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no dataset found");
		}
				
		return listOfDataSetUser;
	}
	
	
	/**
	 * Method for getting a filter list of DataSet Type A (USER)
	 * The filter has to be an unique string(the senderId for creating the dataset)
	 * @param query (senderId)
	 * @return listOfDataSetUser
	 */
	public static ArrayList<DataSetUser> getDataSetUserFilterList(Long query) {
		
		ArrayList<DataSetUser> listOfDataSetUser = new ArrayList<DataSetUser>();
		DataSetUser datSetUserRecord = new DataSetUser();
		
		try {
			
			Iterator<Entry<Long, DataSetUser>> hmIterator = hashMapDataSet.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	@SuppressWarnings("rawtypes")
				Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		    	datSetUserRecord = (DataSetUser) hashMapElement.getValue();
		    	
		    	if (datSetUserRecord.getUserTelegram().getSenderId().equals(query)) {
		    		
		    		listOfDataSetUser.add(datSetUserRecord);
		    	}
		    	
		    	
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no dataset found");
		}
				
		return listOfDataSetUser;
		
	}
	
}
