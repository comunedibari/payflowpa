package it.tasgroup.ge.helpers;

import javax.persistence.EntityManager;

import it.tasgroup.ge.bean.GestoreEventiInterface;
import it.tasgroup.ge.helpers.avvisodiriaccreditoente.AvvisoRiaccreditoEnteHelperImpl;
import it.tasgroup.ge.helpers.avvisodiricezionerimborso.AvvisoRicezioneRimborsoHelperImpl;
import it.tasgroup.ge.helpers.avvisopendenzainscadenza.AvvisoPendenzaInScadenzaHelperImpl;
import it.tasgroup.ge.helpers.avvisoricezioneRT.AvvisoRicezioneRTHelperImpl;
import it.tasgroup.ge.helpers.invioquietanza.InvioQuietanzaHelperImpl;
import it.tasgroup.ge.helpers.segnalazionebonificodiriaccreditofallito.SegnalazioneBonificoRiaccreditoFallitoHelperImpl;
import it.tasgroup.ge.helpers.segnalazionepagamentospontaneo.SegnalazionePagamentoSpontaneoHelperImpl;
import it.tasgroup.ge.opentoscana.AvvisoPagamentoEseguito;
import it.tasgroup.ge.opentoscana.AvvisoPagamentoInScadenza;

public class GestoreEventiHelperFactory {

	public static final int INVIO_QUIETANZA_PAGAMENTO_HELPER =1;
	public static final int AVVISO_PENDENZA_IN_SCADENZA_HELPER = 2;
	public static final int AVVISO_DI_RIACCREDITO_HELPER = 3;
	public static final int AVVISO_DI_RICEZIONE_RIMBORSO = 4;
	public static final int SEGNALAZIONE_BONIFICO_RIACCREDITO_FALLITO = 5;
	public static final int SEGNALAZIONE_PAGAMENTO_SPONTANEO = 6;
	public static final int AVVISO_RICEZIONE_RT = 7;
	public static final int AVVISATURA_PUSH_INSERT = 101;
	public static final int AVVISATURA_PUSH_DELETE = 102;
	public static final int AVVISATURA_PUSH_UPDATE = 103;
	public static final int AVVISO_DI_PAGAMENTO_ESEGUITO = 110;
	
	
	public static GestoreBatchEventiHelper getBatchEventiHelper(EntityManager manager,GestoreEventiInterface gestoreEventiProxy) {
		GestoreBatchEventiHelper bjh  = new GestoreBatchEventiHelperImpl(manager,gestoreEventiProxy);
		return bjh;
	}

	public static GestoreEventiHelper getHelper(String helperType, EntityManager manager) {
		GestoreEventiHelper bjh = null;
		Integer hType = new Integer(helperType);
		switch (hType) {
			case INVIO_QUIETANZA_PAGAMENTO_HELPER:
				bjh = new InvioQuietanzaHelperImpl(manager);
				break;
			case AVVISO_PENDENZA_IN_SCADENZA_HELPER:
				bjh = new AvvisoPendenzaInScadenzaHelperImpl(manager);
				break;
			case AVVISO_DI_RIACCREDITO_HELPER:
				bjh = new AvvisoRiaccreditoEnteHelperImpl(manager);
				break;
			case AVVISO_DI_RICEZIONE_RIMBORSO:
				bjh = new AvvisoRicezioneRimborsoHelperImpl(manager);
				break;
			case SEGNALAZIONE_BONIFICO_RIACCREDITO_FALLITO:
				bjh = new SegnalazioneBonificoRiaccreditoFallitoHelperImpl(manager);
				break;
			case SEGNALAZIONE_PAGAMENTO_SPONTANEO:
				bjh = new SegnalazionePagamentoSpontaneoHelperImpl(manager);
				break;
			case AVVISO_RICEZIONE_RT:
				bjh = new AvvisoRicezioneRTHelperImpl(manager); 
				break;
			case AVVISATURA_PUSH_INSERT:
				bjh = new AvvisoPagamentoInScadenza(manager);
				break;
			case AVVISATURA_PUSH_DELETE:
				bjh = new AvvisoPagamentoInScadenza(manager);
				break;
			case AVVISATURA_PUSH_UPDATE:
				bjh = new AvvisoPagamentoInScadenza(manager);
				break;
			case AVVISO_DI_PAGAMENTO_ESEGUITO:
				bjh = new AvvisoPagamentoEseguito(manager);
				break;
			default:
				bjh = null;
		}
		return bjh;
	}
} 