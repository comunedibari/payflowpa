package it.tasgroup.iris.business.ejb.client.anagrafica;

import it.nch.is.fo.enti.TipologiaEnti;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;

import java.util.List;
import java.util.Map;

public interface TributoEnteBusiness {	

	public List<Enti> listaEnti();
	
	public List<TributoEnte> listaTributiEnti();

    public Map<String, String> mapEnti();
    
    public String getIdEnteFromIntestario(String intestatario);
       
    public List<TributoEnte> listaTributiEnte(ContainerDTO containerDTO);
    
    public List<TributoEnte> listaIBANRiversamenti(ContainerDTO containerDTO);
    
	public List<TributoEnte> listaIBANTributiNDP(ContainerDTO containerDTO);

	public List<TributoEnte> listaTributiEntiByIBAN(String iban);

	public TributoEnte getTributiEntiByKey(String idEnte, String idTributo);
	
	public CfgNotificaPagamento createCfgNotificaPagamento(CfgNotificaPagamento cfg);

	public TributoEnte createTributoEnte(TributoEnteDTO dtoIn);

	public List<TributoEnte> listTributoEnteByFilterParams(ContainerDTO dtoIn);

	public List<CategoriaTributo> listCategorieTributoByFilterParams(ContainerDTO dtoIn);

	public List<SistemaEnte> listSistemaEnteByFilterParams(ContainerDTO dtoIn);

	public SistemaEnte createSistemaEnte(SistemaEnteDTO dtoIn);

	public List<TributoEnte> getTributiBySysIDEnte(SistemaEnteDTO dtoIn);
	
	public void deleteSistemaEnte(SistemaEnteDTO dtoIn);
	
	public void changeStatusTributoEnte(List<TributoEnteDTO> inputList);
	
	public void changeStatusCategoriaTributo(List<CategoriaTributoDTO> categorie);

	public CategoriaTributo createCategoriaTributo(CategoriaTributo cat);

	public CategoriaTributo getCategoriaTributoById(String idTributo);
	
	public CategoriaTributo updateCategoriaTributo(CategoriaTributoDTO catDTO);
	
	public void deleteCategorieTributi(List<String> selectedIds);
	
	public TributoEnte updateTributoEnte(TributoEnteDTO tribDTO);

	public List<CategoriaTributo> listCategorieTributiEnte(ContainerDTO dtoIn, String idEnte);

	public void deleteTributiEnte(List<String> selectedIds);

	public List<TributoEnte> getAllTributiEntibyEnte(String idEnte);
	
	public List<TipologiaEnti> getListaTipologiaEnti();

	public SistemaEnte updateSistemaEnte(SistemaEnteDTO silDTO);

	public Long countPendenzeTributo(String idEnte, String cdTrbEnte);

	public List<TributoEnte> getTributiEntiIbanCCpNull();
	
	public int countTributiEntiIbanCCpNull(String idEnte, String cdTrbEnte);
	
	public int countTributiEntiIbanMyBankNull(String idEnte, String cdTrbEnte);
	
	public List<SistemaEnte> selectActiveAndSemplSistemaEnte();
	
	public List<String> getNdpIuvStartNumByIdEnte(String idSystem, Long oldNdpIuvStartNum);

	public SistemaEnte getSistemaEnteByCdEnteIdSystem(String cdEnte, String idSystem);
	
	public void updateFlagPerConfigurazioneCanaliPagamento(String idEnte, TributoEnteDTO tribDTO);
	
	public int countTributiEntiByIban(String idEnte, String iban);
	
	public List<TributoEnte> getAllTributiEnteEseguiti(String idEnte);

	public SistemaEnte getSistemaEnteByCdEnteIdSystemStorico(String cdEnte, String idSystem);

}

