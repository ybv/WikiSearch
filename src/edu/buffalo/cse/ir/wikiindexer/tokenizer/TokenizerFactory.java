/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.Properties;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.AccentHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.ApostropheHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.CapHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.DatesHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.DelimHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.HyphenHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.NumberHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.PunctuationHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.SpecialCharsHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.StopHandler;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.WhiteSpceHandler;


public class TokenizerFactory {
	//private instance, we just want one factory
	private static TokenizerFactory factory;

	//properties file, if you want to read soemthing for the tokenizers
	private static Properties props;

	/**
	 * Private constructor, singleton
	 */
	private TokenizerFactory() {
		//TODO: Implement this method
	}

	/**
	 * MEthod to get an instance of the factory class
	 * @return The factory instance
	 */
	public static TokenizerFactory getInstance(Properties idxProps) {
		if (factory == null) {
			factory = new TokenizerFactory();
			props = idxProps;
		}

		return factory;
	}

	/**
	 * Method to get a fully initialized tokenizer for a given field type
	 * @param field: The field for which to instantiate tokenizer
	 * @return The fully initialized tokenizer
	 */
	public Tokenizer getTokenizer(INDEXFIELD field) {
		switch(field){
			case TERM:
			try {//new StopHandler(),new EnglishStemmer(),new AccentHandler(), new ApostropheHandler(), new CapHandler(), new DatesHandler(), new HyphenHandler(), new NumberHandler(), new PunctuationHandler(), new SpecialCharsHandler(), 
				return new Tokenizer(new WhiteSpceHandler(), new NumberHandler(),new SpecialCharsHandler(),  new PunctuationHandler(),new ApostropheHandler(), new HyphenHandler(),new AccentHandler(),new StopHandler(),new EnglishStemmer(), new CapHandler());
			} catch (TokenizerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			case AUTHOR:
				try {//new CapHandler()
					return new Tokenizer(new AccentHandler());
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case LINK:
				try {//new AccentHandler()
					return new Tokenizer(new DelimHandler("$"));
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case CATEGORY:
				try {//new AccentHandler()
					return new Tokenizer(new DelimHandler("$"));
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//TODO: Implement this method
		/*
		 * For example, for field F1 I want to apply rules R1, R3 and R5
		 * For F2, the rules are R1, R2, R3, R4 and R5 both in order
		 * So the pseudo-code will be like:
		 * if (field == F1)
		 * 		return new Tokenizer(new R1(), new R3(), new R5())
		 * else if (field == F2)
		 * 		return new TOkenizer(new R1(), new R2(), new R3(), new R4(), new R5())
		 * ... etc
		 */
		return null;
	}
}
