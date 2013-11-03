
package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.PUNCTUATION)
public class PunctuationHandler implements TokenizerRule {

	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) { 
				token = stream.next();
				if (token != null) {	
					if(!isIp(token) && !startsWSp(token) &&!midWSp(token)){
							//System.out.println(token);
							token=token.replaceAll("[^a-zA-Z0-9\\s]", "");
						}
					stream.previous();
					stream.set(token);
					stream.next();

				}
			}
			
			
		}
		stream.reset();
	}
	/**
	 * Method to check if the given token is purely alphabetic in nature
	 * @param token: The token to be checked
	 * @return true if consists only of characters, false otherwise
	 */
	private boolean isIp(String token) {
		Pattern p2 = Pattern.compile("^(\\d+)(\\.)(\\d+)(\\.)(\\d+)(\\.)(\\d+)$",Pattern.MULTILINE);
		Matcher m3 = p2.matcher(token);
		boolean t = false;
		while(m3.find()){
			t = true;
		}
		//System.out.println(t+"in ip matcher");
		return t;
	}
	private boolean startsWSp(String token){
		Pattern p2 = Pattern.compile("^[^a-zA-Z0-9\\s]",Pattern.MULTILINE);
		Matcher m3 = p2.matcher(token);
		boolean t = false;
		while(m3.find()){
			t = true;

		}
		return t;
	}
	private boolean midWSp(String token){
		Pattern p2 = Pattern.compile("^\\w+?[^a-zA-Z0-9]\\w+$",Pattern.MULTILINE);
		Matcher m3 = p2.matcher(token);
		boolean t = false;
		while(m3.find()){
			t = true;

		}
		return t;
	}




}
