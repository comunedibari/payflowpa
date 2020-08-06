package it.nch.is.fo.tributi;

import it.nch.fwk.fo.common.IBaseForm;



public interface ITributoEnteForm extends ITributoEnte, IBaseForm{

	public String getIdEnte();
	public void setIdEnte(String idEnte);
	
	public String getCdTrbEnte();
	public void setCdTrbEnte(String cdTrbEnte);

	public  String getIdTributo();

	public  void setIdTributo(String idTributo);

	public  String getDeTrb();

	public  void setDeTrb(String deTrb);

	public  String getFlIniziativa();

	public  void setFlIniziativa(String flIniziativa);

	public  String getSoggEsclusi();

	public  void setSoggEsclusi(String soggEsclusi);

	public  String getStato();

	public  void setStato(String stato);

	public String getSIL();
	
	public void setSIL(String SIL);
	
	public String getFlPredeterm();

	public void setFlPredeterm(String flPredeterm);
	
	public String getFlNotificaPagamento();

	public void setFlNotificaPagamento(String flNotificaPagamento);
	
	public String getFlVerificaPagamento();

	public void setFlVerificaPagamento(String flVerificaPagamento);
 
	public String getIBAN();

	public void setIBAN(String flPredeterm);
	
	
	public String getDeSil();
	
	public void setDeSil(String deSil);
	
	public  String getInfoTributo();

	public  void setInfoTributo(String infoTributo);

	
	public String getFlRicevutaAnonimo();
	public void setFlRicevutaAnonimo(String flRicevutaAnonimo);

	public String getUrlInfoService();
	public void setUrlInfoService(String urlInfoService);

	public String getFlNascostoFe();
	public void setFlNascostoFe(String flNascostoFe);

	public String getUrlUpdService();
	public void setUrlUpdService(String urlUpdService);
	
	public String getIstruzioniPagamento();
	public void setIstruzioniPagamento(String istruzioniPagamento);
	
	
	public String getFlNdp();
		
	public void setFlNdp(String flNdp);
	
	public String getFlNdpModello3();

	public void setFlNdpModello3(String flNdpModello3);
	
	public String getFlBancaTesoriera();

	public void setFlBancaTesoriera(String flBancaTesoriera);
	
	public String getFlBlf();
	
	public void setFlBlf(String flBlf);

}
