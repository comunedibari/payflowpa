package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoGestioneFlussiDao extends Dao<GestioneFlussi> {
	
	public List<GestioneFlussi> getDistinteByIdCondizionePagamento(String idCondizionePagamento);
	public GestioneFlussi getDistintaById(Long flussoId);

}
