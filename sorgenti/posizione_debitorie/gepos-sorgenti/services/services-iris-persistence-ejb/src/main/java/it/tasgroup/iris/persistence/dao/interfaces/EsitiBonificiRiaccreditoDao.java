package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EsitiBonificiRiaccreditoDao extends Dao<EsitiBonificiRiaccredito>{
	
	public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoByIdBonifico(Long idBonifico);

	public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoById(Long id);

	public List<EsitiBonificiRiaccredito> listEsitiBonificiRiaccreditoByIdRend(ContainerDTO containerDTO);
	
	
}
