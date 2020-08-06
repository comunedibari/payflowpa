package it.tasgroup.idp.domain.iuv;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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

/**
 * The persistent class for the JLTENTR database table.
 * 
 */
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
	private int prVersione=0;
	
	
	
		
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

	@Column(name="VERSION")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
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