/**
 * Created on 16/gen/08
 */
package it.nch.eb.flatx.generator.javamodel;

import it.nch.eb.flatx.generator.XlsModel;


/**
 * @author gdefacci
 */
public class XPathDeclStrategy implements FieldDeclStrategy {

	public String createInitializer(XlsModel model, int pos) {
		return "createXPath(" + pos + "," + model.getInfos()[1] + ",\"" + model.getInfos()[0] + "\")";
	}

	public boolean match(XlsModel model) {
		if (model.getModifier()!=null && model.getModifier().toLowerCase().equals("x")) {
			return true;
		} else  if ("".equals(model.getModifier().trim())) {
			return isXPath(model.getInfos()[0]);
		}
		return false;
	}

	public boolean isXPath(String value) {
		if (value.indexOf('/')>0 || value.indexOf(':')>0 || value.indexOf('@')>0) return true;
		return false;
	}

	public String createName(XlsModel model) {
		String value = model.getInfos()[1];
//		we cant use a reg exp since not all context free grammar production(xpath grammar is) cant be matched using reg exp
//		use a (poor) euristic
		int slashPos = value.indexOf('/');
		int colonPos = value.indexOf(':');
		if (slashPos>0 || colonPos>0 || value.indexOf('@')>0) {
			if (colonPos<0 && slashPos<0) {
				if (value.startsWith("@")) return value.substring(1);
				else return value.replaceAll("@", "_");
			} else {
				return value.substring(max(slashPos, colonPos)).replaceAll("@", "_");
			}
		}
		return value;
	}
	
	int max(int a, int b) {
		if (b>a) return b;
		else return a;
	}

}
