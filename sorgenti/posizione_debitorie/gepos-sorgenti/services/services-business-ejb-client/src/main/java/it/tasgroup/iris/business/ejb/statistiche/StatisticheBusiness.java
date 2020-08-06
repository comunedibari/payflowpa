package it.tasgroup.iris.business.ejb.statistiche;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;

public interface StatisticheBusiness {

	public List<Object[]>  riepilogoPosizioniEseguiteMese(String idEnte, int anno);
	public List<Object[]>  riepilogoPagamentiEseguitiMese(String idEnte, int anno);
	public List<Object[]>  riepilogoPagamentiSpontaneiEseguitiMese(String idEnte, int anno);
	
	public Object[] getTotalePosizioniEseguite(String idEnte, int anno);
	public Object[] getTotalePagamentiEseguiti(String idEnte, int anno);
	public Object[] getTotalePagamentiSpontaneiEseguiti(String idEnte, int anno);
	void esportaStatisticheCsv(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi,
			String cfOperatore, Class<?> theClass, Locale locale, String customFileName);
	
	public Object[] getTotalePosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<Object[]> riepilogoPosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat);
	
	public Object[] getTotalePagamentiEseguitiPerTipoDebito (String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<Object[]> riepilogoPagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat);
	
	public Object[] getTotalePagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<Object[]> riepilogoPagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat);

}
