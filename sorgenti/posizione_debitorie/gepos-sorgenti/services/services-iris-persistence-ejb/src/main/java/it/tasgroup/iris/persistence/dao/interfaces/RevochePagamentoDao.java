package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.idp.backoffice.revoca.RiepilogoRevocaPagamentoVO;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface RevochePagamentoDao extends Dao<RevochePagamento> {

	public List<RevochePagamento> getRevocaPagamentoByIdPagamento(Long idPagamento);

	public List<RevochePagamento> getListaRevochePagamento(ContainerDTO input);

	public RiepilogoRevocaPagamentoVO getRiepilogoRevocaPagamento(String idPagamento);
	
	public void updateRevocaPagamento(RevochePagamento revoca);
}
