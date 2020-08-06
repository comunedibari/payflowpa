/**
 * Created on 18/giu/07
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import it.nch.eb.flatx.files.model.InputFileWithBuffer;
import it.nch.eb.flatx.files.model.InputFileWithBuffer.FilePositionMarker;
import it.nch.eb.flatx.files.model.InputFileWithBuffer.LineDecorator;
import it.nch.fwk.test.Testable;
import it.nch.fwk.test.ThrowsAssertor;

import java.io.StringWriter;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class InputFileWithBufferTest1 extends TestCase {

	private static final String TEXT	= "0\n1111\n22\n333\n444444\n55\n66";
	private InputFileWithBuffer	inputFile;

	//	@Override
	protected void setUp() throws Exception {
		this.inputFile = new InputFileWithBuffer(TEXT);
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
		
		FilePositionMarker marker = inputFile.getPositionMarker();
		
		assertEquals("1111", inputFile.currentLine());
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		assertEquals("444444", inputFile.nextLine());
		assertEquals("444444", inputFile.currentLine());
		assertEquals("55", inputFile.nextLine());

		String[] lines = inputFile.getBufferFrom(marker);
		ArrayAssertions.assertEquals(new String[] {
		    "1111"	,
			"22"    ,
			"333"   ,
			"444444",
		}, lines);
		
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
		
		inputFile.nextLine();
		assertNull(inputFile.nextLine());
		
		inputFile.rollbackToSavePoint();
		
		String[] lines1 = inputFile.getBufferFrom(marker);
		ArrayAssertions.assertEquals(new String[] {
				"1111"    ,
			}, lines1);
		
		
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
		
		FilePositionMarker marker = inputFile.getPositionMarker();
		
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
		
		String[] strs = inputFile.getBufferFrom(marker);
		ArrayAssertions.assertEquals(new String[] {
				"333",
				"444444",
				"55"    ,
			}, strs);
		
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
		InputFileWithBuffer other = new InputFileWithBuffer("sdff\n\nfsdfsdfsdf\n\nsdffsd");
		other.nextLine();
		other.nextLine();
		
		final FilePositionMarker cheatsMarker = other.getPositionMarker();
		
		assertEquals("0", inputFile.currentLine());
		
		inputFile.markSavePoint();
		FilePositionMarker marker = inputFile.getPositionMarker();
		
		String[] strsEmpty = inputFile.getBufferFrom(marker);
		ArrayAssertions.assertEquals(new String[0], strsEmpty);
		
		assertEquals("1111", inputFile.nextLine());
		
		String[] strs2 = inputFile.getBufferFrom(marker);
		ArrayAssertions.assertEquals(new String[] {
				"0"	,	
			}, strs2);
		
		assertEquals("1111", inputFile.currentLine());
		
		String[] strs = inputFile.getBufferFrom(marker);
		ArrayAssertions.assertEquals(new String[] {
				"0"	,	
			}, strs);
		
		inputFile.nextLine();
		assertEquals("333", inputFile.nextLine());
		assertEquals("333", inputFile.currentLine());
		
		inputFile.commit();
		
		ThrowsAssertor.assertThrow(IllegalStateException.class, new Testable() {
			public void test() {
				inputFile.rollbackToSavePoint();
			}
		});
		
		ThrowsAssertor.assertThrow(IllegalStateException.class, new Testable() {
			public void test() {
				inputFile.getBufferFrom(cheatsMarker);
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
		
		FilePositionMarker mrkr = inputFile.getPositionMarker();
		
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
		
		String[] strs = inputFile.getBufferFrom(mrkr);
		
		ArrayAssertions.assertEquals(new String[] {
				"22",
				"333",
				"444444",
			}, strs);
		
		StringWriter sw = new StringWriter();
		
		inputFile.writeFromMarker(sw, "---", mrkr);
		
		assertEquals("22---333---444444---", sw.getBuffer().toString());
		
		StringWriter sw1 = new StringWriter();
		
		LineDecorator hateDecorator = new LineDecorator() {

			public String decorated(String line) {
				return "hate " + line + "--";
			}
			
		};
		
		inputFile.writeFromMarker(sw1, hateDecorator, mrkr);
		
		assertEquals("hate 22--hate 333--hate 444444--", sw1.getBuffer().toString());
		
		
	}
}
