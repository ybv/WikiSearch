package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.APOSTROPHE)
public class ApostropheHandler implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// (?=.*\w)^(\w|')+$

		if (stream != null) {
			String token;

			while (stream.hasNext()) { 
				token = stream.next(); //read next token
				//"token"+token);

				if (token != null) {
					int apindex = token.indexOf("\'");
					if(apindex>=0){
						if(token.equals("'em")){
							
							token="them";
							stream.previous();
							stream.set(token);
							stream.next();
						}
						if(token.equals("df'/dx")){
							
							token="df/dx";
							stream.previous();
							stream.set(token);
							stream.next();
						}
						if(token.equals("f''(x)")){
							
							token="f(x)";
							stream.previous();
							stream.set(token);
							stream.next();
						}

						if(token.startsWith("\'")){
							token =token.replaceFirst("\'", "").trim();
							stream.previous();
							stream.set(token);
							stream.next();
						}
						else if(token.trim().endsWith("\'")){
							//"--------------------------------------------");
							token =token.replaceFirst("\'", "").trim();
							stream.previous();
							stream.set(token);
							stream.next();

						}
						else if(token.trim().endsWith("\''")){
							//"--------------------------------------------");
							token =token.replaceAll("\''", "").trim();
							stream.previous();
							stream.set(token);
							stream.next();

						}
						else{
							if(token.charAt(apindex+1)=='('){
								//"--------------------------------------------");
								token =token.replaceAll("\'", "");
								token =token.replaceAll("\''", "");
								stream.previous();
								stream.set(token);
								stream.next();
							}

							String initpart =token.substring(0,apindex);
							String laterpart = token.substring(apindex+1,token.length());
							if(laterpart.trim().equals("s")){
								if(initpart.length()<=3){
									token =initpart;
									stream.previous();
									stream.set(initpart,"us");
									stream.next();
								}else{
								token =initpart;
								stream.previous();
								stream.set(token);
								stream.next();
								}
							}
							if(laterpart.trim().length()>3){
								token =initpart+""+laterpart;
								stream.previous();
								stream.set(token);
								stream.next();
							}
							if(laterpart.equals("t")){
								if(initpart.equals("isn")){
									token="is";
									stream.previous();
									stream.set(token,"not");
									stream.next();
								}
								if(initpart.equals("don")){
									token="do";
									stream.previous();
									stream.set(token,"not");
									stream.next();
								}
								if(initpart.equals("won")){
									token="will";
									stream.previous();
									stream.set(token,"not");
									stream.next();
								}
								if(initpart.equals("shan")){
									token="shall";
									stream.previous();
									stream.set(token,"not");
									stream.next();
								}

							}
							if(laterpart.equals("m")){
								token="I";
								stream.previous();
								stream.set(token,"am");
								stream.next();
							}
							if(laterpart.equals("re")){
								if(initpart.contains("w")){
									token="we";
									stream.previous();
									stream.set(token,"are");
									stream.next();
								}
								if(initpart.contains("t")){
									token="they";
									stream.previous();
									stream.set(token,"are");
									stream.next();
								}
							}
							if(laterpart.equals("ve")){
								if(initpart.contains("I")){
									token="I";
									stream.previous();
									stream.set(token,"have");
									stream.next();
								}
								if(initpart.contains("S")){
									token="Should";
									stream.previous();
									stream.set(token,"have");
									stream.next();
								}

							}
							if(laterpart.equals("ll")){
								if(initpart.contains("S")){
									token="She";
									stream.previous();
									stream.set(token,"will");
									stream.next();
								}

							}
							if(laterpart.equals("em")){
								if(initpart.contains("P")){
									token="Put";
									stream.previous();
									stream.set(token,"them");
									stream.next();
								}

							}
							if(laterpart.equals("d")){
								if(initpart.contains("T")||initpart.contains("t")){
									token="They";
									stream.previous();
									stream.set(token,"would");
									stream.next();
								}

							}
							

							}

						
					}
					
					}



				}stream.reset();

			}
		}

	}


