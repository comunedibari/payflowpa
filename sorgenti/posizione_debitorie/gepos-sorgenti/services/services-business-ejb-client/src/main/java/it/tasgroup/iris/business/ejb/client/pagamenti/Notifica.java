/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.pagamenti;

import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.NotificaPagamentoDTO;

import java.io.Serializable;


public interface Notifica extends Serializable
{	
	EsitoOperazionePagamentoDTO notifica(NotificaPagamentoDTO dto);
}
