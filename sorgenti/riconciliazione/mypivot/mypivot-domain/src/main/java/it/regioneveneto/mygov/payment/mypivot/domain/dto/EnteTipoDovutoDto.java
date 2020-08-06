package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;

public class EnteTipoDovutoDto  implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7907060994216991035L;
	public EnteTO ente;
    public Long id;
    public String codTipo;
    public String deTipo;

    public EnteTipoDovutoDto() {
    }

    public EnteTipoDovutoDto(EnteTO ente, Long id, String codTipo, String deTipo) {
        super();
        this.ente = ente;
        this.id = id;
        this.codTipo = codTipo;
        this.deTipo = deTipo;
    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getDeTipo() {
        return deTipo;
    }

    public void setDeTipo(String deTipo) {
        this.deTipo = deTipo;
    }

    @Override
    public String toString() {
        return "EnteTipoDovutoTO["
            + ente
            + ", "
            + id
            + ", "
            + codTipo
            + ", "
            + deTipo
            + "]";
    }

}
