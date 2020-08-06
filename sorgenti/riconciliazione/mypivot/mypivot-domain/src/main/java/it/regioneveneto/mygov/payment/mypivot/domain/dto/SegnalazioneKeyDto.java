package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class SegnalazioneKeyDto {
	private String codIpaEnte;
	private String classificazioneCompletezza;
	private String codIuf;
	private String codIud;
	private String codIuv;
	
	
	public SegnalazioneKeyDto() {
		super();
	}

	public SegnalazioneKeyDto(String codIpaEnte, String classificazioneCompletezza, String codIuf, String codIud,
			String codIuv) {
		super();
		this.codIpaEnte = codIpaEnte;
		this.classificazioneCompletezza = classificazioneCompletezza;
		this.codIuf = codIuf;
		this.codIud = codIud;
		this.codIuv = codIuv;
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

	public String getCodIud() {
		return codIud;
	}

	public void setCodIud(String codIud) {
		this.codIud = codIud;
	}

	public String getCodIuv() {
		return codIuv;
	}

	public void setCodIuv(String codIuv) {
		this.codIuv = codIuv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificazioneCompletezza == null) ? 0 : classificazioneCompletezza.hashCode());
		result = prime * result + ((codIpaEnte == null) ? 0 : codIpaEnte.hashCode());
		result = prime * result + ((codIud == null) ? 0 : codIud.hashCode());
		result = prime * result + ((codIuf == null) ? 0 : codIuf.hashCode());
		result = prime * result + ((codIuv == null) ? 0 : codIuv.hashCode());
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
		SegnalazioneKeyDto other = (SegnalazioneKeyDto) obj;
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
		if (codIud == null) {
			if (other.codIud != null)
				return false;
		} else if (!codIud.equals(other.codIud))
			return false;
		if (codIuf == null) {
			if (other.codIuf != null)
				return false;
		} else if (!codIuf.equals(other.codIuf))
			return false;
		if (codIuv == null) {
			if (other.codIuv != null)
				return false;
		} else if (!codIuv.equals(other.codIuv))
			return false;
		return true;
	}

}
