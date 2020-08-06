/**
 * 17/set/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.util.List;

/**
 * @author gdefacci
 */
public class ArrayUtils {

	public static String[] concat(String[] left, String[] right) {
		if (left == null || left.length == 0) return right;
		if (right == null || right.length == 0) return left;
		String[] res = new String[left.length + right.length];
		System.arraycopy(left, 0, res, 0, left.length);
		System.arraycopy(right, 0, res, left.length, right.length);
		return res;
	}

	public static String[] concat(List/*<String>*/ left, String[] right) {
		return concat((String[]) left.toArray(new String[0]), right);
	}
	
	public static String[] concat(String[] left, List/*<String>*/ right) {
		return concat(left, (String[]) right.toArray(new String[0]));
	}
}
