package it.tasgroup.idp.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class PrevisitingData implements Serializable {

	String E2EMsgId = null;
	String idMittente = null;
	String silMittente = null;
	String idRicevente = null;
	String silRicevente = null;
	String stato = null;
	int numPendXPath = 0;
	boolean consistenzaTipoOperazione = true;
	boolean pendenzeUnivoche = true;
	boolean idPagamentoUnivoci = true;
	boolean tipoPendenzaUnivoco = true;
	String tipoOperazione = null;
	String tipoPendenza = null;
	//iban
	HashSet iban = null;

	boolean allMassivoCompletato = true;

	public boolean isAllMassivoCompletato() {
		return allMassivoCompletato;
	}
	public void setAllMassivoCompletato(boolean allMassivoCompletato) {
		this.allMassivoCompletato = allMassivoCompletato;
	}
	public String getTipoPendenza() {
		return tipoPendenza;
	}
	public void setTipoPendenza(String tipoPendenza) {
		this.tipoPendenza = tipoPendenza;
	}
	public boolean isTipoPendenzaUnivoco() {
		return tipoPendenzaUnivoco;
	}
	public void setTipoPendenzaUnivoco(boolean tipoPendenzaUnivoco) {
		this.tipoPendenzaUnivoco = tipoPendenzaUnivoco;
	}
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public boolean isConsistenzaTipoOperazione() {
		return consistenzaTipoOperazione;
	}
	public void setConsistenzaTipoOperazione(boolean consistenzaTipoOperazione) {
		this.consistenzaTipoOperazione = consistenzaTipoOperazione;
	}

	public String getE2EMsgId() {
		return E2EMsgId;
	}
	public void setE2EMsgId(String e2eMsgId) {
		E2EMsgId = e2eMsgId;
	}
	public String getIdMittente() {
		return idMittente;
	}
	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}
	public String getSilMittente() {
		return silMittente;
	}
	public void setSilMittente(String silMittente) {
		this.silMittente = silMittente;
	}
	public String getIdRicevente() {
		return idRicevente;
	}
	public void setIdRicevente(String idRicevente) {
		this.idRicevente = idRicevente;
	}
	public String getSilRicevente() {
		return silRicevente;
	}
	public void setSilRicevente(String silRicevente) {
		this.silRicevente = silRicevente;
	}
	public int getNumPendXPath() {
		return numPendXPath;
	}
	public void setNumPendXPath(int numPendXPath) {
		this.numPendXPath = numPendXPath;
	}
	public boolean isPendenzeUnivoche() {
		return pendenzeUnivoche;
	}
	public void setPendenzeUnivoche(boolean pendenzeUnivoche) {
		this.pendenzeUnivoche = pendenzeUnivoche;
	}
	public boolean isIdPagamentoUnivoci() {
		return idPagamentoUnivoci;
	}
	public void setIdPagamentoUnivoci(boolean idPagamentoUnivoci) {
		this.idPagamentoUnivoci = idPagamentoUnivoci;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}	
	public HashSet getIban() {
		return iban;
	}
	public void setIban(HashSet iban) {
		this.iban = iban;
	}
	
}
