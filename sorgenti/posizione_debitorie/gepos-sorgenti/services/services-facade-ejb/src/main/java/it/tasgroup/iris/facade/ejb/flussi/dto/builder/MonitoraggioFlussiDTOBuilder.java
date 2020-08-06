package it.tasgroup.iris.facade.ejb.flussi.dto.builder;

import it.nch.erbweb.web.util.ComboOption;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.EsitiCbill;
import it.tasgroup.iris.domain.EsitiNdp;
import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.domain.MovimentiAccredito;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.domain.Riversamento;
import it.tasgroup.iris.dto.flussi.BFLNonRiconciliatoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDispoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.iris.dto.flussi.DettaglioCasellarioDTOLight;
import it.tasgroup.iris.dto.flussi.DettaglioEsitiDTOLight;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.dto.flussi.EventoNDP_DTO;
import it.tasgroup.iris.dto.flussi.ListaCasellarioDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiCbillDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiNdpDTOLight;
import it.tasgroup.iris.dto.flussi.ListaOperazioniPerEsitoDTOLight;
import it.tasgroup.iris.dto.flussi.NDPNonRiconciliatoDTO;
import it.tasgroup.iris.dto.flussi.RendicontazioniDTOLight;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DistintePagamentoDTOBuilder;
import it.tasgroup.iris.gde.GiornaleEventi;
import it.tasgroup.services.util.enumeration.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MonitoraggioFlussiDTOBuilder {




	/**
	 *
	 * @param listaRendicontazioni
	 * @return
	 */
	public static List<RendicontazioniDTOLight> populateListRendicontazioniDTO(Collection<Rendicontazioni> listaRendicontazioni) {

		List<RendicontazioniDTOLight> dtos = new ArrayList<RendicontazioniDTOLight>();

		for (Rendicontazioni rend : listaRendicontazioni) {
			RendicontazioniDTOLight dto = populateRendicontazioniDTOLight(rend);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param rend
	 * @return
	 */
	public static RendicontazioniDTOLight populateRendicontazioniDTOLight(Rendicontazioni rend) {
		if (rend == null)
			return null;

		RendicontazioniDTOLight dto = new RendicontazioniDTOLight();

		dto.setId(rend.getId());
		dto.setDataCreazione(rend.getDataCreazione());
		dto.setImporto(rend.getImporto());
		dto.setNumeroEsiti(rend.getNumeroEsiti());
		dto.setNumEsitiInsoluto(rend.getNumEsitiInsoluto());
		dto.setStato(rend.getStato());
		dto.setDataRicezione(rend.getDataRicezione());
		dto.setUtenteCreatore(rend.getUtenteCreatore());
		dto.setCodRendicontazione(rend.getCodRendicontazione());
		return dto;
	}


	/**
	 *
	 * @param listagf
	 * @return
	 */
	public static List<DistintePagamentoDTOLight> populateListDistintePagamentoDTO(List<GestioneFlussi> listagf) {
		List<DistintePagamentoDTOLight> dtos = new ArrayList<DistintePagamentoDTOLight>();

		for (GestioneFlussi gf : listagf) {
			DistintePagamentoDTOLight dto = populateDistintePagamentoDTO(gf);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param gestioneFlussi
	 * @return
	 */
	public static DistintePagamentoDTOLight populateDistintePagamentoDTO(GestioneFlussi gestioneFlussi) {
		DistintePagamentoDTOLight dto = new DistintePagamentoDTOLight();

		dto.setId(gestioneFlussi.getId().intValue());

		dto.setCodTransazione(gestioneFlussi.getCodTransazione());
		dto.setDataCreazione(gestioneFlussi.getTmbcreazione());
		dto.setDataSpedizione(gestioneFlussi.getDataSpedizione());
		dto.setStato(gestioneFlussi.getStato());
		dto.setNumDisposizioni(gestioneFlussi.getNumeroDisposizioni());
		dto.setImporto(gestioneFlussi.getTotimportipositivi());
		dto.setUtenteCreatore(gestioneFlussi.getUtentecreatore());
		Iterator<Pagamenti> pagIter=gestioneFlussi.getPagamenti().iterator();
		while (pagIter.hasNext()) {
			Pagamenti p = pagIter.next();
			dto.setAssociatedDocAvailable(p.isAssociatedDocumentAvailable());
		    break;
	    }
		return dto;
	}

	/**
	 *
	 * @param listaCasellario
	 * @return
	 */
	public static List<CasellarioDTO> populateListCasellarioDTOInfo(List<CasellarioInfo> listaCasellario, boolean isLoadFlussoCBI) {

		List<CasellarioDTO> dtos = new ArrayList<CasellarioDTO>();

		for (CasellarioInfo cd : listaCasellario) {
			CasellarioDTO dto = populateCasellarioDTOInfo(cd, isLoadFlussoCBI);
			dtos.add(dto);
		}

		return dtos;
	}

        /**
	 *
	 * @param lista
	 * @return
	 */
	public static List<ComboOption> populateListComboOption(List<Object[]> lista) {

		List<ComboOption> dtos = new ArrayList<ComboOption>();

		for (Object[] cd : lista) {
                        ComboOption option = new ComboOption((String)cd[0], (String)cd[1]);
			dtos.add(option);
		}

		return dtos;
	}

	/**
	 *
	 * @param cd
	 * @return
	 */
	public static CasellarioDTO populateCasellarioDTOInfo(CasellarioInfo ci, boolean isLoadFlussoCBI) {
		if (ci == null)
			return null;

		CasellarioInfoDTO dto = new CasellarioInfoDTO();

                dto.setDataElaborazione(ci.getDataElaborazione());
                dto.setMittente(ci.getMittente());
		dto.setRicevente(ci.getRicevente());
		dto.setId(ci.getId());
		dto.setDescErrore(ci.getDescErrore());
		dto.setDimensione(ci.getDimensione());
		dto.setNomeSupporto(ci.getNomeSupporto());
		dto.setNumeroRecord(ci.getNumeroRecord());
		dto.setTipoErrore(ci.getTipoErrore());
		dto.setTipoFlusso(ci.getTipoFlusso());
		dto.setDescErrore(ci.getDescErrore());

		if ((ci.getTipoErrore() != null && !ci.getTipoErrore().equals(0)) && ci.getFlagElaborazione().equals(1)) {
			dto.setStato_virtuale(EnumStatoRendicontazione.RIFIUTATO.getChiave());
		} else if (ci.getFlagElaborazione().equals(0)) {
			dto.setStato_virtuale(EnumStatoRendicontazione.DA_ELABORARE.getChiave());
		}

		dto.setRendicontazioni(populateRendicontazioniDTOLight(ci.getRendicontazioni()));
		dto.setFlagElaborazione(EnumFlagElaborazioneFlussi.getValoreByChiave(ci.getFlagElaborazione().toString()));
		if (isLoadFlussoCBI)
			dto.setFlussoCBI(ci.getFlussoCbi());

		dto.setNomeFile(ci.getNomeFile());

		return dto;
	}




	/**
	 *
	 * @param listaCasellario
	 * @return
	 */
	public static List<CasellarioDTO> populateListCasellarioDTODispo(List<CasellarioDispo> listaCasellario, boolean isLoadFlussoCBI) {

		List<CasellarioDTO> dtos = new ArrayList<CasellarioDTO>();

		for (CasellarioDispo cd : listaCasellario) {
			CasellarioDTO dto = populateCasellarioDTODispo(cd,isLoadFlussoCBI);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param cd
	 * @return
	 */
	public static CasellarioDTO populateCasellarioDTODispo(CasellarioDispo cd, boolean isLoadFlussoCBI) {
		if (cd == null)
			return null;

		CasellarioDispoDTO dto = new CasellarioDispoDTO();

		dto.setDataElaborazione(cd.getDataElaborazione());

		dto.setId(cd.getId());
		dto.setDescErrore(cd.getDescErrore());
		dto.setDimensione(cd.getDimensione());
		dto.setNomeSupporto(cd.getNomeSupporto());
		dto.setNumeroRecord(cd.getNumeroRecord());
		dto.setTipoErrore(cd.getTipoErrore());
		dto.setTipoFlusso(cd.getTipoFlusso());
		dto.setDescErrore(cd.getDescErrore());

		if ((cd.getTipoErrore() != null && !cd.getTipoErrore().equals(0)) && cd.getFlagElaborazione().equals(1)) {
			dto.setStato_virtuale(EnumStatoRendicontazione.RIFIUTATO.getChiave());
		} else if (cd.getFlagElaborazione().equals(0)) {
			dto.setStato_virtuale(EnumStatoRendicontazione.DA_ELABORARE.getChiave());
		}

		if(cd.getDistintePagamento() != null){
			dto.setDistintePagamento(populateDistintePagamentoDTO(cd.getDistintePagamento()));
		} else {
			dto.setDistinteRaccredito(DistinteRiaccreditoDTOBuilder.populateDistinteRiaccreditoDTO(cd.getDistinteRiaccredito()));
		}

		dto.setFlagElaborazione(EnumFlagElaborazioneFlussi.getValoreByChiave(cd.getFlagElaborazione().toString()));
		if (isLoadFlussoCBI)
			dto.setFlussoCBI(cd.getFlussoCbi());

		return dto;
	}


	/**
	 *
	 * @param listaCasellario
	 * @return
	 */
	public static List<ListaCasellarioDTOLight> populateListListaCasellarioDTOLightInfo(List<CasellarioDTO> listaCasellario) {

		List<ListaCasellarioDTOLight> dtos = new ArrayList<ListaCasellarioDTOLight>();

		for (CasellarioDTO c : listaCasellario) {
			ListaCasellarioDTOLight dto = populateListaCasellarioDTOLightInfo((CasellarioInfoDTO)c);
			dtos.add(dto);
		}

		return dtos;
	}


	/**
	 *
	 * @param cd
	 * @return
	 */
	public static ListaCasellarioDTOLight populateListaCasellarioDTOLightInfo(CasellarioInfoDTO ci) {
		if (ci == null)
			return null;

		ListaCasellarioDTOLight dto = new ListaCasellarioDTOLight();

		dto.setId(ci.getId());

		if (ci.getRendicontazioni() != null) {
			dto.setImporto(ci.getRendicontazioni().getImporto());
			dto.setDataRicezione(ci.getRendicontazioni().getDataRicezione());
			dto.setIdRendicontazione(ci.getRendicontazioni().getId());
			dto.setStato(ci.getRendicontazioni().getStato());
		} else {
			dto.setStato(ci.getStato_virtuale());
		}

		dto.setDataCreazione(ci.getDataElaborazione());
		dto.setTipoFlusso(ci.getTipoFlusso());
                dto.setMittente(ci.getMittente());
		dto.setRicevente(ci.getRicevente());
		dto.setNomeSupporto(ci.getNomeSupporto());
		dto.setDimensione(ci.getDimensione());
		dto.setNumeroRecord(ci.getNumeroRecord());
		dto.setNomeFile(ci.getNomeFile());
		return dto;
	}


	/**
	 *
	 * @param listaCasellario
	 * @return
	 */
	public static List<ListaCasellarioDTOLight> populateListListaCasellarioDTOLightDispo(List<CasellarioDTO> listaCasellario) {

		List<ListaCasellarioDTOLight> dtos = new ArrayList<ListaCasellarioDTOLight>();

		for (CasellarioDTO c : listaCasellario) {
			ListaCasellarioDTOLight dto = populateListaCasellarioDTOLightDispo((CasellarioDispoDTO)c);
			dtos.add(dto);
		}

		return dtos;
	}


	/**
	 *
	 * @param cd
	 * @return
	 */
	public static ListaCasellarioDTOLight populateListaCasellarioDTOLightDispo(CasellarioDispoDTO cd) {
		if (cd == null)
			return null;

		ListaCasellarioDTOLight dto = new ListaCasellarioDTOLight();

		dto.setId(cd.getId());
		dto.setNumeroRecord(cd.getNumeroRecord());
		if(cd.getDistintePagamento() != null){
			dto.setImporto(cd.getDistintePagamento().getImporto());
			dto.setDataSpedizione(cd.getDistintePagamento().getDataSpedizione());
			dto.setIdDistintaPagamento(cd.getDistintePagamento().getId().longValue());
			dto.setStato(cd.getDistintePagamento().getStato());
		} else {
			dto.setImporto(cd.getDistinteRaccredito().getImporto());
			dto.setDataSpedizione(cd.getDistinteRaccredito().getDataSpedizione());
			dto.setIdDistintaRiaccredito(cd.getDistinteRaccredito().getId());
			dto.setStato(cd.getDistinteRaccredito().getStato());
		}

		dto.setDataCreazione(cd.getDataElaborazione());

		if (dto.getStato().equals(""))
			dto.setStato(cd.getStato_virtuale());

		dto.setTipoFlusso(cd.getTipoFlusso());
		dto.setNomeSupporto(cd.getNomeSupporto());
		dto.setDimensione(cd.getDimensione());

		return dto;
	}


	/**
	 *
	 * @param ci
	 * @return
	 */
	public static DettaglioCasellarioDTOLight populateDettaglioCasellarioDTOLightInfo(CasellarioInfoDTO ci) {
		if (ci == null)
			return null;

		DettaglioCasellarioDTOLight dto = new DettaglioCasellarioDTOLight();

		dto.setId(ci.getId());
		if(ci.getRendicontazioni() != null){
			dto.setDataRicezione(ci.getRendicontazioni().getDataRicezione());
			dto.setNumeroEsiti(ci.getRendicontazioni().getNumeroEsiti());
			dto.setUtenteCreatore(ci.getRendicontazioni().getUtenteCreatore());
			dto.setNumEsitiPagato(ci.getRendicontazioni().getNumEsitiPagato());
			dto.setNumEsitiInsoluto(ci.getRendicontazioni().getNumEsitiInsoluto());
			dto.setIdRendicontazione(ci.getRendicontazioni().getId());
		}

		dto.setDataCreazione(ci.getDataElaborazione());

		dto.setTipoFlusso(ci.getTipoFlusso());
                dto.setMittente(ci.getMittente());
                dto.setRicevente(ci.getRicevente());
		dto.setNomeSupporto(ci.getNomeSupporto());
		dto.setNumeroRecord(ci.getNumeroRecord());
		dto.setFlagElaborazione(ci.getFlagElaborazione());
		dto.setDataElaborazione(ci.getDataElaborazione());

		dto.setTipoErrore(EnumTipoErroreRid.getValoreByChiave(String.valueOf(ci.getTipoErrore())));

		dto.setDescErrore(ci.getDescErrore());

		return dto;
	}

	/**
	 *
	 * @param cd
	 * @return
	 */
	public static DettaglioCasellarioDTOLight populateDettaglioCasellarioDTOLightDispo(CasellarioDispoDTO cd) {
		if (cd == null)
			return null;

		DettaglioCasellarioDTOLight dto = new DettaglioCasellarioDTOLight();

		dto.setId(cd.getId());

		if(cd.getDistintePagamento() != null){
			dto.setDataSpedizione(cd.getDistintePagamento().getDataSpedizione());
			dto.setNumeroEsiti(cd.getDistintePagamento().getNumDisposizioni());
			dto.setUtenteCreatore(cd.getDistintePagamento().getUtenteCreatore());
			dto.setCodTransazione(cd.getDistintePagamento().getCodTransazione());
			dto.setIdDistintaPagamento(cd.getDistintePagamento().getId().longValue());
		} else {
			dto.setDataCreazione(cd.getDistinteRaccredito().getDataCreazione());
			dto.setDataSpedizione(cd.getDistinteRaccredito().getDataSpedizione());
			dto.setNumeroEsiti(cd.getDistinteRaccredito().getNumeroDisposizioni());

			//TODO: Utente creatore nelle distinte di riaccredito??
			dto.setUtenteCreatore("");
			dto.setCodTransazione(cd.getDistinteRaccredito().getCodTransazione());
			//
		}

		dto.setDataCreazione(cd.getDataElaborazione());

		dto.setTipoFlusso(cd.getTipoFlusso());
		dto.setNomeSupporto(cd.getNomeSupporto());
		dto.setNumeroRecord(cd.getNumeroRecord());

		dto.setFlagElaborazione(cd.getFlagElaborazione());
		dto.setDataElaborazione(cd.getDataElaborazione());
		dto.setTipoErrore(cd.getTipoErrore());
		dto.setDescErrore(cd.getDescErrore());

		return dto;
	}



	/**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightEsitiBb(List<EsitiBb> esiti) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (EsitiBb eBb : esiti) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightEsitiBb(eBb);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param eBb
	 * @return
	 */
	public static ListaEsitiDTOLight populateEsitiDTOLightEsitiBb(EsitiBb eBb) {
		if (eBb == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(eBb.getId());
		dto.setTipoEsito(eBb.getModalitaPagamento());
		dto.setProgressivo(eBb.getProgressivo());
		dto.setImporto(eBb.getImporto());
		dto.setDataPagamento(eBb.getDataPagamento());
		dto.setDataValuta(eBb.getDataValuta());
		dto.setRiferimento(eBb.getNumeroIncasso());
		dto.setFlagRiconciliazione(eBb.getFlagRiconciliazione());
		dto.setIdRiconciliazione((String)eBb.getIdRiconciliazione());
		dto.setIdBozzeBonificiRiaccredito(eBb.getIdBozzeBonificiRiaccredito());
		dto.setIdrendicontazione(eBb.getRendicontazioni().getId());

		return dto;
	}


	/**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightEsitiRct(List<EsitiRct> esiti) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (EsitiRct eRct : esiti) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightEsitiRct(eRct);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param eRct
	 * @return
	 */
	public static ListaEsitiDTOLight populateEsitiDTOLightEsitiRct(EsitiRct eRct) {
		if (eRct == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(eRct.getId());
		dto.setTipoEsito(eRct.getTipoRecord());
		dto.setProgressivo(eRct.getProgressivo());
		dto.setImporto(eRct.getImporto());
		dto.setDataPagamento(eRct.getDataContabile());
		dto.setDataValuta(eRct.getDataValuta());
		dto.setRiferimento(eRct.getRiferimentoBanca());
		dto.setFlagRiconciliazione(eRct.getFlagRiconciliazione());
		dto.setIdRiconciliazione((String)eRct.getIdRiconciliazione());
		dto.setIdBozzeBonificiRiaccredito(eRct.getIdBozzeBonificiRiaccredito());
		dto.setIdrendicontazione(eRct.getRendicontazioni().getId());

		return dto;
	}

        /**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiCbillDTOLight> populateListaEsitiDTOLightEsitiCbill(List<EsitiCbill> esiti) {

		List<ListaEsitiCbillDTOLight> dtos = new ArrayList<ListaEsitiCbillDTOLight>();

		for (EsitiCbill eRct : esiti) {
			ListaEsitiCbillDTOLight dto = populateEsitiDTOLightEsitiCbill(eRct);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param eCbill
	 * @return
	 */
	public static ListaEsitiCbillDTOLight populateEsitiDTOLightEsitiCbill(EsitiCbill eCbill) {
		if (eCbill == null)
			return null;

		ListaEsitiCbillDTOLight dto = new ListaEsitiCbillDTOLight();

		dto.setId(eCbill.getId());

        if (eCbill.getImportoDebito()!=null) {
        	dto.setImporto(eCbill.getImportoDebito().divide(BigDecimal.valueOf(100)));
        } else {
        	dto.setImporto(BigDecimal.ZERO);
        }

		dto.setDataPagamento(eCbill.getDataPagamento());
		dto.setFlagRiconciliazione(eCbill.getFlagRiconciliazione());
		dto.setIdRiconciliazione((String)eCbill.getIdRiconciliazione());
		dto.setIdBozzeBonificiRiaccredito(eCbill.getIdBozzeBonificiRiaccredito());
		dto.setIdrendicontazione(eCbill.getRendicontazioni().getId());
                dto.setCanale(eCbill.getCanale());
                dto.setCodiceBiller(eCbill.getCodiceBiller());
                dto.setIdVersamento(eCbill.getIdVersamento());
                dto.setIdDebito(eCbill.getIdDebito());
                if (eCbill.getImportoTotale()!=null) {
                	dto.setImportoTotale(eCbill.getImportoTotale().divide(BigDecimal.valueOf(100)));
                } else {
                	dto.setImportoTotale(BigDecimal.ZERO);
                }

                if (eCbill.getCommissioniBiller()!=null) {
                	dto.setImportoTotale(eCbill.getCommissioniBiller().divide(BigDecimal.valueOf(100)));
                } else {
                	dto.setImportoTotale(BigDecimal.ZERO);
                }

                if (eCbill.getCommissioniBanca()!=null) {
                	dto.setImportoTotale(eCbill.getCommissioniBanca().divide(BigDecimal.valueOf(100)));
                } else {
                	dto.setImportoTotale(BigDecimal.ZERO);
                }

                dto.setIbanAccredito(eCbill.getIbanAccredito());
                dto.setBancaOrdinante(eCbill.getBancaOrdinante());
                dto.setBancaBeneficiario(eCbill.getBancaBeneficiario());
                dto.setCodTransazionePSP(eCbill.getCodTransazionePSP());
                dto.setDettagliAtm(eCbill.getDettagliAtm());

		return dto;
	}

        /**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiNdpDTOLight> populateListaEsitiDTOLightEsitiNdp(List<EsitiNdp> esiti) {

		List<ListaEsitiNdpDTOLight> dtos = new ArrayList<ListaEsitiNdpDTOLight>();

		for (EsitiNdp ndp : esiti) {
			ListaEsitiNdpDTOLight dto = populateEsitiDTOLightEsitiNdp(ndp);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param eNdp
	 * @return
	 */
	public static ListaEsitiNdpDTOLight populateEsitiDTOLightEsitiNdp(EsitiNdp eNdp) {
		if (eNdp == null)
			return null;

		ListaEsitiNdpDTOLight dto = new ListaEsitiNdpDTOLight();

		dto.setId(eNdp.getId());
		dto.setDataPagamento(eNdp.getDataPagamento());
		dto.setFlagRiconciliazione(eNdp.getFlagRiconciliazione());
		dto.setIdRiconciliazione((String)eNdp.getIdRiconciliazione());
		dto.setIdBozzeBonificiRiaccredito(eNdp.getIdBozzeBonificiRiaccredito());
		dto.setIdrendicontazione(eNdp.getRendicontazioni().getId());
		dto.setImporto(eNdp.getImporto());

                dto.setImportoConSegno(eNdp.getImportoConSegno());
                dto.setImportoSenzaSegno(eNdp.getImportoSenzaSegno());
                dto.setId(eNdp.getId());
                dto.setEsitoPagamento(eNdp.getEsitoPagamento());
                dto.setIdRiscossione(eNdp.getIdRiscossione());
                dto.setTipoAnomalia(EnumTipoAnomaliaNDP.getByKey(eNdp.getCodAnomalia()));

		return dto;
	}

        /**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaOperazioniPerEsitoDTOLight> populateListaOperazioniPerEsitoDTOLight(List<Object[]> esiti) {

		List<ListaOperazioniPerEsitoDTOLight> dtos = new ArrayList<ListaOperazioniPerEsitoDTOLight>();

		Integer numTotaleOperazioni = 0;
		for (Object[] operazione : esiti) {
			ListaOperazioniPerEsitoDTOLight dto = populateListaOperazioniPerEsitoDTOLight(operazione);
			numTotaleOperazioni=numTotaleOperazioni+dto.getNumeroOperazioni().intValue();
			dtos.add(dto);
		}

		System.out.println("Num Totale Operazioni="+numTotaleOperazioni);

		// Calcolo le percentuali per ogni riga trovata
		for (ListaOperazioniPerEsitoDTOLight dto: dtos){
			dto.setPercSuTotOperazioni(new BigDecimal(dto.getNumeroOperazioni().intValue()).multiply(new BigDecimal("100.00")).divide(new BigDecimal(numTotaleOperazioni),2, RoundingMode.HALF_UP));
		}

		return dtos;
	}

	/**
	 *
	 * @param pagOnline
	 * @return
	 */
	public static ListaOperazioniPerEsitoDTOLight populateListaOperazioniPerEsitoDTOLight(Object[] pagOnline) {
		if (pagOnline == null)
			return null;

		ListaOperazioniPerEsitoDTOLight dto = new ListaOperazioniPerEsitoDTOLight();

		dto.setEsito((String) pagOnline[0]);
                dto.setCodErrore((String) pagOnline[1]);
		dto.setNumeroOperazioni((Number) pagOnline[2]);

                return dto;
	}

        /**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaOperazioniPerEsitoDTOLight> populateListaDettaglioOperazioniPerEsitoDTOLight(List<PagamentiOnline> esiti) {

		List<ListaOperazioniPerEsitoDTOLight> dtos = new ArrayList<ListaOperazioniPerEsitoDTOLight>();

		for (PagamentiOnline pagamento : esiti) {
			ListaOperazioniPerEsitoDTOLight dto = populateListaDettaglioOperazioniPerEsitoDTOLight(pagamento);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param pagOnline
	 * @return
	 */
	public static ListaOperazioniPerEsitoDTOLight populateListaDettaglioOperazioniPerEsitoDTOLight(PagamentiOnline pagOnline) {
		if (pagOnline == null)
			return null;

		ListaOperazioniPerEsitoDTOLight dto = new ListaOperazioniPerEsitoDTOLight();

                dto.setEsito(pagOnline.getEsito());
                dto.setCodErrore(pagOnline.getCodErrore());
                dto.setSystemId(pagOnline.getSystemId());
                dto.setApplicationId(pagOnline.getApplicationId());
                dto.setTsOperazione(pagOnline.getTsOperazione());
                dto.setTsInserimento(pagOnline.getTsInserimento());
                dto.setTsAggiornamento(pagOnline.getTsAggiornamento());
                dto.setTiOperazione(pagOnline.getTiOperazione());

                dto.setSessionIdToken(pagOnline.getSessionIdToken());
                dto.setSessionIdTimbro(pagOnline.getSessionIdTimbro());
                dto.setSessionIdTerminale(pagOnline.getSessionIdTerminale());
                dto.setSessionIdSistema(pagOnline.getSessionIdSistema());
                dto.setOpInserimento(pagOnline.getOpInserimento());
                dto.setOpAggiornamento(pagOnline.getOpAggiornamento());
                dto.setNumOperazione(pagOnline.getNumOperazione());
                dto.setIdOperazione(pagOnline.getIdOperazione());
                dto.setDeOperazione(pagOnline.getDeOperazione());
                dto.setCodAutorizzazione(pagOnline.getCodAutorizzazione());
                GestioneFlussi flussoDistintaOnline = pagOnline.getFlussoDistintaOnline();
                dto.setDistintaPagamento(flussoDistintaOnline != null? DistintePagamentoDTOBuilder.populateDistintePagamentoDTO(flussoDistintaOnline) : null);

                return dto;
	}

	/**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightEsitiBonificiRiaccredito(List<EsitiBonificiRiaccredito> esiti) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (EsitiBonificiRiaccredito eBR : esiti) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightEsitiBonificiRiaccredito(eBR);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param eRct
	 * @return
	 */
	public static ListaEsitiDTOLight populateEsitiDTOLightEsitiBonificiRiaccredito(EsitiBonificiRiaccredito eBR) {

		if (eBR == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(eBR.getId());
		dto.setTipoEsito(eBR.getCausale());
		dto.setImporto(eBR.getImporto());
		dto.setDataPagamento(eBR.getDataOrdine());
		dto.setDataValuta(eBR.getDataValutaOrdinante());
		dto.setRiferimento(eBR.getCodiceRiferimento());
		dto.setFlagRiconciliazione(eBR.getFlagRiconciliazione());
		dto.setIdRiconciliazione((String)eBR.getIdRiconciliazione());
		dto.setIdrendicontazione(eBR.getRendicontazioni().getId());

		return dto;
	}


	/**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightIncassiBonificiRh(List<IncassiBonificiRh> esiti) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (IncassiBonificiRh iBRh : esiti) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightIncassiBonificiRh(iBRh);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param iBRh
	 * @return
	 */
	public static ListaEsitiDTOLight populateEsitiDTOLightIncassiBonificiRh(IncassiBonificiRh iBRh) {

		if (iBRh == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(iBRh.getId());
		dto.setTipoEsito(iBRh.getCausale());
		dto.setProgressivo(iBRh.getProgressivo61()+iBRh.getProgressivo62());
		dto.setImporto(iBRh.getImporto());
		dto.setDataPagamento(iBRh.getDataContabile());
		dto.setDataValuta(iBRh.getDataValuta());
		dto.setRiferimento(iBRh.getRiferimentoBanca());
		dto.setFlagRiconciliazione(iBRh.getFlagRiconciliazione());


		dto.setIdRiconciliazione(iBRh.getIdRiconciliazione());

		dto.setIdBozzeBonificiRiaccredito(iBRh.getIdBozzeBonificiRiaccredito());
		dto.setIdrendicontazione(iBRh.getRendicontazioni().getId());

		return dto;
	}


	/**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(EsitiBb esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdBozzeBonificiRiaccredito(esito.getIdBozzeBonificiRiaccredito());
		dto.setIdRiconciliazione((String)esito.getIdRiconciliazione());

		dto.setAbiEsattrice(esito.getAbiEsattrice());
		dto.setCabEsattrice(esito.getCabEsattrice());
		dto.setCodeline(esito.getCodeline());

		return dto;
	}

        /**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(EsitiCbill esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdBozzeBonificiRiaccredito(esito.getIdBozzeBonificiRiaccredito());
		dto.setIdRiconciliazione((String)esito.getIdRiconciliazione());

		dto.setCanale(esito.getCanale());
                dto.setCodiceBiller(esito.getCodiceBiller());
                dto.setIdVersamento(esito.getIdVersamento());
                dto.setIdDebito(esito.getIdDebito());
                dto.setImportoTotale(esito.getImportoTotaleInEuro());
                dto.setCommissioniBiller(esito.getCommissioniBiller());
                dto.setCommissioniBanca(esito.getCommissioniBanca());
                dto.setIbanAccredito(esito.getIbanAccredito());
                dto.setBancaOrdinante(esito.getBancaOrdinante());
                dto.setBancaBeneficiario(esito.getBancaBeneficiario());
                dto.setCodTransazionePSP(esito.getCodTransazionePSP());
                dto.setDettagliAtm(esito.getDettagliAtm());
                dto.setImportoDebito(esito.getImportoDebitoInEuro());
                dto.setDettagliAtm(esito.getDettagliAtm());
                dto.setFlagRiconciliazione(esito.getFlagRiconciliazione());

                return dto;
	}

        /**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(EsitiNdp esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdBozzeBonificiRiaccredito(esito.getIdBozzeBonificiRiaccredito());
		dto.setIdRiconciliazione((String)esito.getIdRiconciliazione());

        dto.setImportoConSegno(esito.getImportoConSegno());
        dto.setIdRiscossione(esito.getIdRiscossione());
        dto.setEsitoPagamento(esito.getEsitoPagamento());

        return dto;
	}

	/**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(EsitiRct esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdBozzeBonificiRiaccredito(esito.getIdBozzeBonificiRiaccredito());
		dto.setIdRiconciliazione((String)esito.getIdRiconciliazione());


		return dto;
	}

	/**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(IncassiBonificiRh esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdBozzeBonificiRiaccredito(esito.getIdBozzeBonificiRiaccredito());

		dto.setIdRiconciliazione(esito.getIdRiconciliazione());


		return dto;
	}


	/**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(EsitiBonificiRiaccredito esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		if (esito.getBonificiRiaccredito()!=null) {
		  dto.setIdBonificiRiaccredito(esito.getBonificiRiaccredito().getId());
		}
		dto.setDataEsecuzione(esito.getDataEsecuzione());
		dto.setDataContabileAddebito(esito.getDataContabileAddebito());
		dto.setDataValutaBeneficiario(esito.getDataValutaBeneficiario());


		return dto;
	}


	/**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightRID(List<Rid> esiti) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (Rid rid : esiti) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightRid(rid);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param rid
	 * @return
	 */
	public static ListaEsitiDTOLight populateEsitiDTOLightRid(Rid rid) {
		if (rid == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(rid.getId());
		dto.setTipoEsito(rid.getCausale());
		dto.setProgressivo(rid.getProgressivo());
		dto.setImporto(rid.getImporto());
		dto.setDataPagamento(rid.getDataScadenza());
		dto.setRiferimento(rid.getRiferimentoDebito());
		dto.setIdRiconciliazione((Integer)rid.getIdDisposizioneOrig());
		dto.setIdBozzeBonificiRiaccredito(rid.getIdBozzeBonificiRiaccredito());
		dto.setIdrendicontazione(rid.getRendicontazioni().getId());

		return dto;
	}

	/**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(Rid esito) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdBozzeBonificiRiaccredito(esito.getIdBozzeBonificiRiaccredito());
		dto.setIdRiconciliazione((String)esito.getRiferimentoDebito());

		dto.setCausale(EnumCausaleRid.getValoreByChiave(esito.getCausale()));
		dto.setDataScadenzaOrig(esito.getDataScadenzaOrig());
		dto.setSiaCreditore(esito.getSiaCreditore());
		dto.setCodDebitore(esito.getCodDebitore());
		dto.setTipoCodIndividuale(esito.getTipoCodIndividuale());
		dto.setAbiBancaAssuntrice(esito.getAbiBancaAssuntrice());
		dto.setCabBancaAssuntrice(esito.getCabBancaAssuntrice());
		dto.setIbanOrdinante(esito.getIbanOrdinante());
		dto.setCodRiferimento(esito.getCodRiferimento());
		if (esito.getCausaleStorno() != null)
			dto.setCausaleStorno(EnumCausaleStornoRid.getValoreByChiave(esito.getCausaleStorno()));

		return dto;
	}

	/**
	 *
	 * @param esiti
	 * @return
	 */
	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightAEA(List<AllineamentiElettroniciArchivi> esiti) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (AllineamentiElettroniciArchivi aea : esiti) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightAEA(aea);
			dtos.add(dto);
		}

		return dtos;
	}

	public static List<ListaEsitiDTOLight> populateListaEsitiDTOLightOPI(List<MovimentiAccredito> listaMovimentiAccredito) {

		List<ListaEsitiDTOLight> dtos = new ArrayList<ListaEsitiDTOLight>();

		for (MovimentiAccredito movimentoAccredito : listaMovimentiAccredito) {
			ListaEsitiDTOLight dto = populateEsitiDTOLightOPI(movimentoAccredito);
			dtos.add(dto);
		}

		return dtos;
	}

	/**
	 *
	 * @param rid
	 * @return
	 */
	public static ListaEsitiDTOLight populateEsitiDTOLightAEA(AllineamentiElettroniciArchivi aea) {
		if (aea == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(aea.getId());
		dto.setTipoEsito(aea.getCausale());
		dto.setProgressivo(aea.getProgressivo());
		dto.setRiferimento(aea.getCodRiferimento());
		dto.setIdRiconciliazione(aea.getIdDisposizioneOrig());
		dto.setIdrendicontazione(aea.getRendicontazioni().getId());

		return dto;
	}

	public static ListaEsitiDTOLight populateEsitiDTOLightOPI(MovimentiAccredito movimentoAccredito) {
		if (movimentoAccredito == null)
			return null;

		ListaEsitiDTOLight dto = new ListaEsitiDTOLight();

		dto.setId(movimentoAccredito.getId());
		dto.setTipoEsito(movimentoAccredito.getTipoAccredito());
		Integer progressivo = 0;
		try {
			progressivo = Integer.valueOf(movimentoAccredito.getIdMovimento().split("\\.")[1]);
		} catch (Exception e) {}
		dto.setProgressivo(progressivo);
		dto.setImporto(movimentoAccredito.getImporto());
		dto.setDataPagamento(movimentoAccredito.getDataContabile());
		dto.setDataValuta(movimentoAccredito.getDataValutaBeneficiario());
		dto.setRiferimento(movimentoAccredito.getIdMovimento());
		dto.setFlagRiconciliazione(Integer.valueOf(movimentoAccredito.getFlagRiconciliazione()));
		dto.setIdRiconciliazione(movimentoAccredito.getIdRiconciliazione());

		return dto;
	}

	/**
	 *
	 * @param esito
	 * @return
	 */
	public static DettaglioEsitiDTOLight populateDettaglioEsitiDTOLight(AllineamentiElettroniciArchivi esito, String id_ric) {
		if (esito == null)
			return null;

		DettaglioEsitiDTOLight dto = new DettaglioEsitiDTOLight();

		dto.setId(esito.getId());
		dto.setIdRiconciliazione(id_ric);

		dto.setCausale(EnumCausaleAEA.getValoreByChiave(esito.getCausale()));
		dto.setDataCreazioneOrig(esito.getDataCreazioneOrig());
		dto.setSiaCreditore_aea(esito.getSiaCreditore());
		dto.setCodIndividuale(esito.getCodIndividuale());
		dto.setTipoCodIndividuale_aea(esito.getTipoCodIndividuale());
		dto.setAbiAddebito(esito.getAbiAddebito());
		dto.setCabAddebito(esito.getCabAddebito());
		dto.setNumeroCcAddebito(esito.getNumeroCcAddebito());
		dto.setCodPaeseAddebito(esito.getCodPaeseAddebito());
		dto.setCheckDigitAddebito(esito.getCheckDigitAddebito());
		dto.setCinAddebito(esito.getCinAddebito());
		dto.setCodRiferimento_aea(esito.getCodRiferimento());

		return dto;
	}

	public static List<BFLNonRiconciliatoDTO> populateListBFLNonRiconciliati(List<IncassiBonificiRh> listaIncassi) {

		List<BFLNonRiconciliatoDTO> listaNonRiconciliati = new ArrayList<BFLNonRiconciliatoDTO>();

		for (IncassiBonificiRh inc : listaIncassi){

			BFLNonRiconciliatoDTO bfl = new BFLNonRiconciliatoDTO();

			bfl.setIdIncasso(inc.getId());

			bfl.setIdRiconciliazione(inc.getIdRiconciliazione());

			bfl.setIdRiconciliazioneOrig(inc.getIdRiconciliazioneOrig());

			GestioneFlussi distinta = inc.getDistintePagamento();

			if (distinta != null){

				String flagIncasso = distinta.getPagamenti().iterator().next().getFlagIncasso();

				bfl.setStatoIncasso(EnumStatoIncasso.getByKey(flagIncasso));

			}

			bfl.setDataValutaAccredito(inc.getDataValuta());

			bfl.setDataContabile(inc.getDataContabile());

			bfl.setImporto(inc.getImporto());

			bfl.setTransRefNumber(inc.getTrn());

			String cro = inc.getRiferimentoBanca();

			bfl.setCodRifOperazione(cro);

			bfl.setTipoOperazione(EnumTipoOperazione.getByCRO(cro));

			bfl.setProgr61(inc.getProgressivo61());

			bfl.setProgr62(inc.getProgressivo62());

			Rendicontazioni rend = inc.getRendicontazioni();

			if (rend != null){

				CasellarioInfo casell = rend.getCasellarioInfo();

				bfl.setIdCasellario(casell.getId());
			}

			Riversamento riv = inc.getRiversamento();

			if (inc.getAnomalia()!= null)
				bfl.setTipoAnomalia(EnumTipoAnomaliaIncasso.valueOf(inc.getAnomalia()));

			if (riv!= null){

				bfl.setIdRiversamento(riv.getId().toString());

				if (riv.getStato() != null)
					bfl.setStatoRiversamento(EnumStatoRiversamento.valueOf(riv.getStato()));

				if (riv.getFlagChiusura()==0)
					bfl.setStatoChiusura(EnumStatoChiusuraRiversamento.APERTA);
				else
					bfl.setStatoChiusura(EnumStatoChiusuraRiversamento.CHIUSA);

				bfl.setNote(riv.getNote());

				bfl.setIBAN(riv.getIban());

				bfl.setCodTribEnte(riv.getCdTrbEnte());
			}
			
			bfl.setIbanAccredito(inc.getIbanAccredito());

			listaNonRiconciliati.add(bfl);

		}

		return listaNonRiconciliati;
	}

	public static List<NDPNonRiconciliatoDTO> populateListNDPNonRiconciliati(List<MovimentiAccredito> movimenti) {

		List<NDPNonRiconciliatoDTO> listaNonRiconciliati = new ArrayList<NDPNonRiconciliatoDTO>();

		for (MovimentiAccredito mov : movimenti){

			NDPNonRiconciliatoDTO ndp = new NDPNonRiconciliatoDTO();

			ndp.setIdIncasso(mov.getId());

			ndp.setIdRiconciliazione(mov.getIdRiconciliazione());

			ndp.setIdRiconciliazioneOrig(mov.getIdRiconciliazioneOrig());


//			GestioneFlussi distinta = mov.getDistintePagamento();
//
//			if (distinta != null){
//
//				String flagIncasso = distinta.getPagamentiCreditore().iterator().next().getFlagIncasso();
//
//				ndp.setStatoIncasso(EnumStatoIncasso.getByKey(flagIncasso));
//
//			}

			ndp.setDataValutaAccredito(mov.getDataValutaBeneficiario());

			ndp.setDataContabile(mov.getDataContabile());

			ndp.setImporto(mov.getImporto());

			ndp.setTransRefNumber(mov.getTrn());

			String cro = mov.getRiferimentoBanca();

			ndp.setCodRifOperazione(cro);

			ndp.setTipoAccredito(EnumTipoAccredito.getByKey(mov.getTipoAccredito()));

			ndp.setStatoRiconciliazione(EnumStatoRiconciliazione.getByShortValue(mov.getFlagRiconciliazione()));

			ndp.setId(mov.getId());

			ndp.setTipoAnomalia(EnumTipoAnomaliaNDP.getByKey(mov.getCodAnomalia()));

			ndp.setIBAN(mov.getIban());

			ndp.setTipoMovimento(EnumTipoMovimento.getByKey(mov.getTipoMovimento()));


			Rendicontazioni rend = mov.getRendicontazioni();

			if (rend != null){

				CasellarioInfo casell = rend.getCasellarioInfo();
				ndp.setIdCasellario(casell.getId());

				if (rend.getCodRendicontazione().equalsIgnoreCase("OPI")) {
					ndp.setContoEvidenza(mov.getContoEvidenza());
					ndp.setNumeroDocumento(mov.getNumeroDocumento());
				} else {
					ndp.setProgr61(mov.getProgr61());
					ndp.setProgr62(mov.getProgr62());
				}

			}

//			ndp.setTipoAnomalia(EnumTipoAnomaliaNDP.valueOf(mov.getCodAnomalia()));
			ndp.setTipoAnomalia(EnumUtils.findByChiave(mov.getCodAnomalia(), EnumTipoAnomaliaNDP.class));

//			if (riv!= null){
//
//				ndp.setIdRiversamento(riv.getId().toString());
//
//				if (riv.getStato() != null)
//					ndp.setStatoRiversamento(EnumStatoRiversamento.valueOf(riv.getStato()));
//
//				if (riv.getFlagChiusura()==0)
//					ndp.setStatoChiusura(EnumStatoChiusuraRiversamento.APERTA);
//				else
//					ndp.setStatoChiusura(EnumStatoChiusuraRiversamento.CHIUSA);
//
//				ndp.setNote(riv.getNote());
//
//				ndp.setIBAN(riv.getIban());
//
//				ndp.setCodTribEnte(riv.getCdTrbEnte());
//			}

			listaNonRiconciliati.add(ndp);

		}

		return listaNonRiconciliati;
	}

	public static List<EventoNDP_DTO> populateListaEventiNDPDTOLight(List<GiornaleEventi> listaEventi) {

		List<EventoNDP_DTO> listaEventiDTO = new ArrayList<EventoNDP_DTO>();

		for(GiornaleEventi evento : listaEventi){

			EventoNDP_DTO eventoDTO = populateEventoNDPDTOLight(evento);

	        listaEventiDTO.add(eventoDTO);

		}

		return listaEventiDTO;
	}

	public static EventoNDP_DTO populateEventoNDPDTO(GiornaleEventi evento) {

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

        eventoDTO.setFaultID(evento.getFaultId()==null?"":evento.getFaultId());

        eventoDTO.setFaultCode(evento.getFaultCode()==null?"":evento.getFaultCode());

        eventoDTO.setFaultSerial(evento.getFaultSerial()+"");

        eventoDTO.setFaultString(evento.getFaultString()==null?"":evento.getFaultString());

        eventoDTO.setFaultDescr(evento.getFaultDescription()==null?"":evento.getFaultDescription());

    	eventoDTO.setOriginalFaultCode(evento.getOriginalFaultCode() == null? "" : evento.getOriginalFaultCode());
    	
     	eventoDTO.setOriginalFaultString(evento.getOriginalFaultString() == null? "" : evento.getOriginalFaultString());
     	
     	eventoDTO.setOriginalFaultDescr(evento.getOriginalFaultDescription() == null? "" : evento.getOriginalFaultDescription()); 
     	
        eventoDTO.setTipoVersamento(evento.getTipoVersamento().name());

        eventoDTO.setInterfaccia(evento.getParametriSpecificiInterfaccia());

        eventoDTO.setIntermediarioPA(evento.getIdStazioneIntermediarioPA());

        eventoDTO.setEsito(evento.getEsito());

        eventoDTO.setIdErogatore(evento.getIdErogatore());

        eventoDTO.setIdFruitore(evento.getIdFruitore());

        eventoDTO.setComponente(evento.getComponente().name());

		return eventoDTO;
	}

	public static EventoNDP_DTO populateEventoNDPDTOLight(GiornaleEventi evento) {

		EventoNDP_DTO eventoDTO = new EventoNDP_DTO();

        eventoDTO.setIdEvento(evento.getId().toString());

        eventoDTO.setIdDominio(evento.getIdDominio());

        eventoDTO.setIuv(evento.getIdUnivocoVersamento());

        eventoDTO.setData(evento.getDataOraEvento());

        eventoDTO.setCodContesto(evento.getCodiceContestoPagamento());

        eventoDTO.setTipo(evento.getTipoEvento());

        eventoDTO.setSottoTipo(evento.getSottoTipoEvento().getDescrizione());

        eventoDTO.setIdPSP(evento.getIdPSP());

        eventoDTO.setEsito(evento.getEsito());

        eventoDTO.setIdErogatore(evento.getIdErogatore());

        eventoDTO.setIdFruitore(evento.getIdFruitore());

        eventoDTO.setComponente(evento.getComponente().name());

		return eventoDTO;
	}


}
