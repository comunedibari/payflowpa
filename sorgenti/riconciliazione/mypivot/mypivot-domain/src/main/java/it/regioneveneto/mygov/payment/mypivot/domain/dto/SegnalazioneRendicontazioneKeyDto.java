package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class SegnalazioneRendicontazioneKeyDto {
	private String codIpaEnte;
	private String classificazioneCompletezza;
	private String codIuf;
	private String dataOraFlussoRendicontazione;

	public SegnalazioneRendicontazioneKeyDto() {
		super();
	}

	public SegnalazioneRendicontazioneKeyDto(String codIpaEnte, String classificazioneCompletezza, String codIuf,
			String dataOraFlussoRendicontazione) {
		super();
		this.codIpaEnte = codIpaEnte;
		this.classificazioneCompletezza = classificazioneCompletezza;
		this.codIuf = codIuf;
		this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
	}

	public String getCodIpaEnte() {
		return codIpaEnte;
	}

	public void setCodIpaEnte(String codIpaEnte) {
		this.codIpaEnte = codIpaEnte;
	}

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
	}

	public String getCodIuf() {
		return codIuf;
	}

	public void setCodIuf(String codIuf) {
		this.codIuf = codIuf;
	}

	public String getDataOraFlussoRendicontazione() {
		return dataOraFlussoRendicontazione;
	}

	public void setDataOraFlussoRendicontazione(String dataOraFlussoRendicontazione) {
		this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificazioneCompletezza == null) ? 0 : classificazioneCompletezza.hashCode());
		result = prime * result + ((codIpaEnte == null) ? 0 : codIpaEnte.hashCode());
		result = prime * result + ((codIuf == null) ? 0 : codIuf.hashCode());
		result = prime * result
				+ ((dataOraFlussoRendicontazione == null) ? 0 : dataOraFlussoRendicontazione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SegnalazioneRendicontazioneKeyDto other = (SegnalazioneRendicontazioneKeyDto) obj;
		if (classificazioneCompletezza == null) {
			if (other.classificazioneCompletezza != null)
				return false;
		} else if (!classificazioneCompletezza.equals(other.classificazioneCompletezza))
			return false;
		if (codIpaEnte == null) {
			if (other.codIpaEnte != null)
				return false;
		} else if (!codIpaEnte.equals(other.codIpaEnte))
			return false;
		if (codIuf == null) {
			if (other.codIuf != null)
				return false;
		} else if (!codIuf.equals(other.codIuf))
			return false;
		if (dataOraFlussoRendicontazione == null) {
			if (other.dataOraFlussoRendicontazione != null)
				return false;
		} else if (!dataOraFlussoRendicontazione.equals(other.dataOraFlussoRendicontazione))
			return false;
		return true;
	}

}
