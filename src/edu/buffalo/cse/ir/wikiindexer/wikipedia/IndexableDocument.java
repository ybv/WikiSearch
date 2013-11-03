/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.HashMap;
import java.util.Map;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;


public class IndexableDocument {
	/**
	 * Default constructor
	 */
	Map<INDEXFIELD, TokenStream> idftsmap ;
	String docidtitle = "";
	public IndexableDocument() {
		this.idftsmap  = new HashMap<INDEXFIELD,TokenStream>();
	}
	
	
	/**
	 * MEthod to add a field and stream to the map
	 * If the field already exists in the map, the streams should be merged
	 * @param field: The field to be added
	 * @param stream: The stream to be added.
	 */
	public void addField(INDEXFIELD field, TokenStream stream) {
		//System.out.println("INSIDE INDEXIBLE DOCUMENT ADD FIELD");
		if(stream!=null && !field.toString().equals("")){
			idftsmap.put(field, stream);
			//System.out.println(field.toString());
			//System.out.println(stream.getAllTokens());
		}
	}
	
	/**
	 * Method to return the stream for a given field
	 * @param key: The field for which the stream is requested
	 * @return The underlying stream if the key exists, null otherwise
	 */
	public TokenStream getStream(INDEXFIELD key) {
		//TODO: Implement this method
		return idftsmap.get(key);
	}
	
	/**
	 * Method to return a unique identifier for the given document.
	 * It is left to the student to identify what this must be
	 * But also look at how it is referenced in the indexing process
	 * @return A unique identifier for the given document
	 */
	public String getDocumentIdentifier() {
		//TODO: Implement this method
		return this.docidtitle;
	}
	public void addTitle(String title) {
		// TODO Auto-generated method stub
		this.docidtitle=title;
	}
	
}
