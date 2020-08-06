package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.nch.is.fo.enti.TipologiaEnti;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface TipologiaEntiDao extends Dao<TipologiaEnti>{
	
	List<TipologiaEnti> getListaTipologiaEnti();
	
}
