/**
 * Created on 16/gen/08
 */
package it.nch.eb.flatx.generator.javamodel;

import it.nch.eb.flatx.generator.XlsModel;


/**
 * @author gdefacci
 */
public class GetBindingValueFieldDeclStrategy implements FieldDeclStrategy {

	public String createInitializer(XlsModel model, int pos) {
		String bindingName = model.getInfos()[0];
		String converterName = model.getInfos()[1];
		return "createGetBindingValue(" + pos + ", " + converterName + ",\"" + bindingName + "\");";
	}

	public String createName(XlsModel xlsModel) {
		String bindingName = xlsModel.getInfos()[0];
		bindingName = bindingName.replace(' ', '_');
		return bindingName;
	}

	public boolean match(XlsModel model) {
		return "v".equals( model.getModifier() );
	}

}
