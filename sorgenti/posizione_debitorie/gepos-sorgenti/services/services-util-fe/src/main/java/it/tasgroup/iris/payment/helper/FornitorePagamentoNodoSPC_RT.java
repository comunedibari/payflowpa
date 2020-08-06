package it.tasgroup.iris.payment.helper;

import it.gov.digitpa.schemas.x2011.pagamenti.RPTDocument;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Personalizzazione per Regione Toscana dell'implementazione standard del Nodo Dei Pagamenti SPC.<br>
 * 
 * 1. Il controllo sulla composizione Carrello � meno restrittivo dello Standard. (solo omogeneit� debitore)<br>
 * 2. Utilizza una sottoclasse personalizzata dell'RPTBuilder per la costruzione della richiesta.
 * 
 */
public class FornitorePagamentoNodoSPC_RT extends FornitorePagamentoNodoSPC {

	FornitorePagamentoNodoSPC_RT() {
		
	}

	@Override
	protected void verificaCarrello() throws PagamentoException {
        //
		// controllo omogeneit� per Debitore
		//
		String debitore = null;
		
		for (SessionShoppingCartItemDTO cartItem : pagamentiWIP) {

			if (debitore == null)
				debitore = cartItem.getDebtor();
			
			else if (!debitore.equals(cartItem.getDebtor()))
				// Per poter utilizzare questa modalit� di pagamento � necessario che i pagamenti siano riferiti ad un unico debitore e a favore di un unico Ente   
				throw new PagamentoException("posizionedebitoria.nodospc.error.stessodebitore", "DEBITORE NON OMOGENEO");
			
		}
		
	}
	
	/**
	 * Personalizzazione del metodo creaRichiestaPagamentoTelematico per il customer Regione Toscana.
	 * La richiesta di pagamento RPT viene creata a partire da UN UNICO pagamento risultante dalla
	 * somma di tutti gli elementi del carrello. (vedi <code>creaCarrelloRegioneToscana</code>)
	 */
	@Override
	protected String creaRichiestaPagamentoTelematico(HttpServletRequest request, DistintaCartaCreditoVO distinta) {
		
		List<SessionShoppingCartItemDTO> pagamenti = creaCarrelloRegioneToscana(distinta.getCodPagamento());
		
		String ibanAddebito = request.getParameter("ibanAddebito");
		String codFiscale = request.getParameter("codFiscale"); // quello della form
		
		FrontEndContext fec = WebUtil.getLocatedFrontEndContext(request);
		@SuppressWarnings("deprecation")
		OperatoriPojo operatorePojo = (OperatoriPojo)fec.getOperatore().getPojo();
		
		OperatoreDTO operatore = new OperatoreDTO();
        operatore.setNome(operatorePojo.getName());
        operatore.setCognome(operatorePojo.getSurname());

        IntestatarioDTO profilo = new IntestatarioDTO();
        profilo.setIdFiscale(profileManager.getLapl());
        profilo.setEmail(profileManager.getEmailPagante());
// TODO: eliminare dopo i test
        if(isAnonymous()) {
        	profilo.setIdFiscale(codFiscale);
        }

        String richiestaPagamentoTelematico = RPTBuilder.buildRPTXmlString(profilo, isAnonymous(), operatore, pagamenti, cfgPagamento.getTipoVersamento(), 
				importoTotalePagamenti, distinta.getCodPagamento(), distinta.getCodTransazione(), distinta.getCodTransazionePSP(), distinta.getDataOrdine(), ibanAddebito);

        
		return richiestaPagamentoTelematico;
	}

	/**
	 * Creazione di un carrello fittizio con un solo elemento risultato della somma di tutti gli elementi del carrello
	 * @return
	 */
	protected List<SessionShoppingCartItemDTO> creaCarrelloRegioneToscana(String codicePagamentoIris) {
		
		SessionShoppingCartItemDTO item = new SessionShoppingCartItemDTO();
		//TODO qui tutto � cablato come regione toscana e l'iban di appoggio � null
		item.setIdFiscaleEnte(FornitorePagamentoNodoSPC.ID_FISCALE_RT);
		item.setEnte(FornitorePagamentoNodoSPC.DENOMINAZIONE_RT);
		//TODO non � detto che il conto di appoggio sia quello di regione toscana
		item.setIbanBeneficiario(FornitorePagamentoNodoSPC.IBAN_CONTO_TECNICO);
		
		item.setImporto(importoTotalePagamenti);
		item.setIdPagamentoEnte(codicePagamentoIris);
		// posso prendere solo il primo elemento perch� sono omogenei per debitore
		item.setCodFiscaleDebitore(pagamentiWIP.get(0).getCodFiscaleDebitore());
		item.setIbanAppoggio(null);
		
		// creo carrello con un solo elemento
		List<SessionShoppingCartItemDTO> carrelloRegioneToscana = new ArrayList<SessionShoppingCartItemDTO>();
		carrelloRegioneToscana.add(item);
		return carrelloRegioneToscana;
	}
	
}
