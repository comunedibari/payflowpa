package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.enti.TipologiaEnti;
import it.tasgroup.iris.persistence.dao.interfaces.TipologiaEntiDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "TipologiaEntiDaoService")
public class TipologiaEntiDaoImpl extends DaoImplJpaCmtJta<TipologiaEnti> implements TipologiaEntiDao {

	private static final Logger LOGGER = LogManager.getLogger(TipologiaEntiDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<TipologiaEnti> getListaTipologiaEnti() {
		try {
			TypedQuery<TipologiaEnti> query = em.createQuery("from TipologiaEnti", TipologiaEnti.class);
			return query.getResultList();
		} catch (Exception e) {
			LOGGER.error("error on listEntiAbleToAnonimousPayment ", e);
			throw new DAORuntimeException(e);
		}

	}

}
