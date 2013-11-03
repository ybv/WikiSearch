package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.CAPITALIZATION)
public class CapHandler implements TokenizerRule {
	Pattern p1 = Pattern.compile("(.*)(\\s*)([A-Z]{3,})(\\s*)(.*)");
	Pattern p2 = Pattern.compile("(.*)(\\s*)(^[a-z]+[A-Z]+)(\\s*)(.*)");
	Pattern cap = Pattern.compile("(.*)(\\s*)([A-Z][a-z]+)(\\s*)(.*)");
	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			String s="";
			while (stream.hasNext()) { 
				token = stream.next(); //read next token
				//System.out.println("token"+token);
				if (token != null) {
					String[] words=token.trim().split("\\s+");
					int len =words.length;
						s = properCase(words[0]);
						if(words.length==1){
						stream.previous();
						stream.set(s);
						stream.next();
				}else{
					s = properCase(words[0]);
					for (int i =1; i<words.length; i++){
						s+=" "+words[i];
					}
					stream.previous();
					stream.set(s);
					stream.next();
				}
				
				//System.out.println("ALL TOKENS AFTER CAPITALS :   "+stream.getAllTokens().toString());
			}
			
		}stream.reset();
		}
	}
	String properCase (String inputVal) {
	    // Empty strings should be returned as-is.
		//System.out.println("inputval"+ inputVal);
	    if (inputVal.length() == 0) return "";
	    Matcher m1 = p1.matcher(inputVal);
	    while(m1.find()){
	    		return m1.group(3);
	    }
	    Matcher m2 = p2.matcher(inputVal);
	    while(m2.find()){
	    		return m2.group(3);
	    }
	    // Strings with only one character uppercased.
	   Matcher m3 = cap.matcher(inputVal);
	   if(inputVal.length()>4){
	   while(m3.find()){
		   
		   return m3.group();
	   }
	   }// Otherwise uppercase first letter, lowercase the rest.
	   
	    return inputVal.substring(0,1).toLowerCase()
	        + inputVal.substring(1).toLowerCase();
	    }
	    

}
