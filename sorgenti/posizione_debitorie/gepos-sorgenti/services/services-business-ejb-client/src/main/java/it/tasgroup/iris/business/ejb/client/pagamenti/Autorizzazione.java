/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.pagamenti;

import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;

import java.io.Serializable;
import java.util.List;


public interface Autorizzazione extends Serializable{
	
	/**
	 * @param autorizzazioneDto
	 * @return
	 */
	public EsitoOperazionePagamentoDTO autorizzaPagamento(RichiestaAutorizzazioneDTO autorizzazioneDto);
	
	/**
	 * @param autorizzazioneDto
	 * @return
	 */
	public EsitoOperazionePagamentoDTO autorizzaPagamentoCBILL(RichiestaAutorizzazioneDTO autorizzazioneDto);
	
	/**
	 * @param autorizzazioneDto
	 * @return
	 */
	public EsitoOperazionePagamentoDTO autorizzaPagamentoPAI(RichiestaAutorizzazioneDTO autorizzazioneDto);

	/**
	 * @param autorizzazioneDto
	 * @return
	 */
	public EsitoOperazionePagamentoDTO verificaPagamentoPSP(RichiestaAutorizzazioneDTO autorizzazioneDto);
	
	/**
	 * @param autorizzazioneDto
	 * @return
	 */
	public EsitoOperazionePagamentoDTO attivaPagamentoPSP(RichiestaAutorizzazioneDTO autorizzazioneDto);

	/**
	 * @param dtoOut
	 * @param listaCondizioni
	 * @return
	 */
	public EsitoOperazionePagamentoDTO checkCondizioniPagabili(EsitoOperazionePagamentoDTO dtoOut, List<CondizionePagamento> listaCondizioni);	
		
}
