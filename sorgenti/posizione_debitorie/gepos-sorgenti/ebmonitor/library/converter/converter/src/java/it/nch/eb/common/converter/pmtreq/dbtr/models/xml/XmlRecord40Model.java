/**
 * Created on 10/mar/2009
 */
package it.nch.eb.common.converter.pmtreq.dbtr.models.xml;

import it.nch.eb.common.converter.pmtreq.dbtr.models.Record40Model;


/**
 * @author gdefacci
 */
public class XmlRecord40Model extends Record40Model {
	
	public boolean getShowPmtTpInf() {
		return getSvcLvl_prtry()!=null || getLclInstrm_prtry()!=null || getCtgyPurp()!=null;
	}

}
