package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.services.dao.ejb.Dao;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface CondizioneDocumentoDao extends Dao<CondizioneDocumento>{
	
	public List<CondizioneDocumento> listCondizioniByIdDocumentoDiPagamento(String idDocumento);
	


}
