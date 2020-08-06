package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface ContoTecnicoDAO extends Dao<ContoTecnico>{
	
	public ContoTecnico readUniqueContoTecnicoAttivo(String intestatario);
}
