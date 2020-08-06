/**
 * Created on 06/dic/07
 */
package it.nch.eb.common.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gdefacci
 */
public class RegexpUtils {

	public static final String	ESCAPE_SEQUENCE	= "\\";
	public final static char[] SPECIAL_CHARS = {
		'.'
	};
	
	public static String escaped(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			char[] specialChars = SPECIAL_CHARS;
			boolean toEscape = containsOneOf(ch, specialChars);
			if (!toEscape) sb.append(ch);
			else sb.append(ESCAPE_SEQUENCE + ch);
		}
		return sb.toString();
	}

	public static boolean containsOneOf(char ch, char[] specialChars) {
		boolean toEscape = false;
		for (int j = 0; j < specialChars.length; j++) {
			if (ch==specialChars[j]) toEscape = true;
		}
		return toEscape;
	}
	
	public static String[] split(String str, String sep) {
		String sepRegExp = escaped(sep);
		String[] res = str.split(sepRegExp);
		List/*<String>*/ list = new ArrayList();
		for (int i = 0; i < res.length; i++) {
			if (!"".equals(res[i].trim())) {
				list.add(res[i]);
			}
		}
		return (String[]) list.toArray(new String[0]);
	}
	
	/**
	 * create filler, filled with <code>filler</code> parameter, len bytes long
	 * @param filler
	 * @param len
	 * @return
	 */
	public static String makeFiller(String filler, int len) {
		int n = len / filler.length() + 1;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			sb.append(filler);
		}
		return sb.toString().substring(0,len);
	}
	
	/**
	 * remove any contiguos sequence of filler from value, from left (alignment2 = Alignments.RIGHT)
	 * or from right (alignment2 != Alignments.RIGHT). The alignment2 parameter means the way in which value is aligned .
	 * @param value
	 * @param filler
	 * @param alignment2
	 * @return
	 */
	public static String removeFiller(String value, String filler, int alignment2) {
		int fillerLen = filler.length();
		if (alignment2 == Alignments.RIGHT) {
			int i = 0;
			while (i < value.length()) {
				String part = value.substring(i, i + fillerLen);
				if (!part.equals(filler)) {
					return value.substring(i, value.length());
				}
				i = i + fillerLen;
			}
			return value;
		} else {
			int i = value.length() - fillerLen;
			while (i > 0) {
				String part = value.substring(i, i + fillerLen);
				if (!part.equals(filler)) {
					return value.substring(0, i+ fillerLen);
				}
				i = i - fillerLen;
			}
			return value;
		}
	}
	
	public static String fill(String value, int size) {
		return fill(value, size, " ", Alignments.LEFT);
	}
	
	public static String fill(String value, int size, int align) {
		return fill(value, size, " ", align);
	}
	
	public static String fill(String value, Integer size, String filler, int align) {
		int intSize = size.intValue();
		return fill(value, intSize, filler, align);
	}

	public static String fill(String value, int size, String filler, int align) {
		if ((value==null) || (filler==null)) throw new IllegalArgumentException("the string to fill or the filler are null\nstring[" + value + "]\tfiller[" + filler + "]");
		int strLen = value.length();
		
		if (strLen==size) return value;
		if (strLen>size) return value.substring(0,size);
		int gapLen = size-strLen;
		String flr = RegexpUtils.makeFiller(filler, gapLen);
		if (align==Alignments.RIGHT) return flr + value;
		return value + flr;
	}
}
