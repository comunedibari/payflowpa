package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DettaglioDDPDTO;
import it.tasgroup.iris.dto.ddp.DettaglioDTO;
import it.tasgroup.iris.dto.ddp.DettaglioNDPDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTOLight;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.report.PrintableDocument;
import it.tasgroup.report.utility.PdfUtils;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author PazziK
 * 
 */
public class DDPDTOBuilder {

	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");

	
	public static DocumentiRepository populateDocumentoRepository(IProfileManager profile, DocumentoRepositoryDTO docRepository) {
		
		DocumentiRepository docRepo = new DocumentiRepository();
		docRepo.setDimensione(docRepository.getSize());
		docRepo.setDocumento(docRepository.getContent());
		docRepo.setId(docRepository.getId());
		docRepo.setNomeFile(docRepository.getFileName());
		if (profile != null){
			docRepo.setOpInserimento(profile.getCodiceFiscale());			
			docRepo.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		}
		return docRepo;
	}
	
	public static DocumentiRepository populateDocumentoRepository(Intestatari intestatario, DocumentoRepositoryDTO docRepository) {
		
		DocumentiRepository docRepo = new DocumentiRepository();
		docRepo.setDimensione(docRepository.getSize());
		docRepo.setDocumento(docRepository.getContent());
		docRepo.setId(docRepository.getId());
		docRepo.setNomeFile(docRepository.getFileName());
		if (intestatario != null){
			docRepo.setOpInserimento(intestatario.getLapl());			
			docRepo.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		}
		return docRepo;
	}
	
	public static DocumentoRepositoryDTO populateDocumentoRepositoryDTO(PrintableDocument printableDoc, DocumentiRepository docRepository) {
		
		DocumentoRepositoryDTO docRepoDTO = new DocumentoRepositoryDTO();
		
		if(docRepository!=null){

			if (printableDoc != null && printableDoc.needWatermark()) {
				ResourceBundle bundle = ResourceBundle.getBundle("watermark");
				String localizedStatus = printableDoc.getWatermarkText(bundle);

				try {
					byte[] markedPdf = PdfUtils.watermark(docRepository.getDocumento(), localizedStatus, 120, 0.1f, null, null, (float) Math.PI / 4);
					docRepoDTO.setContent(markedPdf);
					docRepoDTO.setSize(markedPdf.length);
				} catch (Exception e) {
					// TODO:MINO verificare gestione eccezioni ...
					e.printStackTrace();
					throw new RuntimeException(e);
				}

			} else {
				docRepoDTO.setContent(docRepository.getDocumento());
				docRepoDTO.setSize(docRepository.getDimensione());

			}
			docRepoDTO.setFileName(docRepository.getNomeFile());
			docRepoDTO.setFileExtension("pdf");
			docRepoDTO.setId(docRepository.getId());
		}
		
		return docRepoDTO;
		
	}
	
	public static DocumentoRepositoryDTO populateDocumentoRepositoryDTO(PrintableDocument printableDoc, byte[] documento, String nomeFile) {
		DocumentoRepositoryDTO docRepoDTO = new DocumentoRepositoryDTO();
		if (printableDoc != null && printableDoc.needWatermark()) {
			ResourceBundle bundle = ResourceBundle.getBundle("watermark");
			String localizedStatus = printableDoc.getWatermarkText(bundle);
			try {
				byte[] markedPdf = PdfUtils.watermark(documento, localizedStatus, 120, 0.1f, null, null, (float) Math.PI / 4);
				docRepoDTO.setContent(markedPdf);
				docRepoDTO.setSize(markedPdf.length);
			} catch (Exception e) {
				// TODO:MINO verificare gestione eccezioni ...
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			docRepoDTO.setContent(documento);
			docRepoDTO.setSize(documento.length);
		}
		docRepoDTO.setFileName(nomeFile);
		docRepoDTO.setFileExtension("pdf");
		return docRepoDTO;
	}

	public static List<DocumentoDiPagamentoDTO> populateDDPDTOList(Intestatari intestatarioDDP, Intestatari intestatario, List<DocumentoDiPagamento> ddpList, CfgEntiLogo logoEnte, ContoTecnico contoTecnico, Locale locale) {

		List<DocumentoDiPagamentoDTO> returnDTOList = new ArrayList<DocumentoDiPagamentoDTO>();
		
		for (DocumentoDiPagamento ddp : ddpList) {
			
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO = populateDDPDTO(intestatarioDDP, intestatario, ddp, logoEnte, contoTecnico, locale);
			
			returnDTOList.add(documentoDiPagamentoDTO);
		}
		
		return returnDTOList;
	}

	public static DocumentoDiPagamentoDTO populateDDPDTO( Intestatari intestatarioDDP, Intestatari intestatario,
			DocumentoDiPagamento ddp, CfgEntiLogo logoEnte, ContoTecnico contoTecnico, Locale locale) {

		DocumentoDiPagamentoDTO documentoDiPagamentoDTO = new DocumentoDiPagamentoDTO();
		
		IndirizzoDTOBuilder.populateIndirizzoList(intestatario, documentoDiPagamentoDTO);

		List<CondizioneDDPDTO> carrello = CondizioniDocumentoDTOBuilder.populateCarrello(ddp, logoEnte, locale);
		documentoDiPagamentoDTO.setCarrello(carrello);

		populateDDPSpecificDetails(intestatarioDDP, contoTecnico, ddp, documentoDiPagamentoDTO);
		documentoDiPagamentoDTO.setCfIntestatarioPendenza(ddp.getCondizioni().iterator().next().getCondizionePagamento().getPendenza().getDestinatari().iterator().next().getCoDestinatario());
		documentoDiPagamentoDTO.setCfPagante(ddp.getCodFiscalePagante());
		documentoDiPagamentoDTO.setEmailPagante(ddp.getEmailVersante());
		documentoDiPagamentoDTO.setId(ddp.getId());
		documentoDiPagamentoDTO.setImporto(ddp.getImporto());
		documentoDiPagamentoDTO.setImportoCommissioni(ddp.getImportoCommissioni());
		documentoDiPagamentoDTO.setDataPagamento(ddp.getTsEmissione());
		documentoDiPagamentoDTO.setDtScadenzaDoc(ddp.getDtScadenzaDoc());
		// i documenti di pagamento sono sempre relativi a pagamento offline?
		documentoDiPagamentoDTO.setPagamentoOffline(true); 
		documentoDiPagamentoDTO.setIntestatario(ddp.getIntestatario());
		
		//
		// setto il nome file in modo da usarlo anche su JaspeReport
		//
		EnumTipoDDP tipo = ddp.getTipoDocumentoEnum();
		documentoDiPagamentoDTO.setTipoDocumento(tipo);
		String downloadFileName = null;
		
		
		String ddpCustomPrefix = conf.getProperty("configuration.ddp.prefix");
		
		String prefix = "";
		if(ddpCustomPrefix.length() > 0)
			prefix = ddpCustomPrefix + "_";
		
		if (tipo == EnumTipoDDP.NDP) {
			documentoDiPagamentoDTO.setIuv(((DettaglioNDPDTO)documentoDiPagamentoDTO.getSpecificDetail()).getIdPagamento());
			// nel caso di Nodo Di Pagamenti il nome file viene calcolato utilizzando il Numero Avviso
			String specificFileName=((DettaglioNDPDTO)documentoDiPagamentoDTO.getSpecificDetail()).getFormattedID().replace(" ", "");
			downloadFileName = prefix + CommonConstant.DDP_FILE_NAME_PREFIX + tipo + "_" +specificFileName;
		} else
			downloadFileName = prefix + CommonConstant.DDP_FILE_NAME_PREFIX + tipo + "_" + ddp.getId();
		
		documentoDiPagamentoDTO.setNomeFile(downloadFileName);
		
		return documentoDiPagamentoDTO;
	}
	
	public static DocumentoDiPagamentoDTOLight populateDDPDTOLight(DocumentoDiPagamento ddp, String idDdpSelected, Locale locale) {
		
		DocumentoDiPagamentoDTOLight ddpSummaryDTO = new DocumentoDiPagamentoDTOLight();
		
		ddpSummaryDTO.setId(ddp.getId());
		ddpSummaryDTO.setDataAnnullamento(ddp.getTsAnnullamento());
		ddpSummaryDTO.setDataEmissione(ddp.getTsEmissione());
		ddpSummaryDTO.setStatoDDP(ddp.getStatoEnum());
		ddpSummaryDTO.setTipoDDP(ddp.getTipoDocumentoEnum());
		ddpSummaryDTO.setNumCondizioni(ddp.getNumCondizioni());
		ddpSummaryDTO.setOpInserimento(ddp.getOpInserimento());
		ddpSummaryDTO.setIntestatario(ddp.getIntestatario());
		ddpSummaryDTO.setEmail(ddp.getEmailVersante());
		ddpSummaryDTO.setCodPagante(ddp.getCodFiscalePagante());
		
		ddpSummaryDTO.setDistinta(ddp.getDistinta() != null ? DistintePagamentoDTOBuilder.populateDistintePagamentoDTOLight(ddp.getDistinta()) : null);
		
		if (idDdpSelected != null && idDdpSelected.equals(ddp.getId())){
			List<CondizioneDDPDTO> carrello = CondizioniDocumentoDTOBuilder.populateCarrello(ddp, null, locale);
			ddpSummaryDTO.setCarrello(carrello);
		}
		
		ddpSummaryDTO.setImportoNetto(ddp.getImporto());
		ddpSummaryDTO.setImporto(ddp.getImporto().add(ddp.getImportoCommissioni()).setScale(2,RoundingMode.HALF_UP));
		ddpSummaryDTO.setImportoCommissioni(ddp.getImportoCommissioni());
		
		return ddpSummaryDTO;
		
	}
		

	public static List<DocumentoDiPagamentoDTOLight> populateDDPDTOLightList(List<DocumentoDiPagamento> ddpList, String idDdpSelected, Locale locale) {
		
		List<DocumentoDiPagamentoDTOLight> returnDTOList = new ArrayList<DocumentoDiPagamentoDTOLight>();

		for (DocumentoDiPagamento ddp : ddpList) {
			
			DocumentoDiPagamentoDTOLight ddpSummaryDTO = populateDDPDTOLight(ddp, idDdpSelected, locale);

			returnDTOList.add(ddpSummaryDTO);
		}

		return returnDTOList;
	}
	
	private static void populateDDPSpecificDetails(Intestatari intestatarioDDP, ContoTecnico contoTecnico, DocumentoDiPagamento ddp, DocumentoDiPagamentoDTO documentoDiPagamentoDTO) {
		
		List<DettaglioDTO> dettaglioDTOList = new ArrayList<DettaglioDTO>();
		DettaglioDDPDTO dettaglioDTO = DettaglioDDPDTOFactory.createSpecificDetail(intestatarioDDP, contoTecnico, ddp);
		dettaglioDTOList.add(dettaglioDTO);
		documentoDiPagamentoDTO.setSpecificDetails(dettaglioDTOList);
	}
	
	public static DocumentiRepository makeDocumentiRepository(byte[] reportFlow, String downloadFileName,  String opInserimento) {
		DocumentiRepository docRepo = new DocumentiRepository();
		docRepo.setDocumento(reportFlow);
		docRepo.setDimensione(reportFlow.length);
		docRepo.setNomeFile(downloadFileName);
		docRepo.setOpInserimento(opInserimento);
		docRepo.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		return docRepo;		
	}

}
