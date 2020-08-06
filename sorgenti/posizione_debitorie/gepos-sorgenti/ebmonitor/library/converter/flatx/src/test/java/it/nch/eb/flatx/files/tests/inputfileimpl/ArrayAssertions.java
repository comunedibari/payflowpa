/**
 * Created on 05/giu/08
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;


/**
 * @author gdefacci
 */
public class ArrayAssertions {
	
	public static String toString(Object[] arr) {
		StringBuffer sb = new StringBuffer("(");
		if (arr.length>0) sb.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			Object object = arr[i];
			sb.append(", " + object.toString());
		}
		sb.append(")");
		return sb.toString();
	}
	
	public static void assertEquals(Object[] expectd, Object[] objs) {
		if (expectd.length != objs.length) 
			throw new AssertionFailedError( toString(objs) + " has a different size from " + toString(expectd));
		
		for (int i = 0; i < expectd.length; i++) {
			Object expct = expectd[i];
			Assert.assertEquals(expct, objs[i]);
		}
		
	}

}
