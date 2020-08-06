package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.AnagraficaCorsiDottorato;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface AnagraficaCorsiDottoratoDAO extends Dao<AnagraficaCorsiDottorato>{
	
	public AnagraficaCorsiDottorato readCorsoDottoratoById(String id);
	
	public List<AnagraficaCorsiDottorato> readListaCorsiDottorato();

}
