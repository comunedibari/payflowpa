package it.nch.pagamenti;


public interface IPagamentoSpontaneoForm{
	
	public String getTipoTributo();
	public void setTipoTributo(String tipoTributo);

	public String getDescTipoTributo();
	
	public void setDescTipoTributo(String descTipoTributo);
	
	public String getCodEnte();
	public void setCodEnte(String codEnte);

	public  String getDescEnte();
	public  void setDescEnte(String descEnte);

	public  String getCodTributo();

	public  void setCodTributo(String codTributo);

	public  String getDescTributo();

	public  void setDescTributo(String descTributo);
	
	public String getCommand(); 
	
	public void setCommand(String command);
	
	public String getIdPagamento();
	
	public void setIdPagamento(String idPagamento);
	
	public String getNotePagamento();
	
	public void setNotePagamento(String notePagamento);
	
	public String getPredeterminabile();

	public void setPredeterminabile(String predeterminabile);
	
	public String getIniziativa();

	public void setIniziativa(String iniziativa);
	
	public String getPagamentoFW();

	public void setPagamentoFW(String pagamentoFW);
	
	
}
