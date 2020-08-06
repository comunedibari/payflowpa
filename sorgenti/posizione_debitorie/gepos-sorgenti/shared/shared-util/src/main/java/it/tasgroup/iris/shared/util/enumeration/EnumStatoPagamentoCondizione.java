package it.tasgroup.iris.shared.util.enumeration;

/**
 * Questo enumerativo contiene i valori che determinano lo stato
 * "complessivo" di una condizione pagamento rispetto al processo
 * di pagamento in Iris 
 * @author RepettiS
 *
 */
public enum EnumStatoPagamentoCondizione implements MessageDescription{
	
	DA_PAGARE("DA PAGARE","Da pagare, pagabile.","posizionedebitoria.statoPagamentoCondizione.daPagare"),
	PAGATA("PAGATA","Pagata","posizionedebitoria.statoPagamentoCondizione.pagata"),
	IN_CORSO("IN CORSO","Pagamento in corso","posizionedebitoria.statoPagamentoCondizione.inCorso"),
	NON_PAGABILE_TERMINI("NON PAGABILE TERMINI","Non pagabile per termini di validita' o su indicazione dell'ente","posizionedebitoria.statoPagamentoCondizione.nonPagabileTermini"),
	NON_PAGABILE_ASSOCIATA_DOCUMENTO("NON PAGABILE ASSOCIATA DOCUMENTO","Non pagabile in quanto associata a documento emesso e non ancora pagato. Annullare il documento","posizionedebitoria.statoPagamentoCondizione.nonPagabileAssociataDocumento"),
	NON_PAGABILE_RIMBORSATA("NON PAGABILE RIMBORSATA","Non pagabile perche' rimborsata","posizionedebitoria.statoPagamentoCondizione.nonPagabileRimborsata"),
	NON_PAGABILE_RATEIZZAZIONE("NON PAGABILE RATEIZZAZIONE","Pagamento singolo non pagabile in quanto in corso pagamento rateale o viceversa","posizionedebitoria.statoPagamentoCondizione.nonPagabileRateizzazione"),
	NON_PAGABILE_PENDENZA_CHIUSA("NON PAGABILE PENDENZA CHIUSA","Non pagabile, la pendenza e' chiusa","posizionedebitoria.statoPagamentoCondizione.nonPagabilePendenzaChiusa"),
	NON_PAGABILE_PAGAMENTO_IRREGOLARE("NON PAGABILE PAGAMENTO IRREGOLARE","Non pagabile, la condizione e' stata pagata irregolarmente","posizionedebitoria.statoPagamentoCondizione.nonPagabilePagamentoIrregolare");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoPagamentoCondizione(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
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
	
	public String encodeForECD() {
		
		String returnValue;
		
		switch(this) {
			case DA_PAGARE:
				returnValue="DA PAGARE";
				break;
			case IN_CORSO:
				returnValue="PAGAMENTO IN CORSO";
				break;
			case PAGATA:
				returnValue="PAGATA";
				break;
			default:
				returnValue="NON PAGABILE";
				break;
		}
		
		return returnValue;
	}
	

}
