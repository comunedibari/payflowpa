/**
 * 05/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;

import org.springframework.beans.factory.BeanFactory;


/**
 * @author gdefacci
 */
public interface BeanFactoryProvider {
	
	BeanFactory create(String name);

}
