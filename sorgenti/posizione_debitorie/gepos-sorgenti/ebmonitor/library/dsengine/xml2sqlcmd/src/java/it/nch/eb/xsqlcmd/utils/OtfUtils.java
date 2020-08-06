/**
 * 04/set/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;

/**
 * @author gdefacci
 */
public class OtfUtils {
	
	public static String backUrl(OtfModel pm) {
		return pm.getUrlBack();
	}
	
	public static String cancelUrl(OtfModel pm) {
		return pm.getUrlCancel();
	}

}
