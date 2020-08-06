package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord84Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record84Model {
	public boolean getShowId(){
		return getShowOrgId() || getShowProvtId();
	}
	
	public boolean getShowOrgId(){
		return getBei() != null || 
			getTaxIdNb() != null || 
			getId() != null;
	}
	
	public boolean getShowProvtId(){
		return getTaxIdNb1() != null || getId1() != null;
	}
}
