package it.tasgroup.iris.persistence.dao;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.iris.persistence.dao.interfaces.NotifichePagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "NotifichePagamentiDaoService")
public class NotifichePagamentiDaoImpl extends DaoImplJpaCmtJta<NotifichePagamenti> implements NotifichePagamentoDao {

	private static final Logger LOGGER = LogManager.getLogger(NotifichePagamentiDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	

	@Override
	public NotifichePagamenti getNotifichePagamentiByIdPagamentiAndStato(
			Long idPagamento, String statoPagamento) {

		Query query = em.createNamedQuery("getNotifichePagamentiByIdPagamentiAndStato");
		query.setParameter("idPagamento", idPagamento);
		query.setParameter("statoPagamento", statoPagamento);
		
		NotifichePagamenti not =(NotifichePagamenti)query.getSingleResult();
		
		return null;
	}

	
}