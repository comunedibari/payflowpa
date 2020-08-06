package it.nch.eb.common.converter.pmtreq.cdtr.models.xml;

public class XmlRecord68sModel extends it.nch.eb.common.converter.pmtreq.cdtr.models.Record68sModel {
	public boolean getShowPrvtId(){
		return getRecord69() != null && getRecord69().size() > 0;
	}

	public boolean getShowOrgId(){
		return getRecord68() != null && (
				getRecord68().getBic() != null ||
				getRecord68().getIbei() != null ||
				getRecord68().getBei() != null ||
				getRecord68().getEangln() != null ||
				getRecord68().getUschu() != null ||
				getRecord68().getDuns() != null ||
				getRecord68().getBkPtyId() != null ||
				getRecord68().getTaxIdNb() != null ||
				getRecord68().getId() != null);	
	}
}
