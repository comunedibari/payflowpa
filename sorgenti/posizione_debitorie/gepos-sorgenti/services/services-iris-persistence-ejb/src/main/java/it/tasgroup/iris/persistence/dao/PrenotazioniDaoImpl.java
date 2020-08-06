package it.tasgroup.iris.persistence.dao;

import it.nch.erbweb.common.PreferenzeVO;
import it.nch.erbweb.orm.Jltpref;
import it.nch.erbweb.profilo.ProfiloInputVO;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.PrenotazioniDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="PrenotazioniDaoService")
public class PrenotazioniDaoImpl extends DaoImplJpaCmtJta<Prenotazioni> implements PrenotazioniDao{

	private static final Logger LOGGER = LogManager.getLogger(PrenotazioniDaoImpl.class);	
	public static final String IDP_SYS = "IDP";
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public Prenotazioni creaPrenotazione(Prenotazioni prenotazione) {
		
		em.persist(prenotazione);
		em.flush();
		return prenotazione;
	}
	
	@Override
	public List<Prenotazioni> listPrenotazioni(ContainerDTO inputDTO) throws Exception {
		ProfiloInputVO voprof = (ProfiloInputVO)((DTO) inputDTO.getInputDTOList().get(0)).getVO();

		String query = "select p.* " +
				"from PRENOTAZIONI p " +
				"where p.intestatario = '"+voprof.getIntestatario()+"' " +
				"and p.tiposervizio = '"+voprof.getTipoServizio()+"'" +
				"and p.op_inserimento IN ('"+voprof.getOperatore()+"', '" + IDP_SYS + "')"; 
		
		String orderClause = " order by p.TS_INSERIMENTO desc ";
		
		Map parameters = new HashMap();

		PagingCriteria pagingCriteria = inputDTO.getPagingCriteria();

		PagingData pagingData = inputDTO.getPagingData();

		List retList = paginateByQuery(Prenotazioni.class, query + orderClause, pagingCriteria, pagingData, parameters);

		return retList;
	}
	
	@Override
	public Prenotazioni updatePrenotazione(Prenotazioni prenotazione) {
		// TODO Auto-generated method stub
		em.merge(prenotazione);
		
		return null;
	}
//	@EJB(name = "GestioneFlussiDaoImpl")
//	GestioneFlussiDao GFAO;
//	
//	@EJB(name = "CondizioniPagamentoDaoImpl")
//	CondizioniPagamentoDao CPFAO;

	@Override
	public void refreshPrenotazione(Prenotazioni prenotazione) {
		em.refresh(prenotazione);
		
	}
	
	@Override
	public void deletePreferenze(String intestatario, String username,
			String tipoServizio,String propertyPrefix) {
		
		String fromWhereClause = getFromWhereClause(intestatario, username, tipoServizio, propertyPrefix);
		
		LOGGER.debug("deletePreferenze start");
		
		String query = "delete " + fromWhereClause;
		
		LOGGER.debug("query: " + query);
		
		Query q = em.createNativeQuery(query, Jltpref.class);
		
		q.executeUpdate();
		
		LOGGER.debug("deletePreferenze - ok");
		
	}

	@Override
	public List<PreferenzeVO> listPreferenze(String intestatrio, String operatore,
			String servizio, String exportTypePrefix) {
		
		String fromWhereClause = getFromWhereClause(intestatrio, operatore, servizio, exportTypePrefix);
		
		
		String query = "select *" +
				fromWhereClause +
				"		order by PROPRIETA, INTESTATARIO DESC, OPERATORE DESC ";
		
		
		LOGGER.debug("query: " + query);
		Query q = em.createNativeQuery(query, Jltpref.class);
		List<Jltpref> l =  q.getResultList();
		List<PreferenzeVO> ret = new ArrayList<PreferenzeVO>();
		
		for (Jltpref jltpref : l) {
			
			PreferenzeVO vo = new PreferenzeVO();
			vo.setIntestatario(jltpref.getJltprefId().getIntestatario());
			vo.setOperatore(jltpref.getJltprefId().getOperatore());
			vo.setProprieta(jltpref.getJltprefId().getProprieta());
			vo.setValore(jltpref.getValore());
			vo.setTiposervizio(jltpref.getJltprefId().getTiposervizio());
			
			ret.add(vo);
			
		}
		
		return ret;
	}

	private String getFromWhereClause(String intestatario, String operatore,
			String servizio, String exportType) {
		String fromWhereClause = "		from JLTPREF" +
				"		where ((INTESTATARIO = '' and OPERATORE = '')" +
				" 		or (INTESTATARIO = '" + intestatario + "' and OPERATORE = '')" +
				"		or (INTESTATARIO = '" + intestatario + "' and OPERATORE = '" + operatore + "'))" +
				"		and TIPOSERVIZIO = '" + servizio + "'" +
				"       and PROPRIETA like '" + exportType + "export%'";
		return fromWhereClause;
	}
	
	@Override
	public void savePreferenza(Jltpref jltpref) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Jltpref> criteria = builder.createQuery( Jltpref.class );
		Root<Jltpref> personRoot = criteria.from( Jltpref.class );
		criteria.select(personRoot);
		
		criteria.where(
				builder.and( 
						builder.equal( personRoot.get( "jltprefId" ).get("intestatario"), jltpref.getJltprefId().getIntestatario()),
						builder.equal( personRoot.get( "jltprefId" ).get("operatore"), jltpref.getJltprefId().getOperatore()),
						builder.equal( personRoot.get( "jltprefId" ).get("tiposervizio"), jltpref.getJltprefId().getTiposervizio()),
						builder.equal( personRoot.get( "jltprefId" ).get("proprieta"), jltpref.getJltprefId().getProprieta())));
//						builder.equal( personRoot.get( "idFiscaleSottoscrittore" ), sottoscrittore ),
//						builder.or( 
//								builder.equal(personRoot.get("stato"), EnumStatoAEA.ACCETTATA.getChiave()),
//								builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO.getChiave()),
//								builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave()),
//								builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO_REVOCA.getChiave()),
//								builder.equal(personRoot.get("stato"), EnumStatoAEA.VARIATA.getChiave()))));
		
		List<Jltpref> l = em.createQuery( criteria ).getResultList();
				
	    if (l == null || l.size() == 0)
	    	em.persist(jltpref);
	    else
	    	em.merge(jltpref);
	}

    @Override
    public boolean deletePrenotazione(Long idPrenotazione) {
        try {            
            Prenotazioni p = em.find(Prenotazioni.class, idPrenotazione);
            em.remove(p);
        } catch(Exception e){
            LOGGER.error("Errore durante la cancellazione della prenotazione: ", e);
            return false;
        }
        
        return true;
    }

	
//	@Override
//	public Pagamenti salvaPagamento(String op,String idPendenzaEnte,PagamentoPosizioneDebitoriaVO pvo,Long idDistintaPagamento){
//		Pagamenti pagam = new Pagamenti();
//		try{
//			
//			pagam.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
//			pagam.setDtScadenza(new Date(System.currentTimeMillis()));
//			pagam.setTsOrdine(new Timestamp(System.currentTimeMillis()));
//	
//			pagam.setCondPagamento(CPFAO.getById(CondizionePagamento.class, pvo.getIdCondizione()));
//			pagam.setIdEnte(pvo.getIdEnte());
//			pagam.setIdTributo(pvo.getIdTributo());
//			pagam.setCdTrbEnte(pvo.getIdTributoEnte());
//			pagam.setFlussoDistinta(GFAO.getById(GestioneFlussi.class, idDistintaPagamento));
//			pagam.setImPagato(pvo.getImporto());
//			pagam.setDistinta("distinta");
//	
//			pagam.setCoPagante(op);//cf_utente in sessione
//			pagam.setTiPagamento(pvo.getTipoPagamento());//tipoPagamento
//			pagam.setTiDebito(pvo.getTiDebito());//tipodebito
//			pagam.setStPagamento(StatiPagamentiIris.IN_CORSO.getPagaMapping());//IC
//			pagam.setStRiga("V");//V
//			
//			pagam.setOpInserimento(op);
//			pagam.setTsInserimento(new Timestamp(System.currentTimeMillis()));
//			
//			pagam.setIdPendenza(pvo.getIdPendenza());
//			pagam.setIdPendenzaente(idPendenzaEnte);
//			pagam.setFlagIncasso("0");
//			
//	//		em.persist(distPag);
//			em.persist(pagam);
//			return pagam;
//		}catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("salvaPagamento - eccezione", e);
//		}
//		
//		return null;
//	}
	
	

}