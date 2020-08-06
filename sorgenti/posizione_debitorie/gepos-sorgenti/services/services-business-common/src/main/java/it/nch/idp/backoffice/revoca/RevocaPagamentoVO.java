package it.nch.idp.backoffice.revoca;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class RevocaPagamentoVO {
	
	private Date dataRichiesta; 
	private	String idRichiesta; 
	private	String descrizioneAttestante;
	private	String idAttestante;
	private	String motivoRevoca; 
	private	String iuv; 
	private	String causaleRichiesta;
	private	String datiAggiuntiviRichiesta;
	private	Date dataAR; 
	private	String causaleEsito; 
	private	String datiAggiuntiviEsito; 
	private	String operatoreAR; 
	
	private String idPagamento;
	private String idRevoca;
	
	private String statoRevoca;
	private Timestamp tsAggiornamento; 
	
	public String getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(String idRevoca) {
		this.idRevoca = idRevoca;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	private ArrayList<String> idTributoLista ;
	

	
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public String getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public String getMotivoRevoca() {
		return motivoRevoca;
	}
	public void setMotivoRevoca(String motivoRevoca) {
		this.motivoRevoca = motivoRevoca;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	
	public Date getDataAR() {
		return dataAR;
	}
	public void setDataAR(Date dataAR) {
		this.dataAR = dataAR;
	}
	public String getOperatoreAR() {
		return operatoreAR;
	}
	public void setOperatoreAR(String operatoreAR) {
		this.operatoreAR = operatoreAR;
	}
	public ArrayList<String> getIdTributoLista() {
		return idTributoLista;
	}
	public void setIdTributoLista(ArrayList<String> idTributoLista) {
		this.idTributoLista = idTributoLista;
	}
	public String getDescrizioneAttestante() {
		return descrizioneAttestante;
	}
	public void setDescrizioneAttestante(String descrizioneAttestante) {
		this.descrizioneAttestante = descrizioneAttestante;
	}
	public String getIdAttestante() {
		return idAttestante;
	}
	public void setIdAttestante(String idAttestante) {
		this.idAttestante = idAttestante;
	}
	public String getCausaleRichiesta() {
		return causaleRichiesta;
	}
	public void setCausaleRichiesta(String causaleRichiesta) {
		this.causaleRichiesta = causaleRichiesta;
	}
	public String getDatiAggiuntiviRichiesta() {
		return datiAggiuntiviRichiesta;
	}
	public void setDatiAggiuntiviRichiesta(String datiAggiuntiviRichiesta) {
		this.datiAggiuntiviRichiesta = datiAggiuntiviRichiesta;
	}
	public String getCausaleEsito() {
		return causaleEsito;
	}
	public void setCausaleEsito(String causaleEsito) {
		this.causaleEsito = causaleEsito;
	}
	public String getDatiAggiuntiviEsito() {
		return datiAggiuntiviEsito;
	}
	public void setDatiAggiuntiviEsito(String datiAggiuntiviEsito) {
		this.datiAggiuntiviEsito = datiAggiuntiviEsito;
	}
	public String getStatoRevoca() {
		return statoRevoca;
	}
	public void setStatoRevoca(String statoRevoca) {
		this.statoRevoca = statoRevoca;
	}
	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	
	public String toString() {
		return "RevocaPagamentoVO ["
				+ "idRevoca: " + idRevoca
				+ " - dataRichiesta: " + dataRichiesta
				+ " - idRichiesta: " + idRichiesta
				+ " - descrizioneAttestante: " + descrizioneAttestante
				+ " - idAttestante: " + idAttestante
				+ " - motivoRevoca: " + motivoRevoca
				+ " - iuv: " + iuv
				+ " - causaleRichiesta: " + causaleRichiesta
				+ " - datiAggiuntiviRichiesta: " + datiAggiuntiviRichiesta
				+ " - dataAR: " + dataAR
				+ " - causaleEsito: " + causaleEsito
				+ " - datiAggiuntiviEsito: " + datiAggiuntiviEsito
				+ " - operatoreAR: " + operatoreAR
				+ " - idPagamento: " + idPagamento
				
				+ " - statoRevoca: " + statoRevoca
				+ " - tsAggiornamento: " + tsAggiornamento 
				+ "]";  
	}
	
}
