package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.services.dao.ejb.Dao;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

@Local
public interface DDPDAO extends Dao<DocumentoDiPagamento>{
	
	public List<DocumentoDiPagamento> listDDPByFilterParameters(String codFiscale, String azienda,ContainerDTO ddpInputDTO);
	
	public List<DocumentoDiPagamento> listDDPByIdCondizione(String idCondizione);
	
	public DocumentoDiPagamento retrieveDDPById(String id);
	
	public Long countDDPByIdAndStatus(String docId, EnumStatoDDP satus);
	
	public DocumentoDiPagamento createDDP(DDPInputDTO inputDTO, CfgGatewayPagamento cfg);
		
	public List<DocumentoDiPagamento> listDDPBylistIdCondizioni(List<String> condizioni);
	
	public Set<CondizioneDocumento> listCondizioniDiPagamentoByIdDocumento(String idDocumento );
	
	public Integer countDDPByIdCondizione(String idCondizione);

	public void nullifyDDPList(String codFiscale, String[] idDocumenti);
	
	public DocumentoDiPagamento updateDDP(DocumentoDiPagamento ddp);
	
}
