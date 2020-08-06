package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO usato per contenere le liste da visualizzare in home page cittadino
 *  
 * @author fabrizie
 *
 */
public class CruscottoHomePageDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String tipoDebito;
    private Integer numero;
    private BigDecimal importo;
	
	public CruscottoHomePageDTO() {}

    public String getTipoDebito() {
        return tipoDebito;
    }

    public void setTipoDebito(String tipoDebito) {
        this.tipoDebito = tipoDebito;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }
	
	
}


