package it.tasgroup.iris.facade.ejb.client.pendenze;

import java.util.List;

import it.nch.idp.posizionedebitoria.AllegatoAvvisoPosDeb;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPendenzaDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;


public interface PendenzeFacade {
	
	/**
	 * Ricerca Pendenze (avvisi pagamento) in base a criteri di selezione ai fini della visualizzazione
	 * nelle liste posizione debitoria
	 * @param method
	 * @param profilo
	 * @param dto
	 * @return
	 */
	public ContainerDTO ricercaAvvisi(String method, IProfileManager profilo, ContainerDTO dto);

	/**
	 * Ricerca Pendenze (avvisi pagamento) in base a criteri di selezione ai fini dell'export da CSV
	 * @param method
	 * @param profilo
	 * @param dto
	 * @return
	 */
	public ContainerDTO exportAvvisi(String method, IProfileManager profilo, ContainerDTO dto);

	
	/**
	 * Dettaglio completo di una pendenza
	 * @param method
	 * @param profilo
	 * @param dto
	 * @return
	 */
	public AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso(String idPendenza, IProfileManager profilo);

	/**
	 * Recupera un allegato dato il suo id. L'oggetto risultante contiene sia i metadati che il file stesso (encoded base64)
	 * 
	 * @param idAllegato
	 * @return
	 */
	public AllegatoAvvisoPosDeb getAllegatoAvvisoById(String idAllegato);
	
	
	/**
	 * Ritorna l'Id Fisico di una pendenza data la sua chiave semantica.
	 * Questo metodo permette di trovare in particolare l'Id Fisico dell'unica pendenza "attiva" esistente
	 * che rispetta la chiave semantica. (st_riga='V') 
	 * 
	 * @param cdTrbEnte
	 * @param idEnte
	 * @param idPendenzaEnte
	 * @return
	 */
	public String getIdPendenzaByChiaveSemantica(String cdTrbEnte, String idEnte, String idPendenzaEnte);

	public List<AvvisoPosizioneDebitoriaVO> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale,
			String idEnte) throws BusinessConstraintException;
	
	/**
	 * 
	 * @param id
	 * @param tipo
	 */
	public void resettaPrenotaAvvisiDigitali(Long id, String tipo);

	public ContainerDTO ricercaAvvisiStorico(String method, IProfileManager profilo, ContainerDTO dto);

	ContainerDTO exportAvvisiStorico(String method, IProfileManager profilo, ContainerDTO dto);

	AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvvisoStorico(String idPendenza, IProfileManager profilo); 
}
