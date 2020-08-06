package it.tasgroup.iris.persistence.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiPendenzaDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "EsitiPendenzaDao")
public class EsitiPendenzaDaoImpl extends DaoImplJpaCmtJta<EsitiPendenza> implements EsitiPendenzaDao {

	private static final Logger LOGGER = LogManager.getLogger(EsitiPendenzaDaoImpl.class);
	
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> groupByDescrizioneEsito(ContainerDTO dtoIn) {
		
		FilterVO filterVO = (FilterVO) dtoIn.getInputDTO();
		
		List<Object[]> resultList = null;
			
		String senderId = filterVO.getSenderId();
		
		String senderSys = filterVO.getSenderSys();
		
		String e2emsgid = filterVO.getE2emsgid();
		
		try {
		
			String querysql = "SELECT DESCRIZIONE_ESITO, COUNT(*) "+  
								"FROM ESITI_PENDENZA " +
								"WHERE 1=1 "+
								"AND E2EMSGID = :e2emsgid "+
								"AND SENDERSYS = :senderSys "+
								"AND SENDERID =  :senderId "+
								"AND ESITO_PENDENZA = :esitoPendenza "+
								"GROUP BY DESCRIZIONE_ESITO ";
			
			Query query= em.createNativeQuery(querysql);
			
			query.setParameter("e2emsgid", e2emsgid);
			query.setParameter("senderSys", senderSys);
			query.setParameter("senderId", senderId);
			query.setParameter("esitoPendenza", "WARN");

			resultList = query.getResultList();	
			
		
		} catch (Exception e) {
			
			LOGGER.error("Error on groupByDescrizioneEsito "+e2emsgid+" "+senderSys+" "+senderId, e);		
			throw new DAORuntimeException(e);
		}

	     return resultList;
	}

	@Override
	public Long countPendenzeCaricate(String e2emsgid, String senderid,
			String sendersys) {
		String countQuery = "select count(*) from ESITI_PENDENZA where e2emsgid=:e2emsgid and senderid=:senderid and sendersys=:sendersys and esito_pendenza='OK' ";
		
		Query q = em.createNativeQuery(countQuery);

		q.setParameter("e2emsgid", e2emsgid);
		q.setParameter("senderid", senderid);
		q.setParameter("sendersys", sendersys);
		
		Number result = (Number)q.getSingleResult();
		Long count = result.longValue();
		
		return count;
	}

	@Override
	public Long countPendenze(String e2emsgid, String senderid,
			String sendersys) {
		String countQuery = "select count(*) from ESITI_PENDENZA where e2emsgid=:e2emsgid and senderid=:senderid and sendersys=:sendersys ";
		
		Query q = em.createNativeQuery(countQuery);

		q.setParameter("e2emsgid", e2emsgid);
		q.setParameter("senderid", senderid);
		q.setParameter("sendersys", sendersys);
		
		Number result = (Number)q.getSingleResult();
		Long count = result.longValue();
		
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EsitiPendenza> listPendenzeErrate(String e2emsgid, String senderid, String sendersys) {
		
		String queryString = "select * from ESITI_PENDENZA where e2emsgid=:e2emsgid and senderid=:senderid and sendersys=:sendersys and esito_pendenza='KO' order by id_pendenza_ente asc";

		Query q = em.createNativeQuery(queryString,EsitiPendenza.class);

		q.setParameter("e2emsgid", e2emsgid);
		q.setParameter("senderid", senderid);
		q.setParameter("sendersys", sendersys);
		
		List<EsitiPendenza> result = (List<EsitiPendenza>)q.getResultList();

		
		return result;
	}

	@Override
	public int updateEsitiPendenzaStato(List<PendenzeCart> pkList,String oldState,String newState) {
		LOGGER.info(this.getClass().getName() + "updateEsitiPendenzaToSend" + "START");
		String query = 
				"UPDATE EsitiPendenza ep " +
			    " SET ep.stato = :stato " +		
			    " WHERE ep.pendenzeCart in :pkList" + 
			    " AND   ep.stato = :oldState";
		
		Query queryUpdatePend = em.createQuery (query);
			queryUpdatePend.setParameter("stato", newState);			
			queryUpdatePend.setParameter("pkList", pkList);
			queryUpdatePend.setParameter("oldState", oldState);
		int esitiPend = queryUpdatePend.executeUpdate();
		LOGGER.info(this.getClass().getName() + "updateEsitiPendenzaToSend" + "END");
		return esitiPend;
	}

	@Override
	public int updateAllEsitiPendenzaStato(FilterVO filter, String newState) {
		LOGGER.info(this.getClass().getName() + " updateAllEsitiPendenzaStato" + " START");
		String query = "UPDATE EsitiPendenza  e " +
							"SET e.stato=:newState " +
								"WHERE EXISTS ( " +
									"SELECT p.pk.e2emsgid " + 
										"FROM PendenzeCart p " + 
											"WHERE p.pk.e2emsgid = e.pendenzeCart.pk.e2emsgid " +
											"AND p.pk.senderid = e.pendenzeCart.pk.senderid " +
											"AND p.pk.sendersys = e.pendenzeCart.pk.sendersys ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("newState", newState);
		query = createQueryParameters(filter, query, parameters);
		query += ")";
		Query queryNotifiche = em.createQuery(query);
		for (String paramName : parameters.keySet()) {
			queryNotifiche.setParameter(paramName, parameters.get(paramName));
		}
		LOGGER.info(this.getClass().getName() + " updateAllEsitiPendenzaStato" + " END");
		return queryNotifiche.executeUpdate();
	}
	
	
	private String createQueryParameters(FilterVO filterVO, String query, Map<String, Object> parameters) {
		String tipoMessaggio = filterVO.getTipoMessaggio();
		if (StringUtils.isNotEmpty(tipoMessaggio)) {			
				query+=" AND (p.tipoMessaggio = :tipoMessaggio) ";
				parameters.put("tipoMessaggio",tipoMessaggio);
		}
		
		String stato = filterVO.getStato();
		if (StringUtils.isNotEmpty(stato)) {			
				query+=" AND (p.stato = :stato) ";
				parameters.put("stato",stato);
		}
		
		String operazione = filterVO.getOperazione();
		if (StringUtils.isNotEmpty(operazione)) {			
				query+=" AND (p.tipoOperazione = :tipoOperazione) ";
				parameters.put("tipoOperazione",operazione);
		}
		
		String e2eMsgId = filterVO.getE2emsgid();
		if (StringUtils.isNotEmpty(e2eMsgId)) {
			if (StringUtils.contains(e2eMsgId, "*")) {
				e2eMsgId = e2eMsgId.replace("*", "%");
				query+=" AND (upper(p.pk.e2emsgid) like :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId.toUpperCase());
				 
			} else {
				query+=" AND (p.pk.e2emsgid = :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId);
			}
		}

		String senderId = filterVO.getSenderId();
		if (StringUtils.isNotEmpty(senderId)) {
			if (StringUtils.contains(senderId, "*")) {
				senderId = senderId.replace("*", "%");
				query+=" AND (upper(p.pk.senderid) like :senderId) ";
				parameters.put("senderId",senderId.toUpperCase());
				
			} else {
//				query+=" AND (p.senderid = :senderId) ";
				query+=" AND (p.pk.senderid = :senderId) ";
				parameters.put("senderId",senderId);
			}
		}

		String senderSys = filterVO.getSenderSys();
		if (StringUtils.isNotEmpty(senderSys)) {
			if (StringUtils.contains(senderSys, "*")) {
				senderSys = senderSys.replace("*", "%");
				query+=" AND (upper(p.pk.sendersys) like :senderSys) ";
				parameters.put("senderSys",senderSys.toUpperCase());
				
			} else {
				query+=" AND (p.pk.sendersys = :senderSys) ";
				parameters.put("senderSys",senderSys);
			}
		}
		
		String tipoTributo = filterVO.getTipoDebito();
		if (StringUtils.isNotEmpty(tipoTributo)) {
			if (StringUtils.contains(tipoTributo, "*")) {
				tipoTributo = tipoTributo.replace("*", "%");
				query+=" AND (upper(p.pk.tipo_tributo) like :tipoTributo) ";
				parameters.put("tipoTributo",tipoTributo.toUpperCase());
				
			} else {
				query+=" AND (p.tipoTributo = :tipoTributo) ";
				parameters.put("tipoTributo",tipoTributo.toUpperCase());
			}
		}

		if(filterVO.getDataCaricamentoDa()!=null )
		{
			query+=" AND (p.timestampRicezione >= :dataCaricamentoDa) ";
			parameters.put("dataCaricamentoDa",filterVO.getDataCaricamentoDa());		
		}

		if(filterVO.getDataCaricamentoA()!=null )
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(filterVO.getDataCaricamentoA());  
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);         
			cal.set(Calendar.MILLISECOND,999);  
			query+=" AND (p.timestampRicezione <= :dataCaricamentoA) ";
			parameters.put("dataCaricamentoA",cal.getTime());		
		}
		return query;
	}

	
}
