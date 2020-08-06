package it.tasgroup.iris.business.ejb.client.esportazioni;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.nch.erbweb.profilo.ProfiloInputVO;
import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.domain.Esportazioni;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;



public interface ExportBusiness {

	public void esporta(Prenotazioni prenotazione, Map<String, String> mapListaCampi,
			String cfOperatore, String riga, String separatore, List outputDTO,
			String myvaloreselezionato,String tipoExport, EnumDynaReportFormat dynaReportFormat,  EnumExportSTDFormat enumExportSTDFormat, Class clazz, Locale locale);

	
	
	public Prenotazioni creaPrenotazione(ContainerDTO inputDTO,
			IProfileManager profile, String cfOperatore);

	public Prenotazioni updatePrenotazione(ContainerDTO inputDTO) throws Exception;

	public boolean deletePrenotazione(Long idPrenotazione) throws Exception;

	public HashMap listaPreferenzeExport(Collection lista, ProfiloInputVO vo) throws Exception;

	public List listaPreferenze(IProfileManager profilo, ProfiloInputVO inputVO)
			throws Exception;

	public void aggiornaPreferenze(IProfileManager profilo, PreferenzaEsportazioneVO dform)
			throws Exception;

	public void esportaQuietanze(List<DocumentoRepositoryDTO> lDoc,
			String cfOperatore, Prenotazioni prenotazione) throws Exception;

	public List<Prenotazioni> listaPrenotazioni(ContainerDTO inputDTO);

	public Esportazioni getEsportazioni(Long idPren);



	void esporta(Prenotazioni prenotazione, Map<String, String> mapListaCampi, String cfOperatore, String riga,
			String separatore, List outputDTO, String myvaloreselezionato, String tipoEsportazione,
			EnumDynaReportFormat dynaReportFormat, EnumExportSTDFormat enumExportSTDFormat, Class clazz, Locale locale,
			String filename);



	public List<Prenotazioni> listaPrenotazioniStorico(ContainerDTO inputDTO);	
	
}

