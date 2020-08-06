/**
 * Created on 10/mar/2009
 */
package it.nch.eb.common.converter.pmtreq.dbtr.models.xml;

import it.nch.eb.common.converter.pmtreq.dbtr.models.PmtReqDbtrStatusInfo;


/**
 * @author gdefacci
 */
public class XmlPmtReqDbtrStatusInfo extends PmtReqDbtrStatusInfo{
	
	public boolean getShowStsRsnInf() {
		boolean toShow = false;
		if (!toShow) toShow = getRecord30().getBic() != null;
		if (!toShow) toShow = getRecord30().getStsRsn_Cd() != null;
		if (!toShow) toShow = getRecord30().getElmRfc() != null;
		if (!toShow) toShow = getRecord34() != null && !getRecord34().isEmpty();
		return toShow;
	}

}
