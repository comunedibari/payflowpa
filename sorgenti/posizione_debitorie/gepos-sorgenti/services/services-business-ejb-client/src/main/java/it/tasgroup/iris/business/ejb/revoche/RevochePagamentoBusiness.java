package it.tasgroup.iris.business.ejb.revoche;

import java.util.List;

import it.nch.idp.backoffice.revoca.RevocaPagamentoVO;
import it.nch.idp.backoffice.revoca.RiepilogoRevocaPagamentoVO;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.ContainerDTO;

public interface RevochePagamentoBusiness {

	public List<RevochePagamento> getListaRevochePagamento(ContainerDTO input);
	public RiepilogoRevocaPagamentoVO getRiepilogoRevocaPagamento(String idPagamento);
	public void updateRevocaPagamento(RevocaPagamentoVO revoca) throws Exception;
	

}
