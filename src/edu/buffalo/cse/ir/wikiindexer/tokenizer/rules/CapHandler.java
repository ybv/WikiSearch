package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.CAPITALIZATION)
public class CapHandler implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if(stream!=null){
			String token;
			StringBuffer sb = new StringBuffer();
			while(stream.hasNext()){
				token =stream.next();
				if(token!=null){
					for (char c : token.toCharArray()){
						System.out.println("character value "+ c);
					}
				}
			}
		}
		
	}


}
