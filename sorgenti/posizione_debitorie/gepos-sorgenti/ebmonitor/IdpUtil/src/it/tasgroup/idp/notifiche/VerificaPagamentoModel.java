package it.tasgroup.idp.notifiche;

public class VerificaPagamentoModel {
	
	public static enum EnumStatoPagamentoDettagliato {

	    POSIZIONE_NON_PRESENTE,
	    POSIZIONE_NON_PAGATA,
	    POSIZIONE_NON_PAGABILE,
	    POSIZIONE_PAGATA,
	    POSIZIONE_PAGATA_SBF,
	    POSIZIONE_CON_PAG_IN_CORSO,
	    POSIZIONE_CON_DOC_EMESSO;

	}
	
	
	public static enum EnumDescrizioneStato {

		PAG_IN_ERRORE,
	    PAG_ANNULLATO_OPER,
	    PAG_ANNULLATO,
	    PAG_NON_ESEGUITO,
	    PAG_STORNATO,
	    PAG_RIMBORSATO,
		NON_PAGABILE_TERMINI,
		NON_PAGABILE_RATE,
		PAG_ESEGUITO_IDP,
		PAG_ESEGUITO_OTH	   
	}
	
	String idPagamento;
	String tipoPendenza;
	EnumStatoPagamentoDettagliato statoPagamento;
	String descrizioneStato;
	boolean refreshData=false; 
	
	PagamentoModelTyped pagamento;

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getTipoPendenza() {
		return tipoPendenza;
	}

	public void setTipoPendenza(String tipoPendenza) {
		this.tipoPendenza = tipoPendenza;
	}

	public EnumStatoPagamentoDettagliato getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoPagamento(EnumStatoPagamentoDettagliato statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public PagamentoModelTyped getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoModelTyped pagamento) {
		this.pagamento = pagamento;
	}

	public boolean isRefreshData() {
		return refreshData;
	}

	public void setRefreshData(boolean refreshData) {
		this.refreshData = refreshData;
	}
	

}
