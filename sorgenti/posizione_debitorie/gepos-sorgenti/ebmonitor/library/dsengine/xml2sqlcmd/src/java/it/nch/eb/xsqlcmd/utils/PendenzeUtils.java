/**
 * 04/set/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public class PendenzeUtils {
	
	public static String pendenzaId(PendenzeModel pm) {
//		return pm.getIdPendenzaEnte();
		return pm.getIdPendenzaEnte() + ":" + pm.getCdTrbEnte() + ":" + pm.getIdEnte();
	}
	
	public static String pendenzaDescId(PendenzeModel pm) {
		return pm.getIdPendenzaEnte() + ":" + pm.getCdTrbEnte() + ":" + pm.getIdEnte() + ":" + pm.getOperationName();
	}

}
