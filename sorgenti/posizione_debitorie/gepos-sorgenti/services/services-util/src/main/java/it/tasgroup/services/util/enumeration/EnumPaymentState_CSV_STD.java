package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumPaymentState_CSV_STD implements MessageDescription{

	DA_PAGARE 			("DA_PAGARE", "Da Pagare", "export.csv.standard.stato.pagamento.dapagare"),
	PAGATO 				("PAGATO", "Pagato", "export.csv.standard.stato.pagamento.pagato"),
	NON_PAGABILE		("NON_PAGABILE", "Pagato", "export.csv.standard.stato.pagamento.nonpagabile"),
	PAGATO_IRREGOLARE	("PAGATO_IRREGOLARE", "Pagato", "export.csv.standard.stato.pagamento.pagatoirregolare"),
	CHIUSO				("CHIUSO", "Pagato", "export.csv.standard.stato.pagamento.chiuso"),
	CANCELLATO			("CANCELLATO", "Pagato", "export.csv.standard.stato.pagamento.cancellato");
	
	private String chiave;
	
	private String descrizione;
	
	private String chiaveBundle;


	private EnumPaymentState_CSV_STD(String chiave, String descrizione, String chiaveBundle) {
		
		this.chiave = chiave;
		
		this.descrizione = descrizione;
		
		this.chiaveBundle = chiaveBundle;

	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
}
