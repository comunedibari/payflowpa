/**
 * 05/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.common.utils.StringUtils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class FileSystemBeanFactoryProvider implements BeanFactoryProvider {
	
	public static BeanFactoryProvider instance = new FileSystemBeanFactoryProvider(Environments.DEFAULT_APPLICATION_CONTEXT_FILE_NAME);
	private String applicationContextFileName;

	public FileSystemBeanFactoryProvider(String applicationContextFileName) {
		this.applicationContextFileName = applicationContextFileName;
	}

	public BeanFactory create(String name) {
		FileSystemXmlApplicationContext res;
		try {
			res = new FileSystemXmlApplicationContext(StringUtils.concatPaths(name, applicationContextFileName));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("FileSystemBeanFactoryProvider cant find " + name, e);
		}
		if (res == null) throw new RuntimeException("FileSystemBeanFactoryProvider cant find " + name);
		return res;
	}
	
}