package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.domain.BozzeBonificiRiaccredito;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface BozzeBonificiRiaccreditoDao extends Dao<BozzeBonificiRiaccredito>{
	
	public List<BozzeBonificiRiaccredito> getListBozzeBonificiRiaccreditoByIdBonifico(Integer idBonifico);
	

	public BozzeBonificiRiaccredito retrieveBozzeBonificiRiaccreditoById(Long id);


	public BozzeBonificiRiaccredito getBozzeBonificiRiaccreditoById(Long id);
	
}
