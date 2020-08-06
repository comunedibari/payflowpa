package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.EsitiNdp;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EsitiNdpDao extends Dao<EsitiNdp>{

	public List<EsitiNdp> listEsitiNdpByIdRendicontazione(ContainerDTO containerDTO);
	
	public EsitiNdp getEsitiNdpById(Long id);
	
	public List<EsitiNdp> getEsitiNdpByIdFlusso(String idFlusso);

	public EsitiNdp updateEsitiNdp(EsitiNdp esito);

	public EsitiNdp retrieveEsitiNdpById(Long id);
	
}
