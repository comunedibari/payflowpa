package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MOLTEPLICITA_COND_DDP")
//@Table(name="JLTMODP")
@NamedQueries(
{	
	@NamedQuery(
			name="getMolteplicitaDocumentoDiPagamentoByTipo",
			query="select molt from MolteplicitaDDP molt "+
				  "where molt.tiDocumento =:tipoDoc")
		}
)
public class MolteplicitaDDP extends BaseIdEntity {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Properties ***/

	private int molteplicita;
	private String opAggiornamento;
	private String opAnnullamento;
	private String tiDocumento;
	private Timestamp tsAggiornamento;
	private Timestamp tsAnnullamento;
	private Timestamp tsInserimento;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="molteplicita_ddp_pk_gen")	
	@SequenceGenerator(
		    name="molteplicita_ddp_pk_gen",
		    sequenceName="HIBERNATE_SEQUENCE",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	public int getMolteplicita() {
		return this.molteplicita;
	}
	public void setMolteplicita(int molteplicita) {
		this.molteplicita = molteplicita;
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

	@Column(name="TI_DOCUMENTO", nullable=false, length=2)
	public String getTiDocumento() {
		return this.tiDocumento;
	}
	public void setTiDocumento(String tiDocumento) {
		this.tiDocumento = tiDocumento;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getTiDocumento() == null) ? 0 : getTiDocumento().hashCode());
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
		MolteplicitaDDP other = (MolteplicitaDDP) obj;
		if (getTiDocumento() == null) {
			if (other.getTiDocumento() != null)
				return false;
		} else if (!getTiDocumento().equals(other.getTiDocumento()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MolteplicitaDDP [id=");
		builder.append(getId());
		builder.append(", molteplicita=");
		builder.append(molteplicita);
		builder.append(", opAggiornamento=");
		builder.append(opAggiornamento);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", tiDocumento=");
		builder.append(tiDocumento);
		builder.append(", tsAggiornamento=");
		builder.append(tsAggiornamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}
	

	
}