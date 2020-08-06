/**
 * Created on 13/giu/08
 */
package it.nch.eb.common.converter.pmtreq.advinf.models.xml;

import it.nch.eb.common.converter.pmtreq.advinf.models.Record03Model;


/**
 * @author gdefacci
 */
public class XmlRecord03Model extends Record03Model {
	
	public boolean isShowAmt() {
		return !isZero(getAmt());
	}
	
	public boolean isShowXcghRate() {
		return !isZero(getXcghRate());
	}
	
	public boolean isShowEqvAmt() {
		return isShowAmt() || isShowXcghRate();
	}
	
	public boolean isShowTxDtls() {
		return (!isEmpty(getCd()) || !isEmpty(getInf())); 
	}

	public static boolean isZero(String amt2) {
		if (isEmpty(amt2)) return true;
		if (amt2.equals("0")) return true;
		return false;
	}

	public static boolean isEmpty(String amt2) {
		if (amt2==null) return true;
		if (amt2.trim().equals("")) return true;
		return false;
	}
	

}
