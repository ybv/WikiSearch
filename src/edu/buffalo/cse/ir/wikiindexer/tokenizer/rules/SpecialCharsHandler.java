package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Normalizer;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.SPECIALCHARS)
public class SpecialCharsHandler implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		if (stream != null) {
			String token;
			while (stream.hasNext()) { 
				token = stream.next(); //read next token
				//System.out.println("token "+token);
				if (token != null) {
					if(token.contains("@")){
						String[] words = token.split("@");
						if(words.length>1){
							stream.previous();

							stream.set(words);
							stream.next();

						}

					}else if (token.contains("^")	){
						
						String[] words = token.split("\\^");

						stream.previous();
						stream.set(words);
						stream.next();

					}
					else if(token.contains("*")){
						//System.out.println("here "+token);
						String[] words = token.split("\\*");
						stream.previous();
						stream.set(words);
						stream.next();
					}
					else{
						token = token.trim();
						//token.equals("=")||token.equals("&")||token.equals("+")||token.equals("|")||token.equals("<")||token.equals(">")
						if(token.matches("[^A-Za-z0-9\\-\\.]+"))
						{
							stream.previous();
							stream.remove();
							//stream.next();
						}
						else{
							//System.out.println("token in else is "+ token);
							token= token.replaceAll("[^A-Za-z0-9\\-\\.]", "");
							token = token.replaceAll("=","");
							stream.previous();
							stream.set(token);
							stream.next();
						}
					}

				}
			}
			stream.reset();
		//	System.out.println("IN SPECIAL CHARS"+stream.getAllTokens());
		}
	}
}