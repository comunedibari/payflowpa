package it.tasgroup.idp.domain.posizioneDebitoria;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the JLTALPE database table.
 * 
 */
@Entity
@Table(name="JLTALPE")
public class AllegatiPendenza  extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idAllegato;
	private byte[] datiBody;
	private String flContesto;
	private String idAntifalsific;
	private String idPendenza;
	private int prVersione;
	private String stRiga;
	private String tiAllegato;
	private String tiCodificaBody;
	private String titolo;
	private Timestamp tsDecorrenza;
	
	/*** Persistent Associations ***/
	private CondizioniPagamento condizioniPagamento;

    
	@Id
	@Column(name="ID_ALLEGATO")
//    @GenericGenerator(name = "alleg", strategy="it.tasgroup.idp.domain.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "JLTALPE_ID_ALLEGATO_SEQ") })
//    @GeneratedValue(generator = "alleg")        
	public String getIdAllegato() {
		return this.idAllegato;
	}

	public void setIdAllegato(String idAllegato) {
		this.idAllegato = idAllegato;
	}

    @Lob()
	@Column(name="DATI_BODY")
	public byte[] getDatiBody() {
		return this.datiBody;
	}

	public void setDatiBody(byte[] datiBody) {
		this.datiBody = datiBody;
	}

	@Column(name="FL_CONTESTO")
	public String getFlContesto() {
		return this.flContesto;
	}

	public void setFlContesto(String flContesto) {
		this.flContesto = flContesto;
	}

	@Column(name="ID_ANTIFALSIFIC")
	public String getIdAntifalsific() {
		return this.idAntifalsific;
	}

	public void setIdAntifalsific(String idAntifalsific) {
		this.idAntifalsific = idAntifalsific;
	}

	@Column(name="ID_PENDENZA")
	public String getIdPendenza() {
		return this.idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
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

	@Column(name="TI_ALLEGATO")
	public String getTiAllegato() {
		return this.tiAllegato;
	}

	public void setTiAllegato(String tiAllegato) {
		this.tiAllegato = tiAllegato;
	}

	@Column(name="TI_CODIFICA_BODY")
	public String getTiCodificaBody() {
		return this.tiCodificaBody;
	}

	public void setTiCodificaBody(String tiCodificaBody) {
		this.tiCodificaBody = tiCodificaBody;
	}

	public String getTitolo() {
		return this.titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
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
		builder.append("AllegatiPendenza [idAllegato=");
		builder.append(idAllegato);
		builder.append(", datiBody=");
		builder.append(Arrays.toString(datiBody));
		builder.append(", flContesto=");
		builder.append(flContesto);
		builder.append(", idAntifalsific=");
		builder.append(idAntifalsific);
		builder.append(", idPendenza=");
		builder.append(idPendenza);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tiAllegato=");
		builder.append(tiAllegato);
		builder.append(", tiCodificaBody=");
		builder.append(tiCodificaBody);
		builder.append(", titolo=");
		builder.append(titolo);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		//builder.append(", condizioniPagamento=");
		//builder.append(condizioniPagamento);
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAllegato == null) ? 0 : idAllegato.hashCode());
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
		AllegatiPendenza other = (AllegatiPendenza) obj;
		if (idAllegato == null) {
			if (other.idAllegato != null) {
				return false;
			}
		} else if (!idAllegato.equals(other.idAllegato)) {
			return false;
		}
		return true;
	}

	
}