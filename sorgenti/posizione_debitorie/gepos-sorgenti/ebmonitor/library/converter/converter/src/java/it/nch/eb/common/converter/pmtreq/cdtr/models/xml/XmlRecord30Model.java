package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord30Model extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record30Model {
	public boolean getShowPurp(){
		return getCd() != null && getPrtry1() != null;
	}
}
