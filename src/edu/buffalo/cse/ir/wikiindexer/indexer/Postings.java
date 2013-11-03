package edu.buffalo.cse.ir.wikiindexer.indexer;
import java.io.Serializable;
public class Postings implements Serializable{

	
	private static final long serialVersionUID = 1L;
		public int fre;
		public int did;
		
		/**
		 * Default constructor. Please do not change visibility of the method.
		 */
		public Postings(int docid, int freq) {
			this.fre = freq;
			this.did = docid;
		}

		/**
		 * @return the docId
		 */
		public int getdocId() {
			return did;
		}

		/**
		 * @return the freq
		 */
		public int getFreq() {
			return fre;
		}
		
		public void setFreby(int num){
			this.fre+=num;
		}

		public boolean cmp(int dcid){
			if(this.did==dcid){
			return true;
			}else{
				return false;
			}
		}
	
}
