package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.ACCENTS)
public class AccentHandler implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		if (stream != null) {
			String token;
			String string;
			//Pattern p1 = Pattern.compile("^([a-zA-Z])+([A-Z]{1,})([a-z])+$",Pattern.MULTILINE);
			while (stream.hasNext()) { 
				token = stream.next(); //read next token
			//	System.out.println("token"+token);

				if (token != null) {
					string = Normalizer.normalize(token, Normalizer.Form.NFD);
					//System.out.println("string is"+ string);
					string = string.replaceAll("[^\\p{ASCII}]", "");
					//System.out.println("string is"+ string);
					int len = string.length();
					if(len>1)
					string = string.charAt(0) + string.substring(1,string.length()).toLowerCase();
					
				//	Matcher m1 = p1.matcher(string);
					//while(m1.find()){
						
					
					
					stream.previous();
					stream.set(string);
					stream.next();
					
				}
		}stream.reset();
	}


}
}