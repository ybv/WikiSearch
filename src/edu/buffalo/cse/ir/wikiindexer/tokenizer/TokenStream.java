/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class TokenStream implements Iterator<String>{
	private ArrayList<String> stringcoll = new ArrayList<String>();
	Map<String,Integer> tokmap = new TreeMap<String, Integer>();
	private int index =0;
	/**
	 * Default constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(StringBuilder bldr) {
		//TODO: Implement this method

	}

	/**
	 * Overloaded constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(String string) {
		if(string!=null && !string.equals("")){
			stringcoll.add(string);
		//	System.out.println("added"+string);
			}
	}

	/**
	 * Method to append tokens to the stream
	 * @param tokens: The tokens to be appended
	 */
	public void append(String... tokens) {
		if(tokens!=null)
		{		for (String s : tokens){
			if(s!=null &&  !s.equals("")){
				stringcoll.add(s);
		//		System.out.println("added"+s);
			}
		}
		}	//TODO: Implement this method
	}

	/**
	 * Method to retrieve a map of token to count mapping
	 * This map should contain the unique set of tokens as keys
	 * The values should be the number of occurrences of the token in the given stream
	 * @return The map as described above, no restrictions on ordering applicable
	 */
	public Map<String, Integer> getTokenMap() {
		//TODO: Implement this method

		Iterator<String> it = stringcoll.iterator();
		tokmap.clear();
		//while(it.hasNext())System.out.println(it.next());	
		//System.out.println(stringcoll.toString());
		int count=0;
		while(it.hasNext()){

			String s = it.next();
			//System.out.println("in while"+s);
			if(s!=null){
			//	System.out.println("here is s "+s);
				if(tokmap.containsKey(s)){
					//System.out.println("coming here "+s);
					tokmap.put(s, tokmap.get(s)+1);
				}else{
					//System.out.println("coming here "+s);
					tokmap.put(s, 1);
				}

			}

		}


		if(tokmap.isEmpty()){
			return null;
		}else{
			return tokmap;
		}

	}

	/**
	 * Method to get the underlying token stream as a collection of tokens
	 * @return A collection containing the ordered tokens as wrapped by this stream
	 * Each token must be a separate element within the collection.
	 * Operations on the returned collection should NOT affect the token stream
	 */
	public Collection<String> getAllTokens() {
		if(stringcoll.isEmpty()){
			return null;
		}
		return stringcoll;

	}

	/**
	 * Method to query for the given token within the stream
	 * @param token: The token to be queried
	 * @return: THe number of times it occurs within the stream, 0 if not found
	 */
	public int query(String token) {
		int count=0;
		Iterator<String> it = stringcoll.iterator();
		while(it.hasNext()){
			if(it.next().equals(token.toString())){
				count++;
			}
		}

		return count;
	}

	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasNext() {
		// TODO: Implement this method
		if(stringcoll.size()>index){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasPrevious() {
		if(index<=0)
			return false;
		else{
			return true;
		}
	}

	/**
	 * Iterator method: Method to get the next token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String next() {
		String s =null;
		//System.out.println(index + "ind");
		//System.out.println(stringcoll.size()+ "size");
		if(index<stringcoll.size()){
			s=stringcoll.get(index);
			index++;
		}
		return s;
	}

	/**
	 * Iterator method: Method to get the previous token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String previous() {
		String s =null;
		if(index>=1){
			index--;
			s=stringcoll.get(index);
			//System.out.println(" in previous "+s);
			//System.out.println("index is"+ index);
		}return s;
	}

	/**
	 * Iterator method: Method to remove the current token from the stream
	 */
	public void remove() {

		if(stringcoll.size()!=0 && index!=stringcoll.size()){
			//System.out.println("index of the removed "+index);
			//System.out.println("removed"+stringcoll.get(index));
			stringcoll.remove(index);
			//System.out.println(stringcoll.toString());
			//index--;
		}

	}

	/**
	 * Method to merge the current token with the previous token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the previous one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithPrevious() {
		String prev = null;
		String news;
		boolean a = false;
		if(index>0 && !stringcoll.isEmpty() && hasPrevious()){

			index--;
			prev = stringcoll.get(index);
			remove();
		//	System.out.println("prev is:"+prev);
			if(stringcoll.isEmpty())
				return false;
			news =prev+" "+stringcoll.get(index);
			stringcoll.set(index, news);
			a=true;

		//	System.out.println(stringcoll.toString()+ "merge with previous");

		}
		//System.out.println("boolean value here "+ a );
		return a;
	}

	/**
	 * Method to merge the current token with the next token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the current one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithNext() {
		String next = null;
		String news;
		String now;
		boolean a = false;
		//System.out.println("index in merge with next"+ index);
		if(index>=0 && stringcoll.size()>0 && index <stringcoll.size() && hasNext()){

			a=true;
			now = stringcoll.get(index);
			//System.out.println("now is :"+now );
			try{
			next = stringcoll.get(index+1);
			}
			catch(Exception e){
			//	System.out.println("next is :"+next );
			}
			finally{
				if(next==null){
					return false;
				}
			}
			remove();
			news = now+" "+next;
			
			stringcoll.set(index, news);
			if(index>stringcoll.size()){
				a=false;
			}
			//System.out.println(stringcoll.toString()+" in next");
			}
		
		return a;
	}

	/**
	 * Method to replace the current token with the given tokens
	 * The stream should be manipulated accordingly based upon the number of tokens set
	 * It is expected that remove will be called to delete a token instead of passing
	 * null or an empty string here.
	 * The iterator should point to the last set token, i.e, last token in the passed array.
	 * @param newValue: The array of new values with every new token as a separate element within the array
	 */
	public void set(String... newValue) {
		boolean rem =true;
		if(newValue!=null && !newValue.equals("null")){

			int size = newValue.length;
			StringBuffer sb = new StringBuffer();
			for(String s : newValue){
				
				if(stringcoll.size()>0 && index<stringcoll.size()){
					if(stringcoll.get(0)!=null){
						if(s!=null && !s.trim().equals("null") && !s.trim().equals("")){
							if(rem){
								remove();
								rem = false;
								stringcoll.add(index,s.trim());

							}
							else{//System.out.println(stringcoll.toString());
							index++;
							//System.out.println(index);
							
							stringcoll.add(index,s.trim());

							//System.out.println("index value in first if "+index);
							//System.out.println(stringcoll.toString());
							}
							//System.out.println("ind "+ index+" stirng coll size is"+stringcoll.size()+" "+stringcoll.toString());

						}		}

				}
			} }
	}

	/**
	 * Iterator method: Method to reset the iterator to the start of the stream
	 * next must be called to get a token
	 */
	public void reset() {
		index=0;
	}

	/**
	 * Iterator method: Method to set the iterator to beyond the last token in the stream
	 * previous must be called to get a token
	 */
	public void seekEnd() {
		index=stringcoll.size();
		//System.out.println("index value in seekend "+ stringcoll.toString());
	}

	/**
	 * Method to merge this stream with another stream
	 * @param other: The stream to be merged
	 */
	public void merge(TokenStream other) {
		if(other!=null){
			while(other.hasNext()){
				stringcoll.add(other.next());
			}
		}}
}
