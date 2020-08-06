package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.tasgroup.iris.domain.Province;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface ProvinceDAO extends Dao<Province>{
	
	public Province readProvinciaById(String id);
	
	public List<Province> readListaProvince();

}
