package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.tasgroup.iris.domain.Canali;

@Local
public interface CanaliDao {

	public List<Canali> findCanaliByDenominazione(String denominazione);

}
