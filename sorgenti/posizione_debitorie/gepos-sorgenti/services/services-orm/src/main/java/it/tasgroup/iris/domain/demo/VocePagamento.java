package it.tasgroup.iris.domain.demo;

import it.tasgroup.iris.domain.BaseEntity;
import it.tasgroup.services.util.IVocePagamento;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JLTVOPD")
public class VocePagamento  extends BaseEntity implements IVocePagamento{

	private static final long serialVersionUID = 1L;
	
	private String idPendenza;
	private String idCondizione;
	private String idVoce;
	private Timestamp tsDecorrenza;
	private String tiVoce;
	private String coVoce;
	private String deVoce;
	private BigDecimal imVoce;
	private String coCapBilancio;
	private String coAccertamento;
	private String stRiga;
	private int prVersione;
	private String opInserimento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;

	public VocePagamento() {
	}

	@Column(name="ID_PENDENZA", nullable=false, length=40)
	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name="ID_CONDIZIONE", nullable=false, length=40)
	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	@Id
	@Column(name="ID_VOCE", unique=true, nullable=false, length=40)
	public String getIdVoce() {
		return idVoce;
	}

	public void setIdVoce(String idVoce) {
		this.idVoce = idVoce;
	}

	@Column(name="TS_DECORRENZA", nullable=false)
	public Timestamp getTsDecorrenza() {
		return tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}

	@Column(name="TI_VOCE", nullable=false, length=70)
	public String getTiVoce() {
		return tiVoce;
	}

	public void setTiVoce(String tiVoce) {
		this.tiVoce = tiVoce;
	}

	@Column(name="CO_VOCE", nullable=false, length=70)
	public String getCoVoce() {
		return coVoce;
	}

	public void setCoVoce(String coVoce) {
		this.coVoce = coVoce;
	}

	@Column(name="DE_VOCE", nullable=false, length=510)
	public String getDeVoce() {
		return deVoce;
	}

	public void setDeVoce(String deVoce) {
		this.deVoce = deVoce;
	}

	@Column(name="IM_VOCE", nullable=false, precision=15, scale=2)
	public BigDecimal getImVoce() {
		return imVoce;
	}

	public void setImVoce(BigDecimal imVoce) {
		this.imVoce = imVoce;
	}

	@Column(name="CO_CAPBILANCIO", length=70)
	public String getCoCapBilancio() {
		return coCapBilancio;
	}

	public void setCoCapBilancio(String coCapBilancio) {
		this.coCapBilancio = coCapBilancio;
	}

	@Column(name="CO_ACCERTAMENTO", length=70)
	public String getCoAccertamento() {
		return coAccertamento;
	}

	public void setCoAccertamento(String coAccertamento) {
		this.coAccertamento = coAccertamento;
	}

	@Column(name="ST_RIGA", nullable=false, length=2)
	public String getStRiga() {
		return stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	@Column(name="PR_VERSIONE", nullable=false)
	public int getPrVersione() {
		return prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="OP_INSERIMENTO", nullable=false, length=80)
	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO", length=80)
	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	
}
