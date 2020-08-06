package it.tasgroup.idp.domain.posizioneDebitoria;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="getVociPagamento", 
	query=
	"SELECT voci FROM  VociPagamento voci " +
	" WHERE voci.condizioniPagamento.idCondizione =:idCondizione AND voci.idPendenza =:idPendenza AND voci.tiVoce =:tiVoce ")})


/**
 * The persistent class for the JLTVOPD database table.
 * 
 */
@Entity
@Table(name="JLTVOPD")
public class VociPagamento extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idVoce;
	private String coAccertamento;
	private String coCapbilancio;
	private String coVoce;
	private String deVoce;
	private String idPendenza;
	private BigDecimal imVoce;
	private int prVersione;
	private String stRiga;
	private String tiVoce;
	private Timestamp tsDecorrenza;
	
	/*** Persistent Associations ***/
	private CondizioniPagamento condizioniPagamento;


	@Id
	@Column(name="ID_VOCE")
//    @GenericGenerator(name = "voce", strategy="it.tasgroup.idp.domain.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "JLTVOPD_ID_VOCE_SEQ") })
//    @GeneratedValue(generator = "voce")        
	public String getIdVoce() {
		return this.idVoce;
	}

	public void setIdVoce(String idVoce) {
		this.idVoce = idVoce;
	}

	@Column(name="CO_ACCERTAMENTO")
	public String getCoAccertamento() {
		return this.coAccertamento;
	}

	public void setCoAccertamento(String coAccertamento) {
		this.coAccertamento = coAccertamento;
	}

	@Column(name="CO_CAPBILANCIO")
	public String getCoCapbilancio() {
		return this.coCapbilancio;
	}

	public void setCoCapbilancio(String coCapbilancio) {
		this.coCapbilancio = coCapbilancio;
	}

	@Column(name="CO_VOCE")
	public String getCoVoce() {
		return this.coVoce;
	}

	public void setCoVoce(String coVoce) {
		this.coVoce = coVoce;
	}

	@Column(name="DE_VOCE")
	public String getDeVoce() {
		return this.deVoce;
	}

	public void setDeVoce(String deVoce) {
		this.deVoce = deVoce;
	}

	@Column(name="ID_PENDENZA")
	public String getIdPendenza() {
		return this.idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name="IM_VOCE")
	public BigDecimal getImVoce() {
		return this.imVoce;
	}

	public void setImVoce(BigDecimal imVoce) {
		this.imVoce = imVoce;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	@Column(name="TI_VOCE")
	public String getTiVoce() {
		return this.tiVoce;
	}

	public void setTiVoce(String tiVoce) {
		this.tiVoce = tiVoce;
	}

	@Column(name="TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}


	//bi-directional many-to-one association to CondizioniPagamento
    @ManyToOne
	@JoinColumn(name="ID_CONDIZIONE")
	public CondizioniPagamento getCondizioniPagamento() {
		return this.condizioniPagamento;
	}

	public void setCondizioniPagamento(CondizioniPagamento condizioniPagamento) {
		this.condizioniPagamento = condizioniPagamento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VociPagamento [idVoce=");
		builder.append(idVoce);
		builder.append(", coAccertamento=");
		builder.append(coAccertamento);
		builder.append(", coCapbilancio=");
		builder.append(coCapbilancio);
		builder.append(", coVoce=");
		builder.append(coVoce);
		builder.append(", deVoce=");
		builder.append(deVoce);
		builder.append(", idPendenza=");
		builder.append(idPendenza);
		builder.append(", imVoce=");
		builder.append(imVoce);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tiVoce=");
		builder.append(tiVoce);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVoce == null) ? 0 : idVoce.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VociPagamento other = (VociPagamento) obj;
		if (idVoce == null) {
			if (other.idVoce != null) {
				return false;
			}
		} else if (!idVoce.equals(other.idVoce)) {
			return false;
		}
		return true;
	}

	
}