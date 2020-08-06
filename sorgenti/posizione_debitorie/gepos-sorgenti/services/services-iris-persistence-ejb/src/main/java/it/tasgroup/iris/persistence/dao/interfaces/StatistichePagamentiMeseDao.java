package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.tasgroup.iris.domain.StatistichePagamentiMese;
import it.tasgroup.services.dao.ejb.Dao;

public interface StatistichePagamentiMeseDao extends Dao<StatistichePagamentiMese>{
	
	public List<Object[]> riepilogoPagamentiEseguitiMese(String idEnte, int anno);
	public List<Object[]> riepilogoPagamentiSpontaneiEseguitiMese(String idEnte, int anno);

	public Object[] totalePagamentiEseguitiAnno(String idEnte, int anno);
	public Object[] totalePagamentiSpontaneiEseguitiAnno(String idEnte, int anno);
	
	public Object[] totalePagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<Object[]> riepilogoPagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat);

	public Object[] totalePagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat);
	public List<Object[]> riepilogoPagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat);

}
