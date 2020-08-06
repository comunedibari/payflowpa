/**
 * Created on 06/giu/07
 */
package it.nch.eb.flatx.files.model;



/**
 * return true/false querying fields inside lineTokenizer 
 * def matcher10 = { line -> 
			if (line.get(0) == "10") return true
			else return false 
		}
		
		
 * @author gdefacci
 */
public interface LineMatcher {
	
	boolean match(TokenizedLine tokenizedLine);
	
}
