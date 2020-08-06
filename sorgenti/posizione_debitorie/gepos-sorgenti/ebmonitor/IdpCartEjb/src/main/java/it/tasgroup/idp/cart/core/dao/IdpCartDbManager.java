package it.tasgroup.idp.cart.core.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.MessaggioNonGestitoModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.model.converter.EntityConverter;
import it.tasgroup.idp.cart.orm.monitoraggio.Gestioni;
import it.tasgroup.idp.cart.orm.monitoraggio.Messaggi;
import it.tasgroup.idp.cart.orm.monitoraggio.MessaggiNonGestiti;

@Stateless
public class IdpCartDbManager  implements IIdpCartDbManager{

	@PersistenceContext(unitName="dscmtCart")
	private EntityManager entityManager;

	private final Log log = LogFactory.getLog(this.getClass());

	@PostConstruct
	public void init(){
		log.info("IdpCartDbManager startup compleatato."); 
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long inserisciMessaggioNonGestito(MessaggioNonGestitoModel messaggio,String esecutoreOperazione) throws Exception{

		if (System.getProperty("iris.flag.tracking") == null) {
			log.info("Inserimento MessaggioNonGestito DISABILITATO - system property iris.flag.tracking = null");
			return null;
		}

		log.info("Inserimento MessaggioNonGestito -> Data Registrazione: ["+messaggio.getDataRicezione()+"] IdEgov: ["+messaggio.getIdEgov()
		+"] Mittente: ["+messaggio.getTipoMittente()+messaggio.getMittente()+"] Errore: ["+messaggio.getCodErrore()+"] in corso...");

		try{
			MessaggiNonGestiti msgNonGestVo = EntityConverter.getMessaggioNonGestitoVO(messaggio);

			msgNonGestVo.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			msgNonGestVo.setOpInserimento(esecutoreOperazione); 

			entityManager.persist(msgNonGestVo);
			entityManager.flush();
			
			log.info("Inserimento MessaggioNonGestito -> Data Registrazione: ["+messaggio.getDataRicezione()+"] IdEgov: ["+messaggio.getIdEgov()
			+"] Mittente: ["+messaggio.getTipoMittente()+messaggio.getMittente()+"] Errore: ["+messaggio.getCodErrore()+"] completato.");

			return msgNonGestVo.getId();
		}catch(Exception e){
			log.error("Errore durante l'inserimento MessaggioNonGestito -> Data Registrazione: ["+messaggio.getDataRicezione()+"] IdEgov: ["+messaggio.getIdEgov()
			+"] Mittente: ["+messaggio.getTipoMittente()+messaggio.getMittente()+"] Errore: ["+messaggio.getCodErrore()+"] :"+e.getMessage(),e);

			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long inserisciGestione(MessaggioModel messaggio, GestioneModel gestione,String esecutoreOperazione) throws Exception{

		if (System.getProperty("iris.flag.tracking") == null) {
			log.info("Inserimento Gestione e Messaggio DISABILITATO - system property iris.flag.tracking = null");
			return null;
		}
		
		log.info("Inserimento Gestione e Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"] in corso...");
		Long idGestione = null;
		Long idMessaggio = null;
		Messaggi mVo = null;
		List<Object[]> lst= null;
		try{


			Messaggi messaggiVo = EntityConverter.getMessaggioVo(messaggio);

			// 1. controllo se esiste gia' un messaggio 
			Query query = entityManager.createQuery("select m.id, m.flRichiestaConsegnataAsChar, m.flEsitoConsegnatoAsChar from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.msgId = :msgId");
			query.setParameter("soggetto", messaggiVo.getSoggetto());
			query.setParameter("sil", messaggiVo.getSil());
			query.setParameter("tipoMessaggio", messaggiVo.getTipoMessaggio());
			query.setParameter("msgId", messaggiVo.getMsgId());
			try{ 
				lst = query.getResultList();
				
				if(lst != null && lst.size() == 1){
					Object[] obj = lst.get(0);
					mVo = new Messaggi();
					mVo.setId((Long) obj[0]);
					mVo.setFlRichiestaConsegnataAsChar((String) obj[1]);
					mVo.setFlEsitoConsegnatoAsChar((String) obj[2]);
				}
//				idMessaggio = (Long) query.getSingleResult();
			}catch (NoResultException nre){
//				idMessaggio = -1l;
			}

			// ho trovato il messaggio esco
//			if(idMessaggio > 0){
			if(mVo != null){
				// Messggio esistente. Lo devo aggiornare.
				idMessaggio = mVo.getId();
				
				log.info("Messaggio -> Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["+messaggio.getTipo()+"] msgId: ["+messaggio.getMsgId()+"] gia' presente.");
				
				log.info("Insert Gestione -> Esecutore Operazione: ["+esecutoreOperazione+"] IdEgov: ["+gestione.getIdEgov()
					+"] Id Messaggio: ["+idMessaggio+"] TIpo Gestione: ["+gestione.getTipo().name()+"] in corso...");
				
				// Inserimento gestione
				query = entityManager.createNativeQuery("insert into ssil_gestioni (OP_INSERIMENTO, TS_INSERIMENTO, data_inizio_gestione, id_egov, id_messaggio, tempo_gestione, tipo) "
						+ "values(?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, 0, ?)"	);
				// opInserimento
				query.setParameter(1, esecutoreOperazione);
				query.setParameter(2, gestione.getIdEgov());
				query.setParameter(3, idMessaggio);
				query.setParameter(4, gestione.getTipo().name()); 
				
				int result = query.executeUpdate();
				
				log.debug("Inserita Gestione Numero righe modificate ["+result+"]");
				
				
				query = entityManager.createNativeQuery("select max(g.id) from ssil_gestioni g where g.id_messaggio = ? ");
				query.setParameter(1, idMessaggio);

				try{ 
					Object o = query.getSingleResult();
					if (o instanceof Integer) {
						idGestione = ((Integer) o).longValue();
					} else {
						Object[] ob = (Object []) o;
						idGestione = ((Integer) ob[1]).longValue();
					}
				} catch (NoResultException nre){
					idGestione = -1l;
				}
				
				// aggiorno messaggio
				
				query = entityManager.createQuery("update Messaggi m set m.tsAggiornamento =:tsAggiornamento, m.opAggiornamento =:opAggiornamento where m.id = :id");
				
				query.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
				query.setParameter("opAggiornamento", esecutoreOperazione);
				query.setParameter("id", idMessaggio);
				
				
				result = query.executeUpdate();

				log.debug("Aggiornato Messaggio Numero righe modificate ["+result+"]"); 
				
				messaggio.setId(idMessaggio);
				messaggio.setEsitoConsegnato(mVo.isFlEsitoConsegnato());
				messaggio.setRichiestaConsegnata(mVo.isFlRichiestaConsegnata()); 
				gestione.setId(idGestione);
								
			} else {
				// Messaggio nuovo. Lo devop inserire
				messaggiVo.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				messaggiVo.setOpInserimento(esecutoreOperazione);

				Gestioni gestioneVO = EntityConverter.getGestioneVO(gestione);

				gestioneVO.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				gestioneVO.setOpInserimento(esecutoreOperazione);

				if(messaggiVo.getGestionis() == null)
					messaggiVo.setGestionis(new HashSet<Gestioni>());

				messaggiVo.getGestionis().add(gestioneVO);

				gestioneVO.setMessaggi(messaggiVo); 

				entityManager.persist(messaggiVo);
				entityManager.flush();

				messaggio.setId(messaggiVo.getId());
				gestione.setId(gestioneVO.getId());

				idGestione = gestioneVO.getId();
			}

			log.info("Inserimento Gestione e Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["
					+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"] completato.");
			return idGestione;
		}catch(Exception e){
			log.error("Errore durante l'inserimento Gestione e Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["
					+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"]: "+e.getMessage(),e);
			throw e;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void aggiornaGestione(MessaggioModel messaggio, GestioneModel gestione,TipoGestione tipoGestione, String esecutoreOperazione,boolean sincrono) throws Exception{
		
		if (System.getProperty("iris.flag.tracking") == null) {
			log.info("Update Gestione e Messaggio DISABILITATO - system property iris.flag.tracking = null");
			return;
		}
		
		log.info("Update Gestione e Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["
				+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"] in corso...");
		try{
			Query query = null;

			String msg = null;
			if( (tipoGestione.equals(TipoGestione.INBOUND) && (messaggio.getTipo().equals(TipoMessaggio.ALLINEAMENTO_PENDENZE) || sincrono))
					|| 	(tipoGestione.equals(TipoGestione.OUTBOUND) && messaggio.getTipo().equals(TipoMessaggio.INFORMATIVA_PAGAMENTO) && !sincrono)) {
				
				query = entityManager.createQuery("update Messaggi m set m.dataUltimaConsegnaRichiesta =:dataUltimaConsegnaRichiesta, m.flRichiestaConsegnataAsChar =:flRichiestaConsegnata,"
						+ " m.tsAggiornamento =:tsAggiornamento, m.opAggiornamento =:opAggiornamento where m.id = :id");
				query.setParameter("dataUltimaConsegnaRichiesta", new Timestamp(messaggio.getDataUltimaConsegnaRichiesta().getTime()));
				query.setParameter("flRichiestaConsegnata", messaggio.isRichiestaConsegnata() ? "Y" : "N");
				
				msg = " DataUltimaConsegnaRichiesta: ["+messaggio.getDataUltimaConsegnaRichiesta() +"] RichiestaConsegnata: [" + (messaggio.isRichiestaConsegnata() ? "Y" : "N") +"]";
			} else {
				query = entityManager.createQuery("update Messaggi m set m.dataUltimaConsegnaEsito =:dataUltimaConsegnaEsito, m.flEsitoConsegnatoAsChar =:flEsitoConsegnato,"
						+ " m.tsAggiornamento =:tsAggiornamento, m.opAggiornamento =:opAggiornamento where m.id = :id");
				query.setParameter("dataUltimaConsegnaEsito", new Timestamp(messaggio.getDataUltimaConsegnaEsito().getTime()));
				query.setParameter("flEsitoConsegnato", messaggio.isEsitoConsegnato() ? "Y" : "N");
				
				msg = " DataUltimaConsegnaEsito: ["+messaggio.getDataUltimaConsegnaEsito() +"] EsitoConsegnato: [" + (messaggio.isEsitoConsegnato() ? "Y" : "N") +"]";
			}

			query.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
			query.setParameter("opAggiornamento", esecutoreOperazione);
			query.setParameter("id", messaggio.getId());

			query.executeUpdate();

			log.debug("Aggiornato Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["
				+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"]" + msg); 

			Gestioni gestioneVO = this.entityManager.find(Gestioni.class, gestione.getId());
			
			if(gestione.getErroreComponente() != null){
				gestioneVO.setCodErrore(gestione.getErroreComponente().getCodErrore());
				gestioneVO.setComponenteResponsabile(gestione.getErroreComponente().getCodiceComponente());

				String descrErrore = gestione.getErroreComponente().getDescrErrore();
				if(descrErrore!= null && descrErrore.length() > 254)
					descrErrore = descrErrore.substring(0,254);

				gestioneVO.setDescrErrore(descrErrore);
			}
			if(gestione.getInizioGestione() != null)
				gestioneVO.setDataInizioGestione(new Timestamp(gestione.getInizioGestione().getTime()));   
			gestioneVO.setIdEgov(gestione.getIdEgov());
			gestioneVO.setTempoGestione(gestione.getTempoGestione());
			gestioneVO.setTipoGestione(EntityConverter.getTipoGestioneVO(gestione.getTipo()));
			gestioneVO.setHttpHeaders(gestione.getHttpHeaders());
			gestioneVO.setHttpResponseCode(gestione.getHttpResponseCode()); 

			gestioneVO.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			gestioneVO.setOpAggiornamento(esecutoreOperazione); 
			
			this.entityManager.flush();

			
			log.info("Update Gestione e Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["
					+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"] completato.");
		}catch(Exception e){
			log.error("Errore durante l'aggiornamento Gestione e Messaggio ->  Soggetto: ["+messaggio.getSoggetto()+"] SIL: ["+messaggio.getSil()+"] Tipo: ["
					+messaggio.getTipo()+"] MsgId: ["+messaggio.getMsgId()+"], Tipo Gestione: ["+gestione.getTipo()+"] IdEgov: ["+gestione.getIdEgov()+"]: "+e.getMessage(),e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GestioneModel getUltimaGestione(String soggetto, String sil, String msgId, TipoMessaggio tipoMessaggio,
			TipoGestione tipoGestione) throws Exception {
		log.info("Get Ultima Gestione -> Soggetto: ["+soggetto+"], SIL: ["+sil
				+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"] in corso...");

		GestioneModel gestione = null;
		List<GestioneModel> listaGestione = new ArrayList<GestioneModel>();
		try{

			Query query = entityManager.createQuery("select g from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.msgId = :msgId order by g.dataInizioGestione desc");
			query.setParameter("tipoGestione", EntityConverter.getTipoGestioneVO(tipoGestione));
			query.setParameter("soggetto", soggetto);
			query.setParameter("sil", sil);
			query.setParameter("tipoMessaggio", EntityConverter.getTipoMessaggioVO(tipoMessaggio));
			query.setParameter("msgId", msgId); 
			query.setMaxResults(1);

			List<Gestioni> list = query.getResultList();

			for (Gestioni g : list) {
				listaGestione.add(EntityConverter.getGestioneDTO(g));
			}

			if(listaGestione != null && listaGestione.size() == 1){
				gestione = listaGestione.get(0); 
			}

			log.info("Get Ultima Gestione -> Soggetto: ["+soggetto+"], SIL: ["+sil
					+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"] completata.");
			return gestione;

		}catch(Exception e){
			log.error("Errore durante Get Ultima Gestione -> Soggetto: ["+soggetto+"], SIL: ["+sil
					+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"]:"+e.getMessage(),e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public MessaggioModel getMessaggioUltimaGestione(String soggetto, String sil, String msgId,
			TipoMessaggio tipoMessaggio, TipoGestione tipoGestione) throws Exception {
		log.info("Get Messaggio Ultima Gestione -> Soggetto: ["+soggetto+"], SIL: ["+sil
				+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"] in corso...");

		MessaggioModel messaggio = null;
		try{

			Query query = entityManager.createQuery("select g from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.msgId = :msgId order by g.dataInizioGestione desc");
			query.setParameter("tipoGestione", EntityConverter.getTipoGestioneVO(tipoGestione));
			query.setParameter("soggetto", soggetto);
			query.setParameter("sil", sil);
			query.setParameter("tipoMessaggio", EntityConverter.getTipoMessaggioVO(tipoMessaggio));
			query.setParameter("msgId", msgId); 
			query.setMaxResults(1);

			List<Gestioni> list = query.getResultList();

			if(list != null && list.size() == 1){
				Gestioni g = list.get(0);
				if(g.getMessaggi() != null) 
					messaggio = EntityConverter.getMessaggioDTO(g.getMessaggi());
			}

			log.info("Get Messaggio Ultima Gestione -> Soggetto: ["+soggetto+"], SIL: ["+sil
					+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"] completata.");
			return messaggio;

		}catch(Exception e){
			log.error("Errore  Messaggio durante Get Ultima Gestione -> Soggetto: ["+soggetto+"], SIL: ["+sil
					+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"]:"+e.getMessage(),e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GestioneModel> getGestioni(String soggetto, String sil, String msgId, TipoMessaggio tipoMessaggio,
			TipoGestione tipoGestione) throws Exception {
		log.info("Get Gestioni -> Soggetto: ["+soggetto+"], SIL: ["+sil
				+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"] in corso...");

		List<GestioneModel> listaGestione = new ArrayList<GestioneModel>();
		try{

			Query query = entityManager.createQuery("select g from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.msgId = :msgId order by g.dataInizioGestione desc");
			query.setParameter("tipoGestione", EntityConverter.getTipoGestioneVO(tipoGestione));
			query.setParameter("soggetto", soggetto);
			query.setParameter("sil", sil);
			query.setParameter("tipoMessaggio", EntityConverter.getTipoMessaggioVO(tipoMessaggio));
			query.setParameter("msgId", msgId); 

			List<Gestioni> list = query.getResultList();

			for (Gestioni g : list) {
				listaGestione.add(EntityConverter.getGestioneDTO(g));
			}

			log.info("Get Gestioni -> Soggetto: ["+soggetto+"], SIL: ["+sil
					+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"] completata.");
			return listaGestione;

		}catch(Exception e){
			log.error("Errore durante Get Gestioni -> Soggetto: ["+soggetto+"], SIL: ["+sil
					+"], MsgId: ["+msgId+"], Tipo Gestione: ["+tipoGestione+"], TipoMessaggio: ["+tipoMessaggio+"]:"+e.getMessage(),e);
			throw e;
		}
	}

}
