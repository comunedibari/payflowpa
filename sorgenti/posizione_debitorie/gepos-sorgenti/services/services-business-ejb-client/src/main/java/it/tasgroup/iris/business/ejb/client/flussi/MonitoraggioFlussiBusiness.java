package it.tasgroup.iris.business.ejb.client.flussi;

import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.idp.domain.messaggi.ConfermeCart;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.EsitiCbill;
import it.tasgroup.iris.domain.EsitiNdp;
import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.domain.MovimentiAccredito;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.exception.CheckSospettoGRException;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.services.util.enumeration.EnumStatoChiusuraRiversamento;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;

import java.util.List;

public interface MonitoraggioFlussiBusiness {

	List<CasellarioInfo> getListCasellarioInfoByFilterParameters(ContainerDTO dto);

	List<IncassiBonificiRh> getListBFLNonRiconciliati(ContainerDTO dto);

	List<CasellarioDispo> getListCasellarioDispoByFilterParameters(ContainerDTO dto);

	CasellarioInfoDTO getCasellarioInfoByMovimentoAccreditoId(Long id);

	CasellarioInfo getCasellarioInfoById(Long id);

	CasellarioDispo getCasellarioDispoById(Long id);

	Rendicontazioni getRendicontazioniById(Long id);

	List<EsitiBb> getEsitiBbByIdRendicontazione(ContainerDTO containerDTO);

	List<EsitiRct> getEsitiRctByIdRendicontazione(ContainerDTO containerDTO);

	List<EsitiRct> getEsitiRctByIdRendicontazioneAndTipoEsito(ContainerDTO containerDTO);

	List<IncassiBonificiRh> getIncassiBonificiRhByIdRendicontazione(ContainerDTO containerDTO);

	EsitiBb getEsitiBbById(Long id);

	EsitiRct getEsitiRctById(Long id);

	IncassiBonificiRh getIncassiBonificiRhById(Long id);

	List<Rid> getEsitiRidByIdRendicontazioneAndCausaleNotEquals(ContainerDTO containerDTO, String causale);

	Rid getRidById(Long id);

	List<EsitiBonificiRiaccredito> getEsitiBonificiRiaccreditoByIdRend(ContainerDTO containerDTO);

	EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoById(Long id);

	List<AllineamentiElettroniciArchivi> getEsitiAEAByIdRendicontazioneAndCausaleNotIn(ContainerDTO containerDTO);

	List<MovimentiAccredito> getMovimentiDaGiornaleDiCassa(ContainerDTO containerDTO);
	
	AllineamentiElettroniciArchivi getAEAById(Long id);

    List<EsitiCbill> getEsitiCbillByIdRendicontazione(ContainerDTO containerDTO);

    List<EsitiNdp> getEsitiNdpByIdRendicontazione(ContainerDTO containerDTO);

    List<Object[]> readListaOperazioniEsitiByFilters(ContainerDTO containerDTO);

    List<PagamentiOnline> readListaDettaglioOperazioniEsitiByFilters(ContainerDTO containerDTO);

    EsitiCbill getEsitiCbillById(Long id);

    EsitiNdp getEsitiNdpById(Long id);

    void riversa(IProfileManager profile, String selectedIBAN, String[] daRiversare, boolean isGiaRiversato) throws CheckSospettoGRException;

    List<Object[]> getListaMittenti();
    
    List<Object[]> getListaMittenti(String ricevente);

    List<Object[]> getListaRiceventi();

    List<Object[]> readSystemIdsList();

    List<Object[]> readApplicationIdsList();

    List<Object[]> readTiOperationsList();

    IncassiBonificiRh saveIncassi(Long idRiconciliazione, String note, EnumStatoChiusuraRiversamento statoChiusura);

    List<MovimentiAccredito> getListNDPNonRiconciliati(ContainerDTO dto);

    List<EsitiNdp> getEsitiNdpByIdFlusso(String idRiconciliazione);

    List<Rendicontazioni> getRendicontazioniRiversamento(ContainerDTO containerDTO);

    MovimentiAccredito riconciliaMovimentoAccredito(EnumTipoAccredito tipoAccredito, String idRiconciliazione, Long idMov);

    MovimentiAccredito riconciliaMovimentoAccredito(Long idMov);

    EsitiNdp riconciliaEsitiNDP(String codTransazione, Long id);

    List<Object[]> groupByDescrizioneEsitoPendenza(ContainerDTO dtoIn);

    /**
     * Metodi per flussi Allineamento Pendenze / Notifichhe pagamento (Tavolo operativo)
     */
	List<PendenzeCart> getListaMessaggiAllineamentoPendenzeCaricati(ContainerDTO dtoIn);

    PendenzeCart getMessaggioAllineamentoPendenzeByKey(String e2emsgid, String senderid, String sendersys);

    EsitiCart getMessaggioEsitoAllineamentoPendenzeByKey(String e2emsgid, String senderid, String sendersys);

	Long countPendenzeElaborate(String e2emsgid, String senderid, String sendersys);
	
	Long countPendenze(String e2emsgid, String senderid, String sendersys);

	List<EsitiPendenza> listPendenzeErrate(String e2emsgid, String senderid, String sendersys);

	byte[] downloadMessaggioAllineamentoPendenze(String e2emsgid, String senderid, String sendersys);
	byte[] downloadMessaggioAllineamentoPendenzeStorico(String e2emsgid, String senderid, String sendersys);

	byte[] downloadMessaggioEsitoAllineamentoPendenze(String e2emsgid, String senderid, String sendersys);
	byte[] downloadMessaggioEsitoAllineamentoPendenzeStorico(String e2emsgid, String senderid, String sendersys);

	List<ErroriCart> getListaMessaggiAllineamentoPendenzeScartati(ContainerDTO dtoIn);

	byte[] downloadMessaggioAllineamentoPendenzeScartato(String idMessaggio);

	List<ErroriIdp> listEsitiScartati(String e2emsgid, String senderid, String sendersys);

	List<ErroriIdp> listEsitiScartatiPaginated(ContainerDTO dtoIn);


	byte[] downloadMessaggioNotificaScartato(String e2emsgid);

	List<NotificheCart> getListaMessaggiNotificaCaricati(ContainerDTO dtoIn);

	NotificheCart getMessaggioNotificaByKey(String e2emsgid, String receiverid, String receiversys);

	ConfermeCart getMessaggioConfermaNotificaByKey(String e2emsgid, String receiverid, String receiversys);

	byte[] downloadMessaggioNotificaPagamento(String e2emsgid, String receiverid, String receiversys);
	byte[] downloadMessaggioNotificaPagamentoStorico(String e2emsgid, String receiverid, String receiversys);

	byte[] downloadMessaggioConfermaNotificaPagamento(String e2emsgid, String senderid, String sendersys);
	byte[] downloadMessaggioConfermaNotificaPagamentoStorico(String e2emsgid, String senderid, String sendersys);

	MovimentiAccredito readMovimentoAccredito(Long idMovimento);
	void updateMessageNotSent(String[] messaggi);
	void updateNotificheNotSent(String[] messaggi);
	int updateAllNotificheNotSent(FilterVO filter);
	int updateAllMessageNotSent(FilterVO filter);

	public List<PendenzeCart> getListaMessaggiAllineamentoPendenzeCaricatiStorico(ContainerDTO dtoIn);
	public EsitiCart getMessaggioEsitoAllineamentoPendenzeByKey(String e2emsgid, String senderid, String sendersys, boolean isStorico);
	public Long countPendenzeElaborate(String e2emsgid, String senderid, String sendersys, boolean isStorico);
	public Long countPendenze(String e2emsgid, String senderid, String sendersys, boolean isStorico);
	public void updateMessageNotSent(String[] messaggi, boolean isStorico);
	public List<NotificheCart> getListaMessaggiNotificaCaricatiStorico(ContainerDTO dtoIn);
	public ConfermeCart getMessaggioConfermaNotificaByKeyStorico(String e2emsgid, String receiverid, String receiversys);

	public PendenzeCart getMessaggioAllineamentoPendenzeByKeyStorico(String e2emsgid, String senderid, String sendersys);
	public Long countPendenzeElaborateStorico(String e2emsgid, String senderid, String sendersys);
	public Long countPendenzeStorico(String e2emsgid, String senderid, String sendersys);
	public EsitiCart getMessaggioEsitoAllineamentoPendenzeByKeyStorico(String e2emsgid, String senderid, String sendersys);
	public List<EsitiPendenza> listPendenzeErrateStorico(String e2emsgid, String senderid, String sendersys);
	public List<ErroriIdp> listEsitiScartatiStorico(String e2emsgid, String senderid, String sendersys);

	NotificheCart getMessaggioNotificaByKeyStorico(String e2emsgid, String receiverid, String receiversys);
	
	public List<String> listIbanAccredito();

}
