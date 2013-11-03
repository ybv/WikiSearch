/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SharedDictionary extends Dictionary {

	/**
	 * Public default constructor
	 * @param props: The properties file
	 * @param field: The field being indexed by this dictionary
	 */
	Map<String,Integer> docdic =  new HashMap<String, Integer>();
	public SharedDictionary(Properties props, INDEXFIELD field) {
		super(props, field);
		// TODO Add more code here if needed
	}

	/**
	 * Method to lookup and possibly add a mapping for the given value
	 * in the dictionary. The class should first try and find the given
	 * value within its dictionary. If found, it should return its
	 * id (Or hash value). If not found, it should create an entry and
	 * return the newly created id.
	 * @param value: The value to be looked up
	 * @return The id as explained above.
	 */
	public synchronized int lookup(String value) {
		String keystr = value;
		if(docdic.containsKey(keystr)){
			//System.out.println("IN SHARED DICT LOOKUP "+ docdic.get(keystr));
			return docdic.get(keystr);
		}
		else{
			docdic.put(keystr,keystr.hashCode());
			return docdic.get(keystr);
		}

	}
	
	public void writeToDisk() throws IndexerException {


		try{
			fout = new FileOutputStream(output_file+"/doc_dic.ser");
			//System.out.println("term on disk");

			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(fout);

				oos.writeObject(docdic);

				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   

			//System.out.println("Doc dic Done");

		}catch(Exception ex){
			ex.printStackTrace();
		}


	}

}
