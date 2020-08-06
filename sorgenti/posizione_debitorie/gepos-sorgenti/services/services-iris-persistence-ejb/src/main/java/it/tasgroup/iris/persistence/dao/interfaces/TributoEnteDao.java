package it.tasgroup.iris.persistence.dao.interfaces;

//import it.nch.is.fo.tributi.JltentrId;
import it.nch.is.fo.tributi.JltentrId;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TributoEnteDao extends Dao<TributoEnte>{
	
	public List<TributoEnte> listTributiAbleToAnonymousPaymentByIdEnte(String idEnte);
	
	public List<TributoEnte> listTributiAbleToAnonymousPayment();
	
	public List<TributoEnte> getTributiEnte(ContainerDTO inputDTO);

	public List<TributoEnte> listaIBANRiversamenti(ContainerDTO inputDTO);
	
	public List<TributoEnte> getIBANTributiEnteNDP(ContainerDTO inputDTO);

	public List<TributoEnte> getTributiEnteByIBAN(String iban);

	public TributoEnte getTributiEntiByKey(String idEnte, String cdTributoEnte);
	
	public TributoEnte updateTributo(TributoEnteDTO tribDTO);

	public TributoEnte createTributo(TributoEnteDTO trbDTO);

	public List<TributoEnte> listTributoEnteByFilterParams(ContainerDTO dtoIn, boolean checkNPendenze);

	public List<TributoEnte> getTributiBySysIDEnte(SistemaEnteDTO dtoIn);

	public void changeStatusTributoEnteList(List<TributoEnteDTO> inputList);

	public String getNextCategoria();

	public List<TributoEnte> listaTributiEnti();
	
	public void deleteTributiEnte(List<String> selectedIds);

	public void deleteTributoEnte(JltentrId key);
	
	public List<TributoEnte> getTributiEntiIbanCCpNull();
	
	public List<TributoEnte> getAllTributiEntibyEnte(String idEnte);
	
	public List<String> getAllTributiEntibyEnteAndNdpCdStaz(String idEnte, String ndpCodStazPa);
	
	public int countTributiEntiIbanCCpNull(String idEnte, String cdTrbEnte);
	
	public int countTributiEntiIbanMyBankNull(String idEnte, String cdTrbEnte);
	
	public List<String> getNdpIuvStartNumByIdEnte(String idSystem, Long oldNdpIuvStartNum);
	
	public void updateFlagPerConfigurazioneCanaliPagamento(String idEnte, TributoEnteDTO tribDTO);
	
	public int countTributiEntiByIban(String idEnte, String iban);
	
	public List<TributoEnte> getAllTributiEnteEseguiti(String idEnte);
}
