package it.tasgroup.iris.facade.ejb.anagrafica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.idp.posizionedebitoria.EntePosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.TipoTributoPosizioneDebitoriaVO;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.nch.is.fo.enti.TipologiaEnti;
import it.nch.is.fo.enti.TipologiaEntiCommon;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IbanEnti;
import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.TributoEnteBusinessLocal;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IbanEnteDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacadeRemote;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DistintePagamentoDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.EntiDTOBuilder;

@Stateless(name = "AnagraficaEntiFacade")
public class AnagraficaEntiFacadeBean implements AnagraficaEntiFacadeLocal, AnagraficaEntiFacadeRemote {
	
	@EJB(name = "IntestatariBusiness")
	private IntestatariBusinessLocal intestatariBusinessBean;
	
	@EJB(name = "TributoEnteBusiness")
	private TributoEnteBusinessLocal tributoEnteBusinessBean;
	
	@EJB
	private IrisCacheSingletonLocal irisCacheSingletonBean;
	

	@Override
	public EnteDTO readEnteFromIntestatario(String intestatario) {
		
		Enti ente = intestatariBusinessBean.readEnteByIntestatario(intestatario);
		
		return EntiDTOBuilder.fillEnteDTO(ente);
	}	
	
	@Override
	public EnteDTO readEnteFromId(String idEnte) {
		
		Enti ente = intestatariBusinessBean.readEnteByIdEnte(idEnte);
		
		return EntiDTOBuilder.fillEnteDTO(ente);
	}	
	
	@Override
	public ContainerDTO getListaEnti(ContainerDTO inputDTO) {
		
		List<Enti> listaEnti = tributoEnteBusinessBean.listaEnti();
		
		List<CodiceDescrizioneVO> dtos = DistintePagamentoDTOBuilder.populateListEntiDTO(listaEnti);
		
		inputDTO.setOutputDTOList(dtos);
		
		return inputDTO;
	}

	@Override
	public List<CodiceDescrizioneVO> getListaTributiEnti() {
		
		List<TributoEnte> listaTributiEnti = tributoEnteBusinessBean.listaTributiEnti();
		
		List<CodiceDescrizioneVO> dtos = DistintePagamentoDTOBuilder.populateListTributiEntiDTO(listaTributiEnti);
				
		return dtos;
	}

	@Override
	public List<EnteDTO> getListaEntiTributi(boolean isBackOffice) {
		
		List<Enti> listaEnti = tributoEnteBusinessBean.listaEnti();
		
		List<EnteDTO> dtos = EntiDTOBuilder.populateListEntiTributiDTO(listaEnti, isBackOffice);
		
		return dtos;
	}
	
	@Override
    public ContainerDTO listaIBANRiversamenti(ContainerDTO containerDTO){
		
		List<TributoEnte> tributiEnte = tributoEnteBusinessBean.listaIBANRiversamenti(containerDTO);
		
		List<TributoEnteDTO> tributiEnteDTO = EntiDTOBuilder.fillListEntiTributiDTO(tributiEnte, true);

		containerDTO.setOutputDTOList(tributiEnteDTO);
		
		return containerDTO;
	}
	
	@Override
    public ContainerDTO listaIBANTributiNDP(ContainerDTO containerDTO){
		
		List<TributoEnte> tributiEnte = tributoEnteBusinessBean.listaIBANTributiNDP(containerDTO);
		
		List<TributoEnteDTO> tributiEnteDTO = EntiDTOBuilder.fillListEntiTributiDTO(tributiEnte, true);

		containerDTO.setOutputDTOList(tributiEnteDTO);
		
		return containerDTO;
	}
	
	@Override
    public List<TributoEnteDTO> listaTributiEntiByIBAN(String iban){
		
		List<TributoEnte> tributiEnte = tributoEnteBusinessBean.listaTributiEntiByIBAN(iban);
		
		List<TributoEnteDTO> tributiEnteDTO = EntiDTOBuilder.fillListEntiTributiDTO(tributiEnte, true);

		return tributiEnteDTO;
	}

	@Override
    public ContainerDTO listaTributiEnte(ContainerDTO containerDTO){
		
		List<TributoEnte> tributiEnte = tributoEnteBusinessBean.listaTributiEnte(containerDTO);
		
		List<TributoEnteDTO> tributiEnteDTO = EntiDTOBuilder.fillListEntiTributiDTO(tributiEnte, true);

		containerDTO.setOutputDTOList(tributiEnteDTO);
		
		return containerDTO;
	}
	
	@Override
	public String getIdEnteFromIntestario(String intestatario) {
		
		String idEnte = tributoEnteBusinessBean.getIdEnteFromIntestario(intestatario);

		return idEnte;
	}
	
	@Override
	public Map<String, String> getMapEnti() {
		
		return tributoEnteBusinessBean.mapEnti();
		
	}
	
	@Override
    public TributoEnteDTO getTributoEnteByKey(String idEnte, String idTributo){
		
    	TributoEnte tributo = tributoEnteBusinessBean.getTributiEntiByKey(idEnte, idTributo); 
    	
    	TributoEnteDTO tribDto = EntiDTOBuilder.fillTributoEnteDTOFull(tributo);
		
    	return tribDto;
    }
	
	@Override
	public TributoEnteDTO createTributoEnte(TributoEnteDTO dtoIn){
		
		TributoEnte trib = tributoEnteBusinessBean.createTributoEnte(dtoIn);
		
		
		TributoEnteDTO dtoOut = EntiDTOBuilder.fillTributoEnteDTOFull(trib);
		
		return dtoOut;
	
	}
	
	@Override
	public SistemaEnteDTO createSistemaEnte(SistemaEnteDTO dtoIn) {
		
		SistemaEnte sil = tributoEnteBusinessBean.createSistemaEnte(dtoIn);
	
		SistemaEnteDTO dtoOut = EntiDTOBuilder.fillSistemaEnteDTO(sil);
		
		return dtoOut;
	
	}
	
	@Override
	public List<SistemaEnteDTO> selectActiveAndSemplSistemaEnte() {
		List<SistemaEnteDTO> silListDTO = new ArrayList<SistemaEnteDTO>();
		List<SistemaEnte> silList = tributoEnteBusinessBean.selectActiveAndSemplSistemaEnte();
		if (silList != null) {
			for (SistemaEnte sil : silList) {
				SistemaEnteDTO dtoOut = EntiDTOBuilder.fillSistemaEnteDTO(sil);
				silListDTO.add(dtoOut);
			}			
		}
		return silListDTO;
	
	}
	
	@Override
    public ContainerDTO listaTributiEntiByFilterParams(ContainerDTO dto){
		
		List<TributoEnte> tributiEnte = tributoEnteBusinessBean.listTributoEnteByFilterParams(dto);
		
		List<TributoEnteDTO> tributiEnteDTO = EntiDTOBuilder.fillListEntiTributiDTOFull(tributiEnte);
		
		dto.setOutputDTOList(tributiEnteDTO);

		return dto;
	}
	
	@Override
    public ContainerDTO listaCategorieTributiByFilterParams(ContainerDTO dto){
		
		List<CategoriaTributo> categorie = tributoEnteBusinessBean.listCategorieTributoByFilterParams(dto);
		
		List<CategoriaTributoDTO> tributiEnteDTO = EntiDTOBuilder.fillListCategorieTributiDTO(categorie);
		
		dto.setOutputDTOList(tributiEnteDTO);

		return dto;
	}
	
	@Override
    public ContainerDTO listaSistemaEnteByFilterParams(ContainerDTO dto){
		
		List<SistemaEnte> sistemaEnte = tributoEnteBusinessBean.listSistemaEnteByFilterParams(dto);
		
		List<SistemaEnteDTO> sistemaEnteDTO = EntiDTOBuilder.fillListSistemaEntiDTO(sistemaEnte);
		
		dto.setOutputDTOList(sistemaEnteDTO);

		return dto;
	}
	
	@Override
    public SistemaEnteDTO getSistemaEnteByCdEnteAndIsSystem(String cdEnte, String idSystem){
		
		SistemaEnteDTO sistemaEnteDTO = null;
		
		SistemaEnte sistemaEnte = tributoEnteBusinessBean.getSistemaEnteByCdEnteIdSystem(cdEnte, idSystem);
	
			if (sistemaEnte != null)
				sistemaEnteDTO = EntiDTOBuilder.fillSistemaEnteDTO(sistemaEnte);
	
		return sistemaEnteDTO;
	}
	
	@Override
    public SistemaEnteDTO getSistemaEnteByCdEnteAndIsSystemStorico(String cdEnte, String idSystem){
		SistemaEnteDTO sistemaEnteDTO = null;
		SistemaEnte sistemaEnte = tributoEnteBusinessBean.getSistemaEnteByCdEnteIdSystemStorico(cdEnte, idSystem);
		if (sistemaEnte != null)
			sistemaEnteDTO = EntiDTOBuilder.fillSistemaEnteDTO(sistemaEnte);
		return sistemaEnteDTO;
	}
	
	@Override
	public List<TributoEnteDTO> getTributiBySysIDEnte(SistemaEnteDTO dtoIn){
		
		List<TributoEnte> tributiEnte = tributoEnteBusinessBean.getTributiBySysIDEnte(dtoIn);
		
		List<TributoEnteDTO> tributi = EntiDTOBuilder.fillListEntiTributiDTOFull(tributiEnte);
		
		return tributi;
	}

	@Override
	public void deleteSistemaEnte(SistemaEnteDTO dtoIn) {
		
		tributoEnteBusinessBean.deleteSistemaEnte(dtoIn);
		
	}

	@Override
	public EntePosizioneDebitoriaVO getEnteBasicDataByCodiceFiscale(
			String codFiscaleEnte) {
		return buildEntePosizioneDebitoriaVO(intestatariBusinessBean.getEnteByCodiceFiscale(codFiscaleEnte));
	}

	@Override
	public List<EntePosizioneDebitoriaVO> getTuttiEntiBasicData() {
		List<Enti> enti = intestatariBusinessBean.getTuttiEnti();
		List<EntePosizioneDebitoriaVO> result = new ArrayList<EntePosizioneDebitoriaVO>();
		for (Enti ente: enti) {
			result.add(buildEntePosizioneDebitoriaVO(ente));
		}
		return result;
	}

	@Override
	public List<TipoTributoPosizioneDebitoriaVO> getTributiEnteByIntestatario(String intestatario) {
		
		
		List<TipoTributoPosizioneDebitoriaVO> result = new ArrayList<TipoTributoPosizioneDebitoriaVO>();
		
		Enti ente =   intestatariBusinessBean.readEnteByIntestatario(intestatario);
		for (TributoEnte te : ente.getTributiEnte()) {
			result.add(fromTributoEnte(te));
			
		}
		return result;
		
	}
	
	private TipoTributoPosizioneDebitoriaVO fromTributoEnte(TributoEnte te) {
		TipoTributoPosizioneDebitoriaVO tevo = new TipoTributoPosizioneDebitoriaVO();
		tevo.setCdTribEnte(te.getCdTrbEnte());
		tevo.setDesTributo(te.getDeTrb());
		tevo.setIdTributo(te.getIdTributo());
		return tevo;
	}
	
	
	private EntePosizioneDebitoriaVO buildEntePosizioneDebitoriaVO(Enti ente) {
		EntePosizioneDebitoriaVO result= new EntePosizioneDebitoriaVO();
		result.setCdEnte(ente.getCodiceEnte());
		result.setDenom(ente.getDenominazione());
		result.setIdEnte(ente.getIdEnte());
		return result;
	}
	
	@Override
	public void changeStatusTributoEnte(List<TributoEnteDTO> inputList){
		
		tributoEnteBusinessBean.changeStatusTributoEnte(inputList);
				
	}

	@Override
	public void changeStatusCategoriaTributo(List<CategoriaTributoDTO> categorie) {
		
		tributoEnteBusinessBean.changeStatusCategoriaTributo(categorie);
		
	}
	
	@Override
	public CategoriaTributoDTO createCategoriaTributo(CategoriaTributoDTO catDTO){
		
		CategoriaTributo cat = EntiDTOBuilder.fillCategoriaTributoEntity(catDTO);
		
		cat = tributoEnteBusinessBean.createCategoriaTributo(cat);
		
		CategoriaTributoDTO outDTO = EntiDTOBuilder.fillCategoriaTributoDTO(cat);
		
		return outDTO;
	}
	
	@Override
	public CategoriaTributoDTO getCategoriaTributoById(String idTributo) {
		
		CategoriaTributo cat =  tributoEnteBusinessBean.getCategoriaTributoById(idTributo);
		
		CategoriaTributoDTO catDTO = EntiDTOBuilder.fillCategoriaTributoDTO(cat);
		
		return catDTO;
		
	}

	@Override
	public CategoriaTributoDTO updateCategoriaTributo(CategoriaTributoDTO catDTO) {
		
		CategoriaTributo updatedCat = tributoEnteBusinessBean.updateCategoriaTributo(catDTO);
		
		CategoriaTributoDTO outDTO = EntiDTOBuilder.fillCategoriaTributoDTO(updatedCat);
		
		return outDTO;
	}

	@Override
	public void deleteCategorieTributi(List<String> selectedIds) {
		
		tributoEnteBusinessBean.deleteCategorieTributi(selectedIds);
		
	}

	@Override
	public TributoEnteDTO updateTributoEnte(TributoEnteDTO dtoIn) {
		
		TributoEnte trib = tributoEnteBusinessBean.updateTributoEnte(dtoIn);
		
		TributoEnteDTO dtoOut = EntiDTOBuilder.fillTributoEnteDTOFull(trib);
		
		return dtoOut;
	}
	
	@Override
	public SistemaEnteDTO updateSistemaEnte(SistemaEnteDTO dtoIn) {
		
		SistemaEnte trib = tributoEnteBusinessBean.updateSistemaEnte(dtoIn);
		
		SistemaEnteDTO dtoOut = EntiDTOBuilder.fillSistemaEnteDTO(trib);
		
		return dtoOut;
	}
	
	@Override
	public ContainerDTO listCategorieTributiEnte(ContainerDTO dtoIn, String idEnte){
		
		List<CategoriaTributo> listaCategorie = tributoEnteBusinessBean.listCategorieTributiEnte(dtoIn, idEnte);
	
		List<CategoriaTributoDTO> dtos = EntiDTOBuilder.fillListCategorieTributiDTO(listaCategorie);
		
		dtoIn.setOutputDTOList(dtos);
		
		return dtoIn;
	}
	
	@Override
	public List<EnteDTO> listaEnti() {
		
		List<Enti> enti = tributoEnteBusinessBean.listaEnti();
		
		List<EnteDTO> entiDTO = EntiDTOBuilder.fillListEntiDTO(enti);
		
		return entiDTO;
	}

	@Override
	public List<TipologiaEntiCommon> getAllTipologieEnti() {

		List<TipologiaEnti> tipologieEnti =  tributoEnteBusinessBean.getListaTipologiaEnti();
		List<TipologiaEntiCommon> returnList = new ArrayList<TipologiaEntiCommon>();
		for (TipologiaEnti t : tipologieEnti)
			returnList.add((TipologiaEntiCommon)t.copy());
		
		return returnList;
		
	}

	@Override
	public void deleteTributiEnte(List<String> selectedIds) {
		
		tributoEnteBusinessBean.deleteTributiEnte(selectedIds);
	}

	@Override
	public List<TributoEnteDTO> getAllTributiEntibyEnte(String idEnte) {
		List<TributoEnte>  tributiEnte =  tributoEnteBusinessBean.getAllTributiEntibyEnte(idEnte);
		List<TributoEnteDTO> resultList = new ArrayList<TributoEnteDTO>();
		for (TributoEnte t : tributiEnte) {
			resultList.add(EntiDTOBuilder.fillTributoEnteDTOLight(t));
		}
		return resultList;
	}
	
	@Override
	public List<TributoEnteDTO> getAllTributiEnteEseguiti(String idEnte) {
		List<TributoEnte>  tributiEnte =  tributoEnteBusinessBean.getAllTributiEnteEseguiti(idEnte);
		List<TributoEnteDTO> resultList = new ArrayList<TributoEnteDTO>();
		for (TributoEnte t : tributiEnte) {
			resultList.add(EntiDTOBuilder.fillTributoEnteDTOLight(t));
		}
		return resultList;
	}
	
	@Override
	public Long countPendenzeTributo(String idEnte, String cdTrbEnte){
		
		return tributoEnteBusinessBean.countPendenzeTributo(idEnte, cdTrbEnte);
	}
	
	@Override
	public List<String> getNdpIuvStartNumByIdEnte(String idEnte, Long oldNdpIuvStartNum) {
		return tributoEnteBusinessBean.getNdpIuvStartNumByIdEnte(idEnte,oldNdpIuvStartNum);
	}

	@Override
	public void updateFlagPerConfigurazioneCanaliPagamento(String idEnte, TributoEnteDTO dtoIn) {
		tributoEnteBusinessBean.updateFlagPerConfigurazioneCanaliPagamento(idEnte, dtoIn);
	}
	
	@Override
	public ContainerDTO getListIbanByEnte(ContainerDTO inputDTO) {

		List<IbanEnti> ibanEnti =  intestatariBusinessBean.getListIbanByEnte(inputDTO);
		List<IbanEnteDTO> resultList = new ArrayList<IbanEnteDTO>();
		for (IbanEnti t : ibanEnti) {
			resultList.add(EntiDTOBuilder.fillIbanEnteDTO(t));
		}
		
		inputDTO.setOutputDTOList(resultList);
		return inputDTO;
		
	}
	@Override
	public ContainerDTO updateListIbanByEnte(ContainerDTO inputDTO){
		List<IbanEnteDTO> ibanDaAggiornareList= inputDTO.getInputDTOList();
		
		for (IbanEnteDTO t : ibanDaAggiornareList) {
			intestatariBusinessBean.updateIbanByEnte(t);
		}
		return inputDTO;
	}
	
	@Override
	public int countTributoByIban(String idEnte, String iban){
		
		return tributoEnteBusinessBean.countTributiEntiByIban(idEnte, iban);
	}
	

}