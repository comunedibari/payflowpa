/**
 * 31/lug/2009
 */
package it.nch.xml2sql.test.t1;

import it.nch.eb.common.utils.resource.ClasspathResources;
import it.nch.xml2sql.test.ClasspathTestsRetriever;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class ClasspathResTest extends TestCase {
	
	public void testClspthRes() throws Exception {
		ClasspathResources resrcs = 
			new ClasspathResources(ClasspathTestsRetriever.insideFolder("tests/insert2"));
		
		assertEquals(3, resrcs.getResources().size());
		
	}

}
