package it.nch.idp.posizionedebitoria;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author stefano
 * 
 *         classe utilizzata nel dettaglio dell'avviso
 */
public class AvvisoPosizioneDebitoriaDettaglioVO extends AvvisoPosizioneDebitoriaVO {
	private static final long serialVersionUID = 1L;

	private Date dataCreazione;
	private Timestamp ultimaModifica;
	private String descrizioneMittente;
	private String descrizioneDestinatario;

	private List<AllegatoAvvisoPosDeb> allegati;
	private List<CondizionePagamentoPosizioneDebitoriaVO> soluzionePagUnica;
	private List<CondizionePagamentoPosizioneDebitoriaVO> ratePagamento;

	private boolean showAggiornaBtn = false;

	private Integer cartellaPagamento;
	private String urlUpdateService;
    private String statoEsito;

	public List<AllegatoAvvisoPosDeb> getAllegati() {
		return allegati;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public String getDescrizioneMittente() {
		return descrizioneMittente;
	}

	public Timestamp getUltimaModifica() {
		return ultimaModifica;
	}

	public void setAllegati(List<AllegatoAvvisoPosDeb> list) {
		allegati = list;
	}

	public void setDataCreazione(Date date) {
		dataCreazione = date;
	}

	public void setDescrizioneMittente(String string) {
		descrizioneMittente = string;
	}

	public void setUltimaModifica(Timestamp timestamp) {
		ultimaModifica = timestamp;
	}

	public List<CondizionePagamentoPosizioneDebitoriaVO> getRatePagamento() {
		return ratePagamento;
	}

	public int getSoluzionePagUnicaSize() {
		return soluzionePagUnica.size();
	}
	
	public List<CondizionePagamentoPosizioneDebitoriaVO> getSoluzionePagUnica() {
		return soluzionePagUnica;
	}

	public void setRatePagamento(List<CondizionePagamentoPosizioneDebitoriaVO> list) {
		ratePagamento = list;
	}

	public void setSoluzionePagUnica(List<CondizionePagamentoPosizioneDebitoriaVO> debitoria) {
		soluzionePagUnica = debitoria;
	}

	public String getDescrizioneDestinatario() {
		return descrizioneDestinatario;
	}

	public void setDescrizioneDestinatario(String string) {
		descrizioneDestinatario = string;
	}

	public boolean isShowAggiornaBtn() {
		return showAggiornaBtn;
	}

	public void setShowAggiornaBtn(boolean showAggiornaBtn) {
		this.showAggiornaBtn = showAggiornaBtn;
	}

	public Integer getCartellaPagamento() {
		return cartellaPagamento;
	}

	public void setCartellaPagamento(Integer cartellaPagamento) {
		this.cartellaPagamento = cartellaPagamento;
	}

	public String getUrlUpdateService() {
		return urlUpdateService;
	}

	public void setUrlUpdateService(String urlUpdateService) {
		this.urlUpdateService = urlUpdateService;
	}

	public void updateShowAggiornaBtn() {
            
		List<CondizionePagamentoPosizioneDebitoriaVO> condizioniTutte = new ArrayList<CondizionePagamentoPosizioneDebitoriaVO>();

		if (ratePagamento != null)
			condizioniTutte.addAll(ratePagamento);

		if (soluzionePagUnica != null)
			condizioniTutte.addAll(soluzionePagUnica);

		for (CondizionePagamentoPosizioneDebitoriaVO condPag : condizioniTutte) {

			if (condPag.getStatoPagamento() != null && condPag.getStatoPagamento().equals(StatiPagamentiIris.IN_CORSO.getPagaMapping())) {

				setShowAggiornaBtn(true);

				break;
			}
		}
	}

    public String getStatoEsito() {
        return statoEsito;
    }

    public void setStatoEsito(String statoEsito) {
        this.statoEsito = statoEsito;
    }

}
