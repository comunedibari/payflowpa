/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.persistence.dao.interfaces.DocumentiRepositoryDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PazziK
 * 
 */
@Stateless(name="DocumentiRepositoryDaoImpl")
public class DocumentiRepositoryDaoImpl extends DaoImplJpaCmtJta<DocumentiRepository> implements DocumentiRepositoryDAO {

	private static final Logger LOGGER = LogManager.getLogger(DocumentiRepositoryDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public DocumentiRepository retrieveById(Long id) {
		DocumentiRepository retrieved = null;
		try {
			retrieved = loadById(DocumentiRepository.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}

	@Override
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo) {
		try {  
						
			DocumentiRepository created = create(docRepo);
			
			return created;
			
		} catch (Exception e) {
			LOGGER.error("error on  createDocumentiRepository, " + docRepo, e);
			throw new DAORuntimeException(e);
		}
	}	
	
	@Override
	public DocumentiRepository updateDocumentiRepository(DocumentiRepository docRepo) {
		try {  
						
			DocumentiRepository updated = update(docRepo);
			
			return updated;
			
		} catch (Exception e) {
			LOGGER.error("error on  updateDocumentiRepository, " + docRepo, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public Long readVersion(Long idDocRepo) {
		
		Long result = null;
		
		Map params = new HashMap();
		
		params.put("ID", idDocRepo);																					
		
		try {
					
			result = (Long) uniqueResultByQuery("getVersion", params);
			
		} catch (Exception e) {
			
			LOGGER.error("error on readVersion ", e);
			
			throw new DAORuntimeException(e);
		}
		
		return result;
	}

	@Override
	public void updateAckDownload(String ackDownload, Long idDocRepo, Long version) {
		
		Query query = createUpdateQuery(ackDownload, version, idDocRepo);
		
		query.executeUpdate();
		
	}	
	
	private Query createUpdateQuery(String ack_download, Long version, Long id) {

		String selectFromWhere = "update DOCUMENTI_REPOSITORY set ACK_DOWNLOAD = ?, VERSION = ?, TS_AGGIORNAMENTO = ?, OP_AGGIORNAMENTO = ? where ID = ?";
		
		Query query = em.createNativeQuery(selectFromWhere);

		query.setParameter(1, ack_download);

		query.setParameter(2, version+1);
		
		query.setParameter(3, new Timestamp(System.currentTimeMillis()));
		
		query.setParameter(4, "DOC_WS");

		query.setParameter(5, id);

		return query;
	}


}
