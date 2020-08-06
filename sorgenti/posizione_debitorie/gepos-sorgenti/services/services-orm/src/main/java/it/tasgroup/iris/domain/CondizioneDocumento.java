package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="CONDIZIONI_DOCUMENTO")
public class CondizioneDocumento extends BaseIdEntity {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Properties ***/
	private String opAggiornamento;
	private String opAnnullamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsAnnullamento;
	private Timestamp tsInserimento;
	
	/*** Persistent References ***/
	private CondizionePagamento condizionePagamento;
	private DocumentoDiPagamento documento;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="condizione_documento_pk_gen")	
	@SequenceGenerator(
		    name="condizione_documento_pk_gen",
		    sequenceName="CONDIZIONI_DOCUMENTO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	@Column(name="OP_AGGIORNAMENTO", length=160)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="OP_ANNULLAMENTO", length=160)
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}
	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}

	@Column(name="OP_INSERIMENTO", nullable=false, length=40)
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}
	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}

	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

    @ManyToOne
	@JoinColumn(name="ID_CONDIZIONE")
	public CondizionePagamento getCondizionePagamento() {
		return this.condizionePagamento;
	}
	public void setCondizionePagamento(CondizionePagamento condizionePagamento) {
		this.condizionePagamento = condizionePagamento;
	}
	
	//bi-directional many-to-one association to DocumentoDiPagamento
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="ID_DOCUMENTO")
	public DocumentoDiPagamento getDocumento() {
		return this.documento;
	}
	public void setDocumento(DocumentoDiPagamento documento) {
		this.documento = documento;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getCondizionePagamento().getIdCondizione() == null || getDocumento().getId() == null) ? 0 : getCondizionePagamento().getIdCondizione().hashCode()*getDocumento().getId().hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CondizioneDocumento other = (CondizioneDocumento) obj;
		if (getCondizionePagamento().getIdCondizione() == null) {
			if (other.getCondizionePagamento().getIdCondizione() != null)
				return false;
		} else if (!getCondizionePagamento().getIdCondizione().equals(other.getCondizionePagamento().getIdCondizione()))
			return false;
		if (getDocumento().getId() == null) {
			if (other.getDocumento().getId() != null)
				return false;
		} else if (!getDocumento().getId().equals(other.getDocumento().getId()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondizioneDocumento [id=");
		builder.append(getId());
		builder.append(", opAggiornamento=");
		builder.append(opAggiornamento);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", tsAggiornamento=");
		builder.append(tsAggiornamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append(", condizionePagamento=");
		builder.append(condizionePagamento);
		builder.append(", documento=");
		builder.append(documento.getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}
	
}