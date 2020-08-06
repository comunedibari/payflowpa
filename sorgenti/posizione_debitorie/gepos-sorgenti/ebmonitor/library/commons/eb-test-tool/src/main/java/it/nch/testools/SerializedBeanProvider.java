/**
 * Created on 10/feb/2009
 */
package it.nch.testools;


import java.io.File;

import org.springframework.beans.factory.BeanFactory;

public class SerializedBeanProvider implements BeanProvider {
	
	private final BeanProvider ctx;

	public SerializedBeanProvider(BeanFactory ctx) {
		this(new BaseBeanProvider( ctx ));
	}
	
	public SerializedBeanProvider(BeanProvider ctx) {
		this.ctx = ctx;
	}

	public Object getBean(String name) {
		Object si = ctx.getBean(name);
		File f = SerializationHelper.instance.serializeObject(si);
		return SerializationHelper.instance.deserialize(f);
	}
	
}