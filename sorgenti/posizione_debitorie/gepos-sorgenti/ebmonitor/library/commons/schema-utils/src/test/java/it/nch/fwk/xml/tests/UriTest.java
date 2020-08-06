/**
 * 
 */
package it.nch.fwk.xml.tests;

import it.nch.fwk.xml.schema.SchemaInfo;
import it.nch.fwk.xml.schema.SchemaLocator;
import it.nch.fwk.xml.schema.impl.DefaultSchemaLocator;
import junit.framework.TestCase;

/**
 * @author gedfr
 *
 */
public class UriTest extends TestCase {

	/**
	 * @param arg0
	 */
	public UriTest(String arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("----");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetSystemId01() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", null);
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSystemId(location));
		assertEquals(location.getSystemId().toString(), helper.getSystemId(location));
	}
	
	public void testGetSystemId02() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", "classpath:/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSystemId(location));
		assertEquals("/Test.xsd", helper.getSystemId(location));
	}
	
	public void testGetSystemId03() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", "classpath:/xsd");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSystemId(location));
		assertEquals("/Test.xsd", helper.getSystemId(location));
	}
	
	public void testGetSystemId04() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "Test.xsd", "classpath:/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSystemId(location));
		assertEquals("/Test.xsd", helper.getSystemId(location));
	}
	
	public void testGetSystemId05() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "Test.xsd", null);
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSystemId(location));
		assertEquals("/Test.xsd", helper.getSystemId(location));
	}
	
	public void testGetLocation01() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", null);
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaLocationUri(location));
		assertEquals("file:/Test.xsd", helper.getSchemaLocationUri(location).toString());
	}
	
	public void testGetLocation02() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", "classpath:/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaLocationUri(location));
		assertEquals("classpath:/Test.xsd", helper.getSchemaLocationUri(location).toString());
	}
	
	public void testGetLocation03() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", "classpath:/xsd/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaLocationUri(location));
		assertEquals("classpath:/xsd/Test.xsd", helper.getSchemaLocationUri(location).toString());
	}
	
	public void testGetLocation04() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "Test.xsd", "classpath:/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaLocationUri(location));
		assertEquals("classpath:/Test.xsd", helper.getSchemaLocationUri(location).toString());
	}
	
	public void testGetLocation05() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "Test.xsd", null);
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaLocationUri(location));
		assertEquals("Test.xsd", helper.getSchemaLocationUri(location).toString());
	}
	
	public void testGetSchemaIdentifier01() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", null);
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaIdentifier(location));
		assertEquals("schema:/Test.xsd", helper.getSchemaIdentifier(location).toString());
	}
	
	public void testGetSchemaIdentifier02() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", "classpath:/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaIdentifier(location));
		assertEquals("schema:/Test.xsd", helper.getSchemaIdentifier(location).toString());
	}
	
	public void testGetSchemaIdentifier03() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "file:/Test.xsd", "classpath:/xsd/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaIdentifier(location));
		assertEquals("schema:/Test.xsd", helper.getSchemaIdentifier(location).toString());
	}
	
	public void testGetSchemaIdentifier04() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "Test.xsd", "classpath:/");
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaIdentifier(location));
		assertEquals("schema:/Test.xsd", helper.getSchemaIdentifier(location).toString());
	}
	
	public void testGetSchemaIdentifier05() throws Exception {
		SchemaInfo location = new SchemaInfo("http://example.org/Test", "Test.xsd", null);
		
		SchemaLocator helper = new DefaultSchemaLocator();
		
		System.out.println(helper.getSchemaIdentifier(location));
		assertEquals("schema:/Test.xsd", helper.getSchemaIdentifier(location).toString());
	}
}
