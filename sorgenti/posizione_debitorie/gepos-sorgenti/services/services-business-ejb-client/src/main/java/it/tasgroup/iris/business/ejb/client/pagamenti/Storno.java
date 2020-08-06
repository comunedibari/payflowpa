/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.pagamenti;

import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.StornoDTO;


public interface Storno
{	
	EsitoOperazionePagamentoDTO storna(StornoDTO stornoDto) ;

}
