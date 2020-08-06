package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the DOCUMENTI_REPOSITORY database table.
 * 
 */
@Entity
@Table(name="DOCUMENTI_REPOSITORY")
@NamedQueries( 
		{
			@NamedQuery(name="getVersion",query="select doc.version from DocumentiRepository doc where doc.id =:ID")
			
		}
	)
public class DocumentiRepository extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer dimensione;
	private byte[] documento;
	private String nomeFile;
	private String ackDownload;
	private String opAggiornamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

    public DocumentiRepository() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="documenti_repository_pk_gen")	
	@SequenceGenerator(
		    name="documenti_repository_pk_gen",
		    sequenceName="DOCUMENTI_REPOSITORY_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		    


	public Integer getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}


    @Lob()
	public byte[] getDocumento() {
		return this.documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}


	@Column(name="NOME_FILE")
	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	@Column(name="ACK_DOWNLOAD")
	public String getAckDownload() {
		return this.ackDownload;
	}

	public void setAckDownload(String ackDownload) {
		this.ackDownload = ackDownload;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
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


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

}