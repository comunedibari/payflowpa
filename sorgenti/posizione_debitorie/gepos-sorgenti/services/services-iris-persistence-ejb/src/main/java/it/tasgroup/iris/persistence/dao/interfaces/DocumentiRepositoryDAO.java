package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface DocumentiRepositoryDAO extends Dao<DocumentiRepository>{
	
	public DocumentiRepository retrieveById(Long id);
	
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo);

	public DocumentiRepository updateDocumentiRepository(DocumentiRepository docRepo);

	public Long readVersion(Long idDocRepo);

	public void updateAckDownload(String ackDownload, Long idDocRepo, Long version);
	
}
