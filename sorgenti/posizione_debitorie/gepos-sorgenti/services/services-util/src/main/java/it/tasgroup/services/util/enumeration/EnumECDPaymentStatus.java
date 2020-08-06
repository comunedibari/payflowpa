package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

/**
 * @author pazzik
 * 
 */
public enum EnumECDPaymentStatus implements MessageDescription {

	DA_PAGARE("00", "DA PAGARE", "posizionedebitoria.statoCondizione.daPagare"), 
	PAGATA("01", "PAGATA", "posizionedebitoria.statoCondizione.pagata"), 
	NON_PAGABILE("02", "NON PAGABILE","posizionedebitoria.statoCondizione.nonPagabile");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	EnumECDPaymentStatus(String chiave, String descrizione,
			String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	@Override
	public String getChiave() {
		return chiave;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public static EnumECDPaymentStatus getByKey(String chiave) {
		EnumECDPaymentStatus desiredItem = null; // Default
		for (EnumECDPaymentStatus item : EnumECDPaymentStatus.values()) {
			if (item.getChiave().equals(chiave)) {
				desiredItem = item;
				break;
			}
		}
		return desiredItem;
	}

	public static EnumECDPaymentStatus getFromEnumStatoPagamentoCondizione(
			EnumStatoPagamentoCondizione statoCond) {

		switch (statoCond) {

		case DA_PAGARE:
		case IN_CORSO:
			return DA_PAGARE;

		case PAGATA:
			return PAGATA;

		case NON_PAGABILE_ASSOCIATA_DOCUMENTO:
		case NON_PAGABILE_PAGAMENTO_IRREGOLARE:
		case NON_PAGABILE_PENDENZA_CHIUSA:
		case NON_PAGABILE_RATEIZZAZIONE:
		case NON_PAGABILE_RIMBORSATA:
		case NON_PAGABILE_TERMINI:
			return NON_PAGABILE;
		}

		return null;

	}

}
