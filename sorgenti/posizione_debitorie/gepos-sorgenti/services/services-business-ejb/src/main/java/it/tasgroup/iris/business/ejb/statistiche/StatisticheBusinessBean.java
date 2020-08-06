package it.tasgroup.iris.business.ejb.statistiche;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.idp.backoffice.statistiche.RiepilogoCruscottoVO;
import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import it.tasgroup.iris.business.ejb.client.esportazioni.ExportBusinessLocal;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.StatistichePagamentiMeseDao;
import it.tasgroup.iris.persistence.dao.interfaces.StatistichePosizioniMeseDao;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;


@Stateless(name = "StatisticheBusiness")
public class StatisticheBusinessBean implements StatisticheBusinessLocal, StatisticheBusinessRemote {
	
	@EJB(name = "StatistichePosizioniDaoService")
    private StatistichePosizioniMeseDao statistichePosizioniMeseDao;
    
    @EJB(name = "StatistichePagamentiDaoService")
    private StatistichePagamentiMeseDao statistichePagamentiMeseDao;
    
    @EJB(name = "ExportBusiness")
    private ExportBusinessLocal exportBusinessBean;

	@Override
	public List<Object[]> riepilogoPosizioniEseguiteMese(String idEnte, int anno) {
		return statistichePosizioniMeseDao.riepilogoPosizioniEseguiteMese(idEnte, anno);
	}

	@Override
	public List<Object[]> riepilogoPosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		return statistichePosizioniMeseDao.riepilogoPosizioniCaricate(idEnte, anno, stat);
	}
	
	@Override
	public List<Object[]> riepilogoPagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		return statistichePagamentiMeseDao.riepilogoPagamentiEseguitiPerTipoDebito(idEnte, anno, stat);
	}
	
	@Override
	public List<Object[]> riepilogoPagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		return statistichePagamentiMeseDao.riepilogoPagamentiEseguitiPerCircuito(idEnte, anno, stat);
	}
	
	@Override
	public Object[] getTotalePagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		return statistichePagamentiMeseDao.totalePagamentiEseguitiPerCircuito(idEnte, anno, stat);
	}
	
	@Override
	public List<Object[]>  riepilogoPagamentiEseguitiMese(String idEnte, int anno) {
		return statistichePagamentiMeseDao.riepilogoPagamentiEseguitiMese(idEnte, anno);
	}

	@Override
	public List<Object[]>  riepilogoPagamentiSpontaneiEseguitiMese(String idEnte, int anno) {
		return statistichePagamentiMeseDao.riepilogoPagamentiSpontaneiEseguitiMese(idEnte, anno);
	}

	@Override
	public Object[] getTotalePosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		return statistichePosizioniMeseDao.totalePosizioniCaricate(idEnte, anno, stat);
	}

	@Override
	public Object[] getTotalePosizioniEseguite(String idEnte, int anno) {
		return statistichePosizioniMeseDao.totalePosizioniEseguiteAnno(idEnte, anno);
	}

	@Override
	public Object[] getTotalePagamentiEseguiti(String idEnte, int anno) {
		return statistichePagamentiMeseDao.totalePagamentiEseguitiAnno(idEnte, anno);
	}
	
	@Override
	public Object[] getTotalePagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		return statistichePagamentiMeseDao.totalePagamentiEseguitiPerTipoDebito(idEnte, anno, stat);
	}
	
	@Override
	public Object[] getTotalePagamentiSpontaneiEseguiti(String idEnte, int anno) {
		return statistichePagamentiMeseDao.totalePagamentiSpontaneiEseguitiAnno(idEnte, anno);
	}
	
	@Override
    @Asynchronous
    public void esportaStatisticheCsv(ContainerDTO inputDTO, 
    		Prenotazioni prenotazione, Map<String, String> mapListaCampi, 
    		String cfOperatore, Class<?> theClass, Locale locale, String customFileName) {

    		PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(0);
    		exportBusinessBean.esporta(
    				prenotazione,
    				mapListaCampi,
    				cfOperatore,
    				vo.getRigaIntestazione(),
    				vo.getMySelectSeparatore(),
    				(List) inputDTO.getInputDTOList().get(2),
    				vo.getMyvaloreselezionato(),
    				vo.getTipoEsportazione().getChiave(),
    				EnumDynaReportFormat.CSV_CUSTOM,null,
    				theClass, locale, customFileName);

    }

	
}
