package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.erbweb.common.PreferenzeVO;
import it.nch.erbweb.orm.Jltpref;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoPrenotazioniDao extends Dao<Prenotazioni>{
	public List<PreferenzeVO> listPreferenze(String intestatrio, String operatore, String servizio, String propertyPrefix);
	public List<Prenotazioni> listPrenotazioni(ContainerDTO inputDTO) throws Exception;
	public Prenotazioni creaPrenotazione(Prenotazioni prenotazione);
	public Prenotazioni updatePrenotazione(Prenotazioni prenotazione);
	public boolean deletePrenotazione(Long idPrenotazione);
	public void refreshPrenotazione(Prenotazioni prenotazione);
	public void savePreferenza(Jltpref jltpref);
	public void deletePreferenze(String intestatario, String username, String tipoServizio, String proprieta);
}