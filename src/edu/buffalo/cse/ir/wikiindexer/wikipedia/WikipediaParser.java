/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.io.UnsupportedEncodingException;
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
	static final Pattern secTitlePattern = Pattern.compile("(((^\\=\\=)(.+)(\\=\\=$)(\n)(.+\\s*)[^\\=\\=$]*))",Pattern.MULTILINE);
	static final Pattern linkPattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
	static final Pattern boldPattern= Pattern.compile("(\\'\\'\\')(.*?)(\\'\\'\\')");
	static final Pattern italicPattern= Pattern.compile("(\\'\\')([^\'].*?)(\\'\\')");
	static final Pattern bolditalicPattern= Pattern.compile("(\\'\\'\\'\\'\\')(.*?)(\\'\\'\\'\\'\\')");
	static final Pattern tagFormatPattern = Pattern.compile("");
	static final Pattern listItemPattern = Pattern.compile("((\\*+) (.+))");
	static final Pattern templatePattern = Pattern.compile("(\\{\\{)(.*?)(\\}\\})");
	static String secTitle ="";
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

		String secText ="";
		//change
		String text ="";
		text = titleStr.replaceAll("&gt;", ">");
		text = text.replaceAll("&lt;", "<");
		text = text.replaceAll("<ref>.*?</ref>", " ");
		text = text.replaceAll("\\{\\{cit.*?\\}\\}"," ");
		text = text.replaceAll("</?.*?>", " ");
		Matcher m = secTitlePattern.matcher(text);
		String[] all_sections;
		int count=0;
		while(m.find()) {
			count +=1;
			secText = m.group(1);
			all_sections = secText.trim().split("\\=\\=");
			secTitle=all_sections[1].replaceAll("\\=", " ");
			System.out.println(secTitle+ " is the section title");
			//System.out.println("---------------------");
			//System.out.println(" and the section text is " + secText);
			//wdp.addSection(secTitle, secText);
			//return temp;
			WikipediaParser.parseListItem(secText);

		} 

		System.out.println("removed all section mark up and number of sections = " +count);




		return "snf";
	}

	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * @param itemText: The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		Matcher m = listItemPattern.matcher(itemText);
		String temp ="";
		int count=0;
		while(m.find()) {
			count +=1;
			temp = m.group(3);
			temp = temp.replaceAll("\\'+", "");
			temp = temp.replaceAll("\"+", "");
			temp = temp.replaceAll("\\[\\[(.*?)\\]\\]", "$1");
			System.out.println(temp);
			return temp;
		} 
		System.out.println("------------ removed all list item mark up and number of list items = " +count);

		WikipediaParser.parseTextFormatting(temp);
		return "snf";

	}

	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		Matcher m1 = boldPattern.matcher(text);
		Matcher m2 = italicPattern.matcher(text);
		Matcher m3 = bolditalicPattern.matcher(text);
		String temp ="";
		int count=0;
		while(m1.find()) {
			temp = m1.group(2);
			//System.out.println(m1.groupCount());
			System.out.println(temp.toString());
			count++;
		}
		while(m2.find()) {
			temp = m2.group(2);
			//System.out.println(m2.groupCount());
			System.out.println(temp.toString());
			count++;
		}
		while(m3.find()) {
			temp = m3.group(2);
			//System.out.println(m2.groupCount());
			System.out.println(temp.toString());
			count++;
		}

		//System.out.println("----------- removed all the bold and italics markup with count ="+count);
		//WikipediaParser.parseTagFormatting(text);
		return temp;
	}

	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	/*	public static String parseTagFormatting(String text) {
		WikipediaParser.parseTemplates(text);

		WikipediaParser.parseLinks(text);
		return null;
	}
	 */
	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	/*	public static String parseTemplates(String text) {
		Matcher m = templatePattern.matcher(text);
		String temp ="";
		String[] all_sections;
		int count=0;
		while(m.find()) {
			count +=1;
			temp = m.group(2);

			System.out.println(temp);
			//return temp;
		} 
		System.out.println("removed all template mark up and number of sections = " +count);




		return null;
	}

	 */
	/* TODO */
	/**
	 * Method to parse links and URLs.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * @param text: The text to be parsed
	 * @return An array containing two elements as follows - 
	 *  The 0th element is the parsed text as visible to the user on the page
	 *  The 1st element is the link url
	 */
	/*	public static String[] parseLinks(String text) {

		Matcher m = linkPattern.matcher(text);
		int count=0;
		while(m.find()) {
			String [] temp = m.group(1).split("\\|");
			System.out.println(temp[0].toString());
			count++;
		}
		System.out.println("removed all the links markup with link count ="+count);
		return null;
	}

	 */
	public WikipediaDocument getWikiObject() {
		// TODO Auto-generated method stub
		return wdp;
	}





}
