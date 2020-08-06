package it.nch.idp.backoffice.storicodati;

import java.io.Serializable;
import java.sql.Timestamp;


public class StatoSvecchiamentoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nomeProcesso;
	private Integer stato;
	private Timestamp dataUltimaOperazione;
	private Timestamp dataUltimoDump;
	private String nomeFileDump;
	private Timestamp dataUltimoImport;
	private String parametri;

	
	public StatoSvecchiamentoVO() {
	}


	public String getNomeProcesso() {
		return nomeProcesso;
	}


	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}


	public Integer getStato() {
		return stato;
	}


	public void setStato(Integer stato) {
		this.stato = stato;
	}


	public Timestamp getDataUltimaOperazione() {
		return dataUltimaOperazione;
	}


	public void setDataUltimaOperazione(Timestamp dataUltimaOperazione) {
		this.dataUltimaOperazione = dataUltimaOperazione;
	}


	public Timestamp getDataUltimoDump() {
		return dataUltimoDump;
	}


	public void setDataUltimoDump(Timestamp dataUltimoDump) {
		this.dataUltimoDump = dataUltimoDump;
	}


	public String getNomeFileDump() {
		return nomeFileDump;
	}


	public void setNomeFileDump(String nomeFileDump) {
		this.nomeFileDump = nomeFileDump;
	}


	public Timestamp getDataUltimoImport() {
		return dataUltimoImport;
	}


	public void setDataUltimoImport(Timestamp dataUltimoImport) {
		this.dataUltimoImport = dataUltimoImport;
	}


	public String getParametri() {
		return parametri;
	}


	public void setParametri(String parametri) {
		this.parametri = parametri;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
}
