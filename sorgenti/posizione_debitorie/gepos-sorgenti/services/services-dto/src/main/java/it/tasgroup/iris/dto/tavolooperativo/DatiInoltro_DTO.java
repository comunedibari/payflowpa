package it.tasgroup.iris.dto.tavolooperativo;

import it.tasgroup.iris.dto.ddp.ChiaveValoreDTO;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

public class DatiInoltro_DTO implements Serializable{

	private static final long serialVersionUID = -4295620198690244812L;
	
	private String statoConsegna;
	private Long numTentativiRispedizione;
	private byte[] dettaglioErrore;
	private Timestamp timestampLastRetry;
	private String esitoHTTP;	
	
	private List<ChiaveValoreDTO> couplesList;
	
	public DatiInoltro_DTO() {
		
	}

	public String getStatoConsegna() {
		return statoConsegna;
	}

	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}

	public Long getNumTentativiRispedizione() {
		return numTentativiRispedizione;
	}

	public void setNumTentativiRispedizione(Long numTentativiRispedizione) {
		this.numTentativiRispedizione = numTentativiRispedizione;
	}

	public byte[] getDettaglioErrore() {
		return dettaglioErrore;
	}

	public void setDettaglioErrore(byte[] dettaglioErrore) {
		this.dettaglioErrore = dettaglioErrore;
	}

	public Timestamp getTimestampLastRetry() {
		return timestampLastRetry;
	}

	public void setTimestampLastRetry(Timestamp timestampLastRetry) {
		this.timestampLastRetry = timestampLastRetry;
	}

	public String getEsitoHTTP() {
		return esitoHTTP;
	}

	public void setEsitoHTTP(String esitoHTTP) {
		this.esitoHTTP = esitoHTTP;
	}

	public List<ChiaveValoreDTO> getCouplesList() {
		return couplesList;
	}

	public void setCouplesList(List<ChiaveValoreDTO> couplesList) {
		this.couplesList = couplesList;
	}
	
	public String getDettaglioErroreString(){
	
		try {
			return new String(dettaglioErrore,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
