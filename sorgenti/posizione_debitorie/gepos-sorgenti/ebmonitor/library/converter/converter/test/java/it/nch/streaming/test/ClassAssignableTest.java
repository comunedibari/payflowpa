/**
 * Created on 08/ott/08
 */
package it.nch.streaming.test;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.converter.pmtreq.models.Record01Model;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ClassAssignableTest extends TestCase {

	public void testAssign() throws Exception {
		Class rpcModelClass = Record01Model.class; 
		Class bdyDelim = RecordCountProvider.class;
		
		if (bdyDelim.isAssignableFrom(rpcModelClass)) {
			System.out.println("bdyDelim");
		}
		
		if (rpcModelClass.isAssignableFrom(bdyDelim)) {
			System.out.println("rpcModelClass");
		}
	}
}
