package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.dto.ddp.DettaglioDRPDTO;
import it.tasgroup.iris.dto.ddp.DettaglioDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumStatoDRP;
import it.tasgroup.services.util.enumeration.EnumTipoDRP;

/**
 * @author PazziK
 *
 */
public class DRPDTOBuilder {
	
	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");
    
	private static final ConfigurationPropertyLoader confNodo = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
	
	// uso locale "ENGLISH" perchï¿½ il separatore decimale deve essere il "."
	private static final DecimalFormat nFormatter = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
	
	/**
	 * @deprecated Use {@link #populateDRPSpecificDetails(EnumTipoDRP,EnumStatoDRP,Intestatari,String,Timestamp,DocumentoDiPagamentoDTO,ContoTecnico,String)} instead
	 */
	private static void populateDRPSpecificDetails(EnumTipoDRP tipo, EnumStatoDRP stato,
			Intestatari intestatario, String codTransazione, Timestamp datePagamento,
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO, ContoTecnico contoTecnico) {
				populateDRPSpecificDetails(tipo, stato, intestatario, codTransazione, datePagamento,
						documentoDiPagamentoDTO, contoTecnico, null);
			}

	public static List<DocumentoDiPagamentoDTO> populateDRPDTOList(GestioneFlussi distinta, Intestatari intestatario, Set<Pagamenti> pagamentiCarrello, ContoTecnico contoTecnico, Locale locale, TributoEnte tributoEnte) {
		
		CfgGatewayPagamento gateway = distinta.getCfgGatewayPagamento();
		EnumTipoDRP tipo = getCheckedTipoDRP(gateway);
		EnumStatoDRP stato = getCheckedStatoDRP(pagamentiCarrello);
        Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
        
    	boolean nodoPagamenti1_7 = true; //beProperties.getProperty("iris.pagamenti.nodoPagamenti1_7").equals("true");
		
		
		String fornitGateway=gateway.getCfgFornitoreGateway().getBundleKey();		
		List<DocumentoDiPagamentoDTO> returnDTOList = new ArrayList<DocumentoDiPagamentoDTO>();
		DocumentoDiPagamentoDTO documentoDiPagamentoDTO = new DocumentoDiPagamentoDTO();		
		documentoDiPagamentoDTO.setPagamentoOffline(EnumTipoDRP.BONIFICO_OFFLINE.equals(tipo)); // per adesso solo BONIFICO OFFLINE
		
		if ("NDP".equals(fornitGateway)&& nodoPagamenti1_7) {
			documentoDiPagamentoDTO.setNdp_1_7(true);
		} else {
			documentoDiPagamentoDTO.setNdp_1_7(false);
		}
		String codPagamento = distinta.getCodPagamento();
		Timestamp datePagamento = distinta.getTsInserimento();

		IndirizzoDTOBuilder.populateIndirizzoList(intestatario, documentoDiPagamentoDTO);
		
		CondizioniDocumentoDTOBuilder.populateCarrello(pagamentiCarrello, documentoDiPagamentoDTO, locale);
		
		String causaleRPT = causaleRPT(distinta, tributoEnte);
		
		populateDRPSpecificDetails(tipo, stato, intestatario, codPagamento, datePagamento, documentoDiPagamentoDTO, contoTecnico, causaleRPT);
		
		documentoDiPagamentoDTO.setCfIntestatarioPendenza(pagamentiCarrello.iterator().next().getCondPagamento().getPendenza().getDestinatari().iterator().next().getCoDestinatario());
		
//		if(EnumTipoDRP.ATM.equals(tipo) ||EnumTipoDRP.BONIFICO_OFFLINE.equals(tipo)){
//			documentoDiPagamentoDTO.setCfPagante(""); 	
//		} else {
			documentoDiPagamentoDTO.setCfPagante(distinta.getUtentecreatore()); 
//		}
		

		// TODO: fix provvisoria. E' necessario sanare il meccanismo di scrittura sulla tabella delle distinte
		if (EnumTipoDRP.ATM.equals(tipo)) {		

			documentoDiPagamentoDTO.setImporto(distinta.getTotimportipositivi());
			documentoDiPagamentoDTO.setImportoCommissioni(distinta.getImportoCommissioni());
			documentoDiPagamentoDTO.setImportoTotale(distinta.getTotimportipositivi().add(distinta.getImportoCommissioni()));
		
		} else {

			documentoDiPagamentoDTO.setImporto(distinta.getTotimportipositivi().subtract(distinta.getImportoCommissioni()));
			documentoDiPagamentoDTO.setImportoCommissioni(distinta.getImportoCommissioni());
			documentoDiPagamentoDTO.setImportoTotale(distinta.getTotimportipositivi());
	
		}
		
		documentoDiPagamentoDTO.setDataPagamento(datePagamento);
		documentoDiPagamentoDTO.setNumPagamenti(distinta.getNumeroDisposizioni());
		documentoDiPagamentoDTO.setCodiceTransazione(distinta.getCodTransazionePSP());
		documentoDiPagamentoDTO.setIuv(distinta.getIuv());
		if (!"NDP".equals(fornitGateway)){	
		    documentoDiPagamentoDTO.setNomeIstitutoAttestante(distinta.getCfgGatewayPagamento().getSystemName());
		    if ("6".equals(distinta.getCfgGatewayPagamento().getId().toString())) {
		    	//per i BFL non so quale sia l'effettivo istituto attestante, per cui si è deciso di 
		    	//impostare su DB il SubsystemId con un -
		    	documentoDiPagamentoDTO.setIdIstitutoAttestante(distinta.getCfgGatewayPagamento().getSubsystemId());
		    	documentoDiPagamentoDTO.setDescrizioneIdIstitutoAttestante("");
		    }
		    else {
		    	documentoDiPagamentoDTO.setIdIstitutoAttestante(distinta.getCfgGatewayPagamento().getSubsystemId());
		    	documentoDiPagamentoDTO.setDescrizioneIdIstitutoAttestante("C..F.");
		    }
	    } else {
	    	documentoDiPagamentoDTO.setNomeIstitutoAttestante(distinta.getDescrizioneAttestante());
		    documentoDiPagamentoDTO.setIdIstitutoAttestante(distinta.getIdentificativoAttestante());
		    if ("G".equals(distinta.getTipoIdentificativoAttestante())){
		       documentoDiPagamentoDTO.setDescrizioneIdIstitutoAttestante("C.F.");
		    } else {
		       if ("B".equals(distinta.getTipoIdentificativoAttestante())){
		    	   documentoDiPagamentoDTO.setDescrizioneIdIstitutoAttestante("BIC");
		       } else {
		    	   if ("A".equals(distinta.getTipoIdentificativoAttestante())){
			    	   documentoDiPagamentoDTO.setDescrizioneIdIstitutoAttestante("ABI");
			       } else {
			    	   documentoDiPagamentoDTO.setDescrizioneIdIstitutoAttestante("");
			       }
		       }
		    }
	    }
		//
		// setto il nome file in modo da usarlo anche su JaspeReport
		//
		String receiptCustomPrefix = conf.getProperty("configuration.receipt.prefix");
		
		String prefix = "";
		if(receiptCustomPrefix.length() > 0)
			prefix = receiptCustomPrefix + "_";
		
		String downloadFileName = prefix + CommonConstant.RICEVUTA_FILE_NAME_PREFIX + distinta.getCodPagamento();
		
		documentoDiPagamentoDTO.setNomeFile(downloadFileName);
		
		
		returnDTOList.add(documentoDiPagamentoDTO);
		
		return returnDTOList;
	}
	
	private static EnumStatoDRP getCheckedStatoDRP(Set<Pagamenti> pagamenti) {
		
		EnumStatoDRP statoPrev = null;
		String flagIncassoPrev = null;
		
		
		for(Pagamenti p : pagamenti){			
						
			GestioneFlussi flussoDistinta = (p != null? p.getFlussoDistinta() : null);
			
			EnumStatoDRP statoCurr = (flussoDistinta != null? flussoDistinta.getEnumStatoDRP() : null);
			
			String flagIncassoCurr = (p != null? p.getFlagIncasso() : null);
			
			if (statoCurr != null && statoPrev != null && statoPrev != statoCurr)
				
				throw new IllegalStateException("Tutte le condizioni di una stessa distinta devono avere lo stesso stato di pagamento");				
				
			statoPrev = statoCurr;
				
			if (flagIncassoCurr != null && flagIncassoPrev != null && !flagIncassoPrev.equals(flagIncassoCurr))
				throw new IllegalStateException("Tutte le condizioni di una stessa distinta devono avere lo stesso flag incasso");
			
			flagIncassoPrev = flagIncassoCurr;
		}
		
		if (flagIncassoPrev != null && !flagIncassoPrev.equals("0") && (EnumStatoDRP.ESEGUITO.equals(statoPrev) || EnumStatoDRP.ESEGUITO_SBF.equals(statoPrev)) )
			statoPrev = EnumStatoDRP.INCASSATO;
		
		return statoPrev;
	}

	private static EnumTipoDRP getCheckedTipoDRP(CfgGatewayPagamento  gateway) {
			
		EnumTipoDRP tipoCurr = EnumTipoDRP.getByIdCfgModalitaPagamento(gateway == null? null : gateway.getCfgModalitaPagamento().getId());
		
		// patch WISP 2.0
		if (gateway != null && "AGID".equals(gateway.getSystemName())){
			tipoCurr = EnumTipoDRP.PAGO_PA;
		}
		return tipoCurr;
	}
	
	
	private static void populateDRPSpecificDetails(EnumTipoDRP tipo, EnumStatoDRP stato, 
			Intestatari intestatario, String codTransazione, Timestamp datePagamento, 
			DocumentoDiPagamentoDTO documentoDiPagamentoDTO, ContoTecnico contoTecnico,String causaleRPT) {
		
		List<DettaglioDTO> dettaglioDTOList = new ArrayList<DettaglioDTO>();
		
		DettaglioDRPDTO dettaglioDTO = DettaglioDRPDTOFactory.createSpecificDetail(tipo, stato, intestatario, codTransazione, datePagamento, contoTecnico, causaleRPT);

		
		dettaglioDTOList.add(dettaglioDTO);
		
		documentoDiPagamentoDTO.setSpecificDetails(dettaglioDTOList);
		
	}

	
	private static String causaleRPT(GestioneFlussi distinta,TributoEnte tributo) {
		
		Pagamenti pagamenti = distinta.getPagamenti().iterator().next();
		CondizionePagamento cond = pagamenti.getCondPagamento();
		DestinatariPendenza dest =cond.getPendenza().getDestinatari().iterator().next();
		String iuv = distinta.getIuv();
		BigDecimal importo = cond.getImTotale();
		String idFiscaleDebitore = dest.getCoDestinatario();
		String causaleRPT = null;
		if (!confNodo.getBooleanProperty("nodopagamentispc.rpt.causaleCondizioniInCausaleVersamento") || cond.getCausalePagamento()==null ){
			// formato causaleVersamento: /RFB/<codice versamento
			// ente>/<importo>/TXT/DEBITORE/<codice fiscale debitore>

			
			if (idFiscaleDebitore==null || (idFiscaleDebitore.length() != 16 && idFiscaleDebitore.length() != 11)) {
				causaleRPT ="/RFB/"
						+ iuv + "/"
						+ nFormatter.format(importo)
						;

			} else {
				String causaleVers = "/RFB/"
						+ iuv + "/"
						+ nFormatter.format(importo)
						+ "/TXT/DEBITORE/" + idFiscaleDebitore;
				
				
				
				if (confNodo.getBooleanProperty("nodopagamentispc.rpt.descrizioneDebitoInCausaleVersamento")){
					causaleVers = causaleVers + "/PER/" + tributo.getDeTrb();
				}
				if (causaleVers.length()>140) {
					causaleVers=causaleVers.substring(0,137)+"...";
				}
				causaleRPT= causaleVers;
			}
		} else {
            // formato causaleVersamento /RFB/<IUV>/TXT/<descrizione >
			String descrizione = getCausalePagam("A", cond.getCausalePagamento());
			String causaleVersamentoRPT = "/RFB/"+ iuv + "/TXT/"+descrizione;
			
			if (causaleVersamentoRPT.length()>140) {
				causaleVersamentoRPT=causaleVersamentoRPT.substring(0,137)+"...";
			}
			
			//String descrizione = (elementoCarrello.getCausaleCondizione().length()> 115)?elementoCarrello.getCausaleCondizione().substring(0,114):elementoCarrello.getCausaleCondizione();
			causaleRPT= causaleVersamentoRPT;
		}
		return causaleRPT;
	}
	
	private static String getCausalePagam(String tiCausalePagamento, String causalePagamento) {
		String causaleIntera;
		String causaleFormattata = new String("");
		
		causaleIntera = causalePagamento;
		
		if (tiCausalePagamento==null || tiCausalePagamento.equals("A")) {
			String causaleTroncata = causaleIntera.length() > 115 ? causaleIntera.substring(0, 112).concat("...") : causaleIntera;
			return causaleTroncata;
		} else {
			if (tiCausalePagamento.equals("B") || tiCausalePagamento.equals("C")) {
				
				String causaleTemp = new String(causaleIntera);
				while (causaleTemp.length()>0) {
					String t = causaleTemp.substring(0, Math.min(35, causaleTemp.length()));
					causaleTemp=causaleTemp.substring(Math.min(35, causaleTemp.length()));
					if (tiCausalePagamento.equals("B")) {
						causaleFormattata = causaleFormattata +(causaleFormattata.length()>0?" ":"")+t.trim();
					 
					} else {
						// C
						String causale = t.substring(0, 25);
						String importoStr = t.substring(25).trim();
						importoStr = new BigDecimal(importoStr).toString();

						causaleFormattata = causaleFormattata +(causaleFormattata.length()>0?" ":"")+causale.trim()+ " "+importoStr;
						
					}
				}
				String causaleTroncata = causaleFormattata.length() > 115 ? causaleFormattata.substring(0, 112).concat("...") : causaleFormattata;
				return causaleTroncata;
				
			} else {
				return "";
			}
		}
		
	}
}
