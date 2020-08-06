package it.tasgroup.iris.persistence.dao;

import java.util.Calendar;
import java.util.Date;
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
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.iris.persistence.dao.interfaces.NotificheCartDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "NotificheCartDaoService")
public class NotificheCartDaoImpl extends DaoImplJpaCmtJta<NotificheCart> implements NotificheCartDao {

	private static final Logger LOGGER = LogManager.getLogger(NotificheCartDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<NotificheCart> getByFilterParametersPaginated(FilterVO filterVO, PagingCriteria pagingCriteria, PagingData pagingData) {
		
		String query = "select n.* from NOTIFICHE_CART n ";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		query = createQueryWithParameters(filterVO, query, parameters);
		
		
		query +=" order by n.ts_inserimento desc ";


		try {
			return paginateByQuery(NotificheCart.class, query, pagingCriteria, pagingData, parameters);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
		
	}

	private String createQueryWithParameters(FilterVO filterVO, String query, Map<String, Object> parameters) {
		String idPagamento = filterVO.getIdPagamento();
		String idDebito = filterVO.getIdDebito();
		if (StringUtils.isNotEmpty(idPagamento) || StringUtils.isNotEmpty(idDebito)) {
			query += "join NOTIFICHE_PAGAMENTI npag on n.E2EMSGID = npag.E2EMSGID and n.RECEIVERID = npag.CD_ENTE and n.RECEIVERSYS = npag.ID_SYSTEM";
		}
		String tipoFormatoNotifiche = filterVO.getTipoFormatoNotifiche();
		if (StringUtils.isNotEmpty(tipoFormatoNotifiche)) {
			query += "join CFG_NOTIFICA_PAGAMENTO np on n.TIPO_NOTIFICA = np.TIPO_NOTIFICA and n.ID_ENTE = np.ID_ENTE and n.CD_TRB_ENTE = np.CD_TRB_ENTE ";
		} 
		query += " where 1 = 1 ";
		if (StringUtils.isNotEmpty(idPagamento)) {
			query += " and npag.ID_PAGAMENTO = :idPagamento";
			parameters.put("idPagamento", idPagamento);
		}
		if (StringUtils.isNotEmpty(idDebito)) {
			if (StringUtils.contains(idDebito, "*")) {
				idDebito = idDebito.replace("*", "%");
				query += " and npag.ID_PENDENZAENTE like :idDebito ";
			} else {
				query += " and npag.ID_PENDENZAENTE = :idDebito";
				
			}
			parameters.put("idDebito", idDebito);
		}
		if (StringUtils.isNotEmpty(tipoFormatoNotifiche)) {
			query += " and np.FORMATO_NOTIFICA = :formatoNotifica ";
			parameters.put("formatoNotifica", tipoFormatoNotifiche);
		}
		
		String e2eMsgId = filterVO.getE2emsgid();
		if (StringUtils.isNotEmpty(e2eMsgId)) {
			if (StringUtils.contains(e2eMsgId, "*")) {
				e2eMsgId = e2eMsgId.replace("*", "%");
				
				query+=" AND (upper(n.e2emsgid) like :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId.toUpperCase());
				 
			} else {
				query+=" AND (n.e2emsgid = :e2emsgid) ";
				parameters.put("e2emsgid",e2eMsgId);
			}
		}
		
		String receiverId = filterVO.getReceiverId();
		if (StringUtils.isNotEmpty(receiverId)) {
			if (StringUtils.contains(receiverId, "*")) {
				receiverId = receiverId.replace("*", "%");
				query+=" AND (upper(n.receiverId) like :receiverId) ";
				parameters.put("receiverId",receiverId.toUpperCase());
				
			} else {
				query+=" AND (n.receiverId = :receiverId) ";
				parameters.put("receiverId",receiverId);
			}
		}

		String receiverSys = filterVO.getReceiverSys();
		if (StringUtils.isNotEmpty(receiverSys)) {
			if (StringUtils.contains(receiverSys, "*")) {
				receiverSys = receiverSys.replace("*", "%");
				query+=" AND (upper(n.receiverSys) like :receiverSys) ";
				parameters.put("receiverSys",receiverSys.toUpperCase());
				
			} else {
				query+=" AND (n.receiverSys = :receiverSys) ";
				parameters.put("receiverSys",receiverSys);
			}
		}
		
						
		if(filterVO.getDataCaricamentoDa()!=null )
		{
			query+=" AND (n.ts_inserimento  >= :dataInvioDa) ";
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
			query+=" AND (n.ts_inserimento  <= :dataInvioA) ";
			parameters.put("dataInvioA",cal.getTime());		
		}
		
		if(filterVO.getStato() != null && !filterVO.getStato().equals("")) {
			if (filterVO.getStato().equalsIgnoreCase("reinviato") && filterVO.getDataReinvioDa() != null && filterVO.getDataReinvioA() != null) {
				Calendar calTo = Calendar.getInstance();
				calTo.setTime(filterVO.getDataReinvioA() != null ? filterVO.getDataReinvioA() : new Date());
				calTo.set(Calendar.HOUR_OF_DAY, 23);
				calTo.set(Calendar.MINUTE, 59);
				calTo.set(Calendar.SECOND, 59);         
				calTo.set(Calendar.MILLISECOND,999);
				if (filterVO.getDataReinvioA() != null) {
					Calendar calFrom = Calendar.getInstance();
					calFrom.setTime(filterVO.getDataReinvioDa());  
					calFrom.set(Calendar.HOUR_OF_DAY, 24);
					calFrom.set(Calendar.MINUTE, 00);
					calFrom.set(Calendar.SECOND, 00);         
					calFrom.set(Calendar.MILLISECOND,001);
					query += " AND (upper(n.stato) = :stato AND n.ts_aggiornamento between :dataReInvioFrom and :dataReInvioTo)";
					parameters.put("stato", filterVO.getStato().toUpperCase());
					parameters.put("dataReInvioFrom", calFrom.getTime());
					parameters.put("dataReInvioTo",calTo.getTime());					
				} else {
					query += " AND (upper(n.stato) = :stato AND n.ts_aggiornamento <= :dataReInvioTo)";
					parameters.put("stato", filterVO.getStato().toUpperCase());
					parameters.put("dataReInvioTo",calTo.getTime());
				}
			} else {
				query += " AND (upper(n.stato) = :stato)";
				parameters.put("stato", filterVO.getStato().toUpperCase());
			}
		}
		
		if (filterVO.getTipoNotifica() != null && !filterVO.getTipoNotifica().isEmpty()) {
			query+=" AND n.TIPO_NOTIFICA = :tipo_notifica";
			parameters.put("tipo_notifica", filterVO.getTipoNotifica().toUpperCase());
		}
		if (filterVO.getTipoDebito() != null && !filterVO.getTipoDebito().isEmpty()) {
			query+=" AND n.cd_trb_ente = :cd_trb_ente";
			parameters.put("cd_trb_ente", filterVO.getTipoDebito().toUpperCase());
		} else {
			if(filterVO.getTipiDebitoAmmessi() != null && !filterVO.getTipiDebitoAmmessi().isEmpty()) {
				query+=" AND n.cd_trb_ente IN (:tipiDebitoAmmessi)";
				parameters.put("tipiDebitoAmmessi", filterVO.getTipiDebitoAmmessi());
			}
		}
		
		
		
		return query;
	}

	@Override
	public int updateNotificheCartStato(List<NotificheCartPK> listNotifichePK, String oldState, String newState) {
		LOGGER.info(this.getClass().getName() + "updateNotificheCartStato" + "START");
		String query = 
				"UPDATE NotificheCart N " +
			    " SET n.stato = :stato ,  " +
				" n.tentativi = 0" + 		
			    " WHERE n.pk in :pkList" + 
			    " AND   n.stato = :oldState";
		Query queryUpdateNotifiche = em.createQuery (query);
		queryUpdateNotifiche.setParameter("stato", newState);			
		queryUpdateNotifiche.setParameter("pkList", listNotifichePK);
		queryUpdateNotifiche.setParameter("oldState", oldState);
		int esitiPend = queryUpdateNotifiche.executeUpdate();
		LOGGER.info(this.getClass().getName() + "updateNotificheCartStato" + "END");
		return esitiPend;
		
	}
	
	
	@Override
	public int updateMoreNotificheStato(List<NotificheCartPK> listNotifichePK, List<String> oldStateList, String newState) {
		LOGGER.info(this.getClass().getName() + "updateMoreNotificheStato" + "START");
		String query = 
				"UPDATE NotificheCart N " +
			    " SET n.stato = :stato ,  " +
				" n.tentativi = 0" + 		
			    " WHERE n.id in :pkList" + 
			    " AND   n.stato in :oldStateList";
		Query queryUpdateNotifiche = em.createQuery (query);
		queryUpdateNotifiche.setParameter("stato", newState);			
		queryUpdateNotifiche.setParameter("pkList", listNotifichePK);
		queryUpdateNotifiche.setParameter("oldStateList", oldStateList);
		int esitiPend = queryUpdateNotifiche.executeUpdate();
		LOGGER.info(this.getClass().getName() + "updateMoreNotificheStato" + "END");
		return esitiPend;
		
	}

	@Override
	public int updateAllNotificheStato(FilterVO filter,String newState) {
		LOGGER.info(this.getClass().getName() + " updateMoreNotificheStato" + " START");
		try {
			String query = "UPDATE NOTIFICHE_CART n SET n.stato = :newState , n.tentativi = 0 where 1=1 ";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("newState", newState);
			query = createQueryWithParameters(filter, query, parameters);
			Query queryNotifiche = em.createNativeQuery(query, NotificheCart.class);
			for (String paramName : parameters.keySet()) {
				queryNotifiche.setParameter(paramName, parameters.get(paramName));
			}
			LOGGER.info(this.getClass().getName() + " updateMoreNotificheStato" + " END");
			return queryNotifiche.executeUpdate();
		}catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
			
	}

	
}