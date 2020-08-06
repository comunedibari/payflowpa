package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class FlussoDto {

	private String idFlusso;
	private String nome;
	private String dataCaricamento;
	private String operatore;
	private String codStato;
	private String stato;

	public FlussoDto() {
		super();
	}

	public String getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(String dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getCodStato() {
		return codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

}
