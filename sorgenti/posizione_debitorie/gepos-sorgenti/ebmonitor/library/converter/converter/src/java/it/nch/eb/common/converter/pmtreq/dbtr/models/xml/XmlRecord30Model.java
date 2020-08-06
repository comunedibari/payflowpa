/**
 * Created on 10/mar/2009
 */
package it.nch.eb.common.converter.pmtreq.dbtr.models.xml;

import it.nch.eb.common.converter.pmtreq.dbtr.models.Record30Model;


/**
 * @author gdefacci
 */
public class XmlRecord30Model extends Record30Model {
	
	public boolean getShowStsRsn() {
		return getStsRsn_Cd()!=null || getElmRfc()!=null; 
	}
	
	public boolean getShowDpsrPurp() {
		return getPurp_Prtry()!=null || getPurp_CD() !=null ;
			
	}

}
