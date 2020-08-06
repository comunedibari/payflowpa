/*
 * Created on Sep 25, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.eb.common.converter.pmtreq.advinf.models.xml;

import it.nch.eb.common.converter.pmtreq.advinf.models.Record05Model;

/**
 * @author bastiap
 */
public class XmlRecord05Model extends Record05Model {
	
	public boolean isShowCdtrAcct() {
		return !(
		XmlRecord03Model.isEmpty(this.getIban()) &&
		XmlRecord03Model.isEmpty(this.getBban()) &&
		XmlRecord03Model.isEmpty(this.getId1())) ;
	}
}
