package it.tasgroup.ge.bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.Eventi;
import it.tasgroup.ge.enums.EnumStatoConfigurazioneEvento;
import it.tasgroup.ge.enums.EnumStatoEventi;
import it.tasgroup.ge.helpers.GestoreEventiHelper;
import it.tasgroup.ge.helpers.GestoreEventiHelperFactory;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.idp.util.ServiceLocalMapper;

@Stateless
public class GestoreEventiBean implements GestoreEventiInterface {
	/*** Resources ***/
	@Resource
	private EJBContext ctx;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;  

	private final Log logger = LogFactory.getLog(this.getClass());
	

	// gestore singolo evento
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public HashMap<String, String> fireCommunicationEvt (CommunicationEvent e) {
		
		logger.info(this.getClass().getSimpleName() + " fireCommunicationEvt " );
		HashMap<String, String> result = new HashMap<String, String>();
		
		try {
			String info = "OK";
			if (e!=null){
				// recupero helper per il particolare evento
				logger.info(this.getClass().getSimpleName() + " recupero helper per codice evento " + e.getCodiceEvento() );
				
				GestoreEventiHelper helper = GestoreEventiHelperFactory.getHelper(e.getCodiceEvento(),manager);
				
				logger.info(this.getClass().getSimpleName() + "  helper  " + helper.toString());
				
				
				// la configurazione dell'evento dovrebbe arrivare in input nel configurationEvent
				
				CfgEventi cfgEvento = null;
				
				if (e.getCfgEvento() == null) {
					
					logger.info(this.getClass().getSimpleName() + " la configurazione dell'evento e' null " );

					Query queryConfigurazioneEventi = manager.createQuery("SELECT cfgEventi FROM CfgEventi cfgEventi inner join cfgEventi.cfgDestinatari ");
					List<CfgEventi> resultEventi = queryConfigurazioneEventi.getResultList();
					for (CfgEventi cfg : resultEventi) {
						if (cfg.getCodiceEvento().equals(e.getCodiceEvento())) {
							cfgEvento = cfg;
							break;
						}
					}

				} else {
					cfgEvento = e.getCfgEvento();
					logger.info(this.getClass().getSimpleName() + " cfgEvento " + cfgEvento.toString() + cfgEvento.getAttivo());
				}
				

					
					logger.info(this.getClass().getSimpleName() + " verifico se l'evento e' attivo ");
					
					if (cfgEvento.getAttivo()!=null && !cfgEvento.getAttivo().equals("")){
						logger.info(this.getClass().getSimpleName() + " Evento Attivo = " +cfgEvento.getAttivo());
							if (cfgEvento.getAttivo().equals(EnumStatoConfigurazioneEvento.DISATTIVO.getChiave())){
							logger.info(this.getClass().getSimpleName() + " ---> la gestione dell'evento non e' attiva--- EVENTO NON GESTITO "+ cfgEvento.getCodiceEvento());
							throw new RuntimeException("Non e' possibile processare l'evento!!! La sua configurazione non e' attiva!!");
							}
					}
					
					logger.info(this.getClass().getSimpleName() + " getMessaggioLogico ");
					
				try{
					
					info = helper.fireCommunicationEvt(e, cfgEvento);					

				} catch (Exception ex) {
					logger.error(" Errore durante la creazione di un messaggio logico: " + ex.getMessage());
					logger.error(ex);
					throw new RuntimeException(ex);

				}
			}
			
			
			
			 result.put("OK", info);
			 
			
		} catch (PersistenceException ex) {
			logger.error(" Errore di persistenza durante la gestione di un evento" + ex.getMessage());
			logger.error(ex);
			result.put("ERROR",ex.getMessage());
			throw ex;
		} catch (RuntimeException ex) {
			logger.error(" Errore generico durante la gestione di un evento" + ex.getMessage());
			logger.error(ex);
			result.put("ERROR",ex.getMessage());
			ctx.setRollbackOnly();
			throw ex;
		} 	
		return result;
	}

	public void eventNotify(CommunicationEvent e){
		logger.info(this.getClass().getSimpleName() + " eventNotify " );
		// prima di procedere controllo se l'evento e' attivo
		
		//CfgEventi confEvento = (CfgEventi)manager.find(CfgEventi.class, new Long(e.getCodiceEvento()));
		
		//recupero i dati dell'evento
		Query queryEvento = manager.createNamedQuery("ConfEventoByCodEvento");
		queryEvento.setParameter("codiceEvento", e.getCodiceEvento());
		CfgEventi confEvento = (CfgEventi) queryEvento.getSingleResult();			
		
		if (confEvento !=null){
			if (confEvento.getAttivo()!=null){
				if (confEvento.getAttivo().equals(EnumStatoConfigurazioneEvento.ATTIVO.getChiave())){
					Eventi nuovoEvento = new Eventi();
					nuovoEvento.setCodiceEvento(e.getCodiceEvento());
					nuovoEvento.setDatiEvento(e.getObjectId());
					nuovoEvento.setNumeroTentativi(0);
					nuovoEvento.setStato(EnumStatoEventi.INATTESA.getChiave());
					manager.persist(nuovoEvento);
					manager.flush();
					logger.info(this.getClass().getSimpleName() + " EVENTO INSERITO NELLA GEV_EVENTI " );
				}else{
					logger.info(this.getClass().getSimpleName() + " EVENTO CON CODICE " + e.getCodiceEvento() + " NON ATTIVO" );
				}
			}else{
				logger.info(this.getClass().getSimpleName() + " EVENTO CON CODICE " + e.getCodiceEvento() + " NON CONFIGURATO" );
			}
		}else{
			logger.info(this.getClass().getSimpleName() + " NON SONO RIUSCITO A RECUPERARE LA CONFIGURAZIONE EVENTO CON CODICE" );
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eventUpdate(String status, String descr, Long id, Boolean aggiornaData, int numeroTent) {

		String queryString = "Update Eventi eventi " +
				" set eventi.stato = :stato, eventi.descrStato = :descr, eventi.dataUltimaEsecuz=:dataEsec  ";
		if (aggiornaData){
			queryString = queryString + ", eventi.numeroTentativi=:numT ";
		}
		queryString = queryString + "where eventi.id=:id";
		Query queryUpdateEvento = manager.createQuery (queryString);
		
		
		queryUpdateEvento.setParameter("stato", status);
		queryUpdateEvento.setParameter("descr", descr);
		queryUpdateEvento.setParameter("id", id);
		queryUpdateEvento.setParameter("dataEsec", new Timestamp(new Date().getTime()));
		if (aggiornaData){
			queryUpdateEvento.setParameter("numT", numeroTent);
		}
		int esitoUpd = queryUpdateEvento.executeUpdate();
		logger.debug(this.getClass().getSimpleName() + " Aggiornato stato evento con id: " + id + "allo stato: " + status );
	}

}
