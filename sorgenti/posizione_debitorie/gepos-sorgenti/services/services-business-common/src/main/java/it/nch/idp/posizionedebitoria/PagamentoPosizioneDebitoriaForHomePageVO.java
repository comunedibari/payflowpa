package it.nch.idp.posizionedebitoria;

import java.math.BigDecimal;

/**
 * @author Simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PagamentoPosizioneDebitoriaForHomePageVO extends PagamentoPosizioneDebitoriaVO {
	
	private static final long serialVersionUID = 1L;
	
	//per la visualizzazione in home page
	private String causalePagamento;
	private Boolean flagContoTerzi;
	private BigDecimal importoTotCondizione;
	private String codPagamento;
	private String debitore;
	private String riscossore;
	private String riferimento;
	
	public String getCausalePagamento() {
		return causalePagamento;
	}

	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}

	public Boolean getFlagContoTerzi() {
        return flagContoTerzi;
    }

    public void setFlagContoTerzi(Boolean flagContoTerzi) {
        this.flagContoTerzi = flagContoTerzi;
    }

    public BigDecimal getImportoTotCondizione() {
        return importoTotCondizione;
    }

    public void setImportoTotCondizione(BigDecimal importoTotCondizione) {
        this.importoTotCondizione = importoTotCondizione;
    }

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	public String getDebitore() {
		return debitore;
	}

	public void setDebitore(String debitore) {
		this.debitore = debitore;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
    

}
