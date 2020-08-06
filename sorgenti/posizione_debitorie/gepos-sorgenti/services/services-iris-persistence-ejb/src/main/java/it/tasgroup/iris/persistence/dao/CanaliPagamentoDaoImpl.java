package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CanaliPagamento;
import it.tasgroup.iris.persistence.dao.interfaces.CanaliPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name="CanaliPagamentoDaoService")
public class CanaliPagamentoDaoImpl extends DaoImplJpaCmtJta<CanaliPagamento> implements CanaliPagamentoDao {
    
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	

	@Override
	public CanaliPagamento getByTipoCanale(String tipoCanale) {

		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("tipoCanale", tipoCanale);
		CanaliPagamento cp=null;
		try {
			cp = (CanaliPagamento) em.createNamedQuery("getByTipoCanale").setParameter("tipoCanale", tipoCanale).getSingleResult();
	
		} catch (Exception e) {
			throw new DAORuntimeException();
		}
		return cp;
		
		
	}

}
