package it.tasgroup.iris.persistence.dao;

import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioneDocumentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CondizioniDocumentoDaoService") 
public class CondizioneDocumentoDaoImpl extends DaoImplJpaCmtJta<CondizioneDocumento> implements CondizioneDocumentoDao{
	private static final Logger LOGGER = LogManager.getLogger(GestioneFlussiDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
    @Override
 	public List<CondizioneDocumento> listCondizioniByIdDocumentoDiPagamento(String idDocumento){
	return null;	
	}


}
