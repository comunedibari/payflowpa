package it.tasgroup.iris.persistence.dao.interfaces;



import java.util.List;

import it.nch.is.fo.tributi.IUVSequence;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface IUVSequenceDao extends Dao<IUVSequence>{
	
	public void insertOrEnable(String idEnte, String cdTrbEnte); 
	public void disable(String idEnte, String cdTrbEnte);
	public void delete(List<String> l);
	
}
