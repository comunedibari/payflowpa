package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;


import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaVO;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.domain.CfgStrumentoPagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.dto.flussi.PagamentoDTOLightForReceipt;
import it.tasgroup.iris.dto.flussi.PrenotazioniDTO;
import it.tasgroup.iris.dto.flussi.RevocaPagamentoDTO;
import it.tasgroup.services.util.enumeration.EnumStatoRevoca;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DistintePagamentoDTOBuilder {
    
	
	/**
	 * 
	 * @param listagf
	 * @return
	 */
	public static List<DistintePagamentoDTO> populateListDistintePagamentoDTO(List<GestioneFlussi> listagf) {
		List<DistintePagamentoDTO> dtos = new ArrayList<DistintePagamentoDTO>();
		
		for (GestioneFlussi gf : listagf) {
			DistintePagamentoDTO dto = populateDistintePagamentoDTO(gf);
			dtos.add(dto);
		}
		
		return dtos;
	}

	/**
	 * 
	 * @param gestioneFlussi
	 * @return
	 */
	public static DistintePagamentoDTO populateDistintePagamentoDTO(GestioneFlussi gestioneFlussi) {
		DistintePagamentoDTO dto = new DistintePagamentoDTO();

//		dto.setId(gestioneFlussi.getId());
		dto.setCodPagamento(gestioneFlussi.getCodPagamento());
		dto.setCodTransazione(gestioneFlussi.getCodTransazione());
		dto.setDataCreazione(gestioneFlussi.getTmbcreazione());
		dto.setStato(gestioneFlussi.getStato());
		dto.setImporto(gestioneFlussi.getTotimportipositivi());
		dto.setImportoCommissione(gestioneFlussi.getImportoCommissioni());
		dto.setModalitaPagamento(gestioneFlussi.getCfgGatewayPagamento().getCfgModalitaPagamento().getDescrizione());
		
                dto.setCodTransazionePSP(gestioneFlussi.getCodTransazionePSP());
                dto.setUtentecreatore(gestioneFlussi.getUtentecreatore());
                dto.setEmailVersante(gestioneFlussi.getEmailVersante());
                dto.setOpInserimento(gestioneFlussi.getOpInserimento());
		
		return dto;
	}
	
	public static DistintePagamentoDTOLight populateDistintePagamentoDTOLight(GestioneFlussi gestioneFlussi){
	    return populateDistintePagamentoDTOLight(gestioneFlussi, null, null);
	}
	
	/**
	 * 
	 * @param gestioneFlussi
	 * @return
	 */
	public static DistintePagamentoDTOLight populateDistintePagamentoDTOLight(GestioneFlussi gestioneFlussi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo) {
		//Attenzione ce n'� una copia in DistintePagamentoBusinessBean
		DistintePagamentoDTOLight dto = new DistintePagamentoDTOLight();
		
		dto.setId(new Integer(gestioneFlussi.getId().intValue())); 
		dto.setDataCreazione(gestioneFlussi.getTmbcreazione());
		dto.setStato(gestioneFlussi.getStato());
		dto.setImporto(gestioneFlussi.getTotimportipositivi());
		dto.setCodTransazione(gestioneFlussi.getCodTransazione());
		dto.setIuv(gestioneFlussi.getIuv());
		dto.setCodTransazionePSP(gestioneFlussi.getCodTransazionePSP());
		dto.setIdentificativoFiscaleCreditore(gestioneFlussi.getIdentificativoFiscaleCreditore());
		dto.setImportoCommissione(gestioneFlussi.getImportoCommissioni());
		dto.setUtenteCreatore(gestioneFlussi.getUtentecreatore());
		
		CfgModalitaPagamento modalitaPagamento = gestioneFlussi.getCfgGatewayPagamento().getCfgModalitaPagamento();
		String descrizioneModalitaPagamento = modalitaPagamento!=null?modalitaPagamento.getDescrizione():"";
		dto.setModalitaPagamento(descrizioneModalitaPagamento);
		
		CfgFornitoreGateway fornitorePagamento = gestioneFlussi.getCfgGatewayPagamento().getCfgFornitoreGateway();
		String bundleKeyFornitorePagamento = fornitorePagamento != null ? fornitorePagamento.getBundleKey() : "";
		dto.setFornitorePagamento(bundleKeyFornitorePagamento);
		
		CfgFornitoreGateway fornitore = gestioneFlussi.getCfgGatewayPagamento().getCfgFornitoreGateway();
		String circuito = fornitore != null ? fornitore.getDescrizione() : "";
		dto.setCircuito(circuito);

		Pagamenti pag = null;
		
		// TODO controllare INSERITO CONTROLLO PER IRROBUSTIRE CAUSA ESPLOSIONI PER MANCANZA PAGAMENTI....?????
		if (gestioneFlussi.getPagamenti().iterator().hasNext()) {
			pag = gestioneFlussi.getPagamenti().iterator().next();
		    try{
			   StatiPagamentiIris st = StatiPagamentiIris.getStatiPagamentiIrisFromPaga(pag.getStPagamento());
			   dto.setStatoPagamento(st == null ? "NP" : st.getFludMapping());
			   dto.setNumeroRevoche(pag.getNumeroRevoche());
			   dto.setNumeroRevocheDaValutare(pag.getNumeroRevocheDaValutare());
			   	  
//			   dto.setDaRevocare(st != null 
//					   && st.equals(StatiPagamentiPaytas.ESEGUITO)
//					   && pag.getNumeroRevocheDaValutare() != null
//					   && pag.getNumeroRevocheDaValutare() > 0);
			   
			   dto.setFlagIncasso(pag.getFlagIncasso());
			   if (pag.getRevochePagamento() != null) {
				   for(RevochePagamento revoca : pag.getRevochePagamento()) {
					   dto.addRevocaPagamentoDTO(RevocaPagamentoDTOBuilder.populateRevocaPagamentoDTO(revoca));
				   }
				}
			   dto.setIdQuietanza(pag.getId());
			   dto.setOpInserimento(pag.getOpInserimento());
			   dto.setTipoSpontaneo(pag.getCondPagamento().getPendenza().getTributoEnte().getFlPredeterm());
			   if(mapEnti != null && !mapEnti.isEmpty()){
			      dto.setDenomEnte(mapEnti.get(pag.getIdEnte()));
			   } else {
			      dto.setDenomEnte(pag.getIdEnte());
			   }
			
			   if(mapTipiTributo != null && !mapTipiTributo.isEmpty() && mapTipiTributo.get(pag.getCdTrbEnte()) != null){
                  dto.setDesTributo(mapTipiTributo.get(pag.getCdTrbEnte()));
               } else {
                   dto.setDesTributo(pag.getCdTrbEnte());
               }
			   dto.setAssociatedDocAvailable(pag.isAssociatedDocumentAvailable());
		    }catch (Exception e) {
			   e.printStackTrace();
		    }

		    dto.setIdPagamento(pag.getCondPagamento().getIdPagamento());
		    dto.setIdPendenza(pag.getIdPendenzaente());
		    dto.setCausalePagamento(pag.getCondPagamento().getPendenza().getDeCausale());
		
		    dto.setImPagato(pag.getImPagato());
		    dto.setNotePagamento(pag.getNotePagamento());
		    dto.setIdDocumentoRepository(gestioneFlussi.getIdDocumentoRepository());
		
		    dto.setCodPagamento(gestioneFlussi.getCodPagamento());
		    
		    dto.setPsp(gestioneFlussi.getCfgGatewayPagamento().getSystemName());
		    dto.setNomePSP(gestioneFlussi.getCfgGatewayPagamento().getSystemId());
		    dto.setSubPSP(gestioneFlussi.getCfgGatewayPagamento().getSubsystemId());
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

		    DestinatariPendenza destPend = null;
		
		    if(pag.getCondPagamento() != null && pag.getCondPagamento().getPendenza() != null){
                //prendo il primo (cosi' come accade nel dao)
				Pendenza pendenza = pag.getCondPagamento().getPendenza();
				try {
					Set<DestinatariPendenza> destinatariPendenza = pendenza.getDestinatari();
					if (destinatariPendenza != null && !destinatariPendenza.isEmpty()) {
						destPend = destinatariPendenza.iterator().next();
						dto.setDenominazioneDebitore(destPend.getDeDestinatario());
						dto.setCodFiscDebitore(destPend != null ? destPend.getCoDestinatario() : null);
					}
					dto.setAnnoRif(pendenza.getAnnoRiferimento());
					dto.setNote(pendenza.getNote());
				} catch (Throwable t) {
					dto.setCodFiscDebitore(null);
				}
				TributoStrutturato tributoStrutturato = pendenza.getTributoStrutturato();
				if (tributoStrutturato != null) {
					dto.setNoteVersante(tributoStrutturato.getNoteVersante());
				}				
				
				dto.setImportoDovuto(pag.getCondPagamento().getImTotale());
				dto.setCausaleVersamento(pag.getCondPagamento().getCausalePagamento());
				List<VocePagamento> vociPagamento = pag.getCondPagamento().getVociPagamento();
				if (vociPagamento != null && !vociPagamento.isEmpty()) {
					String vociStr ="";
					for(VocePagamento vp : vociPagamento) {
						vociStr += vp.getDeVoce() + "=" + vp.getImVoce() + ";";
					}
					dto.setVociImporto(vociStr.endsWith(";") ? vociStr.substring(0, vociStr.length()-1) : vociStr);
					
				}

            }
		    
		    dto.setEmailVersante(gestioneFlussi.getEmailVersante());
		    dto.setCodFiscVersante(pag.getCoPagante());
		    dto.setIdFlusso(pag.getIdentificativoFlusso());
		    dto.setTRN(pag.getTRN());
		    dto.setIdRiscossionePSP(pag.getIdRiscossionePSP());
		    java.util.Date dr = (java.util.Date) pag.getDataRegolamento();
		    if(dr != null) {
		    	Timestamp tsr = new Timestamp(dr.getTime());
			    dto.setDataRegolamento(tsr);
		    }
		}
		return dto;
	}
	
	public static PrenotazioniDTO populatePrenotazioniDTO(Prenotazioni prenotazione) {
		
		PrenotazioniDTO dto = new PrenotazioniDTO();
		
		dto.setId(prenotazione.getId());
		dto.setStato(prenotazione.getStato());
		dto.setTipoDato(prenotazione.getCodRich());
		dto.setTipoEsportazioni(prenotazione.getTipoEsportazione());
		dto.setDataPrenotazione(prenotazione.getTsInserimento());
		dto.setCfOperatore(prenotazione.getOpInserimento());
		
		return dto;
	}
	
	public static List<DistintePagamentoDTOLight> populateListDistintePagamentoDTOLight(List<GestioneFlussi> listagf){
	    
		return populateListDistintePagamentoDTOLight(listagf, null, null);
	
	}
	
	/**
	 * 
	 * @param gestioneFlussi
	 * @return
	 */
	public static List<DistintePagamentoDTOLight> populateListDistintePagamentoDTOLight(List<GestioneFlussi> listagf, Map<String, String> mapEnti, Map<String, String> mapTipiTributo) {
		
		List<DistintePagamentoDTOLight> returnDTOList = new ArrayList<DistintePagamentoDTOLight>();

		for (GestioneFlussi gf : listagf) {
			DistintePagamentoDTOLight dto = populateDistintePagamentoDTOLight(gf, mapEnti, mapTipiTributo);//888
			returnDTOList.add(dto);
		}
		
		return returnDTOList;
	}

	public static List<PrenotazioniDTO> populateListPrenotazioniDTOLight(
			List<Prenotazioni> listaPrenotazioni) {
		List<PrenotazioniDTO> returnDTOList = new ArrayList<PrenotazioniDTO>();

		for (Prenotazioni pr : listaPrenotazioni) {
			PrenotazioniDTO dto = populatePrenotazioniDTO(pr);
			returnDTOList.add(dto);
		}
		
		return returnDTOList;
	}

	
	
	public static List<CodiceDescrizioneVO> populateListEntiDTO(
			List<Enti> listaEnti) {
		List<CodiceDescrizioneVO> returnDTOList = new ArrayList<CodiceDescrizioneVO>();

		for (Enti pr : listaEnti) {
			CodiceDescrizioneVO dto = populateEntiDTO(pr);
			returnDTOList.add(dto);
		}
		
		return returnDTOList;
	}
	
	public static List<CodiceDescrizioneVO> populateListTributiEntiDTO(List<TributoEnte> listaTributiEnti) {
        List<CodiceDescrizioneVO> returnDTOList = new ArrayList<CodiceDescrizioneVO>();

        for (TributoEnte pr : listaTributiEnti) {
            CodiceDescrizioneVO dto = populateTributoEnteDTO(pr);
            returnDTOList.add(dto);
        }
        
        return returnDTOList;
    }

	private static CodiceDescrizioneVO populateEntiDTO(Enti pr) {
		CodiceDescrizioneVO vo = new CodiceDescrizioneVO();
		vo.setCodice(pr.getIdEnte());
		vo.setDescrizione(pr.getDenominazione());
		
		return vo;
	}
	
	private static CodiceDescrizioneVO populateTributoEnteDTO(TributoEnte pr) {
        CodiceDescrizioneVO vo = new CodiceDescrizioneVO();
        vo.setCodice(pr.getCdTrbEnte());
        vo.setDescrizione(pr.getDeTrb());
        
        return vo;
    }

	public static List<DistintaPosizioneDebitoriaDettaglioVO> fillDistintePosizioneDebitoriaDettaglioVO(List<GestioneFlussi> flows) {
		
		List<DistintaPosizioneDebitoriaDettaglioVO> distinteVOs = new ArrayList<DistintaPosizioneDebitoriaDettaglioVO>();
		
//		SELECT F.ID as idFlusso, P.ID_CONDIZIONE, F.DATA_SPEDIZIONE,  F.STATO as stato, F.STATO as descrizioneStato, 
//		F.IMPORTO as importoPositivo, F.DATA_CREAZIONE as timbroConferma, P.ID_PENDENZA AS IDPENDENZA, F.COD_TRANSAZIONE as EXTTOKEN, 
//		P.ID as IDPAGAMENTO, P.ST_PAGAMENTO as STPAGAMENTO,  P.FLAG_INCASSO as flagIncasso,  P.TI_DEBITO as TIDEBITO, DP.ID  as IDAUTORIZZAZIONE, 
//		F.TS_INSERIMENTO as DATATRANSAZIONE, F.IMPORTO_COMMISSIONI as IMPORTOCOMMISSIONI, F.IMPORTO as IMPORTONETTO, F.COD_TRANSAZIONE_PSP as codiceContestoPagamento,
//		P.IM_PAGATO as IMPORTOPENDENZA, COALESCE(I.RAGIONESOCIALE,P.ID_ENTE) AS ENTE, I.LAPL AS idFiscaleEnte, COALESCE(T.DE_TRB,P.ID_TRIBUTO) AS TIPOTRIBUTO, 
//		P.CO_PAGANTE as coPagante, P.TIPOSPONTANEO, G.SYSTEM_ID, G.SUBSYSTEM_ID, G.APPLICATION_ID, G.ID_CFG_FORNITORE_GATEWAY as idFornitorePagamento, MP.ID as idModalitaPagamento, MP.DESCRIZIONE as modalitaPagamento, MP.BUNDLE_KEY as bundleModPag,  SP.DESCRIZIONE as tipoStrumento, F.COD_PAGAMENTO as codPagamento
//		
		for (GestioneFlussi flow : flows){			
			DistintaPosizioneDebitoriaDettaglioVO distinta = fillDistintaPosizioneDebitoriaDettaglioVO(flow,false);
			distinteVOs.add(distinta);
		}
		
		return distinteVOs;
	}
	
	
	public static DistintaPosizioneDebitoriaDettaglioVO fillDistintaPosizioneDebitoriaDettaglioVO(GestioneFlussi flow, boolean includePendenzeList) {

		DistintaPosizioneDebitoriaDettaglioVO distinta = new DistintaPosizioneDebitoriaDettaglioVO();
		
		distinta.setIdFlusso(flow.getId());
		
		distinta.setIdModalitaPagamento(flow.getCfgGatewayPagamento().getCfgModalitaPagamento().getId());
		
		distinta.setModalitaPagamento(flow.getCfgGatewayPagamento().getCfgModalitaPagamento().getDescrizione());
		distinta.setModelloVersamento(flow.getCfgGatewayPagamento().getModelloVersamento());
		
		distinta.setIdFornitorePagamento(flow.getCfgGatewayPagamento().getCfgFornitoreGateway().getId());
		
		distinta.setCodPagamento(flow.getCodPagamento());
		
		distinta.setCodiceContestoPagamento(flow.getCodTransazionePSP());
		
		distinta.setDataTransazione(flow.getTsInserimento());
		
		distinta.setExtToken(flow.getCodTransazione());
	
		distinta.setImporto(flow.getTotimportipositivi());

		distinta.setImportoCommissioni(flow.getImportoCommissioni());
		
		distinta.setStato(flow.getStato());
		
		distinta.setDataPagamento(flow.getTsInserimento());
		
		distinta.setIuv(flow.getIuv());
		
		distinta.setTipoIdentificativoAttestante(flow.getTipoIdentificativoAttestante());
		
		distinta.setIdentificativoAttestante(flow.getIdentificativoAttestante());
		
		distinta.setDescrizioneAttestante(flow.getDescrizioneAttestante());
		
		distinta.setIdPspModello3(flow.getIdPspModello3());
		distinta.setIdIntermediarioModello3(flow.getIdIntermediarioModello3());
		distinta.setIdCanaleModello3(flow.getIdCanaleModello3());
		// //////////////////////////////////////////////////////////////////////
		/*
		 * VECCHIA QUERY
		 * 
		SELECT
		T.ID_ENTE as idEnte ,
		T.CD_TRB_ENTE as codTrbEnte,
		F.ID as idFlusso,
		P.ID_CONDIZIONE,
		F.DATA_SPEDIZIONE,
		F.STATO as stato,
		F.STATO as descrizioneStato,
		F.IMPORTO as importoPositivo,
		F.DATA_CREAZIONE as timbroConferma,
		P.ID_PENDENZA AS IDPENDENZA,
		F.COD_TRANSAZIONE as EXTTOKEN,
		P.ID as IDPAGAMENTO,
		P.ST_PAGAMENTO as STPAGAMENTO,
		P.FLAG_INCASSO as flagIncasso,
		P.TI_DEBITO as TIDEBITO,
		DP.ID as IDAUTORIZZAZIONE,
		F.TS_INSERIMENTO as DATATRANSAZIONE,
		F.IMPORTO_COMMISSIONI as IMPORTOCOMMISSIONI,
		F.IMPORTO as IMPORTONETTO,
		F.COD_TRANSAZIONE_PSP as codiceContestoPagamento,
		P.IM_PAGATO as IMPORTOPENDENZA,
		COALESCE(I.RAGIONESOCIALE,P.ID_ENTE) AS ENTE,
		I.LAPL AS idFiscaleEnte,
		COALESCE(T.DE_TRB,P.ID_TRIBUTO) AS TIPOTRIBUTO,
		P.CO_PAGANTE as coPagante,
		P.TIPOSPONTANEO,
		G.SYSTEM_ID,
		G.SUBSYSTEM_ID,
		G.APPLICATION_ID,
		G.ID_CFG_FORNITORE_GATEWAY as idFornitorePagamento,
		MP.ID as idModalitaPagamento,
		MP.DESCRIZIONE as modalitaPagamento,
		MP.BUNDLE_KEY as bundleModPag,
		SP.DESCRIZIONE as tipoStrumento,
		F.COD_PAGAMENTO as codPagamento
		FROM IRIS2.DISTINTE_PAGAMENTO F 
		left outer join IRIS2.PAGAMENTI P on F.ID=P.ID_DISTINTE_PAGAMENTO  
		left outer join IRIS2.JLTENTI E ON E.ID_ENTE=P.ID_ENTE 
		left outer join IRIS2.INTESTATARI I ON I.INTESTATARIO=E.INTESTATARIO 
		left outer join IRIS2.JLTENTR T ON P.CD_TRB_ENTE=T.CD_TRB_ENTE AND P.ID_ENTE = T.ID_ENTE
		left outer join IRIS2.DOCUMENTI_PAGAMENTO DP ON F.ID = DP.ID_DISTINTA_PAGAMENTO
		left outer join IRIS2.CFG_GATEWAY_PAGAMENTO G on G.ID=F.ID_CFG_GATEWAY_PAGAMENTO 
		left outer join IRIS2.CFG_MODALITA_PAGAMENTO MP on G.ID_CFG_MODALITA_PAGAMENTO=MP.ID 
		left outer join IRIS2.CFG_STRUMENTO_PAGAMENTO SP on G.ID_CFG_STRUMENTO_PAGAMENTO=SP.ID			
		 * 
		 */
		
		// //////////////////////////////////////////////////////////////////////			
		//
		// PATCH dopo refactoring query.		
		//
		
		System.out.println("distinta id: " + flow.getId());
		System.out.println("n. pagamenti associati: " + flow.getPagamenti().size());
		
		
		
		if (flow.getPagamenti()==null || flow.getPagamenti().size()==0 ) {
			System.out.println(">>>>Trovata Distinta senza pagamenti: id="+flow.getId());
			
		}
		Pagamenti paga = flow.getPagamenti().iterator().next();
		CfgGatewayPagamento gateway = flow.getCfgGatewayPagamento();
		String denominazEnte = null;
		distinta.setIdCfgGatewayPagamento(flow.getCfgGatewayPagamento().getId());
		
		denominazEnte = paga.getCondPagamento().getEnte().getDenominazione();
		
		distinta.setFlagIncasso(paga.getFlagIncasso());
		distinta.setIdPendenza(paga.getIdPendenza());
		distinta.setIdEnte(paga.getIdEnte());
		distinta.setCodTrbEnte(paga.getCdTrbEnte());
		distinta.setIdCondizione(paga.getCondPagamento().getIdCondizione());
		
		distinta.setFlagOpposizione730(paga.getFlagOpposizione730());
		
		distinta.setCoPagante(paga.getCoPagante());
		distinta.setImportoNetto(paga.getCondPagamento().getImTotale());
		distinta.setIdFiscaleEnte(flow.getIdentificativoFiscaleCreditore());
		distinta.setEnte(denominazEnte != null ? denominazEnte : paga.getIdEnte()); // TODO: qui c'era COALESCE(I.RAGIONESOCIALE,P.ID_ENTE) AS ENTE
		
		distinta.setSystemId(gateway.getSystemId());
		distinta.setSubsystemId(gateway.getSubsystemId());
		distinta.setApplicationId(gateway.getApplicationId());
		distinta.setAssociatedDocAvailable(paga.isAssociatedDocumentAvailable());
		
		CfgStrumentoPagamento strumento = gateway.getCfgStrumentoPagamento();
		
		if (strumento!=null ) {
			distinta.setTipoStrumento(gateway.getCfgStrumentoPagamento().getDescrizione());
		} else {
			distinta.setTipoStrumento("");
		}
		
		
		
		//
		//
		// //////////////////////////////////////////////////////////////////////			

		
		if (includePendenzeList) {
			
			for(Pagamenti p: flow.getPagamenti()) { 
			distinta.setPendenze(new ArrayList<DistintaPosizioneDebitoriaVO>());
			
			DistintaPosizioneDebitoriaVO posdebPendenza = new DistintaPosizioneDebitoriaVO();	
		
			posdebPendenza.setCoPagante(p.getCoPagante());		
			posdebPendenza.setIdPagamento(p.getId());
			
			posdebPendenza.setIdPendenza(p.getIdPendenza());
			posdebPendenza.setStPagamento(p.getStPagamento());
			posdebPendenza.setImportoPendenza(p.getImPagato());
			posdebPendenza.setDataPagamento(p.getTsDecorrenza());
			posdebPendenza.setTipoSpontaneo(p.getTipospontaneo());
			posdebPendenza.setTiDebito(p.getTiDebito());
			posdebPendenza.setCodPagamento(flow.getCodPagamento());
			posdebPendenza.setFlagIncasso(p.getFlagIncasso());						
			posdebPendenza.setAssociatedDocAvailable(p.isAssociatedDocumentAvailable());
			
			Enti ente= p.getCondPagamento().getEnte(); 
			TributoEnte tributoEnte = p.getCondPagamento().getPendenza().getTributoEnte();
			
			posdebPendenza.setEnte(ente.getDenominazione());  
			posdebPendenza.setTipoTributo(tributoEnte.getDeTrb()); 

			distinta.getPendenze().add(posdebPendenza);
			
			}
			
		}
		
		
		return distinta;
		
	}
	
public static PagamentoDTOLightForReceipt populatePagamentoDTOLightForReceipt(Pagamenti p) {
		
		PagamentoDTOLightForReceipt dto = new PagamentoDTOLightForReceipt();
		dto.setId(p.getId());
		dto.setCodAziendaSanitariaEnte(p.getCondPagamento().getPendenza().getTributoEnte().getEntiobj().getCdAziendaSanitaria());
		dto.setCodPagamento(p.getFlussoDistinta().getCodPagamento());
		dto.setCodPagante(p.getCoPagante());
		dto.setExternalReceipt((p.getIdDocumentoExt()!=null)&&(!"".equals(p.getIdDocumentoExt().trim())));
		dto.setIdEnte(p.getIdEnte());
		dto.setIdPagamentoEnte(p.getCondPagamento().getIdPagamento());
		dto.setIdPendenzaEnte(p.getIdPendenzaente());
				
		return dto;
	}

	
}
