package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.util.ArrayList;

public enum EnumLifeCycleAllineaPendenze implements MessageDescription{
	
	ALL_PEND_CART_ASYNC("ALL_PEND_CART_ASYNC", "Allineamento Pendenze Asincrono", "common.label.AllineamentoPendenze"),	
	ALL_PEND_CART_SYNC("ALL_PEND_CART_SYNC", "Allineamento Pendenze Sincrono", "common.label.AllineamentoSync"),
	ALL_PEND_OTF_MX("ALL_PEND_OTF_MX", "Allineamento Pendenze Multi Ente", "common.label.AllineamentoOTFMX"),
	LOADER("LOADER", "Allineamento Pendenze CSV Loader", "common.label.AllineamentoCSVBASICV3");
	//LOADER("LOADER-REST", "Allineamento Pendenze CSV Loader", "common.label.AllineamentoCSVBASICV2");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private ArrayList<EnumStatoAllineaPendenze> statusList;
	private ArrayList<EnumOperazioneCRUD> operationList;

	private EnumLifeCycleAllineaPendenze(String chiave, String descrizione, String chiaveBundle) {
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		fillStatusList();
		fillOperationList();
	}

	private void fillStatusList() {
		
		this.statusList = new ArrayList<EnumStatoAllineaPendenze>();
		
		if ("ALL_PEND_CART_ASYNC".equals(chiave)){
			statusList.add(EnumStatoAllineaPendenze.DA_ELABORARE);
			statusList.add(EnumStatoAllineaPendenze.COMPLETO);
			statusList.add(EnumStatoAllineaPendenze.NON_VALIDO_XSDSCHEMA);
			statusList.add(EnumStatoAllineaPendenze.IN_SPEDIZIONE);
			statusList.add(EnumStatoAllineaPendenze.RISPOSTA_INVIATA);
			statusList.add(EnumStatoAllineaPendenze.RISPOSTA_NON_INVIATA);
		}
			
		if ("ALL_PEND_CART_SYNC".equals(chiave)){
			statusList.add(EnumStatoAllineaPendenze.DA_ELABORARE);
			statusList.add(EnumStatoAllineaPendenze.COMPLETO);
			statusList.add(EnumStatoAllineaPendenze.NON_VALIDO_XSDSCHEMA);
			statusList.add(EnumStatoAllineaPendenze.IN_SPEDIZIONE);
			statusList.add(EnumStatoAllineaPendenze.RISPOSTA_INVIATA_WS);
		}
			
		if ("ALL_PEND_OTF_MX".equals(chiave)){
			statusList.add(EnumStatoAllineaPendenze.RISPOSTA_OK_WS);
			statusList.add(EnumStatoAllineaPendenze.RISPOSTA_KO_WS);				
		}
		
		if ("LOADER".equals(chiave)){
			statusList.add(EnumStatoAllineaPendenze.DA_ELABORARE_LOADER);
			statusList.add(EnumStatoAllineaPendenze.VALIDATO_OK_LOADER);
			statusList.add(EnumStatoAllineaPendenze.VALIDATO_KO_LOADER);
			statusList.add(EnumStatoAllineaPendenze.ESITATO_LOADER);
		}
		//per ora loader vale sia per i caricamenti via soap che via rest
		/*
		if ("LOADER-REST".equals(chiave)){
			statusList.add(EnumStatoAllineaPendenze.DA_ELABORARE_LOADER);
			statusList.add(EnumStatoAllineaPendenze.VALIDATO_OK_LOADER);
			statusList.add(EnumStatoAllineaPendenze.VALIDATO_KO_LOADER);
			statusList.add(EnumStatoAllineaPendenze.ESITATO_LOADER);
		}
		*/
		
				
	}
	
	private void fillOperationList() {
		
		this.operationList = new ArrayList<EnumOperazioneCRUD>();
		
		if ("ALL_PEND_CART_ASYNC".equals(chiave)){
			operationList.add(EnumOperazioneCRUD.DELETE);
			operationList.add(EnumOperazioneCRUD.INSERT);
			operationList.add(EnumOperazioneCRUD.REPLACE);
			operationList.add(EnumOperazioneCRUD.UPDATE_STATUS);
		}
			
		if ("ALL_PEND_CART_SYNC".equals(chiave)){
			operationList.add(EnumOperazioneCRUD.DELETE);
			operationList.add(EnumOperazioneCRUD.INSERT);
			operationList.add(EnumOperazioneCRUD.REPLACE);
			operationList.add(EnumOperazioneCRUD.UPDATE_STATUS);
		}
			
		if ("ALL_PEND_OTF_MX".equals(chiave)){
			operationList.add(EnumOperazioneCRUD.INSERT);				
		}
		
		if ("LOADER".equals(chiave)){
			operationList.add(EnumOperazioneCRUD.MIXED);
			
		}
		//per ora loader vale sia per i caricamenti via soap che via rest
		/*
		if ("LOADER-REST".equals(chiave)){
			operationList.add(EnumOperazioneCRUD.MIXED);
			
		}*/
				
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}
	
	public static EnumLifeCycleAllineaPendenze getByKey(String chiave) {
		
		for (EnumLifeCycleAllineaPendenze item : EnumLifeCycleAllineaPendenze.values()) {
            
			if (item.getChiave().equals(chiave))
				
                return item;
                
        }
		
        return null;
    }

	public ArrayList<EnumStatoAllineaPendenze> getStatusList() {
		return statusList;
	}

	public void setStatusList(ArrayList<EnumStatoAllineaPendenze> statusList) {
		this.statusList = statusList;
	}
	
	public String getStatusListString() {
		
		StringBuffer statusBuffer = new StringBuffer();
		
		for(EnumStatoAllineaPendenze status : statusList)
			
			statusBuffer.append(status.getChiave()).append("|");
			
			
		return statusBuffer.toString();
		
	}

	public ArrayList<EnumOperazioneCRUD> getOperationList() {
		return operationList;
	}

	public void setOperationList(ArrayList<EnumOperazioneCRUD> operationList) {
		this.operationList = operationList;
	}

}
