/**
 * 07/set/2009
 */
package it.tasgroup;

import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class SpringTest extends TestCase {
	
	public void testSystemProps() throws Exception {
		ClassPathXmlApplicationContext bf = new ClassPathXmlApplicationContext("assembly/h2/files-configuration.xml");
		Properties props = (Properties) bf.getBean("configurationBean");
		String nm = (String) props.get("DefaultDataSourceLocalRefName");
		System.out.println(nm);
	}

}
