/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;

/**
 * This class implements Wikipedia markup processing.
 * Wikipedia markup details are presented here: http://en.wikipedia.org/wiki/Help:Wiki_markup
 * It is expected that all methods marked "todo" will be implemented by students.
 * All methods are static as the class is not expected to maintain any state.
 */

public class WikipediaParser {
	public static WikipediaDocument wdp;
	String str_to_format;
	static final Pattern httplinkPattern = Pattern.compile("\\[http://(.*)\\]",Pattern.MULTILINE);
	static final Pattern info =Pattern.compile("(\\{\\{)(.*?)(\\}\\})");
	static final Pattern titlePattern = Pattern.compile("((^\\=\\=)(.+)(\\=\\=$)(\n)(.+\\s*)[^\\=*]*)",Pattern.MULTILINE);
	static final Pattern secTitlePattern = Pattern.compile("(\\=\\=)(.+)(\\=\\=)(\n)(.*[^(\\=\\=)])",Pattern.DOTALL);
	static final Pattern linkPattern = Pattern.compile("(\\[\\[(.*?)\\]\\])");
	static final Pattern linkwithtextinendPattern = Pattern.compile("(\\[\\[(.*)\\]\\](.+))");
	static final Pattern WikipedialinkPattern = Pattern.compile("(\\[\\[(Wikipedia:(.+))\\]\\])");
	static final Pattern WiktionaryPattern = Pattern.compile("\\[\\[(Wiktionary:(.+))\\]\\]");
	static final Pattern boldPattern= Pattern.compile("(\\'\\'\\')(.*?)(\\'\\'\\')");
	static final Pattern italicPattern= Pattern.compile("(\\'\\')(.*?)(\\'\\')");
	static final Pattern bolditalicPattern= Pattern.compile("(\\'\\'\\'\\'\\')(.*?)(\\'\\'\\'\\'\\')");
	static final Pattern liststarItemPattern = Pattern.compile("((\\*+) (.+))");
	static final Pattern listhashItemPattern = Pattern.compile("((\\#+) (.+))");
	static final Pattern defItemPattern = Pattern.compile("((\\:+) (.+))");
	static final Pattern templatePattern = Pattern.compile("(?m)\\{\\{[^\\{]+?\\}\\}",Pattern.MULTILINE);
	static final Pattern template2Pattern = Pattern.compile("(?m)\\{\\{(.*)?\\}\\}",Pattern.MULTILINE);
	static final Pattern infoboxPattern = Pattern.compile("(?m)\\{\\{Infobox(\\s)(.*)\\}\\}$",Pattern.MULTILINE);
			
	static final Pattern temprempat = Pattern.compile("\\w+\\]\\]");
	static final Pattern catPattern = Pattern.compile("\\[\\[Category:(.*?)\\]\\]", Pattern.MULTILINE);
	static final Pattern remtempPat = Pattern.compile("");
	public static String secTitle ="";
	public static ArrayList<String> linkcoll = new ArrayList<String>();
	public static ArrayList<String> catcoll = new ArrayList<String>();
	public static StringBuffer aftlink = new StringBuffer();

	static final Pattern equalsMatch = Pattern.compile("={2,5}([^=].*?)={2,5}");
	static boolean titleexists =false;

	static final Map<String, String> titleandtext = new HashMap<String, String>();

	public WikipediaParser(int thisID, String thisdate,
			String thisAuthor, String thisTitle) throws ParseException {
		wdp = new WikipediaDocument(thisID,thisdate,thisAuthor,thisTitle);

	}

	public static Map<String, String> ParseDriver(String string) {
		int sec_no = 0;
		Map<String, String> maptitletext = new HashMap<String, String>();
		ArrayList<Integer> startIndeces = new ArrayList<Integer>();
		ArrayList<Integer> endIndeces = new ArrayList<Integer>();
		ArrayList<String> AllsecTitles = new ArrayList<String>();
		Matcher cmatcher = catPattern.matcher(string);
		while(cmatcher.find()) {
			//System.out.println(" IN CAT MATCHER ");
			String [] temp = cmatcher.group(1).split("\\|");
			//System.out.println(temp[0]);
			catcoll.add(temp[0]);

		}
		Matcher m = equalsMatch.matcher(string);
		while(m.find()) {
			//System.out.println(m.group(1));
			AllsecTitles.add(m.group(1));
			//System.out.println(m.start());
			//System.out.println(m.end());
			startIndeces.add(m.start());
			endIndeces.add(m.end());
			sec_no++;
		} 
		startIndeces.add(string.length());
		String initDefText = string.substring(0,startIndeces.get(0));

		maptitletext.put("Default", initDefText);

		for(int i=0;i<sec_no;i++){

			String text = string.substring(endIndeces.get(i), startIndeces.get(i+1)-1);

			maptitletext.put(AllsecTitles.get(i),text);

		}


		return maptitletext;
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
		String retsecTitle ="";
		//System.out.println(titleStr.trim());
		if(titleStr!=null){
			Matcher m = equalsMatch.matcher(titleStr);
			while(m.find()){
				retsecTitle = m.group(1).trim();
				//System.out.println(retsecTitle);
			}
		}
		secTitle = titleStr;
		if(!retsecTitle.equals("")){
			//System.out.println("the section title is " +secTitle);

			return retsecTitle;
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
		if(!listtemp.equals("")){

			return listtemp;
		}

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
		//System.out.println("String coming into text formating"+ text);

		//System.out.println("-------------------------");
		//System.out.println(secTitle+"   is the section title and the text that is being parsed at format level is ");
		//System.out.println(text);
		if(text!=null){
			Matcher m1 = boldPattern.matcher(text);
			Matcher m2 = italicPattern.matcher(text);
			Matcher m3 = bolditalicPattern.matcher(text);
			textemp=text;
			int count=0;
			while(m1.find()) {
				textemp =textemp.replaceAll("(\\'\\'\\')(.*?)(\\'\\'\\')", "$2");
				//System.out.println(m1.groupCount());
				//System.out.println(textemp.toString());
				count++;
			}
			while(m2.find()) {
				textemp =textemp.replaceAll("(\\'\\')(.*?)(\\'\\')","$2");
				//System.out.println(m2.groupCount());
				//System.out.println(textemp.toString());
				count++;
			}
			while(m3.find()) {
				textemp = textemp.replaceAll("(\\'\\'\\'\\'\\')(.*?)(\\'\\'\\'\\'\\')", "$2");
				//System.out.println(m2.groupCount());
				//System.out.println(textemp.toString());
				count++;
			}

		}
		//System.out.println("-------------------------");
		//System.out.println("String going out of text formating"+ textemp);
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
			text = text.replaceAll("&gt;", ">").trim();
			text = text.replaceAll("&lt;", "<").trim();
			text = text.replaceAll("<.*>(.*?)<.*>", "$1").trim();
			text =text.replaceAll("<.*>", "").trim();
			text =text.replaceAll("\\s{2,}", " ").trim();;
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
			
			//System.out.println("text coming into parse templates "+ text);

			Matcher mt = templatePattern.matcher(text);
			while(mt.find()){
				//System.out.println("1st :"+mt.group());
				text = text.replace(mt.group(), "");
			
			}
			
		
			
	
			//System.out.println("text coming out of parse templates "+ text);
		}
		return text;
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

		//System.out.println("section text before going in links in links"+ text);
		
		String[] emptArray = {"",""};
		String[] returnArr = new String[] {"",""};

		if(text==null || text.equals("")){
			return emptArray;
		}
		Matcher m = linkPattern.matcher(text);
		text = text.replaceAll("<nowiki />","");
		Matcher mhttp = httplinkPattern.matcher(text);
			while(mhttp.find()){
				String [] httpsplits = mhttp.group(1).split(" ");
				if(httpsplits.length>=2){
					text =text.replace(mhttp.group(),httpsplits[1].trim());
					returnArr[1]= "";
					returnArr[0]=text;
					
				}else{
					text =text.replace(mhttp.group(),"");
					returnArr[1]= "";
					returnArr[0]="";
				}
		}
		while(m.find()) {
			//System.out.println(m.group());
			if(m.group().contains("|")){


				if(m.group(2)!=null && m.group(2)!=""){
					if(m.group(2).endsWith("|")){
						if(m.group(2).startsWith("Wikipedia:")){
							Matcher mw = WikipedialinkPattern.matcher(text);


							while(mw.find()){
								if(mw.group().contains("#")){
									String text2 ="";

									text2= mw.group(2).replace("|", " ").trim();
									text =text.replace(m.group(),text2.trim());
									returnArr[1]= "";
									returnArr[0]=text;
								}else{
									String text2 ="";
									//System.out.println(m.group(2));
									text2= mw.group(2).replace("|", " ").trim();
									text2 = text2.replace("Wikipedia:", "");
									if(text2.contains("(")){
										int i= text2.indexOf("(");
										text2=text2.substring(0,i-1);
									}

									text =text.replace(m.group(),text2.trim());
									returnArr[1]= "";
									returnArr[0]=text;
								}
							}
						}else if(m.group(2).startsWith("Wiktionary:")){
							Matcher mwt = WiktionaryPattern.matcher(text);
							while(mwt.find()){
								String text2 =mwt.group(2);
								text2 = text2.replace("|","");
								//System.out.println(text2);
								text =text.replace(m.group(),text2.trim());
								returnArr[1]="";
								returnArr[0]=text;
							}

						}

						else{
							String[] biosplits = m.group(2).trim().split(" ");
							String kingtext;
							kingtext=biosplits[0].substring(0,1).toUpperCase()+biosplits[0].substring(1);
							biosplits[0]=biosplits[0].replace(",","").trim();
							String _sum = "";
							//System.out.println(biosplits[1]);
							_sum= kingtext+"_"+biosplits[1].replace("|", "");
							//System.out.println("1"+_sum);
							//System.out.println("0"+biosplits[0]);
							text =text.replace(m.group(),biosplits[0].trim());
							returnArr[1]= _sum;
							returnArr[0]=text;
							if(returnArr[1]!=null){
								linkcoll.add(returnArr[1]);
							}
						}	
					}else{
						if(m.group(2).startsWith("media:")){
							//System.out.println(m.group(2));
							String[] splitlink = m.group(2).trim().split("\\|");
						
							if(splitlink[1]!=null && !splitlink[1].equals("")){
								linkcoll.add(splitlink[1]);
							}
							text =text.replace(m.group(),splitlink[0].trim());
							returnArr[1]="";
							returnArr[0]=splitlink[1];
						}
						else if(m.group(2).startsWith("File:")){
							//System.out.println("here "+m.group(2));
							String [] filesplits= m.group(2).split("\\|");
							if(filesplits.length>2){
								text =text.replace(m.group(),filesplits[2].trim());
								returnArr[1]="";
								returnArr[0]=filesplits[filesplits.length-1];
							}
						}
						else{
							//System.out.println(" | at the middle ");
							String [] afterpipe = m.group(2).split("\\|");
							String linktext = afterpipe[0];
							//System.out.println("link is "+linktext);
							String trimmed = linktext.trim();
							int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
							if(words==1){//just one word in link

								if(linktext!=null){
									linkcoll.add(linktext);
								}
								text =text.replace(m.group(),afterpipe[1].trim());
								returnArr[1]= linktext;
								returnArr[0]=text;
							}
							else{//adding _ between links 
							//	System.out.println("coming to else");
								afterpipe[0]=afterpipe[0].substring(0,1).toUpperCase()+afterpipe[0].substring(1);
								String[] afterpipesplits =afterpipe[0].split(" ");
								String sum = "";
								for(int i=0; i<afterpipesplits.length-1;i++){
									sum = afterpipesplits[i]+"_"+afterpipesplits[i+1];
								}
								if(linktext!=null){
									linkcoll.add(linktext);
								}
								text =text.replace(m.group(),afterpipe[1].trim());
								returnArr[1]= sum;
								returnArr[0]=text;
							}
						}
					}


				}
			}
			else{
				if(m.group(2)!=null){
					if(m.group(2).startsWith("Wiktionary:")){
						//System.out.println("wiktionary");
						Matcher mwt = WiktionaryPattern.matcher(text);
						while(mwt.find()){
							String text2 =mwt.group(1);
						//	System.out.println(text2);
							text =text.replace(m.group(),text2.trim());
							returnArr[1]="";
							returnArr[0]=text;
						}
					}
					else if(m.group(2).startsWith("File:")){
						//System.out.println("here"+m.group(2));
						returnArr[1]="";
						returnArr[0]="";
					}
					else{

						if(m.group(2).endsWith("-")){
							String cleanlink = m.group(2);
							cleanlink=cleanlink.substring(0,1).toUpperCase()+cleanlink.substring(1);
							text = text.replace(m.group(), m.group(2));
							returnArr[1]=cleanlink;
							returnArr[0]=text;
							if(returnArr[1]!=null){
								linkcoll.add(returnArr[1]);
							}

						}else if (m.group(2).startsWith("Category:")){

							String cleancat = m.group(2);
							//System.out.println(cleancat);
							String text2 = cleancat.replace("Category:", "");
							text = text.replace(m.group(), text2);
						//	System.out.println("text2"+text2);
							catcoll.add(text2);
							returnArr[1]="";
							returnArr[0]=text;
						}else if(m.group(2).startsWith(":Category:")){

							String cleancat = m.group(2);
						//	System.out.println(cleancat);
							String text2 = cleancat.replaceFirst(":", "");
							text = text.replace(m.group(), text2);
						//	System.out.println("text2"+text2);
							returnArr[1]="";
							returnArr[0]=text;

						}
						else{
							if(m.group(2).matches("\\w{2}\\:.*")){
								text = text.replace(m.group(), m.group(2));
								returnArr[0]=text;
								returnArr[1]="";
							}else{
						//		System.out.println("in else"+m.group(2));
								String intext =m.group(2).trim();
								String[] _links = intext.split(" ");
								for(int i= 0;i<_links.length-1;i++){
									if(i == 0){
										_links[i]=_links[i].substring(0,1).toUpperCase()+_links[i].substring(1);
									}
									if(i!=_links.length){
										_links[i]+="_";
									}

								}
								String _sum = "";
								for(String s : _links)
									_sum+=s;
								returnArr[1]=_sum;
								text = text.replace(m.group(), intext);
								returnArr[0]=text;
								if(returnArr[1]!=null){
									linkcoll.add(returnArr[1]);
								}
							}
						}
					}
				}
			}
		}
		if(returnArr[0].equals("")&& !text.startsWith("[[File:")){
			returnArr[0]=text;
		}
		//System.out.println("section text  after going out of links in links"+ text);

		
		return returnArr;
	}


	public WikipediaDocument getWikiObject(String parsecomplete) {
		
		if(!secTitle.contains("External link"))
				wdp.addSection(secTitle, parsecomplete);
		wdp.addCategories(catcoll);
		Iterator<String> i = linkcoll.iterator();
		while(i.hasNext()){
			wdp.addLink(i.next());
		}
		
		catcoll.clear();
		//System.out.println("SIZE OF LINK COLL "+ linkcoll.size());
		linkcoll.clear();
		
		return wdp;
	}



}
