package it.tasgroup.idp.proxyndp.bean; 

import it.tasgroup.idp.bean.AbstractExecutorRemote;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.bean.ExecutorRemote;
import it.tasgroup.idp.gateway.Contotecnico;
import it.tasgroup.idp.pojo.StoricoData;
import it.tasgroup.idp.proxyndp.utils.PostTimerBusinessMethodInterceptor;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumFlagIncasso;
import it.tasgroup.iris2.pagamenti.MovimentiAccredito;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* 
 * Documentazione dell'algoritmo di riconciliazione: 
 * https://docs.google.com/a/tasgroup.it/document/d/1KADg5VCbL3MTYB4-vfKgMtzvr-vOKdCMNT5xVr1fbkE/edit?usp=sharing
 */

@SuppressWarnings("unchecked")
@Stateless
public class RichiestaRiconciliazioneMovimentiNdp extends AbstractExecutorRemote implements ExecutorLocal, ExecutorRemote {
	private static final String HHRANGE_RICONCILIAZIONI_PROPERTY_NAME = "iris.riconciliazioni.ndp.hhrange";
	private static final Short HHRANGE_RICONCILIAZIONI_DEFAULT_VALUE = 48;
	private static final String NMAX_RICONCILIAZIONI_PROPERTY_NAME = "iris.riconciliazioni.ndp.nmax";
	private static final Short NMAX_RICONCILIAZIONI_DEFAULT_VALUE = 5;
	
	private static final String ACCREDITO_SINGOLO = MovimentiAccredito.TipoAccredito.S.name();
	private static final String ACCREDITO_CUMULATIVO = MovimentiAccredito.TipoAccredito.C.name();
	
	private final Log logger = LogFactory.getLog(this.getClass());
	private Short hhRangeRiconciliazioni;
	private Short nMaxRiconciliazioni;
	
	@EJB(beanName = "ElaboraRiconciliazioneMovimentiNdp")
	private IElaboraRiconciliazioneMovimentiNdp elaboraRiconciliazioneMovimentiNdp;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager em;

	@PostConstruct
	private void init() {
		// Lettura da file di properties del range temporale (in ore) di visibilita' del flusso
		try {
			this.hhRangeRiconciliazioni = Short.parseShort(IrisProperties.getProperty(HHRANGE_RICONCILIAZIONI_PROPERTY_NAME));
			logger.info("Acquisito da file di properties il valore associato al parametro " + HHRANGE_RICONCILIAZIONI_PROPERTY_NAME);
		} catch (Exception e) {
			hhRangeRiconciliazioni = HHRANGE_RICONCILIAZIONI_DEFAULT_VALUE;
			logger.info("Non e' stato possibile acquisire da file di properties il valore associato al parametro " + HHRANGE_RICONCILIAZIONI_PROPERTY_NAME + " - Assunto default: " + HHRANGE_RICONCILIAZIONI_DEFAULT_VALUE);
		}
		// Lettura da file di properties del numero massimo dei tentativi di riconciliazione
		try {
			this.nMaxRiconciliazioni = Short.parseShort(IrisProperties.getProperty(NMAX_RICONCILIAZIONI_PROPERTY_NAME));
			logger.info("Acquisito da file di properties il valore associato al parametro " + NMAX_RICONCILIAZIONI_PROPERTY_NAME);
		} catch (Exception e) {
			nMaxRiconciliazioni = NMAX_RICONCILIAZIONI_DEFAULT_VALUE;
			logger.info("Non e' stato possibile acquisire da file di properties il valore associato al parametro " + NMAX_RICONCILIAZIONI_PROPERTY_NAME + " - Assunto default: " + NMAX_RICONCILIAZIONI_DEFAULT_VALUE);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Interceptors(PostTimerBusinessMethodInterceptor.class)
	public StoricoData executeApplicationTransaction() {
		StoricoData output = null;
		
		// Estraggo la lista dei conti tecnici, una volta sola per tutta la durata della rendicontazione
		// Per evitare di fare questa query per ogni movimento.
		// Il fatto che un movimento sia su un conto tecnico o meno guida la gestione del flag incasso sui pagamenti
		List<Contotecnico> contiTecnici = em.createNamedQuery("Contotecnico.findAll").getResultList();
		
		
		Set<String> setIbanContiTecnici = new HashSet<String>();
		
		if (contiTecnici!=null) {
			for (Contotecnico c: contiTecnici) {
				setIbanContiTecnici.add(c.getIban());
			}
		}
		
		// Individuazione flussi che hanno superato il numero massimo di tentativi di riconciliazione e non cadono entro la data soglia di elaborazione
		Timestamp tsLimite = new Timestamp(System.currentTimeMillis() - hhRangeRiconciliazioni * 60/*min*/ * 60/*sec*/  * 1000/*millis*/);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String testoTsLimite = dateFormat.format(tsLimite); 
		logger.info("Ricerca movimenti di accredito con numero elaborazioni superiore o uguale al massimo consentito (%1) e antecedenti alla data soglia di elaborazione (%2)".replace("%1", String.valueOf(nMaxRiconciliazioni)).replace("%2", testoTsLimite));
		List<MovimentiAccredito> movimentiAccreditoMaxNumElab = em.createNamedQuery("findMovimentiAccreditoToUpdateByElaborazioniAndTsInserimento")
			.setParameter("elaborazioni", this.nMaxRiconciliazioni)
			.setParameter("tsLimite", tsLimite)
			.getResultList();
		if (movimentiAccreditoMaxNumElab == null || movimentiAccreditoMaxNumElab.size() == 0) {
			logger.info("Nessun movimento di accredito con numero elaborazioni superiore o uguale al massimo consentito e antecedente alla data soglia di elaborazione");
		} else {
			logger.info("Trovati %1 movimenti di accredito con numero elaborazioni superiore o uguale al massimo consentito e antecedenti alla data soglia di elaborazione".replace("%1", String.valueOf(movimentiAccreditoMaxNumElab.size())));
			for (MovimentiAccredito movimentiAccredito : movimentiAccreditoMaxNumElab) {
				logger.debug("Update COD_ANOMALIA su record di MOVIMENTI_ACCREDITO con ID %1".replace("%1", String.valueOf(movimentiAccredito.getId())));
				elaboraRiconciliazioneMovimentiNdp.setMaxExecutionOccurredOnMovimentiAccredito(movimentiAccredito);
				logger.debug("Update eseguito");
			}
		}

		// Individuazione flussi da riconciliare
		logger.info("Ricerca movimenti di accredito da riconciliare");
		List<MovimentiAccredito> movimentiAccreditoToProcess = em.createNamedQuery("findMovimentiAccreditoToProcess")
			.getResultList();
		if (movimentiAccreditoToProcess == null || movimentiAccreditoToProcess.size() == 0) {
			logger.info("Nessun movimento di accredito da riconciliare");
			return output;
		}
		logger.info("Trovati %1 movimenti di accredito da processare".replace("%1", String.valueOf(movimentiAccreditoToProcess.size())));
		for (MovimentiAccredito movimentiAccredito : movimentiAccreditoToProcess) {
			Long idAccredito = movimentiAccredito.getId();
			String tipoAccredito = movimentiAccredito.getTipo();
			EnumFlagIncasso flagIncasso;
			
			if(setIbanContiTecnici.contains(movimentiAccredito.getIban())) {
				flagIncasso=EnumFlagIncasso.ACCREDITATO_SU_CONTO_TECNICO;
			} else {
				flagIncasso=EnumFlagIncasso.RIACCREDITATO_A_ENTE;
			}
			
			// TODO verificare la granularità della transazione: in caso di errore / eccezione si deve andare al movimento successivo, non al giro di timer successivo
			if (ACCREDITO_SINGOLO.equalsIgnoreCase(tipoAccredito)) {
				// Riconciliazione accredito singolo
				logger.info("Inizio procedura di riconciliazione accredito singolo per il movimento di accredito con id %1".replace("%1", String.valueOf(idAccredito)));
				elaboraRiconciliazioneMovimentiNdp.doRiconciliazioneAccreditoSingolo(movimentiAccredito,flagIncasso);
				logger.info("Procedura di riconciliazione terminata per il movimento di accredito con id %1".replace("%1", String.valueOf(idAccredito)));
			} else if (ACCREDITO_CUMULATIVO.equalsIgnoreCase(tipoAccredito)) {
				// Riconciliazione accredito cumulativo
				logger.info("Inizio procedura di riconciliazione accredito cumulativo per il movimento di accredito con id %1".replace("%1", String.valueOf(idAccredito)));
				elaboraRiconciliazioneMovimentiNdp.doRiconciliazioneAccreditoCumulativo(movimentiAccredito,flagIncasso);
				logger.info("Procedura di riconciliazione terminata per il movimento di accredito con id %1".replace("%1", String.valueOf(idAccredito)));
			} else {
				// Tipo di riconciliazione non trattabile
				logger.info ("Impossibile riconciliare il flusso con id %1: tipo di accredito non riconosciuto".replace("%1", String.valueOf(idAccredito)));
			}
		}
		return output;
	}
}
