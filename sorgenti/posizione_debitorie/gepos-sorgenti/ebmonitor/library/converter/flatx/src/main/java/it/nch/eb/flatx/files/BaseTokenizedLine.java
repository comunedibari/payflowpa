/**
 * Created on 03/giu/07
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.files.model.TokenizedLine;

import java.util.List;



/**
 * helper class
 * @author gdefacci
 */
public class BaseTokenizedLine implements TokenizedLine {
	
	private String[]	tokens;
	private int			idx;
//	lazy cached instance => tokens concatenations
	private String		rawValue;

	public BaseTokenizedLine(final String[] tokens) {
		this.tokens = tokens;
	}
	
	public BaseTokenizedLine(final List/*<String>*/ tokens) {
		this.tokens = (String[]) tokens.toArray(new String[0]);
	}

	public String[] all() {
		return tokens;
	}
	
	public int getSize() {
		return tokens.length;
	}

	public boolean hasNext() {
		return idx<tokens.length;
	}

	public String nextToken() {
		String res = tokens[idx];
		idx++;
		return res;
	}

	public void skip(int n) {
		idx+=n;
	}

	public String get(int idx) {
		if ((idx<0) || !(idx<tokens.length)) return noSuchIndex(idx);
		return tokens[idx];
	}

	protected String noSuchIndex(int idx2) {
		throw new IndexOutOfBoundsException(this.getClass().getName() + "index"+ idx2 + " out of bound" + toString() );
	}

//	@Override
	public String toString() {
		return "(idx=" + this.idx + ")Tokens :" + asString(this.tokens);
	}
	
	public static String asString(Object[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			Object object = array[i];
			String strValue;
			if (object!=null) strValue = object.toString();
			else strValue = "null";
			sb.append("[(" + i + ")" + strValue + "]");
		}
		return sb.toString();
	}

	/**
	 * returns the concatenated tokens. delimiter could be not included
	 */
	public String getRawLine() {
		if (rawValue==null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.tokens.length; i++) sb.append(tokens[i]);
			rawValue = sb.toString();
		}
		return rawValue;
	}
	
	

}
