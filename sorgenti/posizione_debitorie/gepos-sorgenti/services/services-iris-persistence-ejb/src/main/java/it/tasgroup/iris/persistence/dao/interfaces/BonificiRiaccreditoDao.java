package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface BonificiRiaccreditoDao extends Dao<BonificiRiaccredito>{
	
	public List<BonificiRiaccredito> listBonificiRiaccreditoByIdDistinta(ContainerDTO containerDTO);
	
	public BonificiRiaccredito retrieveBonificiRiaccreditoById(Long id);
	
	
}
