package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the DOCUMENTI_REPOSITORY database table.
 * 
 */
@Entity
@Table(name="DOCUMENTI_REPOSITORY")
public class DocumentiRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int dimensione;

	@Lob
	private byte[] documento;

	@Column(name="NOME_FILE")
	private String nomeFile;

	@Column(name="OP_AGGIORNAMENTO")
	private String opAggiornamento;

	@Column(name="OP_INSERIMENTO")
	private String opInserimento;

	@Column(name="TS_AGGIORNAMENTO")
	private Timestamp tsAggiornamento;

	@Column(name="TS_INSERIMENTO")
	private Timestamp tsInserimento;

	@Column(name="\"VERSION\"")
	private int version;

	//bi-directional many-to-one association to DocumentiPagamento
	@OneToMany(mappedBy="documentiRepository")
	private List<DocumentiPagamento> documentiPagamentos;

	public DocumentiRepository() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(int dimensione) {
		this.dimensione = dimensione;
	}

	public byte[] getDocumento() {
		return this.documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<DocumentiPagamento> getDocumentiPagamentos() {
		return this.documentiPagamentos;
	}

	public void setDocumentiPagamentos(List<DocumentiPagamento> documentiPagamentos) {
		this.documentiPagamentos = documentiPagamentos;
	}

	public DocumentiPagamento addDocumentiPagamento(DocumentiPagamento documentiPagamento) {
		getDocumentiPagamentos().add(documentiPagamento);
		documentiPagamento.setDocumentiRepository(this);

		return documentiPagamento;
	}

	public DocumentiPagamento removeDocumentiPagamento(DocumentiPagamento documentiPagamento) {
		getDocumentiPagamentos().remove(documentiPagamento);
		documentiPagamento.setDocumentiRepository(null);

		return documentiPagamento;
	}

}