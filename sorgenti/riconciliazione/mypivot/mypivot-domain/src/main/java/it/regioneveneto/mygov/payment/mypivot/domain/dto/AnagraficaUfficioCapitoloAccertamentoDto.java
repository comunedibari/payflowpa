package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import java.util.Date;

import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;

public class AnagraficaUfficioCapitoloAccertamentoDto {

    private EnteTO ente;
    private Long id;
    private String codAccertamento;
    private String codCapitolo;
    private String codTipoDovuto;
    private String deTipoDovuto;
    private String codUfficio;
    private String deAccertamento;
    private String deAnnoEsercizio;
    private String deCapitolo;
    private String deUfficio;
    private Date dtCreazione;
    private Date dtUltimaModifica;
    private boolean flgAttivo;
    private boolean flgAttivoCambiato;

    public AnagraficaUfficioCapitoloAccertamentoDto() {
    }

    public AnagraficaUfficioCapitoloAccertamentoDto(EnteTO ente, Long id, String codAccertamento, String codCapitolo, String codTipoDovuto, String deTipoDovuto, String codUfficio, String deAccertamento, String deAnnoEsercizio, String deCapitolo, String deUfficio, Date dtCreazione, Date dtUltimaModifica, boolean flgAttivo, boolean flgAttivoCambiato) {
        super();
        this.ente = ente;
        this.id = id;
        this.codAccertamento = codAccertamento;
        this.codCapitolo = codCapitolo;
        this.codTipoDovuto = codTipoDovuto;
        this.deTipoDovuto = deTipoDovuto;
        this.codUfficio = codUfficio;
        this.deAccertamento = deAccertamento;
        this.deAnnoEsercizio = deAnnoEsercizio;
        this.deCapitolo = deCapitolo;
        this.deUfficio = deUfficio;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.flgAttivo = flgAttivo;
        this.flgAttivoCambiato = flgAttivoCambiato;
    }

//    public static AnagraficaUfficioCapitoloAccertamentoDto copyOf(AnagraficaUfficioCapitoloAccertamentoDto o) {
//            return new it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO(
//                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).ente),
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).id,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codAccertamento,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codCapitolo,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codTipoDovuto,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codUfficio,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deAccertamento,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deAnnoEsercizio,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deCapitolo,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deUfficio,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).dtCreazione,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).dtUltimaModifica,
//                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).flgAttivo
//                );
//    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodAccertamento() {
        return codAccertamento;
    }

    public void setCodAccertamento(String codAccertamento) {
        this.codAccertamento = codAccertamento;
    }

    public String getCodCapitolo() {
        return codCapitolo;
    }

    public void setCodCapitolo(String codCapitolo) {
        this.codCapitolo = codCapitolo;
    }

    public String getCodTipoDovuto() {
		return codTipoDovuto;
	}

	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}

	public String getDeTipoDovuto() {
		return deTipoDovuto;
	}

	public void setDeTipoDovuto(String deTipoDovuto) {
		this.deTipoDovuto = deTipoDovuto;
	}

	public String getCodUfficio() {
        return codUfficio;
    }

    public void setCodUfficio(String codUfficio) {
        this.codUfficio = codUfficio;
    }

    public String getDeAccertamento() {
        return deAccertamento;
    }

    public void setDeAccertamento(String deAccertamento) {
        this.deAccertamento = deAccertamento;
    }

    public String getDeAnnoEsercizio() {
        return deAnnoEsercizio;
    }

    public void setDeAnnoEsercizio(String deAnnoEsercizio) {
        this.deAnnoEsercizio = deAnnoEsercizio;
    }

    public String getDeCapitolo() {
        return deCapitolo;
    }

    public void setDeCapitolo(String deCapitolo) {
        this.deCapitolo = deCapitolo;
    }

    public String getDeUfficio() {
        return deUfficio;
    }

    public void setDeUfficio(String deUfficio) {
        this.deUfficio = deUfficio;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }
    
	public boolean isFlgAttivoCambiato() {
		return flgAttivoCambiato;
	}

	public void setFlgAttivoCambiato(boolean flgAttivoCambiato) {
		this.flgAttivoCambiato = flgAttivoCambiato;
	}

	@Override
    public String toString() {
        return "AnagraficaUfficioCapitoloAccertamentoTO["
            + ente
            + ", "
            + id
            + ", "
            + codAccertamento
            + ", "
            + codCapitolo
            + ", "
            + codTipoDovuto
            + ", "
            + deTipoDovuto
            + ", "
            + codUfficio
            + ", "
            + deAccertamento
            + ", "
            + deAnnoEsercizio
            + ", "
            + deCapitolo
            + ", "
            + deUfficio
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + flgAttivo
            + ", "
            + flgAttivoCambiato
            + "]";
    }

}
