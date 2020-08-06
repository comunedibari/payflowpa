/**
 * 05/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClasspathBeanFactoryProvider implements BeanFactoryProvider {
	
	public static BeanFactoryProvider instance = new ClasspathBeanFactoryProvider();

	public BeanFactory create(String name) {
		ClassPathXmlApplicationContext res = null;
		String apContext = "";
		try {
			apContext = "classpath:" + name;
			res = new ClassPathXmlApplicationContext(apContext);			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cant find " + apContext);
			
			System.out.println("setting classloader ");
			res.setClassLoader(this.getClass().getClassLoader());
			System.out.println("refresh");
			res.refresh();
			System.out.println("does it works ?");
			
			throw new RuntimeException("ClasspathBeanFactoryProvider cant find " + apContext);
		}
		


		
		return res;
	}
	
}