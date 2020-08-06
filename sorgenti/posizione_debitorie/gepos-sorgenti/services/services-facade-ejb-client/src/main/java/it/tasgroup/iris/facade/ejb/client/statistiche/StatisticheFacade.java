package it.tasgroup.iris.facade.ejb.client.statistiche;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.nch.idp.backoffice.statistiche.CruscottoVO;
import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.ContainerDTO;

public interface StatisticheFacade {

	public List<CruscottoVO> getListaPosizioniEseguite(String idEnte, int anno);
	public List<CruscottoVO> getListaPagamentiEseguiti(String idEnte, int anno);
	public List<CruscottoVO> getListaPagamentiSpontaneiEseguiti(String idEnte, int anno);
	
	public CruscottoVO getTotalePosizioniEseguite(String idEnte, int anno);
	public CruscottoVO getTotalePagamentiEseguiti(String idEnte, int anno);
	public CruscottoVO getTotalePagamentiSpontaneiEseguiti(String idEnte, int anno);
	
	void esportaStatistiche(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi,
			String cfOperatore, Class<?> theClass, Locale locale, String customFileName) throws Exception;
	
	public RiepilogoStatisticheVO getTotalePosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<RiepilogoStatisticheVO> getListaPosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat);
	
	public RiepilogoStatisticheVO getTotalePagamentiEseguitiPerTipoDebito (String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<RiepilogoStatisticheVO> getListaPagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat);
	
	public RiepilogoStatisticheVO getTotalePagamentiEseguitiPerCircuito (String idEnte, int anno, RiepilogoStatisticheVO stat);
	public LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> getListaPagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat);
}
