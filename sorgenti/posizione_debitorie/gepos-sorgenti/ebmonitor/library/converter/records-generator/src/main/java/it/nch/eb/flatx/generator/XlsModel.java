/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.generator;


/**
 * @author gdefacci
 */
public class XlsModel {
	
	final String modifier;
	final String[] infos;
	
	public XlsModel(String modifier, String[] infos) {
		this.modifier = modifier;
		this.infos = infos;
	}
	
	public String getModifier() {
		return modifier;
	}
	
	public String[] getInfos() {
		return infos;
	}
	
}
