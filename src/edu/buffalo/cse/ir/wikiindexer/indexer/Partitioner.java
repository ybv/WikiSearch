/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;


public class Partitioner {
	/**
	 * Method to get the total number of partitions
	 * THis is a pure design choice on how many partitions you need
	 * and also how they are assigned.
	 * @return: Total number of partitions
	 */
	public static int getNumPartitions() {
		//TODO: Implement this method
		return 27;
	}

	/**
	 * Method to fetch the partition number for the given term.
	 * The partition numbers should be assigned from 0 to N-1
	 * where N is the total number of partitions.
	 * @param term: The term to be looked up
	 * @return The assigned partition number for the given term
	 */
	public static int getPartitionNumber (String term) {
		//TDOD: Implement this method
		String term_v = term.toLowerCase();
		if(term_v.startsWith("a")){
			return 0;
		}else if(term_v.startsWith("b")){
			return 1;
		}
		else if(term_v.startsWith("c")){
			return 2;
		}
		else if(term_v.startsWith("d")){
			return 3;
		}else if(term_v.startsWith("e")){
			return 4;
		}
		else if(term_v.startsWith("f")){
			return 5;
		}
		else if(term_v.startsWith("g")){
			return 6;
		}else if(term_v.startsWith("h")){
			return 7;
		}
		else if(term_v.startsWith("i")){
			return 8;
		}
		else if(term_v.startsWith("j")){
			return 9;
		}else if(term_v.startsWith("k")){
			return 10;
		}
		else if(term_v.startsWith("l")){
			return 11;
		}
		else if(term_v.startsWith("m")){
			return 12;
		}else if(term_v.startsWith("n")){
			return 13;
		}
		else if(term_v.startsWith("o")){
			return 14;
		}
		else if(term_v.startsWith("p")){
			return 15;
		}else if(term_v.startsWith("q")){
			return 16;
		}
		else if(term_v.startsWith("r")){
			return 17;
		}
		else if(term_v.startsWith("s")){
			return 18;
		}else if(term_v.startsWith("t")){
			return 19;
		}
		else if(term_v.startsWith("u")){
			return 20;
		}
		else if(term_v.startsWith("v")){
			return 21;
		}else if(term_v.startsWith("w")){
			return 22;
		}
		else if(term_v.startsWith("x")){
			return 23;
		}
		else if(term_v.startsWith("y")){
			return 24;
		}else if(term_v.startsWith("z")){
			return 25;
		}
		else {
			return 26;
		}
	}
}
