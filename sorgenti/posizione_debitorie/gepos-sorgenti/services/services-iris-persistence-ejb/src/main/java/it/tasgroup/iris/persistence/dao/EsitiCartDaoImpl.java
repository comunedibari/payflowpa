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

import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiCartDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "EsitiCartDaoService")
public class EsitiCartDaoImpl extends DaoImplJpaCmtJta<EsitiCart> implements EsitiCartDao {

	private static final Logger LOGGER = LogManager.getLogger(EsitiCartDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public int updateEsitiCartStato(List<EsitiCartPK> pkList,String oldState,String newState) {
		LOGGER.info(this.getClass().getName() + "updateEsitiCartToSend" + "START");
		String query = 
				"UPDATE EsitiCart es " +
			    " SET es.stato = :stato ,  " +
				" es.tentativi = 0" + 		
			    " WHERE es.pk in :pkList" + 
			    " AND   es.stato = :oldState";
		
		
		Query queryUpdatePend = em.createQuery (query);
			queryUpdatePend.setParameter("stato", newState);			
			queryUpdatePend.setParameter("pkList", pkList);
			queryUpdatePend.setParameter("oldState", oldState);
		int esitiPend = queryUpdatePend.executeUpdate();
		LOGGER.info(this.getClass().getName() + "updateEsitiCartToSend" + "END");
		return esitiPend;
	}

	@Override
	public int updateAllEsitiCartStato(FilterVO filter, String newState) {
		LOGGER.info(this.getClass().getName() + " updateAllPendenzeCartStato" + " START");
		// NOT NAMED
		String query = "UPDATE EsitiCart e " +
				"SET e.stato=:newState , " +
				" e.tentativi = 0 " +
					"WHERE EXISTS ( " +
						"SELECT p.pk.e2emsgid " + 
							"FROM PendenzeCart p " + 
								"WHERE p.pk.e2emsgid = e.pk.e2emsgid " +
								"AND p.pk.senderid = e.pk.senderid " +
								"AND p.pk.sendersys = e.pk.sendersys ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("newState", newState);
		query = createQueryParameters(filter, query, parameters);
		query += ")";
		Query queryEsitiCart = em.createQuery(query);
		for (String paramName : parameters.keySet()) {
			queryEsitiCart.setParameter(paramName, parameters.get(paramName));
		}
		LOGGER.info(this.getClass().getName() + " updateAllPendenzeCartStato" + " END");
		return queryEsitiCart.executeUpdate();
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
				query+=" AND (upper(p.tipoTributo) like :tipoTributo) ";
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