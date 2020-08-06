package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord80Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record80Model {
	public boolean getShowUltmtDbtr(){
		return getNm() != null || getCtry() != null;
	}
	
	public boolean getShowId(){
		return getTaxIdNb() != null || getId() != null;
	}
}
