package it.tasgroup.ge.helpers.segnalazionebonificodiriaccreditofallito;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.helpers.GestoreEventiMailHelper;
import it.tasgroup.ge.pojo.ErroreRiaccredito;
import it.tasgroup.ge.pojo.MessaggioLogico;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;



public class SegnalazioneBonificoRiaccreditoFallitoHelperImpl extends GestoreEventiMailHelper {
	
	public SegnalazioneBonificoRiaccreditoFallitoHelperImpl(EntityManager mgr) {
		manager = mgr;
	}
	@Override
	public MessaggioLogico getMessaggioLogico(String datiEvento, CfgEventi confEvento) throws Exception {
		logger.info(this.getClass().getSimpleName()
				+ " recupero dati necessari per la segnalazione di Bonifico di Riaccredito Fallito con id: "
				+ datiEvento);

		MessaggioLogico ml = null;
		ErroreRiaccredito errRiacc = getDatiPerInvioSegnalazione(datiEvento);
		
		if (errRiacc != null) {
			ArrayList<String> destinatariTo = creaListaDestinatari(confEvento, null, TIPO_INVIO_TO);
			ArrayList<String> destinatariCc = creaListaDestinatari(confEvento, null, TIPO_INVIO_CC);
			logger.debug(this.getClass().getSimpleName() + "creazione del messaggio logico");
			ml = creaMessaggioLogico(confEvento, errRiacc, destinatariTo, destinatariCc);
		}
		
		if (ml != null) {
			logger.info(this.getClass().getSimpleName() + "messaggio Logico Creato ");
			logger.info(this.getClass().getSimpleName() + "soggetto: " + ml.getSubject());
			logger.info(this.getClass().getSimpleName() + "testo: " + ml.getContent());
		}
		return ml;
	}
	
	/**
	 * Metodo che recupera i dati necessari per creare il messaggio logico nel caso di una segnalazione di Bonifico di Riaccredito Fallito
	 * @param datiEvento identificativo del bonifico di riaccredito fallito
	 * @return ErroreRiaccredito
	 */
	protected ErroreRiaccredito getDatiPerInvioSegnalazione(String datiEvento) throws Exception{
		
		logger.info(this.getClass().getSimpleName() + " recupero ID_UTENTE_IRIS AMMINISTRATORE BACKOFFICE " );
		Query utenteQuery =  manager.createNativeQuery(
				"select esiti.CODICE_RIFERIMENTO, esiti.IMPORTO, "+ 
				"bon.PROGRESSIVO, boz.ID_PAG_COND_ENTE, boz.RAG_SOCIALE_BENEFICIARIO, " +
				"boz.IBAN_BENEFICIARIO, case.NOME_SUPPORTO "+
				"from BONIFICI_RIACCREDITO bon, ESITI_BONIFICI_RIACCREDITO esiti, " +
				"BOZZE_BONIFICI_RIACCREDITO boz, "+
				"CASELLARIO_INFO case, RENDICONTAZIONI rend "+
				"where bon.ID=esiti.ID_BONIFICI_RIACCREDITO " +
				"and bon.ID=boz.ID_BONIFICI_RIACCREDITO  "+
				"and esiti.ID_RENDICONTAZIONE=rend.ID and rend.ID_CASELLARIO_INFO=case.ID "+
				"AND BON.ID=:idBon");

		
	
		utenteQuery.setParameter("idBon", datiEvento);
		Object res = utenteQuery.getSingleResult();
		ErroreRiaccredito errRiacc=null;
		if (res!=null ){
			errRiacc = new ErroreRiaccredito();
			Object[] result = (Object[]) res;
			errRiacc.setCro((String) result[1]);
			errRiacc.setImporto((BigDecimal) result[2]);
			errRiacc.setProgressivoCBI((Integer) result[3]);
			errRiacc.setCausale((String) result[4]);
			errRiacc.setBeneficiario((String) result[5]);
			errRiacc.setIbanBeneficiario((String) result[6]);
			errRiacc.setNomeSupporto((String) result[7]);
			logger.debug(this.getClass().getSimpleName() + " errore riaccredito: " );
			logger.debug(this.getClass().getSimpleName() + " cro: " + errRiacc.getCro() );
			logger.debug(this.getClass().getSimpleName() + " importo: " + errRiacc.getImporto() );
			logger.debug(this.getClass().getSimpleName() + " progressivo: " + errRiacc.getProgressivoCBI());
			logger.debug(this.getClass().getSimpleName() + " causale: " + errRiacc.getCausale());
			logger.debug(this.getClass().getSimpleName() + " beneficiario: " + errRiacc.getBeneficiario());
			logger.debug(this.getClass().getSimpleName() + " Iban beneficiario: " + errRiacc.getIbanBeneficiario());
			logger.debug(this.getClass().getSimpleName() + " NomeSupporto: " + errRiacc.getNomeSupporto());
		}
		
		return errRiacc;
	}
}
