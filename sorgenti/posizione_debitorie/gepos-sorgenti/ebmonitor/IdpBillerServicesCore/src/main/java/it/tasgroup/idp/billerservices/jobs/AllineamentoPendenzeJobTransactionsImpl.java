package it.tasgroup.idp.billerservices.jobs;

import it.tasgroup.idp.billerservices.api.GestorePendenze;
import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumEsitoAllineamentoPendenza;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni.EnumStatoTrasmissione;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.DatiAllineamento;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.iuvgenerator.IUVGenerator;
import it.tasgroup.idp.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.idp.util.ServiceLocalMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AllineamentoPendenzeJobTransactionsImpl implements AllineamentoPendenzeJobTransactions {
	
	private final Log logger = LogFactory.getLog(this.getClass());

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;

	@EJB(name="IUVGenerator")
	private IUVGeneratorLocal iuvGenerator;
	
	@Override
	public void registrazioneTrasmissione(
			DatiPiazzaturaFlusso datiPiazzaturaFlusso, String file) {
		GestoreTrasmissioni.insertNewTrasmissione(datiPiazzaturaFlusso, file, em);
		
	}

	
	
	@Override
	public void cambioStatoTrasmissione(
			DatiPiazzaturaFlusso datiPiazzaturaFlusso,
			EnumStatoTrasmissione nuovoStato) {
		
		GestoreTrasmissioni.updateStatoTrasmissione(datiPiazzaturaFlusso.senderId,      
				                                    datiPiazzaturaFlusso.senderSys, 
				                                    datiPiazzaturaFlusso.e2eMsgId, 
				                                    nuovoStato, 
				                                    em);
		
	}



	@Override
	public void registrazioneEsitoTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso,
			String msgEsito, EnumStatoTrasmissione nuovoStatoTrasmissione) {
		// Inserisco esito e cambio stato in transazione
		GestoreTrasmissioni.insertNewEsito(datiPiazzaturaFlusso, msgEsito, em);
		GestoreTrasmissioni.updateStatoTrasmissione(datiPiazzaturaFlusso.senderId, datiPiazzaturaFlusso.senderSys, datiPiazzaturaFlusso.e2eMsgId, nuovoStatoTrasmissione, em);
	}

	@Override
	public EnumEsitoAllineamentoPendenza allineamentoPendenza(DatiAllineamento datiAllineamento)
			throws LoaderException {
		
		//TODO: estendere al caso generico multi-condizione
		//TODO: rivedere il metodo di allineamento
		
		Pendenze p = datiAllineamento.pendenzaDaAllineare;
		CondizioniPagamento c=p.getCondizioniPagamento().iterator().next();
		
//		return GestorePendenze.allineaPendenzaMonoCondizione( c, 
//				                                              !EnumTipoAllineamento.INSERT.equals(datiAllineamento.tipoAllineamento), 
//				                                              true, 
//				                                              !EnumTipoAllineamento.INSERT.equals(datiAllineamento.tipoAllineamento), 
//				                                              EnumTipoAllineamento.UPDATE_STATUS.equals(datiAllineamento.tipoAllineamento), 
//				                                              em);

		return GestorePendenze.allineaPendenza(p, datiAllineamento.tipoAllineamento, true, em,iuvGenerator);
		
	}

	@Override
	public void persistEsitoPendenza(EsitiPendenza esito, ErroriEsitiPendenza errore) {
		
		em.persist(esito);
		if (errore!=null) {
			em.persist(errore);
		}
				
	}

	
	
	
}
