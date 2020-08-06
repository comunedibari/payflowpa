package it.tasgroup.iris.facade.ejb.client.flussi;

import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.EventoNDP_DTO;

import java.util.Locale;
import java.util.Map;

public interface GiornaleEventiFacade {
	
	public ContainerDTO readEventiNDPListLight(ContainerDTO dto);

	public EventoNDP_DTO retrieveEventoNDP(ContainerDTO containerDTO);

	public byte[] getGiornaleEventiDocumentiNDP(String codContesto, String idDominio, String iuv, String tipo, String idPsp);

	public void esportaEventiNDP(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, String cfOperatore, Locale locale) throws Exception;

}
