package it.tasgroup.iris.facade.ejb.anonymous.dto.builder;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * @author pazzik
 *
 */
public class SessionShoppingCartItemDTOBuilder {
	
	private static final String PAGAMENTO_IN_CORSO = StatiPagamentiIris.IN_CORSO.getPagaMapping();
	
	public static SessionShoppingCartItemDTO populateSessionShoppingCartItemDTO(CondizionePagamento condizione, String stato){
			
			SessionShoppingCartItemDTO dto = new SessionShoppingCartItemDTO();
			
			dto.setIdCondizione(condizione.getIdCondizione());
			dto.setIdPendenza(condizione.getPendenza().getIdPendenza());
			dto.setIdPagamentoEnte(condizione.getIdPagamento());
			dto.setCausaleCondizione(condizione.getCausalePagamento());
			dto.setCausale(condizione.getPendenza().getDeCausale());
			dto.setCartellaPagamento(new Integer(1).equals(condizione.getPendenza().getCartellaPagamento()));
			
			Set<DestinatariPendenza> destinatari = condizione.getPendenza().getDestinatari();
			for (DestinatariPendenza destinatario : destinatari) {
				if(!"DE".equals(destinatario.getTiDestinatario())) {
					dto.setCodFiscaleDebitore(destinatario.getCoDestinatario());
					break;
				}
			}
			// dto.setCodFiscaleDebitore(condizione.getPendenza().getDestinatari().iterator().next().getCoDestinatario());
			
			dto.setIdMittentePend(condizione.getPendenza().getIdMittente());
			dto.setDeMittentePend(condizione.getPendenza().getDeMittente());
			
			dto.setIdEnte(condizione.getEnte().getIdEnte());
			dto.setIdFiscaleEnte(condizione.getEnte().getIntestatarioobj().getLaplIForm());
			dto.setEnte(condizione.getEnte().getIntestatarioobj().getRagionesocialeIForm());
			dto.setImporto(condizione.getImTotale());
			dto.setScadenza(condizione.getDtScadenza());
			dto.setTipoPagamento(condizione.getTiPagamento());
			dto.setTipoSpontaneo("PEND");
			dto.setTributo(condizione.getPendenza().getTributoEnte().getDeTrb());
			dto.setIdTributo(condizione.getPendenza().getTributoEnte().getIdTributo());
			dto.setIdTributoEnte(condizione.getPendenza().getTributoEnte().getCdTrbEnte());
			if(condizione.getPendenza().getTributoEnte().getCfgTributoEntePlugin() != null) {
				dto.setCdPlugin(condizione.getPendenza().getTributoEnte().getCfgTributoEntePlugin().getCdPlugin());
			}
			dto.setDecorrenza(new Date());
			dto.setStato(PAGAMENTO_IN_CORSO);
			dto.setStatoPagamentoCondizione(condizione.getStatoPagamentoCalcolato());
			dto.setIuv(condizione.getIdPagamento());
			Set<Pagamenti> pagamenti = condizione.getPagamenti();
			
			if (pagamenti!=null){
				
				for(Pagamenti paga: pagamenti){
					if (paga.getStPagamento().equals(PAGAMENTO_IN_CORSO))
						dto.setIdDistinta(paga.getFlussoDistinta().getId().toString());
				}
				
			}	
			
			//eliminata la gestione di reperimento dell'IBAN dalla condizione
			/*if (StringUtils.isNotEmpty(condizione.getIbanBeneficiario())) {
				dto.setIbanBeneficiario(condizione.getIbanBeneficiario());
				dto.setIbanBeneficiarioCondizione(true); 
			} else {
			*/
				dto.setIbanBeneficiario(condizione.getPendenza().getTributoEnte().getIBAN());
				dto.setIbanBeneficiarioCondizione(false); 
			//}
			
			//dto.setDatiSpecificiRiscossione(condizione.getDatiRiscossione());
			// nel caso di MARCA BOLLO DIGITALE
			if ("E_BOLLO".equals(condizione.getPendenza().getTributoEnte().getCdTrbEnte())) {
				// recupero i dati specifici della marca da bollo, inseriti in fase di caricamento negli allegati
				AllegatiPendenza alpend =condizione.getAllegatiPendenza().iterator().next();
				
				String s=new String(Base64.decodeBase64(alpend.getDatiBody()));
				String[] xx=s.split(";");
				int i=0;
				while (i<xx.length){
					String cc=xx[i];
					String dd[]=cc.split("=");
					if ("TIPO".equals(dd[0])){
						dto.setMarcaBolloTipoBollo(dd[1]);
					}
					if ("PROV".equals(dd[0])){
						dto.setMarcaBolloProvResidenzaDebitore(dd[1]);
					}
					if ("HASH".equals(dd[0])){
						dto.setMarcaBolloHashDocumento(dd[1]);
					}
					i++;
				}
								
				
			}
			return dto;
		}
	
//	public static List<SessionShoppingCartItemDTO> populateSessionShoppingCartItemDTOList(Sessione session, String stato) {
//		
//		List<IrisGatewayClientShopCart> cart = session.getIrisGatewayClientShopCarts();
//		
//		List<SessionShoppingCartItemDTO> dtoList = new ArrayList<SessionShoppingCartItemDTO>();
//		
//		for(IrisGatewayClientShopCart cartItem: cart){
//			
//			CondizionePagamento condizione = cartItem.getCondizionePagamento();
//			
//			SessionShoppingCartItemDTO newDTO = populateSessionShoppingCartItemDTO(condizione, stato);
//			
//			dtoList.add(newDTO);
//		}
//		
//		return dtoList;
//	}

	public static List<SessionShoppingCartItemDTO> populateSessionShoppingCartItemDTOList(List<CondizionePagamento> condizioni, String stato) {
		
		List<SessionShoppingCartItemDTO> cartItems = new ArrayList<SessionShoppingCartItemDTO>();
		
		for(CondizionePagamento condizione: condizioni){
			SessionShoppingCartItemDTO newDTO = populateSessionShoppingCartItemDTO(condizione, stato);
			cartItems.add(newDTO);
		}
		
		// accorpo i pagamenti per pendenza
		Map<String, List<SessionShoppingCartItemDTO>> mapItemsByPendenza = new HashMap<String, List<SessionShoppingCartItemDTO>>();
		for (SessionShoppingCartItemDTO item : cartItems) {
			List<SessionShoppingCartItemDTO> tmpList = mapItemsByPendenza.get(item.getIdPendenza());
			if(tmpList == null) {
				tmpList = new ArrayList<SessionShoppingCartItemDTO>();
				mapItemsByPendenza.put(item.getIdPendenza(), tmpList);
			}
			tmpList.add(item);
		}
		

		List<SessionShoppingCartItemDTO> dtoList = new ArrayList<SessionShoppingCartItemDTO>();
		
		for (String idPendenza : mapItemsByPendenza.keySet()) {
			List<SessionShoppingCartItemDTO> tmpList = mapItemsByPendenza.get(idPendenza);
			String idCondizioniCartella = "";
			List<BigDecimal> importiCartella = new ArrayList<BigDecimal>();
			int progr = 1;
			List<SessionShoppingCartItemDTO> cartella = new ArrayList<SessionShoppingCartItemDTO>();
			for (SessionShoppingCartItemDTO sessionShoppingCartItemDTO : tmpList) {
				if(sessionShoppingCartItemDTO.isCartellaPagamento()) {
					// creo una lista con i pagamenti della cartella
					sessionShoppingCartItemDTO.setNumeroPagamentiCartella(tmpList.size());
					sessionShoppingCartItemDTO.setProgressivoPagamentoCartella(progr++);
					idCondizioniCartella += sessionShoppingCartItemDTO.getIdCondizione() + "!";
					importiCartella.add(sessionShoppingCartItemDTO.getImporto());
					cartella.add(sessionShoppingCartItemDTO);
					
				} else {
					// altrimenti aggiungo subito alla lista
					dtoList.add(sessionShoppingCartItemDTO);
					
				}
			}

			if(!cartella.isEmpty()) {
				// setto gli id di tutte le condizioni della cartella e aggiuno alla lista glbale del carrello
				for (SessionShoppingCartItemDTO itemCartella : cartella) {
					itemCartella.setIdCondizioniCartella(idCondizioniCartella);
					itemCartella.setImportiCartella(importiCartella);
					dtoList.add(itemCartella);
				}
				
			}
		
		}
		
		return dtoList;
	}
	
// Non UTLIZZATO	
//	public static SessionShoppingCart<SessionShoppingCartItemDTO>  populateNewSessionShoppingCart(IProfileManager profile, List<CondizionePagamento> condizioni){
//		
//		List<SessionShoppingCartItemDTO> cartItems = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTOList(condizioni, StatiPagamentiIris.IN_CORSO.getPagaMapping());
//		
//		SessionShoppingCart<SessionShoppingCartItemDTO> cart = new SessionShoppingCart<SessionShoppingCartItemDTO>();
//		
//		cart.setItemList(cartItems);
//		
//		return cart;
//		
//	}
}
