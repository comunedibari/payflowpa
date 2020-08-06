/**
 * Created on 03/giu/07
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.exceptions.TokenizationException;
import it.nch.eb.flatx.files.model.TokenizedLine;

/**
 * @author gdefacci
 */
public class TokenizationUtil {
	
	static interface LastTokenErrorHandlingStrategy {
		void onToken(String value, int position, int expecetedSize, String fullline);
	}
	
	static final LastTokenErrorHandlingStrategy FAIL= new LastTokenErrorHandlingStrategy() {

		public void onToken(String value, int position, int expecetedSize, String fullline) {
			throw new TokenizationException(value, position, expecetedSize, fullline);
		}
		
	};
	
	static final LastTokenErrorHandlingStrategy IGNORE = new LastTokenErrorHandlingStrategy() {

		public void onToken(String value, int position, int expecetedSize, String fullline) {
		}
		
	};
	
	static final LastTokenErrorHandlingStrategy tokenErrorHandler = FAIL;
	
	public static TokenizedLine createDelimiterTokenizer(String content, String regexp) {
		String[] res = content.trim().split(regexp);
		if ("".equals(res[res.length-1].trim())) {
//			could happen trim returns an extra element containig an emty string; 
			String[] aux = new String[res.length-1];
			System.arraycopy(res, 0, aux, 0, res.length-1);
			return new BaseTokenizedLine(aux);
		} else {
			return new BaseTokenizedLine(res);
		}
	}
	
	public static TokenizedLine createFixedSizeTokenizer(String content, int[] columns) {
		int pos = 0;
		String[] tokens = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			int size = columns[i];
			if (i==columns.length-1) {
				tokens[i] = content.substring(pos);
			}
			else {
				try {
					if (size<1) throw new TokenizationException("invalid size [" + 0 + "]");
					tokens[i] = content.substring(pos, pos+size);
				} catch (Exception e) {
					TokenizationException tex = new TokenizationException("after token " + tokens[i-1], tokens[i], pos, size, content);
					tex.setLinePositionStart(pos);
					tex.setLinePositionEnd(pos+size);
					throw tex;
//					return new BaseTokenizedLine(tokens);
				}
			}
			if (tokens[i].length()>size) {
				tokenErrorHandler.onToken(tokens[i], pos, size, content);
			}
			pos = pos + size;
		}
		return new BaseTokenizedLine(tokens);
	}
	
	public static TokenizedLine createIgnoreMissingTokensTokenizer(String content, int[] columns, String emptyToken) {
		int pos = 0;
		String[] tokens = new String[columns.length];
		int colCnt = 0;
		boolean finishedLineTokenization = false;
		while (!finishedLineTokenization && colCnt< columns.length) {
			int size = columns[colCnt];
			String remainder = content.substring(pos);
			if (remainder.length()>= size) {
				tokens[colCnt] = content.substring(pos, pos+size);
			} else {
				tokens[colCnt] = content.substring(pos, content.length());
				finishedLineTokenization = true;
			}
			pos = pos + size;
			colCnt ++;
		}
		BaseTokenizedLine res = new BaseTokenizedLine(tokens);
		return res;
	}

}
