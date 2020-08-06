/**
 * Created on 18/giu/07
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class InputFileTest extends TestCase {

	private static final String TEXT	= "0\n1111\n22\n333\n444444\n55\n66";
	private InputFile	inputFile;

	//	@Override
	protected void setUp() throws Exception {
		this.inputFile = new InputFileImpl(TEXT);
	}
	
	public void test2() {
		assertEquals("0", inputFile.currentLine());

		assertEquals("1111", inputFile.nextLine());
		
		assertEquals(1, inputFile.getLineNumber());
		
		assertEquals("1111", inputFile.currentLine());
		
		assertEquals(1, inputFile.getLineNumber());
		
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		
		assertEquals(3, inputFile.getLineNumber());
		
		assertEquals("444444", inputFile.nextLine());
		
		assertEquals(4, inputFile.getLineNumber());
		
		assertEquals("444444", inputFile.currentLine());
		assertEquals("55", inputFile.nextLine());
		
		assertEquals(5, inputFile.getLineNumber());
		
		inputFile.nextLine();
		assertNull(inputFile.nextLine());
		
		assertNull(inputFile.nextLine());
	}
	
	public void test1() {
		this.inputFile = new InputFileImpl("");
		assertEquals(false, inputFile.hasNext());
		assertNull(inputFile.nextLine());
	}
	
	public void testRollback() {
		assertEquals("0", inputFile.currentLine());

		inputFile.nextLine();
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback();
		
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		assertEquals("22", inputFile.nextLine());
		
		inputFile.rollback();
		
		assertEquals("1111", inputFile.currentLine());
		assertEquals("22", inputFile.nextLine());
		assertEquals("22", inputFile.currentLine());
		
	}
	
	public void testSavepoint() {
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback(1);
		
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback();
		
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("22", inputFile.nextLine());
		assertEquals("22", inputFile.currentLine());
	}
	
	public void testSavepointAndCommit() {
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback(1);
		
//		inputFile.commit();
		inputFile.nextLine();
		
		assertEquals("22", inputFile.nextLine());
		assertEquals("22", inputFile.currentLine());
	}
	
	public void testSavepoint1() {
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback(1);
		
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback();
		
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("22", inputFile.nextLine());
		assertEquals("22", inputFile.currentLine());
	}
}
