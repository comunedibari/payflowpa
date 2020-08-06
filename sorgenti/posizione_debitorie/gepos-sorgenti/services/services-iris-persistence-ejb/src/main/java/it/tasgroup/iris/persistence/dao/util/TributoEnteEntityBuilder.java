/**
 * 
 */
package it.tasgroup.iris.persistence.dao.util;

import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.iris.dto.anagrafica.CfgNotificaPagamentoDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.services.util.enumeration.EnumTipoIniziativa;
import it.tasgroup.services.util.enumeration.EnumTipoPredeterminato;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class TributoEnteEntityBuilder {
	
public static TributoEnte fillTributoEntity(TributoEnteDTO dtoIn, TributoEnte tributoEnte) {
		
		tributoEnte.setCdTrbEnte(dtoIn.getCdTrbEnte());
		
		tributoEnte.setDeTrb(dtoIn.getDeTrb());
		
		tributoEnte.setIdEnte(dtoIn.getEnte().getId());
		
		tributoEnte.setFlIniziativa(dtoIn.getFlIniziativa());
		
		tributoEnte.setFlNascostoFe(dtoIn.getFlNascostoFe());
		
		tributoEnte.setFlNotificaPagamento(dtoIn.getFlNotificaPagamento());
		
		tributoEnte.setFlVerificaPagamento(dtoIn.getFlVerificaPagamento()!=null?dtoIn.getFlVerificaPagamento():"N");
		
		if (dtoIn.getFlPredeterm() == null && dtoIn.getFlIniziativa().equals(EnumTipoIniziativa.RICHIESTO.getChiave())) {
			tributoEnte.setFlPredeterm(EnumTipoPredeterminato.NON_PREDETERMINATO.getChiave()); // N
		} else {
			tributoEnte.setFlPredeterm(dtoIn.getFlPredeterm());
		}
		
		tributoEnte.setFlRicevutaAnonimo(dtoIn.getFlRicevutaAnonimo());
		
		tributoEnte.setIBAN(dtoIn.getIBAN());
		
		tributoEnte.setIBAN_CCP(dtoIn.getIBAN_CCP());
		
		tributoEnte.setIBAN_MYBANK(dtoIn.getIBAN_MYBANK());
		
		tributoEnte.setIBANContoTecnico(dtoIn.getIBANContoTecnico());
		
		tributoEnte.setIdTributo(dtoIn.getIdTributo());
		
		tributoEnte.setSIL(dtoIn.getSIL());
		
		tributoEnte.setInfoTributo(dtoIn.getInfoTributo());
		
		tributoEnte.setIstruzioniPagamento(dtoIn.getIstruzioniPagamento());
		
		if (dtoIn.getStato() !=null && dtoIn.getStato().trim().length()>0)
										tributoEnte.setStato(dtoIn.getStato());
		
		tributoEnte.setUrlInfoService(dtoIn.getUrlInfoService());
		
		tributoEnte.setUrlUpdService(dtoIn.getUrlUpdService());
		
		tributoEnte.setNdpCodStazPa(dtoIn.getNdpCodStazPa()); 
		tributoEnte.setNdpAuxDigit((dtoIn.getNdpAuxDigit()==null)?"0":dtoIn.getNdpAuxDigit());  
		tributoEnte.setNdpCodSegr(dtoIn.getNdpCodSegr());
		tributoEnte.setFlNdpIuvGenerato((dtoIn.getFlNdpIuvGenerato()==null)?"0":dtoIn.getFlNdpIuvGenerato()); 
		tributoEnte.setNdpIuvStartNum(dtoIn.getNdpIuvStartNum());
		
		
		tributoEnte.setFlBancaTesoriera(dtoIn.getFlBancaTesoriera());
		tributoEnte.setFlBlf(dtoIn.getFlBlf());
		tributoEnte.setFlNdp(dtoIn.getFlNdp());
		tributoEnte.setFlNdpModello3(dtoIn.getFlNdpModello3());
		
		tributoEnte.setUoaCompetente(dtoIn.getUoaCompetente());
		tributoEnte.setIntestazioneCCP(dtoIn.getIntestazioneCCP());
		tributoEnte.setAutorizzStampaBP(dtoIn.getAutorizzStampaBP());
		
		tributoEnte.setCfgTsrCodiceEnte(dtoIn.getCfgTsrCodiceEnte());
		tributoEnte.setCfgTsrContoEnte(dtoIn.getCfgTsrContoEnte());


		tributoEnte.setTipoGestioneRichiestaRevoca(dtoIn.getTipoGestioneRichiestaRevoca());
		
		
		if (dtoIn.isNew()) {
			
			tributoEnte.setOpInserimento(dtoIn.getOpInserimento());
			tributoEnte.setTsInserimento(new Date());
			
		} else {
			
			tributoEnte.setOpAggiornamento(dtoIn.getOpAggiornamento());
			tributoEnte.setTsAggiornamento(new Date());
		}
	
		if ("N".equals(tributoEnte.getFlNotificaPagamento())) {
			
			if (tributoEnte.getCfgNotificaPagamentos()!=null) {
				tributoEnte.getCfgNotificaPagamentos().clear();
			}
			
			return tributoEnte;
		
		} 
		
		
		//Caso di aggiunta/modifica delle cfgNotiticaPagamento
		
		List<CfgNotificaPagamento> cfgs = new ArrayList<CfgNotificaPagamento>();
		
		if (tributoEnte.getCfgNotificaPagamentos()!=null) {
			cfgs  = tributoEnte.getCfgNotificaPagamentos();
		}
			
		
			if (dtoIn.getCfgNotificaEseguito() != null) {
					
				CfgNotificaPagamentoDTO notificaEseguitoDTO = dtoIn.getCfgNotificaEseguito();
				
				CfgNotificaPagamento notificaEseguito = tributoEnte.getCfgNotificaEseguito() != null ? tributoEnte.getCfgNotificaEseguito() : new CfgNotificaPagamento();
				
				notificaEseguito.setTributoEnte(tributoEnte);
				
				notificaEseguito.setConsegnaNotifica(notificaEseguitoDTO.getConsegnaNotifica());
				
				notificaEseguito.setFormatoNotifica(notificaEseguitoDTO.getFormatoNotifica());
				
				notificaEseguito.setFreqNotifica(notificaEseguitoDTO.getFreqNotifica());
				
				notificaEseguito.setTipoNotifica(notificaEseguitoDTO.getTipoNotifica());
				
				if (dtoIn.isNew()) {
					
					notificaEseguito.setOpInserimento(dtoIn.getOpInserimento());
					notificaEseguito.setTsInserimento(new Date());
					
				} else {
					
					notificaEseguito.setOpAggiornamento(dtoIn.getOpAggiornamento());
					notificaEseguito.setTsAggiornamento(new Date());
					
					// Per gestire il caso di update tributo/ente con primo inserimento della cfg_notifica_pagamento.
					if (notificaEseguito.getOpInserimento()==null) {
						notificaEseguito.setOpInserimento(dtoIn.getOpAggiornamento());
					}
					if (notificaEseguito.getTsInserimento()==null) {
						notificaEseguito.setTsInserimento(new Date());
					}
					
					
				}
			
				cfgs.add(notificaEseguito);	
				
			}	
			
			
		
		if (dtoIn.getCfgNotificaIncassato() != null) {
			
			CfgNotificaPagamentoDTO notificaIncassatoDTO = dtoIn.getCfgNotificaIncassato();
			
			CfgNotificaPagamento notificaIncassato = tributoEnte.getCfgNotificaIncassato() != null ? tributoEnte.getCfgNotificaIncassato() : new CfgNotificaPagamento();
			
			notificaIncassato.setTributoEnte(tributoEnte);
			
			notificaIncassato.setConsegnaNotifica(notificaIncassatoDTO.getConsegnaNotifica());
			
			notificaIncassato.setFormatoNotifica(notificaIncassatoDTO.getFormatoNotifica());
			
			notificaIncassato.setFreqNotifica(notificaIncassatoDTO.getFreqNotifica());
			
			notificaIncassato.setTipoNotifica(notificaIncassatoDTO.getTipoNotifica());
			
			if (dtoIn.isNew()) {
				
				notificaIncassato.setOpInserimento(dtoIn.getOpInserimento());
				notificaIncassato.setTsInserimento(new Date());
				
			} else {
				
				notificaIncassato.setOpAggiornamento(dtoIn.getOpAggiornamento());
				notificaIncassato.setTsAggiornamento(new Date());
				
				// Per gestire il caso di update tributo/ente con primo inserimento della cfg_notifica_pagamento.
				if (notificaIncassato.getOpInserimento()==null) {
					notificaIncassato.setOpInserimento(dtoIn.getOpAggiornamento());
				}
				if (notificaIncassato.getTsInserimento()==null) {
					notificaIncassato.setTsInserimento(new Date());
				}
				
			}
			
			cfgs.add(notificaIncassato);
			
		}
		
		if (dtoIn.getCfgNotificaRegolato() != null){
			
			CfgNotificaPagamentoDTO notificaRegolatoDTO = dtoIn.getCfgNotificaRegolato();
			
			CfgNotificaPagamento notificaRegolato = tributoEnte.getCfgNotificaRegolato() != null ? tributoEnte.getCfgNotificaRegolato() : new CfgNotificaPagamento();
			
			notificaRegolato.setTributoEnte(tributoEnte);
			
			notificaRegolato.setConsegnaNotifica(notificaRegolatoDTO.getConsegnaNotifica());
			
			notificaRegolato.setFormatoNotifica(notificaRegolatoDTO.getFormatoNotifica());
			
			notificaRegolato.setFreqNotifica(notificaRegolatoDTO.getFreqNotifica());
			
			notificaRegolato.setTipoNotifica(notificaRegolatoDTO.getTipoNotifica());
			
			if (dtoIn.isNew()) {
				
				notificaRegolato.setOpInserimento(dtoIn.getOpInserimento());
				notificaRegolato.setTsInserimento(new Date());
				
			} else {
				
				notificaRegolato.setOpAggiornamento(dtoIn.getOpAggiornamento());
				notificaRegolato.setTsAggiornamento(new Date());
				
				// Per gestire il caso di update tributo/ente con primo inserimento della cfg_notifica_pagamento.
				if (notificaRegolato.getOpInserimento()==null) {
					notificaRegolato.setOpInserimento(dtoIn.getOpAggiornamento());
				}
				if (notificaRegolato.getTsInserimento()==null) {
					notificaRegolato.setTsInserimento(new Date());
				}				
			}
			
			cfgs.add(notificaRegolato);
			
		}
		
		tributoEnte.setCfgNotificaPagamentos(cfgs);
		
		return tributoEnte;
		
	}

	public static SistemaEnte fillSistemaEnteEntity(SistemaEnteDTO dtoIn) {
		
		SistemaEnte sil = new SistemaEnte();
		
		sil.setIdEnte(dtoIn.getIdEnte());
		
		sil.setDeSystem(dtoIn.getDeSystem());
		
		sil.setIdSystem(dtoIn.getIdSystem());
		
		sil.setStato(dtoIn.getStato());
		
		sil.setTrtId(dtoIn.getTrtId());
		
		sil.setTrtSystem(dtoIn.getTrtSystem());
		
		sil.setsSilEnabledAsString(dtoIn.getIsSSilEnabled());
		
		sil.setUserId(dtoIn.getUserId());
		
		sil.setAuthId(dtoIn.getAuthId());
		
		sil.setOpInserimento(dtoIn.getOpInserimento());
		
		sil.setTsInserimento(new Date());
		
		return sil;
		
	}
	
	public static TributoEnte fillFlagTributoEntity(TributoEnteDTO dtoIn, TributoEnte tributoEnte){
		tributoEnte.setFlBancaTesoriera(dtoIn.getFlBancaTesoriera());
		tributoEnte.setFlBlf(dtoIn.getFlBlf());
		tributoEnte.setFlNdp(dtoIn.getFlNdp());
		tributoEnte.setFlNdpModello3(dtoIn.getFlNdpModello3());
		return tributoEnte;
		
	}

}
