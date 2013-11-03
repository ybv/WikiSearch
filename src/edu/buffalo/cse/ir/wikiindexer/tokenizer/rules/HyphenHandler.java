package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


//example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.HYPHEN)
public class HyphenHandler implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			Pattern p1 =Pattern.compile("[\\d+]-[\\d+]");
			Pattern p2 = Pattern.compile("[a-zA-Z]-(\\w+)\\d{1,}");
			Pattern p3 =Pattern.compile("[\\d+]-[a-zA-Z]");
			Pattern p4 =Pattern.compile("[a-zA-Z]-[\\d+]");
			Pattern p5 = Pattern.compile("^(\\w+)-(\\w+[^0-9])$",Pattern.MULTILINE);
			Pattern p6 = Pattern.compile("^(\\s+)(-+)(\\s+)$",Pattern.MULTILINE);
			Pattern p7 = Pattern.compile("^(\\w+)(-+)$|^(-+)(\\w+)$",Pattern.MULTILINE);
			while (stream.hasNext()) { 	
				token = stream.next(); 
				//System.out.println("token"+token);
				if (token != null) {

					Matcher m1 = p1.matcher(token);
					while(m1.find()){//6-6
				//		System.out.println("here in m1");
				//		System.out.println(token);
					}
					Matcher m3 = p3.matcher(token);
					while(m3.find()){//12-B
				//		System.out.println("here in m3");
				//		System.out.println(token);
					}
					Matcher m4 = p4.matcher(token);
					while(m4.find()){//B-52
				//		System.out.println("here in m4");
				//		System.out.println(token);
					}
					Matcher m2 = p2.matcher(token);
					while(m2.find()){//D-BB3
				//		System.out.println("here in m2");
				//		System.out.println(token);
					}
					Matcher m5 = p5.matcher(token);
					while(m5.find()){//week-day
				//		System.out.println("here in m5");
						token=token.replaceAll("-", " ");
				//		System.out.println(token);
						stream.previous();
						stream.set(token);
						stream.next();
					}
					Matcher m6=p6.matcher(token);
					while(m6.find()){
				//		System.out.println(token);
				//		System.out.println("here in m6");
						stream.previous();
						stream.remove();
					}
					Matcher m7 =p7.matcher(token);
					while(m7.find()){
				//		System.out.println("here in m7");
						token=token.replaceAll("-", " ").trim();
				//		System.out.println(token);
						stream.previous();
						stream.set(token);
						stream.next();
					}
				
				}
			}

			stream.reset();

		}


	}

}


