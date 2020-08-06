package it.nch.is.fo.tributi;

import it.tasgroup.iris.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
	private String opInserimento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;
	
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
	
	
	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
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
		builder.append(", version=");
		builder.append(getVersion());
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