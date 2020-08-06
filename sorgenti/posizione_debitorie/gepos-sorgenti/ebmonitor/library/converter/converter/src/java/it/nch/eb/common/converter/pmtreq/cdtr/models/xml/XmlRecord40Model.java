package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord40Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record40Model {
	public boolean getShowPmtTpInf(){
		return getPrtry() != null || getPrtry1() != null || getCtgyPurp() != null;
	}
	
	public boolean getShowRltdRmtInf(){
		return getRmtId() != null || getRmtLctnMtd() != null || getRmtLctnElctrncAdr() != null;		
	}
}
