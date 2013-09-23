/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers;
import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerFactory;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.DocumentTransformer;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * @author nikhillo
 *
 */
public class Parser extends DefaultHandler{
	/* */
	private final Properties props;
	INDEXFIELD IND;
	public Collection<WikipediaDocument> documents=new ConcurrentLinkedQueue<WikipediaDocument>();
	WikipediaParser WP;
	WikipediaDocument WD;
	ArrayList<String> textstr = new ArrayList<String>();
	String thisXMLTag;
	String thisXMLText;
	String thisdate;
	String thisAuthor;
	int thisID;
	String thisTitle;
	StringBuilder onlytext;
	String textval;

	/**
	 * 
	 * @param idxConfig
	 * @param parser
	 */

	public Parser(Properties idxProps) {
		props = idxProps;
	}

	/* TODO: Implement this method */
	/**
	 * 
	 * @param filename
	 * @param docs
	 */
	public void parse(String filename, Collection<WikipediaDocument> docs) {
		documents= docs;
		
		//System.out.println(docs.toString());
		SAXParserFactory sp = SAXParserFactory.newInstance();
		SAXParser sparser= null;
		try {
			sparser = sp.newSAXParser();
			//System.out.println("this happened");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Parser handler = new Parser(props);
		//System.out.println("this happened");
		
		try {
			sparser.parse(filename,handler);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void characters (char[] buffer, int start, int length ){

		thisXMLText = String.valueOf(buffer, start, length).trim();

		if(thisXMLTag.equalsIgnoreCase("text")){
			//System.out.println("text goes on");
			//System.out.println(thisXMLText);
			/*if(thisXMLText.equals("")){
				onlytext.append("\n");
			}*/
			onlytext.append(buffer,start,length);
		}
	}

	public void startElement( String uri, String localName, String qName, Attributes attributes) {
		thisXMLTag=qName;

		if(thisXMLTag.equalsIgnoreCase("page")){
			onlytext= new StringBuilder("");
			onlytext.delete(0, onlytext.length());
			System.out.println("start element here --------------------------- "+thisXMLTag);
		}
	}

	public void endElement(String uri, String localName, String qName){

		if(qName.equalsIgnoreCase("page")){
			try {
				System.out.println("end element here ------------------------------ "+qName);
				//System.out.println(onlytext.toString());
				WD = getDocObject(onlytext);
				add(WD,documents);
				System.out.println(documents.size());
				//System.out.println(str);
				textstr.add(onlytext.toString());
				//System.out.println("OBJECT WORKED ----- "+ WD.getAuthor());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		else if(thisXMLTag.equals("id")){
			if(!thisXMLText.equals(""))
				thisID= Integer.parseInt(thisXMLText);
			//System.out.println(thisID);
		}
		else if(thisXMLTag.equals("timestamp")){
			thisdate=thisXMLText;
			//System.out.println(thisdate);
		}
		else if(thisXMLTag.equals("username")){
			thisAuthor=thisXMLText;
			//	System.out.println("user" +thisAuthor);
		}
		else if(thisXMLTag.equals("ip") && qName.equals("ip")){
			thisAuthor=thisXMLText;
			//	System.out.println("ip"+thisAuthor);
		}

		else if(thisXMLTag.equals("title")){
			thisTitle=thisXMLText;
			//	System.out.println(thisTitle);
		}


	}


	public WikipediaDocument getDocObject(StringBuilder onlytext2) throws ParseException, IOException{

		WikipediaParser WP = new WikipediaParser(thisID,thisdate,thisAuthor,thisTitle);
		String sectiontextstr= WP.parseSectionTitle(onlytext2.toString());
		//System.out.println(sectiontextstr+ " ----------------------- section text parsed");
		WikipediaDocument wdp = WP.getWikiObject();
		System.out.println("here in parser -----------------");
		//System.out.println(wdp.getAuthor());
		//System.out.println(wdp.getTitle());
		//System.out.println(wdp.getCategories());
		
		return wdp;

		
	}

	/**
	 * Method to add the given document to the collection.
	 * PLEASE USE THIS METHOD TO POPULATE THE COLLECTION AS YOU PARSE DOCUMENTS
	 * For better performance, add the document to the collection only after
	 * you have completely populated it, i.e., parsing is complete for that document.
	 * @param doc: The WikipediaDocument to be added
	 * @param documents: The collection of WikipediaDocuments to be added to
	 * @throws ParseException 
	 */


	private synchronized void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
	
		documents.add(doc);
		
	}

}

