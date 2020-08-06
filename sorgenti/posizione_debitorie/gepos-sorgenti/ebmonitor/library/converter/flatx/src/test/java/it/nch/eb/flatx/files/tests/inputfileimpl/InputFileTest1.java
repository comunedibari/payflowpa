/**
 * Created on 18/giu/07
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.fwk.test.Testable;
import it.nch.fwk.test.ThrowsAssertor;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class InputFileTest1 extends TestCase {

	private static final String TEXT	= "0\n1111\n22\n333\n444444\n55\n66";
	private InputFileImpl	inputFile;

	//	@Override
	protected void setUp() throws Exception {
		this.inputFile = new InputFileImpl(TEXT);
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
		
		inputFile.rollback();
		
		assertEquals("1111", inputFile.currentLine());
		
		inputFile.rollback();
		
		assertEquals("0", inputFile.currentLine());
		
	}
	
	public void test2() {
		assertEquals("0", inputFile.currentLine());
		
		inputFile.markSavePoint();
		
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		assertEquals("55", inputFile.nextLine());
		
		inputFile.rollbackToSavePoint();
		
		assertEquals("0", inputFile.currentLine());
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		inputFile.nextLine();
		
		inputFile.markSavePoint();
		
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		assertEquals("55", inputFile.nextLine());
		
		assertEquals("66", inputFile.nextLine());
		assertNull(inputFile.nextLine());
		
		inputFile.rollbackToSavePoint();
		
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		assertEquals("444444", inputFile.nextLine());
		
	}
	
	public void testCommitMultiple() {
		assertEquals("0", inputFile.currentLine());
		
		inputFile.markSavePoint();//0
		inputFile.markSavePoint();//1
		
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());  
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		
		inputFile.commit();//1
		
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		
		inputFile.markSavePoint();//2
		
		assertEquals("55", inputFile.nextLine());
		assertEquals("66", inputFile.nextLine());
		
		inputFile.rollbackToSavePoint();//2
		
		inputFile.markSavePoint();//3
		
		assertEquals("55", inputFile.nextLine());
		assertEquals("66", inputFile.nextLine());
		assertNull(inputFile.nextLine());
		
		inputFile.commit();//3
		
		inputFile.rollbackToSavePoint(); //0
		
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine()); 
		
	}
	
	public void testCommit() {
		assertEquals("0", inputFile.currentLine());
		
		inputFile.markSavePoint();
		
		assertEquals("1111", inputFile.nextLine());
		assertEquals("1111", inputFile.currentLine());
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		
		inputFile.commit();
		
		ThrowsAssertor.assertThrow(IllegalStateException.class, new Testable() {
			public void test() {
				inputFile.rollbackToSavePoint();
			}
		});
		
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		
		inputFile.markSavePoint();
		
		assertEquals("55", inputFile.nextLine());
		assertEquals("66", inputFile.nextLine());
		
		inputFile.rollbackToSavePoint();
		
		ThrowsAssertor.assertThrow(IllegalStateException.class, new Testable() {
			public void test() {
				inputFile.rollbackToSavePoint();
			}
		});
		
		assertEquals("55", inputFile.nextLine());
		assertEquals("66", inputFile.nextLine());
		assertNull(inputFile.nextLine());
		
		assertNull(inputFile.nextLine());
		
	}
	
	
	public void test4() throws Exception {
		assertEquals("0", inputFile.currentLine());

		assertEquals("1111", inputFile.nextLine());
		
		assertEquals(1, inputFile.getLineNumber());
		
		assertEquals("1111", inputFile.currentLine());
		
		assertEquals(1, inputFile.getLineNumber());
		
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		
		assertEquals(3, inputFile.getLineNumber());
		assertEquals("333", inputFile.currentLine());
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		
		assertEquals(4, inputFile.getLineNumber());
		
		assertEquals("55", inputFile.nextLine());
		
		inputFile.rollback(5);
		
		assertEquals(0, inputFile.getLineNumber());
		
		assertEquals("1111", inputFile.nextLine());
		
		assertEquals(1, inputFile.getLineNumber());
		
		assertEquals("1111", inputFile.currentLine());
		
		assertEquals(1, inputFile.getLineNumber());
		
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		
		inputFile.rollback(2);
		
		assertEquals(1, inputFile.getLineNumber());
		
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		
		assertEquals("333", inputFile.currentLine());
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		
		assertEquals(4, inputFile.getLineNumber());
		
		assertEquals("55", inputFile.nextLine());
		
		
	}
}
