package it.tasgroup.idp.rs.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CondizionePagamentoReference implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String idCondizione;
	private BigDecimal importo;
	private int flagOpposizione730;

	public CondizionePagamentoReference() {
    }
	
	public CondizionePagamentoReference(String idCondizione, BigDecimal importo, int flagOpposizione730) {
        this.idCondizione = idCondizione;
        this.importo = importo;
        this.flagOpposizione730 = flagOpposizione730;
        
    }

	@XmlElement(required = true)
	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	@XmlElement(required = true)
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	@XmlElement(required = true)
	public int getFlagOpposizione730() {
		return flagOpposizione730;
	}

	public void setFlagOpposizione730(int flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}

}
