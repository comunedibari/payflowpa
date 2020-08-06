/**
 * Created on 16/gen/08
 */
package it.nch.eb.flatx.generator.javamodel;

import it.nch.eb.flatx.generator.XlsModel;


/**
 * @author gdefacci
 */
public class FixedValueFieldDeclStrategy implements FieldDeclStrategy {

	public String createInitializer(XlsModel model, int pos) {
		String value = get(model.getInfos(), 0 );
		String conveter = get(model.getInfos(), 1);
		if (conveter==null) return "createFixedValue(" + pos + ", \"" + value + "\");";
		return "createFixedValue(" + pos + "," + conveter +", \"" + value + "\");";
	}

	private String get(String[] infos, int i) {
		if (infos.length <= i) return null;
		return infos[i];
	}

	public String createName(XlsModel xlsModel) {
		if (xlsModel.getInfos().length>=4) {
			if (xlsModel.getInfos()[3]!=null && !"".equals(xlsModel.getInfos()[3])) {
				return xlsModel.getInfos()[3];
			}
		}
		return "fixedValue";
	}

	public boolean match(XlsModel model) {
		return "f".equals(model.getModifier().trim());
	}

}
