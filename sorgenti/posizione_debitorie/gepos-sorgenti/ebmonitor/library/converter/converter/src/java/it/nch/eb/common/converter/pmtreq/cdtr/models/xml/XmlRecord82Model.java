package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord82Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record82Model{
	public boolean getShowId(){
		return getShowOrgId() || getShowPrvtId();
	}
	
	public boolean getShowOrgId(){
		return getBei() != null || getTaxIdNb() != null || getId() != null;		
	}
	
	public boolean getShowPrvtId(){
		return getTaxIdNb1() != null || getId1() != null;
	}	
	
	public boolean getShowCdtrAcct(){
		return getIban() != null || getBban() != null;
	}
}
