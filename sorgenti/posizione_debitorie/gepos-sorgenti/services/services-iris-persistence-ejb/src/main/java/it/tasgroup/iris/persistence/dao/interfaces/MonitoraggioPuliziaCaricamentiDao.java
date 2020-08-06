package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.tasgroup.iris.domain.SveLog;
import it.tasgroup.iris.domain.SveStato;
import it.tasgroup.services.dao.ejb.Dao;

public interface MonitoraggioPuliziaCaricamentiDao extends Dao<SveLog>{
	
	public List<SveStato> getListaStatoSvecchiamento();

	List<SveLog> getListaLogSvecchiamento(String nomeProcesso);
}