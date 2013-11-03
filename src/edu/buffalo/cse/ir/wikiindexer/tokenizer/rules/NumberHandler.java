package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.NUMBERS)
public class NumberHandler implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) { 
				token = stream.next(); //read next token
				Pattern p1 = Pattern.compile("(\\s*)(\\d+)[,.]*(\\d+)");
				//System.out.println("token"+token);
				if (token != null) {
					Matcher m1 =p1.matcher(token);
					while(m1.find()){
				//		System.out.println("here in m1");
				//		System.out.println(m1.group());
						token= token.replaceAll(m1.group().trim(),"");
						String[] words=token.trim().split("\\s+");
						token =token.replaceAll("\\s{2,}", " ");
						stream.previous();
						if(words.length==1 && !words[0].contains("/")&& !words[0].contains("%")){
							stream.remove();
						}else{
						stream.set(token);
						stream.next();
						}
					}
				}
			}stream.reset();
		}
		
	}
}	


