package it.tasgroup.iris.business.ejb.revoche;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.idp.backoffice.revoca.RevocaPagamentoVO;
import it.nch.idp.backoffice.revoca.RiepilogoRevocaPagamentoVO;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.RevochePagamentoDao;

@Stateless(name = "RevochePagamentoBusiness")
public class RevochePagamentoBusinessBean implements RevochePagamentoBusinessLocal, RevochePagamentoBusinessRemote {
	
    private static final Logger LOGGER = LogManager.getLogger(RevochePagamentoBusinessBean.class);

    @EJB(name = "RevochePagamentoDaoService")
    RevochePagamentoDao revocaPagamento;
	
	@Override
    public List<RevochePagamento> getListaRevochePagamento(ContainerDTO input) {
        return (List<RevochePagamento>) revocaPagamento.getListaRevochePagamento(input);
    }
	
	@Override
    public RiepilogoRevocaPagamentoVO getRiepilogoRevocaPagamento(String idPagamento) {
        return revocaPagamento.getRiepilogoRevocaPagamento(idPagamento);
    }
	
	@Override
	public void updateRevocaPagamento(RevocaPagamentoVO revoca) {
		RevochePagamento rev;
		try {
			
			rev = revocaPagamento.getById(RevochePagamento.class, Long.decode(revoca.getIdRevoca()));
		} catch (Exception e) {
			LOGGER.error("error on updateRevocaPagamento " + revoca, e);
			throw new DAORuntimeException(e);
		}
		rev.setStatoRevoca(revoca.getStatoRevoca());
		rev.setCausaleEsitoRevoca(revoca.getCausaleEsito());
		rev.setDatiAggiuntiviEsitoRevoca(revoca.getDatiAggiuntiviEsito());
		rev.setOpAggiornamento(revoca.getOperatoreAR());
		rev.setTsAggiornamento(revoca.getTsAggiornamento());
		revocaPagamento.updateRevocaPagamento(rev);
	}
	
}
