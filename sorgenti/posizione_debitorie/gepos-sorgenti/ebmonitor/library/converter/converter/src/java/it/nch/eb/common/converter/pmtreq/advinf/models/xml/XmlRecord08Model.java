/*
 * Created on Sep 25, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.eb.common.converter.pmtreq.advinf.models.xml;

import it.nch.eb.common.converter.pmtreq.advinf.models.Record08Model;

/**
 * @author bastiap
 */
public class XmlRecord08Model extends Record08Model {
	
	public boolean isShowAssRate() {
		return !XmlRecord03Model.isZero(this.getAssRate());
	}
	
	public boolean isShowAssAmt() {
		return !XmlRecord03Model.isZero(this.getAssAmt());
	}

}
