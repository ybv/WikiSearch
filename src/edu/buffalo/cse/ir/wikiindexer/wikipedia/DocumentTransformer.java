/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;

public class DocumentTransformer implements Callable<IndexableDocument> {
	/**
	 * Default constructor, DO NOT change
	 * @param tknizerMap: A map mapping a fully initialized tokenizer to a given field type
	 * @param doc: The WikipediaDocument to be processed
	 */
	Map<INDEXFIELD, Tokenizer> tknmap;
	WikipediaDocument wd;
	public DocumentTransformer(Map<INDEXFIELD, Tokenizer> tknizerMap, WikipediaDocument doc) {
		this.tknmap = tknizerMap;
		this.wd = doc;
	}
	
	/**
	 * Method to trigger the transformation
	 * @throws TokenizerException Inc ase any tokenization error occurs
	 */
	public IndexableDocument call() throws TokenizerException {
		// TODO Implement this method
		Set<INDEXFIELD> indexf = tknmap.keySet();
		IndexableDocument id = new IndexableDocument();
		StringBuffer sb = new StringBuffer();
		id.addTitle(wd.getTitle());
		for(INDEXFIELD idf : indexf){
			
			if(idf==INDEXFIELD.TERM){
				for(Section s: wd.getSections()){
					sb.append(s.getTitle());
					sb.append(s.getText());
					
				}
				Tokenizer tkn =  tknmap.get(idf);
				TokenStream ts =new TokenStream(sb.toString());
				tkn.tokenize(ts);
				id.addField(idf, ts);
			}
			else if (idf == INDEXFIELD.AUTHOR){
				sb.delete(0,sb.length());
				sb.append(wd.getAuthor());
				Tokenizer tkn =  tknmap.get(idf);
				TokenStream ts =new TokenStream(sb.toString());
				tkn.tokenize(ts);
				id.addField(idf, ts);
				
				
			}else if(idf ==INDEXFIELD.CATEGORY){
				sb.delete(0,sb.length());
				List<String> lset = wd.getCategories();
				for(String s:lset){
					sb.append(s);
					sb.append("$");
				}
				Tokenizer tkn =  tknmap.get(idf);
				TokenStream ts =new TokenStream(sb.toString());
				tkn.tokenize(ts);
				id.addField(idf, ts);
				
			}else if(idf == INDEXFIELD.LINK){
				sb.delete(0,sb.length());
				Set<String> lset = wd.getLinks();
				for(String s:lset){
					sb.append(s);
					sb.append("$");
				}
					
				Tokenizer tkn =  tknmap.get(idf);
			
				TokenStream ts =new TokenStream(sb.toString());
				tkn.tokenize(ts);
				id.addField(idf, ts);
				}
			}
			
			
			
		
		
		
		return id;
	}
	
}
