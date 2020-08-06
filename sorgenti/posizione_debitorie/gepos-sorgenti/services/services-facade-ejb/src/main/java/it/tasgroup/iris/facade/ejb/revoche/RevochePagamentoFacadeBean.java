package it.tasgroup.iris.facade.ejb.revoche;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.idp.backoffice.revoca.RevocaPagamentoVO;
import it.nch.idp.backoffice.revoca.RiepilogoRevocaPagamentoVO;
import it.tasgroup.iris.business.ejb.revoche.RevochePagamentoBusinessLocal;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.facade.ejb.client.revoche.RevochePagamentoFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.revoche.RevochePagamentoFacadeRemote;

@Stateless(name = "RevochePagamentoFacade")
public class RevochePagamentoFacadeBean implements RevochePagamentoFacadeLocal, RevochePagamentoFacadeRemote {

	@EJB(name = "RevochePagamentoBusiness")
	private RevochePagamentoBusinessLocal revochePagamento;
	
	@Override
	public ContainerDTO getListaRevochePagamento(ContainerDTO input) {
		List<RevochePagamento>  lista = revochePagamento.getListaRevochePagamento(input);
		List<RevocaPagamentoVO>  listaVO = RevocaPagamentoVOBuilder.fromRevochePagamento(lista);
		input.setOutputDTO(listaVO);
		return input;

	}
	
	@Override
	public RiepilogoRevocaPagamentoVO getRiepilogoRevocaPagamento(String idPagamento) {
		RiepilogoRevocaPagamentoVO  riepilogo = revochePagamento.getRiepilogoRevocaPagamento(idPagamento);
		return riepilogo;
	}
	
	@Override
	public void updateRevocaPagamento(RevocaPagamentoVO revoca) throws Exception {
		revochePagamento.updateRevocaPagamento(revoca);
	}

}

class RevocaPagamentoVOBuilder {
	
	public static RevocaPagamentoVO fromRevoaPagamento(RevochePagamento revoca) {
		RevocaPagamentoVO res = new RevocaPagamentoVO();
		res.setDataRichiesta(revoca.getTsMessaggioRevoca()); 
		res.setIdRichiesta(revoca.getIdMessaggioRevoca()); 
		res.setDescrizioneAttestante(revoca.getPagamento().getFlussoDistinta().getDescrizioneAttestante()); 
		res.setIdAttestante(revoca.getPagamento().getFlussoDistinta().getIdentificativoAttestante()); 
		res.setMotivoRevoca(revoca.getTipoRevocaRichiesta()); 
		res.setIuv(revoca.getPagamento().getFlussoDistinta().getIuv()); 
		res.setCausaleRichiesta(revoca.getCausaleRevocaRichiesta());
		res.setDatiAggiuntiviRichiesta(revoca.getDatiAggRevocaRichiesta());;
		res.setStatoRevoca(revoca.getStatoRevoca()); 
		res.setDataAR(revoca.getTsAggiornamento()); 
		res.setCausaleEsito(revoca.getCausaleEsitoRevoca());; 
		res.setDatiAggiuntiviEsito(revoca.getDatiAggiuntiviEsitoRevoca());;
		res.setOperatoreAR(revoca.getOpAggiornamento()); 
		res.setIdPagamento(String.valueOf(revoca.getPagamento().getId()));
		res.setIdRevoca(String.valueOf(revoca.getId()));
		res.setTsAggiornamento(revoca.getTsAggiornamento());
		return res;
	}
	
	public static List<RevocaPagamentoVO> fromRevochePagamento(List<RevochePagamento> listaRevoche) {
		ArrayList<RevocaPagamentoVO> res = new ArrayList<RevocaPagamentoVO>();
		for(RevochePagamento revoca : listaRevoche) {
			res.add(fromRevoaPagamento(revoca));
		}
		return res;
	}
	

}
