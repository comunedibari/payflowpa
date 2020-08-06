/**
 * 
 */
package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumTipoAllegato;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author pazzik
 *
 */
public class AllegatiPendenzaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idAllegato;
	private byte[] datiBody;
	private String flContesto;
	private String idAntifalsific;
	private String idPendenza;
	private String idCondizione;
	private String stRiga;
	private EnumTipoAllegato tiAllegato;
	private String tiCodificaBody;
	private String titolo;
	private Timestamp tsDecorrenza;
	private boolean compressione;
	
	public String getIdAllegato() {
		return idAllegato;
	}
	
	public void setIdAllegato(String idAllegato) {
		this.idAllegato = idAllegato;
	}
	
	public byte[] getDatiBody() {
		return datiBody;
	}
	
	public void setDatiBody(byte[] datiBody) {
		this.datiBody = datiBody;
	}
	
	public String getFlContesto() {
		return flContesto;
	}
	
	public void setFlContesto(String flContesto) {
		this.flContesto = flContesto;
	}
	
	public String getIdAntifalsific() {
		return idAntifalsific;
	}
	
	public void setIdAntifalsific(String idAntifalsific) {
		this.idAntifalsific = idAntifalsific;
	}
	
	public String getIdPendenza() {
		return idPendenza;
	}
	
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	
	public String getIdCondizione() {
		return idCondizione;
	}
	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}
	
	public String getStRiga() {
		return stRiga;
	}
	
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	public EnumTipoAllegato getTiAllegato() {
		return tiAllegato;
	}
	public void setTiAllegato(EnumTipoAllegato tiAllegato) {
		this.tiAllegato = tiAllegato;
	}
	
	public String getTiCodificaBody() {
		return tiCodificaBody;
	}
	
	public void setTiCodificaBody(String tiCodificaBody) {
		this.tiCodificaBody = tiCodificaBody;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public Timestamp getTsDecorrenza() {
		return tsDecorrenza;
	}
	
	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isCompressione() {
		return compressione;
	}

	public void setCompressione(boolean compressione) {
		this.compressione = compressione;
	}


}
