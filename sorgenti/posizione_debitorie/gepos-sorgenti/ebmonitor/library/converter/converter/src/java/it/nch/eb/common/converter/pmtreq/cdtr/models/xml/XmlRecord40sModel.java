package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord40sModel extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record40sModel {
	public boolean getShowRmtInf(){
		return (getRecord60() != null && getRecord60().size() > 0 ) ||
			(getXmlRecord62Model() != null && getXmlRecord62Model().size() > 0);		
	}
}
