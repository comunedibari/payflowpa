/**
 * 03/ago/2009
 */
package it.nch.xml2sql.test.t1;

import it.nch.eb.common.utils.resource.ClasspathResources;
import it.nch.eb.common.utils.resource.StringPredicate;
import it.nch.xml2sql.test.ClasspathTestsRetriever;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class ClasspathResourcesTest extends TestCase  {
	
	public void testCP() throws Exception {
		StringPredicate pred = ClasspathTestsRetriever.expectedFileInFolder("tests/insert2");
		ClasspathResources lister = new ClasspathResources(pred);
		Collection ress = lister.getResources();
		for (Iterator it = ress.iterator(); it.hasNext();) {
			System.out.println( (Object) it.next() );
			
		}
		System.out.println("ok");
	}

}
