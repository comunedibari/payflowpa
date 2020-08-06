/**
 * Created on 02/ott/08
 */
package it.nch.testools;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author gdefacci
 */
public class ApplicationContextEntry {
	
	private final String name;
	private BeanProvider beansProvider;
	
	public ApplicationContextEntry(String name, BeanFactory applicationContext) {
		this(name, new BaseBeanProvider(applicationContext));
	}
	
	public ApplicationContextEntry(String name, BeanProvider beansProvider) {
		this.name = name;
		this.beansProvider = beansProvider;
	}

	public String getName() {
		return name;
	}
	
	public BeanProvider getBeanProvider() {
		return beansProvider;
	}
	
}