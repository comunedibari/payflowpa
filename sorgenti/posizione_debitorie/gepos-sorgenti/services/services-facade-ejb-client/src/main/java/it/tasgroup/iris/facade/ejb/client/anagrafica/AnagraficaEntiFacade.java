/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.anagrafica;

import java.util.List;
import java.util.Map;

import it.nch.idp.posizionedebitoria.EntePosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.TipoTributoPosizioneDebitoriaVO;
import it.nch.is.fo.common.CodiceDescrizioneVO;
import it.nch.is.fo.enti.TipologiaEntiCommon;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;


/**
 * @author pazzik
 *
 */
public interface AnagraficaEntiFacade {	
	
	public EnteDTO readEnteFromIntestatario(String intestatario);

	public ContainerDTO getListaEnti(ContainerDTO inputDTO);
	
	public List<CodiceDescrizioneVO> getListaTributiEnti();
	
	public List<EnteDTO> getListaEntiTributi(boolean isBackOffice);

	public ContainerDTO listaTributiEnte(ContainerDTO containerDTO);

	public ContainerDTO listaIBANRiversamenti(ContainerDTO containerDTO);
	
	public ContainerDTO listaIBANTributiNDP(ContainerDTO containerDTO);

	public List<TributoEnteDTO> listaTributiEntiByIBAN(String iban);
	
	public Map<String, String> getMapEnti();
	
	public String getIdEnteFromIntestario(String intestatario);
	
	public TributoEnteDTO getTributoEnteByKey(String idEnte, String idTributo);
	
	public TributoEnteDTO updateTributoEnte(TributoEnteDTO dtoIn);
	
	public TributoEnteDTO createTributoEnte(TributoEnteDTO dtoIn);

	public ContainerDTO listaTributiEntiByFilterParams(ContainerDTO dto);
	
	public ContainerDTO listaCategorieTributiByFilterParams(ContainerDTO dto);

	public ContainerDTO listaSistemaEnteByFilterParams(ContainerDTO dto);
	
	public SistemaEnteDTO getSistemaEnteByCdEnteAndIsSystem(String idEnte, String idSystem);

	public SistemaEnteDTO createSistemaEnte(SistemaEnteDTO dtoIn);

	public List<TributoEnteDTO> getTributiBySysIDEnte(SistemaEnteDTO dtoIn);

	public void deleteSistemaEnte(SistemaEnteDTO dtoIn);
	
	public void deleteCategorieTributi(List<String> selectedIds);

	//public void deleteTributoEnte(TributoEnteDTO inDTO);

	public EntePosizioneDebitoriaVO getEnteBasicDataByCodiceFiscale(String codFiscaleEnte);
	
	public List<EntePosizioneDebitoriaVO> getTuttiEntiBasicData();
	
	public List<TipoTributoPosizioneDebitoriaVO> getTributiEnteByIntestatario(String intestatario);

	public void changeStatusTributoEnte(List<TributoEnteDTO> inputList);

	public void changeStatusCategoriaTributo(List<CategoriaTributoDTO> categorie);

	public CategoriaTributoDTO createCategoriaTributo(CategoriaTributoDTO cat);

	public CategoriaTributoDTO getCategoriaTributoById(String idTributo);

	public CategoriaTributoDTO updateCategoriaTributo(CategoriaTributoDTO filterDTO);

	public ContainerDTO listCategorieTributiEnte(ContainerDTO dtoIn, String idEnte);

	public List<EnteDTO> listaEnti();

	public void deleteTributiEnte(List<String> selectedIds);
	
	public List<TributoEnteDTO> getAllTributiEntibyEnte(String idEnte);
	
	public SistemaEnteDTO updateSistemaEnte(SistemaEnteDTO dtoIn);

	//
	// portati dal CorporateBusinessDelegate
	//
	public List<TipologiaEntiCommon> getAllTipologieEnti();

	public Long countPendenzeTributo(String idEnte, String cdTrbEnte);

	public List<SistemaEnteDTO> selectActiveAndSemplSistemaEnte();
	
	public List<String> getNdpIuvStartNumByIdEnte(String idSystem,Long oldNdpIuvStartNum);

	public EnteDTO readEnteFromId(String idEnte);
	
	public void updateFlagPerConfigurazioneCanaliPagamento(String idEnte, TributoEnteDTO dtoIn);
	
	public ContainerDTO getListIbanByEnte(ContainerDTO inputDTO);
	
	public ContainerDTO updateListIbanByEnte(ContainerDTO inputDTO);
	
	public int countTributoByIban(String idEnte, String iban);

	public List<TributoEnteDTO> getAllTributiEnteEseguiti(String idEnte);

	public SistemaEnteDTO getSistemaEnteByCdEnteAndIsSystemStorico(String cdEnte, String idSystem);
	
}
