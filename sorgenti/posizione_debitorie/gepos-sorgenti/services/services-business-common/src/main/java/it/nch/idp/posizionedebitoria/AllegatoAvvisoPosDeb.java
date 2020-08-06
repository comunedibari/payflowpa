package it.nch.idp.posizionedebitoria;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;

public class AllegatoAvvisoPosDeb extends Downloadable{

	private static final long serialVersionUID = 5593901531521953866L;

	private String idallegato;
	private String idpendenza;
	private String idcondizione;
	private Timestamp tsDecorrenza;
	private String flContesto;
	private String tiAllegato;
	private String titolo;
	private String tiCodificaBody;
	private Blob datiBody;
	private byte[] datiBodyAsBytes;
	private String stRiga;
	private Integer versione;


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("idallegato="+idallegato);
		sb.append("; idpendenza="+idpendenza);
		sb.append("; idcondizione="+idcondizione);
		sb.append("; tsDecorrenza="+tsDecorrenza);
		sb.append("; flContesto="+flContesto);
		sb.append("; tiAllegato="+tiAllegato);
		sb.append("; titolo="+titolo);
		sb.append("; tiCodificaBody="+tiCodificaBody);
		sb.append("; datiBody="+datiBody);
		sb.append("; stRiga="+stRiga);
		sb.append("; versione="+versione);
		sb.append("]");

		return sb.toString();
	}


	public Blob getDatiBody() {
		return datiBody;
	}

	public String getFlContesto() {
		return flContesto;
	}

	public String getIdallegato() {
		if(idallegato!=null)	return idallegato.trim();
		else	return idallegato;
	}

	public String getIdcondizione() {
		if(idcondizione!=null)	return idcondizione.trim();
		else	return idcondizione;
	}

	public String getIdpendenza() {
		if(idpendenza!=null)	return idpendenza.trim();
		else	return idpendenza;
	}

	public String getStRiga() {
		return stRiga;
	}

	public String getTiAllegato() {
		return tiAllegato;
	}

	public String getTiCodificaBody() {
		return tiCodificaBody;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setDatiBody(Blob blob) {
		datiBody = blob;
		datiBodyAsBytes = setDownloadableStream(blob);
	}

	public void setFlContesto(String string) {
		flContesto = string;
	}

	public void setIdallegato(String string) {
		idallegato = string;
	}

	public void setIdcondizione(String string) {
		idcondizione = string;
	}

	public void setIdpendenza(String string) {
		idpendenza = string;
	}

	public void setStRiga(String string) {
		stRiga = string;
	}

	public void setTiAllegato(String string) {
		tiAllegato = string;
	}

	public void setTiCodificaBody(String string) {
		tiCodificaBody = string;
	}

	public void setTitolo(String string) {
		titolo = string;
	}

	public Integer getVersione() {
		return versione;
	}

	public void setVersione(Integer integer) {
		versione = integer;
	}

	public Timestamp getTsDecorrenza() {
		return tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp timestamp) {
		tsDecorrenza = timestamp;
	}

	public byte[] getDatiBodyAsBytes() {
		return datiBodyAsBytes;
	}

	public void setDatiBodyAsBytes(byte[] bs) {
		datiBodyAsBytes = bs;
	}

}
