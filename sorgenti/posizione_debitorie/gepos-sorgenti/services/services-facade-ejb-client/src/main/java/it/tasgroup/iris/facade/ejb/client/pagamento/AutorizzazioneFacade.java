/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.pagamento;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;

import java.util.List;
import java.util.Map;



public interface AutorizzazioneFacade
{

	List<DocumentoDiPagamentoDTO> readDDPs(FrontEndContext fec,
			Map<String, ? extends Object> parameters) throws Exception;	
}
