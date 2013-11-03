package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.buffalo.cse.ir.wikiindexer.FileUtil;


public class ReaderTest  {

	

	public static void main(String[] args){
		//INDEXFIELD testarr[] = INDEXFIELD.values();
		Properties p;
		try {
			p = FileUtil.loadProperties("/Users/vaibhavkrishna/Documents/javaworkspace/IRP1-1/src/edu/buffalo/cse/ir/wikiindexer/properties.config");
			IndexReader ir = new IndexReader(p, INDEXFIELD.TERM);
			System.out.println(ir.getTotalKeyTerms());
			Map<String, Integer> a =ir.getPostings("History");
			Iterator<Map.Entry<String,Integer>> iter = a.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry<String,Integer> entry = iter.next();
			  System.out.println("link "+entry.getKey()+ " : "+" link id "+entry.getValue() );
			}
			/*
			Collection<String> topK = ir.getTopK(100);
			Iterator<String> t = topK.iterator();
			while (t.hasNext()) {
			      System.out.println("term is "+t.next());
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	
}
