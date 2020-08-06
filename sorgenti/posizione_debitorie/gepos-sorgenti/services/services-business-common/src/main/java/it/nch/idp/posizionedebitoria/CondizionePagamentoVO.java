package it.nch.idp.posizionedebitoria;

import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.services.util.IVocePagamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CondizionePagamentoVO implements Serializable
{
	
	private 	String	codiceTributoEnte;
	private 	String	coCip;
	private 	String	descrizioneCanaleDiPagamento;
	private 	Date	dataFineValidita;
	private 	Date	dataInizioValidita;
	private 	Date	dataPagamento;
	private 	Date	dataScandenza;
	private 	String	idCondizione;
	private 	String	idEnte;
	private 	String	idPagamento;
	private 	String	idPendenza;
	private 	BigDecimal	importoTotale;
	private 	BigDecimal	importoPagamento;
	private 	String	operatoreAggiornamento;
	private 	String	operatoreAnnullamento;
	private 	String	operatoreInserimento;
	private 	int		prVersione;
	private 	String	statoPagamento;
	private 	String	statoRiga;
	private 	String	tipoCip;
	private 	String	tipoPagamento;
	private 	Date	dataAggiornamento;
	private 	Date	dataAnnullamento;
	private 	Date	dataDecorrenza;
	private 	Date	dataInserimento;

	private     String  causale;
	private 	String  denominazioneEnte;
	private 	String  descrizioneTributo;
	private 	String  ibanBeneficiario;	
	private 	String  ragSocBeneficiario;
	
	private 	List<IVocePagamento> 	vociPagamento;
	
	private 	String 	notePagamento;
	
	
	private PagamentoPosizioneDebitoriaVO pagamento;
	

	public PagamentoPosizioneDebitoriaVO getPagamento() {
		return pagamento;
	}
	public void setPagamento(PagamentoPosizioneDebitoriaVO pagamento) {
		this.pagamento = pagamento;
	}
	public String getCodiceTributoEnte() {
		return codiceTributoEnte;
	}
	public void setCodiceTributoEnte(String codiceTributoEnte) {
		this.codiceTributoEnte = codiceTributoEnte;
	}
	public String getCoCip() {
		return coCip;
	}
	public void setCoCip(String coCip) {
		this.coCip = coCip;
	}
	public String getDescrizioneCanaleDiPagamento() {
		return descrizioneCanaleDiPagamento;
	}
	public void setDescrizioneCanaleDiPagamento(String descrizioneCanaleDiPagamento) {
		this.descrizioneCanaleDiPagamento = descrizioneCanaleDiPagamento;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Date getDataScandenzaVideo() {
		if (dataScandenza.compareTo(SharedConstants.NO_EXPIRE) == 0)
			return null;
		else
			return dataScandenza;
	}
	public Date getDataScandenza() {
		return dataScandenza;
	}
	public void setDataScandenza(Date dataScandenza) {
		this.dataScandenza = dataScandenza;
	}
	public String getIdCondizione() {
		return idCondizione;
	}
	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	public BigDecimal getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(BigDecimal importoTotale) {
		this.importoTotale = importoTotale;
	}
	public String getOperatoreAggiornamento() {
		return operatoreAggiornamento;
	}
	public void setOperatoreAggiornamento(String operatoreAggiornamento) {
		this.operatoreAggiornamento = operatoreAggiornamento;
	}
	public String getOperatoreAnnullamento() {
		return operatoreAnnullamento;
	}
	public void setOperatoreAnnullamento(String operatoreAnnullamento) {
		this.operatoreAnnullamento = operatoreAnnullamento;
	}
	public String getOperatoreInserimento() {
		return operatoreInserimento;
	}
	public void setOperatoreInserimento(String operatoreInserimento) {
		this.operatoreInserimento = operatoreInserimento;
	}
	public int getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}
	public String getStatoPagamento() {
		return statoPagamento;
	}
	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}
	public String getStatoRiga() {
		return statoRiga;
	}
	public void setStatoRiga(String statoRiga) {
		this.statoRiga = statoRiga;
	}
	public String getTipoCip() {
		return tipoCip;
	}
	public void setTipoCip(String tipoCip) {
		this.tipoCip = tipoCip;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}
	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
    public String getCausale() {
        return causale;
    }
    public void setCausale(String causale) {
        this.causale = causale;
    }
	public String getDenominazioneEnte() {
		return denominazioneEnte;
	}
	public void setDenominazioneEnte(String denominazioneEnte) {
		this.denominazioneEnte = denominazioneEnte;
	}
	public String getDescrizioneTributo() {
		return descrizioneTributo;
	}
	public void setDescrizioneTributo(String descrizioneTributo) {
		this.descrizioneTributo = descrizioneTributo;
	}
    
	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}
	
	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}
	
	public String getRagSocBeneficiario() {
		return ragSocBeneficiario;
	}
	
	public void setRagSocBeneficiario(String ragSocBeneficiario) {
		this.ragSocBeneficiario = ragSocBeneficiario;
	}
		
	public List<IVocePagamento> getVociPagamento() {
		return vociPagamento;
	}

	public void setVociPagamento(List<IVocePagamento> vociPagamento) {
		this.vociPagamento = vociPagamento;
	}
	
	public String getNotePagamento() {
		return notePagamento;
	}
	
	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}
	
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
}
