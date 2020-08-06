package it.tasgroup.iris.facade.ejb.statistiche;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.idp.backoffice.statistiche.CruscottoVO;
import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.business.ejb.statistiche.StatisticheBusinessLocal;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.facade.ejb.client.statistiche.StatisticheFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.statistiche.StatisticheFacadeRemote;

@Stateless(name = "StatisticheFacade")
public class StatisticheFacadeBean implements StatisticheFacadeLocal, StatisticheFacadeRemote {

	private static final Logger LOGGER = LogManager.getLogger(StatisticheFacadeBean.class);

	@EJB(name = "StatisticheBusiness")
	private StatisticheBusinessLocal statisticheBusinessBean;
	
	@Override
	public List<CruscottoVO> getListaPosizioniEseguite(String idEnte, int anno) {
		List<Object[]>  lista = statisticheBusinessBean.riepilogoPosizioniEseguiteMese(idEnte, anno);
		return StatisticheVOBuilder.build(lista);
	}
	
	@Override
	public List<RiepilogoStatisticheVO> getListaPosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		List<Object[]>  lista = statisticheBusinessBean.riepilogoPosizioniCaricate(idEnte, anno, stat);
		return StatisticheVOBuilder.buildRiepilogoStatistiche(lista);
	}

	@Override
	public List<CruscottoVO> getListaPagamentiEseguiti(String idEnte, int anno) {
		List<Object[]> lista = statisticheBusinessBean.riepilogoPagamentiEseguitiMese(idEnte, anno);
		return StatisticheVOBuilder.build(lista);
	}

	@Override
	public List<CruscottoVO> getListaPagamentiSpontaneiEseguiti(String idEnte, int anno) {
		List<Object[]>  lista = statisticheBusinessBean.riepilogoPagamentiSpontaneiEseguitiMese(idEnte, anno);
		return StatisticheVOBuilder.build(lista);
	}
	
	@Override
	public RiepilogoStatisticheVO getTotalePosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		Object[] totalePosizioni = statisticheBusinessBean.getTotalePosizioniCaricate(idEnte, anno, stat);
		return StatisticheVOBuilder.buildTotaleRiepilogoStatistiche(totalePosizioni);
	}
	
	@Override
	public RiepilogoStatisticheVO getTotalePagamentiEseguitiPerTipoDebito (String idEnte, int anno, RiepilogoStatisticheVO stat) {
		Object[] totalePagamenti =  statisticheBusinessBean.getTotalePagamentiEseguitiPerTipoDebito(idEnte, anno, stat);
		return StatisticheVOBuilder.buildTotaleRiepilogoStatistiche(totalePagamenti);
	}
	
	@Override
	public RiepilogoStatisticheVO getTotalePagamentiEseguitiPerCircuito (String idEnte, int anno, RiepilogoStatisticheVO stat) {
		Object[] totalePagamenti =  statisticheBusinessBean.getTotalePagamentiEseguitiPerCircuito(idEnte, anno, stat);
		return StatisticheVOBuilder.buildTotaleRiepilogoStatistiche(totalePagamenti);
	}
	
	@Override
	public List<RiepilogoStatisticheVO> getListaPagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		List<Object[]>  lista = statisticheBusinessBean.riepilogoPagamentiEseguitiPerTipoDebito(idEnte, anno, stat);
		return StatisticheVOBuilder.buildRiepilogoStatistiche(lista);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> getListaPagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		List<Object[]>  lista = statisticheBusinessBean.riepilogoPagamentiEseguitiPerCircuito(idEnte, anno, stat);
		return StatisticheVOBuilder.buildRiepilogoStatistichePerCircuito(lista);
	}

	@Override
	public CruscottoVO getTotalePosizioniEseguite(String idEnte, int anno) {
		Object[] totalePosizioni = statisticheBusinessBean.getTotalePosizioniEseguite(idEnte, anno);
		return StatisticheVOBuilder.buildTotale(totalePosizioni);
	}

	@Override
	public CruscottoVO getTotalePagamentiEseguiti(String idEnte, int anno) {
		Object[] totalePagamenti =  statisticheBusinessBean.getTotalePagamentiEseguiti(idEnte, anno);
		return StatisticheVOBuilder.buildTotale(totalePagamenti);
	}

	@Override
	public CruscottoVO getTotalePagamentiSpontaneiEseguiti(String idEnte, int anno) {
		Object[] totalePagamenti =  statisticheBusinessBean.getTotalePagamentiSpontaneiEseguiti(idEnte, anno);
		return StatisticheVOBuilder.buildTotale(totalePagamenti);
	}
	
	@Override
	public void esportaStatistiche(IProfileManager profileManager, 
			ContainerDTO inputDTO, Map<String, String> mapListaCampi, 
			String cfOperatore, Class<?> theClass, Locale locale, String customFileName)
			throws Exception {

		Prenotazioni prenotazione = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaStatistiche - salvata la prenotazione");

		statisticheBusinessBean.esportaStatisticheCsv(inputDTO, prenotazione, mapListaCampi, cfOperatore, theClass, locale, customFileName);
		LOGGER.debug("esportaStatistiche - lanciato l'exporter asincrono");
	}
	
	
}


class StatisticheVOBuilder {
	
	public static LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> buildRiepilogoStatistichePerCircuito(List<Object[]> riepilogo) {
		LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>> res = new LinkedHashMap<String, ArrayList<RiepilogoStatisticheVO>>();
		String lastCircuito = "";
		
		int parzNumOccorrenze = 0, totNumOccorrenze = 0;
		BigDecimal parzImporto = BigDecimal.ZERO, totImporto = BigDecimal.ZERO;

		if (!riepilogo.isEmpty()) {
			lastCircuito = (String)riepilogo.get(0)[0];
			res.put(lastCircuito, new ArrayList<RiepilogoStatisticheVO>());
		}
		
		for(int i = 0; i < riepilogo.size(); i++) {
			String circuito = (String)riepilogo.get(i)[0];
			if (!res.containsKey(circuito)) {
				//Aggiungo totale parziale al circuito precedente
				res.get(lastCircuito).add(RiepilogoStatisticheVO.buildTotaleParziale(parzNumOccorrenze, parzImporto));
				//inizializzo la nuova lista
				res.put(circuito, new ArrayList<RiepilogoStatisticheVO>());
				
				totImporto = totImporto.add(parzImporto);
				totNumOccorrenze += parzNumOccorrenze;
				parzNumOccorrenze = 0;
				parzImporto = BigDecimal.ZERO;
			} 
			RiepilogoStatisticheVO elem = RiepilogoStatisticheVO.buildStatisticaCircuito(riepilogo.get(i));
			parzNumOccorrenze += elem.getNumeroOccorrenze();
			parzImporto = parzImporto.add(elem.getImporto());
			lastCircuito = circuito;
			res.get(circuito).add(elem);
		}
		if(!res.isEmpty()) {
			res.get(lastCircuito).add(RiepilogoStatisticheVO.buildTotaleParziale(parzNumOccorrenze, parzImporto));
			totImporto = totImporto.add(parzImporto);
			totNumOccorrenze += parzNumOccorrenze;
			res.put("Totali", new ArrayList<RiepilogoStatisticheVO>());
			res.get("Totali").add(RiepilogoStatisticheVO.buildTotale(totNumOccorrenze, totImporto));
		}
		return res;
	}
	
	public static List<RiepilogoStatisticheVO> buildRiepilogoStatistiche(List<Object[]> riepilogo) {
		ArrayList<RiepilogoStatisticheVO> res = new ArrayList<RiepilogoStatisticheVO>();
		for(Object[] elem : riepilogo) {
			res.add(RiepilogoStatisticheVO.build(elem));
		}
		return res;
	}
	
	public static List<CruscottoVO> build(List<Object[]> riepilogo) {
		ArrayList<CruscottoVO> res = new ArrayList<CruscottoVO>();
		int index = 0;
		for (int i = 12; i > 0 ; i--) {
			if (!riepilogo.isEmpty() && 
					index < riepilogo.size() &&
					((Integer)riepilogo.get(index)[0]).intValue() == i) {
				res.add(CruscottoVO.build(riepilogo.get(index)));
				index ++;
			} else {
				res.add(CruscottoVO.buildEmpty(i));
			}
		}
		return res;
	}
	
	public static RiepilogoStatisticheVO buildTotaleRiepilogoStatistiche(Object[] riepilogo) {
		RiepilogoStatisticheVO res = new RiepilogoStatisticheVO();
		res.setCdTrbEnte("Totali");;
		res.setNumeroOccorrenze(riepilogo[0] != null ? ((Long)riepilogo[0]).intValue() : null);
		res.setImporto((BigDecimal)riepilogo[1]);
		return res;
	}
	
	public static CruscottoVO buildTotale(Object[] riepilogo) {
		CruscottoVO res = new CruscottoVO();
		res.setOccorrenze(riepilogo[0] != null ? ((Long)riepilogo[0]).intValue() : null);
		res.setImporto((BigDecimal)riepilogo[1]);
		return res;
	}
	
}

