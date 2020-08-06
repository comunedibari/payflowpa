/**
 * Created on 15/apr/08
 */
package it.nch.eb.flatx.generator.xls;


/**
 * @author gdefacci
 */
public interface XlsCustomsFactory {
	
	/**
	 * since basepath is computed after all xpath expression are created, there's the need of recreating all xpath expression
	 * considering the calculated basepath. If a custum generator isnt interested in the basepath, can symply ignore the
	 * argument and symply return a copy
	 * 
	 * @note basePath can be null
	 */
	XlsCustom create(GenTypeModel type, String[] tokens, String basePath); 

}
