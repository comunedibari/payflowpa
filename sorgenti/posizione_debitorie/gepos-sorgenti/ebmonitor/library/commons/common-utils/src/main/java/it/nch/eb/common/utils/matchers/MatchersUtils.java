/**
 * Created on 07/dic/07
 */
package it.nch.eb.common.utils.matchers;


/**
 * @author gdefacci
 */
public class MatchersUtils {

	static boolean isDigits(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!Character.isDigit(ch)) return false; 
		}
		return true;
	}
	
}
