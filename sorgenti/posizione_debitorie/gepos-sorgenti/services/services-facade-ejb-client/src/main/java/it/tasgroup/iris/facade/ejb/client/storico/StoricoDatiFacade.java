package it.tasgroup.iris.facade.ejb.client.storico;

import java.util.List;

import it.nch.idp.backoffice.storicodati.MonitoraggioCaricamentiVO;
import it.nch.idp.backoffice.storicodati.StatoSvecchiamentoVO;
import it.tasgroup.iris.dto.ContainerDTO;

public interface StoricoDatiFacade {

	public ContainerDTO getCondizioniCreditore(ContainerDTO inputDTO);

	List<MonitoraggioCaricamentiVO> getListaLogSvecchiamento(String nomeProcesso);

	List<StatoSvecchiamentoVO> getListaStatoSvecchiamento();

}
