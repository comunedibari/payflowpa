/**
 * Created on 10/feb/2009
 */
package it.nch.testools;

import org.springframework.beans.factory.BeanFactory;

public class BaseBeanProvider implements BeanProvider {
	private final BeanFactory ctx;

	public BaseBeanProvider(BeanFactory ctx) {
		this.ctx = ctx;
	}

	public Object getBean(String name) {
		return ctx.getBean(name);
	}
	
}