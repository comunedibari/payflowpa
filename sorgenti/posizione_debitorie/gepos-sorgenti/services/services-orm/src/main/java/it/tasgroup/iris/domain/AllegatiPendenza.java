package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "JLTALPE")
@NamedQueries({
	@NamedQuery(name = "getAllAllegatiCondizione",
	query = "select allegato from AllegatiPendenza allegato where idPendenza = :idPendenza and condizioniPagamento.idCondizione = :idCondizione and flContesto ='C' and stRiga='V' and tiAllegato not in ('Ricevuta','Quietanza')"), 
	@NamedQuery(name = "getAllegatiByType",
		query = "select allegato from AllegatiPendenza allegato where idPendenza = :idPendenza and condizioniPagamento.idCondizione = :idCondizione and tiAllegato=:tiAllegato"), 
	@NamedQuery(name = "getAllegatoCondizione",
		query = "select allegato from AllegatiPendenza allegato where idPendenza = :idPendenza and condizioniPagamento.idCondizione = :idCondizione and tiAllegato = :tiAllegato and tiCodificaBody = :tiCodificaBody") 
})
public class AllegatiPendenza extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idAllegato;
	private byte[] datiBody;
	private String flContesto;
	private String idAntifalsific;
	private String idPendenza;
	private String idCondizione;
	private int prVersione;
	private String stRiga;
	private String tiAllegato;
	private String tiCodificaBody;
	private String titolo;
	private Timestamp tsDecorrenza;

	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsInserimento;
	private Timestamp tsAggiornamento;
	
	/*** Persistent Associations ***/
	private CondizionePagamento condizioniPagamento;

	
	@Transient
	private AllegatiPendenza allegatoXML;

	@Id
	@Column(name = "ID_ALLEGATO")
	public String getIdAllegato() {
		return this.idAllegato;
	}

	public void setIdAllegato(String idAllegato) {
		this.idAllegato = idAllegato;
	}

	@Lob
	@Column(name = "DATI_BODY")
	public byte[] getDatiBody() {
		return this.datiBody;
	}

	public void setDatiBody(byte[] datiBody) {
		this.datiBody = datiBody;
	}

	@Column(name = "FL_CONTESTO")
	public String getFlContesto() {
		return this.flContesto;
	}

	public void setFlContesto(String flContesto) {
		this.flContesto = flContesto;
	}

	@Column(name = "ID_ANTIFALSIFIC")
	public String getIdAntifalsific() {
		return this.idAntifalsific;
	}

	public void setIdAntifalsific(String idAntifalsific) {
		this.idAntifalsific = idAntifalsific;
	}

	@Column(name = "ID_PENDENZA")
	public String getIdPendenza() {
		return this.idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name = "PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name = "ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	@Column(name = "TI_ALLEGATO")
	public String getTiAllegato() {
		return this.tiAllegato;
	}

	public void setTiAllegato(String tiAllegato) {
		this.tiAllegato = tiAllegato;
	}

	@Column(name = "TI_CODIFICA_BODY")
	public String getTiCodificaBody() {
		return this.tiCodificaBody;
	}

	public void setTiCodificaBody(String tiCodificaBody) {
		this.tiCodificaBody = tiCodificaBody;
	}

	@Column(name = "TITOLO")
	public String getTitolo() {
		return this.titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Column(name = "TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}

	@Column(name = "OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name = "OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name = "TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name = "TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@PrePersist
	public void completeForInsert() {
		if (this.tsInserimento == null)
			this.tsInserimento = new Timestamp(System.currentTimeMillis());
		if (this.opInserimento == null)
			this.opInserimento = "IRIS";
	}

	@PreUpdate
	public void completeForUpdate() {
		if (this.tsAggiornamento == null)
			this.tsAggiornamento = new Timestamp(System.currentTimeMillis());
		if (this.opAggiornamento == null)
			this.opAggiornamento = "IRIS";
	}

	@Transient
	public String getIdCondizione() {
		return condizioniPagamento.getIdCondizione();
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	//bi-directional many-to-one association to CondizioniPagamento
    @ManyToOne
	@JoinColumn(name="ID_CONDIZIONE")
	public CondizionePagamento getCondizioniPagamento() {
		return this.condizioniPagamento;
	}

	public void setCondizioniPagamento(CondizionePagamento condizioniPagamento) {
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
		// builder.append(", condizionePagamento=");
		// builder.append(condizionePagamento);
		builder.append(", idCondizione=");
		builder.append(idCondizione);
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
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
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

	@Transient
	public AllegatiPendenza getAllegatoXML() {
		return allegatoXML;
	}

	@Transient
	public void setAllegatoXML(AllegatiPendenza allegatoXML) {
		this.allegatoXML = allegatoXML;
	}
	
}
