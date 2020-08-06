package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.PagamentoRidDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RidDao extends Dao<Rid>{
	
	public List<Rid> listPagamentiRidByIntestatario(String intestatario) throws Exception;
	
	public Rid createPRid(PagamentoRidDTO inputDTO, ContoTecnico contoTecnico) throws Exception;//(IProfileManager profile, 

	public List<Rid> listEsitiRidByRendicontazioneAndCausale(ContainerDTO containerDTO, String causale);
	
	public Rid getRidById(Long id);
}
