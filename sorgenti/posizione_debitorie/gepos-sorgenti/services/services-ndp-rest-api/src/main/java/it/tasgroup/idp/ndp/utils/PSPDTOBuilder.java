package it.tasgroup.idp.ndp.utils;

import static it.tasgroup.idp.ndp.utils.NdpRestApiConst.CODICE_CONTESTO_PAGAMENTO;
import static it.tasgroup.idp.ndp.utils.NdpRestApiConst.IDENTIFICATIVO_CANALE_PSP;
import static it.tasgroup.idp.ndp.utils.NdpRestApiConst.ID_PSP;
import static it.tasgroup.idp.ndp.utils.NdpRestApiConst.INTERMEDIARIO_PSP;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoPagatore;
import it.gov.digitpa.schemas.x2011.pagamenti.CtSoggettoVersante;
import it.tasgroup.idp.rs.model.pagamenti.NdpAttivaVerificaRisposta;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.EnumTipoAutorizzazione;
import it.tasgroup.iris.dto.EsitoCondizioneDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.IdentificativoUnivocoVersamentoDTO;
import it.tasgroup.iris.dto.RichiestaAUPPerListaCodiciDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumNDPError;
/** 
 * @author pazzik
 *
 */
public class PSPDTOBuilder {
	
	private static final String SENDER_SIL_PSP = "SENDER_SIL_PSP";
	
	public static RichiestaAutorizzazioneDTO fillRichiestaAutorizzazioneDTO(String identificativoDominio, String  identificativoUnivocoVersamento, BigDecimal importo, String codiceContestoPagamento) {
	
		RichiestaAutorizzazioneDTO dto = new RichiestaAutorizzazioneDTO();
		
		//------ TESTATA BEGIN
        TestataMessaggioDTO testataDto = new TestataMessaggioDTO();
		
		testataDto.setSenderSil(SENDER_SIL_PSP);
		
		testataDto.setSenderSys(ID_PSP);		
		
		
		//------ TESTATA END
		//----- fillTestataDTO
        SessionIdDTO session = new SessionIdDTO();
		
		if (ID_PSP.length() > 5)
			session.setIdSistema(ID_PSP.substring(0,5));
		else 
			session.setIdSistema(ID_PSP);
		
		session.setIdTerminale(SENDER_SIL_PSP);
		
		session.setToken(codiceContestoPagamento);
		
		Date now = new Date(System.currentTimeMillis());
		
		session.setDataOraAccesso(now.toString());
		//------
        testataDto.setSession(session);
		
		testataDto.setIdFiscaleCreditore(identificativoDominio);
		
		testataDto.setCodiceContestoPagamento(codiceContestoPagamento);
		dto.setTestata(testataDto);
		
		//
		dto.setIdentificativoCanalePSP(IDENTIFICATIVO_CANALE_PSP);
		dto.setIdentificativoIntermediarioPSP(INTERMEDIARIO_PSP);
		//
		
		dto.setCodTransazionePSP(codiceContestoPagamento);
		
		dto.setIuv(identificativoUnivocoVersamento);
		dto.setExtCodPagamento(null);  // Il cod pagamento verr� generato con un GUID
		dto.setExtTransactionId(null); // Il cod transazione verr� generato con un GUID
		
		String codVersante = null;
		
		String codDebitore = null;
		
		dto.setIdentificativoPSP(ID_PSP);
		
		CtSoggettoVersante soggVersante = null;//richiesta.getDatiPagamentoPSP().getSoggettoVersante();
		
		CtSoggettoPagatore soggPagatore = null;//richiesta.getDatiPagamentoPSP().getSoggettoPagatore();
		
		if (soggVersante != null)
			codVersante = soggVersante.getIdentificativoUnivocoVersante().getCodiceIdentificativoUnivoco();
		
		if (soggPagatore != null)
			codDebitore = soggPagatore.getIdentificativoUnivocoPagatore().getCodiceIdentificativoUnivoco();
		
        RichiestaAUPPerListaCodiciDTO codAutorizzazioneDTO = new RichiestaAUPPerListaCodiciDTO();
		
		codAutorizzazioneDTO.setCodiceFiscaleCreditore(identificativoDominio);
			
		IdentificativoUnivocoVersamentoDTO idVersamentoDTO = new  IdentificativoUnivocoVersamentoDTO();
		
		idVersamentoDTO.setIdPagamentoCreditore(identificativoUnivocoVersamento);
		
		idVersamentoDTO.setTipoIdDebitoCreditore(EnumTipoAutorizzazione.ID_PAGAMENTO_CREDITORE);
		
		idVersamentoDTO.setImporto(importo);
		
		idVersamentoDTO.setCodiceVersante(codVersante);
		
		idVersamentoDTO.setCodiceDebitore(codDebitore);
		
		codAutorizzazioneDTO.getIdVersamenti().add(idVersamentoDTO);
				
		dto.getListaCodici().add(codAutorizzazioneDTO);
		
		return dto;
	
	}
	
	
	private static EnumBusinessReturnCodes getSpecificReturnCode(List<EsitoCondizioneDTO> esitiCondizioni) {
		
		for(EsitoCondizioneDTO esitoCond : esitiCondizioni){
			
			EnumBusinessReturnCodes currRetCode = esitoCond.getReturnCode();
			
			if (currRetCode != null)
				
					return currRetCode;
		}
		
		return null;
	}

	/**
	 * 
	 */
	
	public static NdpAttivaVerificaRisposta buildEsitoAttivaVerifica(EsitoOperazionePagamentoDTO esitoOpDto) {
		
		NdpAttivaVerificaRisposta esito = new NdpAttivaVerificaRisposta();
		EnumBusinessReturnCodes globalReturnCode = esitoOpDto.getReturnCode();
		EnumBusinessReturnCodes specificReturnCode = getSpecificReturnCode(esitoOpDto.getEsitiCondizioni());

		if (!globalReturnCode.isError()){
			EsitoCondizioneDTO esitoCond = esitoOpDto.getEsitiCondizioni().get(0);

			CondizionePagamentoDTO cond = esitoCond.getCondizione();
			BigDecimal importo = cond.getImTotale();
			esito.setImportoSingoloVersamento(importo.setScale(2));
			String causaleIntera = cond.getCausalePendenza();

			String causaleTroncata = causaleIntera.length() > 140 ? causaleIntera.substring(0, 135).concat("...") : causaleIntera;
			esito.setCausaleVersamento(causaleTroncata);
			//**** Ente Beneficiario
			if (esitoOpDto.getDescrizioneEnte()!=null) {
				esito.setTipoIdentificativoUnivocoBeneficiario("G");
				esito.setCodiceIdentificativoUnivocoBeneficiario(esitoOpDto.getIdDominio());
				//
				esito.setDenominazioneBeneficiario(esitoOpDto.getDescrizioneEnte());
			}

			esito.setIbanAccredito(esitoOpDto.getIbanAccredito());
		}
		//*****
		if (globalReturnCode.isError()) {
			esito.setEsito("KO");

			if (specificReturnCode == null || !specificReturnCode.isError())
				specificReturnCode = globalReturnCode;
			
			esito.setFaultCode(EnumNDPError.getFromIrisReturnCode(specificReturnCode).toString());
			esito.setFaultString(EnumNDPError.getFaultStringFromIrisRetCode(specificReturnCode));
			esito.setFaultDescription(EnumNDPError.getFaultStringFromIrisRetCode(specificReturnCode));
		} else
			esito.setEsito("OK");

		return esito;
	}
	
}
