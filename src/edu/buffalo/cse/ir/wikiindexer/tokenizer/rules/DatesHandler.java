package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.DATES)
public class DatesHandler implements TokenizerRule {
//craziest class I've ever written
	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		/*
		if (stream != null) {
			String token;
			boolean datntime = false;
			Pattern mid_dates =Pattern.compile("(.*[^-\\d+])(-*)(\\d{8})(.*)");
			Pattern date_n_time =Pattern.compile("(\\d{8})(\\s)((\\d\\d):(\\d\\d):(\\d\\d))");
			Pattern range_date =Pattern.compile("(\\d{8})\\u2013(\\d{8})");
			Pattern start_dates=Pattern.compile("^(\\d+)(.*)");
			Pattern time = Pattern.compile("(\\d\\d):(\\d\\d):(\\d\\d)");
			while (stream.hasNext()) { 
				token = stream.next(); //read next token
				System.out.println("token"+token);
				if (token != null) {
					Matcher rdt =range_date.matcher(token);
						while(rdt.find()){
						System.out.println(rdt.group());
						
						String[] years =rdt.group().split("\\u2013");
						String styear = years[0].substring(0,4);
						String endyear =years[1].substring(2,4);
						String newrange = styear+"\u2013"+endyear;
						System.out.println(newrange);
						String str = token.replace(rdt.group(), newrange);
						//String newtokhere = rangeleft[0]+newrange+rangeleft[1];
						System.out.println("found here in range----------------");
						System.out.println(str);
						token=str;
						
					}
					
					
					
					Matcher mdt =date_n_time.matcher(token);
					
					while(mdt.find()){//00:58:53 UTC on Sunday, 26 December 2004
						datntime=true;
						//String[] textindnt = token.split(mdt.group(0));
						String dntdateintext = mdt.group(1).substring(6, 8);
						String monthindnt =  (dntdateintext.equals("01")) ? "January" :(dntdateintext.equals("02")? "February": (dntdateintext.equals("03")? "March": (dntdateintext.equals("04")? "April":(dntdateintext.equals("05")? "May":(dntdateintext.equals("06")?"June":(dntdateintext.equals("07")?"July":(dntdateintext.equals("08")?"August":(dntdateintext.equals("09")?"September":(dntdateintext.equals("10")?"October":(dntdateintext.equals("11")?"November":"December")))))))))) ;
						
						String input_date= mdt.group(1).substring(6, 8)+"/"+mdt.group(1).substring(4,6)+"/"+mdt.group(1).substring(0,4);
						  SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
						  Date dt1;
						  String finalDay = "Sunday";
						try {
							dt1 = format1.parse(input_date);
							DateFormat format2=new SimpleDateFormat("EEEE"); 
							  finalDay=format2.format(dt1);
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
						String newdatepattern = mdt.group(3)+" "+ "UTC" +" on "+finalDay+","+dntdateintext+ " "+monthindnt+ " "+mdt.group(1).substring(0,4);
						System.out.println(newdatepattern);
						System.out.println("found");
						token=token.replace(mdt.group(0), newdatepattern);
					}
					Matcher ms =start_dates.matcher(token);
					while(ms.find()&&!datntime){//April 11 
						ms.group(1);
						if(ms.group(1).length()==8){
							String stdateintext = ms.group(1).substring(6, 8);
							String stmonthintext = ms.group(1).substring(4,6);
							if(stmonthintext.equals("01")){
								String modifiedDate =  "January" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
							}
							else if(stmonthintext.equals("02")){
								String modifiedDate =  "February" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
												}
							else if(stmonthintext.equals("03")){
								String modifiedDate =  "March" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
								
							}
							else if(stmonthintext.equals("04")){
								String modifiedDate =  "April" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
														}
							else if(stmonthintext.equals("12")){
								String modifiedDate =  "December" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
														}
							else if(stmonthintext.equals("05")){
								String modifiedDate =  "May" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
														}
							else if(stmonthintext.equals("06")){
								String modifiedDate =  "June" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
														}
							else if(stmonthintext.equals("07")){
								String modifiedDate =  "July" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
							}
							else if(stmonthintext.equals("08")){
								String modifiedDate =  "August" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
								
							}else if(stmonthintext.equals("09")){
								String modifiedDate =  "September" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
							}else if(stmonthintext.equals("10")){
								String modifiedDate =  "October" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
							}else if(stmonthintext.equals("11")){
								String modifiedDate =  "November" + " "+stdateintext.replace("^0*", "");
								String modifiedString = modifiedDate+ ms.group(2);
								token =modifiedString;
								System.out.println(modifiedDate);
								System.out.println(token);
							}
						}
						}
					
					
					Matcher m = mid_dates.matcher(token);
					while(m.find()){
						System.out.println(m.group(3));
						System.out.println(m.group(2).trim());
						boolean bornexists =m.group(1).matches(".*born.$");
						
						boolean theexists=m.group(1).matches(".*the.$");
						boolean onexists=m.group(1).matches(".*on.$");
						boolean Theexists = m.group(1).matches(".*The.$");
						if(m.group(3).length()==8){
							String dateintext = m.group(3).substring(6, 8);
							String monthintext = m.group(3).substring(4,6);
							String yearintext = m.group(3).substring(0,4);

							if(m.group(3).startsWith("0")){
								if(m.group(2).equals("-")){
									String modifiedDate = yearintext.replaceAll("0", "")+ " "+ "BC";
									String modifiedString = m.group(1)+modifiedDate+m.group(4);
									token =modifiedString;
									System.out.println(token);
								}
								else{
									String modifiedDate = yearintext.replaceAll("0", "")+ " "+ "AD";
									String modifiedString = m.group(1)+modifiedDate+m.group(4);
									token =modifiedString;
									System.out.println(token);
								}
							}
							else{
								if(monthintext.equals("01")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "January" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists||Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "January" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "January" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}


								}
								else if(monthintext.equals("02")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "February" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "February" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "February" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("03")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "March" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "March" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "March" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("04")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "April" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "April" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "April" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("12")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "December" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "December" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "December" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("05")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "May" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "May" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "May" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("06")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "June" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "June" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "June" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("07")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "July" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "July" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "July" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
								else if(monthintext.equals("08")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "August" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "August" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "August" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}else if(monthintext.equals("09")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "September" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "September" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "September" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}else if(monthintext.equals("10")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "October" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "October" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "January" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}else if(monthintext.equals("11")){
									if(bornexists){
										String modifiedDate = dateintext.replace("^0*", "") + " "+ "November" + " "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}//The 2004
									else if(theexists || Theexists){
										String modifiedDate =yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// on January 30, 1948
									else if(onexists){
										String modifiedDate =  "November" + " "+dateintext.replace("^0*", "") + ", "+ yearintext;
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}// December 7, 1941,
									else{
										String modifiedDate = "November" +" "+dateintext.replace("^0*", "") + ","+ yearintext+",";
										String modifiedString = m.group(1)+modifiedDate+m.group(4);
										token =modifiedString;
										System.out.println(modifiedDate);
										System.out.println(token);
									}
								}
							}

						}

					}
					Matcher md =time.matcher(token);
					while(md.find()&&!datntime){
						if(md.group().length()==8){
							String[] aftersplit = token.split(md.group());
							boolean abtexists =aftersplit[0].matches(".*about.$");
							boolean atexists =aftersplit[0].matches(".*at.$");
							int hrs = Integer.parseInt(md.group(1));
							int mins = Integer.parseInt(md.group(2));
							String indicator = "";
							System.out.println(hrs + " "+ mins);
							if(hrs>=12){
								hrs = hrs-12;
								if(abtexists){
									indicator ="pm";
									String modifiedTime = hrs+":"+mins+" "+ indicator;
									String newtoken = aftersplit[0]+modifiedTime+aftersplit[1];
									System.out.println(newtoken);
									token=newtoken;
								}
								else if(atexists){
									indicator="PM";
									String modifiedTime = hrs+":"+mins+indicator;
									String newtoken = aftersplit[0]+modifiedTime+aftersplit[1];
									System.out.println(newtoken);
									token=newtoken;
								}
							}
							else{
								
								if(abtexists){
									indicator ="am";
									String modifiedTime = hrs+":"+mins+" "+ indicator;
									String newtoken = aftersplit[0]+modifiedTime+aftersplit[1];
									System.out.println(newtoken);
									token=newtoken;
								}
								else if(atexists){
									indicator="AM";
									String modifiedTime = hrs+":"+mins+indicator;
									String newtoken = aftersplit[0]+modifiedTime+aftersplit[1];
									System.out.println(newtoken);
									token=newtoken;
								}
								
							}
							
			
						}
					}
						
					
					System.out.println("called prev 2" +stream.previous());
					stream.set(token);
					stream.next();
				}

			}stream.reset();

		}

*/
	}


}