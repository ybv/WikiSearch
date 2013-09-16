/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.text.ParseException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nikhillo
 * This class implements Wikipedia markup processing.
 * Wikipedia markup details are presented here: http://en.wikipedia.org/wiki/Help:Wiki_markup
 * It is expected that all methods marked "todo" will be implemented by students.
 * All methods are static as the class is not expected to maintain any state.
 */
public class WikipediaParser {
	public static WikipediaDocument wdp;
	String str_to_format;
	
	
	public WikipediaParser(int thisID, String thisdate,
			String thisAuthor, String thisTitle) throws ParseException {
		wdp = new WikipediaDocument(thisID,thisdate,thisAuthor,thisTitle);

	}
	
	/* TODO */
	/**
	 * Method to parse section titles or headings.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * @param titleStr: The string to be parsed
	 * @return The parsed string with the markup removed
	 * @throws ParseException 
	 */
	

	public static String parseSectionTitle(String titleStr)  {
		 String line = titleStr;
	    

	      // Create a Pattern object
	      Pattern r = Pattern.compile("\\=\\=(.*)\\=\\=");

	      // Now create matcher object.
	      System.out.println(line);
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.groupCount() );
	         String [] temp = m.group(1).split("\\|");
	         for ( String s :temp)
	        	 	System.out.println(s.toString());
	      } else {
	         System.out.println("NO MATCH");
	      }
		return line;
	}
	
	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * @param itemText: The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		System.out.println(itemText);
		
		return itemText;
	}
	
	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		return null;
	}
	
	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String text) {
		return null;
	}
	
	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {
		return null;
	}
	
	
	/* TODO */
	/**
	 * Method to parse links and URLs.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * @param text: The text to be parsed
	 * @return An array containing two elements as follows - 
	 *  The 0th element is the parsed text as visible to the user on the page
	 *  The 1st element is the link url
	 */
	public static String[] parseLinks(String text) {
		return null;
	}


	public WikipediaDocument getWikiObject() {
		// TODO Auto-generated method stub
		return wdp;
	}


	
	
	
}
