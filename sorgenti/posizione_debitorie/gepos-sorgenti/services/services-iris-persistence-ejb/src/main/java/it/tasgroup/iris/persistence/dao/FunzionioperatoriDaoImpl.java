package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.profilo.Funzionioperatori;
import it.nch.is.fo.profilo.FunzionioperatoriId;
import it.tasgroup.iris.persistence.dao.interfaces.FunzionioperatoriDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "FunzionioperatoriDao")
public class FunzionioperatoriDaoImpl extends DaoImplJpaCmtJta<Funzionioperatori> implements FunzionioperatoriDao {

	private static final Logger LOGGER = LogManager.getLogger(FunzionioperatoriDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<String> listEnabledFunctions(String corporate, String operatore) {
		try {
			String qlString = "select FOP.funOpId.functionCode from Funzionioperatori FOP where FOP.funOpId.corporate = :corporate and FOP.funOpId.operatore = :operatore";
			
			TypedQuery<String> query = em.createQuery(qlString, String.class);
			query.setParameter("corporate", corporate);
			query.setParameter("operatore", operatore);

			List<String> functionCodeList = query.getResultList();
			return functionCodeList;
			
		} catch (Exception e) {
			LOGGER.error("error listEnabledFunctions", e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public void cleanFunzionioperatori(String corporate, String operatore) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("corporate", corporate);
		params.put("operatore", operatore);
		try {
			
			List<Funzionioperatori> listaFop = (List<Funzionioperatori>)this.listByQuery("getFunzioniOperatori", params);
			
			for (Funzionioperatori funzionioperatori : listaFop) {
				em.remove(funzionioperatori);
//				this.delete(funzionioperatori);
			}
			
		} catch (Exception e) {
			LOGGER.error("error cleanFunzionioperatori", e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public void synchFunzionioperatori(String corporate, String operatore, String codApplicazione) {
		try {
			// vedi classe ClassiAbilitazioni
			TypedQuery<String> queryFunzioni = em.createNamedQuery("getFunzioniAbilitateByClasseEApplicazione", String.class);
			queryFunzioni.setParameter("nomeClasse", "Default"); // TODO: default!!!
			queryFunzioni.setParameter("applicazione", codApplicazione);
			
			List<String> funzioniDaAbilitare = queryFunzioni.getResultList();
			
			for (String functionCode : funzioniDaAbilitare) {
				FunzionioperatoriId fopId = new FunzionioperatoriId(functionCode, corporate, operatore);
				Funzionioperatori fop = new Funzionioperatori(fopId);
				this.create(fop);
			}
		
		} catch (Exception e) {
			LOGGER.error("error synchFunzionioperatori", e);
			throw new DAORuntimeException(e);
		}

	}

}
