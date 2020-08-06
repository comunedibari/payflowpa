package it.tasgroup.iris.persistence.dao;

import java.util.Arrays;
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
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzeCartDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "PendenzeCartDaoService")
public class PendenzeCartDaoImpl extends DaoImplJpaCmtJta<PendenzeCart> implements PendenzeCartDao {

	private static final Logger LOGGER = LogManager.getLogger(PendenzeCartDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<PendenzeCart> getByFilterParametersPaginated(FilterVO filterVO, PagingCriteria pagingCriteria, PagingData pagingData) {

//		String query = "select new PendenzeCartVO( "+
//			"p.pk.e2emsgid," +
//			"p.pk.senderId," +
//			"p.pk.senderSys,"+ 
//			"p.stato," +
//			"p.timestampRicezione," +
//			"p.numPendenze," +
//		    "p.tipoMessaggio," +
//		    "p.numPendDeleted," +
//			"p.tipoTributo," +
//			"p.tipoOperazione" +
//			") from PendenzeCart p where 1=1 ";

		
//		String query = "select "+
//				"e2emsgid," +
//				"senderId," +
//				"senderSys,"+ 
//				"stato," +
//				"timestamp_ricezione," +
//				"num_Pendenze," +
//			    "tipo_Messaggio," +
//			    "num_Pend_Deleted," +
//				"tipo_Tributo," +
//				"tipo_operazione" +
//				" from Pendenze_Cart where 1=1 ";
		

		String query = "select p.* from PENDENZE_CART p ";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		query = createQueryParameters(filterVO, query, parameters);		
		
		query +=" order by p.timestamp_Ricezione desc ";

//		SELECT 
//		   E2EMSGID, senderId, senderSys, timestamp_ricezione, stato, num_pendenze, tipo_operazione,
//		   CASE WHEN MESSAGGIO_XML IS NULL THEN 0 ELSE 1  END AS presenza_xml, tipo_messaggio , num_pend_deleted , tipo_tributo
//		FROM 
//		   ${databaseSchema}.PENDENZE_CART  P
//		WHERE 
//		   1 = 1
//		   [if E2EMSGID : AND UPPER(P.E2EMSGID) = :E2EMSGID ]
//		   [if E2EMSGID_LIKE : AND UPPER(P.E2EMSGID) LIKE :E2EMSGID_LIKE ]
//		   [if senderId : AND UPPER(P.senderId) = :senderId ]
//		   [if senderId_LIKE : AND UPPER(P.senderId) LIKE :senderId_LIKE ]
//		   [if senderSys : AND UPPER(P.senderSys) = :senderSys ]
//		   [if senderSys_LIKE : AND UPPER(P.senderSys) LIKE :senderSys_LIKE ]
//	       [if dataCaricamentoDa : AND P.TIMESTAMP_RICEZIONE >= :dataCaricamentoDa]
//	       [if dataCaricamentoA : AND P.TIMESTAMP_RICEZIONE &lt;= :dataCaricamentoA]

		try {
			return paginateByQuery(PendenzeCart.class, query, pagingCriteria, pagingData, parameters);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
		
	}

	private String createQueryParameters(FilterVO filterVO, String query, Map<String, Object> parameters) {
		String idDebito = filterVO.getIdDebito();
		if (StringUtils.isNotEmpty(idDebito)) {
			query += " LEFT OUTER JOIN esiti_pendenza EP ON (P.E2EMSGID = EP.E2EMSGID AND P.SENDERID = EP.SENDERID AND P.SENDERSYS = EP.SENDERSYS)" +
						" WHERE EP.ID_PENDENZA_ENTE = :idDebito";
			parameters.put("idDebito", idDebito);
		} else {
			query += " WHERE 1=1 ";
		}
		String tipoMessaggio = filterVO.getTipoMessaggio();
		if (StringUtils.isNotEmpty(tipoMessaggio)) {
			query += " AND p.tipo_messaggio IN (:tipo_messaggio)";
			parameters.put("tipo_messaggio", Arrays.asList(tipoMessaggio.split("\\s*,\\s*")));
		}
		
		String stato = filterVO.getStato();
		if (StringUtils.isNotEmpty(stato)) {			
				query+=" AND (p.stato = :stato) ";
				parameters.put("stato",stato);
		}
		
		String operazione = filterVO.getOperazione();
		if (StringUtils.isNotEmpty(operazione)) {			
				query+=" AND (p.tipo_operazione = :tipoOperazione) ";
				parameters.put("tipoOperazione",operazione);
		}
		
		String e2eMsgId = filterVO.getE2emsgid();
		if (StringUtils.isNotEmpty(e2eMsgId)) {
			if (StringUtils.contains(e2eMsgId, "*")) {
				e2eMsgId = e2eMsgId.replace("*", "%");
				
				query+=" AND (upper(p.e2emsgid) like :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId.toUpperCase());
				 
			} else {
				query+=" AND (p.e2emsgid = :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId);
			}
		}

		String senderId = filterVO.getSenderId();
		if (StringUtils.isNotEmpty(senderId)) {
			if (StringUtils.contains(senderId, "*")) {
				senderId = senderId.replace("*", "%");
				query+=" AND (upper(p.senderid) like :senderId) ";
				parameters.put("senderId",senderId.toUpperCase());
				
			} else {
				query+=" AND (p.senderid = :senderId) ";
				parameters.put("senderId",senderId);
			}
		}

		String senderSys = filterVO.getSenderSys();
		if (StringUtils.isNotEmpty(senderSys)) {
			if (StringUtils.contains(senderSys, "*")) {
				senderSys = senderSys.replace("*", "%");
				query+=" AND (upper(p.sendersys) like :senderSys) ";
				parameters.put("senderSys",senderSys.toUpperCase());
				
			} else {
				query+=" AND (p.sendersys = :senderSys) ";
				parameters.put("senderSys",senderSys);
			}
		}
		
		String tipoTributo = filterVO.getTipoDebito();
		if (StringUtils.isNotEmpty(tipoTributo)) {
			if (StringUtils.contains(tipoTributo, "*")) {
				tipoTributo = tipoTributo.replace("*", "%");
				query+=" AND (upper(p.tipo_tributo) like :tipoTributo) ";
				parameters.put("tipoTributo",tipoTributo.toUpperCase());
				
			} else {
				query+=" AND (p.tipo_tributo = :tipoTributo) ";
				parameters.put("tipoTributo",tipoTributo.toUpperCase());
			}
		} else {
			if(filterVO.getTipiDebitoAmmessi() != null && !filterVO.getTipiDebitoAmmessi().isEmpty()) {
				query+=" and p.tipo_tributo IN (:tipiDebitoAmmessi)";
				parameters.put("tipiDebitoAmmessi", filterVO.getTipiDebitoAmmessi());
			}
		}

		if(filterVO.getDataCaricamentoDa()!=null )
		{
			query+=" AND (p.timestamp_Ricezione >= :dataCaricamentoDa) ";
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
			//int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
			query+=" AND (p.timestamp_Ricezione <= :dataCaricamentoA) ";
			parameters.put("dataCaricamentoA",cal.getTime());		
		}
		return query;
	}

	@Override
	public int updatePendenzeCartStato(List<PendenzeCartPK> pkList,String oldState,String newState) {
		LOGGER.info(this.getClass().getName() + "updatePendenzeCartToSend" + "START");
		String query = 
				"UPDATE PendenzeCart pend " +
			    " SET pend.stato = :stato  " +
			    " WHERE pend.pk in :pkList" + 
			    " AND   pend.stato = :oldState";
		
		Query queryUpdatePend = em.createQuery (query);
			queryUpdatePend.setParameter("stato", newState);			
			queryUpdatePend.setParameter("pkList", pkList);
			queryUpdatePend.setParameter("oldState", oldState);
		int esitiPend = queryUpdatePend.executeUpdate();
		LOGGER.info(this.getClass().getName() + "updatePendenzeCartToSend" + "END");
		return esitiPend;
	}
	
	
	@Override
	public int updateAllPendenzeCartStato(FilterVO filter,String newState) {
		LOGGER.info(this.getClass().getName() + " updateAllPendenzeCartStato" + " START");
		String query = "UPDATE PENDENZE_CART p SET p.stato = :newState where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("newState", newState);
		query = createQueryParameters(filter, query, parameters);
		Query queryNotifiche = em.createNativeQuery(query, PendenzeCart.class);
		for (String paramName : parameters.keySet()) {
			queryNotifiche.setParameter(paramName, parameters.get(paramName));
		}
		LOGGER.info(this.getClass().getName() + " updateAllPendenzeCartStato" + " END");
		return queryNotifiche.executeUpdate();
			
	}

	
}