/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * @author nikhillo
 *
 */
public class Parser extends DefaultHandler{
	/* */
	private final Properties props;
	
	Collection<WikipediaDocument> documents;
	WikipediaParser WP;
	String thisTag;
	String thisText;
	
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
		documents=docs;
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
		
		   thisText = new String(buffer, start, length);
		   
		//System.out.println(buffer);

	}
	public void startElement( String uri, String localName, String qName, Attributes att) {
		thisTag=qName;
		

	}
	public void endElement(String uri, String localName, String qName){
	
			System.out.println(thisText);
			//System.out.println(uri);
			///System.out.println(qName);
			//System.out.println(temp);
		

	}
	/**
	 * Method to add the given document to the collection.
	 * PLEASE USE THIS METHOD TO POPULATE THE COLLECTION AS YOU PARSE DOCUMENTS
	 * For better performance, add the document to the collection only after
	 * you have completely populated it, i.e., parsing is complete for that document.
	 * @param doc: The WikipediaDocument to be added
	 * @param documents: The collection of WikipediaDocuments to be added to
	 */
	private synchronized void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
		documents.add(doc);
	}
}
