package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

import it.nch.eb.common.converter.pmtreq.cdtr.models.Record66sModel;

public class XmlRecord66sModel extends Record66sModel {
	public boolean getShowPrvtId(){
		return getRecord67() != null && getRecord67().size() > 0;
	}

	public boolean getShowOrgId(){
		return getRecord66() != null && (
				getRecord66().getBic() != null ||
				getRecord66().getIbei() != null ||
				getRecord66().getBei() != null ||
				getRecord66().getEangln() != null ||
				getRecord66().getUschu() != null ||
				getRecord66().getDuns() != null ||
				getRecord66().getBkPtyId() != null ||
				getRecord66().getTaxIdNb() != null ||
				getRecord66().getId() != null);	
	}
}
