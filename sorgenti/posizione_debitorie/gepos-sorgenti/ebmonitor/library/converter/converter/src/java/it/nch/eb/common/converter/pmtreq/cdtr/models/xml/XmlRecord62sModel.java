package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

import it.nch.eb.common.converter.pmtreq.cdtr.models.Record62sModel;


public class XmlRecord62sModel extends Record62sModel {
	public boolean getShowInvcr(){
		return getXmlRecord66Model() != null;
	}

	public boolean getShowInvcee(){
		return getXmlRecord68Model() != null;
	}
	
	public boolean getShowRfrdDocTp() {
		return getRecord62().getShowRfrdDocTp();
	}
}
