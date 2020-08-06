package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.profilo.ClassiAbilitazioni;
import it.nch.is.fo.profilo.ClassiAbilitazioniId;
import it.tasgroup.iris.persistence.dao.interfaces.ClassiAbilitazioniDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "ClassiAbilitazioniDao")
public class ClassiAbilitazioniDaoImpl extends DaoImplJpaCmtJta<ClassiAbilitazioni> implements ClassiAbilitazioniDao {

	private static final Logger LOGGER = LogManager.getLogger(ClassiAbilitazioniDaoImpl.class);
			
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<String> getListaClassiByApplicazione(String applicazione) {
		try {
			TypedQuery<String> queryFunzioni = em.createNamedQuery("getNomiClasseByApplicazione", String.class);
			queryFunzioni.setParameter("applicazione", applicazione);
			
			return queryFunzioni.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("error getListaClassiByApplicazione, param applicazione=" + applicazione, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public void abilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione, String user) {

		ClassiAbilitazioniId id = new ClassiAbilitazioniId(classe, applicazione, funzione);
		try {

			ClassiAbilitazioni classeAbilitazioni = new ClassiAbilitazioni();
			classeAbilitazioni.setId(id);
			classeAbilitazioni.setOpInserimento(user);
			classeAbilitazioni.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			classeAbilitazioni.setPrVersione(1);
			
			this.create(classeAbilitazioni);

		} catch (Exception e) {
			LOGGER.error("error abilitaClasseByApplicazioneFunzione, id: " + id, e);
			throw new DAORuntimeException(e);
		}

	}
	
	@Override
	public void disabilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione) {
		
		ClassiAbilitazioniId id = new ClassiAbilitazioniId(classe, applicazione, funzione);
		try {

			this.deleteByKey(ClassiAbilitazioni.class, id);

		} catch (Exception e) {
			LOGGER.error("error disabilitaClasseByApplicazioneFunzione, id:" + id, e);
			throw new DAORuntimeException(e);
		}

	}

}
