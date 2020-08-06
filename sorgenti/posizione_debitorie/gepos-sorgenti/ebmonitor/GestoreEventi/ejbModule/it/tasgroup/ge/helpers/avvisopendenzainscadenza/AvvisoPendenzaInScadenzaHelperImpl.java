package it.tasgroup.ge.helpers.avvisopendenzainscadenza;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.helpers.GestoreEventiMailHelper;
import it.tasgroup.ge.pojo.MessaggioLogico;
import it.tasgroup.ge.pojo.PendenzaInScadenza;
import it.tasgroup.idp.util.IrisProperties;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Query;



public class AvvisoPendenzaInScadenzaHelperImpl extends GestoreEventiMailHelper {

	public AvvisoPendenzaInScadenzaHelperImpl(EntityManager mgr) {
		manager = mgr;
	}
	
	private static final String QUERY_ESTRAI_DATI_PENDENZA_IN_SCADENZA  = ""
			+ "SELECT C.CD_TRB_ENTE, C.DT_SCADENZA, PE.DE_CAUSALE, "
			+ "ENTE.DE_TRB, C.IM_TOTALE, DE.CO_DESTINATARIO, PE.NOTE  "
			+ "FROM CONDIZIONI C,JLTPEND PE, JLTENTR ENTE, JLTDEPD DE  "
			+ "WHERE C.ID_PENDENZA = PE.ID_PENDENZA AND ENTE.ID_ENTE=PE.ID_ENTE AND ENTE.CD_TRB_ENTE=PE.CD_TRB_ENTE AND PE.ID_PENDENZA = DE.ID_PENDENZA AND C.ID_CONDIZIONE=:idCondizione ";
	
	@Override
	public MessaggioLogico getMessaggioLogico(String datiEvento, CfgEventi confEvento) throws Exception {
		logger.info(this.getClass().getSimpleName() + " recupero dati dalla tabella delle distinte di pagamento");
		MessaggioLogico ml = null;
		PendenzaInScadenza pendenzaInScadenza = getDatiPerInvioSegnalazione(datiEvento);
		
		logger.info(this.getClass().getSimpleName() + " dati recuperati dalla tabella delle distinte di pagamento");
		
	

		if (pendenzaInScadenza != null) {
			String url = IrisProperties.getProperty(GESTORE_EVENTI_URL_HOME_PAGE, "www.iris.it", false);
			pendenzaInScadenza.setUrl_Home_Page(url);
			logger.info(this.getClass().getSimpleName() + "creazione lista destinatari");
			ArrayList<String> destinatariTo = creaListaDestinatari(confEvento, pendenzaInScadenza.getUsername(), TIPO_INVIO_TO);
			ArrayList<String> destinatariCc = creaListaDestinatari(confEvento, pendenzaInScadenza.getUsername(), TIPO_INVIO_CC);
			logger.info (this.getClass().getSimpleName() + "creazione del messaggio logico");
			ml = creaMessaggioLogico(confEvento, pendenzaInScadenza, destinatariTo, destinatariCc);
		}else {
			logger.info(this.getClass().getSimpleName() + "pendenzaInScadenza è null");
		}
		
		if (ml != null) {
			System.out.println("messaggio Logico Creato: " + ml.toString());
			logger.info(this.getClass().getSimpleName() + "messaggio Logico Creato ");
			logger.info(this.getClass().getSimpleName() + "soggetto: " + ml.getSubject());
			logger.info(this.getClass().getSimpleName() + "testo: " + ml.getContent());

		}

		return ml;
	}
	
	/**
	 * Metodo che recupera i dati necessari per creare il messaggio logico nel caso di una segnalazione di pendenza in scadenza
	 * @param datiEvento identificativo della condizione di pagamento
	 * @return PendenzaInScadenza
	 */
	protected PendenzaInScadenza getDatiPerInvioSegnalazione(String datiEvento) throws Exception{
		
		logger.info(this.getClass().getSimpleName() + " recupero DATI PER INVIO SEGNALAZIONE SCADENZA " );
		Query utenteQuery =  manager.createNativeQuery(QUERY_ESTRAI_DATI_PENDENZA_IN_SCADENZA);

		logger.info(this.getClass().getSimpleName() + " recupero DATI PER INVIO SEGNALAZIONE SCADENZA: con id " +datiEvento + "res=");
		
		Object res= null;
		try {
		utenteQuery.setParameter("idCondizione", datiEvento);
		res = utenteQuery.getSingleResult();
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " eccezione "+ e.getMessage() + e.getStackTrace());
			
			throw e;
		}
		
		logger.info(this.getClass().getSimpleName() + " recuperati DATI PER INVIO SEGNALAZIONE SCADENZA: con id " +datiEvento + "res=");
		PendenzaInScadenza pendenzaScad=null;
		if (res!=null ){
			pendenzaScad = new PendenzaInScadenza();
			DecimalFormat    df = (DecimalFormat)NumberFormat.getInstance(Locale.ITALY);	 
			df.applyPattern("#,##0.00");
			
			Object[] result = (Object[]) res;
			pendenzaScad.setTributo((String) result[0]);
			pendenzaScad.setDataScadenza( new SimpleDateFormat("dd-MM-yyyy").format(((Date) result[1])));  		
			pendenzaScad.setDescrizionePendenza((String) result[2]);
			pendenzaScad.setCausale((String) result[3]);		
			pendenzaScad.setImporto( df.format(result[4]).toString());
			pendenzaScad.setUsername((String) result[5]);
			//String note = (String) result[6];
			
			logger.debug(this.getClass().getSimpleName() + " pendenza in scadenza: " );
			logger.debug(this.getClass().getSimpleName() + " importo: " + pendenzaScad.getImporto() );
			logger.debug(this.getClass().getSimpleName() + " CAUSALE: " + pendenzaScad.getCausale() );
			logger.debug(this.getClass().getSimpleName() + " data scadenza: " + pendenzaScad.getDataScadenza());
			logger.debug(this.getClass().getSimpleName() + " tributo: " + pendenzaScad.getTributo());
			logger.debug(this.getClass().getSimpleName() + " descrizione pendenza: " + pendenzaScad.getDescrizionePendenza());
		}
		
		return pendenzaScad;
	}
}
