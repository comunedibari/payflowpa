package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.persistence.dao.interfaces.AllegatiPendenzaDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "AllegatiPendenzaDaoImpl")
public class AllegatiPendenzaDaoImpl extends DaoImplJpaCmtJta<AllegatiPendenza> implements AllegatiPendenzaDao {

	private static final Logger LOGGER = LogManager.getLogger(AllegatiPendenzaDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public AllegatiPendenza retrieveById(String id) {
		AllegatiPendenza retrieved = null;
		try {
			retrieved = loadById(AllegatiPendenza.class, id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}

	@Override
	public List<AllegatiPendenza> getRicevuteAllegatiCondizione(String idPendenza, String idCondizione) {

		List<AllegatiPendenza> allegati = (List<AllegatiPendenza>) getAllegatiCondizione(idPendenza, idCondizione, EnumTipoAllegato.RICEVUTA);
		
		return allegati;
	}

	@Override
	public AllegatiPendenza getAllegatoCondizione(String idPendenza, String idCondizione, EnumTipoAllegato tipoAllegato,  EnumCodificaAllegato tipoCodifica) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idPendenza", idPendenza);
		parameters.put("idCondizione", idCondizione);
		parameters.put("tiAllegato", tipoAllegato.getDescrizione());
		parameters.put("tiCodificaBody", tipoCodifica.getChiave());
		
		AllegatiPendenza allegato = null;
		
		try {
			
			allegato = (AllegatiPendenza) uniqueResultByQuery("getAllegatoCondizione", parameters);
		
		} catch (Exception e) {
			LOGGER.error("error on  getAllegatoCondizione, idPendenza = " + idPendenza + " , idCondizione = " + idCondizione, e);
			throw new DAORuntimeException(e);
		}
		return allegato;
	}

	@Override
	public List<AllegatiPendenza> getAllegatiPendenza(String idPendenza) {
				
		String queryAllegatiPendenza = " from AllegatiPendenza where idPendenza=:idPendenza and flContesto=:contesto and tiAllegato=:tiAllegato ";
		
		Query q = em.createQuery(queryAllegatiPendenza);
		q.setParameter("idPendenza", idPendenza);
		q.setParameter("contesto", "P");
		q.setParameter("tiAllegato", EnumTipoAllegato.DOCUMENTO.getDescrizione());
	
		return q.getResultList();
	}

	@Override
	public List<AllegatiPendenza> getDocumentiAllegatiCondizione(String idPendenza, String idCondizione) {
		
		List<AllegatiPendenza> allegati = (List<AllegatiPendenza>) getAllegatiCondizione(idPendenza, idCondizione, EnumTipoAllegato.DOCUMENTO);
		
		return allegati;
		
	}
	
	public List<AllegatiPendenza> getAllegatiCondizione(String idPendenza, String idCondizione, EnumTipoAllegato type) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idPendenza", idPendenza);
		parameters.put("idCondizione", idCondizione);
		parameters.put("tiAllegato", type.getDescrizione());
		
		List<AllegatiPendenza> allegati = null;
		
		try{
			
			allegati = (List<AllegatiPendenza>) listByQuery("getAllegatiByType", parameters);
		
		} catch (Exception e) {
			LOGGER.error("error on  getDocumentiAllegatiCondizione, idPendenza = " + idPendenza + " , idCondizione = " + idCondizione, e);
			throw new DAORuntimeException(e);
		}
		
		return allegati;
		
	}
	
	
	public List<AllegatiPendenza> getAllAllegatiCondizione(String idPendenza, String idCondizione) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idPendenza", idPendenza);
		parameters.put("idCondizione", idCondizione);
		List<AllegatiPendenza> allegati = null;
		
		try{
			
			//NB: gli allegati di tipo ricevuta e quietanza sono comunque esclusi
			allegati = (List<AllegatiPendenza>) listByQuery("getAllAllegatiCondizione", parameters);
		
		} catch (Exception e) {
			LOGGER.error("error on  getAllAllegatiCondizione, idPendenza = " + idPendenza + " , idCondizione = " + idCondizione, e);
			throw new DAORuntimeException(e);
		}
		
		return allegati;
		
	}

}
