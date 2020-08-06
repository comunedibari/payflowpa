package it.regioneveneto.mygov.payment.mypivot.domain.dto;

/**
 * Dettaglio struttura, capitolo ed accertamento contabile associati alla RT in accertamento.
 * 
 * @author Marianna Memoli
 */
public class AccertamentoUfficioCapitoloDto {
	
	/**
	 * Codice ufficio
	 */
	private String codUfficio;
	/**
	 *  Descrizione testuale dell'ufficio 
	 */
	private String deUfficio;
	/**
	 * Codice del capitolo
	 */
	private String codCapitolo;
	/**
	 *  Descrizione testuale del capitolo
	 */
	private String deCapitolo;
    /**
	 * Anno esercizio, attributo riferito al capitolo
	 */
    private String deAnnoEsercizio;
	/**
	 * Codice accertamento
	 */
	private String codAccertamento;
	/**
	 * Descrizione testuale dell'accertamento
	 */
	private String deAccertamento;
	/**
	 * Quota parte dell'importo associata al capitolo ed accertamento contabile selezionati
	 */
	private String numImporto;
	/**
	 * 
	 */
	private boolean flgImportoInserito;
	
	
	
	/**
	 * @return the codUfficio
	 */
	public String getCodUfficio() {
		return codUfficio;
	}
	/**
	 * @param codUfficio the codUfficio to set
	 */
	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}
	/**
	 * @return the deUfficio
	 */
	public String getDeUfficio() {
		return deUfficio;
	}
	/**
	 * @param deUfficio the deUfficio to set
	 */
	public void setDeUfficio(String deUfficio) {
		this.deUfficio = deUfficio;
	}
	/**
	 * @return the codCapitolo
	 */
	public String getCodCapitolo() {
		return codCapitolo;
	}
	/**
	 * @param codCapitolo the codCapitolo to set
	 */
	public void setCodCapitolo(String codCapitolo) {
		this.codCapitolo = codCapitolo;
	}
	/**
	 * @return the deCapitolo
	 */
	public String getDeCapitolo() {
		return deCapitolo;
	}
	/**
	 * @param deCapitolo the deCapitolo to set
	 */
	public void setDeCapitolo(String deCapitolo) {
		this.deCapitolo = deCapitolo;
	}
	/**
	 * @return the deAnnoEsercizio
	 */
	public String getDeAnnoEsercizio() {
		return deAnnoEsercizio;
	}
	/**
	 * @param deAnnoEsercizio the deAnnoEsercizio to set
	 */
	public void setDeAnnoEsercizio(String deAnnoEsercizio) {
		this.deAnnoEsercizio = deAnnoEsercizio;
	}
	/**
	 * @return the codAccertamento
	 */
	public String getCodAccertamento() {
		return codAccertamento;
	}
	/**
	 * @param codAccertamento the codAccertamento to set
	 */
	public void setCodAccertamento(String codAccertamento) {
		this.codAccertamento = codAccertamento;
	}
	/**
	 * @return the deAccertamento
	 */
	public String getDeAccertamento() {
		return deAccertamento;
	}
	/**
	 * @param deAccertamento the deAccertamento to set
	 */
	public void setDeAccertamento(String deAccertamento) {
		this.deAccertamento = deAccertamento;
	}
	/**
	 * @return the numImporto
	 */
	public String getNumImporto() {
		return numImporto;
	}
	/**
	 * @param numImporto the numImporto to set
	 */
	public void setNumImporto(String numImporto) {
		this.numImporto = numImporto;
	}
	/**
	 * @return the flgImportoInserito
	 */
	public boolean isFlgImportoInserito() {
		return flgImportoInserito;
	}
	/**
	 * @param flgImportoInserito the flgImportoInserito to set
	 */
	public void setFlgImportoInserito(boolean flgImportoInserito) {
		this.flgImportoInserito = flgImportoInserito;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccertamentoUfficioCapitoloDto [codUfficio=");
		builder.append(codUfficio);
		builder.append(", deUfficio=");
		builder.append(deUfficio);
		builder.append(", codCapitolo=");
		builder.append(codCapitolo);
		builder.append(", deCapitolo=");
		builder.append(deCapitolo);
		builder.append(", deAnnoEsercizio=");
		builder.append(deAnnoEsercizio);
		builder.append(", codAccertamento=");
		builder.append(codAccertamento);
		builder.append(", deAccertamento=");
		builder.append(deAccertamento);
		builder.append(", numImporto=");
		builder.append(numImporto);
		builder.append(", flgImportoInserito=");
		builder.append(flgImportoInserito);
		builder.append("]");
		return builder.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		AccertamentoUfficioCapitoloDto other = (AccertamentoUfficioCapitoloDto) obj;
		
		if (codUfficio == null) {
			if (other.codUfficio != null) return false;
		} else 
			if (!codUfficio.equals(other.codUfficio)) return false;
		
		if (deAnnoEsercizio == null) {
			if (other.deAnnoEsercizio != null) return false;
		} else 
			if (!deAnnoEsercizio.equals(other.deAnnoEsercizio)) return false;
		
		if (codCapitolo == null) {
			if (other.codCapitolo != null) return false;
		} else 
			if (!codCapitolo.equals(other.codCapitolo)) return false;
		
		if (codAccertamento == null) {
			if (other.codAccertamento != null) return false;
		} else 
			if (!codAccertamento.equals(other.codAccertamento)) return false;
		
		return true;
	}
}