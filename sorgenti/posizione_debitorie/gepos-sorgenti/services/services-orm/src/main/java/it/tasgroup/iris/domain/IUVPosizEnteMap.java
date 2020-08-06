package it.tasgroup.iris.domain;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the JLTENTR database table.
 * 
 */

@NamedQueries({
@NamedQuery(
	name="IUVPosizEnteMapByIdEnteAndIdPagamento", 
	query=
	"SELECT iuvposmap FROM IUVPosizEnteMap iuvposmap " +
	" WHERE iuvposmap.id.idEnte = :idEnte and " +
	" iuvposmap.idPagamento = :idPagamento and iuvposmap.stRiga = 'V' "),
@NamedQuery(
			name="IUVPosizEnteMapByIdEnteAndIuv", 
			query=
			"SELECT iuvposmap FROM IUVPosizEnteMap iuvposmap " +
			" WHERE iuvposmap.id.idEnte = :idEnte and " +
			" iuvposmap.id.iuv = :iuv and iuvposmap.stRiga = 'V' "),
@NamedQuery(
			name="AnnullaIUVPosizEnteMapByIdEnteAndIuv", 
			query=
			"update IUVPosizEnteMap iuvposmap set iuvposmap.stRiga='A', iuvposmap.tsAggiornamento =:tsAggiornamento, iuvposmap.opAggiornamento =:opAggiornamento, " +
			" iuvposmap.tsAnnullamento =:tsAggiornamento, iuvposmap.opAnnullamento =:opAggiornamento " +
			" WHERE iuvposmap.id.idEnte = :idEnte and " +
			" iuvposmap.id.iuv = :iuv and iuvposmap.stRiga = 'V' ")
})

@Entity
@Table(name="IUVMAP")
public class IUVPosizEnteMap extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private IUVPosizEnteMapPK id;
	
	
	private String cdTrbEnte;
	private String idPagamento;
	private String stRiga;
	private Timestamp tsAnnullamento;
	private String opAnnullamento;
	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsInserimento;
	private Timestamp tsAggiornamento;

	private int prVersione=0;
	
	
	public IUVPosizEnteMap() {
		id = new IUVPosizEnteMapPK();
	}
	
	public IUVPosizEnteMap(IUVPosizEnteMapPK key) {
		id = key;
	}
	
	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	@Column(name="ID_PAGAMENTO")
	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	@Column(name="ST_RIGA")
	public String getStRiga() {
		return stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	
	@EmbeddedId
	public IUVPosizEnteMapPK getId() {
		return id;
	}

	public void setId(IUVPosizEnteMapPK id) {
		this.id = id;
	}
    
	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}
    
	@Column(name="OP_ANNULLAMENTO")
	public String getOpAnnullamento() {
		return opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
		
	}	

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
		
	}
	
	@Column(name="ID_ENTE",insertable=false,updatable=false)
	public String getIdEnte() {
		return this.id.getIdEnte();
	}
	public void setIdEnte(String idEnte) {
		this.id.setIdEnte(idEnte);
	}
	
	@Column(name="IUV",insertable=false,updatable=false)
	public String getIuv() {
		return this.id.getIuv();
	}
	public void setIuv(String iuv) {
		this.id.setIuv(iuv);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IUVPosizEnteMap [idEnte=");
		builder.append(id.getIdEnte());
		builder.append(", cdTrbEnte=");
		builder.append(cdTrbEnte);
		builder.append(", idPagamento=");
		builder.append(idPagamento);
		builder.append(", iuv=");
		builder.append(id.getIuv());
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", soggEsclusi=");		
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
	
	
}