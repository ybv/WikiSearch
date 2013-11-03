/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import edu.buffalo.cse.ir.wikiindexer.IndexerConstants;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;

public class IndexWriter implements Writeable {

	/**
	 * Constructor that assumes the underlying index is inverted
	 * Every index (inverted or forward), has a key field and the value field
	 * The key field is the field on which the postings are aggregated
	 * The value field is the field whose postings we are accumulating
	 * For term index for example:
	 * 	Key: Term (or term id) - referenced by TERM INDEXFIELD
	 * 	Value: Document (or document id) - referenced by LINK INDEXFIELD
	 * @param props: The Properties file
	 * @param keyField: The index field that is the key for this index
	 * @param valueField: The index field that is the value for this index
	 */
	List<Postings> posts= new ArrayList<Postings>();

	int pnum;
	INDEXFIELD haha;
	LocalDictionary ld;
	Map<Integer, List<Postings>> index = new HashMap<Integer, List<Postings>>();
	Map<Integer, List<Integer>> linkIndex = new HashMap<Integer, List<Integer>>();
	FileOutputStream fout;
	boolean ter = false;
	boolean link = false;
	String output_file;
	Properties p;
	public IndexWriter(Properties props, INDEXFIELD keyField, INDEXFIELD valueField) {
		this(props, keyField, valueField, false);


	}

	/**
	 * Overloaded constructor that allows specifying the index type as
	 * inverted or forward
	 * Every index (inverted or forward), has a key field and the value field
	 * The key field is the field on which the postings are aggregated
	 * The value field is the field whose postings we are accumulating
	 * For term index for example:
	 * 	Key: Term (or term id) - referenced by TERM INDEXFIELD
	 * 	Value: Document (or document id) - referenced by LINK INDEXFIELD
	 * @param props: The Properties file
	 * @param keyField: The index field that is the key for this index
	 * @param valueField: The index field that is the value for this index
	 * @param isForward: true if the index is a forward index, false if inverted
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField, INDEXFIELD valueField, boolean isForward) {
		//TODO: Implement this method
		Object indexf;
		this.haha = keyField;
		this.p = props;
		this.output_file  = props.getProperty(IndexerConstants.TEMP_DIR);

		if(haha ==INDEXFIELD.AUTHOR){
			try{
				this.ld = new LocalDictionary(p,haha);
				fout = new FileOutputStream(output_file+"/authorindex.ser");
				//System.out.println(output_file+"author.ser");
				//System.out.println("author on disk");
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(haha ==INDEXFIELD.CATEGORY){
			try{
				this.ld = new LocalDictionary(p,haha);
				fout = new FileOutputStream(output_file+"/catsindex.ser");
				//System.out.println("author on category");
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(haha ==INDEXFIELD.LINK){
			try{
				this.ld = new LocalDictionary(p,haha);
				fout = new FileOutputStream(output_file+"/linksindex.ser");
				//System.out.println("author on category");
				link = true;
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

	}

	/**
	 * Method to make the writer self aware of the current partition it is handling
	 * Applicable only for distributed indexes.
	 * @param pnum: The partition number
	 */
	public void setPartitionNumber(int pnum) {
		//TODO: Optionally implement this method
		this.pnum = pnum;

		if(haha ==INDEXFIELD.TERM){
			ter = true;
			try{
				this.ld = new LocalDictionary(p,haha, pnum); 
				fout = new FileOutputStream(output_file+"/termindex"+pnum+".ser");

				//System.out.println("term on disk");
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * @param keyId: The id for the key field, pre-converted
	 * @param valueId: The id for the value field, pre-converted
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, int valueId, int numOccurances) throws IndexerException {

		if(linkIndex.containsKey(keyId)){
			List<Integer> value = linkIndex.get(keyId);
			boolean exists = false;
			for(Integer i : value){
				if(valueId==i){
					
					exists = true;
					break;
				}

			}
			if(!exists){

				value.add(valueId);

			}
			
		}
		else{
			List<Integer> value = new ArrayList<Integer>();
			value.add(valueId);
			linkIndex.put(keyId, value);
			
		}
		
		
		

		
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * @param keyId: The id for the key field, pre-converted
	 * @param value: The value for the value field
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, String value, int numOccurances) throws IndexerException {
		//TODO: Implement this method
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * @param key: The key for the key field
	 * @param valueId: The id for the value field, pre-converted
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(String key, int valueId, int numOccurances) throws IndexerException {
		if(ld.exists(key)){
			int indexkey = ld.lookup(key);
			boolean exists = false;
			List<Postings> postingslist = index.get(indexkey);
			for(Postings p : postingslist){
				if(p.cmp(valueId)){
					p.setFreby(numOccurances);
					exists = true;
				}

			}
			if(!exists){

				postingslist.add(new Postings(valueId,numOccurances));
				

			}

		}
		else{
			int indexkey = ld.lookup(key);
			List<Postings> postingslist = new ArrayList<Postings>();
			postingslist.add(new Postings(valueId, numOccurances));
			index.put(indexkey,postingslist);
			
		}




	}

	/**
	 * Method to add a given key - value mapping to the index
	 * @param key: The key for the key field
	 * @param value: The value for the value field
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(String key, String value, int numOccurances) throws IndexerException {
		//TODO: Implement this method
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method
		//for (Map.Entry<String, List<Object>> entry : index.entrySet()) {
		//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		ld.writeToDisk();

		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(fout);

			if(link){
				oos.writeObject(linkIndex);
			}else{
				oos.writeObject(index);
			}
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

		System.out.println(" Index writer Done");


	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method

	}

}