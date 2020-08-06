package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class EnteDto {

	private String codIpa;
	private String codFiscale;
	private String nomeEnte;
	private String emailAmministratore;
	public Long id;
	
	public EnteDto() {
		super();
	}

	public String getCodIpa() {
		return codIpa;
	}

	public void setCodIpa(String codIpa) {
		this.codIpa = codIpa;
	}

	public String getNomeEnte() {
		return nomeEnte;
	}

	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	
	public String getEmailAmministratore() {
		return emailAmministratore;
	}

	public void setEmailAmministratore(String emailAmministratore) {
		this.emailAmministratore = emailAmministratore;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
	@Override
	public String toString() {
		return "EnteDto [id=" + id + ", codIpa=" + codIpa + ", codFiscale=" + codFiscale + ", nomeEnte=" + nomeEnte + ", emailAmministratore=" + emailAmministratore + "]";
	}
	
}