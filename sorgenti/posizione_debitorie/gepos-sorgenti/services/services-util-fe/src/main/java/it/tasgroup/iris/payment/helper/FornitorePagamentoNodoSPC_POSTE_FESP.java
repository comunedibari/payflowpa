package it.tasgroup.iris.payment.helper;

import it.gov.digitpa.schemas.x2011.pagamenti.RPTDocument;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.tasgroup.iris.comunication.helper.IRISComunicationHelper;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.iris.util.XMLUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public class FornitorePagamentoNodoSPC_POSTE_FESP extends FornitorePagamentoNodoSPC_RT {

	FornitorePagamentoNodoSPC_POSTE_FESP() {
		
	}
	
	protected void verificaSoggettoVersante() throws IncompleteRegistrationException{
		
			
		IntestatarioValidator.validateVersanteFESP(intestatarioVersante, operatoreVersante);
		
	}
	
	@Override
	protected void inizializzaAnagraficaVersante(HttpServletRequest request) {
		
		operatoreVersante = new OperatoreDTO();

        operatoreVersante.setNome(operatorePojo.getName());

        operatoreVersante.setCognome(operatorePojo.getSurname());
        
        String comunicationId = IRISComunicationHelper.getIrisId(request);
		
        String email = IRISComunicationHelper.getEmail(comunicationId);

        intestatarioVersante = AnagraficaDTOBuilder.fillIntestatarioDTO(operatorePojo, email);
              
	}
	
	@Override
	protected void inizializzaAnagraficaDebitore() {
		
		try {
			String idFiscaleDebitore = pagamentiWIP.get(0).getCodFiscaleDebitore();
			
			AnagraficaFacade anagraficaBean = (AnagraficaFacade) ServiceLocator.getSLSBProxy("anagraficaFacadeBean");
		
			intestatarioDebitore = anagraficaBean.readIntestatario(idFiscaleDebitore);
			
        } catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void verificaCarrello() throws PagamentoException {
		//
		// controllo numero pagamenti del carrello
		//
		if(pagamentiWIP.size() > N_MAX_PAGAMENTI)
			throw new PagamentoException("posizionedebitoria.nodospc.error.numeromaxpagamenti", "Superato numero massimo pagamenti consentiti dal Nodo " + pagamentiWIP.size() + " su " + N_MAX_PAGAMENTI);
		
		
		//
		// controllo omogeneita per Debitore e Beneficiario
		//
		String pagante = profileManager.getCodiceFiscale(), debitore = null;
		
		for (SessionShoppingCartItemDTO cartItem : pagamentiWIP) {
				
			if (!pagante.equals(cartItem.getDebtor())) 
				// Per poter utilizzare questa modalità di pagamento è necessario che i pagamenti siano tutti riferiti al pagante
				throw new PagamentoException("posizionedebitoria.nodospc.error.noDelega", "DEBITORE NON OMOGENEO");
			
			if (debitore == null)
				debitore = cartItem.getDebtor();
			
			if (!debitore.equals(cartItem.getDebtor()))
				// Per poter utilizzare questa modalità di pagamento è necessario che i pagamenti siano riferiti ad un unico debitore
				throw new PagamentoException("posizionedebitoria.nodospc.error.stessodebitore", "DEBITORE NON OMOGENEO");
		
		}
		
	}
	
	protected String creaRichiestaPagamentoTelematico(HttpServletRequest request, DistintaCartaCreditoVO distinta) {
			
		List<SessionShoppingCartItemDTO> cartRT = creaCarrelloRegioneToscana(distinta.getCodPagamento());
		
		for (SessionShoppingCartItemDTO cartItem : cartRT)
			// TODO PAZZIK: QUI DEVO METTERE L'IBAN DI POSTE?
			cartItem.setIbanAppoggio(IBAN_CONTO_TECNICO);
		
		RPTDocument rpt = null;
		
		String ibanAddebito = request.getParameter("ibanAddebito");
		
		try {
        	
			AnagraficaFacade anagraficaBean = (AnagraficaFacade) ServiceLocator.getSLSBProxy("anagraficaFacadeBean");
		
			IntestatarioDTO debitoreDTO = anagraficaBean.readIntestatario(cartRT.get(0).getCodFiscaleDebitore());
			
			rpt = FESP_RPTBuilder.buildRPT(intestatarioVersante, operatoreVersante, cartRT, cfgPagamento.getCfgModalitaPagamento().getId(), 
					importoTotalePagamenti, distinta.getCodTransazione(), distinta.getDataOrdine(), ibanAddebito, debitoreDTO);

        } catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String rptString = rpt.toString();
		
		try {
			
			String schemaURL = "/Fesp6Postecom.xsd";
			
			XMLUtils.validate(rptString, schemaURL);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return rptString;
	}
	
}
