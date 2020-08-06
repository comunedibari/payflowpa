package it.nch.idp.util;

import static it.tasgroup.iris.shared.util.UtilDate.date2StringFormat;
import static it.tasgroup.iris.shared.util.UtilDate.dateGreaterThanToday;
import static it.tasgroup.iris.shared.util.UtilDate.dateLessThanToday;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createClickableIcon;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createClickableIconOnlyOneTime;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createStatus;
import static it.tasgroup.iris.web.decorator.MarkupHelper.createTransparentStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import it.nch.idp.posizionedebitoria.AllegatoAvvisoPosDeb;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoVO;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaActionHelper;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaHelper;
import it.nch.idp.posizionedebitoria.PrenotaAvvisiDigitaliVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.web.common.action.BackBaseAction;
import it.nch.profile.FactoryProfileManagerClient;
import it.nch.profile.IProfileManager;
import it.nch.utility.web.WebUtility;
import it.tasgroup.crypt.url.AllegatoParametersEncrypter;
import it.tasgroup.crypt.url.DownloadQuietanzaParametersEncrypter;
import it.tasgroup.crypt.url.DownloadRicevutaParametersEncrypter;
import it.tasgroup.crypt.url.ManageDDPParametersEncrypter;
import it.tasgroup.crypt.url.URLParametersEncrypter;
import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.sessioncart.ShoppingCartSessionHelper;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCart;
import it.tasgroup.iris.web.decorator.MarkupHelper.IconDimension;
import it.tasgroup.iris.web.decorator.MarkupHelper.StatusLevel;
import it.tasgroup.services.util.enumeration.EnumStatoAvviso;
import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;
import it.tasgroup.services.util.enumeration.EnumUtils;

public class StatoCondizionePagamentoAvvisoDecorator {
	private static String nonpagatocolor = "testonormalerosso";
	private static String nonpagabilecolor = "testonormalebold";
	private static String pagatocolor = "testonormaleverde";
	private static String giallocolor = "testonormalegiallo";
	private final static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final Pattern TAG_P_REGEX = Pattern.compile("<p>(.+?)</p>");


	public static String decorateStatoPagamento(String stato, PageContext pageContext) {
		return decorateStatoPagamentoHtml(stato, null, pageContext);
	}
	

	public static String decorateDdpLinkHtml(CondizionePagamentoPosizioneDebitoriaVO condVO, PageContext pageContext) {
		StringBuilder sb = new StringBuilder();

		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");

		String tooltip = "pdf";

		String idCondizione = condVO.getIdCondizione();

		if (condVO.isDDPAssociated()) {
			
			if (idCondizione != null && !"".equals(idCondizione))
				tooltip = getTextMsg(pageContext, "posizionedebitoria.dettaglio.idPagamento") + " " + idCondizione.trim();
				
			ManageDDPParametersEncrypter ep = new ManageDDPParametersEncrypter(condVO.getCfPaganteDDP(),condVO.getIdDocumento(),condVO.getIntestatarioDDP());
				
			String openUrl = gwBaseUrl+"/documentiPagamento.do?method=downloadPublicDDP&amp;"+SharedConstants.CRYPTEDPARAMS+"="+ep.encrypt();

			sb.append("<a class=\"iris-button-standard\" href=\"" + openUrl + "\">");
			//sb.append("<img src=\"/images/icona_pdf_24.png\" alt=\"" + tooltip + "\" title=\"" + tooltip + "\"/>");
			sb.append("<i class='icon-file-alt icon' alt='"+ tooltip+"' title='" + tooltip +"'></i>");
			sb.append("DDP");
			sb.append("</a>");
		}

		return sb.toString();
	}
	

	public static String decorateStatoIncassoHtml(CondizionePagamentoPosizioneDebitoriaVO rata, PageContext pageContext) {
		String statoIncassoKey = rata.getStatoIncasso();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		// TODO consider profiling following 2 lines 
		boolean pca = PosizioneDebitoriaActionHelper.isModalitaAmministrazione(request);
		boolean pcr = PosizioneDebitoriaActionHelper.isModalitaPosizioneCreditoria(request);
		 
		
		String html = "";
		if (statoIncassoKey != null) {
			EnumStatoIncasso statoIncasso = EnumUtils.findByChiave(statoIncassoKey,EnumStatoIncasso.class);
			String displayStatus="",cssClass="";
			switch (statoIncasso) {
				case ATTESO:	
					displayStatus = (pca || pcr) ? getTextMsg(pageContext, statoIncasso.getChiaveBundle()) : "";
					break;
				case ACCREDITATO_CONTO_TECNICO:
					displayStatus = pca ? getTextMsg(pageContext, statoIncasso.getChiaveBundle()) : "";
					cssClass = giallocolor; 
					break;
				case RIACCREDITATO_ENTE:			
					displayStatus = (pca || pcr) ? getTextMsg(pageContext, statoIncasso.getChiaveBundle()) : "";
					cssClass = pagatocolor;
					break;
				case NON_GESTITO:			
					displayStatus = (pca || pcr) ? getTextMsg(pageContext, statoIncasso.getChiaveBundle()) : "";
					cssClass = nonpagabilecolor;
					break;
				default:
					break;
			}
			html = statoIncasso != null ? "<span class='"+ cssClass + "'>" + displayStatus  + "</span>" : "";
		}
		return html;
	}
	
	private static List<String> getTagValues(final String str) {
	    final List<String> tagValues = new ArrayList<String>();
	    final Matcher matcher = TAG_P_REGEX.matcher(str);
	    while (matcher.find()) {
	        tagValues.add(matcher.group(1));
	    }
	    return tagValues;
	}

	public static String decorateButtons(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO condizioneVo, PageContext pageContext, int index) {
		String resultHtml = prepareRender(dettaglioAvviso,condizioneVo, pageContext, Columns.BUTTONS_COLUMN);
		List<String> lista = getTagValues(resultHtml);
		if (lista.size() > index)
			resultHtml = "<p>" + lista.get(index) + "</p>";
		return resultHtml;
	}

	public static String fullDecorateButtons(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO condizioneVo, PageContext pageContext, int index) {
		String result = "";
		String renderedHtml = prepareRender(dettaglioAvviso,condizioneVo, pageContext, Columns.BUTTONS_COLUMN);
		String head = "<td class=\"\" style=\"background-color: white;\"/><div class=\"buttons-list\">";
		String headSpan = "<td class=\"\" style=\"background-color: white;\" "
				+ "rowspan=\"" + dettaglioAvviso.getCondizioni().size() + "\""
						+ "/><div class=\"buttons-list\">";
		String tail = "</div></td>";
		
		String pagaSubito =  getTextMsg(pageContext,"home.pendenza.pagaSubito");
		String aggCarrello = getTextMsg(pageContext,"home.pendenza.aggCarrello");
		
		if (renderedHtml.indexOf(pagaSubito) != -1 || renderedHtml.indexOf(aggCarrello) != -1) {
			if (index == 0)
				result = headSpan + renderedHtml + tail;
		} else {
			List<String> lista = getTagValues(renderedHtml);
			if (lista.size() > index)
				result = head + "<p>" + lista.get(index) + "</p>" + tail;
		}
		return result;
	}

	public static String decorateButtons(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO condizioneVo, PageContext pageContext) {
		return prepareRender(dettaglioAvviso,condizioneVo, pageContext, Columns.BUTTONS_COLUMN);
	}
	
	public static String decorateStatoPagamentoCondizione(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO condizioneVo, PageContext pageContext) {
		return prepareRender(dettaglioAvviso, condizioneVo, pageContext, Columns.STATO_COLUMN);
	}
	
	

	enum Columns {
		STATO_COLUMN,
		BUTTONS_COLUMN
	}
	
	/**
	 * @param cart
	 * @param dettaglioAvviso
	 * @param condizioneVo
	 * @return
	 */
	private static boolean isEquivalentPaymentInCart(SessionShoppingCart cart, AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO currPayment){
		
		List<CondizionePagamentoPosizioneDebitoriaVO> payments = dettaglioAvviso.getSoluzionePagUnica();
		
		// nel caso di cartella di pagamento aggiungo tutti gli id
		// pagamento della pendenza
		if (payments!=null) {
			for (CondizionePagamentoPosizioneDebitoriaVO payment : payments) {
				
				String idCondizione = payment.getIdCondizione();
				
				if (!idCondizione.equals(currPayment.getIdCondizione()))
					if (cart.containsItem(idCondizione))
						return true;
			}
		}	
		return false;
		
		
	}
	
	private static boolean isPagoDopo(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso) {
		Properties cpl = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
		return 
				(dettaglioAvviso.getIdTributo() != null && dettaglioAvviso.getIdTributo().equals("Categoria013") && 
						Boolean.parseBoolean(cpl.getProperty("paytas.abilita.flag.opposizione.730", "")));
	}
	
	/*
	 * Nota: il parametro columnToRender serve per mettere a comune la logica di rendering senza ripeterla su ogni metodo,
	 * qst soluzione si e' resa necessaria dato che il decorator e' attualmente basato su metodi statici, non potendo quindi introdurre
	 * instance fields di appoggio (potenzialmente condivisi fra diversi thread)
	 */
	public static String prepareRender(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO condizioneVo,
			PageContext pageContext, Columns columnToRender) {
	
		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");

		Properties feProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
		
		String publicBaseUrl= feProperties.getProperty("iris.public.baseUrl");
		
		boolean isCartellaPagamento = new Integer(1).equals(dettaglioAvviso.getCartellaPagamento());
		
		String bundleKey = condizioneVo.getStatoPagamentoCondizione().getChiaveBundle();
		
		String statusHtml = getTextMsg(pageContext, bundleKey);
		
		String buttonsHtml = "";
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		switch (condizioneVo.getStatoPagamentoCondizione()) {
		
			case PAGATA:
								
				IProfileManager profile;
				
				try {
					
					profile = FactoryProfileManagerClient.getProfileManager(request, null);
					
				} catch (Exception e) {
					
					throw new RuntimeException(e);
					
				}
				
				String codiceFiscaleUtenteLoggato = profile.getCodiceFiscale();
	
				if (condizioneVo.getNumPagamenti() != null && condizioneVo.getNumPagamenti().intValue() > 0) {
									
	                StatiPagamentiIris statoPagamento = StatiPagamentiIris.getStatiPagamentiIrisFromPaga(condizioneVo.getStatoPagamento());
	                
	                String stato = "";
	                
					if (condizioneVo.getTotale().compareTo(condizioneVo.getImportoPagamentoIris())>0) {
						// Pagamento irregolare
						
						stato = getTextMsg(pageContext,"posizionedebitoria.statoPagamento.irregolare");
						statusHtml = createStatus(stato, StatusLevel.IMPORTANT);
	
			        	DecimalFormat decf1 = new DecimalFormat("##,###,##0.00");
						statusHtml += " <p> Importo pagato = " +  decf1.format(condizioneVo.getImportoPagamentoIris())+"&nbsp;&euro; </p>"; 
						
	
					} else {
						
		                if (statoPagamento== StatiPagamentiIris.ESEGUITO)
		                     stato = getTextMsg(pageContext,"posizionedebitoria.distinte.ricerca.stato.eseguito");
		                else 
		                     stato = getTextMsg(pageContext,"posizionedebitoria.distinte.ricerca.stato.eseguitosbf");
		                
		                statusHtml = createStatus(stato, StatusLevel.SUCCESS);
					}    
	                
	                
	                statusHtml += " <p> (il " +  dateFormat.format(condizioneVo.getDataDecorrenza());
	                
	                if(!condizioneVo.getCodPagante().equals(codiceFiscaleUtenteLoggato))
	                	 statusHtml += " da " + (condizioneVo.getCodPagante().equals("ANONYMOUS") ? 
	                			 						getTextMsg(pageContext,"posizionedebitoria.distinte.ricerca.codPagante.ANONYMOUS") : 
	                			 							condizioneVo.getCodPagante());
	                
	                statusHtml += ")</p>";
	                 
	                
				} else {
					// pagata lato ente
					String dataPagamento = condizioneVo.getDataPagamento() != null ? dateFormat.format(condizioneVo.getDataPagamento()) : null;
	                
					String canalePagamento = condizioneVo.getCanalePagamento();
	
	                statusHtml = statusHtml +  (dataPagamento != null ? " il " + dataPagamento : "") + (canalePagamento != null ? " presso " + canalePagamento : "");
	                
	    			// Se l'ente ha comunicato un dettaglio transazione.
	                String dettaglioTransazioneCondizionePagata=getDettaglioTransazioneComunicataDaEnte(condizioneVo);
	                
	                if(!"".equals(dettaglioTransazioneCondizionePagata))
	                	statusHtml += "<p>"+dettaglioTransazioneCondizionePagata+"</p>"; 
	                
	
				}
				
	
				// ANDREA LIGUORO: mostro il bottone scarico degli allegati indipendentemente dallo stato dell'incasso
				if (EnumUtils.matchByChiave(condizioneVo.getStatoIncasso(), EnumStatoIncasso.ATTESO, EnumStatoIncasso.ACCREDITATO_CONTO_TECNICO,  EnumStatoIncasso.RIACCREDITATO_ENTE, EnumStatoIncasso.NON_GESTITO)) {
					// BEGIN: ANDREA LIGUORO: vedo se il pagamento associato alla condizione ha un documento associato disponibile 
					// (necessario per gestire la generazione della ricevuta ad opera di un ente esterno
					for (CondizionePagamentoVO condPag :dettaglioAvviso.getCondizioni()) {
						if (condPag.getPagamento()!=null) {
							if (condPag.getPagamento().isAssociatedDocAvailable()) {
							   DownloadQuietanzaParametersEncrypter dp1 = new DownloadQuietanzaParametersEncrypter(condizioneVo.getCodPagamento(),condizioneVo.getCodPagante(), condPag.getPagamento().getIdPagamento().toString());
							   String cParam1=dp1.encrypt();
							   buttonsHtml += "<p>" + createClickableIcon(gwBaseUrl+"/documentiPagamento.do?method=downloadQuietanza&"+ SharedConstants.CRYPTEDPARAMS +"=" + cParam1, "Ricevuta", "icon-file-alt", IconDimension.X2,true) + "</p>";
							} else {
								buttonsHtml += "<p></p>";
							}

						}
					}
				} 
				
				break;
				
			case DA_PAGARE:
				
				SessionShoppingCart cart = ShoppingCartSessionHelper.getCart(request);
		
				
				if (cart.containsItem(condizioneVo.getIdCondizione()))
					
					statusHtml = createTransparentStatus("icon-shopping-cart", getTextMsg(pageContext,"home.legenda.stato.carrello"));
				
				else if (isEquivalentPaymentInCart(cart, dettaglioAvviso, condizioneVo))
					
					statusHtml = createTransparentStatus("icon-shopping-cart", "Aggiunto al carrello un pagamento equivalente");
								
				else {
	
					String idCondizioneDaAggiungere;
						
					if (isCartellaPagamento) {
						
						idCondizioneDaAggiungere = "";
						
						List<CondizionePagamentoPosizioneDebitoriaVO> pagamenti = dettaglioAvviso.getSoluzionePagUnica();
						
						// nel caso di cartella di pagamento aggiungo tutti gli id
						// pagamento della pendenza
						for (int i = 0; i < pagamenti.size(); i++) {
							
							idCondizioneDaAggiungere += pagamenti.get(i).getIdCondizione();
							
							if (i < pagamenti.size() - 1)
								idCondizioneDaAggiungere += "!";
						}
						
					} else
						idCondizioneDaAggiungere = condizioneVo.getIdCondizione();
						
										
					statusHtml = createStatus(statusHtml, StatusLevel.INFO);

					String subito =  getTextMsg(pageContext,"home.pendenza.pagaSubito");
					String aggCarrello = getTextMsg(pageContext,"home.pendenza.aggCarrello");
					
            		if ("BOLLO_AUTO".equals(dettaglioAvviso.getCod_tributo()) && (Boolean) request.getAttribute("frazionato")) {
            			String requestParameter = "";
            			try {
							URLParametersEncrypter e = new URLParametersEncrypter("#");
							List<String> parameters = new ArrayList<String>();
							parameters.add(dettaglioAvviso.getCausale());
							parameters.add(dettaglioAvviso.getCodiceFiscale());
							parameters.add(dettaglioAvviso.getIdPendenza());
							parameters.add(dettaglioAvviso.getIdPendenzaEnte());
							requestParameter = e.cryptParameters(parameters);
						} catch (Exception e) {
							//TODO /* non riesco a criptare allora glieli do senza crittaggio */
							//requestParameter = dettaglioAvviso.getCausale() + ";" + dettaglioAvviso.getCodiceFiscale();
							e.printStackTrace();
						}
            				
            			String urlToChangeMesiValidita = publicBaseUrl +"enterFromIris.jsf?m=4&sessionId="+URLEncoder.encode(request.getSession().getId())+"&target=addon/bollo_auto/search.jsf?causale=" + requestParameter;
            			buttonsHtml += "<p>" + createClickableIconOnlyOneTime(urlToChangeMesiValidita, getTextMsg(pageContext,"posizionedebitoria.buttonAggiorna"), "icon-refresh", IconDimension.X2, true) + "</p>";
            		} else {
            			if (cart.isEmpty()) {	
            				if(!isPagoDopo(dettaglioAvviso)) {
            					buttonsHtml += "<p>" + createClickableIcon("javascript:submitOperation('PDC/avvisiMain.do?method=pagaSubito&selCheckbox=" + idCondizioneDaAggiungere + "','containerForm')", subito, "icon-hand-right", IconDimension.X2, true) + "</p>";						
            				}
            				
            			}
            			buttonsHtml += "<p>" + createClickableIcon("javascript:submitOperation('PDC/avvisiMain.do?method=pagaCondizione&selCheckbox=" + idCondizioneDaAggiungere + "','containerForm')", aggCarrello,"icon-shopping-cart", IconDimension.X2, true) + "</p>";
            		}
				}
				break;
				
			case IN_CORSO:
				//statusHtml = "<i class='icon-time'></i><span class='iris-status-warning'>" + statusHtml + "</span>";
	            statusHtml = createStatus(statusHtml,StatusLevel.WARNING);
	            if (operazioneAnnullabile(dettaglioAvviso)) {
	                String urlAbortPagamentoService = "PDC/avvisiMain.do?method=annullaPagamentoInCorsoNDP" +
                        "&selCheckbox=" + dettaglioAvviso.getIdPendenza() +"&isLente=true&selIdCondiz="+condizioneVo.getIdCondizione();
	                // TODO: la visualizzazione � da condizionare
	                buttonsHtml += "<p>" + createClickableIcon(urlAbortPagamentoService, "Annulla", "icon-remove", IconDimension.X2, true) + "</p>";
	            }
	            break;
				
			case NON_PAGABILE_TERMINI:
	            if (!dateLessThanToday(condizioneVo.getDataInizio())) {
	                statusHtml = "Pagabile dal : " + dateFormat.format(condizioneVo.getDataInizio());
	                statusHtml = createStatus(statusHtml, StatusLevel.WARNING);
	            } else if (!dateGreaterThanToday(condizioneVo.getDataFine())) {
                        if(condizioneVo.getStatoCondizione().equals("X")){
                            Logger.getLogger(StatoCondizionePagamentoAvvisoDecorator.class).debug("stato della condizione:" + condizioneVo.getStatoCondizione()+" - " + condizioneVo.getCausaleCondizione());
                        }else if (  dettaglioAvviso.getStatoEsito() != null && dettaglioAvviso.getStatoEsito().equals("OK")){
                            statusHtml = getTextMsg(pageContext,"home.legenda.stato.cod");
                            statusHtml = createStatus(statusHtml, StatusLevel.INFO);
                            if (ShoppingCartSessionHelper.getCart((HttpServletRequest) pageContext.getRequest()).isEmpty()) {
                            	if(!isPagoDopo(dettaglioAvviso)) {
                            		buttonsHtml += "<p>" + createClickableIcon("javascript:submitOperation('PDC/avvisiMain.do?method=pagaSubito&selCheckbox=" + condizioneVo.getIdCondizione() + "','containerForm')", "Paga Subito", "icon-hand-right", IconDimension.X2, true) + "</p>";
                            	}
                            }
                            buttonsHtml += "<p>" + createClickableIcon("javascript:submitOperation('PDC/avvisiMain.do?method=pagaCondizione&selCheckbox=" + condizioneVo.getIdCondizione() + "','containerForm')", "Aggiungi al carrello","icon-shopping-cart", IconDimension.X2, true) + "</p>";
                        }else{
                            statusHtml = getTextMsg(pageContext,"home.legenda.stato.scaduti"); 
                            statusHtml = createStatus(statusHtml, StatusLevel.WARNING);

                            if ("BOLLO_AUTO".equals(dettaglioAvviso.getCod_tributo()) 
                                            && dettaglioAvviso.getUrlUpdateService() != null 
                                            && dettaglioAvviso.getUrlUpdateService().length() > 0) {
                            	
                            		String urlUpdateService = "";
                            		if ((Boolean) request.getAttribute("frazionato")) {
                            			String requestParameter = "";
                            			try {
											URLParametersEncrypter e = new URLParametersEncrypter("#");
											List<String> parameters = new ArrayList<String>();
											parameters.add(dettaglioAvviso.getCausale());
											parameters.add(dettaglioAvviso.getCodiceFiscale());
											parameters.add(dettaglioAvviso.getIdPendenza());
											parameters.add(dettaglioAvviso.getIdPendenzaEnte());
											requestParameter = e.cryptParameters(parameters);
										} catch (Exception e) {
											//TODO /* non riesco a criptare allora glieli do senza crittaggio */
											//requestParameter = dettaglioAvviso.getCausale() + ";" + dettaglioAvviso.getCodiceFiscale();
											e.printStackTrace();
										}
                            			urlUpdateService = publicBaseUrl +"enterFromIris.jsf?m=4&sessionId="+URLEncoder.encode(request.getSession().getId())+"&target=addon/bollo_auto/search.jsf?causale=" + requestParameter;
                            		} else {
                            			urlUpdateService = "PDC/avvisiMain.do?method=refreshAvviso" +
                            					"&idPendenza=" + dettaglioAvviso.getIdPendenza() + "&idCondizione=" + condizioneVo.getIdCondizione();
                            		}
                                    buttonsHtml += "<p>" + createClickableIconOnlyOneTime(urlUpdateService, getTextMsg(pageContext,"posizionedebitoria.buttonAggiorna"), "icon-refresh", IconDimension.X2, true) + "</p>";
                            }
                        }
	            }
				break;
				
			case NON_PAGABILE_ASSOCIATA_DOCUMENTO:
	            String idDocumento = condizioneVo.getIdDocumento();
	            statusHtml = createTransparentStatus("icon-file", statusHtml);
	            if (idDocumento != null) {
	            	
	            	// LA RICHIESTA DI DOCUMENTO E' PUBBLICA E ANONIMA
					// Applico encryption.
					
					
					try {
						
						ManageDDPParametersEncrypter ep = new ManageDDPParametersEncrypter(condizioneVo.getCfPaganteDDP(),idDocumento,condizioneVo.getIntestatarioDDP());
						
						String openUrl = gwBaseUrl+"/documentiPagamento.do?method=downloadPublicDDP&amp;"+SharedConstants.CRYPTEDPARAMS+"="+ep.encrypt();

						String url_back_annulla=BackBaseAction.getLastPageURL((HttpServletRequest)pageContext.getRequest());
						
						String cancelUrl = gwBaseUrl+"/documentiPagamento.do?method=annullaDocumento&amp;"+SharedConstants.CRYPTEDPARAMS+"="+ep.encrypt()+"&URL_BACK="+URLEncoder.encode(url_back_annulla,"UTF-8");
					
						buttonsHtml = "<p>"+createClickableIcon(openUrl , "Apri", "icon-file", IconDimension.X2, true) +"</p>";
		                
		                buttonsHtml += "<p>" + createClickableIcon(cancelUrl, "Annulla", "icon-remove", IconDimension.X2, true) + "</p>";
		            
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace(); // Non dovrebbe mai succedere
						
						throw new RuntimeException(e);
					}
					
	            }
	            
				break;
				
			case NON_PAGABILE_RIMBORSATA:
				statusHtml = createStatus(statusHtml, StatusLevel.SUCCESS);
				
				//Se l'ente ha comunicato un dettaglio rimborso.
				if("R".equals(condizioneVo.getStatoCondizione())) {

	                String dettaglioRimborsoComunicataDaEnte=getDettaglioRimborsoComunicataDaEnte(condizioneVo);             
	                if(!"".equals(dettaglioRimborsoComunicataDaEnte))
	                	statusHtml += " <p> ("+ dettaglioRimborsoComunicataDaEnte + ")</p>";    
				}			
				for (CondizionePagamentoVO condPag :dettaglioAvviso.getCondizioni()) {				
					if (condPag.getPagamento()!=null) {
						// mostro sempre il bottone per scaricare la ricevuta indipendentemente dallo stato incasso
						if (EnumUtils.matchByChiave(condizioneVo.getStatoIncasso(), EnumStatoIncasso.ATTESO, EnumStatoIncasso.ACCREDITATO_CONTO_TECNICO,  EnumStatoIncasso.RIACCREDITATO_ENTE)) {
							if (condPag.getPagamento().isAssociatedDocAvailable()) {
							   DownloadQuietanzaParametersEncrypter dp1 = new DownloadQuietanzaParametersEncrypter(condizioneVo.getCodPagamento(),condizioneVo.getCodPagante(), condPag.getPagamento().getIdPagamento().toString());
							   String cParam1=dp1.encrypt();
							   buttonsHtml += "<p>" + createClickableIcon(gwBaseUrl+"/documentiPagamento.do?method=downloadQuietanza&"+ SharedConstants.CRYPTEDPARAMS +"=" + cParam1, "Ricevuta", "icon-file-alt", IconDimension.X2,true) + "</p>";
							} else {
								buttonsHtml += "<p></p>";
							}
						}
					}
				}	
				
				break;
				
			case NON_PAGABILE_RATEIZZAZIONE:
				// statusHtml = createStatus(statusHtml, StatusLevel.WARNING);
				break;
	
			case NON_PAGABILE_PENDENZA_CHIUSA:
				// statusHtml = createStatus(statusHtml, StatusLevel.WARNING);
				break;
	
			case NON_PAGABILE_PAGAMENTO_IRREGOLARE:
				statusHtml = createStatus(statusHtml, StatusLevel.IMPORTANT);
	
				String dettaglioTransazionePagamentoIrregolare = getDettaglioTransazioneComunicataDaEnte(condizioneVo);
	
				if (!"".equals(dettaglioTransazionePagamentoIrregolare)) {
					statusHtml += "<p>" + dettaglioTransazionePagamentoIrregolare + "</p>";
				}
				break;
	
			default:
				break;
		}

		switch (columnToRender) {
		
			case STATO_COLUMN:
				return statusHtml;
				
			case BUTTONS_COLUMN:
				return buttonsHtml;
				
			default:
				return "";
		}		
	}
	
	
	
	public static boolean operazioneAnnullabile(
			AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso) {
		CondizionePagamentoPosizioneDebitoriaVO p=dettaglioAvviso.getSoluzionePagUnica().get(0);
		ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		;
		if ("NDP".equalsIgnoreCase(p.getFornitoreGateway()) 
			&& "IC".equals(p.getStatoPagamento()) 
			&& props.getBooleanProperty("posizionidebitorie.annulla.operaz.ndp")) {
		      return true;
		}
		return false;
	}


	public static String getDettaglioTransazioneComunicataDaEnte(CondizionePagamentoPosizioneDebitoriaVO condizioneVo) {
        String dettaglioTransazione="";
        
        if (condizioneVo.getDataPagamento()!=null) {
        	dettaglioTransazione += " <br> Data pagamento = " +  dateFormat.format(condizioneVo.getDataPagamento());
        }    
        if (condizioneVo.getCanalePagamento()!=null) {
        	dettaglioTransazione += " <br> Canale pagamento = " +  condizioneVo.getCanalePagamento();
        }    
        if (condizioneVo.getMezzoPagamento()!=null) {
        	dettaglioTransazione += " <br> Mezzo pagamento = " +  condizioneVo.getMezzoPagamento();
        }    
        if (condizioneVo.getImportoPagamento()!=null) {
        	DecimalFormat decf = new DecimalFormat("##,###,##0.00");
        	dettaglioTransazione += " <br> Importo pagato = " +  decf.format(condizioneVo.getImportoPagamento())+"&nbsp;&euro;";
        }    
        if (condizioneVo.getNotePagamento()!=null) {
        	dettaglioTransazione += " <br> Note pagamento = " +  condizioneVo.getNotePagamento();
        }
        
        return dettaglioTransazione;
	}
	
	public static String getDettaglioRimborsoComunicataDaEnte(CondizionePagamentoPosizioneDebitoriaVO condizioneVo) {
        String dettaglioTransazione="";
        
        if (condizioneVo.getDataPagamento()!=null) {
        	dettaglioTransazione += "Rimborso del " +  dateFormat.format(condizioneVo.getDataPagamento());
        }    
        if (condizioneVo.getCanalePagamento()!=null) {
        	dettaglioTransazione += " da " +  condizioneVo.getCanalePagamento();
        }    
        if (condizioneVo.getNotePagamento()!=null) {
        	dettaglioTransazione += " <br> Note: " +  condizioneVo.getNotePagamento();
        }
        
        return dettaglioTransazione;
	}
	
	public static String decorateDettaglioTransazioneComunicataDaEnte(CondizionePagamentoPosizioneDebitoriaVO condizioneVo) {

		if("R".equals(condizioneVo.getStatoCondizione())) 
			return getDettaglioRimborsoComunicataDaEnte(condizioneVo);
		else
			return getDettaglioTransazioneComunicataDaEnte(condizioneVo);

	}
	
	public static String decorateStatoPagamentoBOEHtml(String statoCondizione, String statoPagamento, PageContext pageContext) {
		return decorateStatoPagamentoHtml(statoCondizione, statoPagamento, pageContext, "BOE");
	}
	
	public static String decorateStatoPagamentoBOEDetailHtml(String statoCondizione, String statoPagamento, PageContext pageContext) {
		return decorateStatoPagamentoHtml(statoCondizione, statoPagamento, pageContext, "BOE_D");
	}
	
	public static String decorateStatoPagamentoHtml(String statoCondizione, String statoPagamento, PageContext pageContext) {
		return decorateStatoPagamentoHtml(statoCondizione, statoPagamento, pageContext, "");
	}
		
	/**
	 * USATA DA BACKOFFICE
	 * @param statoCondizione
	 * @param statoPagamento
	 * @param pageContext
	 * @return
	 */
	public static String decorateStatoPagamentoHtml(String statoCondizione, String statoPagamento, PageContext pageContext, String textMsgTail) {
		String retval = "";
		try {
			statoCondizione = statoCondizione.trim();// lo stato condizione
														// pagamento lo assumo
														// sempre non null
			String tmp_risp = calcolaStato(statoCondizione, statoPagamento);

			if (PosizioneDebitoriaHelper.PAGATO.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + pagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.pagato") + "</span>";
			} else if (PosizioneDebitoriaHelper.NONPAGABILE.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + nonpagabilecolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.nonpagabile") + "</span>";
			} else if (PosizioneDebitoriaHelper.DAPAGARE.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + nonpagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.dapagare") + "</span>";
			} else if (PosizioneDebitoriaHelper.INCORSO.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + giallocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.incorso") + "</span>";
			} else if (PosizioneDebitoriaHelper.PAGATOSALVOBUONFINE.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + giallocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.pagatosalvo") + "</span>";
			} else if (PosizioneDebitoriaHelper.ERRORE.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + nonpagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.errore") + "</span>";
			} else if (PosizioneDebitoriaHelper.ANNULLATO.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + nonpagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.annullato" + textMsgTail) + "</span>";
			} else if (PosizioneDebitoriaHelper.INCARRELLO.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + nonpagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.incarrello") + "</span>";
			} else if (PosizioneDebitoriaHelper.RIMBORSATO.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + pagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.rimborsato" + textMsgTail) + "</span>";
			} else if (PosizioneDebitoriaHelper.RIMBORSATO_ENTE.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + pagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.rimborsato.ente" + textMsgTail) + "</span>";
			} else if (PosizioneDebitoriaHelper.IRREGOLARE.equalsIgnoreCase(tmp_risp)) {
				retval = "<span class='" + nonpagatocolor + "'>" + getTextMsg(pageContext, "posizionedebitoria.statoPagamento.irregolare") + "</span>";
			} 

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retval;
	}

	/**
	 * Verifica se una condizione di pagamento singola o a rate � pagabile,
	 * indipendentemente dalle altre, ma semplicemente in base al suo stato e
	 * allo stato dei suoi pagamenti.
	 * 
	 * @param statoCondizione
	 *            lo stato della condizione.
	 * @param statoPagamento
	 *            lo stato dei pagamenti associati alla condizione.
	 * @return true se la condizione � pagabile, indipendentemente dalle altre,
	 *         false altrimenti.
	 */
	public static boolean consentirePagamento(String statoCondizione, String statoPagamento) {
		return PosizioneDebitoriaHelper.isCondizionePagabile(statoCondizione, statoPagamento);
	}

	public static boolean consentirePagamentoCompresoNonPagabile(String statoCondizione, String statoPagamento) {
		return PosizioneDebitoriaHelper.isCondizionePagabileCompresoNonPagabile(statoCondizione, statoPagamento);
	}

	/**
	 * Restituisce una stringa che rappresenta la descrizione dello stato del
	 * pagamento calcolata in base allo stato della condizione di pagamento e
	 * allo stato del pagamento.
	 * 
	 * @param statoCondizione
	 *            stato della condizione di pagamento
	 * @param statoPagamento
	 *            stato del pagamento
	 * @return una stringa che rappresenta la descrizione dello stato del
	 *         pagamento calcolata in base allo stato della condizione di
	 *         pagamento e allo stato del pagamento.
	 */
	public static String calcolaStato(String statoCondizione, String statoPagamento) {
		return PosizioneDebitoriaHelper.calcolaStatoCondizionePagamento(statoCondizione, statoPagamento);
	}

	private static String getTextMsg(PageContext pageContext, String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message);
		} catch (Exception Ex) {
		}
		return msg;
	}

	/**
	 * Il pagamento in una unica soluzione � consentito solo se il pagamento in
	 * unica soluzione non risulta gi� effettuato e non si � effettuato alcun
	 * pagamento a rate.
	 * 
	 * @param dettaglioAvviso
	 *            AvvisoPosizioneDebitoriaDettaglioVO contenente il dettagli
	 *            dell'avviso con le sue condizioni di pagamento.
	 * @return true se il pagamento in una unica soluzione � consentito.
	 */
	public static boolean isSoluzioneUnicaPagabile(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso) {

		// boolean isSolUnicaPagabile =
		// consentirePagamento(dettaglioAvviso.getSoluzionePagUnica().getStatoCondizione(),
		// dettaglioAvviso.getSoluzionePagUnica().getStatoPagamento());

		List<CondizionePagamentoPosizioneDebitoriaVO> rate = dettaglioAvviso.getRatePagamento();
		
		boolean isRataPagata = false;
		
		for (CondizionePagamentoPosizioneDebitoriaVO coPagamento : rate) {
			
			if (!consentirePagamento(coPagamento.getStatoCondizione(), coPagamento.getStatoPagamento())) {
				
				isRataPagata = true;
				
				break;
				
			}
		}
		
		// return isSolUnicaPagabile && !isRataPagata;
		return isRataPagata;
	}

	/**
	 * Verifica se la rata in ingresso � pagabile, cio� se non � ancora stata
	 * pagata e nemmeno il pagamento in un unica soluzione � stato effettuato.
	 * 
	 * @param dettaglioAvviso
	 *            AvvisoPosizioneDebitoriaDettaglioVO contenente il dettagli
	 *            dell'avviso con le sue condizioni di pagamento.
	 * @param rata
	 *            CondizionePagamentoPosizioneDebitoriaVO che rappresenta la
	 *            rata di cui vogliamo verificare se � pagabile.
	 * @return true se la rata in ingresso � pagabile, false altrimenti.
	 */
//	public static boolean isRataPagabile(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso, CondizionePagamentoPosizioneDebitoriaVO rata) {
//
//		boolean isRataPagabile = true;
//
//		// se esiste una condizione singola non pagabile, esco subito perch�
//		// allora niente � pagabile
//		if (esisteRataNonPagabile(dettaglioAvviso.getSoluzionePagUnica())) {
//			isRataPagabile = false;
//		} else {
//			// se la condizione � di tipo singolo e una rata non � pagabile,
//			// allora anche il singolo non � pagabile
//			if (rata.getTipoPagamento().equalsIgnoreCase("S")) {
//				isRataPagabile = consentirePagamento(rata.getStatoCondizione(), rata.getStatoPagamento());
//
//				if (isRataPagabile) {
//					if (esisteRataNonPagabile(dettaglioAvviso.getRatePagamento()) || esisteRataAssociataADDP(dettaglioAvviso.getRatePagamento())) {
//						isRataPagabile = false;
//					}					
//				}
//				if (isRataPagabile) {
//					isRataPagabile = checkValiditaPagamento(rata.getDataFine(), rata.getDataInizio());
//				}
//			} else {
//				isRataPagabile = consentirePagamento(rata.getStatoCondizione(), rata.getStatoPagamento());
//				if (isRataPagabile) {
//					isRataPagabile = checkValiditaPagamento(rata.getDataFine(), rata.getDataInizio());
//				}
//				// affinch� sia pagabile � necessario che la soluzione unica non sia associata a un DDP
//				isRataPagabile = isRataPagabile && !esisteRataAssociataADDP(dettaglioAvviso.getSoluzionePagUnica());
//
//			}
//		}
//
//		return isRataPagabile && !rata.isDDPAssociated();
//
//	}

	
//	private static boolean checkValiditaPagamento(Date dataFine, Date dataInizio) {
//		Date today = new Date();
//
//		// entrambi != null
//		if (dataFine != null && dataInizio != null) {
//			if (dateGreaterthanToday(dataFine) && dataInizio.before(today)) {
//				return true;
//			}
//		}
//
//		// se data fine null
//		if (dataFine == null && dataInizio != null) {
//			if (dataInizio.before(today)) {
//				return true;
//			}
//		}
//		// se data inizio null
//		if (dataInizio == null && dataFine != null) {
//			if (dateGreaterthanToday(dataFine)) {
//				return true;
//			}
//		}
//
//		return false;
//	}

	/**
	 * Stabilisce se esiste una condizione pagabile per cui abbia senso
	 * mantenere abilitato il pulsante.
	 * 
	 * @param dettaglioAvviso
	 *            AvvisoPosizioneDebitoriaDettaglioVO contenente il dettagli
	 *            dell'avviso con le sue condizioni di pagamento.
	 * @return true se esiste qualche condizione rateale o singola pagabile,
	 *         false altrimenti.
	 */
//	public static boolean isPulsantePagaAbilitato(AvvisoPosizioneDebitoriaDettaglioVO dettaglioAvviso) {
//
//		// CondizionePagamentoPosizioneDebitoriaVO solPagUnica =
//		// dettaglioAvviso.getSoluzionePagUnica();
//
//		// if (solPagUnica == null &&
//		// dettaglioAvviso.getRatePagamento().isEmpty())
//		// return false;
//
//		if (dettaglioAvviso.getRatePagamento().isEmpty() && dettaglioAvviso.getSoluzionePagUnica().isEmpty())
//			return false;
//
//		// boolean isSolUnicaPagabile = solPagUnica != null ?
//		// consentirePagamento(solPagUnica.getStatoCondizione(),
//		// solPagUnica.getStatoPagamento()) : true;
//
//		return true;
//
//		// return isSolUnicaPagabile && esisteRataPagabile(dettaglioAvviso);
//	}

	/**
	 * Stabilisce se esiste una condizione rateale pagabile tra quelle del
	 * dettaglio dell'avviso in ingresso.
	 * 
	 * @param dettaglioAvviso
	 *            AvvisoPosizioneDebitoriaDettaglioVO contenente il dettagli
	 *            dell'avviso con le sue condizioni di pagamento.
	 * @return true se esiste una condizione rateale pagabile tra quelle del
	 *         dettaglio dell'avviso in ingresso, false altrimenti.
	 */
//	private static boolean esisteRataPagabile(List rate) {
//		for (Iterator it = rate.iterator(); it.hasNext();) {
//			CondizionePagamentoPosizioneDebitoriaVO coPagamento = (CondizionePagamentoPosizioneDebitoriaVO) it.next();
//			if (consentirePagamento(coPagamento.getStatoCondizione(), coPagamento.getStatoPagamento())) {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * Verifica che una rata nell'elenco sia in uno stato che impedisce il
	 * pagamento
	 * 
	 * @param rate
	 * @return true se almeno una rata non � pagabile
	 */
//	private static boolean esisteRataNonPagabile(List rate) {
//		if (rate == null)
//			return false;
//
//		for (Iterator it = rate.iterator(); it.hasNext();) {
//			CondizionePagamentoPosizioneDebitoriaVO coPagamento = (CondizionePagamentoPosizioneDebitoriaVO) it.next();
//			if (!consentirePagamentoCompresoNonPagabile(coPagamento.getStatoCondizione(), coPagamento.getStatoPagamento())) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * ritorna true se c'� un documento di pagamento associato a una rata 
	 * @param rate
	 * @return
	 */
//	private static boolean esisteRataAssociataADDP(List rate) {
//		boolean ddpAssociated = false;
//		
//		if (rate != null) {
//				for (Iterator it = rate.iterator(); !ddpAssociated && it.hasNext();) {
//					CondizionePagamentoPosizioneDebitoriaVO coPagamento = (CondizionePagamentoPosizioneDebitoriaVO) it.next();
//					ddpAssociated = coPagamento.isDDPAssociated();
//				}
//		}
//		return ddpAssociated;
//	}
	
	
	/**
	 * Restituisce una stringa che il periodo di validit� della
	 * condizione di pagamento, solo per l'operatore Cittadino
	 * 
	 * @param dataFineValidita
	 *            data di fine validit� della condizione di pagamento
	 */
	public static String notePagamento(PageContext pageContext) {
		
		String notePagamento = "";
		
		notePagamento = getTextMsg(pageContext, "posizionedebitoria.statoPagamento.notePagamento.validita")+ " " + date2StringFormat("dd/MM/yyyy", Calendar.getInstance().getTime());
		
		return notePagamento;
	}


	/**
	 * Restituisce la label per Data scadenza pagemento in base al tipo operatore
	 * 
	 * @param operatore
	 *            
	 */
	public static String dataScadenzaLabel(String operatore, PageContext pageContext) {
		String retval = "";
		try {
			if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equalsIgnoreCase(operatore)) {
				retval = getTextMsg(pageContext, "posizionedebitoria.dettaglio.dataScadenzaPagamento.cittadino");
			} else {
				retval = getTextMsg(pageContext, "posizionedebitoria.dettaglio.dataScadenza");
			} 

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retval;
	}

	public static String decorateAllegatiLinkHtml(AllegatoAvvisoPosDeb allegato,String idCondizione, String idPendenza, PageContext pageContext, String operatore) {
		
		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");
		String allegatoMsg="";
		String imgURL="";
		String linkAllegato = gwBaseUrl+"/documentiPagamento.do?method=prepareDownloadAllegato&" + SharedConstants.CRYPTEDPARAMS + "=";
		
		String desc="";
		
		final AllegatoParametersEncrypter encrypter = new AllegatoParametersEncrypter(allegato.getTiAllegato(),allegato.getIdallegato(),idCondizione,idPendenza);
		String cParam = encrypter.encrypt();
		
		if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equals(operatore)) {
			
			//gli allegati MDB non devono essere mostrato
			if(!EnumTipoAllegato.DOCUMENTO.getDescrizione().equals(allegato.getTiAllegato()) && !"DatiMBD.txt".equals(allegato.getTitolo()) ) {
				
				if (EnumTipoAllegato.NDC.getDescrizione().equals(allegato.getTiAllegato())) {
					imgURL ="/images/icona_allegato.png";	
					desc = "Nota di Credito";
				} else {
					imgURL="/images/graffetta_grigia.gif";
					desc = "Scarica Allegato";
				}
				
				allegatoMsg += createClickableIcon(linkAllegato+cParam, desc, "icon-file-alt", IconDimension.X2,true);

			}	
			
		} else { 
			
			if (EnumTipoAllegato.NDC.getDescrizione().equals(allegato.getTiAllegato())) {
				imgURL ="/images/icona_allegato.png";		
			} else {
				imgURL="/images/graffetta_grigia.gif";
			}
			allegatoMsg += "<a href='" + linkAllegato + cParam + "'>"
					 + "<img style=\"width:24px\" src='"+ WebUtility.getContextPath(pageContext)+ imgURL +  "' alt='"+allegato.getTitolo()
					 +"' title='"+dateFormat.format(allegato.getTsDecorrenza()) +"&nbsp;"
					 +"("+allegato.getTiAllegato()+")&nbsp;"
				     +allegato.getTitolo()+"'/>" 
					 +"</a>";
			
		}
		
		return allegatoMsg;
	}
	
	public static String decorateAvvisaturaLinkHtml(CondizionePagamentoPosizioneDebitoriaVO condVO, String tipoAvvisatura,PageContext pageContext, String idPendenza) { 
		StringBuilder sb = new StringBuilder(); 

		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties"); 

		String gwBaseUrl= gwProperties.getProperty("paytas.gateway.webapp.baseUrl"); 

		String tooltip = "Reinvia"; 



		if (condVO.getPrenotaAvvisiDigitali()!=null && !condVO.getPrenotaAvvisiDigitali().isEmpty()) { 

			PrenotaAvvisiDigitaliVO pren = condVO.getPrenotaAvvisiDigitali().get(0); 
			if (tipoAvvisatura.equals("NDP")) { 
				if (pren.getStatoAvviso().equals(EnumStatoAvviso.ERROR.getChiave()) || 
						pren.getStatoAvviso().equals(EnumStatoAvviso.INVIATO_KO.getChiave())) { 

					String openUrl = "./PDC/avvisiMain.do?method=reinviaAvvisoDigitale&isLente=true&selCheckbox="+idPendenza+"&tipoAvv=NDP&idAvv="+pren.getId(); 
					sb.append("<table>"); 
					sb.append("  <tr>"); 
					sb.append("    <td style=\"padding: 5px 10px 5px 5px;\">"); 
					sb.append("      KO"); 
					sb.append("    </td>");                     
					sb.append("    <td>"); 
					sb.append("      <a class=\"export-button exp-btn-pdf\" href=\"" + openUrl + "\">"); 
					sb.append("        <img src=\"images/busta.png\" alt=\"" + tooltip + "\" title=\"" + tooltip + "\"/>"); // era commentato 
					sb.append("        <i class='icon-file-alt icon' alt='"+ tooltip+"' title='" + tooltip +"'></i>");                       
					sb.append("      </a>"); 
					sb.append("    </td>"); 
					sb.append("  </tr>"); 
					sb.append("</table>"); 
				} else { 
					EnumStatoAvviso stato = EnumStatoAvviso.getByKey(pren.getStatoAvviso()); 
					if (stato==null){ 
						sb.append("-"); 
					} else { 
						sb.append(stato.getDescrizione()); 
					} 
				} 
			} 
			if (tipoAvvisatura.equals("SMS")) { 
				sb.append("Non prevista"); 
			} 
			if (tipoAvvisatura.equals("EMAIL")) { 
				sb.append("Non prevista"); 
			} 

		} else {                         
			sb.append("Non prevista"); 
		}        
		return sb.toString(); 
	} 
}
