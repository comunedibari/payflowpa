/**
 * Created on 13/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.common.utils.StringUtils;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class PathConcatTest extends TestCase {

	String expeted = "ashdh/ndd/js/bb/hj/ls";
	String expeted1 = "ashdh/ndd/js/bb/hj/ls/../kjh/hj";
	
	String path1 = "ashdh/ndd/js";
	String path2 = "bb/hj/ls";
	
	String path1a = "ashdh/ndd/js/";
	String path2b = "/bb/hj/ls";
	
	String path3b = "/..";
	String path4b = "/kjh/hj";
	
	public void test1() throws Exception {
		assertEquals(StringUtils.concatPaths(path1, path2), expeted);		
	}
	
	public void test1a() throws Exception {
		assertEquals(StringUtils.concatPaths(path1a, path2), expeted);		
	}
	
	public void test1b() throws Exception {
		assertEquals(StringUtils.concatPaths(path1, path2b), expeted);		
	}

	public void test1c() throws Exception {
		assertEquals(StringUtils.concatPaths(path1a, path2b), expeted);		
	}
	
	public void test2() throws Exception {
		assertEquals(StringUtils.concatPaths(new String[] { path1a, path2b , path3b, path4b }), expeted1);		
	}
}
