package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord62Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record62Model {	
	public boolean getShowCdtrRefInf(){
		return getShowCdtrRefTp() || getCdtrRef() != null;
	}
	
	public boolean getShowCdtrRefTp(){
		return getIssr1() != null || 
		getCd1() != null || getPrtry1() != null;
	}		
	
	public boolean getShowRfrdDocInf(){
		return getShowRfrdDocTp() || getRfrdDocNb() != null;
	}
	
	public boolean getShowRfrdDocTp(){
		return getIssr() != null || 
			getCd() != null || getPrtry() != null;
	}	
}
