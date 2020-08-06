/**
 * Created on 01/giu/07
 */
package it.nch.eb.flatx.files.model;

/**
 * split line. every token could include space charecters and other raw data which
 * is later managed by converters.
 * @author gdefacci
 */
public interface TokenizedLine {
	
	boolean hasNext();
	String nextToken();
	void skip(int n);
	String[] all();
	String get(int idx);
	String getRawLine();
	
	int getSize();

}
