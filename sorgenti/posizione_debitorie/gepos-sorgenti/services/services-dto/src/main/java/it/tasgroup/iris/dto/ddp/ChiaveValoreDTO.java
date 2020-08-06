package it.tasgroup.iris.dto.ddp;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChiaveValoreDTO implements Serializable{
	
	private String chiave;
	private String valore;
	private BigDecimal valoreNumerico;
	
	public ChiaveValoreDTO(String chiave, BigDecimal valoreNumerico) {
		super();
		this.chiave = chiave;
		this.valoreNumerico = valoreNumerico;
	}

	public ChiaveValoreDTO(String chiave, String valore) {
		super();
		this.chiave = chiave;
		this.valore = valore;
	}

	public String getChiave() {
		return chiave;
	}
	public void setChiave(String chiave) {
		this.chiave = chiave;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public BigDecimal getValoreNumerico() {
		return valoreNumerico;
	}
	public void setValoreNumerico(BigDecimal valoreNumerico) {
		this.valoreNumerico = valoreNumerico;
	}
	
}
