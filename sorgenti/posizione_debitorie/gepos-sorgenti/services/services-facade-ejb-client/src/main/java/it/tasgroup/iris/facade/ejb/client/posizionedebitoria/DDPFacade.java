/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.posizionedebitoria;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgUtenteModalitaPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.dto.exception.SecurityException;
import it.tasgroup.iris.dto.flussi.PagamentoDTOLightForReceipt;
import it.tasgroup.services.util.enumeration.EnumAttachmentRendering;


/**
 * @author PazziK
 *
 */
public interface DDPFacade
{

	/**
	 * @param profile
	 * @param parameters
	 * @return
	 * @throws BusinessConstraintException 
	 * @throws Exception
	*/
	public List<DocumentoDiPagamentoDTO> readDDPList(String codFiscale, String azienda, ContainerDTO inputDTO, Locale locale) throws BusinessConstraintException;	
	
	/**
	 * @param profile
	 * @param parameters
	 * @return
	 * @throws SecurityException 
	 * @throws Exception
	*/
	public ContainerDTO readDDPLightList(String codFiscale, String azienda, ContainerDTO inputDTO, Locale locale) throws SecurityException;	
	 
	 
	/**
	 * @param profile
	 * @param dto
	 * @param mezzoDiPagamento
	 * @param locale
	 * @return
	 * @throws BusinessConstraintException
	 */
	public List<DocumentoDiPagamentoDTO> createDDP(IProfileManager profile, DDPInputDTO dto, Locale locale) throws BusinessConstraintException;
	
	/**
	 * Restituisce il file PDF di ricevuta del pagamento con idPagamento dato.
	 * Se il pagamento � in stato pagato (flag incasso 1 o 2) allora viene apposta la filigrana "PAGATO"
	 * 
	 * @param profile
	 * @param dto
	 * @return
	 * @throws Exception
	*/ 
	public DocumentoRepositoryDTO getRicevutaPagamento(Locale locale, String idPagamento,String codPagamento,String codPagante) throws BusinessConstraintException;
	
	/**
	 * Restituisce tutte le ricevute relative al carrello (distinta) con idFlusso dato
	 * Se il carrello (distinta) contiene un solo elemento viene restituito l'unico file PDF della ricevuta.
	 * Se invece ci sono pi� elementi viene restituito un archivio ZIP contenente i singoli file PDF delle ricevute. 
	 *  
	 * @param codPagamento
	 * @param idFlusso
	 * @param codPagante
	 * @param locale
	 * @return
	 * @throws BusinessConstraintException
	 */
	public DocumentoRepositoryDTO getRicevutaCarrello(String codPagamento, String idFlusso, String codPagante, Locale locale) throws BusinessConstraintException;
	
	/**
	 * @param idCondizione
	 * @return
	 * @throws BusinessConstraintException 
	 * @throws Exception
	 */
	public List<DocumentoDiPagamentoDTO> readDDPListByIdCondizione(String idCondizione, Locale locale) throws BusinessConstraintException;
	
	/**
	 * @param profile
	 * @param ddpIds
	 * @throws SecurityException 
	 */
	public void nullifyDDPList(String codFiscale, String[] ddpIds) throws SecurityException;
	
	/**
	 * 
	 * @param condizioniCarrello
	 * @throws BusinessConstraintException
	 */
	public void checkDoubleDocument(List<String> condizioniCarrello) throws BusinessConstraintException;
	
	/**
	 * @param docRepoDTO
	 * @return
	 */
	public DocumentoRepositoryDTO createNewDocumentiRepository(IProfileManager profile, DocumentoRepositoryDTO docRepoDTO, String idDDP);

	/**
	 * @param intestatarioDDP
	 * @param docRepoDTO
	 * @return
	 */
	public DocumentoRepositoryDTO createNewDocumentiRepository(String intestatarioDDP, DocumentoRepositoryDTO docRepoDTO, String idDDP);

	public List<CfgGatewayPagamentoDTO> getLstCfgUtenteModalitaPagamentoUtilizzate(String codFiscale, BigDecimal importoTotalePagamenti) throws BusinessConstraintException;
	
	public List<CfgGatewayPagamentoDTO> getUtenteModalitaPagamentoUtilizzate(String codFiscale) throws BusinessConstraintException;
	
	public List<CfgUtenteModalitaPagamentoDTO> readLstCfgUtenteModalitaPagamento(String codFiscale) throws BusinessConstraintException;

	public DocumentoDiPagamentoDTO readSingleDDP_BKOF(String docId, Locale locale) throws BusinessConstraintException;

	public ContainerDTO readDDPLightListForBackOffice(ContainerDTO containerDTO, Locale locale) throws SecurityException;
	
	public List<DocumentoRepositoryDTO> generaRicevuteDelCarrello(String codPagamento, String idFlusso, String codPagante, Locale locale, boolean createResult, EnumAttachmentRendering attachmentRendering) throws BusinessConstraintException;
	
	public List<DocumentoRepositoryDTO> generaRicevutaSingolaDelCarrello(PagamentoDTOLightForReceipt pagamentoDTO, String idFlusso, String codPagante, Locale locale, boolean createResult, EnumAttachmentRendering attachmentRendering) throws BusinessConstraintException;

	public List<DocumentoDiPagamentoDTO> createDDPMultiBiller(DDPInputDTO inputDTO, Locale locale) throws BusinessConstraintException;

	public DocumentoRepositoryDTO createReceiptMultiBiller(DDPInputDTO inputDTO, Locale locale) throws BusinessConstraintException;
			
	public void ackRicevuta(String senderId, String idPagamento, boolean includiRTAgid) throws InvalidInputException;
	
}
