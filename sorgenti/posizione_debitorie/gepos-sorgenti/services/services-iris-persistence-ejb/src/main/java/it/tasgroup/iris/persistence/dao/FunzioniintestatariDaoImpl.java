package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.profilo.Funzioniintestatari;
import it.nch.is.fo.profilo.FunzioniintestatariId;
import it.tasgroup.iris.persistence.dao.interfaces.FunzioniintestatariDao;
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

@Stateless(name = "FunzioniintestatariDao")
public class FunzioniintestatariDaoImpl extends DaoImplJpaCmtJta<Funzioniintestatari> implements FunzioniintestatariDao {

	private static final Logger LOGGER = LogManager.getLogger(FunzioniintestatariDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void cleanFunzioniintestatari(String corporate) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("corporate", corporate);
		try {
			@SuppressWarnings("unchecked")
			List<Funzioniintestatari> listaFunIntest = (List<Funzioniintestatari>)this.listByQuery("getFunzioniIntestatari", params);
			
			for (Funzioniintestatari funIntest : listaFunIntest) {
				em.remove(funIntest);
			}
		} catch (Exception e) {
			LOGGER.error("error cleanFunzioniintestatari param: " + params, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public void synchFunzioniintestatari(String corporate, String codApplicazione) {
		try {
			// vedi classe ClassiAbilitazioni
			TypedQuery<String> queryFunzioni = em.createNamedQuery("getFunzioniAbilitateByClasseEApplicazione", String.class);
			queryFunzioni.setParameter("nomeClasse", "Default"); // TODO: default!!!
			queryFunzioni.setParameter("applicazione", codApplicazione);
			
			List<String> funzioniDaAbilitare = queryFunzioni.getResultList();
			
			for (String functionCode : funzioniDaAbilitare) {
				FunzioniintestatariId funIntestId = new FunzioniintestatariId(functionCode, corporate);
				Funzioniintestatari funIntest = new Funzioniintestatari(funIntestId);
				this.create(funIntest);
			}
		
		} catch (Exception e) {
			LOGGER.error("error synchFunzioniintestatari", e);
			throw new DAORuntimeException(e);
		}

	}

}
