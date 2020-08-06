package it.tasgroup.iris.persistence.dao.interfaces;

import javax.ejb.Local;

import it.nch.is.fo.sistemienti.SistemaEnte;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoSistemaEnteDAO extends Dao<SistemaEnte>{

	public SistemaEnte getSistemaEnteByCdEnteAndIdSystem(String cdEnte, String idSystem);
	
}
