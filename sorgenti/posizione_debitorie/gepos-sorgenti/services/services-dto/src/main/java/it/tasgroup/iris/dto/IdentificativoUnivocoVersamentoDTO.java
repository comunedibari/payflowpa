/**
 * 
 */
package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author pazzik
 *
 */
public class IdentificativoUnivocoVersamentoDTO implements Serializable {
	
	private String idPagamentoCreditore;
	
	private EnumTipoAutorizzazione tipoIdDebitoCreditore;
	
	private BigDecimal importo;
	
	private String codiceVersante;
	
	private String codiceDebitore;

	public String getIdPagamentoCreditore() {
		return idPagamentoCreditore;
	}

	public void setIdPagamentoCreditore(String idPagamentoCreditore) {
		this.idPagamentoCreditore = idPagamentoCreditore;
	}

	public EnumTipoAutorizzazione getTipoIdDebitoCreditore() {
		return tipoIdDebitoCreditore;
	}

	public void setTipoIdDebitoCreditore(EnumTipoAutorizzazione tipoIdDebitoCreditore) {
		this.tipoIdDebitoCreditore = tipoIdDebitoCreditore;
	}

	@Override
	public String toString() {
		return "IdentificativoUnivocoVersamentoDTO [idPagamentoCreditore="
				+ idPagamentoCreditore + ", tipoIdDebitoCreditore="
				+ tipoIdDebitoCreditore + "]";
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getCodiceVersante() {
		return codiceVersante;
	}

	public void setCodiceVersante(String codiceVersante) {
		this.codiceVersante = codiceVersante;
	}

	public String getCodiceDebitore() {
		return codiceDebitore;
	}

	public void setCodiceDebitore(String codiceDebitore) {
		this.codiceDebitore = codiceDebitore;
	}

}
