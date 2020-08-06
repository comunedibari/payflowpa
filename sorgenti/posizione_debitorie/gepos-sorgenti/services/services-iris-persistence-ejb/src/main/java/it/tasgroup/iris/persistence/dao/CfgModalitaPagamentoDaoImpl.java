package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.persistence.dao.interfaces.CfgModalitaPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoConfigurazionePagamento;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CfgModalitaPagamentoDaoService")
public class CfgModalitaPagamentoDaoImpl extends DaoImplJpaCmtJta<CfgModalitaPagamento> implements CfgModalitaPagamentoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CfgModalitaPagamentoDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<CfgModalitaPagamento> getListaModalitaPagamentoAttive() {
		
		List<CfgModalitaPagamento> listaOut = new ArrayList<CfgModalitaPagamento>();
		
		Map<String, Object> params = new HashMap<String, Object>();

		Timestamp now = new Timestamp(new Date().getTime()); 
		
		params.put("stato", EnumStatoConfigurazionePagamento.ATTIVO.getChiave());		
		params.put("dtInizioValidita", now);
		params.put("dtFineValidita",now);


		try {
			listaOut = (List<CfgModalitaPagamento>) listByQuery("listAllActiveCfgModalita",params);
		} catch (Exception e) {
			LOGGER.error("error on getListaModalitaPagamentoAttive "+params, e);		
			throw new DAORuntimeException(e);
		}
		return listaOut;
	}
	
	@Override
	public List<CfgModalitaPagamento> getListaAllModalitaPagamento() {
		
		List<CfgModalitaPagamento> listaOut = new ArrayList<CfgModalitaPagamento>();
		
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			listaOut = (List<CfgModalitaPagamento>) listByQuery("listAllCfgModalita",params);
		} catch (Exception e) {
			LOGGER.error("error on getListaAllModalitaPagamento "+params, e);		
			throw new DAORuntimeException(e);
		}
		return listaOut;
	}
	
	@Override
	public List<CfgModalitaPagamento> getListaCfgModalitaInActiveCfgFornitore(String circuito) {
		List<CfgModalitaPagamento> listaOut = new ArrayList<CfgModalitaPagamento>();
		
		
		try {
			String query = "select new CfgModalitaPagamento(cmp.id, cmp.descrizione)  "
					+ "from CfgModalitaPagamento cmp  where " +
				" cmp.id in " +
				"(select distinct cgp.cfgModalitaPagamento from cmp.cfgGatewayPagamenti cgp " +
				"where cgp.stRiga = 'V'" +
				((circuito != null && !circuito.trim().equals("")) ? "and cgp.cfgFornitoreGateway = " + circuito + "" : "") + 
				") order by cmp.descrizione asc";
			Query selectQuery = em.createQuery(query);
			listaOut = (List<CfgModalitaPagamento>)selectQuery.getResultList();
		} catch (Exception e) {
			LOGGER.error("error on getListaCfgModalitaInActiveCfgFornitore ", e);		
			throw new DAORuntimeException(e);
		}
		return listaOut;
	}


}
