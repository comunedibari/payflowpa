package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.NazioneCittadinanza;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface NazioneCittadinanzaDAO extends Dao<NazioneCittadinanza>{
	
	public NazioneCittadinanza readNazioneCittadinanzaById(String id);
	
	public List<NazioneCittadinanza> readListaNazioniCittadinanza();

}
