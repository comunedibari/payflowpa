package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class FlussoTesoreriaTO  implements java.io.Serializable {

    public EnteTO ente;
    public Long id;
    public String codAbi;
    public String codBolletta;
    public String codBollo;
    public String codCab;
    public String codCausale;
    public String codCodiceFiscale;
    public String codConto;
    public String codContoAnagrafica;
    public String codDocumento;
    public String codIban;
    public String codIdDominio;
    public String codIdUnivocoFlusso;
    public String codIdUnivocoVersamento;
    public String codPartitaIva;
    public String codPgEsecuzione;
    public String codPgTrasferimento;
    public String codProcesso;
    public String codProvvisorio;
    public Character codTipoConto;
    public String codTipoMovimento;
    public String deAeProvvisorio;
    public String deAnnoBolletta;
    public String deAnnoDocumento;
    public String deCap;
    public String deCausale;
    public String deCitta;
    public String deCognome;
    public String deNome;
    public String deVia;
    public Date dtBolletta;
    public Date dtCreazione;
    public Date dtDataValutaRegione;
    public Date dtRicezione;
    public Date dtUltimaModifica;
    public boolean flgRegolarizzata;
    public BigDecimal numIpBolletta;
    public Long numPgProcesso;
	private Date dtEffettivaSospeso;
	private String codiceGestionaleProvvisorio;

    public FlussoTesoreriaTO() {
    }

    public FlussoTesoreriaTO(EnteTO ente, Long id, String codAbi, String codBolletta, String codBollo, String codCab, String codCausale, String codCodiceFiscale, String codConto, String codContoAnagrafica, String codDocumento, String codIban, String codIdDominio, String codIdUnivocoFlusso, String codIdUnivocoVersamento, String codPartitaIva, String codPgEsecuzione, String codPgTrasferimento, String codProcesso, String codProvvisorio, Character codTipoConto, String codTipoMovimento, String deAeProvvisorio, String deAnnoBolletta, String deAnnoDocumento, String deCap, String deCausale, String deCitta, String deCognome, String deNome, String deVia, Date dtBolletta, Date dtCreazione, Date dtDataValutaRegione, Date dtRicezione, Date dtUltimaModifica, boolean flgRegolarizzata, BigDecimal numIpBolletta, Long numPgProcesso,
			Date dtEffettivaSospeso, String codiceGestionaleProvvisorio) {
        super();
        this.ente = ente;
        this.id = id;
        this.codAbi = codAbi;
        this.codBolletta = codBolletta;
        this.codBollo = codBollo;
        this.codCab = codCab;
        this.codCausale = codCausale;
        this.codCodiceFiscale = codCodiceFiscale;
        this.codConto = codConto;
        this.codContoAnagrafica = codContoAnagrafica;
        this.codDocumento = codDocumento;
        this.codIban = codIban;
        this.codIdDominio = codIdDominio;
        this.codIdUnivocoFlusso = codIdUnivocoFlusso;
        this.codIdUnivocoVersamento = codIdUnivocoVersamento;
        this.codPartitaIva = codPartitaIva;
        this.codPgEsecuzione = codPgEsecuzione;
        this.codPgTrasferimento = codPgTrasferimento;
        this.codProcesso = codProcesso;
        this.codProvvisorio = codProvvisorio;
        this.codTipoConto = codTipoConto;
        this.codTipoMovimento = codTipoMovimento;
        this.deAeProvvisorio = deAeProvvisorio;
        this.deAnnoBolletta = deAnnoBolletta;
        this.deAnnoDocumento = deAnnoDocumento;
        this.deCap = deCap;
        this.deCausale = deCausale;
        this.deCitta = deCitta;
        this.deCognome = deCognome;
        this.deNome = deNome;
        this.deVia = deVia;
        this.dtBolletta = dtBolletta;
        this.dtCreazione = dtCreazione;
        this.dtDataValutaRegione = dtDataValutaRegione;
        this.dtRicezione = dtRicezione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.flgRegolarizzata = flgRegolarizzata;
        this.numIpBolletta = numIpBolletta;
        this.numPgProcesso = numPgProcesso;
		this.dtEffettivaSospeso = dtEffettivaSospeso;
		this.codiceGestionaleProvvisorio = codiceGestionaleProvvisorio;
    }

    public static FlussoTesoreriaTO copyOf(FlussoTesoreriaTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).ente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codAbi,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codBollo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codCab,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codCausale,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codCodiceFiscale,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codConto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codContoAnagrafica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codIban,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codIdDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codIdUnivocoFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codIdUnivocoVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codPartitaIva,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codPgEsecuzione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codPgTrasferimento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codProcesso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codTipoConto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codTipoMovimento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deAeProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deAnnoBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deAnnoDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deCap,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deCausale,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deCitta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deCognome,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deNome,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).deVia,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).dtBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).dtDataValutaRegione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).dtRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).flgRegolarizzata,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).numIpBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).numPgProcesso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).dtEffettivaSospeso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO) o).codiceGestionaleProvvisorio
                );
    }

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

    public String getCodAbi() {
        return codAbi;
    }

    public void setCodAbi(String codAbi) {
        this.codAbi = codAbi;
    }

    public String getCodBolletta() {
        return codBolletta;
    }

    public void setCodBolletta(String codBolletta) {
        this.codBolletta = codBolletta;
    }

    public String getCodBollo() {
        return codBollo;
    }

    public void setCodBollo(String codBollo) {
        this.codBollo = codBollo;
    }

    public String getCodCab() {
        return codCab;
    }

    public void setCodCab(String codCab) {
        this.codCab = codCab;
    }

    public String getCodCausale() {
        return codCausale;
    }

    public void setCodCausale(String codCausale) {
        this.codCausale = codCausale;
    }

    public String getCodCodiceFiscale() {
        return codCodiceFiscale;
    }

    public void setCodCodiceFiscale(String codCodiceFiscale) {
        this.codCodiceFiscale = codCodiceFiscale;
    }

    public String getCodConto() {
        return codConto;
    }

    public void setCodConto(String codConto) {
        this.codConto = codConto;
    }

    public String getCodContoAnagrafica() {
        return codContoAnagrafica;
    }

    public void setCodContoAnagrafica(String codContoAnagrafica) {
        this.codContoAnagrafica = codContoAnagrafica;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getCodIban() {
        return codIban;
    }

    public void setCodIban(String codIban) {
        this.codIban = codIban;
    }

    public String getCodIdDominio() {
        return codIdDominio;
    }

    public void setCodIdDominio(String codIdDominio) {
        this.codIdDominio = codIdDominio;
    }

    public String getCodIdUnivocoFlusso() {
        return codIdUnivocoFlusso;
    }

    public void setCodIdUnivocoFlusso(String codIdUnivocoFlusso) {
        this.codIdUnivocoFlusso = codIdUnivocoFlusso;
    }

    public String getCodIdUnivocoVersamento() {
        return codIdUnivocoVersamento;
    }

    public void setCodIdUnivocoVersamento(String codIdUnivocoVersamento) {
        this.codIdUnivocoVersamento = codIdUnivocoVersamento;
    }

    public String getCodPartitaIva() {
        return codPartitaIva;
    }

    public void setCodPartitaIva(String codPartitaIva) {
        this.codPartitaIva = codPartitaIva;
    }

    public String getCodPgEsecuzione() {
        return codPgEsecuzione;
    }

    public void setCodPgEsecuzione(String codPgEsecuzione) {
        this.codPgEsecuzione = codPgEsecuzione;
    }

    public String getCodPgTrasferimento() {
        return codPgTrasferimento;
    }

    public void setCodPgTrasferimento(String codPgTrasferimento) {
        this.codPgTrasferimento = codPgTrasferimento;
    }

    public String getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(String codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getCodProvvisorio() {
        return codProvvisorio;
    }

    public void setCodProvvisorio(String codProvvisorio) {
        this.codProvvisorio = codProvvisorio;
    }

    public Character getCodTipoConto() {
        return codTipoConto;
    }

    public void setCodTipoConto(Character codTipoConto) {
        this.codTipoConto = codTipoConto;
    }

    public String getCodTipoMovimento() {
        return codTipoMovimento;
    }

    public void setCodTipoMovimento(String codTipoMovimento) {
        this.codTipoMovimento = codTipoMovimento;
    }

    public String getDeAeProvvisorio() {
        return deAeProvvisorio;
    }

    public void setDeAeProvvisorio(String deAeProvvisorio) {
        this.deAeProvvisorio = deAeProvvisorio;
    }

    public String getDeAnnoBolletta() {
        return deAnnoBolletta;
    }

    public void setDeAnnoBolletta(String deAnnoBolletta) {
        this.deAnnoBolletta = deAnnoBolletta;
    }

    public String getDeAnnoDocumento() {
        return deAnnoDocumento;
    }

    public void setDeAnnoDocumento(String deAnnoDocumento) {
        this.deAnnoDocumento = deAnnoDocumento;
    }

    public String getDeCap() {
        return deCap;
    }

    public void setDeCap(String deCap) {
        this.deCap = deCap;
    }

    public String getDeCausale() {
        return deCausale;
    }

    public void setDeCausale(String deCausale) {
        this.deCausale = deCausale;
    }

    public String getDeCitta() {
        return deCitta;
    }

    public void setDeCitta(String deCitta) {
        this.deCitta = deCitta;
    }

    public String getDeCognome() {
        return deCognome;
    }

    public void setDeCognome(String deCognome) {
        this.deCognome = deCognome;
    }

    public String getDeNome() {
        return deNome;
    }

    public void setDeNome(String deNome) {
        this.deNome = deNome;
    }

    public String getDeVia() {
        return deVia;
    }

    public void setDeVia(String deVia) {
        this.deVia = deVia;
    }

    public Date getDtBolletta() {
        return dtBolletta;
    }

    public void setDtBolletta(Date dtBolletta) {
        this.dtBolletta = dtBolletta;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtDataValutaRegione() {
        return dtDataValutaRegione;
    }

    public void setDtDataValutaRegione(Date dtDataValutaRegione) {
        this.dtDataValutaRegione = dtDataValutaRegione;
    }

    public Date getDtRicezione() {
        return dtRicezione;
    }

    public void setDtRicezione(Date dtRicezione) {
        this.dtRicezione = dtRicezione;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public boolean getFlgRegolarizzata() {
        return flgRegolarizzata;
    }

    public void setFlgRegolarizzata(boolean flgRegolarizzata) {
        this.flgRegolarizzata = flgRegolarizzata;
    }

    public BigDecimal getNumIpBolletta() {
        return numIpBolletta;
    }

    public void setNumIpBolletta(BigDecimal numIpBolletta) {
        this.numIpBolletta = numIpBolletta;
    }

    public Long getNumPgProcesso() {
        return numPgProcesso;
    }

    public void setNumPgProcesso(Long numPgProcesso) {
        this.numPgProcesso = numPgProcesso;
    }

    public Date getDtEffettivaSospeso() {
		return dtEffettivaSospeso;
	}

	public void setDtEffettivaSospeso(Date dtEffettivaSospeso) {
		this.dtEffettivaSospeso = dtEffettivaSospeso;
	}

	public String getCodiceGestionaleProvvisorio() {
		return codiceGestionaleProvvisorio;
	}

	public void setCodiceGestionaleProvvisorio(String codiceGestionaleProvvisorio) {
		this.codiceGestionaleProvvisorio = codiceGestionaleProvvisorio;
	}

	public FlussoTesoreriaTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "FlussoTesoreriaTO["
            + ente
            + ", "
            + id
            + ", "
            + codAbi
            + ", "
            + codBolletta
            + ", "
            + codBollo
            + ", "
            + codCab
            + ", "
            + codCausale
            + ", "
            + codCodiceFiscale
            + ", "
            + codConto
            + ", "
            + codContoAnagrafica
            + ", "
            + codDocumento
            + ", "
            + codIban
            + ", "
            + codIdDominio
            + ", "
            + codIdUnivocoFlusso
            + ", "
            + codIdUnivocoVersamento
            + ", "
            + codPartitaIva
            + ", "
            + codPgEsecuzione
            + ", "
            + codPgTrasferimento
            + ", "
            + codProcesso
            + ", "
            + codProvvisorio
            + ", "
            + codTipoConto
            + ", "
            + codTipoMovimento
            + ", "
            + deAeProvvisorio
            + ", "
            + deAnnoBolletta
            + ", "
            + deAnnoDocumento
            + ", "
            + deCap
            + ", "
            + deCausale
            + ", "
            + deCitta
            + ", "
            + deCognome
            + ", "
            + deNome
            + ", "
            + deVia
            + ", "
            + dtBolletta
            + ", "
            + dtCreazione
            + ", "
            + dtDataValutaRegione
            + ", "
            + dtRicezione
            + ", "
            + dtUltimaModifica
            + ", "
            + flgRegolarizzata
            + ", "
            + numIpBolletta
            + ", "
            + numPgProcesso
            + ", "
            + dtEffettivaSospeso
            + ", "
            + codiceGestionaleProvvisorio
            + "]";
    }

}
