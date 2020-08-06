package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface AllineamentiElettroniciArchiviDao extends Dao<AllineamentiElettroniciArchivi>{
	
	public List<AllineamentiElettroniciArchivi> listAEAAccettateBySottoscrittore(String sottoscrittore) throws Exception;

	public List<AllineamentiElettroniciArchivi> listAEAAccettateByIntestatario(String intestatario) throws Exception;

	public List<AllineamentiElettroniciArchivi> listAEAAccettateByIntestAndSottosc(String intestatario,String sottoscrittore) throws Exception;

	public List<AllineamentiElettroniciArchivi> listAEAByIntestatario(ContainerDTO inputDTO, String causale) throws Exception;
	
	public AllineamentiElettroniciArchivi createAEA(AllineamentiElettroniciArchiviDTO inputDTO) throws Exception;//IProfileManager profile, 

	public AllineamentiElettroniciArchivi updateAEA(AllineamentiElettroniciArchivi aea) throws Exception;//IProfileManager profile, 
	
	public AllineamentiElettroniciArchivi retriveAEAById(String id);

	public AllineamentiElettroniciArchivi verificaIbanDuplicato(String iban,String sottoscrittore) throws Exception;

	public AllineamentiElettroniciArchivi verificaAbiDuplicato(String iban,String sottoscrittore) throws Exception;
	
	public List<AllineamentiElettroniciArchivi> listEsitiAeaByRendicontazioneAndCausale(ContainerDTO containerDTO);
	
	public AllineamentiElettroniciArchivi getAEAById(Long id);
}
