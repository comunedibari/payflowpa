package it.tasgroup.iris.domain;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


/**
 * The persistent class for the BACHECA_NEWS database table.
 * 
 */
@Entity
@Table(name="BACHECA_NEWS")

public class BachecaNews extends BaseIdEntity {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long priorita;
	private String titolo;
	private String messaggio;
	private Date dataDecorrenza;
	private Date dataScadenza;
	
	private byte[] imgExtBlob;
	private String nomeFileImgExt;
	 
	private byte[] imgIntBlob;
	private String nomeFileImgInt;

	private String opAggiornamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;	 
	}		
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	public BachecaNews() {
	}

	
	@Column(name="PRIORITA")
	public Long getPriorita() {
		return priorita;
	}

	public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}

	@Lob()
	@Column(name="MESSAGGIO")
	public String getMessaggio() {
		return this.messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	@Column(name="TITOLO")
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	@Column(name="DATA_DECORRENZA")
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	@Column(name="DATA_SCADENZA")
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	@Column(name="IMG_EXT_BLOB")
	public byte[] getImgExtBlob() {
		return imgExtBlob;
	}
	public void setImgExtBlob(byte[] imgExtBlob) {
		this.imgExtBlob = imgExtBlob;
	}
	@Column(name="NOME_FILE_EXT_IMG")
	public String getNomeFileImgExt() {
		return nomeFileImgExt;
	}
	public void setNomeFileImgExt(String nomeFileImgExt) {
		this.nomeFileImgExt = nomeFileImgExt;
	}
	@Column(name="IMG_INT_BLOB")
	public byte[] getImgIntBlob() {
		return imgIntBlob;
	}
	public void setImgIntBlob(byte[] imgIntBlob) {
		this.imgIntBlob = imgIntBlob;
	}
	@Column(name="NOME_FILE_INT_IMG")
	public String getNomeFileImgInt() {
		return nomeFileImgInt;
	}
	public void setNomeFileImgInt(String nomeFileImgInt) {
		this.nomeFileImgInt = nomeFileImgInt;
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