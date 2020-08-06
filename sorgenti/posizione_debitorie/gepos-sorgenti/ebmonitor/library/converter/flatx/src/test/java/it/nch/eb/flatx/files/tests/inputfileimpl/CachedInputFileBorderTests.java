/**
 * Created on 28/set/07
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import it.nch.eb.flatx.files.model.InputFileImpl;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class CachedInputFileBorderTests extends TestCase {
	
	
	public void testRollback1() throws Exception {
		final String res = "MY HEADER\nKHOvanna\nAffghh";

		InputFileImpl inf = new InputFileImpl(res);
		
		assertEquals("MY HEADER", inf.currentLine());
		
		inf.nextLine();
		
		assertEquals("KHOvanna", inf.currentLine());
		
		inf.rollback();
		
		assertEquals("MY HEADER", inf.currentLine());
	}


}
