/**
 * Created on 03/giu/07
 */
package it.nch.eb.flatx.files.model;

/**
 * @author gdefacci
 */
public interface InputFile {
	
	/**
	 * is there something to read?
	 * @return
	 */
	boolean hasNext();
	/**
	 * consume a line and return it
	 * @return
	 */
	String nextLine();
	/**
	 * return the last line read, without counsuming lines
	 * @return
	 */
	String currentLine();
	
	/**
	 * the number of the last read line: keep traks of the rollbacks
	 */
	public int getLineNumber();
	
	/**
	 * rollback last read line
	 */
	void rollback();
	
	void markSavePoint();

	void rollbackToSavePoint();
	
	void commit();
	
	public void rollback(int n);
	
	
}
