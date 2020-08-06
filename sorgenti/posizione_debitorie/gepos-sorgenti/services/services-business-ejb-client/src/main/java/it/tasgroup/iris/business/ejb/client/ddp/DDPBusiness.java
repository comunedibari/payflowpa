package it.tasgroup.iris.business.ejb.client.ddp;

import it.nch.is.fo.profilo.Intestatari;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgUtenteModalitaPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.SecurityException;
import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;

import java.util.List;
import java.util.Locale;


/**
 * @author PazziK
 *
 */
public interface DDPBusiness
{	

	public List<DocumentoDiPagamento> readDDPs(String codFiscale, String azienda, ContainerDTO containerDTO) throws SecurityException;
	
	public List<DocumentoDiPagamento> readDDPListByIdCondizione(String idCondizione);
	
	/**
	 * Metodo di raccordo tra il download dei DDP da webapp ed il metodo di creazione dei DDP 
	 * accentrato tra DocumentsWS e webapp
	 * 
	 * @param profile
	 * @param inputDTO
	 * @return
	 * @throws BusinessConstraintException
	 */
	public DocumentoDiPagamento createDDP(IProfileManager profile, DDPInputDTO inputDTO, Intestatari intestatarioDDP) throws BusinessConstraintException;

	/**
	 * Creazione del DDP comune a DocumentsWS e download di DDP da webapp
	 * 
	 * @param dto
	 * @param cfgGateway
	 * @return
	 * @throws BusinessConstraintException
	 */
	public DocumentoDiPagamento createDDP(DDPInputDTO dto, Intestatari intestatarioDDP, CfgGatewayPagamento cfgGateway) throws BusinessConstraintException;	
	
	public void nullifyDDPList(String codFiscale, String[] ddpIds) throws SecurityException;
	
	public CondizionePagamento readCondizionePagamento(String idCondizione);
	
	public DocumentiRepository readDocumentiRepositoryById(Long idDocRepository);
	
	public void checkDoubleDocument(List<String> condizioniCarrello) throws BusinessConstraintException;
	
	public Pagamenti readPagamentoById(Long idPagamento);	

	public byte[] creaPdfDocumentoDiPagamento(List<DocumentoDiPagamentoDTO> dtoList, Locale locale);
	
	public byte[] creaPdfRicevuta(List<DocumentoDiPagamentoDTO> dtoList, Locale locale);

	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, DocumentoDiPagamento ddp);
	
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, GestioneFlussi distinta);
	
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, Long long1, AllegatiPendenza allegato,String idDocumentoExt);
	
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, String idDDP);

	public DocumentiRepository updateDocumentiRepository(DocumentiRepository docRepo);
	
	public List<CfgUtenteModalitaPagamento> readUtenteModalitaPagamentoUtilizzate(String codiceFiscale);

	public void checkEnteTributo(DDPInputDTO dto) throws BusinessConstraintException;

	public DocumentoDiPagamento readSingleDDP_BKOF(String docId);

	public List<DocumentoDiPagamento> readDDPsForBackOffice(ContainerDTO dto) throws SecurityException;

	public List<CondizionePagamento> getListCondizioniByIdPagamento(String idPagamento, String codTributoEnte, String idEnte);

	public AllegatiPendenza getRicevutaAllegatoCondizione(String idPendenza, String idCondizione, Locale locale, EnumCodificaAllegato enumCodificaAllegato);

	public void ackRicevuta(Long idDocRepo);

	DocumentoDiPagamento checkExistingDocument(List<String> condizioniCarrello) throws BusinessConstraintException;
	
}
