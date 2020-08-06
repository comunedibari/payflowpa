package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;
import javax.xml.datatype.XMLGregorianCalendar;

@Local
public interface DestinatariPendenzaDao extends Dao<DestinatariPendenza>{
	
  	
	public List<String> listaDebitoriByIdPendenza(String idPendenza);
	
	public List<DestinatariPendenza> listaDestinatariByCodiceFiscale(String codiceFiscale);
	
	public List<DestinatariPendenza> listaDestinatariByCodiceFiscaleAndStato(String codiceFiscale, String statoPendenza);
	
	public List<DestinatariPendenza> ListaDestinatariByCodiceFiscaleStatoAndDate(String codiceFiscale, String statoPendenza,XMLGregorianCalendar dataIni, XMLGregorianCalendar dataFin);
	
}
