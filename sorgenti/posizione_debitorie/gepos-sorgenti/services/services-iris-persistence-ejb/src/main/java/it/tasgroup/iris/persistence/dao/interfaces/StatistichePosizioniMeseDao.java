package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.tasgroup.iris.domain.StatistichePosizioniMese;
import it.tasgroup.services.dao.ejb.Dao;

public interface StatistichePosizioniMeseDao extends Dao<StatistichePosizioniMese> {
	
	public List<Object[]> riepilogoPosizioniEseguiteMese(String idEnte, int anno);
	public Object[] totalePosizioniEseguiteAnno(String idEnte, int anno);

	public List<Object[]> riepilogoPosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat);
	public Object[] totalePosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat);


}
