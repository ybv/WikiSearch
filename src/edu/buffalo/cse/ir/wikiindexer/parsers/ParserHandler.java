package edu.buffalo.cse.ir.wikiindexer.parsers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

public class ParserHandler extends DefaultHandler{
	ArrayList<String> textstr = new ArrayList<String>();
	String thisXMLTag;
	String thisXMLText;
	String thisdate;
	String thisAuthor;
	int thisID;
	String thisTitle;
	StringBuilder onlytext;
	String textval;
	WikipediaParser WP;
	WikipediaDocument WD;
	

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
			//System.out.println("start element here --------------------------- "+thisXMLTag);
		}
	}

	public void endElement(String uri, String localName, String qName){

		if(qName.equalsIgnoreCase("page")){
			try {
				
				//System.out.println("end element here ------------------------------ "+qName);
				
				WikipediaParser WP = new WikipediaParser(thisID,thisdate,thisAuthor,thisTitle);
				Map<String, String> x = WP.ParseDriver(onlytext.toString());
				//System.out.println(sectiontextstr+ " ----------------------- section text parsed");
				//Pattern infobox = Pattern.compile("(\\{\\{(.*)\\}\\})",Pattern.DOTALL);
				for (Map.Entry entry : x.entrySet()) {
					String secTitle =entry.getKey().toString().trim();
					//System.out.println("section title (==============================):"+secTitle);
					
					WikipediaParser.parseSectionTitle(secTitle);
					String rawsectext= entry.getValue().toString().trim();
					//System.out.println("section text is :"+rawsectext);
					String listret = WikipediaParser.parseListItem(rawsectext);
					String textret = WikipediaParser.parseTextFormatting(listret);
					String tagsret = WikipediaParser.parseTagFormatting(textret);
					String tempret = WikipediaParser.parseTemplates(tagsret);
					String[] linksret = WikipediaParser.parseLinks(tempret);
					if(secTitle.equals("Default") && linksret[0].contains("{{Infobox")){
						
						//System.out.println("YES YES YES YES YES YES YES YES ");
							
						//	System.out.println("YES YES YES YES YES YES YES YES ");
							int str = linksret[0].indexOf('{');
							int end = linksret[0].indexOf('}');
							if(str>=0 && end< linksret[0].length() && str<end){
								//System.out.println(str+""+end);
							String substr = linksret[0].substring(str,end);
							linksret[0] = linksret[0].replace(substr, "");
							
							}
						
					}
					linksret[0]= linksret[0].replaceAll("\\{\\{", "");

					linksret[0]= linksret[0].replaceAll("\\}\\}", "");
					//System.out.println("in parse handler ==============");
					//System.out.println("section text is :"+linksret[0]);
					WD = WP.getWikiObject(linksret[0]);

					
				}
				Parser.documents.add(WD);
			
				textstr.add(onlytext.toString());
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
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


	public WikipediaDocument getWD()
	{
		return WD;
	}
}
