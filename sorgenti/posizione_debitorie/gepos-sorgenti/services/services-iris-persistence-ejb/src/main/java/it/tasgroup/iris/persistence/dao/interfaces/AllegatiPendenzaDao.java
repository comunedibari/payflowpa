package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.services.dao.ejb.Dao;
import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;

import java.util.List;

import javax.ejb.Local;

@Local
public interface AllegatiPendenzaDao extends Dao<AllegatiPendenza> {

	public AllegatiPendenza retrieveById(String idAllegato);
	
	public AllegatiPendenza getAllegatoCondizione(String idPendenza, String idCondizione, EnumTipoAllegato tipoAllegato, EnumCodificaAllegato tipoCodifica);
	
	public List<AllegatiPendenza> getAllegatiPendenza(String idPendenza); 
	
	public List<AllegatiPendenza> getAllAllegatiCondizione(String idPendenza, String idCondizione); 
	
	public List<AllegatiPendenza> getDocumentiAllegatiCondizione(String idPendenza, String idCondizione);

	public List<AllegatiPendenza> getRicevuteAllegatiCondizione(String idPendenza,String idCondizione);

}
