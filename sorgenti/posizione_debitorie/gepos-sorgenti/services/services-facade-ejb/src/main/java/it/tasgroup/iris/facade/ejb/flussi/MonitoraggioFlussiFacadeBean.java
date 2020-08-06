package it.tasgroup.iris.facade.ejb.flussi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.erbweb.web.util.ComboOption;
import it.nch.idp.backoffice.tavolooperativo.ConfermaCartVO;
import it.nch.idp.backoffice.tavolooperativo.ErroreIDPVO;
import it.nch.idp.backoffice.tavolooperativo.ErroriCartVO;
import it.nch.idp.backoffice.tavolooperativo.EsitoCartVO;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.nch.idp.backoffice.tavolooperativo.NotificheCartVO;
import it.nch.idp.backoffice.tavolooperativo.PendenzaErrataVO;
import it.nch.idp.backoffice.tavolooperativo.PendenzeCartVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.profile.IProfileManager;
import it.nch.utility.exception.WrongProfileException;
import it.tasgroup.idp.domain.messaggi.ConfermeCart;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.rs.model.creditore.MovimentoAccredito;
import it.tasgroup.idp.rs.model.creditore.MovimentoText;
import it.tasgroup.idp.rs.model.creditore.RiconciliazioneMovimentoAccreditoInfo;
import it.tasgroup.iris.business.ejb.client.flussi.MonitoraggioFlussiBusinessLocal;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.EsitiCbill;
import it.tasgroup.iris.domain.EsitiNdp;
import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.domain.MovimentiAccredito;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.ChiaveValoreDTO;
import it.tasgroup.iris.dto.exception.CheckSospettoGRException;
import it.tasgroup.iris.dto.flussi.BFLNonRiconciliatoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDispoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.iris.dto.flussi.DettaglioCasellarioDTOLight;
import it.tasgroup.iris.dto.flussi.DettaglioEsitiDTOLight;
import it.tasgroup.iris.dto.flussi.ListaCasellarioDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiCbillDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiNdpDTOLight;
import it.tasgroup.iris.dto.flussi.ListaOperazioniPerEsitoDTOLight;
import it.tasgroup.iris.dto.flussi.NDPNonRiconciliatoDTO;
import it.tasgroup.iris.dto.flussi.RendicontazioniDTO;
import it.tasgroup.iris.dto.flussi.RendicontazioniDTOLight;
import it.tasgroup.iris.facade.ejb.client.flussi.MonitoraggioFlussiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.flussi.MonitoraggioFlussiFacadeRemote;
import it.tasgroup.iris.facade.ejb.flussi.dto.builder.EsitiBonificiRiaccreditoDTOBuilder;
import it.tasgroup.iris.facade.ejb.flussi.dto.builder.MonitoraggioFlussiDTOBuilder;
import it.tasgroup.services.util.enumeration.*;

@Stateless(name = "MonitoraggioFlussiFacade")
public class MonitoraggioFlussiFacadeBean implements MonitoraggioFlussiFacadeLocal, MonitoraggioFlussiFacadeRemote {

//	private static final Logger LOGGER = LogManager.getLogger(MonitoraggioFlussiFacadeBean.class);

	@EJB(name = "MonitoraggioFlussiBusiness")
	private MonitoraggioFlussiBusinessLocal monitoraggioFlussiBusinessBean;
	
	/**
	 *
	 */
	@Override
	public ContainerDTO readCasellarioListLight(ContainerDTO dto, String flusso){

		List<CasellarioDTO> dtos = null;

		List<ListaCasellarioDTOLight> dtosreturn = null;

		if(flusso.equals(PosizioneDebitoriaConstants.FLUSSO_INFORMATIVO)){

			List<CasellarioInfo> listaflussi = monitoraggioFlussiBusinessBean.getListCasellarioInfoByFilterParameters(dto);

			dtos = MonitoraggioFlussiDTOBuilder.populateListCasellarioDTOInfo(listaflussi, false);

			dtosreturn = MonitoraggioFlussiDTOBuilder.populateListListaCasellarioDTOLightInfo(dtos);

		} else {

			List<CasellarioDispo> listaflussi = monitoraggioFlussiBusinessBean.getListCasellarioDispoByFilterParameters(dto);

			dtos = MonitoraggioFlussiDTOBuilder.populateListCasellarioDTODispo(listaflussi, false);

			dtosreturn = MonitoraggioFlussiDTOBuilder.populateListListaCasellarioDTOLightDispo(dtos);
		}

		dto.addAllOutputDTO(dtosreturn);

		return dto;
	}


	@Override
	public DettaglioCasellarioDTOLight readCasellarioById(IProfileManager profileManager, Long id, String flusso){

		CasellarioDTO dto = null;

		DettaglioCasellarioDTOLight dtoreturn = null;

		if(flusso.equals(PosizioneDebitoriaConstants.FLUSSO_INFORMATIVO)){

			CasellarioInfo casellarioInfo = monitoraggioFlussiBusinessBean.getCasellarioInfoById(id);
			
			final String tipoOperatore = profileManager.getCategoria();
			if (EnumCategoriaIntestatario.EN.toString().equals(tipoOperatore)  && !casellarioInfo.getRicevente().equals(profileManager.getCodiceFiscale())) {
				throw new WrongProfileException();
			}

			dto = MonitoraggioFlussiDTOBuilder.populateCasellarioDTOInfo(casellarioInfo, false);

			dtoreturn = MonitoraggioFlussiDTOBuilder.populateDettaglioCasellarioDTOLightInfo((CasellarioInfoDTO)dto);
		} else {
			CasellarioDispo casellarioDispo = monitoraggioFlussiBusinessBean.getCasellarioDispoById(id);

			dto = MonitoraggioFlussiDTOBuilder.populateCasellarioDTODispo(casellarioDispo, false);

			dtoreturn = MonitoraggioFlussiDTOBuilder.populateDettaglioCasellarioDTOLightDispo((CasellarioDispoDTO)dto);
		}

		return dtoreturn;
	}

	@Override
	public MovimentoText readFlussoByIdMovimentoAccredito(Long id) {
		CasellarioInfoDTO casellarioInfoDTO= monitoraggioFlussiBusinessBean.getCasellarioInfoByMovimentoAccreditoId(id);
		return new MovimentoText(casellarioInfoDTO.getNomeSupporto(), new String(casellarioInfoDTO.getFlussoCBI()));
	}



	@Override
	public CasellarioDTO readCasellarioInfoById(IProfileManager profileManager, Long id, boolean isLoadFlussoCBI){

		CasellarioDTO dto = null;

		CasellarioInfo casellarioInfo = monitoraggioFlussiBusinessBean.getCasellarioInfoById(id);
		
		final String tipoOperatore = profileManager.getCategoria();
		if (EnumCategoriaIntestatario.EN.toString().equals(tipoOperatore) && !casellarioInfo.getRicevente().equals(profileManager.getCodiceFiscale())) {
			throw new WrongProfileException();
		}

		dto = MonitoraggioFlussiDTOBuilder.populateCasellarioDTOInfo(casellarioInfo, isLoadFlussoCBI);

		return dto;
	}

	@Override
	public CasellarioDTO readCasellarioDispoById(Long id, boolean isLoadFlussoCBI){

		CasellarioDTO dto = null;

		CasellarioDispo casellarioDispo = monitoraggioFlussiBusinessBean.getCasellarioDispoById(id);

		dto = MonitoraggioFlussiDTOBuilder.populateCasellarioDTODispo(casellarioDispo, isLoadFlussoCBI);

		return dto;
	}


	@Override
	public RendicontazioniDTOLight readRendicontazioneById(Long id){

		Rendicontazioni rendicontazioni = monitoraggioFlussiBusinessBean.getRendicontazioniById(id);

		RendicontazioniDTOLight dto = MonitoraggioFlussiDTOBuilder.populateRendicontazioniDTOLight(rendicontazioni);

		return dto;
	}

	@Override
	public ContainerDTO getRendicontazioniRiversamento(ContainerDTO containerDTO){

		List<Rendicontazioni> rendicontazioni = monitoraggioFlussiBusinessBean.getRendicontazioniRiversamento(containerDTO);

		List<RendicontazioniDTO> dtos = EsitiBonificiRiaccreditoDTOBuilder.populateListRendicontazioniDTO(rendicontazioni);

		containerDTO.setOutputDTOList(dtos);

		return containerDTO;
	}

	@Override
	public DettaglioEsitiDTOLight readEsitoByIdAndCodRendicontazione(Long id, String codRendicontazione){

		DettaglioEsitiDTOLight dto = null;

		if(codRendicontazione.equals(EnumCodRendicontazione.BB.getChiave())){
			EsitiBb esitiBb =  monitoraggioFlussiBusinessBean.getEsitiBbById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(esitiBb);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.SL.getChiave())){
			EsitiRct esitiRct =  monitoraggioFlussiBusinessBean.getEsitiRctById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(esitiRct);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.RH.getChiave())){
			IncassiBonificiRh incassiBonificiRh =  monitoraggioFlussiBusinessBean.getIncassiBonificiRhById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(incassiBonificiRh);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.EP.getChiave()) || codRendicontazione.equals(EnumCodRendicontazione.EX.getChiave())){
			EsitiBonificiRiaccredito esitiBonificiRiaccredito = monitoraggioFlussiBusinessBean.getEsitiBonificiRiaccreditoById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(esitiBonificiRiaccredito);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.IR.getChiave())){
			Rid rid =  monitoraggioFlussiBusinessBean.getRidById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(rid);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.AL.getChiave())){
			AllineamentiElettroniciArchivi aea =  monitoraggioFlussiBusinessBean.getAEAById(id);
			AllineamentiElettroniciArchivi aea_ric =  monitoraggioFlussiBusinessBean.getAEAById(Long.parseLong(aea.getIdDisposizioneOrig()));
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(aea, aea_ric.getDistintePagamento().getCodTransazione());
		} else if(codRendicontazione.equals(EnumCodRendicontazione.CBILL.getChiave())){
			EsitiCbill cbill =  monitoraggioFlussiBusinessBean.getEsitiCbillById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(cbill);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.NDP.getChiave())){
			EsitiNdp ndp =  monitoraggioFlussiBusinessBean.getEsitiNdpById(id);
			dto = MonitoraggioFlussiDTOBuilder.populateDettaglioEsitiDTOLight(ndp);
		}


		return dto;
	}

	@Override
	public ContainerDTO readEsitiByIdRendicontazione(ContainerDTO containerDTO){

		List<ListaEsitiDTOLight> dto = null;

		String codRendicontazione = (String) containerDTO.getInputDTOList().get(1);

		if(codRendicontazione.equals(EnumCodRendicontazione.BB.getChiave())){
			List<EsitiBb> esitiBb =  monitoraggioFlussiBusinessBean.getEsitiBbByIdRendicontazione(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiBb(esitiBb);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.SL.getChiave())){
			List<EsitiRct> esitiRct =  monitoraggioFlussiBusinessBean.getEsitiRctByIdRendicontazione(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiRct(esitiRct);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.RH.getChiave())){
			List<IncassiBonificiRh> incassiBonificiRh =  monitoraggioFlussiBusinessBean.getIncassiBonificiRhByIdRendicontazione(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightIncassiBonificiRh(incassiBonificiRh);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.EP.getChiave()) || codRendicontazione.equals(EnumCodRendicontazione.EX.getChiave())){
			List<EsitiBonificiRiaccredito> esitiBonificiRiaccredito =  monitoraggioFlussiBusinessBean.getEsitiBonificiRiaccreditoByIdRend(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiBonificiRiaccredito(esitiBonificiRiaccredito);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.IR.getChiave())){
			List<Rid> rid =  monitoraggioFlussiBusinessBean.getEsitiRidByIdRendicontazioneAndCausaleNotEquals(containerDTO, EnumCausaleRid.D50000.getChiave());
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightRID(rid);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.AL.getChiave())){
			List<AllineamentiElettroniciArchivi> aea =  monitoraggioFlussiBusinessBean.getEsitiAEAByIdRendicontazioneAndCausaleNotIn(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightAEA(aea);
		} else if(codRendicontazione.equals(EnumCodRendicontazione.OPI.getChiave())){
			List<MovimentiAccredito> movimentiDaGiornaleDiCassa = monitoraggioFlussiBusinessBean.getMovimentiDaGiornaleDiCassa(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightOPI(movimentiDaGiornaleDiCassa);
        }

		containerDTO.addAllOutputDTO(dto);

		return containerDTO;
	}

	@Override
	public ContainerDTO readEsitiByIdRendicontazioneAndTipoesitoRct(ContainerDTO containerDTO){

		List<ListaEsitiDTOLight> dto = null;

		List<EsitiRct> esitiRct =  monitoraggioFlussiBusinessBean.getEsitiRctByIdRendicontazioneAndTipoEsito(containerDTO);
		dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiRct(esitiRct);

		containerDTO.addAllOutputDTO(dto);

		return containerDTO;
	}

        @Override
	public ContainerDTO readEsitiCbillByIdRendicontazione(ContainerDTO containerDTO){

		List<ListaEsitiCbillDTOLight> dto = null;

		List<EsitiCbill> esitiCbill =  monitoraggioFlussiBusinessBean.getEsitiCbillByIdRendicontazione(containerDTO);
		dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiCbill(esitiCbill);

		containerDTO.addAllOutputDTO(dto);

		return containerDTO;
	}

        @Override
		public ContainerDTO readEsitiNdpByIdRendicontazione(ContainerDTO containerDTO){

			List<ListaEsitiNdpDTOLight> dto = null;

			List<EsitiNdp> esitiCbill =  monitoraggioFlussiBusinessBean.getEsitiNdpByIdRendicontazione(containerDTO);
			dto = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiNdp(esitiCbill);

			containerDTO.addAllOutputDTO(dto);

			return containerDTO;
		}

        @Override
    	public List<ListaEsitiNdpDTOLight> readEsitiNdpByIdFlusso(String idRiconciliazione){

    		List<EsitiNdp> esitiNDP =  monitoraggioFlussiBusinessBean.getEsitiNdpByIdFlusso(idRiconciliazione);

    		List<ListaEsitiNdpDTOLight> dtos = MonitoraggioFlussiDTOBuilder.populateListaEsitiDTOLightEsitiNdp(esitiNDP);

    		return dtos;
    	}

        @Override
        public List<ComboOption> readMittentiList(){
            return readMittentiList(null);
        }
        
        @Override
        public List<ComboOption> readMittentiList(String ricevente){
            List<ComboOption> dtos = null;
            List<Object[]> listaMittenti = null;
            if (ricevente == null)
            	listaMittenti = monitoraggioFlussiBusinessBean.getListaMittenti();
            else
            	listaMittenti = monitoraggioFlussiBusinessBean.getListaMittenti(ricevente);	

            dtos = MonitoraggioFlussiDTOBuilder.populateListComboOption(listaMittenti);

            return dtos;
        }

        @Override
	public List<ComboOption> readRiceventiList(){
            List<ComboOption> dtos = null;

            List<Object[]> listaRiceventi = monitoraggioFlussiBusinessBean.getListaRiceventi();

            dtos = MonitoraggioFlussiDTOBuilder.populateListComboOption(listaRiceventi);

            return dtos;
	}

    @Override
    public List<ComboOption> readSystemIdsList() {
            List<ComboOption> dtos = null;

            List<Object[]> lista = monitoraggioFlussiBusinessBean.readSystemIdsList();

            dtos = MonitoraggioFlussiDTOBuilder.populateListComboOption(lista);

            return dtos;
    }

    @Override
    public List<ComboOption> readApplicationIdsList() {
            List<ComboOption> dtos = null;

            List<Object[]> lista = monitoraggioFlussiBusinessBean.readApplicationIdsList();

            dtos = MonitoraggioFlussiDTOBuilder.populateListComboOption(lista);

            return dtos;
    }

    @Override
    public List<ComboOption> readTiOperationsList() {
            List<ComboOption> dtos = null;

            List<Object[]> lista = new ArrayList<Object[]>(); //monitoraggioFlussiBusinessBean.readTiOperationsList();

            EnumOperazioniPagamento[] enumOperPag=EnumOperazioniPagamento.values();
            for (int i=0;i< enumOperPag.length;i++) {
            	Object[] obj = new Object[2];
            	obj[0]=enumOperPag[i].getChiave();
            	obj[1]=enumOperPag[i].getChiave();
            	lista.add(obj);
            }
            dtos = MonitoraggioFlussiDTOBuilder.populateListComboOption(lista);

            return dtos;
    }

    @Override
    public ContainerDTO readListaOperazioniEsitiByFilters(ContainerDTO containerDTO) {
		List<ListaOperazioniPerEsitoDTOLight> dto = null;

		List<Object[]> listaOperazioni =  monitoraggioFlussiBusinessBean.readListaOperazioniEsitiByFilters(containerDTO);

		dto = MonitoraggioFlussiDTOBuilder.populateListaOperazioniPerEsitoDTOLight(listaOperazioni);

		containerDTO.addAllOutputDTO(dto);

		return containerDTO;
    }

    @Override
    public ContainerDTO readListaDettaglioOperazioniEsitiByFilters(ContainerDTO containerDTO) {

    	List<ListaOperazioniPerEsitoDTOLight> dto = null;

		List<PagamentiOnline> listaOperazioni =  monitoraggioFlussiBusinessBean.readListaDettaglioOperazioniEsitiByFilters(containerDTO);

		dto = MonitoraggioFlussiDTOBuilder.populateListaDettaglioOperazioniPerEsitoDTOLight(listaOperazioni);

		containerDTO.addAllOutputDTO(dto);

		return containerDTO;
    }

    @Override
    public ContainerDTO readListBFLNonRiconciliati(ContainerDTO dto) {

		List<IncassiBonificiRh> listaflussi = monitoraggioFlussiBusinessBean.getListBFLNonRiconciliati(dto);

		List<BFLNonRiconciliatoDTO> dtos = MonitoraggioFlussiDTOBuilder.populateListBFLNonRiconciliati(listaflussi);

		dto.addAllOutputDTO(dtos);

		return dto;

    }

    @Override
    public ContainerDTO readListNDPNonRiconciliati(ContainerDTO dto){

		List<MovimentiAccredito> listaflussi = monitoraggioFlussiBusinessBean.getListNDPNonRiconciliati(dto);

		List<NDPNonRiconciliatoDTO> dtos = MonitoraggioFlussiDTOBuilder.populateListNDPNonRiconciliati(listaflussi);

		dto.addAllOutputDTO(dtos);

		return dto;

    }


	@Override
	public BFLNonRiconciliatoDTO saveIncassi(BFLNonRiconciliatoDTO dett) {

		IncassiBonificiRh saved = monitoraggioFlussiBusinessBean.saveIncassi(dett.getIdIncasso(), dett.getNote(), dett.getStatoChiusura());

		List<IncassiBonificiRh> listaflussi = new ArrayList<IncassiBonificiRh>();

		listaflussi.add(saved);

		List<BFLNonRiconciliatoDTO> dtos = MonitoraggioFlussiDTOBuilder.populateListBFLNonRiconciliati(listaflussi);

		return dtos.get(0);
	}

	@Override
	public void riversa(IProfileManager profile, String selectedIBAN, String[] daRiversare, boolean isGiaRiversato) throws CheckSospettoGRException{

		monitoraggioFlussiBusinessBean.riversa(profile, selectedIBAN, daRiversare, isGiaRiversato);

	}

	@Override
	public void riconciliaMovimentoAccredito(EnumTipoAccredito tipoAccredito, String idRiconciliazione, Long idMov) {

		monitoraggioFlussiBusinessBean.riconciliaMovimentoAccredito(tipoAccredito, idRiconciliazione, idMov);

	}

	@Override
	public Long riconciliaMovimentoAccredito(Long idMov) {
		MovimentiAccredito movimentiAccredito = monitoraggioFlussiBusinessBean.riconciliaMovimentoAccredito(idMov);
		return movimentiAccredito != null ? movimentiAccredito.getId() : -1;
	}

	@Override
	public MovimentoAccredito readMovimentoById(Long idMov) {
		MovimentiAccredito nonRiconciliato = monitoraggioFlussiBusinessBean.riconciliaMovimentoAccredito(idMov);
		return new MovimentoAccredito(nonRiconciliato.getId(), nonRiconciliato.getDataValutaBeneficiario(), nonRiconciliato.getDataContabile(),
				nonRiconciliato.getTrn(), nonRiconciliato.getImporto(), EnumUtils.findByChiave(nonRiconciliato.getCodAnomalia(), EnumTipoAnomaliaNDP.class), EnumUtils.findByChiave(nonRiconciliato.getTipoAccredito(), EnumTipoAccredito.class),
				nonRiconciliato.getIban(), nonRiconciliato.getIdRiconciliazione());
	}

	@Override
	public void riconciliaAccreditoCumulativoConSingolo(Long idMov, Long idEsitoNdp, String codTransazione) {

		monitoraggioFlussiBusinessBean.riconciliaMovimentoAccredito(idMov);

		monitoraggioFlussiBusinessBean.riconciliaEsitiNDP(codTransazione, idEsitoNdp);

	}

	@Override
	public ListaEsitiNdpDTOLight getEsitiNDPById(Long id) {

		EsitiNdp esito = monitoraggioFlussiBusinessBean.getEsitiNdpById(id);

		ListaEsitiNdpDTOLight esitoDTO = MonitoraggioFlussiDTOBuilder.populateEsitiDTOLightEsitiNdp(esito);

		return esitoDTO;
	}

	@Override
	public List<ChiaveValoreDTO> groupByDescrizioneEsitoPendenza(ContainerDTO dtoIn){

		List<Object[]> results = monitoraggioFlussiBusinessBean.groupByDescrizioneEsitoPendenza(dtoIn);

		List<ChiaveValoreDTO> resultList = new ArrayList<ChiaveValoreDTO>();

		for (Object[] record : results)

			resultList.add(new ChiaveValoreDTO((String)record[0], BigDecimal.valueOf(((Number)record[1]).longValue())));

		return resultList;

	}

	@Override
	public MovimentoAccredito updateMovimentoAccredito(RiconciliazioneMovimentoAccreditoInfo riconciliazioneMovimentoAccreditoInfo) {
		@SuppressWarnings("unused")
		MovimentiAccredito movimentiAccredito = monitoraggioFlussiBusinessBean.riconciliaMovimentoAccredito(riconciliazioneMovimentoAccreditoInfo.getId());
		// trasformare da MovimentiAccredito in MovimentoAccredito??
		return null;
	}


	/**
	 * Metodi per il monitoraggio flussi allineamento pendenze / notifiche pagamento (Tavolo operativo)
	 */
	
	@Override
	public ContainerDTO getListaMessaggiAllineamentoPendenzeCaricatiStorico(ContainerDTO dtoIn) {
		List<PendenzeCart> pendenzeCart = monitoraggioFlussiBusinessBean.getListaMessaggiAllineamentoPendenzeCaricatiStorico(dtoIn);
		buildPendenzeCart(dtoIn, pendenzeCart, true);
		return dtoIn;
	}

	@Override
	public ContainerDTO getListaMessaggiAllineamentoPendenzeCaricati(ContainerDTO dtoIn) {
		List<PendenzeCart> pendenzeCart = monitoraggioFlussiBusinessBean.getListaMessaggiAllineamentoPendenzeCaricati(dtoIn);
		buildPendenzeCart(dtoIn, pendenzeCart);
		return dtoIn;
	}

	private void buildPendenzeCart(ContainerDTO dtoIn, List<PendenzeCart> pendenzeCart) {
		buildPendenzeCart (dtoIn, pendenzeCart, false);
	}

	private void buildPendenzeCart(ContainerDTO dtoIn, List<PendenzeCart> pendenzeCart, boolean isStorico) {
		List<PendenzeCartVO> pendenzeCartVO = new ArrayList<PendenzeCartVO>();

		for (PendenzeCart p:pendenzeCart) {
			int presenza_xml = getPresenza_xml(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys(), isStorico);
			PendenzeCartVO pvo =  new PendenzeCartVO(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys(), p.getStato(), 
					p.getTimestampRicezione(),new Long( p.getNumPendenze()), p.getTipoMessaggio(), p.getNumPendDeleted(), p.getTipoTributo(), 
					p.getTipoOperazione(), p.getTrt_senderid(), p.getTrt_sendersys(), presenza_xml);
			pendenzeCartVO.add(pvo);
			
			// Aggiungo il numero pendenze Caricate
			
			Long numeroPendenzeCaricate = monitoraggioFlussiBusinessBean.countPendenzeElaborate(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys(), isStorico);
			pvo.setNumeroPendenzeCaricate(numeroPendenzeCaricate);
			
			// Aggiungo il numero pendenze
			Long numeroPendenze = monitoraggioFlussiBusinessBean.countPendenze(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys(), isStorico);
			pvo.setNumeroPendenze(numeroPendenze);
		}

		dtoIn.setOutputDTO(pendenzeCartVO);
	}
	
	private int getPresenza_xml(String e2emsgid, String senderid, String sendersys) {
		return getPresenza_xml(e2emsgid, senderid, sendersys, false);
	}
	
	private int getPresenza_xmlStorico(String e2emsgid, String senderid, String sendersys) {
		return getPresenza_xml(e2emsgid, senderid, sendersys, true);
	}
	
	private int getPresenza_xml(String e2emsgid, String senderid, String sendersys, boolean isStorico) {
		EsitiCart ecartvo = monitoraggioFlussiBusinessBean.getMessaggioEsitoAllineamentoPendenzeByKey(e2emsgid, senderid, sendersys, isStorico);
		int res = 0;
		if (ecartvo != null) {
			if (ecartvo.getEsitoXml() != null && ecartvo.getEsitoXml().length > 0)
				res = 1;
		}
		return res;
	}
	
	@Override
	public PendenzeCartVO getMessaggioAllineamentoPendenzeByKey(
			String e2emsgid, String senderid, String sendersys) {
		PendenzeCartVO pvo = null;
		PendenzeCart p = monitoraggioFlussiBusinessBean.getMessaggioAllineamentoPendenzeByKey(e2emsgid, senderid, sendersys);
		if (p != null) {
			int presenza_xml = getPresenza_xml(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys());
			pvo =  new PendenzeCartVO(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys(), 
					p.getStato(), p.getTimestampRicezione(),new Long( p.getNumPendenze()), p.getTipoMessaggio(), 
					p.getNumPendDeleted(), p.getTipoTributo(), p.getTipoOperazione(), p.getTrt_senderid(), p.getTrt_sendersys(), presenza_xml);

			// Calcolo del numero pendenze Caricate
			Long numeroPendenzeCaricate = monitoraggioFlussiBusinessBean.countPendenzeElaborate(e2emsgid, senderid, sendersys);
			pvo.setNumeroPendenzeCaricate(numeroPendenzeCaricate);

			// Calcolo del numero pendenze 
			Long numeroPendenze = monitoraggioFlussiBusinessBean.countPendenze(e2emsgid, senderid, sendersys);
			pvo.setNumeroPendenze(numeroPendenze);
		} 
		return pvo;

	}

	@Override
	public PendenzeCartVO getMessaggioAllineamentoPendenzeByKeyStorico(
			String e2emsgid, String senderid, String sendersys) {
		PendenzeCartVO pvo = null;
		PendenzeCart p = monitoraggioFlussiBusinessBean.getMessaggioAllineamentoPendenzeByKeyStorico(e2emsgid, senderid, sendersys);
		if (p != null) {
			int presenza_xml = getPresenza_xmlStorico(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys());
			pvo =  new PendenzeCartVO(p.getPk().getE2emsgid(), p.getPk().getSenderid(), p.getPk().getSendersys(), 
					p.getStato(), p.getTimestampRicezione(),new Long( p.getNumPendenze()), p.getTipoMessaggio(), 
					p.getNumPendDeleted(), p.getTipoTributo(), p.getTipoOperazione(), p.getTrt_senderid(), p.getTrt_sendersys(), presenza_xml);

			// Calcolo del numero pendenze Caricate
			Long numeroPendenzeCaricate = monitoraggioFlussiBusinessBean.countPendenzeElaborateStorico(e2emsgid, senderid, sendersys);
			pvo.setNumeroPendenzeCaricate(numeroPendenzeCaricate);

			// Calcolo del numero pendenze 
			Long numeroPendenze = monitoraggioFlussiBusinessBean.countPendenzeStorico(e2emsgid, senderid, sendersys);
			pvo.setNumeroPendenze(numeroPendenze);
		} 
		return pvo;

	}

	@Override
	public EsitoCartVO getMessaggioEsitoAllineamentoPendenzeByKey(
			String e2emsgid, String senderid, String sendersys) {

		EsitiCart e = monitoraggioFlussiBusinessBean.getMessaggioEsitoAllineamentoPendenzeByKey(e2emsgid, senderid, sendersys);
		

		if (e!=null) {
			int presenza_xml = (e.getEsitoXml() != null && e.getEsitoXml().length > 0) ? 1 : 0;
			EsitoCartVO evo =  new EsitoCartVO(e.getPk().getE2emsgid(),e.getPk().getSenderid(),e.getPk().getSendersys(),e.getStato(),
					e.getTimestampInvio(), presenza_xml);
			return evo;
		}

		return null;

	}
	
	@Override
	public EsitoCartVO getMessaggioEsitoAllineamentoPendenzeByKeyStorico(
			String e2emsgid, String senderid, String sendersys) {

		EsitiCart e = monitoraggioFlussiBusinessBean.getMessaggioEsitoAllineamentoPendenzeByKeyStorico(e2emsgid, senderid, sendersys);

		if (e!=null) {
			int presenza_xml = (e.getEsitoXml() != null && e.getEsitoXml().length > 0) ? 1 : 0;
			EsitoCartVO evo =  new EsitoCartVO(e.getPk().getE2emsgid(),e.getPk().getSenderid(),e.getPk().getSendersys(),e.getStato(),
					e.getTimestampInvio(), presenza_xml);
			return evo;
		}

		return null;

	}


	@Override
	public List<PendenzaErrataVO> listPendenzeErrate(String e2emsgid,
			String senderid, String sendersys) {

		List<EsitiPendenza> pendenzeErrate = monitoraggioFlussiBusinessBean.listPendenzeErrate(e2emsgid, senderid, sendersys);
		List<PendenzaErrataVO> result = new ArrayList<PendenzaErrataVO>();

		for(EsitiPendenza ep: pendenzeErrate)  {
			if (ep.getErroriEsitiPendenzaCollection()!=null)  {
				for( ErroriEsitiPendenza epDettaglioErrore : ep.getErroriEsitiPendenzaCollection()) {
					PendenzaErrataVO epvo = new PendenzaErrataVO();
					epvo.setIdPendenzaEnte(ep.getIdPendenzaEnte());
					epvo.setCodice(epDettaglioErrore.getCodice());
					epvo.setDescrizioneErrore(epDettaglioErrore.getDescrizioneErrore());
					result.add(epvo);
				}
			}

		}

		return result;
	}

	@Override
	public List<PendenzaErrataVO> listPendenzeErrateStorico(String e2emsgid,
			String senderid, String sendersys) {

		List<EsitiPendenza> pendenzeErrate = monitoraggioFlussiBusinessBean.listPendenzeErrateStorico(e2emsgid, senderid, sendersys);
		List<PendenzaErrataVO> result = new ArrayList<PendenzaErrataVO>();

		for(EsitiPendenza ep: pendenzeErrate)  {
			if (ep.getErroriEsitiPendenzaCollection()!=null)  {
				for( ErroriEsitiPendenza epDettaglioErrore : ep.getErroriEsitiPendenzaCollection()) {
					PendenzaErrataVO epvo = new PendenzaErrataVO();
					epvo.setIdPendenzaEnte(ep.getIdPendenzaEnte());
					epvo.setCodice(epDettaglioErrore.getCodice());
					epvo.setDescrizioneErrore(epDettaglioErrore.getDescrizioneErrore());
					result.add(epvo);
				}
			}

		}

		return result;
	}

	@Override
	public byte[] downloadMessaggioAllineamentoPendenze(String e2emsgid, String senderid,
			String sendersys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioAllineamentoPendenze( e2emsgid,  senderid,  sendersys);
	}
	
	@Override
	public byte[] downloadMessaggioAllineamentoPendenzeStorico(String e2emsgid, String senderid,
			String sendersys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioAllineamentoPendenzeStorico( e2emsgid,  senderid,  sendersys);
	}


	@Override
	public byte[] downloadMessaggioEsitoAllineamentoPendenze(String e2emsgid,
			String senderid, String sendersys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioEsitoAllineamentoPendenze( e2emsgid,  senderid,  sendersys);
	}

	@Override
	public byte[] downloadMessaggioEsitoAllineamentoPendenzeStorico(String e2emsgid,
			String senderid, String sendersys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioEsitoAllineamentoPendenzeStorico( e2emsgid,  senderid,  sendersys);
	}

	@Override
	public ContainerDTO getListaMessaggiAllineamentoPendenzeScartati(
			ContainerDTO dtoIn) {

		List<ErroriCart> erroriCart = monitoraggioFlussiBusinessBean.getListaMessaggiAllineamentoPendenzeScartati(dtoIn);
		List<ErroriCartVO> erroriCartVO = new ArrayList<ErroriCartVO>();

		for (ErroriCart e:erroriCart) {
			ErroriCartVO evo =  new ErroriCartVO( e.getTsInserimento(),e.getTipoMessaggio(), e.getStatoErrore(), e.getIdErroreCart());
			erroriCartVO.add(evo);
		}

		dtoIn.setOutputDTO(erroriCartVO);
		return dtoIn;

	}


	@Override
	public byte[] downloadMessaggioAllineamentoPendenzeScartato(String idMessaggio) {

		return monitoraggioFlussiBusinessBean.downloadMessaggioAllineamentoPendenzeScartato( idMessaggio );

	}


	@Override
	public List<ErroreIDPVO> listEsitiScartati(String e2emsgid,
			String senderid, String sendersys) {

		List<ErroriIdp> messaggiScartati = monitoraggioFlussiBusinessBean.listEsitiScartati(e2emsgid, senderid, sendersys);
		List<ErroreIDPVO> result = new ArrayList<ErroreIDPVO>();

		for (ErroriIdp e: messaggiScartati) {
			ErroreIDPVO evo = new  ErroreIDPVO(e.getPk().getE2emsgid(), e.getPk().getSenderid(), e.getPk().getSendersys(), e.getReceiverid(), e.getReceiversys(), e.getPk().getStato(), e.getDescrizioneStato(), e.getPrVersione(), e.getOpInserimento(), e.getTsInserimento());
			result.add(evo);
		}

		return result;

	}
	
	@Override
	public List<ErroreIDPVO> listEsitiScartatiStorico(String e2emsgid,
			String senderid, String sendersys) {

		List<ErroriIdp> messaggiScartati = monitoraggioFlussiBusinessBean.listEsitiScartatiStorico(e2emsgid, senderid, sendersys);
		List<ErroreIDPVO> result = new ArrayList<ErroreIDPVO>();

		for (ErroriIdp e: messaggiScartati) {
			ErroreIDPVO evo = new  ErroreIDPVO(e.getPk().getE2emsgid(), e.getPk().getSenderid(), e.getPk().getSendersys(), e.getReceiverid(), e.getReceiversys(), e.getPk().getStato(), e.getDescrizioneStato(), e.getPrVersione(), e.getOpInserimento(), e.getTsInserimento());
			result.add(evo);
		}

		return result;

	}


	@Override
	public ContainerDTO getListaMessaggiNotificaPagamentoScartati(
			ContainerDTO dtoIn) {

		List<ErroriIdp> messaggiScartati = monitoraggioFlussiBusinessBean.listEsitiScartatiPaginated(dtoIn);
		List<ErroreIDPVO> result = new ArrayList<ErroreIDPVO>();

		for (ErroriIdp e: messaggiScartati) {
			ErroreIDPVO evo = new  ErroreIDPVO(e.getPk().getE2emsgid(), e.getPk().getSenderid(), e.getPk().getSendersys(), e.getReceiverid(), e.getReceiversys(), e.getPk().getStato(), e.getDescrizioneStato(), e.getPrVersione(), e.getOpInserimento(), e.getTsInserimento());
			result.add(evo);
		}

		dtoIn.setOutputDTO(result);
		return dtoIn;

	}


	@Override
	public byte[] downloadMessaggioNotificaScartato(String e2emsgid) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioNotificaScartato(e2emsgid);
	}


	@Override
	public ContainerDTO getListaMessaggiNotificaCaricati(ContainerDTO dtoIn) {
		List<NotificheCart> notificheCart = monitoraggioFlussiBusinessBean.getListaMessaggiNotificaCaricati(dtoIn);
		List<NotificheCartVO> notificheCartVO = new ArrayList<NotificheCartVO>();

		for (NotificheCart n:notificheCart) {
				final ConfermaCartVO confermaCartVO = getMessaggioConfermaNotificaByKey(n.getId().getE2emsgid(), n.getId().getReceiverid(), n.getId().getReceiversys());
				NotificheCartVO pvo =  new NotificheCartVO(n.getId().getE2emsgid(),n.getId().getReceiverid(),n.getId().getReceiversys(), n.getStato(),n.getTimestampInvio(), n.getCdTrbEnte(),n.getTentativi(), confermaCartVO);
				pvo.setSenderId(n.getSenderid());
				pvo.setDeTrb(n.getTributoente().getDeTrb());
				pvo.setTipoNotifica(n.getTipoNotifica());
				List<CfgNotificaPagamento> lnp = n.getTributoente().getCfgNotificaPagamentos();
				boolean found = false;
				for (int i = 0; i < lnp.size() && !found; i++)  {
					CfgNotificaPagamento np = lnp.get(i);
					if (np.getTipoNotifica().equals(n.getTipoNotifica()))
						pvo.setTipoFormatoNotifica(np.getFormatoNotifica());
				}
				
				notificheCartVO.add(pvo);
		}

		dtoIn.setOutputDTO(notificheCartVO);
		return dtoIn;
	}
	
	@Override
	public ContainerDTO getListaMessaggiNotificaCaricatiStorico(ContainerDTO dtoIn) {
		List<NotificheCart> notificheCart = monitoraggioFlussiBusinessBean.getListaMessaggiNotificaCaricatiStorico(dtoIn);
		List<NotificheCartVO> notificheCartVO = new ArrayList<NotificheCartVO>();

		for (NotificheCart n:notificheCart) {
				final ConfermaCartVO confermaCartVO = getMessaggioConfermaNotificaByKeyStorico(n.getId().getE2emsgid(), n.getId().getReceiverid(), n.getId().getReceiversys());
				NotificheCartVO pvo =  new NotificheCartVO(n.getId().getE2emsgid(),n.getId().getReceiverid(),n.getId().getReceiversys(), n.getStato(),n.getTimestampInvio(), n.getCdTrbEnte(),n.getTentativi(), confermaCartVO);
				if(n.getTributoente() != null) {
					pvo.setDeTrb(n.getTributoente().getDeTrb());
					List<CfgNotificaPagamento> lnp = n.getTributoente().getCfgNotificaPagamentos();
					boolean found = false;
					for (int i = 0; i < lnp.size() && !found; i++)  {
						CfgNotificaPagamento np = lnp.get(i);
						if (np.getTipoNotifica().equals(n.getTipoNotifica()))
							pvo.setTipoFormatoNotifica(np.getFormatoNotifica());
					}
				}
				
				pvo.setTipoNotifica(n.getTipoNotifica());
				notificheCartVO.add(pvo);
		}

		dtoIn.setOutputDTO(notificheCartVO);
		return dtoIn;
	}


	@Override
	public NotificheCartVO getMessaggioNotificaByKey(String e2emsgid, String receiverid, String receiversys) {

		NotificheCart n= monitoraggioFlussiBusinessBean.getMessaggioNotificaByKey(e2emsgid, receiverid, receiversys);

		if (n!=null) {
			NotificheCartVO nvo =  new NotificheCartVO(n.getId().getE2emsgid(),n.getId().getReceiverid(),n.getId().getReceiversys(),n.getStato(),n.getTimestampInvio(), n.getCdTrbEnte(),n.getTentativi());
			return nvo;
		}

		return null;
	}

	@Override
	public NotificheCartVO getMessaggioNotificaByKeyStorico(String e2emsgid, String receiverid, String receiversys) {

		NotificheCart n= monitoraggioFlussiBusinessBean.getMessaggioNotificaByKeyStorico(e2emsgid, receiverid, receiversys);

		if (n!=null) {
			NotificheCartVO nvo =  new NotificheCartVO(n.getId().getE2emsgid(),n.getId().getReceiverid(),n.getId().getReceiversys(),n.getStato(),n.getTimestampInvio(), n.getCdTrbEnte(),n.getTentativi());
			return nvo;
		}

		return null;
	}

	@Override
	public ConfermaCartVO getMessaggioConfermaNotificaByKey(String e2emsgid,
			String receiverid, String receiversys) {

		ConfermeCart n = monitoraggioFlussiBusinessBean.getMessaggioConfermaNotificaByKey(e2emsgid, receiverid, receiversys);

		if (n!=null) {
			ConfermaCartVO ccvo =  new ConfermaCartVO(n.getId().getE2emsgid(),n.getId().getSenderid(),n.getId().getSendersys(),n.getStato(),n.getReceiverid(),n.getTimestampRicezione());
			return ccvo;
		}

		return null;

	}
	
	@Override
	public ConfermaCartVO getMessaggioConfermaNotificaByKeyStorico(String e2emsgid,
			String receiverid, String receiversys) {

		ConfermeCart n = monitoraggioFlussiBusinessBean.getMessaggioConfermaNotificaByKeyStorico(e2emsgid, receiverid, receiversys);

		if (n!=null) {
			ConfermaCartVO ccvo =  new ConfermaCartVO(n.getId().getE2emsgid(),n.getId().getSenderid(),n.getId().getSendersys(),n.getStato(),n.getReceiverid(),n.getTimestampRicezione());
			return ccvo;
		}

		return null;

	}


	@Override
	public byte[] downloadMessaggioNotificaPagamento(String e2emsgid,
			String receiverid, String receiversys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioNotificaPagamento(e2emsgid, receiverid, receiversys);
	}
	
	@Override
	public byte[] downloadMessaggioNotificaPagamentoStorico(String e2emsgid,
			String receiverid, String receiversys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioNotificaPagamentoStorico(e2emsgid, receiverid, receiversys);
	}


	@Override
	public byte[] downloadMessaggioConfermaNotificaPagamento(String e2emsgid,
			String senderid, String sendersys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioConfermaNotificaPagamento(e2emsgid, senderid, sendersys);
	}
	
	@Override
	public byte[] downloadMessaggioConfermaNotificaPagamentoStorico(String e2emsgid,
			String senderid, String sendersys) {
		return monitoraggioFlussiBusinessBean.downloadMessaggioConfermaNotificaPagamentoStorico(e2emsgid, senderid, sendersys);
	}


	@Override
	public void updateMessagesNotSent(String[] messaggi) {
		monitoraggioFlussiBusinessBean.updateMessageNotSent(messaggi);
		
	}
	
	@Override
	public void updateMessagesNotSentStorico(String[] messaggi) {
		monitoraggioFlussiBusinessBean.updateMessageNotSent(messaggi, true);
	}
	
	@Override
	public void updateNotificheNotSent(String[] messaggi) {
		monitoraggioFlussiBusinessBean.updateNotificheNotSent(messaggi);
		
	}


	@Override
	public int updateAllNotificheNotSent(FilterVO filter) {
		return monitoraggioFlussiBusinessBean.updateAllNotificheNotSent(filter);
	}
	
	@Override
	public int updateAllMessageNotSent(FilterVO filter) {
		return monitoraggioFlussiBusinessBean.updateAllMessageNotSent(filter);
	}

	@Override
	public List<String> listIbanAccredito() {
		return monitoraggioFlussiBusinessBean.listIbanAccredito();
	}

}
