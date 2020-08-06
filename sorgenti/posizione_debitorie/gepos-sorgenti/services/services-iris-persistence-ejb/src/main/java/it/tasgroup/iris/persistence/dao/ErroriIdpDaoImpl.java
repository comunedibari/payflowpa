package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.nch.idp.backoffice.tavolooperativo.TavoloOperativoConstants;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.iris.persistence.dao.interfaces.ErroriIdpDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

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

@Stateless(name = "ErroriIdpDaoService")
public class ErroriIdpDaoImpl extends DaoImplJpaCmtJta<ErroriIdp> implements ErroriIdpDao {

	private static final Logger LOGGER = LogManager.getLogger(ErroriIdpDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<ErroriIdp> getByFilterParametersPaginated(FilterVO filterVO, String tipoMessaggio, PagingCriteria pagingCriteria, PagingData pagingData) {

		
		String query = "select e.* from ERRORI_IDP e where serviceName=:serviceName ";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("serviceName",tipoMessaggio);
		
		String e2eMsgId = filterVO.getE2emsgid();
		if (StringUtils.isNotEmpty(e2eMsgId)) {
			if (StringUtils.contains(e2eMsgId, "*")) {
				e2eMsgId = e2eMsgId.replace("*", "%");
				
				query+=" AND (upper(e2emsgid) like :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId.toUpperCase());
				 
			} else {
				query+=" AND (e2emsgid = :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId);
			}
		}
		
		String senderId = filterVO.getSenderId();
		if (StringUtils.isNotEmpty(senderId)) {
			if (StringUtils.contains(senderId, "*")) {
				senderId = senderId.replace("*", "%");
				query+=" AND (upper(senderid) like :senderId) ";
				parameters.put("senderId",senderId.toUpperCase());
				
			} else {
				query+=" AND (senderid = :senderId) ";
				parameters.put("senderId",senderId);
			}
		}

		String senderSys = filterVO.getSenderSys();
		if (StringUtils.isNotEmpty(senderSys)) {
			if (StringUtils.contains(senderSys, "*")) {
				senderSys = senderSys.replace("*", "%");
				query+=" AND (upper(sendersys) like :senderSys) ";
				parameters.put("senderSys",senderSys.toUpperCase());
				
			} else {
				query+=" AND (sendersys = :senderSys) ";
				parameters.put("senderSys",senderSys);
			}
		}

		String receiverId = filterVO.getReceiverId();
		if (StringUtils.isNotEmpty(receiverId)) {
			if (StringUtils.contains(receiverId, "*")) {
				receiverId = receiverId.replace("*", "%");
				query+=" AND (upper(receiverId) like :receiverId) ";
				parameters.put("receiverId",receiverId.toUpperCase());
				
			} else {
				query+=" AND (receiverId = :receiverId) ";
				parameters.put("receiverId",receiverId);
			}
		}

		String receiverSys = filterVO.getReceiverSys();
		if (StringUtils.isNotEmpty(receiverSys)) {
			if (StringUtils.contains(receiverSys, "*")) {
				receiverSys = receiverSys.replace("*", "%");
				query+=" AND (upper(receiverSys) like :receiverSys) ";
				parameters.put("receiverSys",receiverSys.toUpperCase());
				
			} else {
				query+=" AND (receiverSys = :receiverSys) ";
				parameters.put("receiverSys",receiverSys);
			}
		}
		
						
		if(filterVO.getDataCaricamentoDa()!=null )
		{
			query+=" AND (ts_inserimento  >= :dataInvioDa) ";
			parameters.put("dataInvioDa",filterVO.getDataCaricamentoDa());		
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
			query+=" AND (ts_inserimento  <= :dataInvioA) ";
			parameters.put("dataInvioA",cal.getTime());		
		}
		
		
		query +=" order by ts_inserimento desc ";

		try {
			if(pagingCriteria==null ) {
				Query q = em.createNativeQuery(query, ErroriIdp.class);
				for (String key: parameters.keySet()) {
					q.setParameter(key, parameters.get(key));
				}
				
				return q.getResultList();
			}
			else {
				return paginateByQuery(ErroriIdp.class, query, pagingCriteria, pagingData, parameters);
			}
			
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
		
	}

	@Override
	public ErroriIdp getNotificaScartataByE2eMsgId(String e2emsgid) {

		try {
			/**
			 * Siamo noi che generiamo E2EMsgId delle notifiche, in modo univoco, quindi nella tabella Errori IDP  
			 * è sufficiente il filtro per questo parametro. (Purché sia una notifica pagamento).
			 */
			Query q = em.createQuery(" from ErroriIdp where e2emsgid=:e2emsgid and serviceName=:serviceName", ErroriIdp.class);
			q.setParameter("e2emsgid", e2emsgid);
			q.setParameter("serviceName", TavoloOperativoConstants.TIPO_ERRORE_IDP_NOTIFICHE);
			
			return (ErroriIdp)q.getSingleResult();
			
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
		
		
	}

	
}