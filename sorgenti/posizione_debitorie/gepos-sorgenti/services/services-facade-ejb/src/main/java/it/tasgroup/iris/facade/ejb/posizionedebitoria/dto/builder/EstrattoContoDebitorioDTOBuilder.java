package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.nch.is.fo.profilo.Indirizzipostali;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DettaglioBonificoDTO;
import it.tasgroup.iris.dto.ddp.DettaglioDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.bcel.generic.ALOAD;

public class EstrattoContoDebitorioDTOBuilder {
	
	public static IndirizzoDTO populateIndirizzoDTO(Intestatari intestatario){
		IndirizzipostaliCommon indirizzoEntity = intestatario.getIndirizzipostaliobj();
		IndirizzoDTO returnAddress = new IndirizzoDTO();
		returnAddress.setRagioneSociale(intestatario.getRagionesociale());
		returnAddress.setProvincia(indirizzoEntity.getProvinceIForm());
		returnAddress.setCap(intestatario.getIndirizzipostaliobj().getCapCodeIForm());
		returnAddress.setCitta(intestatario.getIndirizzipostaliobj().getCityIForm());
		returnAddress.setNumeroCivico(intestatario.getIndirizzipostaliobj().getNumeroCivicoIForm());
		returnAddress.setVia(intestatario.getIndirizzipostaliobj().getAddressIForm());
		returnAddress.setPiva(intestatario.getIndirizzipostaliobj().getVatCodeIForm());
		return returnAddress;
	}
	
	public static DocumentoDiPagamentoDTO populateDDPDTO(Intestatari intestatario, DocumentoDiPagamento ddp){
		DocumentoDiPagamentoDTO returnValue = new DocumentoDiPagamentoDTO();		
		returnValue.setId(ddp.getId());
		IndirizzoDTO returnAddress = populateIndirizzoDTO(intestatario);
		ArrayList<IndirizzoDTO> indirizziRT = new ArrayList<IndirizzoDTO>();
		indirizziRT.add(returnAddress);
		returnValue.setIndirizziRT(indirizziRT);
		return returnValue;
	}
	
	public static List<DocumentoDiPagamentoDTO> populateDDPDTOList(Intestatari intestatario, List<DocumentoDiPagamento> ddpList){
		
		List<DocumentoDiPagamentoDTO> returnDTOList = new ArrayList<DocumentoDiPagamentoDTO> ();
		
		for(DocumentoDiPagamento ddp: ddpList){
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO = new DocumentoDiPagamentoDTO();
			documentoDiPagamentoDTO.setId(ddp.getId());
			
			populateIndirizzoList(intestatario, documentoDiPagamentoDTO);			
			
			populateCarrello(ddp, documentoDiPagamentoDTO);
			
			populateSpecificDetails(ddp, documentoDiPagamentoDTO);
		
			returnDTOList.add(documentoDiPagamentoDTO);
		}
				
		return returnDTOList;
	}

	private static void populateSpecificDetails(DocumentoDiPagamento ddp,
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO) {
		List<DettaglioDTO> dettaglioDTOList = new ArrayList<DettaglioDTO>();
		DettaglioDTO dettaglioDTO = null;
	/*	
		switch (ddp.getTipo()) {
		case BONIFICO:
			dettaglioDTO = createBonificoSpecificDetail();
			break;
		case ATM:
			dettaglioDTO = createATMSpecificDetail();
			break;
		case GDO:
			dettaglioDTO = createGDOSpecificDetail();
			break;
		};
		*/
		dettaglioDTOList.add(dettaglioDTO);
		documentoDiPagamentoDTO.setSpecificDetails(dettaglioDTOList);
	}

	private static DettaglioDTO createGDOSpecificDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	private static DettaglioDTO createATMSpecificDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void populateCarrello(DocumentoDiPagamento ddp,
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO) {
		List<CondizioneDDPDTO> carrello = new ArrayList<CondizioneDDPDTO>();
		
		Set<CondizioneDocumento> condizioni = ddp.getCondizioni();
		for (CondizioneDocumento condizioneDocumento : condizioni) {
			CondizioneDDPDTO condizioneDDPDTO = new CondizioneDDPDTO();
			
//			String ente = condizioneDocumento.getCondizionePagamento().getEnte().getDenom();
	//		condizioneDDPDTO.setEnte(ente);
			
			BigDecimal importo = condizioneDocumento.getCondizionePagamento().getImTotale();
			condizioneDDPDTO.setImporto(importo);
			
			Date scadenza = condizioneDocumento.getCondizionePagamento().getDtScadenza();
			condizioneDDPDTO.setScadenza(scadenza);
			
//			String tributo = condizioneDocumento.getCondizionePagamento().getPendenza().getTributo().getDeTrb();
	//		condizioneDDPDTO.setTributo(tributo);
			
			carrello.add(condizioneDDPDTO);
		}
		
		documentoDiPagamentoDTO.setCarrello(carrello);
	}

	private static DettaglioDTO createBonificoSpecificDetail() {
//		DettaglioBonificoDTO dettaglioDTO = new DettaglioBonificoDTO();
//		String beneficiario = ddp.getCondizioni();
//		dettaglioDTO.setBeneficiario(beneficiario  );
//		dettaglioDTO.setCognomeOrdinante(cognomeOrdinante);
//		dettaglioDTO.setCoordinateBancarie(coordinateBancarie);
//		dettaglioDTO.setNomeOrdinante(nomeOrdinante);
//		dettaglioDTO.setStato(stato);
//		return dettaglioDTO;
		return null;
	}

	private static void populateIndirizzoList(Intestatari intestatario,
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO) {
		IndirizzoDTO dtoAddress = populateIndirizzoDTO(intestatario);
		List<IndirizzoDTO> indirizziRT = new ArrayList<IndirizzoDTO>();
		indirizziRT.add(dtoAddress);
		documentoDiPagamentoDTO.setIndirizziRT(indirizziRT);
	}
	
	public static DocumentoDiPagamento populateDDPEntity(DocumentoDiPagamentoDTO ddp){
		DocumentoDiPagamento returnValue = new DocumentoDiPagamento();
		returnValue.setId(ddp.getId());
		return returnValue;
	}

	private static Indirizzipostali populateIndirizzoPostale(IndirizzoDTO addressVO) {
		Indirizzipostali addressEntity = new Indirizzipostali();
		addressEntity.setCapCode(addressVO.getCap());
		return null;
	}
	
}

