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


public class LocalDictionary extends Dictionary {
	
	/**
	 * Public default constructor
	 * @param props: The properties file
	 * @param field: The field being indexed by this dictionary
	 */
	Map<String,Integer> docdic =  new HashMap<String, Integer>();
	int part;
	INDEXFIELD ind;
	public LocalDictionary(Properties props, INDEXFIELD field) {
		super(props, field);
		this.ind=field;
		// TODO Auto-generated constructor stub
	}
	public LocalDictionary(Properties props, INDEXFIELD field, int num) {
		this(props, field);
		this.part = num;
		this.ind=field;
		// TODO Auto-generated constructor stub
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
	public int lookup(String value) {
		String keystr = value;
		if(docdic.containsKey(keystr)){
			//System.out.println("IN LOCAL DICT LOOKUP "+ docdic.get(keystr));
			return docdic.get(keystr);
		}
		else{
			docdic.put(keystr,keystr.hashCode());
			return docdic.get(keystr);
		}
	}
	public boolean exists(String value) {
		if(docdic.containsKey(value)){
			return true;
		}
		else{
			
			return false;
		}
	}

	public int getTotalTerms() {
	
		return docdic.size();
	}
	public void writeToDisk() throws IndexerException {

		if(docdic.size()!=0 && docdic!=null){
			
		}
		try{
			if(ind == INDEXFIELD.TERM){
				fout = new FileOutputStream(output_file+"/local_dic"+part+".ser");
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
			}
			else{
				fout = new FileOutputStream(output_file+"loc"+ind.toString()+".ser");
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
			}
			

		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		

	}
}
