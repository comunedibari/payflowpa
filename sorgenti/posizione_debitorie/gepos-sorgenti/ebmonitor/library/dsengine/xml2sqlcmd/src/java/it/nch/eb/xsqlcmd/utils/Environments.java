/**
 * 05/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;




/**
 * @author gdefacci
 */
public class Environments {
	
	public static Environment h2 = newEnvironment("h2");
	public static Environment db2 = newEnvironment("db2");
	public static Environment mysql = newEnvironment("mysql");
	public static Environment colltas = newEnvironment("colltas");
	
	public static Environment newEnvironment(String name, String path, String appCtxFileNm, BeanFactoryProvider beanFactoryProvider) {
		return new Environment(name, path, appCtxFileNm, beanFactoryProvider);
	}
	
	public static final String ASSEMBLIES_PACKAGE = "assembly/";
	public static final String DEFAULT_APPLICATION_CONTEXT_FILE_NAME = "application-context.xml";
	
	public static Environment newEnvironment(String name) {
		return newEnvironment(name, ASSEMBLIES_PACKAGE + name, DEFAULT_APPLICATION_CONTEXT_FILE_NAME, ClasspathBeanFactoryProvider.instance);
	};
}
