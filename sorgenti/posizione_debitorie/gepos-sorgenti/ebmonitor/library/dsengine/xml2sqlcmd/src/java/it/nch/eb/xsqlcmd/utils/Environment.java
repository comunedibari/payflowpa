/**
 * 05/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.common.utils.StringUtils;

import org.springframework.beans.factory.BeanFactory;

public class Environment {
	private final String name;
	private final String folder;
	private final Jdk14Lazy beanFactoryProvider;
	private final String applicationContextName;
	private final BeanFactoryProvider loader;

	public Environment(String name, 
			final String folderPath, 
			final String applicationContextFileNm, 
			final BeanFactoryProvider loader) {
		this(name, folderPath, applicationContextFileNm, loader, 
				lazyContext(folderPath, applicationContextFileNm, loader));
	}

	private static Jdk14Lazy lazyContext(final String folderPath, final String applicationContextFileNm,
			final BeanFactoryProvider loader) {
		return new Jdk14Lazy() {

			protected Object getValue() {
				return loader.create(StringUtils.concatPaths(folderPath , applicationContextFileNm ));
			}
			
		};
	}
	
	protected Environment(String name, 
			String folder, 
			String applicationContextName, 
			BeanFactoryProvider loader, Jdk14Lazy beanFactoryProvider) {
		this.name = name;
		this.folder = folder;
		this.beanFactoryProvider = beanFactoryProvider;
		this.applicationContextName = applicationContextName;
		this.loader = loader;
	}
	
	public BeanFactoryProvider getLoader() {
		return loader;
	}

	public String getName() {
		return name;
	}

	public Jdk14Lazy getBeanFactoryProvider() {
		return beanFactoryProvider;
	}

	public BeanFactory getBeanFactory() {
		return (BeanFactory) beanFactoryProvider.apply();
	}

	public String getApplicationContextName() {
		return applicationContextName;
	}

	public String getFolder() {
		return folder;
	}
	
	public Environment withApplicationContextNamed(String newName) {
		return new Environment(name, getFolder(), newName, loader);
	}
	
	
	
}