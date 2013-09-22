/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
	static final Pattern titlePattern = Pattern.compile("(((^\\=\\=)(.*?)(\\=\\=$)(\n)*(.+\\s*)[^\\=\\=$]))",Pattern.MULTILINE);
	static final Pattern secTitlePattern = Pattern.compile("(((^\\=\\=)(.+)(\\=\\=$)(\n)(.+\\s*)[^\\=\\=$]*))",Pattern.MULTILINE);
	static final Pattern linkPattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
	static final Pattern boldPattern= Pattern.compile("(\\'\\'\\')(.*?)(\\'\\'\\')");
	static final Pattern italicPattern= Pattern.compile("(\\'\\')(.*?)(\\'\\')");
	static final Pattern bolditalicPattern= Pattern.compile("(\\'\\'\\'\\'\\')(.*?)(\\'\\'\\'\\'\\')");
	static final Pattern liststarItemPattern = Pattern.compile("((\\*+) (.+))");
	static final Pattern listhashItemPattern = Pattern.compile("((\\#+) (.+))");
	static final Pattern defItemPattern = Pattern.compile("((\\:+) (.+))");
	static final Pattern templatePattern = Pattern.compile("(\\{\\{)(.*?)(\\}\\})");
	static String secTitle ="";
	
	static final Map<String, String> titleandtext = new HashMap<String, String>();
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
		
		System.out.println(titleStr.trim());
		if(titleStr!=null){
			String[] parts = titleStr.split("\\==+");
			for(int i=1;i<parts.length;i+=2){
				if(parts[i].trim().equals("")){
					titleandtext.put("Default", parts[i+1]);
					System.out.println(parts[i]);
				}
				try{
				titleandtext.put(parts[i], parts[i+1]);
				}catch(IndexOutOfBoundsException e){
					titleandtext.put(parts[i], "");
				}
				}
			for (Map.Entry entry : titleandtext.entrySet()) {
				secTitle =entry.getKey().toString().trim();
				secText= entry.getValue().toString();
				//System.out.println("section text being sent is"+ secText);
				//WikipediaParser.parseListItem(secText);
			}
			System.out.println("comes here");
		}
		if(!secTitle.equals("")){
			System.out.println("the section title is " +secTitle);
			return secTitle;
		}
		return titleStr;
	}

	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * @param itemText: The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		String listtemp ="";
		System.out.println(secTitle+"   is the section title and the text that is being parsed at item level is ");
		System.out.println(itemText);
		if(itemText!=null){
			Matcher m = liststarItemPattern.matcher(itemText);
			Matcher m2 = listhashItemPattern.matcher(itemText);
			Matcher m3 = defItemPattern.matcher(itemText);
			int count=0;
			while(m.find()) {
				count +=1;
				listtemp+= m.group(3);
				listtemp = listtemp.replaceAll("\\'+", "");
				listtemp = listtemp.replaceAll("\"+", "");
				listtemp = listtemp.replaceAll("\\[\\[(.*?)\\]\\]", "$1");
				//System.out.println(temp);

			} 
			while(m2.find()) {
				count +=1;
				listtemp+= m2.group(3);
				listtemp = listtemp.replaceAll("\\'+", "");
				listtemp = listtemp.replaceAll("\"+", "");
				listtemp = listtemp.replaceAll("\\[\\[(.*?)\\]\\]", "$1");
				//System.out.println(temp);

			} 
			while(m3.find()) {
				count +=1;
				listtemp+= m3.group(3);
				listtemp = listtemp.replaceAll("\\'+", "");
				listtemp = listtemp.replaceAll("\"+", "");
				listtemp = listtemp.replaceAll("\\[\\[(.*?)\\]\\]", "$1");
				//System.out.println(temp);

			} 

		}
		//	System.out.println("------------ removed all list item mark up and number of list items = " +count);
		System.out.println("------------------parsing list markup ---------------------------");
		System.out.println(listtemp);
		titleandtext.put(secTitle, listtemp);
		System.out.println("-----------------finished parsing list markup---------------------");

		
		if(!listtemp.equals("")){
			WikipediaParser.parseTextFormatting(listtemp);
			return listtemp;
		}
		WikipediaParser.parseTextFormatting(itemText);
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
		String textemp ="";
		System.out.println(secTitle+"   is the section title and the text that is being parsed at format level is ");
		System.out.println(text);
		if(text!=null){
			Matcher m1 = boldPattern.matcher(text);
			Matcher m2 = italicPattern.matcher(text);
			Matcher m3 = bolditalicPattern.matcher(text);
			textemp=text;
			int count=0;
			while(m1.find()) {
				textemp =textemp.replaceAll("(\\'\\'\\')(.*?)(\\'\\'\\')", "$2");
				//System.out.println(m1.groupCount());
				System.out.println(textemp.toString());
				count++;
			}
			while(m2.find()) {
				textemp =textemp.replaceAll("(\\'\\')(.*?)(\\'\\')","$2");
				//System.out.println(m2.groupCount());
				System.out.println(textemp.toString());
				count++;
			}
			while(m3.find()) {
				textemp = textemp.replaceAll("(\\'\\'\\'\\'\\')(.*?)(\\'\\'\\'\\'\\')", "$2");
				//System.out.println(m2.groupCount());
				System.out.println(textemp.toString());
				count++;
			}
			
		}
		
		System.out.println("------------------parsing text formatting markup ---------------------------");
		System.out.println(textemp);
		titleandtext.put(secTitle, textemp);
		System.out.println("-----------------finished text formatting  markup---------------------");
		//System.out.println("----------- removed all the bold and italics markup with count ="+count);
		WikipediaParser.parseTagFormatting(textemp);
		if(!textemp.equals("")){
			return textemp;
		}
		return text;
	}

	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String text) {
		if(text!=null){
			text = text.replaceAll("&gt;", ">");
			text = text.replaceAll("&lt;", "<");
			text = text.replaceAll("<.*>(.*?)<.*>", "$1").trim();
			text =text.replaceAll("<.*>", "");
			System.out.println("------------------parsing tag markup ---------------------------");
			System.out.println(text);
			titleandtext.put(secTitle, text);
			System.out.println("-----------------finished tag markup---------------------");
			WikipediaParser.parseTemplates(text);
			
		}
		
		return text;
	}

	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {
		if(text!=null){
			text = text.replaceAll("\\{\\{(.*?)\\}\\}","$1");
			System.out.println("------------- parsing templates now --------------------");
			System.out.println(text);
			titleandtext.put(secTitle, text);
			System.out.println("---------------parsed templates--------------------------");
			WikipediaParser.parseLinks(text);
		}
		
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
		String textemp ="";
		System.out.println(secTitle+"   is the section title and the text that is being parsed at link level is ");
		System.out.println(text);
		Matcher m = linkPattern.matcher(text);
		int count=0;
		String [] temp = {"",""};	
		while(m.find()) {
			temp = m.group(1).split("\\|");
			System.out.println(temp[0].toString());
			count++;
		}
		System.out.println("------------- parsing links now --------------------");
		System.out.println(temp[0]);
		System.out.println("---------------parsed links--------------------------");
		return null;
	}


	public WikipediaDocument getWikiObject() {
		// TODO Auto-generated method stub
		String Text="";
		for (Map.Entry entry : titleandtext.entrySet()) {
			secTitle =entry.getKey().toString().trim();
			Text= entry.getValue().toString().trim();
			//System.out.println(secTitle+ " is the section "); 
			 //System.out.println(Text + "is the text");
		}
		return wdp;
	}





}
