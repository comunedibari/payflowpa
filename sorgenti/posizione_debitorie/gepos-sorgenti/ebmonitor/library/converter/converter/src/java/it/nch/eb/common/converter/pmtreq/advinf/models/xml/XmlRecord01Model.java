/*
 * Created on Sep 25, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.eb.common.converter.pmtreq.advinf.models.xml;

import it.nch.eb.common.converter.pmtreq.advinf.models.Record01Model;

/**
 * @author bastiap
 */
public class XmlRecord01Model extends Record01Model {
	               
	public boolean isShowDbtrAgt() {
		return (XmlRecord03Model.isEmpty(this.getBic()) && 
				XmlRecord03Model.isEmpty(this.getDesc()) );
	}
	
	public boolean getShowDbtrAgt() {
		return isShowDbtrAgt();
	}
}
