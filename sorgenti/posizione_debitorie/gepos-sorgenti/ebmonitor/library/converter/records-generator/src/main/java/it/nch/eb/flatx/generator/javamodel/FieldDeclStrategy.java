/**
 * Created on 16/gen/08
 */
package it.nch.eb.flatx.generator.javamodel;

import it.nch.eb.flatx.generator.XlsModel;


/**
 * @author gdefacci
 */
public interface FieldDeclStrategy {
	
	String createInitializer(XlsModel model, int pos);
	boolean match(XlsModel model);
	
	String createName(XlsModel xlsModel);

}
