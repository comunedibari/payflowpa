package it.tasgroup.iris.business.ejb.client.giornaleeventi;

import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.gde.GiornaleEventi;

import java.util.List;
import java.util.Locale;
import java.util.Map;


public interface GiornaleEventiBusiness {
	
	public void save(GiornaleEventiExtDTO g);
	
	public void save(GiornaleEventiExtDTO[] g);
	
	public List<GiornaleEventi> getListEventiByFilterParams(ContainerDTO dto);

	public byte[] getGiornaleEventiDocumentiNDP(String codContesto, String idDominio, String iuv, String tipo,String idPsp);

	public void esportaEventiNDPFull(ContainerDTO inputDTO, Prenotazioni prenotazione,
			Map<String, String> mapListaCampi, String cfOperatore,
			Locale locale);
	
	public void ackRTAgid(String codContesto, String idDominio, String iuv, String tipo);

}
