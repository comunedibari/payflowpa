package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.tasgroup.iris.domain.UtentiCanali;

@Local
public interface UtentiCanaliDao {
	
	public UtentiCanali saveUtentiCanali(UtentiCanali uc);
	public List<UtentiCanali> findUtentiCanaliByUtente(String idUtente);
	public int deleteUtentiCanaliByUtente(String idUtente);

}
