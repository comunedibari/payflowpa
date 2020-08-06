package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.iris.persistence.dao.interfaces.CfgNotificaPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CfgNotificaPagamentoDao")
public  class CfgNotificaPagamentoDaoImpl extends DaoImplJpaCmtJta<CfgNotificaPagamento> implements CfgNotificaPagamentoDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgNotificaPagamentoDaoImpl.class);	

	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	public void setEntityMangerFactory(EntityManagerFactory emf){
		this.em = emf.createEntityManager();
	}

	@Override
	public CfgNotificaPagamento updateCfgNotificaPagamento(CfgNotificaPagamento cfg) {
		
		try {
			
			cfg = this.update(cfg);
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateCfgNotificaPagamento ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return cfg;
	}
	
	@Override
	public CfgNotificaPagamento createCfgNotificaPagamento(CfgNotificaPagamento cfg) {
		
		try {

			//CfgNotificaPagamento cfg = new CfgNotificaPagamento();

			CfgNotificaPagamento created = create(cfg);

			return created;

		} catch (Exception e) {
			
			LOGGER.error("error on  createCfgNotificaPagamento, " + cfg, e);
			
			throw new DAORuntimeException(e);
		}

	}
	
}
