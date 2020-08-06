/**
 * 
 */
package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import java.sql.Date;
import java.text.DecimalFormat;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.tasgroup.avvisi.NumeroAvvisoUtils;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.ddp.BollettinoFrecciaDTO;
import it.tasgroup.iris.dto.ddp.DettaglioATMDTO;
import it.tasgroup.iris.dto.ddp.DettaglioBonificoDTO;
import it.tasgroup.iris.dto.ddp.DettaglioDDPDTO;
import it.tasgroup.iris.dto.ddp.DettaglioGDODTO;
import it.tasgroup.iris.dto.ddp.DettaglioNDPDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;

/**
 * @author PazziK
 *
 */
public class DettaglioDDPDTOFactory {
	
	public static DettaglioDDPDTO createSpecificDetail(Intestatari intestatarioDDP,
			 ContoTecnico contoTecnico, DocumentoDiPagamento ddp) {
		
		DettaglioDDPDTO dettaglioDTO = null;
		
		switch (ddp.getTipoDocumentoEnum()) {

		case BONIFICO:
			dettaglioDTO = createBonificoSpecificDetail(intestatarioDDP, contoTecnico, ddp);
			break;

		case ATM:
			dettaglioDTO = createATMSpecificDetail(ddp);
			break;

		case GDO:
			dettaglioDTO = createGDOSpecificDetail(ddp);
			break;
		case FRECCIA:
			dettaglioDTO = createFRECCIASpecificDetail(intestatarioDDP, contoTecnico, ddp);
			break;

		case NDP:
			dettaglioDTO = createNDPSpecificDetail(ddp); // TODO: manca implementazione per NDP
			break;
		}
		
		dettaglioDTO.setDataPagamento(new Date(ddp.getTsEmissione().getTime()));
		dettaglioDTO.setDtScadenzaDoc(ddp.getDtScadenzaDoc());
		return dettaglioDTO;
	}	
	
	private static DettaglioDDPDTO createFRECCIASpecificDetail( Intestatari intestatarioDDP, ContoTecnico contoTecnico, DocumentoDiPagamento ddp) {
		
		BollettinoFrecciaDTO dettaglioDTO = new BollettinoFrecciaDTO();
		//IProfileManager profile = (IProfileManager) profileManager;
		//Intestatarioperatori intestOper = (Intestatarioperatori) profile.getFec().getIntestatarioperatoriCorrente().getPojo();
		//IndirizzipostaliCommon indirizzoPostale =  intestOper.getIntestatario().getIndirizzipostaliobj();
		IndirizzipostaliCommon indirizzoPostale = intestatarioDDP.getIndirizzipostaliobj();
			// TODO PAZZIK CORREGGERE	
				//intestatarioDDP!=null? intestatarioDDP.getIndirizzipostaliobj() : ((Intestatarioperatori)profile.getFec().getIntestatarioperatoriCorrente().getPojo()).getIntestatario().getIndirizzipostaliobj();
				

		dettaglioDTO.setCap(indirizzoPostale.getCapCodeIForm());
		dettaglioDTO.setCitta(indirizzoPostale.getCityIForm());
		dettaglioDTO.setCsia(contoTecnico.getIntestatario().getSia());
		dettaglioDTO.setNomeOrdinante(intestatarioDDP.getRagionesociale());
		dettaglioDTO.setStato(ddp.getStatoEnum());
		dettaglioDTO.setCoordinateBancarie(contoTecnico.getIban());
		
		dettaglioDTO.setInd(indirizzoPostale != null ? indirizzoPostale.getAddressIForm() : "");

		dettaglioDTO.setId(ddp.getId());
		dettaglioDTO.setProv(indirizzoPostale.getProvinceIForm());
		
		dettaglioDTO.setIdDocRepository(ddp.getIdDocumentoRepository());
		
		String mail = null; //indirizzoPostale.getEmailIForm();
		
		String mailExolabFormat = mail != null ? mail : "";	
		
		dettaglioDTO.setMail(mailExolabFormat);
		
		return dettaglioDTO;
	}

	private static DettaglioDDPDTO createBonificoSpecificDetail(Intestatari intestatarioDDP,
			ContoTecnico contoTecnico, DocumentoDiPagamento ddp) {
		
		DettaglioBonificoDTO dettaglioDTO = new DettaglioBonificoDTO();
		
		if (contoTecnico != null) {
			
			dettaglioDTO.setBeneficiario(contoTecnico.getIntestatario().getRagionesociale());
			dettaglioDTO.setCoordinateBancarie(contoTecnico.getIban());			
		}
		
		//if(cognome == null) TODO: da decommentare quando si avra' il cognome.
		dettaglioDTO.setCognomeOrdinante("");
		
		dettaglioDTO.setNomeOrdinante(intestatarioDDP.getRagionesociale());
		
		dettaglioDTO.setStato(getCheckedStatoDDP(ddp));
		
		return dettaglioDTO;
	}
	
	public static DettaglioDDPDTO createGDOSpecificDetail(DocumentoDiPagamento ddp) {
		DettaglioDDPDTO dettGDO = new DettaglioGDODTO();
		dettGDO.setStato(getCheckedStatoDDP(ddp));
		return dettGDO;
	}

	public static DettaglioDDPDTO createATMSpecificDetail(DocumentoDiPagamento ddp) {
		DettaglioDDPDTO dettATM = new DettaglioATMDTO();
		dettATM.setStato(getCheckedStatoDDP(ddp));
		return dettATM;
	}

	public static DettaglioDDPDTO createNDPSpecificDetail(DocumentoDiPagamento ddp) {
		
		DettaglioNDPDTO dettNDP = new DettaglioNDPDTO();
        
		CondizioneDocumento condizione = ddp.getCondizioni().iterator().next();

		AnagraficaEntiFacade dpBean;
		try {
			dpBean = (AnagraficaEntiFacade) ServiceLocator.getSLSBProxy("anagraficaEntiFacadeBean");
		} catch (ServiceLocatorException e) {
			throw new RuntimeException(e);
		}		
		TributoEnteDTO tributo = dpBean.getTributoEnteByKey(condizione.getCondizionePagamento().getEnte().getIdEnte(), condizione.getCondizionePagamento().getCdTrbEnte());
		String auxDigit = tributo.getNdpAuxDigit();
		String codStazPa = tributo.getNdpCodStazPa();
		String ibanCCP = tributo.getIBAN_CCP();
		String infoTributo = tributo.getInfoTributo();
		String uoaCompetente = tributo.getUoaCompetente();
		String intestazioneCCP = tributo.getIntestazioneCCP();
		String deTrb = tributo.getDeTrb();
		
        dettNDP.setIdPagamento(condizione.getCondizionePagamento().getIdPagamento());
        
        dettNDP.setStato(getCheckedStatoDDP(ddp));
        
        dettNDP.setDescrizionePagamento(ddp.getCfgGatewayPagamento().getDescrizione());
        
        dettNDP.setXmlQRcode(getQRcodeXml(condizione, auxDigit, codStazPa));
        
        dettNDP.setBarCode(getBarCode(condizione, false, auxDigit, codStazPa));
        
        dettNDP.setFormattedBarCode(getBarCode(condizione, true, auxDigit, codStazPa));
        
        dettNDP.setIbanCCP(ibanCCP);
        
        Enti ente = condizione.getCondizionePagamento().getEnte();
        
        dettNDP.setCfCreditore(ente.getIntestatarioobj().getLaplIForm());
        
        dettNDP.setGLN(ente.getGln());
        
        dettNDP.setIndirizzo(populateIndirizzoDTO(ente));
        
		dettNDP.setAuxDigit(auxDigit);
		
		dettNDP.setApplicationCode(codStazPa);
		
		dettNDP.setInfoTributo(infoTributo);
		
		dettNDP.setUoaCompetente(uoaCompetente);
		
		dettNDP.setAutorizzStampaBP(tributo.getAutorizzStampaBP());
		dettNDP.setIntestazioneCCP(intestazioneCCP);
		
		dettNDP.setDeTrb(deTrb);

		return dettNDP;
	}
        
        public static IndirizzoDTO populateIndirizzoDTO(Enti entiobj) {
        	
		IndirizzoDTO returnAddress = new IndirizzoDTO();
                
        IntestatariCommon intestatario = entiobj.getIntestatarioobj();
		IndirizzipostaliCommon indirizzoEntity = intestatario.getIndirizzipostaliobjIForm();
                
		returnAddress.setRagioneSociale(safePrintString(intestatario.getRagionesocialeIForm()));
		returnAddress.setProvincia(safePrintString(indirizzoEntity.getProvinceIForm()));
		returnAddress.setCap(safePrintString(indirizzoEntity.getCapCodeIForm()));
		returnAddress.setCitta(safePrintString(indirizzoEntity.getCityIForm()));
		returnAddress.setNumeroCivico(safePrintString(indirizzoEntity.getNumeroCivicoIForm()));
		returnAddress.setVia(safePrintString(indirizzoEntity.getAddressIForm()));
		returnAddress.setPiva(safePrintString(indirizzoEntity.getVatCodeIForm()));
                
		return returnAddress;
	}
        
    private static String safePrintString(String s){
    	
        return s!= null && !s.isEmpty() ? s : ""; 
        
    }
	
	public static EnumStatoDDP getCheckedStatoDDP(DocumentoDiPagamento ddp) {
		
		EnumStatoDDP statoDoc = ddp.getStatoEnum();
		
		String flagIncassoPrev = null;
		
		String flagIncassoCurr = null;
		
		for(CondizioneDocumento condizioneDoc : ddp.getCondizioni()){	
			
			CondizionePagamento condizione = condizioneDoc.getCondizionePagamento();
			
			Pagamenti uniqueValidPayment =  BillItemInspector.getUniqueValidPayment(condizione);
			
			if (uniqueValidPayment != null)
				flagIncassoCurr = uniqueValidPayment.getFlagIncasso();
				
			if (flagIncassoPrev != null && !flagIncassoPrev.equals(flagIncassoCurr))
				throw new IllegalStateException("Tutte le condizioni di una stessa distinta devono avere lo stesso flag incasso "+ ddp);
			
			if (flagIncassoCurr!= null && !flagIncassoCurr.equals("0")  && (!statoDoc.equals(EnumStatoDDP.PAGATO)))
				if(uniqueValidPayment.getFlussoDistinta().getCodTransazione().equals(ddp.getId()))
					throw new IllegalStateException("Il flag incasso puo' essere diverso da 0 solo in caso di DDP in stato PAGATO associato al pagamento" + ddp);
			
			flagIncassoPrev = flagIncassoCurr;
		}
		
		if (flagIncassoPrev != null && !flagIncassoPrev.equals("0") && (statoDoc.equals(EnumStatoDDP.PAGATO)))
			statoDoc = EnumStatoDDP.INCASSATO;
		
		return statoDoc;
	}

	private static String getQRcodeXml(CondizioneDocumento condizione, String auxDigit, String codStazPa) {
        
		String iuv = condizione.getCondizionePagamento().getIdPagamento();
		String numeroAvviso = NumeroAvvisoUtils.calculateNumeroAvviso(auxDigit, codStazPa, iuv, false);
		
        DecimalFormat df = new DecimalFormat("0000000000");
	    String importoTotale = df.format(condizione.getCondizionePagamento().getImTotale().movePointRight(2));
        
        String codiceIdentificativoEnte = condizione.getCondizionePagamento().getEnte() != null && condizione.getCondizionePagamento().getEnte().getIntestatarioobj() != null ?
                condizione.getCondizionePagamento().getEnte().getIntestatarioobj().getLaplIForm() : "";
        
//        String ret ="<informazioniVersamento>"
//                +       "<codiceIdentificativoEnte>"+codiceIdentificativoEnte+"</codiceIdentificativoEnte>"
//                +        "<numeroAvviso>"
//                +          "<auxDigit>"+auxDigit+"</auxDigit>"
//                +           "<applicationCode>"+applicationCode+"</applicationCode>"
//                +           "<IUV>"+IUV+"</IUV>"
//                +       "</numeroAvviso>"
//                +       "<importoVersamento>"+importoTotale+"</importoVersamento>"
//                + "</informazioniVersamento>";

        
		String ret = "PAGOPA|002|" + numeroAvviso + "|" + codiceIdentificativoEnte + "|" + importoTotale;
        return ret;
    }

    private static String getBarCode(CondizioneDocumento condizione, boolean isFormatted, String auxDigit, String codStazPa) {
    	
    	String iuv = condizione.getCondizionePagamento().getIdPagamento();
    	String numeroAvviso = NumeroAvvisoUtils.calculateNumeroAvviso(auxDigit, codStazPa, iuv, false);

        DecimalFormat df = new DecimalFormat("0000000000");
	    String importoTotale = df.format(condizione.getCondizionePagamento().getImTotale().movePointRight(2));
        
        Enti ente = condizione.getCondizionePagamento().getEnte();

        if (isFormatted) {
        	return "(415)" + ente.getGln() + "(8020)" + numeroAvviso + "(3902)" + importoTotale;
        } else {
        	return "415" + ente.getGln() + "8020" + numeroAvviso + "3902" + importoTotale;
        }
    }
    
}
