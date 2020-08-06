package it.nch.is.fo.tributi;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.is.fo.profilo.EntiCommon;
import it.nch.is.fo.sistemienti.ISistemaEnte;


public interface ITributoEnte  extends CommonBusinessObject{
	
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

	public void setSIL(String sil);
	
	public String getSIL();
	
	public  EntiCommon getEntiobj();

	public  void setEntiobj(EntiCommon entiobj);

	public  ICategoriaTributo getCategoriaobj();

	public  void setCategoriaobj(ICategoriaTributo categoriaobj);
	
	public ISistemaEnte getSistemaEnteobj();

	public void setSistemaEnteobj(ISistemaEnte sistemaEnteobj);
	
	public String getFlPredeterm();

	public void setFlPredeterm(String flPredeterm);
	
	public String getFlNotificaPagamento();

	public void setFlNotificaPagamento(String flNotificaPagamento);
	
	public String getFlVerificaPagamento();

	public void setFlVerificaPagamento(String flVerificaPagamento);
	
	public String getIBAN();

	public void setIBAN(String flPredeterm);
	
	public String getDesCategoriaTributo();
	
	public void setDesCategoriaTributo(String desCategoriaTributo);
	
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

	public String getNdpCodStazPa(); 
	public String getNdpAuxDigit();  
	public String getFlNdpIuvGenerato(); 
	//public Long   getNdpIuvStartNum(); 
	
	public void setNdpCodStazPa(String s); 
	public void setNdpAuxDigit(String s);  
	public void setFlNdpIuvGenerato(String s); 
	//public void setNdpIuvStartNum(Long l); 
	
	public String getFlNdp();
	
	public void setFlNdp(String flNdp);
	
	public String getFlNdpModello3();

	public void setFlNdpModello3(String flNdpModello3);
	
	public String getFlBancaTesoriera();

	public void setFlBancaTesoriera(String flBancaTesoriera);
	
	public String getFlBlf();
	
	public void setFlBlf(String flBlf);
	
	public String getIntestazioneCCP();
	
	public void setIntestazioneCCP(String intestazioneCCP);	
	
	public String getUoaCompetente();
	
	public void setUoaCompetente(String uoaCompetente);
	
	public String getNdpCodSegr();
	public void setNdpCodSegr(String ndpCodSegr);

	public String getTipoGestioneRichiestaRevoca();
	public void setTipoGestioneRichiestaRevoca(String tipoGestioneRichiestaRevoca);

	public String getAutorizzStampaBP();
	public void setAutorizzStampaBP(String autorizzStampaBP);
	
}