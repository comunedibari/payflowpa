package it.tasgroup.iris.facade.ejb.client.flussi;

import it.nch.erbweb.web.util.ComboOption;
import it.nch.idp.backoffice.tavolooperativo.ConfermaCartVO;
import it.nch.idp.backoffice.tavolooperativo.ErroreIDPVO;
import it.nch.idp.backoffice.tavolooperativo.EsitoCartVO;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.nch.idp.backoffice.tavolooperativo.NotificheCartVO;
import it.nch.idp.backoffice.tavolooperativo.PendenzaErrataVO;
import it.nch.idp.backoffice.tavolooperativo.PendenzeCartVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.idp.rs.model.creditore.MovimentoAccredito;
import it.tasgroup.idp.rs.model.creditore.MovimentoText;
import it.tasgroup.idp.rs.model.creditore.RiconciliazioneMovimentoAccreditoInfo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.ChiaveValoreDTO;
import it.tasgroup.iris.dto.exception.CheckSospettoGRException;
import it.tasgroup.iris.dto.flussi.BFLNonRiconciliatoDTO;
import it.tasgroup.iris.dto.flussi.CasellarioDTO;
import it.tasgroup.iris.dto.flussi.DettaglioCasellarioDTOLight;
import it.tasgroup.iris.dto.flussi.DettaglioEsitiDTOLight;
import it.tasgroup.iris.dto.flussi.ListaEsitiNdpDTOLight;
import it.tasgroup.iris.dto.flussi.RendicontazioniDTOLight;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;

import java.util.List;

public interface MonitoraggioFlussiFacade {

	ContainerDTO readCasellarioListLight(ContainerDTO dto, String flusso);

	ContainerDTO readListBFLNonRiconciliati(ContainerDTO dto);

	DettaglioCasellarioDTOLight readCasellarioById(IProfileManager profileManager, Long id, String flusso);

	MovimentoText readFlussoByIdMovimentoAccredito(Long id);

	CasellarioDTO readCasellarioInfoById(IProfileManager profileManager, Long id, boolean isLoadFlussoCBI);

	CasellarioDTO readCasellarioDispoById(Long id, boolean isLoadFlussoCBI);

	RendicontazioniDTOLight readRendicontazioneById(Long id);

	ContainerDTO readEsitiByIdRendicontazione(ContainerDTO containerDTO);

	DettaglioEsitiDTOLight readEsitoByIdAndCodRendicontazione(Long id, String codRendicontazione);

	ContainerDTO readEsitiByIdRendicontazioneAndTipoesitoRct(ContainerDTO containerDTO);

    List<ComboOption> readMittentiList();
    
    List<ComboOption> readMittentiList(String ricevente);

    List<ComboOption> readRiceventiList();

    ContainerDTO readEsitiCbillByIdRendicontazione(ContainerDTO containerDTO);

    ContainerDTO readEsitiNdpByIdRendicontazione(ContainerDTO containerDTO);

    ContainerDTO readListaOperazioniEsitiByFilters(ContainerDTO containerDTO);

    ContainerDTO readListaDettaglioOperazioniEsitiByFilters(ContainerDTO containerDTO);

    List<ComboOption> readSystemIdsList();

    List<ComboOption> readApplicationIdsList();

    List<ComboOption> readTiOperationsList();

	BFLNonRiconciliatoDTO saveIncassi(BFLNonRiconciliatoDTO dettaglio);

	void riversa(IProfileManager profile, String string, String[] strings, boolean isGiaRiversato) throws CheckSospettoGRException;

	ContainerDTO readListNDPNonRiconciliati(ContainerDTO dto);

	List<ListaEsitiNdpDTOLight> readEsitiNdpByIdFlusso(String idRiconciliazione);

	ContainerDTO getRendicontazioniRiversamento(ContainerDTO containerDTO);

	Long riconciliaMovimentoAccredito(Long idMov);

	void riconciliaMovimentoAccredito(EnumTipoAccredito tipoAccredito, String idRiconciliazione, Long idMov);

	MovimentoAccredito readMovimentoById(Long idMov);

	void riconciliaAccreditoCumulativoConSingolo(Long idMov, Long idEsitoNdp, String codTransazione);

	ListaEsitiNdpDTOLight getEsitiNDPById(Long id);

	List<ChiaveValoreDTO> groupByDescrizioneEsitoPendenza(ContainerDTO dtoIn);


	/**
	 * Metodi per la gestione dei flussi Posizione Debitoria / Notifiche (Tavolo Operativo)
	 *
	 */

	/**
	 *
	 * Ritorna una lista paginata di messaggi allineamento pendenze caricati nel sistema
	 *
	 * @param dtoIn
	 * @return List<PendenzeCartVO>
	 */
	ContainerDTO  getListaMessaggiAllineamentoPendenzeCaricati(ContainerDTO dtoIn);

	/**
	 * Ritorna un messaggio di allineamento pendenze
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	PendenzeCartVO getMessaggioAllineamentoPendenzeByKey(String e2emsgid, String senderid, String sendersys);

	/**
	 * Ritorna l'esito di un messaggio di allineamento pendenze
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	EsitoCartVO getMessaggioEsitoAllineamentoPendenzeByKey(String e2emsgid, String senderid, String sendersys);

	/**
	 * Lista delle pendenze errate contenute in  un messaggio
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	List<PendenzaErrataVO> listPendenzeErrate(String e2emsgid, String senderid, String sendersys);


	/**
	 * Lista degli esiti pendenza scartati ed inseriti in tabella errori_idp
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	List<ErroreIDPVO> listEsitiScartati(String e2emsgid, String senderid, String sendersys);



	/**
	 * Download il contenuto di un messaggio di allineamento pendenze
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	byte[] downloadMessaggioAllineamentoPendenze(String e2emsgid, String senderid, String sendersys);
	byte[] downloadMessaggioAllineamentoPendenzeStorico(String e2emsgid, String senderid, String sendersys);



	/**
	 * Download il contenuto di un messaggio di esito allineamento pendenze
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	byte[] downloadMessaggioEsitoAllineamentoPendenze(String e2emsgid, String senderid, String sendersys);
	byte[] downloadMessaggioEsitoAllineamentoPendenzeStorico(String e2emsgid, String senderid, String sendersys);


	/**
	 *
	 * Ritorna una lista paginata di messaggi allineamento pendenze in tabella errori
	 *
	 * @param dtoIn
	 * @return List<PendenzeCartVO>
	 */
	ContainerDTO  getListaMessaggiAllineamentoPendenzeScartati(ContainerDTO dtoIn);


	/**
	 * Download il contenuto di un messaggio di allineamento pendenze scartato
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	byte[] downloadMessaggioAllineamentoPendenzeScartato(String idMessaggio);



	/**
	 *
	 * Ritorna una lista paginata di messaggi allineamento pendenze in tabella errori
	 *
	 * @param dtoIn
	 * @return List<PendenzeCartVO>
	 */
	ContainerDTO  getListaMessaggiNotificaPagamentoScartati(ContainerDTO dtoIn);


	/**
	 * Download il contenuto di un messaggio di Notifica pagamento scartato
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	byte[] downloadMessaggioNotificaScartato(String e2emsgid);


	/**
	 *
	 * Ritorna una lista paginata di messaggi Notifiche Pagamento
	 *
	 * @param dtoIn
	 * @return List<NotificheCartVO>
	 */
	ContainerDTO  getListaMessaggiNotificaCaricati(ContainerDTO dtoIn);


	/**
	 * Ritorna l'esito di un messaggio di notifiche  pagamento, dato la sua  chiave univoca
	 *
	 * @param e2emsgid
	 * @param receiverid
	 * @param receiversys
	 * @return
	 */
	NotificheCartVO getMessaggioNotificaByKey(String e2emsgid, String receiverid, String receiversys);

	/**
	 * Ritorna la conferma di un messaggio di Notifica pagamento
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	ConfermaCartVO getMessaggioConfermaNotificaByKey(String e2emsgid, String receiverid, String receiversys);


	/**
	 * Download il contenuto di un messaggio di Notifica pagamento
	 *
	 * @param e2emsgid
	 * @param receiverid
	 * @param receiversys
	 * @return
	 */
	byte[] downloadMessaggioNotificaPagamento(String e2emsgid, String receiverid, String receiversys);
	byte[] downloadMessaggioNotificaPagamentoStorico(String e2emsgid, String receiverid, String receiversys);


	/**
	 * Download il contenuto di un messaggio di Conferma Notifica pagamento
	 *
	 * @param e2emsgid
	 * @param senderid
	 * @param sendersys
	 * @return
	 */
	byte[] downloadMessaggioConfermaNotificaPagamento(String e2emsgid, String senderid, String sendersys);
	byte[] downloadMessaggioConfermaNotificaPagamentoStorico(String e2emsgid, String senderid, String sendersys);
	
	
	/**
	 * Update pendenzeCart,EsitiCart,EsitiPendenza per reinviare il messaggio
	 *
	 * @param String[] messaggi
	 * @return
	 */
	void updateMessagesNotSent(String[] messaggi);
	
	/**
	 * Update NotificheCart per reinviare il messaggio
	 *
	 * @param String[] messaggi
	 * @return
	 */
	void updateNotificheNotSent(String[] messaggi);
	
	/**
	 * Update tutte le NotificheCart per reinviare il messaggio 
	 *
	 * @param String[] messaggi
	 * @return
	 */
	int updateAllNotificheNotSent(FilterVO filter);
	
	/**
	 * Update tutte le PendenzeCart per reinviare il messaggio
	 *
	 * @param String[] messaggi
	 * @return
	 */
	int updateAllMessageNotSent(FilterVO filter);


   MovimentoAccredito updateMovimentoAccredito(RiconciliazioneMovimentoAccreditoInfo movimentiAccredito);

   ContainerDTO getListaMessaggiAllineamentoPendenzeCaricatiStorico(ContainerDTO dtoIn);

   public void updateMessagesNotSentStorico(String[] messaggi);

   public ContainerDTO getListaMessaggiNotificaCaricatiStorico(ContainerDTO dtoIn);

   public ConfermaCartVO getMessaggioConfermaNotificaByKeyStorico(String e2emsgid, String receiverid, String receiversys);

   public PendenzeCartVO getMessaggioAllineamentoPendenzeByKeyStorico(String e2emsgid, String senderid, String sendersys);

   public EsitoCartVO getMessaggioEsitoAllineamentoPendenzeByKeyStorico(String e2emsgid, String senderid, String sendersys);

   public List<PendenzaErrataVO> listPendenzeErrateStorico(String e2emsgid, String senderid, String sendersys);

   public List<ErroreIDPVO> listEsitiScartatiStorico(String e2emsgid, String senderid, String sendersys);

NotificheCartVO getMessaggioNotificaByKeyStorico(String e2emsgid, String receiverid, String receiversys);

	public List<String> listIbanAccredito();

}
