/**
 *
 */
package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.flussi.RevocaPagamentoDTO;

/**
 * @author Rosario M
 *
 */
public class RevocaPagamentoDTOBuilder {
	
	public static RevocaPagamentoDTO populateRevocaPagamentoDTO(RevochePagamento revocaPagamento) {
		RevocaPagamentoDTO res = new RevocaPagamentoDTO();
		res.setId(revocaPagamento.getId());
		res.setStatoRevoca(revocaPagamento.getStatoRevoca());
		res.setEsitoRevoca(revocaPagamento.getEsitoRevoca());
		res.setTipoRevocaRichiesta(revocaPagamento.getTipoRevocaRichiesta());
		res.setCausaleRevocaRichiesta(revocaPagamento.getCausaleRevocaRichiesta());
		res.setDatiAggRevocaRichiesta(revocaPagamento.getDatiAggRevocaRichiesta());
		res.setIdMessaggioRevoca(revocaPagamento.getIdMessaggioRevoca());
		res.setTsMessaggioRevoca(revocaPagamento.getTsMessaggioRevoca()); 
		res.setCausaleEsitoRevoca(revocaPagamento.getCausaleEsitoRevoca()); 	
		res.setDatiAggiuntiviEsitoRevoca(revocaPagamento.getDatiAggiuntiviEsitoRevoca());
		return res;
	}
	

}
