package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.pagamenti.Multe;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface MulteDao extends Dao<Multe>{
	public Multe getMultaByTargaAndNumVerbale(String targa, String numeroVerbale);
	public Multe saveMulta(Multe multa);
}
