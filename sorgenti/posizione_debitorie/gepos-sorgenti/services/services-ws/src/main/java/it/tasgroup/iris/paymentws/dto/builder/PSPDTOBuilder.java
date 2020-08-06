package it.tasgroup.iris.paymentws.dto.builder;

import gov.telematici.pagamenti.ws.EsitoAttivaRPT;
import gov.telematici.pagamenti.ws.EsitoVerificaRPT;
import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.PaaAttivaRPT;
import gov.telematici.pagamenti.ws.PaaAttivaRPTRisposta;
import gov.telematici.pagamenti.ws.PaaTipoDatiPagamentoPA;
import gov.telematici.pagamenti.ws.PaaVerificaRPT;
import gov.telematici.pagamenti.ws.PaaVerificaRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.gov.digitpa.schemas._2011.pagamenti.CtEnteBeneficiario;
import it.gov.digitpa.schemas._2011.pagamenti.CtIdentificativoUnivocoPersonaG;
import it.gov.digitpa.schemas._2011.pagamenti.CtSoggettoPagatore;
import it.gov.digitpa.schemas._2011.pagamenti.CtSoggettoVersante;
import it.gov.digitpa.schemas._2011.pagamenti.StTipoIdentificativoUnivocoPersG;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.EnumTipoAutorizzazione;
import it.tasgroup.iris.dto.EsitoCondizioneDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.IdentificativoUnivocoVersamentoDTO;
import it.tasgroup.iris.dto.RichiestaAUPPerListaCodiciDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumNDPError;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class PSPDTOBuilder {
	
	private static final String SENDER_SIL_PSP = "SENDER_SIL_PSP";
	
	public static RichiestaAutorizzazioneDTO fillRichiestaAutorizzazioneDTO(IntestazionePPT testata, PaaAttivaRPT richiesta) {
	
		RichiestaAutorizzazioneDTO dto = new RichiestaAutorizzazioneDTO();
		
		dto.setTestata(fillTestataDTO(testata,richiesta.getIdentificativoPSP()));
		
		//
		dto.setIdentificativoCanalePSP(richiesta.getIdentificativoCanalePSP());
		dto.setIdentificativoIntermediarioPSP(richiesta.getIdentificativoIntermediarioPSP());
		//
		
		dto.setCodTransazionePSP(testata.getCodiceContestoPagamento());
		
		dto.setIuv(testata.getIdentificativoUnivocoVersamento());
		dto.setExtCodPagamento(null);  // Il cod pagamento verr� generato con un GUID
		dto.setExtTransactionId(null); // Il cod transazione verr� generato con un GUID
		
		String codVersante = null;
		
		String codDebitore = null;
		
		dto.setIdentificativoPSP(richiesta.getIdentificativoPSP());
		
		CtSoggettoVersante soggVersante = richiesta.getDatiPagamentoPSP().getSoggettoVersante();
		
		CtSoggettoPagatore soggPagatore = richiesta.getDatiPagamentoPSP().getSoggettoPagatore();
		
		if (soggVersante != null)
			codVersante = soggVersante.getIdentificativoUnivocoVersante().getCodiceIdentificativoUnivoco();
		
		if (soggPagatore != null)
			codDebitore = soggPagatore.getIdentificativoUnivocoPagatore().getCodiceIdentificativoUnivoco();
		
		fillRichiestaAUPPerIdPagamento(dto, testata, richiesta.getDatiPagamentoPSP().getImportoSingoloVersamento(), codVersante, codDebitore);
	
		return dto;
	
	}
	
	public static void fillRichiestaAUPPerIdPagamento(RichiestaAutorizzazioneDTO dto, IntestazionePPT testata, BigDecimal importo, String codVersante,String codDebitore) {
						
		RichiestaAUPPerListaCodiciDTO codAutorizzazioneDTO = new RichiestaAUPPerListaCodiciDTO();
		
		codAutorizzazioneDTO.setCodiceFiscaleCreditore(testata.getIdentificativoDominio());
			
		IdentificativoUnivocoVersamentoDTO idVersamentoDTO = new  IdentificativoUnivocoVersamentoDTO();
		
		idVersamentoDTO.setIdPagamentoCreditore(testata.getIdentificativoUnivocoVersamento());
		
		idVersamentoDTO.setTipoIdDebitoCreditore(EnumTipoAutorizzazione.ID_PAGAMENTO_CREDITORE);
		
		idVersamentoDTO.setImporto(importo);
		
		idVersamentoDTO.setCodiceVersante(codVersante);
		
		idVersamentoDTO.setCodiceDebitore(codDebitore);
		
		codAutorizzazioneDTO.getIdVersamenti().add(idVersamentoDTO);
				
		dto.getListaCodici().add(codAutorizzazioneDTO);
		
	}
	
	private static TestataMessaggioDTO fillTestataDTO(IntestazionePPT testata, String idPSP) {
		
		TestataMessaggioDTO testataDto = new TestataMessaggioDTO();
		
		testataDto.setSenderSil(SENDER_SIL_PSP);
		
		testataDto.setSenderSys(idPSP);		
		
		testataDto.setSession(PSPDTOBuilder.fillSessionIdDTO(testata,idPSP));
		
		testataDto.setIdFiscaleCreditore(testata.getIdentificativoDominio());
		
		testataDto.setCodiceContestoPagamento(testata.getCodiceContestoPagamento());
		
		return testataDto;

	}

	private static SessionIdDTO fillSessionIdDTO(IntestazionePPT testata, String idPSP) {
		
		SessionIdDTO session = new SessionIdDTO();
		
		if (idPSP.length() > 5)
			session.setIdSistema(idPSP.substring(0,5));
		else 
			session.setIdSistema(idPSP);
		
		session.setIdTerminale(SENDER_SIL_PSP);
		
		session.setToken(testata.getCodiceContestoPagamento());
		
		Date now = new Date(System.currentTimeMillis());
		
		session.setDataOraAccesso(now.toString());
		
		return session;
		
	}
	
	private static EsitoAttivaRPT fillEsitoAttivaRPT(EsitoOperazionePagamentoDTO esitoOpDto){
		
		EsitoAttivaRPT esito = new EsitoAttivaRPT();
		
		EnumBusinessReturnCodes globalReturnCode = esitoOpDto.getReturnCode();
		
		EnumBusinessReturnCodes specificReturnCode = getSpecificReturnCode(esitoOpDto.getEsitiCondizioni());
		
		
		
		if (globalReturnCode.isError()){
			
			esito.setEsito("KO");
			
			if (specificReturnCode==null || !specificReturnCode.isError())
				specificReturnCode = globalReturnCode;
			
			FaultBean fault = new FaultBean();
			
			fault.setFaultCode(EnumNDPError.getFromIrisReturnCode(specificReturnCode).toString());
			
			fault.setFaultString(EnumNDPError.getFaultStringFromIrisRetCode(specificReturnCode));
			
			//fault.setDescription(value);
			
			//fault.setSerial(value);
			
			fault.setId(esitoOpDto.getIdDominio());
			
			esito.setFault(fault);
			
		} else esito.setEsito("OK");
		
		return esito;
		
	} 
	
	private static PaaTipoDatiPagamentoPA fillPaaTipoDatiPagamentoPA(EsitoOperazionePagamentoDTO esitoOpDto){
		
		EsitoCondizioneDTO esitoCond = esitoOpDto.getEsitiCondizioni().get(0);
		
		CondizionePagamentoDTO cond = esitoCond.getCondizione();
		
		PaaTipoDatiPagamentoPA tipoDati = new PaaTipoDatiPagamentoPA();
		
		BigDecimal importo = cond.getImTotale();
		
		tipoDati.setImportoSingoloVersamento(importo.setScale(2));
		
		// TODO PAZZIK VALORIZZARE
		//tipoDati.setIbanAccredito(esitoOpDto.getIbanAccreditoTributo());
		
		//tipoDati.setCredenzialiPagatore(value);
		
		//18-06-2014 PAZZIK mettiamo la causale pendenza perch� la causale condizione e nulla per i pagamentispontanei.
		String causaleIntera = cond.getCausalePendenza();
		
		String causaleTroncata = causaleIntera.length() > 140 ? causaleIntera.substring(0, 135).concat("...") : causaleIntera;
		
		tipoDati.setCausaleVersamento(causaleTroncata);
		
		//**** Ente Beneficiario
		if (esitoOpDto.getDescrizioneEnte()!=null) {
		   CtEnteBeneficiario enteBen = new CtEnteBeneficiario();
		   //
		   CtIdentificativoUnivocoPersonaG identUnivPers = new CtIdentificativoUnivocoPersonaG();
		   identUnivPers.setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersG.G);
		   identUnivPers.setCodiceIdentificativoUnivoco(esitoOpDto.getIdDominio());
		   //
		   enteBen.setIdentificativoUnivocoBeneficiario(identUnivPers);
		   enteBen.setDenominazioneBeneficiario(esitoOpDto.getDescrizioneEnte());
		
		   tipoDati.setEnteBeneficiario(enteBen);
        }
		
		tipoDati.setIbanAccredito(esitoOpDto.getIbanAccredito());
		//*****
		tipoDati.setCredenzialiPagatore(esitoOpDto.getCodiceFiscaleDebitore());
		
		//tipoDati.setBicAccredito(value);
		
		return tipoDati;
		
	}
	
	// PAZZIK 21-03-2014 REFS #1390 (INUTILIZZATO SOLO TEMPORANEAMENTE)
	@SuppressWarnings("unused")
	private static CtEnteBeneficiario fillCtEnteBeneficiario(EsitoOperazionePagamentoDTO esitoOpDto){
		
		EsitoCondizioneDTO esitoCond = esitoOpDto.getEsitiCondizioni().get(0);
		
		CondizionePagamentoDTO cond = esitoCond.getCondizione();
		
		EnteDTO enteDTO = cond.getEnte();
				
		CtEnteBeneficiario enteBeneficiario = new CtEnteBeneficiario();
		
		CtIdentificativoUnivocoPersonaG idPersona = new CtIdentificativoUnivocoPersonaG();
		
		IndirizzoDTO indirizzoDTO = enteDTO.getIntestatario().getIndirizzo();
		
		idPersona.setCodiceIdentificativoUnivoco(enteDTO.getCodice());
		
		enteBeneficiario.setIdentificativoUnivocoBeneficiario(idPersona);
		
		enteBeneficiario.setDenominazioneBeneficiario(cond.getDescrizioneCreditore());
		
		enteBeneficiario.setIndirizzoBeneficiario(indirizzoDTO.getVia());
		
		enteBeneficiario.setCapBeneficiario(indirizzoDTO.getCap());
		
		enteBeneficiario.setCivicoBeneficiario(indirizzoDTO.getNumeroCivico());
		
		enteBeneficiario.setLocalitaBeneficiario(indirizzoDTO.getCitta());
		
		enteBeneficiario.setNazioneBeneficiario(indirizzoDTO.getNazione());
		
		enteBeneficiario.setProvinciaBeneficiario(indirizzoDTO.getProvincia());
		
		//enteBeneficiario.setDenomUnitOperBeneficiario(value);
		
		//enteBeneficiario.setCodiceUnitOperBeneficiario(value);
		
		return enteBeneficiario;
	}

	public static PaaAttivaRPTRisposta fillPaaAttivaRPTRisposta(EsitoOperazionePagamentoDTO esitoOpDto) {
		
		PaaAttivaRPTRisposta risposta = new PaaAttivaRPTRisposta();
		
		EsitoAttivaRPT esito = fillEsitoAttivaRPT(esitoOpDto);
		
		risposta.setPaaAttivaRPTRisposta(esito);
		
		
		if (esito.getEsito().equals("KO"))
			return risposta;
			
		PaaTipoDatiPagamentoPA tipoDati = fillPaaTipoDatiPagamentoPA(esitoOpDto);
		// PAZZIK 21-03-2014 REFS #1390 (COMMENTO TEMPORANEO)
//		CtEnteBeneficiario enteBeneficiario = fillCtEnteBeneficiario(esitoOpDto);
//				
//		tipoDati.setEnteBeneficiario(enteBeneficiario);
//		
		esito.setDatiPagamentoPA(tipoDati);
		
		
		return risposta;
	}

	public static IntestazionePPT fillTestataRispostaDTO(IntestazionePPT header) {
		
		IntestazionePPT testata = new IntestazionePPT();
		
		return testata;
	}

	public static RichiestaAutorizzazioneDTO fillRichiestaAutorizzazioneDTO(IntestazionePPT testata, PaaVerificaRPT richiesta) {
		
		RichiestaAutorizzazioneDTO dto = new RichiestaAutorizzazioneDTO();
		
		dto.setTestata(fillTestataDTO(testata, richiesta.getIdentificativoPSP()));
		
		dto.setIdentificativoPSP(richiesta.getIdentificativoPSP());
		fillRichiestaAUPPerIdPagamento(dto, testata, null, null, null);
	
		return dto;
		
	}

	public static PaaVerificaRPTRisposta fillPaaVerificaRPTRisposta(EsitoOperazionePagamentoDTO esitoOpDto) {
		
		PaaVerificaRPTRisposta risposta = new PaaVerificaRPTRisposta();
		
		EsitoVerificaRPT esito = fillEsitoVerificaRPT(esitoOpDto);
		
		risposta.setPaaVerificaRPTRisposta(esito);
		
		if (esito.getEsito().equals("KO"))
			return risposta;
			
		PaaTipoDatiPagamentoPA tipoDati = fillPaaTipoDatiPagamentoPA(esitoOpDto);
		
		// PAZZIK 21-03-2014 REFS #1390 (COMMENTO TEMPORANEO)
//		CtEnteBeneficiario enteBeneficiario = fillCtEnteBeneficiario(esitoOpDto);
//				
//		tipoDati.setEnteBeneficiario(enteBeneficiario);
		
		esito.setDatiPagamentoPA(tipoDati);
		
		return risposta;
	}

	private static EsitoVerificaRPT fillEsitoVerificaRPT(EsitoOperazionePagamentoDTO esitoOpDto) {
		
		EsitoVerificaRPT esito = new EsitoVerificaRPT();
		
		EnumBusinessReturnCodes globalReturnCode = esitoOpDto.getReturnCode();
		
		EnumBusinessReturnCodes specificReturnCode = getSpecificReturnCode(esitoOpDto.getEsitiCondizioni());
		
		if (globalReturnCode.isError()){
			
			esito.setEsito("KO");
			
			if (specificReturnCode ==null || !specificReturnCode.isError())
				specificReturnCode = globalReturnCode;
			
			FaultBean fault = new FaultBean();
			
			fault.setFaultCode(EnumNDPError.getFromIrisReturnCode(specificReturnCode).toString());
			
			fault.setFaultString(EnumNDPError.getFaultStringFromIrisRetCode(specificReturnCode));
			
			//fault.setDescription(value);
			
			//fault.setSerial(value);
			
			fault.setId(esitoOpDto.getIdDominio());
			
			esito.setFault(fault);
			
		} else esito.setEsito("OK");
		
		return esito;
		
	}

	private static EnumBusinessReturnCodes getSpecificReturnCode(List<EsitoCondizioneDTO> esitiCondizioni) {
		
		for(EsitoCondizioneDTO esitoCond : esitiCondizioni){
			
			EnumBusinessReturnCodes currRetCode = esitoCond.getReturnCode();
			
			if (currRetCode != null)
				
					return currRetCode;
		}
		
		return null;
	}

}
