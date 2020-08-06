/**
 * 
 */
package it.tasgroup.iris.business.ejb.pagamenti.dto.builder;

import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoVO;
import it.nch.idp.posizionedebitoria.PagamentoDettaglioIncassoVO;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnViewHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.iris.domain.*;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.helper.BillInspector;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.dto.flussi.EventoNDP_DTO;
import it.tasgroup.iris.dto.flussi.ExportCSV_CST_DTO;
import it.tasgroup.iris.dto.flussi.ExportCSV_STD_DTO;
import it.tasgroup.iris.gde.GiornaleEventi;
import it.tasgroup.iris.shared.util.CausaleFormatter;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.IVocePagamento;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumPaymentState_CSV_STD;
import it.tasgroup.services.util.enumeration.EnumStatoPagamenti;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * @author pazzik
 *
 */
public class ExportDTOBuilder {
	
	private static final ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("SmartProxy.properties");
	
	private static final String compositeFieldsSeparator = props.getProperty("iris.smartproxy.upload.composite.field.separator");

	
	public static List<DistintePagamentoDTOLight> populateListPagamentoDTOLight(List<Pagamenti> listaPagamenti, Map<String, String> mapEnti, Map<String, String> mapTipiTributo) {
        // Attenzione e' una copia del
        // PagamentoDTOBuilder.populateListPagamentoDTOLight

        List<DistintePagamentoDTOLight> returnDTOList = new ArrayList<DistintePagamentoDTOLight>();

        for (Pagamenti p : listaPagamenti) {
            DistintePagamentoDTOLight dto = populatePagamentoDTOLight(p, mapEnti, mapTipiTributo, true);
            returnDTOList.add(dto);
        }

        return returnDTOList;
    }
	
	
	public static List<DistintePagamentoDTOLight> populateListPagamentoDTOLightFromCondizione(List<CondizionePagamento> condizioniPagamento, Map<String, String> mapEnti, Map<String, String> mapTipiTributo) {
		List<DistintePagamentoDTOLight> returnDTOList = new ArrayList<DistintePagamentoDTOLight>();
		
		for (CondizionePagamento condizionePagamento : condizioniPagamento) {
			final List<Pagamenti> pagamentiEseguiti = BillItemInspector.getPagamentiEseguiti(condizionePagamento);

				for (Pagamenti p : pagamentiEseguiti) {
					DistintePagamentoDTOLight dto = populatePagamentoDTOLight(p, mapEnti, mapTipiTributo, true);
					returnDTOList.add(dto);	
				}

				if (pagamentiEseguiti.isEmpty()) {
					DistintePagamentoDTOLight dto = new DistintePagamentoDTOLight();
					populateFromCondizione(dto, condizionePagamento, null);
					returnDTOList.add(dto);
				} 
			
		}
		
		return returnDTOList;
	}
	
	private static String getCreditoreFromEntiMap(Map<String, String> mapEnti, Pagamenti pagamento){
		
		String descrCreditore = null;
		
		if (mapEnti != null && !mapEnti.isEmpty() && mapEnti.get(pagamento.getIdEnte()) != null) {
            
        	descrCreditore = StringUtils.replaceEachRepeatedly(
                                    mapEnti.get(pagamento.getIdEnte()), 
                                    new String[]{"\u00E0", "\u00E1", "\u00E8", "\u00E9", "\u00EC", "\u00EF", "\u00F2", "\u00F3", "\u00F9", "\u00FA"}, 
                                    new String[]{"a'",     "a'",     "e'",     "e'",     "i'",     "i'",     "o'",     "o'",     "u'",     "u'"});
        } else 
        	descrCreditore = pagamento.getIdEnte();
		
		return descrCreditore;
		
	}
   public static DistintePagamentoDTOLight populatePagamentoDTOLight(Pagamenti pagamento,  
		   	Map<String, String> mapEnti, Map<String, String> mapTipiTributo, boolean isCausaleToFormat) {
	   DistintePagamentoDTOLight dtoL = populatePagamentoDTOLight(pagamento, mapEnti, mapTipiTributo);
	   if (isCausaleToFormat) {
		   if (dtoL.getCausalePagamentoFormattata() != null) {
			   dtoL.setCausalePagamento(dtoL.getCausalePagamentoFormattata());
		   }
	   }
	   return dtoL;
   }
	
	
	public static DistintePagamentoDTOLight populatePagamentoDTOLightFromCondizione(Pagamenti pagamento,
																	  Map<String, String> mapEnti, Map<String, String> mapTipiTributo, boolean isCausaleToFormat) {
		DistintePagamentoDTOLight dtoL = populatePagamentoDTOLight(pagamento, mapEnti, mapTipiTributo);
		if (isCausaleToFormat) {
			if (dtoL.getCausalePagamentoFormattata() != null) {
				dtoL.setCausalePagamento(dtoL.getCausalePagamentoFormattata());
			}
		}
		return dtoL;
	}
   
    public static DistintePagamentoDTOLight populatePagamentoDTOLight(Pagamenti pagamento,  Map<String, String> mapEnti, Map<String, String> mapTipiTributo) {
    	
        DistintePagamentoDTOLight dto = new DistintePagamentoDTOLight();
        
        GestioneFlussi gestioneFlussi = pagamento.getFlussoDistinta();

        //dto.setId(dto.getId());
        dto.setDataCreazione(gestioneFlussi.getTmbcreazione());
        dto.setStato(gestioneFlussi.getStato());
                
        dto.setImporto(gestioneFlussi.getTotimportipositivi());
        dto.setCodTransazione(gestioneFlussi.getCodTransazione());
        dto.setImportoCommissione(gestioneFlussi.getImportoCommissioni());
        dto.setUtenteCreatore(gestioneFlussi.getUtentecreatore());

		dto.setCodTransazionePSP(gestioneFlussi.getCodTransazionePSP());
		dto.setNomePSP(gestioneFlussi.getCfgGatewayPagamento().getSystemName());
		dto.setEmailVersante(gestioneFlussi.getEmailVersante());

        CfgModalitaPagamento modalitaPagamento = gestioneFlussi.getCfgGatewayPagamento().getCfgModalitaPagamento();
        String descrizioneModalitaPagamento = modalitaPagamento != null ? modalitaPagamento.getDescrizione() : "";
        dto.setModalitaPagamento(descrizioneModalitaPagamento);

        try {
        	
            StatiPagamentiIris st = StatiPagamentiIris.getStatiPagamentiIrisFromPaga(pagamento.getStPagamento());
            dto.setStatoPagamento(st == null ? "NP" : st.getFludMapping());
            dto.setFlagIncasso(pagamento.getFlagIncasso());
            dto.setIdQuietanza(pagamento.getId());
            dto.setOpInserimento(pagamento.getOpInserimento());
            dto.setTipoSpontaneo(pagamento.getCondPagamento().getPendenza().getTributoEnte().getFlPredeterm());
            
             
            dto.setDenomEnte(getCreditoreFromEntiMap(mapEnti, pagamento));
            
            if (mapTipiTributo != null && !mapTipiTributo.isEmpty() && mapTipiTributo.get(pagamento.getCdTrbEnte()) != null)            	
                dto.setDesTributo(mapTipiTributo.get(pagamento.getCdTrbEnte()));            
            else            	
                dto.setDesTributo(pagamento.getCdTrbEnte());
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        DestinatariPendenza destinatario  = BillInspector.getDestinatario(pagamento.getCondPagamento().getPendenza());
        
        
        dto.setIndirizzoDeb(destinatario.getIndirizzoDestinatario());
        dto.setLuogoNascitaDeb(destinatario.getLuogoNascitaDestinatario());
        if (destinatario.getDataNascitaDestinatario()!= null) 
        	dto.setDataNascitaDeb(new Timestamp(destinatario.getDataNascitaDestinatario().getTime()));
        	
        dto.setIuv(gestioneFlussi.getIuv());
        dto.setTRN(pagamento.getTRN());
        dto.setIdFlusso(pagamento.getIdentificativoFlusso());

        if (pagamento.getDataEsecuzione() != null)
        	dto.setDataEsecuzione(new Timestamp(pagamento.getDataEsecuzione().getTime()));

        if (pagamento.getDataRegolamento() != null)
        	dto.setDataRegolamento(new Timestamp(pagamento.getDataRegolamento().getTime()));
        
        if (pagamento.getTsDecorrenza() != null)
        	dto.setDataRegistrazionePagamento(new Date(pagamento.getTsDecorrenza().getTime()));

        dto.setCodPagamento(gestioneFlussi.getCodPagamento());
        dto.setIdPagamento(pagamento.getCondPagamento().getIdPagamento());
        dto.setIdPendenza(pagamento.getIdPendenzaente());
        String deCausale = pagamento.getCondPagamento().getPendenza().getDeCausale().replaceAll("\t", " ");
        dto.setCausalePagamento(deCausale);
        dto.setCausalePagamentoFormattata(CausaleFormatter.format(dto.getCausalePagamento(), pagamento.getCdTrbEnte()));
        
        String causaleDescr = getDescrCausaleByAddonManager(pagamento);
        
        dto.setCausalePagDescr(causaleDescr);
        
        dto.setNote(getFormattedNote(pagamento.getCondPagamento().getPendenza().getNote()));
        
        dto.setImPagato(pagamento.getImPagato());
        
        dto.setIdDocumentoRepository(gestioneFlussi.getIdDocumentoRepository());
        DestinatariPendenza destPend = null;
        if (pagamento.getCondPagamento() != null && pagamento.getCondPagamento().getPendenza() != null){
        	CondizionePagamento condizionePagamento = pagamento.getCondPagamento();
			populateFromCondizione(dto, condizionePagamento,pagamento);
			dto.setCanalePagamento(pagamento.getFlussoDistinta().getCfgGatewayPagamento().getCfgCanalePagamento().getDescrizione());
	        
		}
        dto.setEmailVersante(gestioneFlussi.getEmailVersante());
        dto.setCoPagante(pagamento.getCoPagante());
        dto.setCodFiscVersante(pagamento.getCoPagante());
        dto.setNotePagamento(getFormattedNote(pagamento.getNotePagamento()));
        dto.setIdentificativoFiscaleCreditore(gestioneFlussi.getIdentificativoFiscaleCreditore());
        dto.setIdRiscossionePSP(pagamento.getIdRiscossionePSP());
        dto.setAutenticazioneVersante(pagamento.getOpInserimento().equalsIgnoreCase("ANONYMOUS") ? "NO" : "SI");
        dto.setDescrIstitutoAttestante(gestioneFlussi.getDescrizioneAttestante()!=null?gestioneFlussi.getDescrizioneAttestante():"");
	    dto.setIdentificativoAttestante(gestioneFlussi.getIdentificativoAttestante()!=null?gestioneFlussi.getIdentificativoAttestante():"");
	    String tipoIdentificativoAttestante="";
	    if (gestioneFlussi.getTipoIdentificativoAttestante()!=null){
	    	if ("G".equals(gestioneFlussi.getTipoIdentificativoAttestante())) {
	    		tipoIdentificativoAttestante = "C.F.";
	    	}
	    	if ("A".equals(gestioneFlussi.getTipoIdentificativoAttestante())) {
	    		tipoIdentificativoAttestante = "ABI";
	    	}
	    	if ("B".equals(gestioneFlussi.getTipoIdentificativoAttestante())) {
	    		tipoIdentificativoAttestante = "BIC";
	    	}
	    }
	    dto.setTipoIdentificativoAttestante(tipoIdentificativoAttestante);
		CfgFornitoreGateway fornitore = gestioneFlussi.getCfgGatewayPagamento().getCfgFornitoreGateway();
		String circuito = fornitore != null ? fornitore.getDescrizione() : "";
		dto.setCircuito(circuito);

        if (pagamento.getStPagamento().equals("ES")) {
        	dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO.getChiave());
        } else {
        	dto.setStatoPagamento(EnumPaymentState_CSV_STD.DA_PAGARE.getChiave());
        }
		
        return dto;
    }
	
	public static void populateFromCondizione(DistintePagamentoDTOLight dto, CondizionePagamento condizionePagamento, Pagamenti p) {
		String sep = "-";
		DestinatariPendenza destPend;
		Pendenza pendenza = condizionePagamento.getPendenza();
		Set<DestinatariPendenza> destinatariPendenza = pendenza.getDestinatari();
		destPend = destinatariPendenza.iterator().next();
	
		dto.setDesTributo(pendenza.getTributoEnte().getDeTrb());
		dto.setDenomEnte(pendenza.getTributoEnte().getEntiobj().getDenominazione());
		dto.setIdPendenza(pendenza.getIdPendenzaente());
		dto.setIdPagamento(condizionePagamento.getIdPagamento());
		dto.setIuv(condizionePagamento.getIdPagamento());  // Questa non è correttissima... 
		dto.setCausalePagamento(pendenza.getDeCausale());
		
		
		dto.setDenominazioneDebitore(destPend.getDeDestinatario());
		dto.setCodFiscDebitore(BillInspector.getDebitori(pendenza,sep));
		dto.setAnnoRif(pendenza.getAnnoRiferimento());
		dto.setNote(getFormattedNote(pendenza.getNote()));
		TributoStrutturato tributoStrutturato = pendenza.getTributoStrutturato();
		if (tributoStrutturato != null) {
			dto.setNoteVersante(getFormattedNote(tributoStrutturato.getNoteVersante()));
			String causaleDescr=getDescrCausaleByAddonManager(pendenza);
	        dto.setCausalePagDescr(causaleDescr);
		}
		dto.setImportoDovuto(condizionePagamento.getImTotale());
		dto.setImportoDebito(pendenza.getImTotale());
		dto.setCausaleVersamento(condizionePagamento.getCausalePagamento());
		List<VocePagamento> vociPagamento = condizionePagamento.getVociPagamento();
		if (vociPagamento != null && !vociPagamento.isEmpty()) {
			String vociStr ="";
			for(VocePagamento vp : vociPagamento) {
				vociStr += vp.getDeVoce() + "=" + vp.getImVoce() + ";";
			}
			dto.setVociImporto(vociStr.endsWith(";") ? vociStr.substring(0, vociStr.length()-1) : vociStr);
			
		}
		dto.setSoluzioneDiPagamento(condizionePagamento.getTiPagamento());
		dto.setStatoPosizione(pendenza.getStPendenza());
		dto.setAnnoRif(pendenza.getAnnoRiferimento());
		dto.setDataScadenzaPagamento(new Timestamp(condizionePagamento.getDtScadenza().getTime()));
		dto.setIdMittente(pendenza.getIdMittente());
		dto.setDeMittente(pendenza.getDeMittente());
		dto.setCoRiscossore(pendenza.getCoRiscossore());
		dto.setRiferimento(pendenza.getRiferimento());
		dto.setTipoIncasso(pendenza.getTributoEnte().getFlPredeterm().equalsIgnoreCase("Y") ? "ATTESO" : "NON_ATTESO");
		
		
		// Gestione dei dati anagrafici debitore.
		if (destPend.getDataNascitaDestinatario()!=null) {
			Timestamp ts = new Timestamp(destPend.getDataNascitaDestinatario().getTime());
			dto.setDataNascitaDeb(ts);
		}
		
		dto.setLuogoNascitaDeb(destPend.getLuogoNascitaDestinatario());
		dto.setIndirizzoDeb(destPend.getLuogoNascitaDestinatario());
		dto.setIdentificativoFiscaleCreditore(pendenza.getTributoEnte().getEntiobj().getIntestatarioIForm().getLaplIForm());
		
		// Gestione del pagamento fuori Iris
		if (condizionePagamento.getStPagamento().equals("P")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO.getChiave());		
			if (condizionePagamento.getImPagamento()!=null) {
				BigDecimal importoPagato = condizionePagamento.getImPagamento();
				dto.setImPagato(importoPagato);
			}
			if (condizionePagamento.getDtPagamento()!=null) {
				dto.setDataRegistrazionePagamento(condizionePagamento.getDtPagamento());
			}			
			dto.setModalitaPagamento("PAGAMENTO FUORI IRIS");
			dto.setFlagIncasso(EnumStatoIncasso.NON_GESTITO.getChiave());
			dto.setCanalePagamento(condizionePagamento.getDeCanalepag());
			dto.setNotePagamento(getFormattedNote(condizionePagamento.getDeNotePagamento()));
		} else if (condizionePagamento.getStPagamento().equals("N")) {
				//prendo quello della condizione sse non c'è il pagamento 
				if (p != null) {
					dto.setImPagato(p.getImPagato());	
				} else {
					dto.setStatoPagamento(EnumPaymentState_CSV_STD.DA_PAGARE.getChiave());
					dto.setImPagato(BigDecimal.ZERO);	
				}
		} else if (condizionePagamento.getStPagamento().equals("X")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.NON_PAGABILE.getChiave());
			dto.setImPagato(BigDecimal.ZERO);
		} else if (condizionePagamento.getStPagamento().equals("E")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO_IRREGOLARE.getChiave());				
				if (p != null) {
					dto.setImPagato(p.getImPagato());	
				} else {	
					//prendo quello della condizione sse non c'è il pagamento  (dovrebbe essere sempre così)
					if (condizionePagamento.getImPagamento()!=null) {
						BigDecimal importoPagato = condizionePagamento.getImPagamento();
						dto.setImPagato(importoPagato);
					}
					if (condizionePagamento.getDtPagamento()!=null) {
						dto.setDataRegistrazionePagamento(condizionePagamento.getDtPagamento());
					}
				}
			dto.setCanalePagamento(condizionePagamento.getDeCanalepag());
			dto.setNotePagamento(getFormattedNote(condizionePagamento.getDeNotePagamento()));		
		}
	}
	
	public static List<ExportCSV_STD_DTO> fillEsportazioneCSVStandardDTO(EnumExportSTDFormat stdFormat, List<Pagamenti> listaPagamenti, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
	        
	        List<ExportCSV_STD_DTO> returnDTOList = new ArrayList<ExportCSV_STD_DTO>();

	        for (Pagamenti p : listaPagamenti) {
	        	
	        	ExportCSV_STD_DTO dto = fillEsportazioneCSV_STD_DTO(stdFormat, p, mapEnti, mapTipiTributo, locale);
	            
	        	returnDTOList.add(dto);
	        
	        }

	        return returnDTOList;
	    }
	
	public static List<ExportCSV_STD_DTO> fillEsportazioneCondizioniCSVStandardDTO(EnumExportSTDFormat stdFormat, List<CondizionePagamento> listaCondizioni, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
		
		List<ExportCSV_STD_DTO> returnDTOList = new ArrayList<ExportCSV_STD_DTO>();
		
		for (CondizionePagamento condizionePagamento : listaCondizioni) {

			
			final List<Pagamenti> pagamentiEseguiti = BillItemInspector.getPagamentiEseguiti(condizionePagamento);
			
			for (Pagamenti p : pagamentiEseguiti) {
				ExportCSV_STD_DTO dto = fillEsportazioneCSV_STD_DTO(stdFormat, p, mapEnti, mapTipiTributo, locale);
				returnDTOList.add(dto);
			}
			
			if (pagamentiEseguiti.isEmpty()) {
				ExportCSV_STD_DTO dto = new ExportCSV_STD_DTO();
				fillEsportazioneFromCondizioneCSV_STD_DTO(dto,stdFormat, condizionePagamento, mapEnti, mapTipiTributo, locale);
				returnDTOList.add(dto);
			}

		}
		
		return returnDTOList;
	}
    
    public static List<ExportCSV_STD_DTO> fillEsportazioneAvvisiCSVStandardDTO(EnumExportSTDFormat enumExportSTDFormat, List<AvvisoPosizioneDebitoriaVO> listaAvvisi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
        
        List<ExportCSV_STD_DTO> returnDTOList = new ArrayList<ExportCSV_STD_DTO>();

        for (AvvisoPosizioneDebitoriaVO avv : listaAvvisi) {
        	
        	for(CondizionePagamentoVO condVO : avv.getCondizioni()){
        		
        		ExportCSV_STD_DTO dto = fillEsportazioneCSV_STD_DTO(enumExportSTDFormat, avv, condVO, mapEnti, mapTipiTributo, locale);
                
            	returnDTOList.add(dto);
        	}
        
        }

        return returnDTOList;
    }
    
    public static List<ExportCSV_CST_DTO> fillEsportazioneAvvisiCSVCustomDTO(EnumExportSTDFormat enumExportSTDFormat, List<AvvisoPosizioneDebitoriaVO> listaAvvisi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
         List<ExportCSV_CST_DTO> returnDTOList = new ArrayList<ExportCSV_CST_DTO>();
        for (AvvisoPosizioneDebitoriaVO avv : listaAvvisi) {
        	for(CondizionePagamentoVO condVO : avv.getCondizioni()){
        		ExportCSV_CST_DTO dto = fillEsportazioneCSV_CST_DTO(enumExportSTDFormat, avv, condVO, mapEnti, mapTipiTributo, locale);
            	returnDTOList.add(dto);
        	}
        }
        return returnDTOList;
    }
    
	private static String getDescrCausaleByAddonManager(Pagamenti pagamento) {
		Pendenza pendenza = pagamento.getCondPagamento().getPendenza();
		return getDescrCausaleByAddonManager(pendenza);
	}


	private static String getDescrCausaleByAddonManager(Pendenza pendenza) {
		TributoEnte tributo = pendenza.getTributoEnte();
		String cdPlugin = tributo.getCfgTributoEntePlugin() != null ? tributo.getCfgTributoEntePlugin().getCdPlugin() : "";
		return getDescrCausaleByAddonManager(tributo.getIdEnte(), tributo.getCdTrbEnte(), cdPlugin, pendenza.getDeCausale());
	}

	
	private static String getDescrCausaleByAddonManager(String idEnte, String cdTrbEnte, String cdPlugin, String deCausale) {

		String causaleDescr;
		if (AddOnManagerFactory.exists(cdPlugin)) {
			AddOnManager<TributoStrutturato> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, cdPlugin); // per es CONFERIMENTO_DISCARICA
			AddOnViewHelper<TributoStrutturato> viewHelper = manager.getViewHelper();
			causaleDescr = viewHelper.getCausale(deCausale);

		} else {
			causaleDescr = deCausale;
		}
		return causaleDescr;

	}
	
	private static String getFormattedNote(String note){
		String noteFormattate= null;
		if (note != null) {
			noteFormattate=note.replaceAll("\\s", " "); 
		}
			
		return noteFormattate;
	}
	    
	public static ExportCSV_STD_DTO fillEsportazioneCSV_STD_DTO(EnumExportSTDFormat enumExportSTDFormat, Pagamenti pagamento,  Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
		
		ExportCSV_STD_DTO dto = new ExportCSV_STD_DTO();
		
		CondizionePagamento condizione = pagamento.getCondPagamento();
		
		fillEsportazioneFromCondizioneCSV_STD_DTO(dto, enumExportSTDFormat, condizione, mapEnti, mapTipiTributo, locale);

		Integer amountMultiplier = enumExportSTDFormat.getAmountMultiplier();
		
		BigDecimal imPagato = pagamento.getImPagato();
		
		dto.setNotePagamento(getFormattedNote(pagamento.getNotePagamento()));
		
		dto.setDataPagamento(pagamento.getTsDecorrenza());
		
		dto.setCanalePagamento(pagamento.getFlussoDistinta().getCfgGatewayPagamento().getCfgModalitaPagamento().getDescrizione());
		
		dto.setDataValutaAccredito(pagamento.getDataAccreditoEnte());
		
		dto.setIdDebito(pagamento.getIdPendenzaente());
		
		
		exportStatoPagamento(pagamento.getStPagamento(), dto);
		
		
		imPagato = imPagato.multiply(new BigDecimal(amountMultiplier));
		
		dto.setTotalePagato(imPagato);
		
		
		
		return dto;
	}
	
	public static void fillEsportazioneFromCondizioneCSV_STD_DTO(ExportCSV_STD_DTO dto, EnumExportSTDFormat enumExportSTDFormat, CondizionePagamento condizione,  Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
		Pendenza pendenza = condizione.getPendenza();
		
		dto.setCreditore(condizione.getEnte().getCodiceEnte());
		
		dto.setIdDebito(condizione.getPendenza().getIdPendenzaente());
		
		dto.setAnnoRiferimento(pendenza.getAnnoRiferimento());
		
		dto.setCausalePagamento(condizione.getCausalePagamento());
		
		dto.setDataFineValidita(new Timestamp(condizione.getDtFinevalidita().getTime()));
		
		if (condizione.getDtIniziovalidita() != null) {
			dto.setDataInizioValidita(new Timestamp(condizione.getDtIniziovalidita().getTime()));
		}
		
		
		String debitori = null;
		
		//if(enumExportSTDFormat.equals(EnumExportSTDFormat.CSV_BASIC_1))
		debitori = BillInspector.getDebitori(pendenza, compositeFieldsSeparator);
		//else 
		//debitori = BillInspector.getDebitoriRich(pendenza,compositeFieldsSeparator);
		
		dto.setDebitori(debitori);
		
		dto.setNoteDebito(getFormattedNote(condizione.getPendenza().getNote()));
		
		String ibanBeneficiario = condizione.getIbanBeneficiario();
		
		String beneficiario = condizione.getRagioneSocaleBeneficiario();
		
		dto.setIbanRiaccredito(buildIbanRiaccredito(beneficiario, ibanBeneficiario));
		
		dto.setDataScadenza(new Timestamp(condizione.getDtScadenza().getTime()));
		
		dto.setIdPagamento(condizione.getIdPagamento());
		
		//String causaleDescr = getDescrCausaleByAddonManager(pagamento);
		String deCausale = condizione.getPendenza().getDeCausale().replaceAll("[;]", compositeFieldsSeparator);
		deCausale = deCausale.replaceAll("\t", " ");
		dto.setCausaleDebito(deCausale);
		
		
		Integer amountMultiplier = enumExportSTDFormat.getAmountMultiplier();
		String amountPattern = enumExportSTDFormat.getAmountPattern();
		dto.setVociPagamento(concatVociPagamento(BillItemInspector.getVoci(condizione), compositeFieldsSeparator, amountPattern, amountMultiplier, locale));
		
		BigDecimal importoTotale = condizione.getImTotale();
		importoTotale = importoTotale.multiply(new BigDecimal(amountMultiplier));
		dto.setImportoPagamento(importoTotale);
		
		dto.setTipoDebito(pendenza.getTributoEnte().getCdTrbEnte());
		
		// Gestione del pagamento fuori Iris
		if (condizione.getStPagamento().equals("P")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO.getChiave());		
			if (condizione.getImPagamento()!=null) {
				BigDecimal importoPagato = condizione.getImPagamento();
				importoPagato = importoPagato.multiply(new BigDecimal(amountMultiplier));
				dto.setTotalePagato(importoPagato);
			}
			if (condizione.getDtPagamento()!=null) {
				dto.setDataPagamento(condizione.getDtPagamento());
			}
			dto.setPagatoIris("No");
			dto.setCanalePagamento(condizione.getDeCanalepag());
			dto.setNotePagamento(getFormattedNote(condizione.getDeNotePagamento()));
		} else if (condizione.getStPagamento().equals("N")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.DA_PAGARE.getChiave());
			dto.setTotalePagato(BigDecimal.ZERO);
		} else if (condizione.getStPagamento().equals("X")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.NON_PAGABILE.getChiave());
			dto.setTotalePagato(BigDecimal.ZERO);
		} else if (condizione.getStPagamento().equals("E")) {
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO_IRREGOLARE.getChiave());
			if (condizione.getImPagamento()!=null) {
				BigDecimal importoPagato = condizione.getImPagamento();
				importoPagato = importoPagato.multiply(new BigDecimal(amountMultiplier));
				dto.setTotalePagato(importoPagato);
			}
			if (condizione.getDtPagamento()!=null) {
				dto.setDataPagamento(condizione.getDtPagamento());
			}
			dto.setPagatoIris("No");
			dto.setCanalePagamento(condizione.getDeCanalepag());
			dto.setNotePagamento(getFormattedNote(condizione.getDeNotePagamento()));					
		}
		
		
	}
	
	private static String concatVociPagamento(List<IVocePagamento> vociPagamento,
			String separator, String amountPattern,
			Integer amountMultiplier, Locale locale) {
		
		StringBuffer buf = new StringBuffer();
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		
		DecimalFormat formatter = new DecimalFormat(amountPattern, symbols);
		
		if (vociPagamento != null){
			
			int counter = 1;
			
			for (IVocePagamento voce : vociPagamento) {
				
				BigDecimal amount = voce.getImVoce().multiply(new BigDecimal(amountMultiplier));
				
				buf.append(voce.getDeVoce()+"="+formatter.format(amount));	
				
				if (counter != vociPagamento.size())
					buf.append(separator);
				
				counter++;
			}
		}
		
		return buf.toString();
	}

	public static ExportCSV_CST_DTO fillEsportazioneCSV_CST_DTO(EnumExportSTDFormat enumExportSTDFormat, AvvisoPosizioneDebitoriaVO avv, CondizionePagamentoVO condVO, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
		ExportCSV_CST_DTO dto = new ExportCSV_CST_DTO();
		dto.setIdPendenza(avv.getIdPendenza());
		dto.setCoDestinatario(avv.getCoDestinatario());
		dto.setCodEnte(avv.getCodEnte());                   // getCodiceEnte
		dto.setDataEmissione(avv.getDataEmissione());
		dto.setDataScadenza(condVO.getDataScandenza());
		dto.setDataFineValidita(condVO.getDataFineValidita());
		dto.setEnte(avv.getEnte());                       // getDenominazione
		dto.setTributo(avv.getTributo());                    // getDeTrb
		dto.setTipoTributo(avv.getTipoTributo());
		dto.setIdTributo(avv.getIdTributo());
		dto.setCod_tributo(avv.getCod_tributo());                // getCdTrbEnte
		dto.setIdEnte(avv.getIdEnte());
		dto.setCdPlugin(avv.getCdPlugin());
		dto.setTributo_strutturato(avv.getTributo_strutturato());
		dto.setCausale(avv.getCausale());
		dto.setCodiceFiscale(avv.getCodiceFiscale());
		dto.setModalita(avv.getModalita());
		if (condVO.getPagamento() != null)
			dto.setImportoPagato(condVO.getPagamento().getImporto());
		else
			dto.setImportoPagato(BigDecimal.ZERO);
		dto.setImportoRiscosso(avv.getImportoRiscosso());
		dto.setStato(avv.getStato());
		dto.setCodicePendenza(avv.getCodicePendenza());
		dto.setImportoSbf(avv.getImportoSbf());
		dto.setImportoNonPagabile(avv.getImportoNonPagabile());
		dto.setCondizioniPagabili(avv.getCondizioniPagabili());
		dto.setCondizioniNonPagabili(avv.getCondizioniNonPagabili());
		dto.setAnnoRif(avv.getAnnoRif());
		dto.setNote(getFormattedNote(avv.getNote()));
		dto.setUrlUpdateService(avv.getUrlUpdateService());
		dto.setDescrizioneMittente(avv.getDescrizioneMittente());
		dto.setRiscossore(avv.getRiscossore());
		dto.setRiferimento(avv.getRiferimento());
		dto.setDataCreazione(avv.getDataCreazione());
		dto.setIdPendenzaEnte(avv.getIdPendenzaEnte());
		
		dto.setImporto(condVO.getImportoTotale());
		dto.setIuv(condVO.getIdPagamento());
		
		
	
		

		return dto;
	}
	

	public static ExportCSV_STD_DTO fillEsportazioneCSV_STD_DTO(EnumExportSTDFormat enumExportSTDFormat, AvvisoPosizioneDebitoriaVO avv, CondizionePagamentoVO condVO, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, Locale locale) {
    	
    	ExportCSV_STD_DTO dto = new ExportCSV_STD_DTO();
        	        
        dto.setCreditore(avv.getCodEnte());
           
        dto.setAnnoRiferimento(avv.getAnnoRif());
        
     // canalePagamento??!?!?// canalePagamento??!?!?dto.setCanalePagamento(condVO.getDescrizioneCanaleDiPagamento());
        
        //String causaleDescr = getDescrCausaleByAddonManager(pagamento);
        
        dto.setCausaleDebito(avv.getCausale());
        
        dto.setCausalePagamento(condVO.getCausale());
        
        dto.setDataFineValidita(new Timestamp(condVO.getDataFineValidita().getTime()));
        
        dto.setDataInizioValidita(new Timestamp(condVO.getDataInizioValidita().getTime()));
        
        if (condVO.getDataPagamento() != null)
        	dto.setDataPagamento(new Timestamp(condVO.getDataPagamento().getTime()));
        
		dto.setDataScadenza(new Timestamp(avv.getDataScadenza().getTime()));
						
		dto.setDebitori(avv.getCoDestinatario());
		
		String ibanBeneficiario = condVO.getIbanBeneficiario();
		
		String beneficiario = condVO.getRagSocBeneficiario();
					
		String	ibanRiaccredito = buildIbanRiaccredito(beneficiario , ibanBeneficiario);
			
		dto.setIbanRiaccredito(ibanRiaccredito);
		
		dto.setIdPagamento(condVO.getIdPagamento());
		
		dto.setIdDebito(condVO.getIdPendenza());

		dto.setTipoDebito(avv.getCod_tributo());
				
		BigDecimal importoTotale = condVO.getImportoTotale();
		
		Integer amountMultiplier = enumExportSTDFormat.getAmountMultiplier();
				
		importoTotale = importoTotale.multiply(new BigDecimal(amountMultiplier));
		
		dto.setImportoPagamento(importoTotale);
		
		String amountPattern = enumExportSTDFormat.getAmountPattern();
		
		dto.setVociPagamento(concatVociPagamento(condVO.getVociPagamento(), compositeFieldsSeparator, amountPattern, amountMultiplier, locale));
		
		dto.setNoteDebito(getFormattedNote(avv.getNote())) ;
		
		PagamentoPosizioneDebitoriaVO pagam = condVO.getPagamento();
		 
		
		if (pagam != null){
			
			/**
			 * Posizione pagata da PAYTAS
			 */
			
			dto.setDataValutaAccredito(pagam.getDataValutaAccredito());
			
			dto.setNotePagamento(getFormattedNote(pagam.getNote()));
			
			exportStatoPagamento(pagam.getStato(), dto);
			
			BigDecimal importoPagato = pagam.getImporto();
			
			if (importoPagato.doubleValue() > 0) {	
				
				importoPagato = importoPagato.multiply(new BigDecimal(amountMultiplier));
				
				dto.setTotalePagato(importoPagato);
			}
			
			PagamentoDettaglioIncassoVO dettaglioIncasso = pagam.getDettaglioIncasso();
			dto.setIstitutoAttestante(dettaglioIncasso.getIstitutoAttestante());
			
			if (StringUtils.isEmpty(dto.getIstitutoAttestante())) {
				dto.setIstitutoAttestante(dettaglioIncasso.getDescrizionePSP());
			}
			
			dto.setIdAttestante(dettaglioIncasso.getIdAttestante());
			
			dto.setTipoIdAttestante(dettaglioIncasso.getTipoIdAttestante());
			
			
		} else {
						
			/**
			 * Posizione pagata da fuori PAYTAS
			 */
			
			if (condVO.getImportoPagamento() != null && condVO.getImportoPagamento().doubleValue() > 0)
				dto.setTotalePagato(condVO.getImportoPagamento().multiply(new BigDecimal(amountMultiplier)));

			dto.setNotePagamento(getFormattedNote(condVO.getNotePagamento()));

			dto.setPagatoIris("No");

			if ("P".equals(condVO.getStatoPagamento()))
				
				dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO.getChiave());
				
			else if ("E".equals(condVO.getStatoPagamento())) 
				dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO_IRREGOLARE.getChiave());
			
			else if ("X".equals(condVO.getStatoPagamento()))
				dto.setStatoPagamento(EnumPaymentState_CSV_STD.NON_PAGABILE.getChiave());	
					
			else		
				dto.setStatoPagamento(EnumPaymentState_CSV_STD.DA_PAGARE.getChiave());
								
		}
		
        return dto;
    }
	
	private static String buildIbanRiaccredito(String beneficiario, String ibanBeneficiario){
		
		String ibanRiaccredito = null;
		
		if (!StringUtils.isEmpty(beneficiario) && !StringUtils.isEmpty(ibanBeneficiario))
			
			ibanRiaccredito = ibanBeneficiario+ compositeFieldsSeparator + beneficiario;
		
		return ibanRiaccredito;
		
	}
	
	private static void exportStatoPagamento(String stPagamento, ExportCSV_STD_DTO dto){
		
		if (stPagamento.equals("ES") || stPagamento.equals("EF")){
			
			dto.setPagatoIris("Si");
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.PAGATO.getChiave());
			
		} else {
			
			dto.setPagatoIris("No");
			dto.setStatoPagamento(EnumPaymentState_CSV_STD.DA_PAGARE.getChiave());
		}
		
	}

	public static List<EventoNDP_DTO> populateListEventoNDP_DTO(List<GiornaleEventi> listaEventi) {
			
		List<EventoNDP_DTO> listaEventiDTO = new ArrayList<EventoNDP_DTO>();
		
		for(GiornaleEventi evento : listaEventi) {
			
			EventoNDP_DTO eventoDTO = populateEventoNDP_DTO(evento);
	        
	        listaEventiDTO.add(eventoDTO);
			
		}
		
		return listaEventiDTO;
	}

	public static EventoNDP_DTO populateEventoNDP_DTO(GiornaleEventi evento) {
		
		EventoNDP_DTO eventoDTO = new EventoNDP_DTO();
        
        eventoDTO.setIdEvento(evento.getId().toString());
        
        eventoDTO.setIdDominio(evento.getIdDominio());
        
        eventoDTO.setIuv(evento.getIdUnivocoVersamento());
        
        eventoDTO.setData(evento.getDataOraEvento());
        
        eventoDTO.setCodContesto(evento.getCodiceContestoPagamento());
        
        eventoDTO.setGde(true);
        
        eventoDTO.setTipo(evento.getTipoEvento());
        
        eventoDTO.setSottoTipo(evento.getSottoTipoEvento().name());
        
        eventoDTO.setIdPSP(evento.getIdPSP());
        
        eventoDTO.setCanalePagamento(evento.getCanalePagamento());
        
        eventoDTO.setCategoria(evento.getCategoriaEvento().name());
        
//        eventoDTO.setFaultID("faultID");
//        
//        eventoDTO.setFaultCode("faultCode");
//        
//        eventoDTO.setFaultSerial("faultSerial");
//        
//        eventoDTO.setFaultString("faultString");
//        
//        eventoDTO.setFaultDescr("faultDescr");
        
        eventoDTO.setTipoVersamento(evento.getTipoVersamento().name());
        
        eventoDTO.setInterfaccia(evento.getParametriSpecificiInterfaccia());
        
        eventoDTO.setIntermediarioPA(evento.getIdStazioneIntermediarioPA());
        
        eventoDTO.setEsito(evento.getEsito());
        
        eventoDTO.setIdErogatore(evento.getIdErogatore());
        
        eventoDTO.setIdFruitore(evento.getIdFruitore());
        
        eventoDTO.setComponente(evento.getComponente().name());
		
		return eventoDTO;
	}

}
