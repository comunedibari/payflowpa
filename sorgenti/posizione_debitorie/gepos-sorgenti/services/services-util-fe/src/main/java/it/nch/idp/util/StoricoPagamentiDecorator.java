package it.nch.idp.util;

import static it.tasgroup.iris.web.decorator.MarkupHelper.createStatus;

import javax.servlet.jsp.PageContext;

import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.utility.web.WebUtility;
import it.tasgroup.iris.payment.helper.RPTBuilder;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.web.decorator.MarkupHelper.StatusLevel;
import it.tasgroup.iris.web.decorator.ViewLayerDecoratorUtils;

import org.displaytag.decorator.TableDecorator;

public class StoricoPagamentiDecorator  extends TableDecorator {
	
    public String getStato() {
		PagamentoPosizioneDebitoriaForHomePageVO pagamento = (PagamentoPosizioneDebitoriaForHomePageVO) getCurrentRowObject();
		if (pagamento.getStato().equals("IC")) {
			return createStatus("In corso", StatusLevel.WARNING);
		} else if (pagamento.getStato().equals("ST")) {
			return createStatus("Stornato", StatusLevel.INFO);
		} else if (pagamento.getStato().equals("ES")) {
			if (pagamento.getImportoTotCondizione().compareTo(pagamento.getImporto()) <= 0) {
				if(pagamento.getFlagContoTerzi()) {
					return createStatus("Eseguito conto terzi", StatusLevel.SUCCESS);
				} else {
					return createStatus(getTextMsg(super.getPageContext(),"posizionedebitoria.distinte.ricerca.stato.eseguito"), StatusLevel.SUCCESS);
				}
			} else {
				return createStatus(getTextMsg(super.getPageContext(),"posizionedebitoria.statoPagamento.irregolare"), StatusLevel.WARNING);
			}
		} else if (pagamento.getStato().equals("EF")) {
			if (pagamento.getImportoTotCondizione().compareTo(pagamento.getImporto()) <= 0) {
				if(pagamento.getFlagContoTerzi()) {
					return createStatus("Eseguito S.B.F. conto terzi", StatusLevel.SUCCESS);
				} else {
					return createStatus("Eseguito S.B.F.", StatusLevel.SUCCESS);
				}
			} else {
				return createStatus(getTextMsg(super.getPageContext(),"posizionedebitoria.statoPagamento.irregolare"), StatusLevel.WARNING);
			}
		} else if (pagamento.getStato().equals("AN") || pagamento.getStato().equals("AO")) {
			return createStatus("Annullato", StatusLevel.IMPORTANT);
		} else if (pagamento.getStato().equals("IE")) {
			return createStatus("In errore", StatusLevel.IMPORTANT);
		} else if (pagamento.getStato().equals("NE")) {
			return createStatus("Non eseguito", StatusLevel.IMPORTANT);
		}
		return createStatus(pagamento.getStato(), StatusLevel.INFO);  //Piuttosto che null ..meglio piuttosto.
    }
    
	public String getUltimiPagamentiDettaglio() {
		
		PagamentoPosizioneDebitoriaForHomePageVO pagamento = (PagamentoPosizioneDebitoriaForHomePageVO) getCurrentRowObject();
		
		return ViewLayerDecoratorUtils.getPaymentsListRowButtons(pagamento.getIdPendenza(), pagamento.getCodPagamento(), pagamento.getIdPagamento().toString(), pagamento.getFlusso(), pagamento.getLaplPagante(), pagamento.getStato(), pagamento.getFlagIncasso(), true,pagamento.isAssociatedDocAvailable(), pagamento.getFlagConsegnaRicevutaIdp());
	
	}

    public String getCausalePagamento() {
    	PagamentoPosizioneDebitoriaForHomePageVO pagamento = (PagamentoPosizioneDebitoriaForHomePageVO) getCurrentRowObject();
		String causaleDecodificata = CausaleHelper.getCausale(pagamento.getCausale(), pagamento.getIdEnte(), pagamento.getIdTributoEnte(), pagamento.getCdPlugin(), PosizioneDebitoriaConstants.OPERATORE_CITTADINO);
        
        StringBuffer causaleComposta = new StringBuffer("");
        
        causaleComposta.append(pagamento.getTipoTributo());
        causaleComposta.append(": ");
        // causale della pendenza
        causaleComposta.append(causaleDecodificata);
        // causale del pagamento
        if (pagamento.getCausalePagamento() != null) {
            causaleComposta.append(" - ").append(pagamento.getCausalePagamento());        	
        }
	    buildCausaleComposta(pagamento, causaleComposta);
	    return causaleComposta.toString();    
    }
    
    public String getCausalePosizioneDebitoriaListStyle() {
       PagamentoPosizioneDebitoriaForHomePageVO pagamento = (PagamentoPosizioneDebitoriaForHomePageVO) getCurrentRowObject();
       String causaleDecodificata = CausaleHelper.getCausale(pagamento.getCausale(), pagamento.getIdEnte(), pagamento.getIdTributoEnte(), pagamento.getCdPlugin(), PosizioneDebitoriaConstants.OPERATORE_CITTADINO);
       StringBuffer causaleComposta = new StringBuffer("");
       causaleComposta.append(getTextMsg("posizionedebitoria.tipoDebito") + ": " + pagamento.getTipoTributo());
       causaleComposta.append("<br>" + getTextMsg("posizionedebitoria.ente.creditore") + ": " + pagamento.getEnte());
       causaleComposta.append("<br>" + getTextMsg("posizionedebitoria.causale") + ": " + causaleDecodificata);
       buildCausaleComposta(pagamento, causaleComposta);
       return causaleComposta.toString();    	
    }
    
    public String getCausaleCompostaListView() {
        PagamentoPosizioneDebitoriaForHomePageVO pagamento = (PagamentoPosizioneDebitoriaForHomePageVO) getCurrentRowObject();
		String causaleDecodificata = CausaleHelper.getCausale(pagamento.getCausale(), pagamento.getIdEnte(), pagamento.getIdTributoEnte(), pagamento.getCdPlugin(), PosizioneDebitoriaConstants.OPERATORE_CITTADINO);
        StringBuffer causaleComposta = new StringBuffer("");
        // causale della pendenza
        causaleComposta.append(causaleDecodificata);
        // causale del pagamento
        if (pagamento.getCausalePagamento() != null) {
            causaleComposta.append(" - ").append(pagamento.getCausalePagamento());        	
        }
        buildCausaleComposta(pagamento, causaleComposta);
        return causaleComposta.toString();
    }

	/**
	 * 
	 * @param pageContext
	 * @param message
	 * @return
	 */
	private String getTextMsg(String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(super.getPageContext(), message);
		} catch (Exception Ex) {
		}
		return msg;
	}	

	private void buildCausaleComposta(PagamentoPosizioneDebitoriaForHomePageVO pagamento, StringBuffer causaleComposta) {
		if (pagamento.getFlagContoTerzi()) {
        	String debitore = pagamento.getDebitore();
        	if(pagamento.getDebitore().equals(ConfigurationPropertyLoader.getInstance( "iris-fe.properties").getProperty("iris.codice.fiscale.anonymous")))
        			debitore = "ANONIMO";
            causaleComposta.append("<br> <em>Pagamento per conto di ").append(debitore).append("</em>");        	
        }
           
        if (pagamento.getRiscossore() != null) {
	        causaleComposta.append("<br>(");
	        causaleComposta.append("Riscossore: ");
	        causaleComposta.append(pagamento.getRiscossore() + " /");
	        causaleComposta.append((pagamento.getRiferimento() == null ?  " - )" : (" " + pagamento.getRiferimento() + " )")));
        }
	}
    
    
    /**
     * 
     * @param pageContext
     * @param message
     * @return
     */
	private static String getTextMsg(PageContext pageContext, String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message);
		} catch (Exception Ex) {
		}
		return msg;
	}    
    
}
