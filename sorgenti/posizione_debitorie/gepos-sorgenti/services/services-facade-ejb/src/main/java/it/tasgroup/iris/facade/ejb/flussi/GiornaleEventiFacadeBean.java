package it.tasgroup.iris.facade.ejb.flussi;

import it.nch.profile.IProfileManager;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.EventoNDP_DTO;
import it.tasgroup.iris.facade.ejb.client.flussi.GiornaleEventiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.flussi.GiornaleEventiFacadeRemote;
import it.tasgroup.iris.facade.ejb.flussi.dto.builder.MonitoraggioFlussiDTOBuilder;
import it.tasgroup.iris.gde.GiornaleEventi;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "GiornaleEventiFacade")
public class GiornaleEventiFacadeBean implements GiornaleEventiFacadeLocal, GiornaleEventiFacadeRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(GiornaleEventiFacadeBean.class);	
	
	@EJB(name = "GiornaleEventiBusiness")
	private GiornaleEventiBusinessLocal giornaleEventiBusinessBean;
	
	@Override
	public ContainerDTO readEventiNDPListLight(ContainerDTO dto){

		List<EventoNDP_DTO> dtosreturn = null;
			
		List<GiornaleEventi> listaEventi = giornaleEventiBusinessBean.getListEventiByFilterParams(dto);
				
		dtosreturn = MonitoraggioFlussiDTOBuilder.populateListaEventiNDPDTOLight(listaEventi);
			
		dto.addAllOutputDTO(dtosreturn);

		return dto;		
	}

	@Override
	public EventoNDP_DTO retrieveEventoNDP(ContainerDTO dto) {
		
		List<GiornaleEventi> eventi = giornaleEventiBusinessBean.getListEventiByFilterParams(dto);
		
		if (eventi.size() != 1)
			throw new IllegalStateException("Bad Access");
		
		EventoNDP_DTO eventoDTO = MonitoraggioFlussiDTOBuilder.populateEventoNDPDTO(eventi.get(0));
		
		return eventoDTO;
	} 
	
	@Override
	public byte[] getGiornaleEventiDocumentiNDP(String codContesto, String idDominio, String iuv, String tipo,String idPsp) {
		
			byte[] docBytes = giornaleEventiBusinessBean.getGiornaleEventiDocumentiNDP(codContesto, idDominio, iuv, tipo,idPsp);
			
			return docBytes;
			
	}

	@Override
	public void esportaEventiNDP(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, String cfOperatore, Locale locale) throws Exception {

		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(2);
		LOGGER.debug("esportaEventiNDP - salvata la prenotazione");

		giornaleEventiBusinessBean.esportaEventiNDPFull(inputDTO, pren, mapListaCampi, cfOperatore, locale);
		LOGGER.debug("esportaEventiNDP - lanciato l'exporter asincrono");
	}
	
}