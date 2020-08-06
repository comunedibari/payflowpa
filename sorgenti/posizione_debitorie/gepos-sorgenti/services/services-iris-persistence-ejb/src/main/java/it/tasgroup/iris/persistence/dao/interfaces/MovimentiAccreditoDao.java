package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.MovimentiAccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface MovimentiAccreditoDao extends Dao<MovimentiAccredito>{
	
	public List<MovimentiAccredito> getNDPNonRiconciliati(ContainerDTO containerDTO);

	public MovimentiAccredito updateMovimentoAccredito(MovimentiAccredito mov);

	public List<MovimentiAccredito> retrieveMovimentoAccreditoByIdRendicontazione(Long idRendicontazione);

	public MovimentiAccredito retrieveMovimentoAccreditoById(Long id);
	
}
