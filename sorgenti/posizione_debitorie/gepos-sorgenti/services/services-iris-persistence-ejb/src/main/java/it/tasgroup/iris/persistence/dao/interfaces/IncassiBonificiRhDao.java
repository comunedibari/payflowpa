package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface IncassiBonificiRhDao extends Dao<IncassiBonificiRh>{

	public List<IncassiBonificiRh> listIncassiBonificiRhByIdRendicontazione(ContainerDTO containerDTO);
	
	public IncassiBonificiRh getIncassiBonificiRhById(Long id);
	
	public List<IncassiBonificiRh> getBFLNonRiconciliati(ContainerDTO containerDTO);

	public IncassiBonificiRh updateIncassi(IncassiBonificiRh inc);

	public List<String> listIbanAccredito();
}
