package it.nch.is.fo.tributi;

import java.util.Collection;
import java.util.Set;


public interface ICategoriaTributo{	
	
	public abstract String getIdTributo();

	public abstract void setIdTributo(String idTributo);

	public abstract String getDeTrb();

	public abstract void setDeTrb(String deTrb);

	public abstract String getCdAde();

	public abstract void setCdAde(String cdAde);

	public abstract String getTpEntrata();

	public abstract void setTpEntrata(String tpEntrata);

	public abstract String getFlPredeterm();

	public abstract void setFlPredeterm(String flPredeterm);

	public abstract String getFlIniziativa();

	public abstract void setFlIniziativa(String flIniziativa);

	public abstract String getStato();

	public abstract void setStato(String stato);

	public abstract String getSoggEsclusi();

	public abstract void setSoggEsclusi(String soggEsclusi);
	
	public abstract String getCdPagamentoSpontaneo();

	public abstract void setCdPagamentoSpontaneo(String cdPagamentoSpontaneo);
	
	public abstract String getTassonomia(); 
 	
 	public abstract void setTassonomia(String tassonomia); 
	
	public Collection getEntiTributi();
	public void setEntiTributi(Set entiTributi);
	

}