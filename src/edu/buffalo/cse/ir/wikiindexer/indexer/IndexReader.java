/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.buffalo.cse.ir.wikiindexer.IndexerConstants;


public class IndexReader {
	/**
	 * Constructor to create an instance 
	 * @param props: The properties file
	 * @param field: The index field whose index is to be read
	 */
	Properties pro;
	INDEXFIELD ind;
	String input_file;
	FileInputStream fin;
	ObjectInputStream ois;
	FileInputStream find;
	ObjectInputStream oind;
	String[] names;
	String[] indexes;
	Map<String, Integer> allTerms = new HashMap<String,Integer>();
	Map<Integer,List<Postings> > index = new HashMap<Integer,List<Postings>>(); 
	Map<Integer,List<Integer> > linkindex = new HashMap<Integer,List<Integer>>(); 
	public IndexReader(Properties props, INDEXFIELD field) {

		this.pro= props;
		this.ind= field;
		this.input_file  = props.getProperty(IndexerConstants.TEMP_DIR);

		try {
			//System.out.println("COMING HERE HERE HERE ");
			if(ind == INDEXFIELD.AUTHOR){

				this.fin = new FileInputStream(input_file+"/loc"+ind.toString()+".ser");
				this.find = new FileInputStream(input_file+"/authorindex.ser");
				try {
					this.ois = new ObjectInputStream(fin);
					this.oind = new ObjectInputStream(find);
					@SuppressWarnings("unchecked")
					Map<String, Integer> merge_dic = (Map<String,Integer>)ois.readObject();
					@SuppressWarnings("unchecked")
					Map<Integer,List<Postings> > merge_index =  (Map<Integer,List<Postings>>)oind.readObject(); 
					allTerms.putAll(merge_dic);
					index.putAll(merge_index);
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(ind == INDEXFIELD.LINK){

				this.fin = new FileInputStream(input_file+"/doc_dic.ser");
				this.find = new FileInputStream(input_file+"/linksindex.ser");
				try {
					this.ois = new ObjectInputStream(fin);
					this.oind = new ObjectInputStream(find);
					@SuppressWarnings("unchecked")
					Map<String, Integer> merge_dic = (Map<String,Integer>)ois.readObject();
					@SuppressWarnings("unchecked")
					Map<Integer,List<Integer> > merge_index =  (Map<Integer,List<Integer>>)oind.readObject(); 
					allTerms.putAll(merge_dic);
					linkindex.putAll(merge_index);
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(ind == INDEXFIELD.CATEGORY){
				this.fin = new FileInputStream(input_file+"/loc"+ind.toString()+".ser");
				this.find = new FileInputStream(input_file+"/catsindex.ser");
				try {
					this.ois = new ObjectInputStream(fin);
					this.oind = new ObjectInputStream(find);
					@SuppressWarnings("unchecked")
					Map<String, Integer> merge_dic = (Map<String,Integer>)ois.readObject();
					@SuppressWarnings("unchecked")
					Map<Integer,List<Postings> > merge_index =  (Map<Integer,List<Postings>>)oind.readObject(); 
					allTerms.putAll(merge_dic);
					index.putAll(merge_index);
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(ind == INDEXFIELD.TERM){
				this.names = new String[]{"local_dic0.ser", "local_dic1.ser", "local_dic2.ser","local_dic3.ser","local_dic4.ser","local_dic5.ser","local_dic6.ser","local_dic7.ser","local_dic8.ser","local_dic9.ser","local_dic10.ser","local_dic11.ser","local_dic12.ser","local_dic13.ser","local_dic14.ser","local_dic14.ser","local_dic15.ser","local_dic16.ser","local_dic17.ser","local_dic18.ser","local_dic19.ser","local_dic20.ser","local_dic21.ser","local_dic22.ser","local_dic23.ser","local_dic24.ser","local_dic25.ser","local_dic26.ser"};
				this.indexes = new String[]{"termindex0.ser", "termindex1.ser", "termindex2.ser","termindex3.ser","termindex4.ser","termindex5.ser","termindex6.ser","termindex7.ser","termindex8.ser","termindex9.ser","termindex10.ser","termindex11.ser","termindex12.ser","termindex13.ser","termindex14.ser","termindex14.ser","termindex15.ser","termindex16.ser","termindex17.ser","termindex18.ser","termindex19.ser","termindex20.ser","termindex21.ser","termindex22.ser","termindex23.ser","termindex24.ser","termindex25.ser","termindex26.ser"};
				for (String name1 : names) {
					fin = new FileInputStream(input_file+name1);   
					try {
						this.ois = new ObjectInputStream(fin);
						@SuppressWarnings("unchecked")
						Map<String, Integer> merge_dic = (Map<String,Integer>)ois.readObject();

						allTerms.putAll(merge_dic);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
				for (String name2 : indexes){
					find = new FileInputStream(input_file+"/"+name2);

					try {
						this.oind = new ObjectInputStream(find);
						@SuppressWarnings("unchecked")
						Map<Integer,List<Postings> > merge_index =  (Map<Integer,List<Postings>>)oind.readObject(); 

						index.putAll(merge_index);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}






	}

	/**
	 * Method to get the total number of terms in the key dictionary
	 * @return The total number of terms as above
	 */

	public int getTotalKeyTerms() {

		int totalKeys = 0;

		totalKeys = allTerms.size();
		Set<String> s = allTerms.keySet();
		/*for (String s2: s){
				System.out.println(s);
			}*/
		return totalKeys;
	}

	/**
	 * Method to get the total number of terms in the value dictionary
	 * @return The total number of terms as above
	 */
	public int getTotalValueTerms() {
		//TODO: Implement this method
		int size = 0;
		try {
			this.fin = new FileInputStream(input_file+"/doc_dic.ser");

			this.ois = new ObjectInputStream(fin);
			@SuppressWarnings("unchecked")
			Map<String, Integer> merge_dic = (Map<String,Integer>)ois.readObject();
			size = merge_dic.size();


		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return size;
	}

	/**
	 * Method to retrieve the postings list for a given dictionary term
	 * @param key: The dictionary term to be queried
	 * @return The postings list with the value term as the key and the
	 * number of occurrences as value. An ordering is not expected on the map
	 */
	public Map<String, Integer> getPostings(String key) {
		//TODO: Implement this method
		Map<String,Integer> returnVal = new HashMap<String,Integer>();
		int id = 0;
		if(ind!=INDEXFIELD.LINK){
		if(allTerms.containsKey(key)){
			id = allTerms.get(key);

			System.out.println("ID "+id);
			try {
				int lookup = 0;
				int freq =0;
				String docname ="dash";
				List<Postings> postings = index.get(id);
				for(Postings p : postings){
					lookup = p.getdocId();
					freq = p.getFreq();

					this.fin = new FileInputStream(input_file+"/doc_dic.ser");
					this.ois = new ObjectInputStream(fin);
					@SuppressWarnings("unchecked")
					Map<String, Integer> docdic = (Map<String,Integer>)ois.readObject();
					for (Entry<String, Integer> me2 : docdic.entrySet()) {


						if (me2.getValue()==lookup) {
							docname = me2.getKey();
							returnVal.put(docname, freq);
						}

					}


				}



			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		}else{
			int docid = 0;
			if(allTerms.containsKey(key)){
				docid = allTerms.get(key);
				System.out.println("docid "+docid);
			}
			List<Integer> links = linkindex.get(docid);
			String docname = "dash";
			if(links!=null){
			for(int l :links){
				System.out.println("l value "+ l);
				for (Entry<String, Integer> all : allTerms.entrySet()) {
						
						

					if (all.getValue()==l) {
						//System.out.println("all.getValue() "+all.getValue() + " l  "+l );
						docname = all.getKey();
						returnVal.put(docname, l);
					}

				}
				
			}
			}
			
		}
		return returnVal;
	}

	/**
	 * Method to get the top k key terms from the given index
	 * The top here refers to the largest size of postings.
	 * @param k: The number of postings list requested
	 * @return An ordered collection of dictionary terms that satisfy the requirement
	 * If k is more than the total size of the index, return the full index and don't 
	 * pad the collection. Return null in case of an error or invalid inputs
	 */
	public Collection<String> getTopK(int k) {
		//TODO: Implement this method
		int id = 0;
		List<String> returnStr = new ArrayList<String>();
		if(ind!=INDEXFIELD.LINK){
	
		TreeMap <Integer,Integer> top = new TreeMap<Integer, Integer>();
		
		for (Entry<Integer, List<Postings>> me : index.entrySet()) {
			Integer key = me.getKey();
			List<Postings> valueList = me.getValue();
			top.put(valueList.size(),key);
			//System.out.println("postings size"+valueList.size());
		}
		NavigableMap<Integer,Integer> topK=top.descendingMap();
		
		int mapsize=topK.size();
		int limit = k;
		if(limit>=mapsize){
			//System.out.println("in if");
			Integer toKey = null;
			int i = 0;
			for (Integer key : topK.values()) {
			    if (i++ == limit) {
			        toKey = key;
			        if(toKey!=null){
			        	String term = null;
			        	for (Entry<String, Integer> me2 : allTerms.entrySet()) {


							if (me2.getValue()==toKey) {
								term = me2.getKey();
								returnStr.add(term);
							}
							
						}
			        }
			    }
			}
		}
		else{
			//System.out.println("in else");
			int toKey = 0;
			int i = 0;
			for (Integer key : topK.values()) {
			    if (i <limit) {
			        toKey = key;
			        	String term = null;
			       // 	System.out.println("one top value is "+toKey);
			        	for (Entry<String, Integer> me2 : allTerms.entrySet()) {
			        			//System.out.println("me2 values"+ me2.getValue());

							if (me2.getValue()==toKey) {
								term = me2.getKey();
					//			System.out.println("term is"+term);
								returnStr.add(term);
							}
							
						}
			        	i++;
			        
			    }
			}
		}
		}
		else{
			TreeMap <Integer,Integer> top = new TreeMap<Integer, Integer>();
			for (Entry<Integer, List<Integer>> me : linkindex.entrySet()) {
				Integer key = me.getKey();
				List<Integer> valueList = me.getValue();
				top.put(valueList.size(),key);
				//System.out.println("postings size"+valueList.size());
			}
			NavigableMap<Integer,Integer> topK=top.descendingMap();
			int mapsize=topK.size();
			int limit = k;
			if(limit>=mapsize){
				//System.out.println("in if");
				Integer toKey = null;
				int i = 0;
				for (Integer key : topK.values()) {
				    if (i++ == limit) {
				        toKey = key;
				        if(toKey!=null){
				        	String term = null;
				        	for (Entry<String, Integer> me2 : allTerms.entrySet()) {


								if (me2.getValue()==toKey) {
									term = me2.getKey();
									returnStr.add(term);
								}
								
							}
				        }
				    }
				}
			}
			else{
				//System.out.println("in else");
				int toKey = 0;
				int i = 0;
				for (Integer key : topK.values()) {
				    if (i <limit) {
				        toKey = key;
				        	String term = null;
				       // 	System.out.println("one top value is "+toKey);
				        	for (Entry<String, Integer> me2 : allTerms.entrySet()) {
				        			//System.out.println("me2 values"+ me2.getValue());

								if (me2.getValue()==toKey) {
									term = me2.getKey();
						//			System.out.println("term is"+term);
									returnStr.add(term);
								}
								
							}
				        	i++;
				        
				    }
				}
			}
			
		}




		return returnStr;
	}

	/**
	 * Method to execute a boolean AND query on the index
	 * @param terms The terms to be queried on
	 * @return An ordered map containing the results of the query
	 * The key is the value field of the dictionary and the value
	 * is the sum of occurrences across the different postings.
	 * The value with the highest cumulative count should be the
	 * first entry in the map.
	 */
	public Map<String, Integer> query(String... terms) {
		//TODO: Implement this method (FOR A BONUS)
		return null;
	}
}
