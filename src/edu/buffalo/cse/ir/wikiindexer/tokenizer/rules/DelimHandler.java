package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.DELIM)
public class DelimHandler implements TokenizerRule {
	String delim;
	public DelimHandler(String string) {
	this.delim=string;
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) { 
				token = stream.next(); //read next token
				//System.out.println("token"+token);
				
				if (token != null) {
					String[] words=token.trim().split("\\"+delim);
					
					stream.previous();
					stream.set(words);
					
					stream.next();
				}
				
		}
			stream.reset();
			//System.out.println("ALL TOKENS AFTER WHITE SPAFE :   "+stream.getAllTokens().toString());
			
	}
}
}	

