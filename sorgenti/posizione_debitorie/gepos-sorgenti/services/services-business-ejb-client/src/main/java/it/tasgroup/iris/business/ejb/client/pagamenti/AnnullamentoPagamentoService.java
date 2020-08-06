package it.tasgroup.iris.business.ejb.client.pagamenti;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAnnullamentoDTO;

import java.io.Serializable;

public interface AnnullamentoPagamentoService extends Serializable{
	
	public EsitoOperazionePagamentoDTO annullamentoPagamento(RichiestaAnnullamentoDTO richiestaAnnullamento);
    public void annullaPagamentoNDP(String codTransazione, StatiPagamentiIris newState);
    public void annullaPagamentoPrecedente(Long idDistinta);
    public boolean checkPagamentoInCorso(String idCondizione);
    
    
}
