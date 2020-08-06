package it.tasgroup.iris.facade.ejb.storicodati;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.idp.backoffice.storicodati.MonitoraggioCaricamentiVO;
import it.nch.idp.backoffice.storicodati.StatoSvecchiamentoVO;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.storicodati.StoricoDatiBusinessLocal;
import it.tasgroup.iris.domain.SveLog;
import it.tasgroup.iris.domain.SveStato;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.facade.ejb.client.storico.StoricoDatiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.storico.StoricoDatiFacadeRemote;

@Stateless(name = "StoricoDatiFacade")
public class StoricoDatiFacadeBean implements StoricoDatiFacadeLocal, StoricoDatiFacadeRemote {

	@EJB(name = "StoricoDatiBusiness")
	private StoricoDatiBusinessLocal storicoDatiBusinessBean;
	
	
	@EJB(name = "DistintePagamentoBusiness")
	private DistintePagamentoBusinessLocal distintePagamentoBusinessBean;
	
	@Override
	public List<MonitoraggioCaricamentiVO> getListaLogSvecchiamento(String nomeProcesso) {
		List<SveLog> lista = storicoDatiBusinessBean.getListaLogSvecchiamento(nomeProcesso);
		List<MonitoraggioCaricamentiVO> result = new LogSvecchiamentoVOBuilder().fromSveLog(lista);
		return result;
	}
	
	@Override
	public List<StatoSvecchiamentoVO> getListaStatoSvecchiamento() {
		List<SveStato> lista = storicoDatiBusinessBean.getListaStatoSvecchiamento();
		List<StatoSvecchiamentoVO> result = new StatoSvecchiamentoVOBuilder().fromSveStato(lista);
		return result;
	}
	
	@Override
	public ContainerDTO getCondizioniCreditore(ContainerDTO inputDTO) {
		List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> listaCondizioni = distintePagamentoBusinessBean.getCondizioniCreditoreStorico(inputDTO);
		inputDTO.setOutputDTOList(listaCondizioni);
		return inputDTO;	
	}
	
	
	
	
	
	class StatoSvecchiamentoVOBuilder {
		public List<StatoSvecchiamentoVO> fromSveStato(List<SveStato> stati) {
			List<StatoSvecchiamentoVO> result = new ArrayList<StatoSvecchiamentoVO>();
			for (SveStato stato : stati) {
				StatoSvecchiamentoVO elem = fromSveStato(stato);
				result.add(elem);
			}
			return result;
		}
		
		public StatoSvecchiamentoVO fromSveStato(SveStato stati) {
			StatoSvecchiamentoVO elem = new StatoSvecchiamentoVO();
			elem.setId(stati.getId());
			elem.setNomeProcesso(stati.getNomeProcesso());
			elem.setStato(stati.getStato());
			elem.setDataUltimaOperazione(stati.getDataUltimaOperazione());
			elem.setDataUltimoDump(stati.getDataUltimoDump());
			elem.setNomeFileDump(stati.getNomeFileDump());
			elem.setDataUltimoImport(stati.getDataUltimoImport());
			elem.setParametri(stati.getParametri());
			return elem;
		}
	}

	class LogSvecchiamentoVOBuilder {
		public List<MonitoraggioCaricamentiVO> fromSveLog(List<SveLog> stati) {
			List<MonitoraggioCaricamentiVO> result = new ArrayList<MonitoraggioCaricamentiVO>();
			for (SveLog stato : stati) {
				MonitoraggioCaricamentiVO elem = fromSveLog(stato);
				result.add(elem);
			}
			return result;
		}
		
		public MonitoraggioCaricamentiVO fromSveLog(SveLog stati) {
			MonitoraggioCaricamentiVO elem = new MonitoraggioCaricamentiVO();
			elem.setDataOraOperazione(stati.getDataOraOperazione());
			elem.setTipoMessaggio(Character.toString(stati.getTipoMessaggio()));
			elem.setInfoOperazione(stati.getInfoOperazione());
			return elem;
		}
	}

}


