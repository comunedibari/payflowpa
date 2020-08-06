package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.tasgroup.iris.domain.PrenotazioneAvvisiDigitali;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoPrenotazioneAvvisiDigitaliDao extends Dao<PrenotazioneAvvisiDigitali> {
	
	public List<PrenotazioneAvvisiDigitali> getPrenotazioneAvvisiDigitaliByCondizione(String idCondizione);
	public void resettaPrenotaAvvisiDigitali(Long id, String tipo);
	
}
