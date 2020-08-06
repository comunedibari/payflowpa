package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord65Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record65Model {
	public boolean getTot(){
		return getTipoImporto().equals("TOT");
	}	
	
	public boolean getSct(){
		return getTipoImporto().equals("SCT");
	}	
	
	public boolean getPag(){
		return getTipoImporto().equals("PAG");
	}
	
	public boolean getNot(){
		return getTipoImporto().equals("NOT");
	}
	
	public boolean getTax(){
		return getTipoImporto().equals("TAX");
	}	
}
