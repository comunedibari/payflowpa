package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.is.fo.profilo.IbanEnti;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.IbanEnteDTO;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface IbanEntiDao extends Dao<IbanEnti>{
	
	public List<IbanEnti> getListIbanByEnte(ContainerDTO inputDTO);
	
	public void updateIbanEnte(IbanEnteDTO ibanDTO);
	
	public int countIbanEnti(String idEnte, String iban);
	
	public void insertIbanEnte(IbanEnti ibanEnti);
	
}
