package it.tasgroup.idp.domain.iuv;

import it.tasgroup.idp.domain.BaseEntity;
import it.tasgroup.idp.domain.enti.TributiEntiPK;

import java.io.Serializable;
import java.math.BigInteger;

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
			name="PrenotaIUVByIdEnteAndTributo", 
			query=
			"update IUVSequence iuvseq set iuvseq.tsAggiornamento =:tsAggiornamento, iuvseq.opAggiornamento =:opAggiornamento, " +
			" iuvseq.lastSeqIuv = iuvseq.lastSeqIuv + :numToBook " +
			" WHERE iuvseq.id.idEnte = :idEnte and " +
			" iuvseq.id.cdTrbEnte = :cdTrbEnte and iuvseq.stRiga = 'V' ")
})
/**
 * The persistent class for the SEQ_IUV database table.
 * 
 */
@Entity
@Table(name="SEQ_IUV")
public class IUVSequence extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private IUVSequencePK id;	
	private BigInteger lastSeqIuv;
	private String stRiga;
	private int prVersione;
	
	@EmbeddedId
	public IUVSequencePK getId() {
		return this.id;
	}

	public void setId(IUVSequencePK id) {
		this.id = id;
	}
	
	@Column(name="ST_RIGA")
	public String getStRiga() {
		return stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	@Column(name="LAST_SEQ_IUV")
	public BigInteger getLastSeqIuv() {
		return lastSeqIuv;
	}

	public void setLastSeqIuv(BigInteger lastSeqIuv) {
		this.lastSeqIuv = lastSeqIuv;
	}	
	
	@Column(name="VERSION")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IUVPosizEnteMap [idEnte=");
		builder.append(id.getIdEnte());
		builder.append(", cdTrbEnte=");
		builder.append(id.getCdTrbEnte());
		builder.append(", lastSeqIuv=");
		builder.append(lastSeqIuv);
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