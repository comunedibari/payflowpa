package it.tasgroup.idp.cart.core.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;

import it.tasgroup.idp.cart.core.dao.IIdpCartDbManager;
import it.tasgroup.idp.cart.core.dto.IdpHeaderDTO;
import it.tasgroup.idp.cart.core.dto.SPCoopHeaderDTO;
import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.model.ErroreComponenteModel;
import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.MessaggioNonGestitoModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;

public class EntitiesUtils {


	public static MessaggioModel getMessaggio(String ente, String sil, String msgId, TipoMessaggio tipoMessaggio,
			Date dataCreazione){
		MessaggioModel m = new MessaggioModel();

		m.setDataCreazione(dataCreazione);
		m.setDataUltimaConsegnaEsito(null);
		m.setDataUltimaConsegnaRichiesta(null);
		m.setEsitoConsegnato(false);
		m.setRichiestaConsegnata(false);
		m.setMsgId(msgId);
		m.setTipo(tipoMessaggio);
		m.setSil(sil);
		m.setSoggetto(ente);	

		return m;
	}

	public static GestioneModel getGestione(TipoGestione tipoGestione, MessaggioModel messaggio,Date dataCreazione){
		GestioneModel gestione = new GestioneModel();

		gestione.setIdEgov(null);
		gestione.setIdMessaggio(messaggio.getId());
		gestione.setInizioGestione(dataCreazione);
		gestione.setTempoGestione(null);
		gestione.setTipo(tipoGestione);
		gestione.setErroreComponente(null);

		return gestione;
	}


	public static MessaggioModel getMessaggio(HttpServletRequest req,SPCoopHeaderDTO spcoopHeaderDTO, IdpHeaderDTO idpHeaderDTO, TipoMessaggio tipoMessaggio,TipoGestione tipoGestione,Date dataCreazione){ 
		MessaggioModel m = new MessaggioModel();

		m.setDataCreazione(dataCreazione);
		m.setDataUltimaConsegnaEsito(null);
		m.setDataUltimaConsegnaRichiesta(null);
		m.setEsitoConsegnato(false);
		m.setRichiestaConsegnata(false);
		m.setMsgId(idpHeaderDTO.getE2eMsgId());
		m.setTipo(tipoMessaggio);
		switch (tipoGestione) {
		case INBOUND:
			m.setSil(idpHeaderDTO.getSenderSysE());
			m.setSoggetto(idpHeaderDTO.getSenderIdE());	
			break;
		case OUTBOUND:
		default:
			m.setSil(idpHeaderDTO.getReceiverSysE());
			m.setSoggetto(idpHeaderDTO.getReceiverIdE());
			break;
		}

		return m;
	}

	public static MessaggioNonGestitoModel getMessaggioNonGestito(HttpServletRequest req,SPCoopHeaderDTO spcoopHeaderDTO , TipoMessaggio tipoMessaggio,IrisException e,Date dataCreazione){ 
		MessaggioNonGestitoModel m = new MessaggioNonGestitoModel();

		m.setDataRicezione(dataCreazione);
		m.setIdEgov(spcoopHeaderDTO.getSpcoopId());
		m.setTipo(tipoMessaggio);
		m.setTipoMittente(spcoopHeaderDTO.getSpcoopTipoMittente());
		m.setMittente(spcoopHeaderDTO.getSpcoopMittente());
		m.setCodErrore(e.getCodiceErrore());
		m.setDescrErrore(e.getFaultString());

		return m;
	}

	public static MessaggioNonGestitoModel getMessaggioNonGestito(String ente, String sil, String msgId, TipoMessaggio tipoMessaggio,IrisException e,Date dataCreazione){
		MessaggioNonGestitoModel m = new MessaggioNonGestitoModel();

		m.setDataRicezione(dataCreazione);
		m.setIdEgov(null);
		m.setTipo(tipoMessaggio);
		m.setTipoMittente(null);
		m.setMittente(null);
		m.setCodErrore(e.getCodiceErrore());
		m.setDescrErrore(e.getFaultString());

		return m;
	}

	public static GestioneModel getGestione(HttpServletRequest req,SPCoopHeaderDTO spcoopHeaderDTO,
			IdpHeaderDTO idpHeaderDTO, TipoGestione tipoGestione, MessaggioModel messaggio,Date dataCreazione){
		GestioneModel gestione = new GestioneModel();

		gestione.setIdEgov(spcoopHeaderDTO.getSpcoopId());
		gestione.setIdMessaggio(messaggio.getId());
		gestione.setInizioGestione(dataCreazione);
		gestione.setTempoGestione(null);
		gestione.setTipo(tipoGestione);
		gestione.setErroreComponente(null);

		return gestione;
	}


	public static ErroreComponenteModel getErroreComponenteOutbound(IrisException e){
		//  codErrore con il tipo di Eccezione e descErrore con il messaggio.
		ErroreComponenteModel err = new ErroreComponenteModel();

		err.setCodErrore(e.getCodiceErrore()); 
		err.setDescrErrore(e.getFaultString());
		err.setCodiceComponente(e.getComponenteResponsabile()); 

		return err;
	}

	public static ErroreComponenteModel getErroreComponenteInbound(IrisException e){
		//  codErrore con il tipo di Eccezione e descErrore con il messaggio.
		ErroreComponenteModel err = new ErroreComponenteModel();

		err.setCodErrore(e.getCodiceErrore()); 
		//err.setCodErrore(e.getClass().getName()); 
		err.setDescrErrore(e.getMessage());
		err.setCodiceComponente(e.getComponenteResponsabile()); 

		return err;
	}

	public static void aggiornaMessaggioGestione(IIdpCartDbManager idpCartDbManager, Log log, MessaggioModel messaggio, GestioneModel gestione,
			String seqMsgric, Date dataRicezione,IrisException eccezioneRilevata, TipoGestione tipoGestione,String esecutoreOperazione, boolean sincrono) throws IrisException {

		Date dataFineGestione = new Date();
		if( (tipoGestione.equals(TipoGestione.INBOUND) && (messaggio.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) || sincrono))
				|| (tipoGestione.equals(TipoGestione.OUTBOUND) && messaggio.getTipo().equals(TipoMessaggio.INFORMATIVA_PAGAMENTO) && !sincrono)) {
			messaggio.setDataUltimaConsegnaRichiesta(dataFineGestione);
		} else {
			messaggio.setDataUltimaConsegnaEsito(dataFineGestione);
		}
		
		if(eccezioneRilevata != null && gestione != null){
			eccezioneRilevata.setComponenteResponsabile(esecutoreOperazione); 
			ErroreComponenteModel err = null; 

			switch (tipoGestione) {
			case INBOUND:
				err =EntitiesUtils.getErroreComponenteInbound(eccezioneRilevata);	
				break;
			case OUTBOUND:
			default:
				err = EntitiesUtils.getErroreComponenteOutbound(eccezioneRilevata);
				break;
			}
			gestione.setErroreComponente(err);
		}

		try {
			long tempoGestione = dataFineGestione.getTime() - dataRicezione.getTime();
			if(gestione != null)
				gestione.setTempoGestione(tempoGestione);
			idpCartDbManager.aggiornaGestione(messaggio, gestione,tipoGestione,esecutoreOperazione,sincrono);				
		} catch (Exception e) {
			log.error("[" + seqMsgric + "] Errore durante l'aggiornamento del messaggio: " + e.getMessage(),e);
			throw new IrisException("IDP003", "Errore durante l'aggiornamento del messaggio", "Errore durante l'aggiornamento del messaggio", e);
		}
	}

}
