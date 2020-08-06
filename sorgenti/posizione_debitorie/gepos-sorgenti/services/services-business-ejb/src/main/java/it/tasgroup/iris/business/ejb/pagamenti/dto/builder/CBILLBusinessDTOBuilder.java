/**
 * 
 */
package it.tasgroup.iris.business.ejb.pagamenti.dto.builder;

import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.dto.RichiestaAnnullamentoDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;

/**
 * @author pazzik
 *
 */
public class CBILLBusinessDTOBuilder {
	
	public final static String CBILL = CfgGatewayPagamento.CBILL;

	
	public static RichiestaAnnullamentoDTO populateRichiestaAnnullamentoDTO(String codTrans, TestataMessaggioDTO testata) {
		
		RichiestaAnnullamentoDTO richDTO = new RichiestaAnnullamentoDTO();
		
		richDTO.setCanale(testata.getSenderSil());
		
		// eliminato altrimenti viene salvato in POL.COD_AUTORIZZAZIONE il codTransazione
		//richDTO.setCodAutorizzazione(codTrans);
		
		richDTO.setCodiceTransazione(codTrans);
		
		TestataMessaggioDTO newHeader = populateTestata(codTrans, testata);

		richDTO.setTestata(newHeader);
		
		return richDTO;
	}

	private static TestataMessaggioDTO populateTestata(String codTrans, TestataMessaggioDTO testataAuth) {
		
		TestataMessaggioDTO header = new TestataMessaggioDTO();
		header.setSenderSys(testataAuth.getSenderSys());
		header.setSenderSil(testataAuth.getSenderSil());
		header.setReceiverSil(testataAuth.getReceiverSil());
		header.setReceiverSys(testataAuth.getReceiverSys());
		header.setSession(populateSessionIdDTO(codTrans, testataAuth.getSession()));
		
		return header;
	}

	private static SessionIdDTO populateSessionIdDTO(String codTrans, SessionIdDTO session) {
		
		SessionIdDTO sessionDTOnew = new SessionIdDTO();
		sessionDTOnew.setWeakEqualityCheck(true);
		sessionDTOnew.setDataOraAccesso(session.getDataOraAccesso());
		sessionDTOnew.setIdSistema(session.getIdSistema());
		sessionDTOnew.setIdTerminale(session.getIdTerminale());
		//sessionDTOnew.setToken(codTrans);
		sessionDTOnew.setToken(session.getToken());
		
		return sessionDTOnew;
	}

}
