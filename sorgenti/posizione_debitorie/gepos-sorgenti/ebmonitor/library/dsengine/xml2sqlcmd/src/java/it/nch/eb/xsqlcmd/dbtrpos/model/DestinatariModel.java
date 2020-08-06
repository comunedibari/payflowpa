/**
 * 21/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;

/**
 * @author gdefacci
 */
public class DestinatariModel extends it.nch.eb.xsqlcmd.dbtrpos.gen.model.DestinatariModel {
	
	private static final long serialVersionUID = 9113859680458733604L;
	private it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel flusso;

	public it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel getFlusso() {
		return flusso;
	}

	public void setFlusso(it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel flusso) {
		this.flusso = flusso;
	}

	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n"
				+ StringUtils.toString(this);
	}


}
