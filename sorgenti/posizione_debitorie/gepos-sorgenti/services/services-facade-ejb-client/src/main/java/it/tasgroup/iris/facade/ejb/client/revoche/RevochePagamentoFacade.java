package it.tasgroup.iris.facade.ejb.client.revoche;

import it.nch.idp.backoffice.revoca.RevocaPagamentoVO;
import it.nch.idp.backoffice.revoca.RiepilogoRevocaPagamentoVO;
import it.tasgroup.iris.dto.ContainerDTO;

public interface RevochePagamentoFacade {

	public ContainerDTO getListaRevochePagamento(ContainerDTO containerDTO);
	public RiepilogoRevocaPagamentoVO getRiepilogoRevocaPagamento(String idPagamento);
	public void updateRevocaPagamento(RevocaPagamentoVO input) throws Exception;

}
