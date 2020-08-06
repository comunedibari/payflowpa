package it.tasgroup.iris.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.tasgroup.iris.domain.PrenotazioneAvvisiDigitali;
import it.tasgroup.iris.persistence.dao.interfaces.PrenotazioneAvvisiDigitaliDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoAvviso;

@Stateless(name="PrenotazioneAvvisiDigitaliDao")
public class PrenotazioneAvvisiDigitaliDaoImpl  extends DaoImplJpaCmtJta<PrenotazioneAvvisiDigitali> implements PrenotazioneAvvisiDigitaliDao {

    private static final Logger LOGGER = LogManager.getLogger(PrenotazioniDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<PrenotazioneAvvisiDigitali> getPrenotazioneAvvisiDigitaliByCondizione(String idCondizione) {
		Query q = em.createNamedQuery("PrenotazAvvisiByIdCondizione");
		q.setParameter("idCondizione", idCondizione);
		return (List<PrenotazioneAvvisiDigitali>)q.getResultList();
	}

	@Override
	public void resettaPrenotaAvvisiDigitali(Long id, String tipo) {
		if ("NDP".equals(tipo)) {
			PrenotazioneAvvisiDigitali p = em.find(PrenotazioneAvvisiDigitali.class, id);
			p.setStatoAvviso(EnumStatoAvviso.INSERITO.getChiave());
			p.setDescrStatoAvviso(null);
			p.setNumTentativiAvviso(new Long(0));
			p.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			p.setOpAggiornamento("IDP");
			em.merge(p);
			
		}
		
	}

	
}
